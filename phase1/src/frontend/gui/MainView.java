package frontend.gui;

import java.io.File;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javafx.stage.Stage;

public class MainView extends Application {
  private static final int WIDTH = 1080;
  private static final int HEIGHT = 720;

  /** Controller for all action event handling */
  private ActionEventController actionEventController;
  private FileListViewController listViewController;
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
    this.actionEventController = new ActionEventController(this);
    this.listViewController = new FileListViewController(this);
  }

  @Override
  public void start(Stage mainStage) throws Exception {

    this.mainStage = mainStage;

    mainStage.setTitle("Picture Manager"); // the primary stage provides the window

    BorderPane root = new BorderPane(); // Using a border pane layout

    root.setTop(createMenuBar()); // add the menu bar on top

    ListView<File> listView = createFileListView();
    listView.setPrefSize(WIDTH / 4, MainView.HEIGHT);
    HBox hbox = new HBox();
    hbox.getChildren().add(listView);


    Image image = new Image(new FileInputStream("dog.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth((3 * WIDTH) / 4);
    imageView.setFitHeight(MainView.HEIGHT);
    imageView.setPreserveRatio(true); // this lets us nicely scale the image

    hbox.getChildren().add(imageView);

    root.setCenter(hbox);

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
    openDirNonRec.setOnAction(this.actionEventController::openDirectory);

    MenuItem openDirRec = new MenuItem("Open Directory Recursively");
    openDirRec.setOnAction(this.actionEventController::openDirectoryRecursively);

    openDir.getItems().addAll(openDirNonRec, openDirRec);

    MenuItem openLog = new MenuItem("Open Log");
    openLog.setOnAction(this.actionEventController::openLog);

    open.getItems().addAll(openDir, openLog);

    Menu save = new Menu("Save");
    MenuItem saveItem = new MenuItem("Save");
    saveItem.setOnAction(this.actionEventController::save);
    save.getItems().add(saveItem);

    Menu undo = new Menu("Undo");
    MenuItem undoItem = new MenuItem("Undo");
    undoItem.setOnAction(this.actionEventController::undo);
    undo.getItems().add(undoItem);

    Menu redo = new Menu("Redo");
    MenuItem redoItem = new MenuItem("Redo");
    redoItem.setOnAction(this.actionEventController::redo);
    redo.getItems().add(redoItem);

    menuBar.getMenus().addAll(open, save, undo, redo);

    return menuBar;
  }


  public File openDirectoryChooser(Stage mainStage) {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File file = dirChooser.showDialog(mainStage);
    return file;
  }


  public File openFileChooser(Stage mainStage) {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", "*jpg", "*.jpeg", "*.png"));

    File file = fileChooser.showOpenDialog(mainStage);

    return file;
  }


  public ListView<File> createFileListView() {
    ListView<File> listView = new ListView<File>(this.listViewController.getItems());
    this.listViewController.setView(listView);  // must call this

    MenuItem rename = new MenuItem("Rename");
    rename.setOnAction(this.listViewController::rename);

    MenuItem move = new MenuItem("Move");
    move.setOnAction(this.listViewController::move);

    MenuItem delete = new MenuItem("Delete");
    delete.setOnAction(this.listViewController::delete);


    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().addAll(rename, move, delete);

    // TODO clean this up later
    listView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {

      @Override
      public ListCell<File> call(ListView<File> param) {
        ListCell<File> cell = new ListCell<File>() {
          @Override
          public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setText(null);
            } else {
              setText(item.getName());
            }
          }
        };

        cell.setContextMenu(contextMenu);
        return cell;
      }
    });

    listView.getSelectionModel().selectedItemProperty().addListener(this.listViewController);

    return listView;
  }

  public Stage getMainStage() {
    return this.mainStage;
  }


  public FileListViewController getListViewController() {
    return this.listViewController;
  }
  

  @Override
  public void stop() {
    // This is where we can put custom shut down code
  }
}
