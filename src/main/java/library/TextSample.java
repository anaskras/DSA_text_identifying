package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by tokar on 16.09.2016.
 */
public class TextSample {
    private int sampleID;
    private int authorID;
    private int bookID;
    private HashMap<String, Integer> myMap;
    private int myMapSize;
    private int myMapWordCount;

    public TextSample(int authorID, int bookID, int sampleID, TreeMap fullMap) {
        String path = "src\\main\\java\\library\\docsTrain\\" + authorID + "\\" + bookID + "\\" + authorID + "-" + bookID + "-" + sampleID + "-map.txt";
        this.authorID = authorID;
        this.bookID = bookID;
        this.sampleID = sampleID;
        try {
            Scanner sc = new Scanner(new File(path));
            myMapSize = sc.nextInt();
            myMap = new HashMap<String, Integer>(myMapSize);
            myMapWordCount = sc.nextInt();
            for (int i = 0; i < myMapSize; i++) {
                String word = sc.next();
                Integer count = sc.nextInt();
                myMap.put(word, count);
                count += (Integer) fullMap.get(word);
                fullMap.put(word, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
