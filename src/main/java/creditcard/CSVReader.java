package creditcard;

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
            while ((line = bufferedReader.readLine()) != null) {
                rows.add(tokenize(line,delim));
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

    public static List<String> tokenize(String line, String delim){
        List<String> row = new ArrayList<String>(3);
        StringTokenizer st = new StringTokenizer(line, delim);
        String value = null;
        while (st.hasMoreElements()) {
            value = (String) st.nextElement();
            row.add(value);
        }
        return row;
    }


    public static List<List <String>> parseCSV(Reader reader) throws IOException {
        return parse(reader, ";");
    }


}
