package convertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShowData {
    public static String showStudentCSV(String sourceData) {
        String data = sourceData;
        BufferedReader bufferedReader = null;
        String line = " ";

        try {
            bufferedReader = new BufferedReader(new FileReader(data));
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                for (int i = 0; i < row.length; i++) {
                    String index = row[i];
                    if (row.length == 1) {
                        row[5] = row[4] + row[3];
                    } else
                        System.out.printf("%-25s", index);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

}

