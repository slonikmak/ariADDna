package com.stnetix.ariaddna.desktopgui.views;

import com.stnetix.ariaddna.desktopgui.controllers.SettingsTemplateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CloudSettingsFactory {

    enum Clouds{
        DROPBOX("Dropbox", "Dropbox"), GOOGLE_DRIVE("GoogleDrive", "Google Drive");

        String name;
        String header;

        Clouds(String name, String header){
            this.name = name;
            this.header = header;
        }
    }

    public Node getNode(String value, FXMLLoaderProvider loaderProvider) throws IOException {
        FXMLLoader fxmlLoader = loaderProvider.get("/com/stentix/ariaddna/desktopgui/fxmlViews/settingsTemplate.fxml");
        Pane parent = fxmlLoader.load();

        Clouds elem = Clouds.valueOf(value);

        SettingsTemplateController controller = fxmlLoader.getController();
        controller.setHeaders(elem.header, elem.name);
        controller.setContent(loaderProvider.get("/com/stentix/ariaddna/desktopgui/fxmlViews/cloudSettingsView.fxml").load());
        return parent;
    }
}
