import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    public static ArrayList<String> toArrayList(String filename) {
        ArrayList<String> text = new ArrayList<String>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {
                String temp = in.nextLine();
                if (!temp.equals("")) {
                    text.add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }
}
