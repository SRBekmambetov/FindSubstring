package ru.tsc.srb.findsubstring;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {

    public static List<String> searchingResults = new ArrayList<>();

    public void writeFileFromList(String outputFile, long timeOfCompletion) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(outputFile)));

        for (String line: searchingResults) {
            bufferedWriter.write(line + "\n");
            bufferedWriter.flush();
        }

        bufferedWriter.write(Long.toString(timeOfCompletion));
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
