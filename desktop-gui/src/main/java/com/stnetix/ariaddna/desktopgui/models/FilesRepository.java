package com.stnetix.ariaddna.desktopgui.models;

import com.stnetix.ariaddna.commonutils.logger.AriaddnaLogger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @autor slonikmak on 31.03.2017.
 */
@Repository
public class FilesRepository {
    private static final AriaddnaLogger LOGGER = AriaddnaLogger.getLogger(FilesRepository.class);
    /**
     * temporary file items repository
     * TODO: replace for getting file items from VUFS
     */
    private ObservableList<FileBrowserElement> allFiles = FXCollections.observableArrayList();
    private ObjectProperty<FileBrowserElement> currentParent = new SimpleObjectProperty<>();
    private ObservableList<FileBrowserElement> currentFiles = FXCollections.observableArrayList();

    public FilesRepository(){
        fillFilesSet();

        currentParent.addListener(new ChangeListener<FileBrowserElement>() {

            @Override
            public void changed(ObservableValue<? extends FileBrowserElement> observable, FileBrowserElement oldValue, FileBrowserElement newValue) {
                currentFiles.clear();
                currentFiles.addAll(allFiles.stream().filter(fileItem -> {
                    return fileItem.getParentPath().equals(newValue.getPath());
                }).collect(Collectors.toList()));
            }
        });

        currentParent.setValue( allFiles.stream().filter(item->item.getPath().equals(Paths.get("/root"))).findFirst().get());
        System.out.println(currentParent);
    }

    /**
     * temporary method for fill files set
     * files structure:  /root/outerFolder/innerFolder/someFolder/file.tmp
     */
    public void fillFilesSet(){
        addFileItems("/root", "/root/Photos", "/root/Docs", "/root/videos",
                "/root/Docs/Office", "/root/Docs/Work", "/root/Docs/Work/file1.jpg","/root/Docs/Work/file2.jpg");
    }


    public void addFileItems(String ...items){
        Arrays.stream(items).forEach(item->{
            if (item.split("\\.").length>1){
                allFiles.add(new FileItem(item, false));
            } else allFiles.add(new FileItem(item));

        });
    }

    public FileBrowserElement getCurrentParent() {
        return currentParent.get();
    }

    public void setCurrentParent(FileBrowserElement currentParent) {
        this.currentParent.set(currentParent);
    }

    public void setCurrentRoot(Path path){
        setCurrentParent(allFiles.filtered(fileItem -> fileItem.getPath().equals(path)).get(0));
    }

    public List<FileBrowserElement> getChildren(FileBrowserElement parent){
        return allFiles.stream().filter(element -> !element.equals(parent)&&element.getParentPath().equals(parent.getPath())).collect(Collectors.toList());
    }

    public ObjectProperty<FileBrowserElement> currentParentProperty() {
        return currentParent;
    }

    public ObservableList<FileBrowserElement> getCurrentFiles() {
        return currentFiles;
    }

    public Optional<FileBrowserElement> getFileByPath(Path path){
        return allFiles.stream().filter(element -> element.getPath().equals(path)).findFirst();
    }
}
