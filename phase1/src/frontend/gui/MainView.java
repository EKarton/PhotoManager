package frontend.gui;

import java.io.File;
import backend.models.Picture;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * The main view
 */
public class MainView extends Application {
  /** The width of the view */
  public static final int WIDTH = 1404;

  /** The width of the height */
  public static final int HEIGHT = 780;

  /** Controller for all action event handling in the menu bar */
  private MenuBarController menuBarController;

  /** Controller for list view */
  private FileListViewController listViewController;

  /** Backend service being used by this application */
  private BackendService backendService;

  /** The main stage. This is used by JavaFX */
  private Stage mainStage;

  /** The picture viewer used to display the images and controls */
  private PictureViewer pictureViewer;

  /** The list view used to display files */
  private ListView<Picture> listView;

  /**
   * Launches the main view
   * 
   * @param args
   * @return
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Constucts the main view.
   * 
   * Note: this constructor will be called by launch(args) but does not need to be called from any
   * where else
   */
  public MainView() {
    this.backendService = new BackendService(this);
    this.menuBarController = new MenuBarController(this, backendService);
    this.listViewController = new FileListViewController(this, backendService);
  }

  @Override
  public void start(Stage mainStage) throws Exception {

    this.mainStage = mainStage;

    mainStage.setTitle("Picture Manager"); // the primary stage provides the window

    BorderPane root = new BorderPane(); // Using a border pane layout

    root.setTop(createMenuBar()); // add the menu bar on top

    ListView<Picture> listView = createFileListView();

    // create an horizontal box to hold the list view and picture viewer
    HBox hBox = new HBox();
    hBox.setMinWidth(MainView.WIDTH);
    hBox.getChildren().add(listView); // add the list view

    this.pictureViewer = new PictureViewer(this);
    hBox.getChildren().add(pictureViewer); // add the picture viewer

    root.setCenter(hBox); // add the hbox to the border pane layout

    // container for all content in a scene graph
    Scene scene = new Scene(root, MainView.WIDTH, MainView.HEIGHT);

    mainStage.setScene(scene);
    mainStage.setResizable(false);
    mainStage.show();
  }

  /**
   * Creates the menu bar for the main view
   * 
   * @return the menu bar
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar(); // the menu bar

    // create menu items
    Menu open = new Menu("Open");

    Menu openDir = new Menu("Open Directory");

    MenuItem openDirNonRec = new MenuItem("Open Directory");
    MenuItem openDirRec = new MenuItem("Open Directory Recursively");

    // set handlers
    openDirNonRec.setOnAction(this.menuBarController::openDirectory);
    openDirRec.setOnAction(this.menuBarController::openDirectoryRecursively);

    // add items to openDir
    openDir.getItems().addAll(openDirNonRec, openDirRec);

    MenuItem openLog = new MenuItem("Open Log");
    openLog.setOnAction(this.menuBarController::openLog);
    open.getItems().addAll(openDir, openLog);

    Menu save = new Menu("Save");
    MenuItem saveItem = new MenuItem("Save");

    saveItem.setOnAction(this.menuBarController::save);

    // add a key combination for save
    saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

    save.getItems().add(saveItem);

    Menu undo = new Menu("Undo");
    MenuItem undoItem = new MenuItem("Undo");
    undoItem.setOnAction(this.menuBarController::undo);

    // add a key combination for undo
    undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

    undo.getItems().add(undoItem);

    Menu redo = new Menu("Redo");
    MenuItem redoItem = new MenuItem("Redo");

    redoItem.setOnAction(this.menuBarController::redo);

    // add a key combination for undos
    redoItem.setAccelerator(
        new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

    redo.getItems().add(redoItem);

    menuBar.getMenus().addAll(open, save, undo, redo);

    return menuBar;
  }

  /**
   * Open the directory chooser
   * 
   * @return the selected directory
   */
  public File openDirectoryChooser() {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File file = dirChooser.showDialog(this.mainStage);
    return file;
  }

  /**
   * Create the list view
   * 
   * @return the list view
   */
  public ListView<Picture> createFileListView() {
    listView = new ListView<Picture>(this.listViewController.getItems());
    this.listViewController.setListView(listView);

    // set size
    listView.setMaxWidth(MainView.WIDTH / 4);
    listView.setMinWidth(MainView.WIDTH / 4);

    listView.setEditable(true); // make it editable

    // Add in context menu items
    MenuItem rename = new MenuItem("Rename");
    rename.setOnAction(this.listViewController::rename);

    MenuItem move = new MenuItem("Move");
    move.setOnAction(this.listViewController::move);

    MenuItem openInOS = new MenuItem("Open in File Viewer");
    openInOS.setOnAction(this.listViewController::openInOSFileViewer);

    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().addAll(rename, move, openInOS);

    // set cell factory
    listView.setCellFactory(new FileListViewCallback(this.listViewController, contextMenu));

    // add a listener for selected item
    listView.getSelectionModel().selectedItemProperty().addListener(this.listViewController);

    return listView;
  }

  /**
   * Get the main stage
   * 
   * @return the main stage
   */
  public Stage getMainStage() {
    return this.mainStage;
  }

  /**
   * Get the list view controller
   * 
   * @return the list view controller
   */
  public FileListViewController getListViewController() {
    return this.listViewController;
  }

  /**
   * Get backend service
   * 
   * @return the backend service
   */
  public BackendService getBackendService() {
    return this.backendService;
  }

  @Override
  public void stop() {
    this.getBackendService().save();
  }

  /**
   * Get picture viewer
   * 
   * @return
   */
  public PictureViewer getPictureViewer() {
    return this.pictureViewer;
  }

  /**
   * Get list view
   * 
   * @return the list view
   */
  public ListView<Picture> getListView() {
    return this.listView;
  }
}
