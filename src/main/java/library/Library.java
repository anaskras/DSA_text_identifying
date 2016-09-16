package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by tokar on 16.09.2016.
 */
public class Library {

    TreeMap<String, Integer> words;

    private int samplingWordCount;

    private boolean selfTesting;

    private int testingPercent;

    private boolean crossvalidation;
    private int crossvalidationFolds;

    private ArrayList<Author> authors;
    private ArrayList<TextSample> trainSamples;
    private ArrayList<TextSample> testSamples;
    Library(){
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
                String authorName = scAuth.nextLine();
                Author author = new Author(authorName, authorID);

                Scanner scBook = new Scanner(new File("src\\main\\java\\library\\sourceTextsTrain\\"+authorID+"\\"+"Books.txt"));
                while (scBook.hasNext()) {
                    int bookID = scBook.nextInt();
                    String bookName = scBook.nextLine();
                    Book book = new Book(author, bookName, bookID);
                    author.addBook(book);
                }
                scBook.close();

                authors.add(author);
            }
            scAuth.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Created by tokar on 16.09.2016.
     */
    public static class Author implements Comparable<Author> {
        private int id;
        private String name;
        ArrayList<Book> books;

        public Author(String name, int id) {
            this.id = id;
            this.name = name;
            books = new ArrayList<Book>();

        }

        public void addBook(Book book) {
            books.add(book);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Book> getBooks() {
            return books;
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

        public Author getAuthor() {
            return author;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "BookID: " + id + System.lineSeparator() + "BookName: " + name + System.lineSeparator();
        }
    }
}
