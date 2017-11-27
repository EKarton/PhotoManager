package frontend.gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
import javax.naming.NoInitialContextException;
import backend.models.Picture;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {
  /** The backend service being used by the program */
  private BackendService backendService;

  /** The stage of the javafx program */
  private Stage stage;

  /** The root of the javafx scene */
  @FXML
  private BorderPane root;

  /** The search bar on top of the ListView */
  @FXML
  private TextField searchBar;

  /** The ListView of pictures */
  @FXML
  private ListView<Picture> pictureListView;

  /** The context menu for the ListView */
  @FXML
  private ContextMenu listCellContextMenu;

  /** The controller for the picture view */
  @FXML
  private PictureViewController pictureView;

  /**
   * Setup the main view. Initialize the backend service, set the list view and set the controller
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.backendService = new BackendService();
    setListView();
    this.pictureView.setBackendService(this.backendService);
    this.pictureView.setMainController(this);
  }

  /**
   * Set the stage
   * 
   * @param stage the stage of the javafx application
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Get the stage
   * 
   * @return The stage
   */
  public Stage getStage() {
    return this.stage;
  }

  /**
   * Sets the list view displaying the list of images
   */
  private void setListView() {
    this.pictureListView.setCellFactory(new FileListViewCallback(this.listCellContextMenu, this));

    // set a listener to listen for changes in selection
    this.pictureListView.getSelectionModel().selectedItemProperty()
        .addListener(new ListViewChangeListener(this));
  }

  /**
   * Get the picture view's controller
   * 
   * @return The picture view's controller
   */
  public PictureViewController getPictureViewController() {
    return this.pictureView;
  }

  /**
   * Open a directory chooser
   * 
   * @return the chosen directory
   */
  private File openDirectoryChooser() {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    return dirChooser.showDialog(this.root.getScene().getWindow());
  }

  /**
   * Open and display directory
   * 
   * @param recursive open directory recursively if true
   */
  private void openDirectory(boolean recursive) {
    File file = openDirectoryChooser();

    if (file != null) {
      String directory = file.getAbsolutePath();

      this.backendService.resetBackendService(directory, recursive);

      pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
    }
  }

  /**
   * Open directory non-recursively
   */
  @FXML
  public void openDirectory() {
    openDirectory(false);
  }

  /**
   * Open directory recursively
   */
  @FXML
  public void openDirectryRecursively() {
    openDirectory(true);
  }

  /**
   * Get the list view that display's the image names
   * 
   * @return the list view
   */
  public ListView<Picture> getListView() {
    return this.pictureListView;
  }

  /**
   * Displays the log in a pop up window
   */
  @FXML
  public void openLog() {
    String logs = this.getBackendService().getCommandManager().getLogs();
    if (logs != "") {
      TextDialog dialog = new TextDialog("Logs", logs);
      dialog.show();
    }
  }

  /**
   * Open the tag manager in a pop up window
   */
  @FXML
  public void manageTags() {
    TagManagement tagManagement = new TagManagement("Tag Manager", this);
    tagManagement.show();
  }

  /**
   * Save the program
   */
  @FXML
  public void save() {
    this.getBackendService().save();
  }

  /**
   * Undo the last change
   */
  @FXML
  public void undo() {
    try {
      this.getBackendService().getCommandManager().undoRecentCommand();
    } catch (NoInitialContextException e) {
    }
  }
  
  /**
   * Redo the last change
   */
  @FXML
  public void redo() {
    try {
      this.getBackendService().getCommandManager().redoRecentCommand();
    } catch (NoInitialContextException e) {
    }
  }

  /**
   * This is the move handler for the move option in the context menu
   * 
   * @param e the action event
   */
  @FXML
  public void move() {
    // get the new directory
    String newDirectory = openDirectoryChooser().getAbsolutePath();

    // get the selected picture
    Picture selectedPicture = this.pictureListView.getSelectionModel().getSelectedItem();

    selectedPicture.setDirectoryPath(newDirectory); // move the picture

    this.pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
  }

  /**
   * Search function for search bar on top of list view
   */
  @FXML
  public void search(KeyEvent keyEvent) {
    String filter = keyEvent.getCharacter().toLowerCase();
    String curText = this.searchBar.getText() + filter;
    if (!filter.equals("") && !filter.equals("\b")){
      ArrayList<Picture> filteredPictures = new ArrayList<>();
      for (Picture picture : this.getBackendService().getPictureManager().getPictures()){
        if (picture.getTaglessName().toLowerCase().contains(curText))
          filteredPictures.add(picture);
      }

      this.pictureListView.getItems().setAll(filteredPictures);
    }
    else if (filter.equals("\b") || curText.equals("")){
      this.pictureListView.getItems().setAll(this.getBackendService().getPictureManager().getPictures());
    }
  }

  /**
   * Start the slide show
   */
  @FXML
  public void startSlideShow() {
    ArrayList<Picture> pictures = this.backendService.getPictureManager().getPictures();

    if (!pictures.isEmpty()) {
      SlideShow slideshow = new SlideShow(pictures);
      slideshow.play();
    }
  }

  /**
   * Opens the OS's file viewer to the current directory
   * 
   * Reference: https://stackoverflow.com/a/12340147
   *
   * @param e the action event
   */
  @FXML
  public void openInOSFileViewer() {
    if (Desktop.isDesktopSupported()) {
      File file =
          new File(this.pictureListView.getSelectionModel().getSelectedItem().getDirectoryPath());
      try {
        Desktop.getDesktop().open(file);
      } catch (IOException e1) {
        // this should never happen - at this point a picture cannot be possibly be a file
      }
    }
  }

  /**
   * Get the backend service
   * 
   * @return the backend service
   */
  public BackendService getBackendService() {
    return this.backendService;
  }

}
