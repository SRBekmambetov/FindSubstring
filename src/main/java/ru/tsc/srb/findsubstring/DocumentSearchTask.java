package ru.tsc.srb.findsubstring;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<String> {

    private Document document;
    private String searchedSubstring;

    public DocumentSearchTask(Document document, String searchedSubstring) {
        this.document = document;
        this.searchedSubstring = searchedSubstring;
    }

    @Override
    protected String compute() {
        return foundSubstring();
    }

    private String foundSubstring() {
        for (String line: document.getLines()) {
            if (line.contains(searchedSubstring)) {
                try {
                    Main.bufferedWriter.append(Thread.currentThread().getName() + " " + document.getPath() + " : " + line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return document.getPath().getAbsolutePath();
    }
}
