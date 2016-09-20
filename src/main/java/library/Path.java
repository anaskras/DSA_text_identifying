package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Nastya on 20.09.2016.
 */
public class Path {
    public void createMap(){
        int[] authorsId = findAuthorsID();
        for (int i = 0; i < authorsId.length; i++){
            int[] booksId = findBooksID(authorsId[i]);
            for (int j = 0; j < booksId.length; j++){
                int[] docsId = findDocsID(authorsId[i], booksId[j]);
                for (int k = 0; k < docsId.length; k++){

                }
            }
        }
    }

    public int[] findAuthorsID(){
        try {
            Scanner scAuth = new Scanner(new File("src\\main\\java\\library\\docsTrain\\path.txt"));
            int numberOf = scAuth.nextInt();
            int[] result = new int[numberOf];
            for (int i = 0; i < numberOf; i++) {
                result[i] = scAuth.nextInt();
            }
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public int[] findBooksID (int authorID) {
        String path = "src\\main\\java\\library\\docsTrain\\" + authorID + "\\path.txt";
        try {
            Scanner scBook = new Scanner(new File(path));
            int numberOf = scBook.nextInt();
            int[] result = new int[numberOf];
            for (int i = 0; i < numberOf; i++) {
                result[i] = scBook.nextInt();
            }
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public int[] findDocsID (int authorID, int bookID) {
        String path = "src\\main\\java\\library\\docsTrain\\" + authorID + "\\" + bookID + "\\path.txt";
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
}
