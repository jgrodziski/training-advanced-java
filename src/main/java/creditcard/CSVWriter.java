package creditcard;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class CSVWriter {

    public static void format(Writer writer, List<List<String>> rows) throws IOException{
        format(writer, rows, ",");
    }

    public static void format(Writer writer, List<List<String>> rows, String delim) throws IOException {
        for (List<String> row : rows) {
            Iterator<String> iter = row.iterator();
            while (iter.hasNext()){
                writer.write(iter.next());
                if (iter.hasNext()){
                    writer.write(delim);
                }
            }
            writer.write("\n");
        }
    }
}
