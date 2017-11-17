package frontend.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

public class FileListViewController extends Controller implements ChangeListener<File> {
  private ObservableList<File> items;
  private ListView<File> listView;
  
  public FileListViewController(MainView mainView) {
    super(mainView);
    
    List<File> defaultEmptyList = new ArrayList<>();
    this.items = FXCollections.observableList(defaultEmptyList);
  }

  public ObservableList<File> getItems() {
    return this.items;
  }

  public void setItems(List<File> list) {
    this.items.setAll(list);
  }

  public void addItem(File item) {
    this.items.add(item);
  }

  @Override
  public void changed(ObservableValue<? extends File> observable, File oldValue, File newValue) {
    try {
      Image image = new Image(new FileInputStream(newValue));
      this.getMainView().getPictureImageView().setImage(image);
      this.getMainView().setPictureName(newValue.getName());
    } catch (FileNotFoundException e) {
      // This should never happen but if it does just set the image to be nothing
      this.getMainView().getPictureImageView().setImage(null);
    }
  }

  public void rename(ActionEvent e) {
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void move(ActionEvent e) {
    String newDirectory = this.getMainView().openDirectoryChooser(this.getMainView().getMainStage()).getAbsolutePath();
    
    // if the file is moved
    if (this.getFileManager().moveFile(this.listView.getSelectionModel().getSelectedItem(), newDirectory)) {
      // remove it from the list
      this.items.remove(this.listView.getSelectionModel().getSelectedIndex());
    }
  }

  public void delete(ActionEvent e) {
    this.getFileManager().deleteFile(this.listView.getSelectionModel().getSelectedItem());
    this.items.remove(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void setView(ListView<File> listView) {
    this.listView = listView;
  }
}
