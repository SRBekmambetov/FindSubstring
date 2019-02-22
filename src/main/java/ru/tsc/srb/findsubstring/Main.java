package ru.tsc.srb.findsubstring;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        if (args.length < 5) {
            System.out.println("Некорретно введены данные");
            return;
        }

        int numberOfThreads = Integer.parseInt(args[0]);
        String substring = args[1];
        String dirName = args[2];
        String outputFile = args[3];
        String[] fileExtensions = new String[args.length - 4];
        for (int i = 0; i < fileExtensions.length; i++) {
            fileExtensions[i] = args[i + 4];
        }
        List<File> files = (List<File>) FileUtils.listFiles(new File(dirName), fileExtensions, true);

        if (files.isEmpty()) {
            System.out.println("Нет файлов с введенными расширениями");
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        long start = System.currentTimeMillis();

        for (File file: files) {
            executorService.submit(new FindSubstring(file, substring));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long timeOfCompletion = System.currentTimeMillis() - start;

        WriteFile writeFile = new WriteFile();

        try {
            writeFile.writeFileFromList(outputFile, timeOfCompletion);
        } catch (IOException e) {
            System.out.println("Папка не доступна для записи");
        }
    }
}
