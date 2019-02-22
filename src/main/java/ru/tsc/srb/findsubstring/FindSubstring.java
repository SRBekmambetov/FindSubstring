package ru.tsc.srb.findsubstring;

import java.io.*;

public class FindSubstring implements Runnable {

    private File file;
    private String substring;

    public FindSubstring(File file, String substring) {
        this.file = file;
        this.substring = substring;
    }

    public void readAndSearchSubstringInFile(File file, String substring) throws IOException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        while ((line = bufferedReader.readLine()) != null) {
            if (line.toLowerCase().contains(substring.toLowerCase())) {
                synchronized (FindSubstring.class) {
                    WriteFile.searchingResults.add(Thread.currentThread().getName().substring(7) + " - " + file.getParentFile() + " - " + file.getName() + " : " + line);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            readAndSearchSubstringInFile(file, substring);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Файл не доступен для чтения");
        }
    }
}
