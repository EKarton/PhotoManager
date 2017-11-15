package frontend.gui;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainView extends Application {
  private static final int WIDTH = 1080;
  private static final int HEIGHT = 720;

  /** Controller for all action event handling */
  private ActionEventController actionEventController;
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
  }

  @Override
  public void start(Stage mainStage) throws Exception {
    
    this.mainStage = mainStage;
    
    mainStage.setTitle("Picture Manager"); // the primary stage provides the window

    BorderPane root = new BorderPane(); // Using a border pane layout

    root.setTop(createMenuBar()); // add the menu bar on top

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

    MenuItem openDir = new MenuItem("Open Directory");
    openDir.setOnAction(this.actionEventController::openDirectory);

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
  
  public Stage getMainStage() {
    return this.mainStage;
  }
  
  
  @Override
  public void stop() {
    // This is where we can put custom shut down code
  }
}
