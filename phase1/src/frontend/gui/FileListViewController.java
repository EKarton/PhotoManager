package frontend.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import backend.models.Picture;
import backend.models.PictureManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

public class FileListViewController extends Controller implements ChangeListener<Picture> {
  private ObservableList<Picture> items;
  private ListView<Picture> listView;

  private BackendService service; // TODO what is this
  
  public FileListViewController(MainView mainView, BackendService service) {
    super(mainView);
    this.service = service;
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
    Picture selectedPicture = newValue;
    if (selectedPicture == null)
      selectedPicture = oldValue;

    this.getMainView().getPictureViewer().setPicture(selectedPicture);
  }

  public void rename(ActionEvent e) {
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void move(ActionEvent e) {
    String newDirectory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    Picture selectedPicture = this.listView.getSelectionModel().getSelectedItem();
    selectedPicture.setDirectoryPath(newDirectory);

    this.getMainView().getListViewController().setItems(this.service.pictureManager().getPictures());
    
    //TODO check this - this is what I had before
    /*
     *   private void openDirectory(boolean recursive) {
    File file = this.getMainView().openDirectoryChooser(this.getMainView().getMainStage());

    if (file != null) {
      String directory = file.getAbsolutePath();
      
      try {
        PictureManager pictureManager = new PictureManager(directory, recursive);
        this.getMainView().getMainController().setPictureManager(pictureManager);
        this.getMainView().getListViewController().setItems(pictureManager.getPictures());
      } catch (Exception exception) {
        this.getMainView().getListViewController().setItems(null);
      }
    }*/
  }

  public void delete(ActionEvent e) {
    Picture pictureSelected = this.listView.getSelectionModel().getSelectedItem();
    this.service.pictureManager().untrackPicture(pictureSelected);

    this.getMainView().getListViewController().setItems(this.service.pictureManager().getPictures());  // TODO before I just removed
  }

  public void setView(ListView<Picture> listView) {
    this.listView = listView;
  }
}
