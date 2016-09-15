package Library.WorkingWithFiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    public static ArrayList<String> toArrayList(String filename) {
        ArrayList<String> fileStrings = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String templine;
            while (!(templine = in.readLine()).equals("")) {
                fileStrings.add(templine);
            }
            in.close();
        } catch (FileNotFoundException fnfe) {
            fnfe.getMessage();
        } catch (IOException ioe) {
            ioe.getMessage();
        }
        return fileStrings;
    }
}
