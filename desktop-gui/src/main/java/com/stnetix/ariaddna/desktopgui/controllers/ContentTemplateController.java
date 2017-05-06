package com.stnetix.ariaddna.desktopgui.controllers;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;
import com.stnetix.ariaddna.desktopgui.views.TreeViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for settings template view
 *
 * @author slonikmak
 */
@Component
public class ContentTemplateController implements IGuiController, Initializable {
    private TreeViewFactory treeViewFactory;

    @FXML
    private Label historyPath;

    @FXML
    private Label header;

    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane breadCrumbContainer;

    private BreadCrumbBar<FileBrowserElement> breadCrumb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        breadCrumb = treeViewFactory.getBreadCrumbBar();
        breadCrumbContainer.getChildren().clear();
        breadCrumbContainer.getChildren().add(breadCrumb);
    }

    /**
     * Set header and path string to settings template
     * @param headerStr
     * @param historyPathStr
     */
    public void setHeaders(String headerStr, String historyPathStr){
        header.setText(headerStr);
        historyPath.setText(historyPathStr);
    }

    /**
     * Add content(some settings view) to #content container into settings template
     * @param addedContent
     */
    public void setContent(Pane addedContent){
        content.getChildren().clear();
        content.getChildren().addAll(addedContent);
    }

    public AnchorPane getBreadCrumbContainer() {
        return breadCrumbContainer;
    }

    @Autowired
    public void setTreeViewFactory(TreeViewFactory factory) {
        this.treeViewFactory = factory;
    }
}
