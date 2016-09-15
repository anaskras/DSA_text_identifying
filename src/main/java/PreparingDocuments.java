import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;



public class PreparingDocuments {
    public static void makeDocs(String mybookname, int authorID, int bookID, int docSize)  {
        ArrayList<String> fileStrings = ReadFile.toArrayList(mybookname); //read file to an arraylist of lines
        MyStemJava stemJava = new MyStemJava();
        ArrayList<String> tokens = null;
        try {
            tokens = stemJava.toStem(fileStrings);
        } catch (MyStemApplicationException e) {
            e.printStackTrace();
        }
        StringBuffer outTokens = new StringBuffer();
        int numberOfDocs = 0;
        for (int i = 0; i < tokens.size()-docSize; i += docSize) {
            numberOfDocs++;

            String tempPath = "docs/" + authorID + "/" + bookID + "/";
            Path path = Paths.get(tempPath);
            String pathFileName = tempPath + authorID + "-" + bookID + "-" + numberOfDocs + ".txt";
            WriteFile.fromArrayList(pathFileName, tokens, i, docSize);
        }
    }
}
