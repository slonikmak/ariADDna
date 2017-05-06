package com.stnetix.ariaddna.desktopgui.controllers;

import com.stnetix.ariaddna.desktopgui.models.FilesRepository;
import com.stnetix.ariaddna.desktopgui.views.FXMLLoaderProvider;
import com.stnetix.ariaddna.desktopgui.views.SettingsViewFactory;
import com.stnetix.ariaddna.desktopgui.views.TreeViewFactory;
import com.stnetix.ariaddna.desktopgui.views.ViewsFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for file browser left pane
 *
 * @author slonikmak
 */
@Component
public class FileBrowserLeftPaneController implements IGuiController, Initializable {
    private MainController mainController;
    private FXMLLoaderProvider provider;
    private TreeViewFactory treeViewFactory;
    private FilesRepository repository;

    @FXML
    private AnchorPane treeViewContainer;

    /**
     * Load settings pane into left and center pane
     * @throws IOException
     */
    @FXML
    void showSettings() throws IOException {
        mainController.setLeftBorderContent(ViewsFactory.LEFT_SETTINGS.getNode(provider));
        mainController.setCenterBorderContent(SettingsViewFactory.ACCOUNT.getNode(provider));
    }

    @FXML
    void createFolder(ActionEvent event) throws IOException {
        /*Stage stage = new Stage();
        Dialog<String> dialog = new Dialog<>();
        FXMLLoader loader = provider.get("/com/stentix/ariaddna/desktopgui/fxmlViews/createFolder.fxml");
        dialog.getDialogPane().setContent(loader.load());
        dialog.showAndWait();*/
        /*Scene scene = new Scene(loader.load(), 300, 150);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();*/
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create new folder");
        dialog.setHeaderText("Create new folder");
        dialog.setContentText("Please enter folder name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> repository.addNewFile(name, true));

    }

    /**
     * inject MainController
     * @param mainController
     */
    @Autowired
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * inject FXMLLoaderProvider
     * @param provider
     */
    @Autowired
    public void setProvider(FXMLLoaderProvider provider) {
        this.provider = provider;
    }

    /**
     * inject TreeViewFactory
     * @param treeViewFactory
     */
    @Autowired
    public void setTreeViewFactory(TreeViewFactory treeViewFactory) {
        this.treeViewFactory = treeViewFactory;
    }

    @Autowired
    public void setRepository(FilesRepository repository) {
        this.repository = repository;
    }

    /**
     * Native init method.
     * Add tree view VUFS items into left pane
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         * TODO: replace to getting elements from FilesRepository
         */
        treeViewContainer.getChildren().add(treeViewFactory.getFileBrowserTreeView());

    }
}
