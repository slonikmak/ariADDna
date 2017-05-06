package com.stnetix.ariaddna.desktopgui.views;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;
import com.stnetix.ariaddna.desktopgui.models.FileItem;
import com.stnetix.ariaddna.desktopgui.models.FilesRepository;
import com.stnetix.ariaddna.desktopgui.models.FilesRepositoryImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.controlsfx.control.BreadCrumbBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Factory for create TreeView of file and settings items and bread crumb bar
 *
 * @author slonikmak
 */
@Component
public class TreeViewFactory {

    private FilesRepository repository;

    //Current treeView element property
    private ObjectProperty<TreeView<FileBrowserElement>> currentTreeView = new SimpleObjectProperty<>();

    //represent Bread Crumb Bar navigation elem
    private BreadCrumbBar<FileBrowserElement> breadCrumbBar;

    private TreeView<FileBrowserElement> fileBrowserTreeView;
    private TreeView<FileBrowserElement> settingsTreeView;

    private TreeItem<FileBrowserElement> selectedTreeItem;

    /**
     * constructor, init breadCrumbBar and bind it to currentTreeView
     * when changed selected tree item the selected crumb bar element will changed to
     */
    public TreeViewFactory() {
        breadCrumbBar = new BreadCrumbBar<>();

        breadCrumbBar.setCrumbFactory(treeItem -> {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_RIGHT);
            icon.setGlyphSize(20);
            String name = treeItem.getValue().getName().equals("root")?"Files":treeItem.getValue().getName();
            Button btn = new Button(name);
            btn.setGraphic(icon);
            return btn;
        });

        currentTreeView.addListener((observable, oldValue, newValue) -> {
            TreeItem<FileBrowserElement> firstElem = newValue.getTreeItem(0);
            setSelectedCrumbElem(firstElem);
        });

        breadCrumbBar.selectedCrumbProperty().addListener((observable, oldValue, newValue) ->
                currentTreeView.getValue().getSelectionModel().select(newValue));


    }

    @PostConstruct
    public void setupTreeView() {
        repository.getCurrentFiles().addListener((ListChangeListener<FileBrowserElement>) c -> {
            loadChildren(getSelectedTreeItem());
        });
    }

    /**
     * setup settings TreeView
     */
    private void setupSettingsTreeView() {
        settingsTreeView = new TreeView<>();
        TreeItem<FileBrowserElement> root = new TreeItem<>(new FileItem("Settings"));

        TreeItem<FileBrowserElement> outer;
        makeBranch(new FileItem("Account"), root);
        outer = makeBranch(new FileItem("Clouds"), root);
        makeBranch(new FileItem("Dropbox"), outer);
        makeBranch(new FileItem("Google Drive"), outer);
        makeBranch(new FileItem("Sync"), root);
        makeBranch(new FileItem("Encription"), root);

        settingsTreeView.setRoot(root);
        settingsTreeView.setPrefWidth(200);

        setTreeCellFactory(settingsTreeView);

        settingsTreeView.getSelectionModel().getSelectedItems().addListener(settingsTreeViewSelectedListener);
        settingsTreeView.setShowRoot(false);
    }

    /**
     * setup file browser TreeView
     */
    private void setupBrowserTreeView() {
        fileBrowserTreeView = new TreeView<>();
        TreeItem<FileBrowserElement> root = new TreeItem<>(repository.getCurrentParent());

        repository.getCurrentFiles().forEach(fileItem -> makeBranch(fileItem, root));

        fileBrowserTreeView.setRoot(root);
        fileBrowserTreeView.setPrefWidth(200);
        setTreeCellFactory(fileBrowserTreeView);

        root.setExpanded(true);
        fileBrowserTreeView.getSelectionModel().getSelectedItems().addListener(browserTreeViewSelectedListener);
    }

    /**
     * file browser TreeView selected listener
     */
    private ListChangeListener<TreeItem<FileBrowserElement>> browserTreeViewSelectedListener = c -> {
        c.next();
        TreeItem<FileBrowserElement> selected = c.getList().get(0);
        selectedTreeItem = selected;
        repository.setCurrentParent(selected.getValue());
        setSelectedCrumbElem(selected);
        loadChildren(selected);
    };

    /**
     * settings TreeView selected listener
     */
    private ListChangeListener<TreeItem<FileBrowserElement>> settingsTreeViewSelectedListener = c -> {
        c.next();
        TreeItem<FileBrowserElement> selected = c.getList().get(0);
        setSelectedCrumbElem(selected);
    };

    private void loadChildren(TreeItem<FileBrowserElement> treeItem) {
        treeItem.getChildren().clear();
        repository.getCurrentFiles().forEach(child -> {
            if (child.isDirectory()) makeBranch(child, treeItem);
        });
    }



    /**
     * Make branch of element in root element
     *
     * @param element inner element
     * @param root    root element
     * @return new branch
     */
    private TreeItem<FileBrowserElement> makeBranch(FileBrowserElement element, TreeItem<FileBrowserElement> root) {
        TreeItem<FileBrowserElement> newBranch = new TreeItem<>(element);
        root.getChildren().add(newBranch);
        return newBranch;
    }

    /**
     * return treeView of file items
     *
     * @return tree view
     */
    public TreeView<FileBrowserElement> getFileBrowserTreeView() {
        if (fileBrowserTreeView == null) {
            setupBrowserTreeView();
        }
        currentTreeView.setValue(fileBrowserTreeView);
        return fileBrowserTreeView;
    }

    /**
     * select element in bread crumb bar
     *
     * @param elem selected item
     */
    private void setSelectedCrumbElem(TreeItem<FileBrowserElement> elem) {
        breadCrumbBar.selectedCrumbProperty().set(elem);
    }

    /**
     * Generate treeView of settings items
     *
     * @return tree view
     */
    public TreeView<FileBrowserElement> getSettingsTreeView() {
        if (settingsTreeView == null) {
            setupSettingsTreeView();
        }
        currentTreeView.setValue(settingsTreeView);
        return settingsTreeView;
    }

    /**
     * Customizing treeView
     *
     * @param tree treeView
     */
    private void setTreeCellFactory(TreeView<FileBrowserElement> tree) {
        PseudoClass firstElementPseudoClass = PseudoClass.getPseudoClass("first-tree-item");

        tree.setCellFactory(param -> new TreeCell<FileBrowserElement>() {
            @Override
            public void updateItem(FileBrowserElement item, boolean empty) {
                super.updateItem(item, empty);
                //setDisclosureNode(null);

                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    String name = item.getName();
                    if (name.equals("root")){
                        name = "Files";
                        pseudoClassStateChanged(firstElementPseudoClass, true);
                        setDisclosureNode(null);

                    }
                    setText(name);

                }
            }

        });

        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            }
        });
    }

    /**
     * @return bread crumb bar
     */
    public BreadCrumbBar<FileBrowserElement> getBreadCrumbBar() {
        return breadCrumbBar;
    }

    private TreeItem<FileBrowserElement> getSelectedTreeItem() {
        return selectedTreeItem;
    }

    @Autowired
    public void setRepository(FilesRepositoryImpl repository) {
        this.repository = repository;
    }
}
