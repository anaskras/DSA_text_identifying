import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import java.io.File;
import java.util.ArrayList;

public class MyStemJava {

    private final static MyStem mystemAnalyzer = new Factory("--format json").newMyStem("3.0", Option.<File>empty()).get();

    public ArrayList<String> toStem(ArrayList<String> input) throws MyStemApplicationException {
        ArrayList<String> output = new ArrayList<String>();
        for (String s : input) {
            Iterable<Info> result = JavaConversions.asJavaIterable(mystemAnalyzer.analyze(Request.apply(s)).info().toIterable());
            for (Info info : result) {
                // System.out.println(info.initial() + " -> " + info.lex() + " | " + info.rawResponse());
                String[] temp = info.toString().split("\"");
                if (temp.length > 9 && !temp[9].equals("bastard")) {
                    output.add(temp[9]);
                }
            }
        }
        return output;
    }

    public ArrayList<String> toStem(String input) throws MyStemApplicationException {
        ArrayList<String> output = new ArrayList<String>();
        Iterable<Info> result = JavaConversions.asJavaIterable(mystemAnalyzer.analyze(Request.apply(input)).info().toIterable());
        for (Info info : result) {
            // System.out.println(info.initial() + " -> " + info.lex() + " | " + info.rawResponse());
            String[] temp = info.toString().split("\"");
            output.add(temp[9]);
        }
        return output;
    }
}