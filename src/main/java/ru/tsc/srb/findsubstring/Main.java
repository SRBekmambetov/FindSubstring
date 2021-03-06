package ru.tsc.srb.findsubstring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static List<String> fileExtensions;

    public static BufferedWriter bufferedWriter;

    private static ForkJoinPool forkJoinPool;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Main main = new Main();

        if (args.length < 5) {
            System.out.println("Некорретно введены данные");
            return;
        }

        int numberOfThreads = 0;

        try {
            numberOfThreads = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Количество потоков введено не верно");
            return;
        }

        if (numberOfThreads < 1) {
            System.out.println("Введено неположительное число потоков");
            return;
        }

        String searchedSubstring = args[1];
        String path = args[2];
        String outputFile = args[3];

        fileExtensions = new ArrayList<>();

        for (int i = 4; i < args.length; i++) {
            fileExtensions.add(args[i]);
        }

        forkJoinPool = new ForkJoinPool(numberOfThreads);

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(outputFile)));
        } catch (IOException e) {
            System.out.println("Не доступна папка для записи");
            return;
        }

        Folder folder = Folder.fromDirectory(new File(path));

        String result = main.foundSubstring(folder, searchedSubstring);

        System.out.println(result);

        long stopTime = System.currentTimeMillis();

        try {
            bufferedWriter.append(Long.toString(stopTime - startTime));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Файл не доступен для записи");
            return;
        }

        System.out.println(stopTime - startTime);
    }

    public String foundSubstring(Folder folder, String searchedSubstring) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedSubstring));
    }
}
