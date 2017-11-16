package frontend.gui;

import java.io.File;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainView extends Application {
  private static final int WIDTH = 1080;
  private static final int HEIGHT = 720;

  /** Controller for all action event handling */
  private ActionEventController actionEventController;
  private ListViewController<File> listViewController;
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

    // create the controller for the ListView that displays the list of files
    this.listViewController = new ListViewController<File>();
  }

  @Override
  public void start(Stage mainStage) throws Exception {

    this.mainStage = mainStage;

    mainStage.setTitle("Picture Manager"); // the primary stage provides the window

    BorderPane root = new BorderPane(); // Using a border pane layout

    root.setTop(createMenuBar()); // add the menu bar on top
    
    ListView<File> listView = createFileListView(this.listViewController);
    root.setCenter(listView);

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

    Menu add = new Menu("Add");

    MenuItem addImage = new MenuItem("Add Image");
    addImage.setOnAction(this.actionEventController::addImage);

    MenuItem addDir = new MenuItem("Add Directory");
    addDir.setOnAction(this.actionEventController::addDirectory);

    add.getItems().addAll(addImage, addDir);

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

    menuBar.getMenus().addAll(add, open);

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

  public ListView<File> createFileListView(ListViewController<File> controller) {

    ListView<File> listView = new ListView<File>(controller.getItems());

    listView.setCellFactory(listCell -> new ListCell<File>() {
      @Override
      public void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item.getName());
        }
      }
    });

    listView.getSelectionModel().selectedItemProperty().addListener(this.listViewController);

    return listView;
  }

  public Stage getMainStage() {
    return this.mainStage;
  }


  public ListViewController<File> getListViewController() {
    return this.listViewController;
  }

  @Override
  public void stop() {
    // This is where we can put custom shut down code
  }
}
