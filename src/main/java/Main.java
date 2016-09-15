import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.util.Scanner;

/**
 * Created by tokar on 14.09.2016.
 */
public class Main {
    public static void main(String[] args) {
        //Scanner sc = new Scanner(System.in);
        //MyStemJava stem = new MyStemJava();
        //String input = sc.nextLine();
        PreparingDocuments.makeDocs("test.txt", 1, 1, 10000);
        /*try {
            System.out.println(stem.toStem(input).toString());
        } catch (MyStemApplicationException e) {
            e.printStackTrace();
        }*/
    }
}
