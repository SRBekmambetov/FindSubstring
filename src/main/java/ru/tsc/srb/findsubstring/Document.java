package ru.tsc.srb.findsubstring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Document {

    private List<String> lines;
    private File path;

    public Document(List<String> lines, File path) {
        this.lines = lines;
        this.path = path;
    }

    public List<String> getLines() {
        return lines;
    }

    public File getPath() {
        return path;
    }

    public static Document fromFile(File file) throws IOException {
        if (Main.fileExtensions.contains(file.getName().substring(file.getName().lastIndexOf(".") + 1))) {
            List<String> lines = new ArrayList<>();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return new Document(lines, file);
        }
        return null;
    }
}
