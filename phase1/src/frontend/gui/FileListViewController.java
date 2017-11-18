package frontend.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import backend.models.Picture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

public class FileListViewController extends Controller implements ChangeListener<Picture>{
  private ObservableList<Picture> items;
  private ListView<Picture> listView;

  public FileListViewController(MainView mainView) {
    super(mainView);

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
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue,
      Picture newValue) {
    try {
      Image image = new Image(new FileInputStream(newValue.getAbsolutePath()));
      this.getMainView().getPictureImageView().setImage(image);
      this.getMainView().setPictureName(newValue.getTaglessName());
    } catch (FileNotFoundException e) {
      // This should never happen but if it does just set the image to be nothing
      this.getMainView().getPictureImageView().setImage(null);
      this.getMainView().setPictureName("");
    }
  }

  public void rename(ActionEvent e) {
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void move(ActionEvent e) {
    String newDirectory = this.getMainView().openDirectoryChooser(this.getMainView().getMainStage())
        .getAbsolutePath();
    String oldDirectory = this.listView.getSelectionModel().getSelectedItem().getAbsolutePath();

    this.listView.getSelectionModel().getSelectedItem().setDirectoryPath(newDirectory);

    // if it was properly moved
    if (!new File(oldDirectory).exists()) {
      this.items.remove(this.listView.getSelectionModel().getSelectedIndex());
    }
  }

  public void delete(ActionEvent e) {
    this.getMainView().getMainController().getPictureManager()
        .untrackPicture(this.listView.getSelectionModel().getSelectedItem());

    this.getFileManager().deleteFile(this.listView.getSelectionModel().getSelectedItem().getAbsolutePath());
    this.items.remove(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void setView(ListView<Picture> listView) {
    this.listView = listView;
  }
}
