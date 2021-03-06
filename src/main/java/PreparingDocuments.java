import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;



public class PreparingDocuments {
    public static void makeSamplesFromSourceBook(String myBookName, int authorID, int bookID, int docSize) {
        ArrayList<String> fileStrings = ReadFile.toArrayList(myBookName); //read file to an arraylist of lines
        MyStemJava stemJava = new MyStemJava();
        ArrayList<String> tokens = null;
        try {
            tokens = stemJava.toStem(fileStrings);
        } catch (MyStemApplicationException e) {
            e.printStackTrace();
        }
        StringBuffer outTokens = new StringBuffer();
        int numberOfDocs = 0;

        String tempPath = "src/docsTrain/" + authorID + "/" + bookID + "/";
        try {
            Files.createDirectories(Paths.get(tempPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pathBookFileName = tempPath + authorID + "-" + bookID;
        WriteFile.fromArrayList(pathBookFileName, tokens, 0, tokens.size());

        for (int i = 0; i < tokens.size() - docSize; i += docSize) {
            numberOfDocs++;
            String pathFileName = tempPath + authorID + "-" + bookID + "-" + numberOfDocs;
            WriteFile.fromArrayList(pathFileName, tokens, i, docSize);
        }

        try {
            FileWriter fw = new FileWriter(tempPath + "path.txt", false);
            fw.write(numberOfDocs);
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

}
