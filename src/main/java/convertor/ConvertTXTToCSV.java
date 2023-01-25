package convertor;

import java.io.*;
import java.util.Scanner;

public class ConvertTXTToCSV {                      // convert student Data (file.txt) to (file.csv)
    public static void csvConvertor(File filePath) {
        filePath = fPath;
    }


    public static File fPath = new File("src//main//resources//StudentData.txt");

    {
        try {
            Scanner scanner = new Scanner(fPath);
            while (scanner.hasNext()) {
                // replace wrong data to make new file
                // replace # with "," to edit data in file.txt
                String studentData = scanner.nextLine().replace("#", ",");
                // replace $ with new line to edit data in file.txt
                studentData = studentData.replace("$", "\n");
                // split data to rows
                String[] newStudentData =studentData.split("\n");

                // saving new student Data and parsing it into file.csv
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("outPutResources//StudentData.csv")));
                for (int i = 0; i < newStudentData.length; i++) {
                        out.println(newStudentData[i]);
                        }
                        out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}