package com.turik.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;

public class Folder {

    private final Folder parent;
    private final String name;
    private final List<Folder> subfolders = new ArrayList<>();
    private int fileSize;

    public Folder(Folder parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public Folder getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public List<Folder> getSubfolders() {
        return subfolders;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public void addSubFolder(String name) {
        this.subfolders.add(new Folder(this, name));
    }

    public Folder getSubfolderByName(String name) {
        for (Folder subfolder : this.subfolders) {
            if (subfolder.name.equals(name)) {
                return subfolder;
            }
        }

        throw new RuntimeException("no subfolder with name " + name);
    }

}
