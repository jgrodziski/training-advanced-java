import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 */
public class CSVReader {

    /**
     * lit le fichier CSV et retourne une liste de lignes qui contient une liste de valeur (String)
     *
     * @param reader
     * @param delim     caractere de séparation. Par défaut ;
     * @return liste de list de String
     * @throws IOException
     */
    public static List<List<String>> parse(Reader reader, String delim) throws IOException {
        if (delim == null || delim.equals("")) {
            parseCSV(reader);
        }
        List rows = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            String line = null;
            String value = null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> row = new ArrayList<String>(3);
                StringTokenizer st = new StringTokenizer(line, delim);
                while (st.hasMoreElements()) {
                    value = (String) st.nextElement();
                    row.add(value);
                }
                rows.add(row);
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (reader != null){
                reader.close();
            }
        }
        return rows;
    }

    public static List<List <String>> parseCSV(Reader reader) throws IOException {
        return parse(reader, ";");
    }


}
