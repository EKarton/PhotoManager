package frontend.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import backend.models.Picture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

/**
 * The list view's controller
 */
public class FileListViewController extends Controller implements ChangeListener<Picture> {
  /** The list of pictures in the list view */
  private ObservableList<Picture> items;

  /** The list view */
  private ListView<Picture> listView;

  /**
   * Constructs the FileListViewController
   * 
   * @param mainView the main view of the application
   * @param service the backend service used by the application
   */
  public FileListViewController(MainView mainView, BackendService service) {
    super(mainView, service);

    // initialize the list
    this.items = FXCollections.observableList(service.getPictureManager().getPictures());
  }

  /**
   * Get the items in the list view
   * 
   * @return an obeservable list of pictures
   */
  public ObservableList<Picture> getItems() {
    return this.items;
  }

  /**
   * Set the items in the list view
   * 
   * @param list the list of pictures were are using to set the list of items in the list view
   */
  public void setItems(List<Picture> list) {
    this.items.setAll(list);
  }

  @Override
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue,
      Picture newValue) {
    if (newValue != null) { // since we set it to null in edit mode
      // update the picture view
      this.getMainView().getPictureViewer().updatePictureViewer(newValue);
    }
  }

  /**
   * This is the rename handler for the rename option in the context menu
   * 
   * @param e the action event
   */
  public void rename(ActionEvent e) {
    // make sure the list is fully updated before
    listView.layout();

    // initialize the edit mode
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  /**
   * This is the move handler for the move option in the context menu
   * 
   * @param e the action event
   */
  public void move(ActionEvent e) {
    // get the new directory
    String newDirectory = this.getMainView().openDirectoryChooser().getAbsolutePath();

    // get the selected picture
    Picture selectedPicture = this.listView.getSelectionModel().getSelectedItem();

    selectedPicture.setDirectoryPath(newDirectory); // move the pictyre

    this.getMainView().getListViewController()
        .setItems(this.getBackendService().getPictureManager().getPictures()); // update the list
  }

  /**
   * Set the list view
   * 
   * @param listView the new list view
   */
  public void setListView(ListView<Picture> listView) {
    this.listView = listView;
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
      File file = new File(this.listView.getSelectionModel().getSelectedItem().getDirectoryPath());
      try {
        Desktop.getDesktop().open(file);
      } catch (IOException e1) {
        // this should never happen - at this point a picture cannot be possibly be a file
      }
    }
  }
}
