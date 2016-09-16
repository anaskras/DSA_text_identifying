package library;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tokar on 16.09.2016.
 */
public class TextSample {
    private int id;
    private Library.Author author;
    private Library.Book book;
    private double [] myVector;

    public TextSample(int id, TreeMap fullMap, HashMap myMap) {
        this.id = id;
        author = null;
        book = null;
        TreeMap<String, Integer> treeMap = new TreeMap(fullMap);
        treeMap.putAll(myMap);
        if (treeMap.size() != fullMap.size()) {
            System.out.println("Unknown words! Update Library dict!");
            System.exit(-1);
        }
        myVector = new double[myMap.size()];
        int i = 0;
        for (Map.Entry<String, Integer> e: treeMap.entrySet()) {
            myVector[i]=e.getValue();
        }
    }

    public TextSample(Library.Author author, int id, TreeMap fullMap, HashMap myMap) {
        this.id = id;
        this.author = author;
        book = null;
        TreeMap<String, Integer> treeMap = new TreeMap(fullMap);
        treeMap.putAll(myMap);
        if (treeMap.size() != fullMap.size()) {
            System.out.println("Unknown words! Update Library dict!");
            System.exit(-1);
        }
        myVector = new double[myMap.size()];
        int i = 0;
        for (Map.Entry<String, Integer> e : treeMap.entrySet()) {
            myVector[i] = e.getValue();
        }
    }

    public TextSample(Library.Book book, int id, TreeMap fullMap, HashMap myMap) {
        this.id = id;
        this.author = book.getAuthor();
        this.book = book;
        TreeMap<String, Integer> treeMap = new TreeMap(fullMap);
        treeMap.putAll(myMap);
        if (treeMap.size() != fullMap.size()) {
            System.out.println("Unknown words! Update Library dict!");
            System.exit(-1);
        }
        myVector = new double[myMap.size()];
        int i = 0;
        for (Map.Entry<String, Integer> e : treeMap.entrySet()) {
            myVector[i] = e.getValue();
        }
    }

    public int getId() {
        return id;
    }

}
