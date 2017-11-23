package frontend.gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import backend.models.Picture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

public class MainController implements Initializable {

  BackendService backendService;

  @FXML
  BorderPane root;

  @FXML
  private ListView<Picture> pictureListView;

  @FXML
  private ContextMenu listCellContextMenu;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.backendService = new BackendService();

    setListView();
  }

  private void setListView() {
    this.pictureListView.setCellFactory(new FileListViewCallback(this.listCellContextMenu));
    this.pictureListView.getSelectionModel().selectedItemProperty()
        .addListener(new ListViewChangeListener());
  }

  private File openDirectoryChooser() {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    return dirChooser.showDialog(this.root.getScene().getWindow());
  }

  private void openDirectory(boolean recursive) {
    File file = openDirectoryChooser();

    if (file != null) {
      String directory = file.getAbsolutePath();

      this.backendService.resetBackendService(directory, recursive);

      pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
    }
  }

  public void openDirectory() {
    openDirectory(false);
  }

  public void openDirectryRecursively() {
    openDirectory(true);
  }

  public void openLog() {
    // TODO fill in
  }

  public void manageTags() {
    // TODO fill in
  }

  public void save() {
    System.out.println("Save");
  }

  public void undo() {
    System.out.println("Undo");
  }

  public void redo() {
    System.out.println("Redo");
  }

  /**
   * This is the move handler for the move option in the context menu
   * 
   * @param e the action event
   */
  public void move() {
    // get the new directory
    String newDirectory = openDirectoryChooser().getAbsolutePath();

    // get the selected picture
    Picture selectedPicture = this.pictureListView.getSelectionModel().getSelectedItem();

    selectedPicture.setDirectoryPath(newDirectory); // move the picture
    
    this.pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
  }

  /**
   * Handler for "Open in File Viewer" function Opens the OSs file viewer in the selected directory
   * 
   * Reference: https://stackoverflow.com/a/12340147
   *
   * @param e the action event
   */
  public void openInOSFileViewer(ActionEvent e) {
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

}
