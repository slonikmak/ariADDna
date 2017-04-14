package com.stnetix.ariaddna.desktopgui.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @autor slonikmak on 31.03.2017.
 */
@Repository
public class FilesRepository {
    /**
     * temporary file items repository
     * TODO: replace for getting file items from VUFS
     */
    private ObservableList<FileItem> allFiles = FXCollections.observableArrayList();
    private ObjectProperty<FileItem> currentRoot = new SimpleObjectProperty<>();
    //private FilteredList<FileItem> currentFiles = new FilteredList<>(allFiles, fileItem -> !currentRoot.isNull().get() && fileItem.getParent().equals(currentRoot.getValue()));
    private ObservableList<FileItem> currentFiles = FXCollections.observableArrayList();

    public FilesRepository(){
        fillFilesSet();

        currentRoot.addListener(new ChangeListener<FileItem>() {
            @Override
            public void changed(ObservableValue<? extends FileItem> observable, FileItem oldValue, FileItem newValue) {
                currentFiles.clear();
                currentFiles.addAll(allFiles.stream().filter(fileItem -> {
                    return fileItem.getRootPath().equals(newValue.getPath());
                }).collect(Collectors.toList()));
            }
        });

        currentRoot.setValue( allFiles.stream().filter(item->item.getPath().equals(Paths.get("/root"))).findFirst().get());
    }

    /**
     * temporary method for fill files set
     * files structure:  root/outerFolder/innerFolder/someFolder/file.tmp
     */
    public void fillFilesSet(){
        addFileItems("/root", "/root/Photos", "/root/Docs", "/root/videos",
                "/root/Docs/Office", "/root/Docs/Work", "/root/Docs/Work/file1.jpg","/root/Docs/Work/file2.jpg");
    }

    public void addFileItems(String ...items){
        Arrays.stream(items).forEach(item->allFiles.add(new FileItem(item)));
    }

    public FileItem getCurrentRoot() {
        return currentRoot.get();
    }

    public void setCurrentRoot(FileItem currentRoot) {
        this.currentRoot.set(currentRoot);
    }

    public void setCurrentRoot(Path path){
        setCurrentRoot(allFiles.filtered(fileItem -> fileItem.getPath().equals(path)).get(0));
    }

    public ObjectProperty<FileItem> currentRootProperty() {
        return currentRoot;
    }

    public ObservableList<FileItem> getCurrentFiles() {
        return currentFiles;
    }
}
