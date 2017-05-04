package com.stnetix.ariaddna.desktopgui.models;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anton on 03.05.2017.
 */
public interface FilesRepository {
    void addFileItems(String... items);

    FileBrowserElement getCurrentParent();

    void setCurrentParent(FileBrowserElement parent);

    List<FileBrowserElement> getChildren(FileBrowserElement parent);

    List<FileBrowserElement> getCurrentFiles();

    Optional<FileBrowserElement> getFileByPath(Path path);

    void setCurrentRoot(Path path);
}
