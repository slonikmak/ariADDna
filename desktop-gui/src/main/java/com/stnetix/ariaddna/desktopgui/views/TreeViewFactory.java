package com.stnetix.ariaddna.desktopgui.views;

import com.stnetix.ariaddna.desktopgui.models.FileBrowserElement;
import com.stnetix.ariaddna.desktopgui.models.FilesRepository;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.controlsfx.control.BreadCrumbBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for create TreeView of file items
 * TODO: work with VUFS item
 *
 * @author slonikmak
 */
@Component
public class TreeViewFactory {

    private FilesRepository repository;

    //Current treeView element property
    private ObjectProperty<TreeView<FileBrowserElement>> currentTree = new SimpleObjectProperty<>();

    //represent Bread Crumb Bar navigation elem
    private BreadCrumbBar<FileBrowserElement> breadCrumbBar;

    /**
     * constructor, init breadCrumbBar and bind it to currentTree
     * when changed selected tree item the selected crumb bar element will changed to
     */
    public TreeViewFactory() {
        breadCrumbBar = new BreadCrumbBar<>();

        breadCrumbBar.setCrumbFactory(treeItem -> {
            if (treeItem.getParent() == treeItem) System.out.println("root");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_RIGHT);
            icon.setGlyphSize(20);
            Button btn = new Button(treeItem.getValue().getName());
            btn.setGraphic(icon);
            return btn;
        });

        currentTree.addListener((observable, oldValue, newValue) -> {
            setTreeViewSelectedListener();
            TreeItem<FileBrowserElement> firstElem = newValue.getTreeItem(0);
            setSelectedCrumbElem(firstElem);
        });


    }

    /**
     * bind selected tree item and selected bread crumb bar elements
     */
    private void setTreeViewSelectedListener() {
        currentTree.getValue().getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<FileBrowserElement>>) c -> {
            c.next();
            TreeItem<FileBrowserElement> selected = c.getList().get(0);
            setSelectedCrumbElem(selected);
        });

        breadCrumbBar.selectedCrumbProperty().addListener((observable, oldValue, newValue) -> {
            currentTree.getValue().getSelectionModel().select(newValue);

        });
    }

    private void loadChildren(TreeItem<FileBrowserElement> treeItem){
                FileBrowserElement element = treeItem.getValue();
        repository.setCurrentRoot(element);
        repository.getCurrentFiles().forEach(element1 -> {
            makeBranch(element, treeItem);
        });
    }

    /**
     * temporary method
     *
     * @return
     */
    public TreeView get() {
        TreeView<String> tree = new TreeView<>();
        tree.setShowRoot(false);
        TreeItem<String> root = new TreeItem<>("");
        tree.setRoot(root);

        ChangeListener<Boolean> expandedListener = (obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                System.out.println("expand");
                ReadOnlyProperty<?> expandedProperty = (ReadOnlyProperty<?>) obs;
                Object itemThatWasJustExpanded = expandedProperty.getBean();
                for (TreeItem<String> item : tree.getRoot().getChildren()) {
                    if (item != itemThatWasJustExpanded) {
                        item.setExpanded(false);
                    }
                }
            }
        };

        for (int i = 1; i <= 4; i++) {
            TreeItem<String> item = new TreeItem<>("Top level " + i);
            item.expandedProperty().addListener(expandedListener);
            root.getChildren().add(item);
            for (int j = 1; j <= 4; j++) {
                TreeItem<String> subItem = new TreeItem<>("Sub item " + i + ":" + j);
                item.getChildren().add(subItem);
            }
        }

        PseudoClass subElementPseudoClass = PseudoClass.getPseudoClass("sub-tree-item");

        tree.setCellFactory((TreeView<String> tv) -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisclosureNode(null);

                    if (empty) {
                        setText("");
                        setGraphic(null);
                    } else {
                        setText(item); // appropriate text for item
                    }
                }

            };
            cell.treeItemProperty().addListener((obs, oldTreeItem, newTreeItem) -> {
                cell.pseudoClassStateChanged(subElementPseudoClass,
                        newTreeItem != null && newTreeItem.getParent() != cell.getTreeView().getRoot());
            });
            return cell;
        });

        return tree;
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
     * Generate treeView of file items
     * TODO: work with VUFS items
     *
     * @return tree view
     */
    public TreeView<FileBrowserElement> getFileBrowserTreeView() {
        TreeView<FileBrowserElement> tree = new TreeView<>();
        TreeItem<FileBrowserElement> root = new TreeItem<>(repository.getCurrentRoot());

        repository.getCurrentFiles().forEach(fileItem -> {
            makeBranch(fileItem, root);
        });

       /* TreeItem<FileBrowserElement> outer1, outer2, inner1, inner2;
        outer1 = makeBranch(new SimpleTreeElement("Folder1", 1), root);
        makeBranch(new SimpleTreeElement("Documents", 2), root);
        outer2 = makeBranch(new SimpleTreeElement("MyFotos", 3), outer1);
        makeBranch(new SimpleTreeElement("NewFolder", 7), outer2);
        makeBranch(new SimpleTreeElement("OtherFiles", 4), outer1);
        makeBranch(new SimpleTreeElement("WorkFiles", 5), root);
        makeBranch(new SimpleTreeElement("Projects", 6), root);*/

        tree.setRoot(root);
        tree.setPrefWidth(200);

        setTreeCellFactory(tree);
        tree.setShowRoot(false);
        currentTree.setValue(tree);
        return tree;
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
     * TODO: work with Repository
     *
     * @return tree view
     */
    public TreeView<FileBrowserElement> getSettingsTreeView() {
        TreeView<FileBrowserElement> tree = new TreeView<>();
        TreeItem<FileBrowserElement> root = new TreeItem<>(new SimpleTreeElement("Settings", 0));

        TreeItem<FileBrowserElement> outer1, outer2, inner1, inner2;
        outer1 = makeBranch(new SimpleTreeElement("Account", 1), root);
        outer2 = makeBranch(new SimpleTreeElement("Clouds", 2), root);
        makeBranch(new SimpleTreeElement("Dropbox", 3), outer2);
        makeBranch(new SimpleTreeElement("Google Drive", 4), outer2);
        makeBranch(new SimpleTreeElement("Sync", 5), root);
        makeBranch(new SimpleTreeElement("Encription", 6), root);

        tree.setRoot(root);
        tree.setPrefWidth(200);

        setTreeCellFactory(tree);


        tree.setShowRoot(false);
        currentTree.setValue(tree);
        return tree;
    }

    /**
     * Customizing treeView
     *
     * @param tree treeView
     */
    private void setTreeCellFactory(TreeView<FileBrowserElement> tree) {
        tree.setCellFactory(param -> new TreeCell<FileBrowserElement>() {
            @Override
            public void updateItem(FileBrowserElement item, boolean empty) {
                super.updateItem(item, empty);
                //setDisclosureNode(null);

                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }

        });

        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue.getValue());
            }
        });
    }

    /**
     * @return bread crumb bar
     */
    public BreadCrumbBar<FileBrowserElement> getBreadCrumbBar() {
        return breadCrumbBar;
    }

    @Autowired
    public void setRepository(FilesRepository repository) {
        this.repository = repository;
    }
}
