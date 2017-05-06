package com.stnetix.ariaddna.desktopgui.models;

import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anton on 03.05.2017.
 */
public interface FilesRepository {
    void addFileItems(String... items);

    void addFileItem(FileBrowserElement file);

    FileBrowserElement addNewFile(String folderName, boolean isDirectory);

    FileBrowserElement getCurrentParent();

    void setCurrentParent(FileBrowserElement parent);

    List<FileBrowserElement> getChildren(FileBrowserElement parent);

    ObservableList<FileBrowserElement> getCurrentFiles();

    Optional<FileBrowserElement> getFileByPath(Path path);

    void setCurrentRoot(Path path);
}
