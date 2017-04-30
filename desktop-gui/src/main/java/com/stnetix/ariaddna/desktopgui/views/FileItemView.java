package com.stnetix.ariaddna.desktopgui.views;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;
import com.stnetix.ariaddna.desktopgui.models.FileItem;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Simple class for present VUFS item
 * @author slonikmak
 * TODO: replace with a real VUFS item class
 */
public class FileItemView extends VBox {
    private FontAwesomeIconView icon;
    private Label fileName;
    private String name;
    private FileBrowserElement fileBrowserElement;

    public FileItemView(String fileName, FileBrowserElement fileBrowserElement) {
        super();
        this.name = fileName;
        this.fileName = new Label(fileName);
        this.fileBrowserElement = fileBrowserElement;

        setIcon();

        this.getChildren().addAll(this.icon, this.fileName);
    }

    private void setIcon(){
        if (fileBrowserElement.isDirectory()){
            this.icon = new FontAwesomeIconView(FontAwesomeIcon.FOLDER_ALT);
        } else {
            this.icon = new FontAwesomeIconView(FontAwesomeIcon.IMAGE);
        }
        this.icon.setStyleClass("file-item-icon");

    }

    public String getName() {
        return name;
    }

    public FontAwesomeIconView getIcon() {
        return icon;
    }

}
