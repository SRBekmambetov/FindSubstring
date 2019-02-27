package ru.tsc.srb.findsubstring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSearchTask extends RecursiveTask<String> {

    private Folder folder;
    private String searchedSubstring;

    public FolderSearchTask(Folder folder, String searchedSubstring) {
        this.folder = folder;
        this.searchedSubstring = searchedSubstring;
    }

    @Override
    protected String compute() {
        String nameFile = "";
        List<RecursiveTask<String>> forks = new ArrayList<>();
        for (Folder subFolder: folder.getSubFolder()) {
            FolderSearchTask task = new FolderSearchTask(subFolder, searchedSubstring);
            forks.add(task);
            task.fork();
        }
        for (Document document: folder.getDocuments()) {
            DocumentSearchTask task = new DocumentSearchTask(document, searchedSubstring);
            forks.add(task);
            task.fork();
        }
        for (RecursiveTask<String> task: forks) {
            nameFile = task.join();
            if (task instanceof DocumentSearchTask) {
                System.out.println("Обработан файл: " + nameFile);
            }
        }
        return "Все файлы были обработаны";
    }
}
