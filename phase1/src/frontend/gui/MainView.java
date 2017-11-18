package frontend.gui;

import backend.models.Picture;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainView extends Application {
  public static final int WIDTH = 1080;
  public static final int HEIGHT = 600;

  /** Controller for all action event handling */
  private MainViewController mainViewController;
  private FileListViewController listViewController;
  private BackendService backendService;
  private ImageView pictureImageView;
  private Label pictureName;

  private PictureViewer pictureViewer;

  private Stage mainStage;

  /**
   * Launches the main view
   * 
   * @param args
   * @return
   */
  public static void startMainView(String[] args) {
    launch(args);
  }

  /**
   * Note: this constructor will be called by launch(args) but does not need to be called from any
   * where else
   */
  public MainView() {
    // create the controller for all action event handling
    this.backendService = new BackendService();

    pictureViewer = new PictureViewer(this);

    this.mainViewController = new MainViewController(this, backendService);
    this.listViewController = new FileListViewController(this, backendService);
  }

  @Override
  public void start(Stage mainStage) throws Exception {

    this.mainStage = mainStage;

    mainStage.setTitle("Picture Manager"); // the primary stage provides the window

    BorderPane root = new BorderPane(); // Using a border pane layout

    root.setTop(createMenuBar()); // add the menu bar on top

    ListView<Picture> listView = createFileListView();
    listView.setPrefSize(WIDTH / 4, MainView.HEIGHT);
    HBox hBox = new HBox();
    hBox.setMinWidth(MainView.WIDTH);
    hBox.getChildren().add(listView);
    hBox.getChildren().add(pictureViewer);

    //hBox.getChildren().add(this.createPictureViewer());

    root.setCenter(hBox);

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
    MenuBar menuBar = new MenuBar();

    Menu open = new Menu("Open");
    Menu openDir = new Menu("Open Directory");
    MenuItem openDirNonRec = new MenuItem("Open Directory");
    MenuItem openDirRec = new MenuItem("Open Directory Recursively");
    openDirNonRec.setOnAction(this.mainViewController::openDirectory);
    openDirRec.setOnAction(this.mainViewController::openDirectoryRecursively);
    openDir.getItems().addAll(openDirNonRec, openDirRec);

    MenuItem openLog = new MenuItem("Open Log");
    openLog.setOnAction(this.mainViewController::openLog);
    open.getItems().addAll(openDir, openLog);

    Menu save = new Menu("Save");
    MenuItem saveItem = new MenuItem("Save");
    saveItem.setOnAction(this.mainViewController::save);
    save.getItems().add(saveItem);

    Menu undo = new Menu("Undo");
    MenuItem undoItem = new MenuItem("Undo");
    undoItem.setOnAction(this.mainViewController::undo);
    undo.getItems().add(undoItem);

    Menu redo = new Menu("Redo");
    MenuItem redoItem = new MenuItem("Redo");
    redoItem.setOnAction(this.mainViewController::redo);
    redo.getItems().add(redoItem);

    menuBar.getMenus().addAll(open, save, undo, redo);

    return menuBar;
  }

  /*
  private VBox createPictureViewer() {
    VBox pictureBox = new VBox();
    
    pictureName = new Label();
    pictureName.setFont(Font.font ("Verdana", 20));
    pictureName.setPadding(new Insets(0, 0, 5, 0));
    
    this.pictureImageView = new ImageView();
    pictureImageView.setFitHeight(HEIGHT);
    pictureImageView.setFitWidth((3 * WIDTH) / 4);
    //pictureImageView.setFitWidth((3 * WIDTH) / 4);
    //pictureImageView.setFitHeight(MainView.HEIGHT);
    pictureImageView.setPreserveRatio(true); // this lets us nicely scale the image
    
    pictureBox.getChildren().addAll(pictureName, this.pictureImageView);
    
    return pictureBox;
  }
  */

  public File openDirectoryChooser() {
    // http://java-buddy.blogspot.ca/2013/03/javafx-simple-example-of.html
    DirectoryChooser dirChooser = new DirectoryChooser();
    //dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File file = dirChooser.showDialog(this.mainStage);
    return file;
  }


  public File openFileChooser() {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", "*jpg", "*.jpeg", "*.png"));

    File file = fileChooser.showOpenDialog(this.mainStage);

    return file;
  }


  private ListView<Picture> createFileListView() {
    ListView<Picture> listView = new ListView<>(this.listViewController.getItems());
    this.listViewController.setView(listView);  // must call this
    
    listView.setEditable(true);

    MenuItem rename = new MenuItem("Rename");
    rename.setOnAction(this.listViewController::rename);

    MenuItem move = new MenuItem("Move");
    move.setOnAction(this.listViewController::move);

    MenuItem delete = new MenuItem("Delete");
    delete.setOnAction(this.listViewController::delete);

    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().addAll(rename, move, delete);
    
    listView.setCellFactory(new FileListViewCallback(this, contextMenu));

    listView.getSelectionModel().selectedItemProperty().addListener(this.listViewController);

    return listView;
  }

  public Stage getMainStage() {
    return this.mainStage;
  }


  public FileListViewController getListViewController() {
    return this.listViewController;
  }

  /*
  public ImageView getPictureImageView() {
    return this.pictureImageView;
  }
  */

  public PictureViewer getPictureViewer() {
    return pictureViewer;
  }

  public BackendService getBackendService() {
    return this.backendService;
  }

  /*
  public void setPictureName(String name) {
    this.pictureName.setText(name);
  }
  */

  @Override
  public void stop() {
    // This is where we can put custom shut down code
  }
}
