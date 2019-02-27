package ru.tsc.srb.findsubstring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Folder {

    private List<Folder> subFolder;
    private List<Document> documents;

    public Folder(List<Folder> subFolder, List<Document> documents) {
        this.subFolder = subFolder;
        this.documents = documents;
    }

    public List<Folder> getSubFolder() {
        return subFolder;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public static Folder fromDirectory(File dir) {
        List<Document> documents = new ArrayList<>();
        List<Folder> subFolders = new ArrayList<>();
        for (File entry: dir.listFiles()) {
            if (entry.isDirectory()) {
                subFolders.add(Folder.fromDirectory(entry));
            } else {
                Document documentFromFile = null;
                try {
                    documentFromFile = Document.fromFile(entry);
                } catch (IOException e) {
                    System.out.println("Файл не досутпен для чтения");
                    continue;
                }
                if (documentFromFile != null) {
                    documents.add(documentFromFile);
                }
            }
        }
        return new Folder(subFolders, documents);
    }
}
