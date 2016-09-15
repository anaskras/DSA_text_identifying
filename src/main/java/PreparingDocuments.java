import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.io.FileNotFoundException;
import java.util.ArrayList;



public class PreparingDocuments {
    public void makeDocs(String mybookname, int authorID, int bookID, int docSize) throws MyStemApplicationException, FileNotFoundException {
        ArrayList<String> fileStrings = ReadFile.toArrayList(mybookname); //read file to an arraylist of lines
        MyStemJava stemJava = new MyStemJava();
        ArrayList<String> tokens = stemJava.toStem(fileStrings);
        StringBuffer outTokens = new StringBuffer();
        int numberOfDocs = 0;
        for (int i = 0; i < tokens.size()-docSize; i += docSize) {
            numberOfDocs++;
            String filename = authorID + "-" + bookID + "-0" + numberOfDocs;
            WriteFile.fromArrayList(filename, tokens, i, docSize);
        }
    }
}
