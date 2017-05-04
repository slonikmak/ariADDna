package com.stnetix.ariaddna.desktopgui.controllers;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;
import com.stnetix.ariaddna.desktopgui.models.FilesRepositoryImpl;
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
    private FilesRepositoryImpl repository;

    @FXML
    private StackPane container;

    private GridView<FileBrowserElement> myGrid;


    /**
     * Native init method, run after FXML field injection
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (myGrid == null) {
            System.out.println("init");
            myGrid = new GridView<>(repository.getCurrentFiles());
            myGrid.setCellFactory(gridView -> new GridCell<FileBrowserElement>() {
                @Override
                public void updateItem(FileBrowserElement item, boolean empty) {
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setGraphic(new FileItemView(item.getName(), item));
                        setOnMouseClicked(event -> {
                            if (item.isDirectory()) repository.setCurrentParent(item);
                        });
                    }

                }
            });
        }



        container.getChildren().add(myGrid);
    }

    @Autowired
    public void setRepository(FilesRepositoryImpl repository) {
        this.repository = repository;
    }
}
