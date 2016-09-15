import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public static void fromArrayList(String fileName, ArrayList<String> outText, int start, int count) {
        HashMap<String, Integer> map = new HashMap<String, Integer>((int) Math.sqrt(count));
        try {
            FileWriter fw = new FileWriter(fileName + ".txt", false);
            for (int i = start; i < start + count; i++) {
                fw.write(outText.get(i) + " ");
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }

        for (int i = start; i < start + count; i++) {
            if (map.containsKey(outText.get(i))) {
                map.put(outText.get(i), map.get(outText.get(i)) + 1);
            } else {
                map.put(outText.get(i), 1);
            }
        }
        try {
            FileWriter fw = new FileWriter(fileName + "-map.txt", false);
            fw.write(map.size() + " " + count + System.lineSeparator());
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                fw.write(entry.getKey() + " " + entry.getValue() + System.lineSeparator());
            }

            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
