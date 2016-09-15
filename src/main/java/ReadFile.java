import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    public static ArrayList<String> toArrayList(String filename)  {
        ArrayList<String> text = new ArrayList<String>();
        Scanner in = null;
        try {
            in = new Scanner(new File(filename));
            while (in.hasNext()) {
                text.add(in.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }
}
