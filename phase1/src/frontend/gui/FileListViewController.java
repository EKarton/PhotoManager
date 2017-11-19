package frontend.gui;

import java.util.ArrayList;
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
    List<Picture> defaultEmptyList = new ArrayList<>();
    this.items = FXCollections.observableList(defaultEmptyList);
  }

  public ObservableList<Picture> getItems() {
    return this.items;
  }

  public void setItems(List<Picture> list) {
    this.items.setAll(list);
  }

  @Override
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue, Picture newValue) {
    this.getMainView().getPictureViewer().setPicture(newValue);
  }

  public void rename(ActionEvent e) {
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void move(ActionEvent e) {
    String newDirectory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    Picture selectedPicture = this.listView.getSelectionModel().getSelectedItem();
    selectedPicture.setDirectoryPath(newDirectory);

    this.getMainView().getListViewController().setItems(this.getBackendService().getPictureManager().getPictures());  // update the list
  }

  public void delete(ActionEvent e) {
    Picture pictureSelected = this.listView.getSelectionModel().getSelectedItem();
    this.getBackendService().getPictureManager().untrackPicture(pictureSelected);  // TODO make sure this deletes it

    this.getMainView().getListViewController().setItems(this.getBackendService().getPictureManager().getPictures());  // update the list
  }
}
