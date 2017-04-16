package com.stnetix.ariaddna.desktopgui.controllers;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;
import com.stnetix.ariaddna.desktopgui.models.FileItem;
import com.stnetix.ariaddna.desktopgui.models.FilesRepository;
import com.stnetix.ariaddna.desktopgui.views.FileItemView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for file browser
 *
 * @author slonikmak
 */
@Component
public class FileBrowserController implements IGuiController, Initializable {
    private ObservableList<FileItemView> list = FXCollections.observableArrayList();
    private FilesRepository repository;

    @FXML
    private StackPane container;

    GridView<FileBrowserElement> myGrid;


    /**
     * Native init method, run after FXML field injection
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myGrid = new GridView<>(repository.getCurrentFiles());
        myGrid.setCellFactory(gridView -> new GridCell<FileBrowserElement>() {
            @Override
            public void updateItem(FileBrowserElement item, boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //setText(item.getName());
                    setGraphic(new FileItemView("icon", item.getName()));
                }

            }
        });
        container.getChildren().add(myGrid);
        showContent();
    }


    /**
     * Method for generate file items for fileBrowser and add it into container(temporary realization)
     */
    private void showContent() {

        list.addAll(new FileItemView("icon", "Folder1"),
                new FileItemView("icon", "Documents"),
                new FileItemView("icon", "WorkFiles"),
                new FileItemView("icon", "Projects"));

    }

    @Autowired
    public void setRepository(FilesRepository repository) {
        this.repository = repository;
    }
}
