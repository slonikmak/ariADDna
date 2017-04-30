package com.stnetix.ariaddna.desktopgui.views;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;

import java.nio.file.Path;

/**
 * Simple model for create a files tree view
 * TODO: replace with VUFS item
 * @author slonikmak
 */
public class SimpleTreeElement implements FileBrowserElement{
    private String name;
    private long id;

    public SimpleTreeElement(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public Path getParentPath() {
        return null;
    }

    @Override
    public Path getPath() {
        return null;
    }

    @Override
    public FileBrowserElement getParent() {
        return null;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String toString() {
        return "SimpleTreeElement{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
