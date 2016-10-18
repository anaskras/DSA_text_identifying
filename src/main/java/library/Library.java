package library;

import Matrix.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by tokar on 16.09.2016.
 */
public class Library {

    TreeMap<String, Integer> fullMap;

    private int samplingWordCount;

    private boolean selfTesting;

    private int testingPercent;

    private boolean crossvalidation;
    private int crossvalidationFolds;

    private ArrayList<Author> authors;
    private ArrayList<TextSample> testSamples;

    private ArrayList<Answer>  answers;

    public Library(){
        fullMap = new TreeMap<String, Integer>();
        authors = new ArrayList<Author>();
        testSamples = new ArrayList<TextSample>();
        try {

            Scanner scLib = new Scanner(new File("src\\main\\java\\library\\DoNotTouchThisIsLibrarySettings.txt"));
            samplingWordCount = scLib.nextInt();
            String testingMode = scLib.next();
            if (testingMode.equals("SelfTesting")){
                selfTesting = true;
                testingMode = scLib.next();
                if (testingMode.equals("crossvalidation")){
                    crossvalidation = true;
                    crossvalidationFolds = scLib.nextInt();
                }else {
                    testingPercent = scLib.nextInt();
                }
            } else {
                selfTesting = false;
            }
            scLib.close();

            Scanner scAuth = new Scanner(new File("src\\main\\java\\library\\sourceTextsTrain\\Authors.txt"));
            while (scAuth.hasNext()){
                int authorID = scAuth.nextInt();
                scAuth.skip(" ");
                String authorName = scAuth.nextLine();
                Author author = new Author(authorName, authorID);

                Scanner scBook = new Scanner(new File("src\\main\\java\\library\\sourceTextsTrain\\"+authorID+"\\"+"Books.txt"));
                while (scBook.hasNext()) {
                    int bookID = scBook.nextInt();
                    scBook.skip(" ");
                    String bookName = scBook.nextLine();
                    Book book = new Book(author, bookName, bookID);
                    author.addBook(book);
                }
                scBook.close();

                authors.add(author);
            }
            scAuth.close();
            createFullMapAndSamples();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Created by Nastya on 20.09.2016.
     */
    public void viewAuthorClassifyingResults() {
        classifyToAuthor();
        double right = 0;
        double fail = 0;
        for (int i = 0; i < answers.size(); i++) {
            System.out.print(testSamples.get(i) + " classified to author: " + answers.get(i).classID + System.lineSeparator());
            if (answers.get(i).classID == testSamples.get(i).getAuthorID()) {
                right++;
            } else {
                fail++;
                for (int j = 0; j < answers.get(i).distancesToClasses.length; j++) {
                    System.out.println("\tdistance to " + String.valueOf(j + 1) + ": " + answers.get(i).distancesToClasses[j]);
                }
            }
        }
        System.out.println("accuracy: " + String.valueOf(right / answers.size()));
    }

    public void classifyToAuthor(){
        ArrayList<Matrix> authorsMatrix = new ArrayList<Matrix>(authors.size());
        for (int i = 0;i<authors.size();i++){
            authorsMatrix.add(makeMatrix(authors.get(i).getTrainSamples()));
        }
        Matrix testSamplesMatrix = makeMatrix(testSamples);
        answers =  getAuthorsBySamples(authorsMatrix, testSamplesMatrix);
    }

    private ArrayList<Answer> getAuthorsBySamples(ArrayList<Matrix> authorMatrix, Matrix sampleTests) {
        //create an array where we will keep authors for samples
        ArrayList<Answer> result = new ArrayList<Answer>(sampleTests.getRows());
        for (int i = 0; i < sampleTests.getRows(); i++) {
            int authorID = 0;
            double minDist = Double.MAX_VALUE;
            double[] distances = new double[authorMatrix.size()];
            for (int j = 0; j < authorMatrix.size(); j++) {
                distances[j] = Matrix.euclidianDistance(sampleTests.getRow(i), authorMatrix.get(j).averageVector());
                //distances[j] = Matrix.cityBlockDistance(sampleTests.getRow(i), authorMatrix.get(j).averageVector());
                //distances[j] = Matrix.chebychevDistance(sampleTests.getRow(i), authorMatrix.get(j).averageVector());
                //distances[j] = Matrix.minkowskiDistance(sampleTests.getRow(i), authorMatrix.get(j).averageVector(), 3);
                if ((distances[j] < minDist) && (authorMatrix.get(j).getDiameter(authorMatrix.get(j).euclideanDistance()) >= distances[j])) {
                    minDist = distances[j];
                    authorID = j + 1;
                }
            }
            result.add(new Answer(authorID, distances));
        }
        return result;
    }

    private Matrix makeMatrix(ArrayList<TextSample> samples){
        double[][] matrix = new double[samples.size()][];
        for (int i = 0; i< matrix.length; i++){
            matrix[i] = makeVector(samples.get(i).getMyMap());
        }
        return new Matrix(matrix);
    }

    private double[] makeVector(HashMap<String, Integer> map){
        double [] vector = new double[fullMap.size()];
        int i = 0;
        for (String s: fullMap.keySet()) {
            if (map.containsKey(s)){
                vector[i] = map.get(s);
            }else{
                vector[i] = 0;
            }
            i++;
        }
        return vector;
    }

    public void createFullMapAndSamples(){
        int[] authorsId = findAuthorsID();
        Random random = new Random();
        for (int i = 0; i < authorsId.length; i++){
            int[] booksId = findBooksID(authorsId[i]);
            for (int j = 0; j < booksId.length; j++){
                int[] docsId = findDocsID(authorsId[i], booksId[j]);
                for (int k = 0; k < docsId.length; k++){
                    TextSample sample = new TextSample(authorsId[i], booksId[j],docsId[k], fullMap);
                    if(random.nextInt() % 10 < 8){
                        authors.get(authorsId[i]-1).addSample(sample);
                    }else{
                        testSamples.add(sample);
                    }
                }
            }
        }
    }

    public int[] findAuthorsID(){
        return readPaths("src\\main\\java\\library\\docsTrain\\path.txt");
    }

    public int[] findBooksID (int authorID) {
        String path = "src\\main\\java\\library\\docsTrain\\" + authorID + "\\path.txt";
        return readPaths(path);
    }

    public int[] findDocsID (int authorID, int bookID) {
        String path = "src\\main\\java\\library\\docsTrain\\" + authorID + "\\" + bookID + "\\path.txt";
        return readPaths(path);
    }
    private int [] readPaths (String path){
        try {
            Scanner scDoc = new Scanner(new File(path));
            int numberOf = scDoc.nextInt();
            int[] result = new int[numberOf];
            for (int i = 0; i < numberOf; i++) {
                result[i] = scDoc.nextInt();
            }
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Created by tokar on 16.09.2016.
     */
    public static class Author implements Comparable<Author> {
        private int id;
        private String name;
        ArrayList<Book> books;
        private ArrayList<TextSample> trainSamples;
        public Author(String name, int id) {
            trainSamples = new ArrayList<TextSample>();
            this.id = id;
            this.name = name;
            books = new ArrayList<Book>();
        }

        public void addSample(TextSample sample){
            trainSamples.add(sample);
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public int getId() {
            return id;
        }

        public ArrayList<TextSample> getTrainSamples() {
            return trainSamples;
        }

        @Override
        public String toString() {
            String s = "AuthorID: " + id + System.lineSeparator() + "AuthorName: " + name + System.lineSeparator() + "Books:" + System.lineSeparator();
            for (Book b : books) {
                s += b.toString();
            }
            return s;
        }

        public int compareTo(Author o) {
            return id - o.getId();
        }
    }

    /**
     * Created by tokar on 16.09.2016.
     */
    public static class Book {
        private int id;
        private String name;
        private Author author;

        public Book(Author author, String name, int id) {
            this.name = name;
            this.id = id;
            this.author = author;
        }

        @Override
        public String toString() {
            return "BookID: " + id + System.lineSeparator() + "BookName: " + name + System.lineSeparator();
        }
    }


    /**
     * Created by tokar on 20.09.2016.
     */
    public static class Answer {
        public int classID;
        public double [] distancesToClasses;
        public Answer (int classesSize){
            distancesToClasses = new double[classesSize];
        }

        public Answer (int classID, double[] distancesToClasses){
            this.classID = classID;
            this.distancesToClasses = distancesToClasses;
        }
    }
}
