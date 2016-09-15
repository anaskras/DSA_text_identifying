package Library.WorkingWithFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    public static void fromStringBuffer (StringBuffer output, String namefile){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(namefile));
            out.write(output.toString());
            out.close();
        } catch (IOException ioe){
            ioe.getMessage();
        }
    }
}
