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

public class FileListViewController extends Controller implements ChangeListener<Picture> {
  private ObservableList<Picture> items;
  private ListView<Picture> listView;

  public FileListViewController(MainView mainView, BackendService service) {
    super(mainView, service);
    this.items = FXCollections.observableList(service.getPictureManager().getPictures());
  }

  public ObservableList<Picture> getItems() {
    return this.items;
  }

  public void setItems(List<Picture> list) {
    this.items.setAll(list);
  }

  @Override
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue,
      Picture newValue) {
    if (newValue != null) {  // since we set it to null in edit mode
      this.getMainView().getPictureViewer().updatePictureViewer(newValue);
    }
  }

  public void rename(ActionEvent e) {
    listView.layout();
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void move(ActionEvent e) {
    String newDirectory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    Picture selectedPicture = this.listView.getSelectionModel().getSelectedItem();
    selectedPicture.setDirectoryPath(newDirectory);

    this.getMainView().getListViewController()
        .setItems(this.getBackendService().getPictureManager().getPictures()); // update the list
  }

  public void setListView(ListView<Picture> listView) {
    this.listView = listView;
  }

  public void openInOSFileViewer(ActionEvent e) {
    // https://stackoverflow.com/a/12340147
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
