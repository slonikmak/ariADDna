package com.stnetix.ariaddna.desktopgui.models;

import java.nio.file.Path;

/**
 * @autor slonikmak on 16.04.2017.
 */
public interface FileBrowserElement {
    String getName();
    long getId();
    Path getRootPath();
    Path getPath();
}
