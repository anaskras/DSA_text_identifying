import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {
    public static void fromStringBuffer(StringBuffer output, String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(output.toString());
            out.close();
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public static void fromArrayList(String fileName, ArrayList<String> outText, int start, int end) {
        try {
            FileWriter fw = new FileWriter(fileName, false);
            for (int i = start; i < start + end; i++) {
                fw.write(outText.get(i) + " ");
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
