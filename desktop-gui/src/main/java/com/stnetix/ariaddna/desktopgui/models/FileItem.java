package com.stnetix.ariaddna.desktopgui.models;

import javafx.scene.layout.Pane;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @autor slonikmak on 01.04.2017.
 */
public class FileItem implements FileBrowserElement{
    private Path path;
    private String name;
    private FileBrowserElement parent = null;
    private long id;
    private boolean isDirectory;

    FileItem(){

    }

    public FileItem(Path path){
        this.path = path;
    }

    public FileItem(String path){
        this.path = Paths.get(path);
        this.isDirectory = true;
    }

    public FileItem(String path, boolean isDirectory){
        this.path = Paths.get(path);
        this.isDirectory = isDirectory;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileBrowserElement getParent() {
        return parent;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    public void setParent(FileBrowserElement parent) {
        this.parent = parent;
    }

    public Path getParentPath(){
        return path.getParent();
    }
}
