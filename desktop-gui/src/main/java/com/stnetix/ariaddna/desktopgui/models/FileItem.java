package com.stnetix.ariaddna.desktopgui.models;

import javafx.scene.layout.Pane;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @autor slonikmak on 01.04.2017.
 */
public class FileItem {
    private Path path;
    private String name;
    FileItem parent;

    FileItem(){

    }

    FileItem(Path path){
        this.path = path;
    }

    FileItem(String path){
        this.path = Paths.get(path);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return path.getFileName().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileItem getParent() {
        return parent;
    }

    public void setParent(FileItem parent) {
        this.parent = parent;
    }

    public Path getRootPath(){
        return path.getParent();
    }
}
