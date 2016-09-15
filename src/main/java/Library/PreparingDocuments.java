package Library;

import Library.WorkingWithFiles.ReadFile;
import Library.WorkingWithFiles.WriteFile;

import java.util.ArrayList;

public class PreparingDocuments {
    public void makeDocs(String mybookname, int authorID, int bookID){
        ArrayList<String> fileStrings = ReadFile.toArrayList(mybookname); //read file to an arraylist of lines
        StringBuffer outTokens = new StringBuffer();
        if (fileStrings.size() != 0) {

            int numberOfDocs = 1; //number of currend document of this book to file
            int numberOfTokens = 0;

            for (String line : fileStrings) {
                ArrayList<String> tokens = new ArrayList<String>(); //lemmatization to make an array of tokens

                for (String token : tokens){
                    numberOfTokens++;
                    if ((numberOfTokens % 10000) == 0){
                        String filename = authorID + "-" + bookID + "-0" + numberOfDocs; //forming filename
                        WriteFile.fromStringBuffer(outTokens, filename);
                        numberOfDocs++;
                        outTokens.delete(0, outTokens.length());
                    } else {
                        outTokens.append(token + "\n");
                    }
                }
            }
        } else {
            System.out.println("There is something wrong with your book.");
        }
    }
}
