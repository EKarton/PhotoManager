package frontend.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import backend.files.FileManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

public class FileListViewController implements ChangeListener<File> {
  private ObservableList<File> items;
  private ListView<File> listView;
  private FileManager fileManager;

  public FileListViewController() {
    List<File> defaultEmptyList = new ArrayList<>();
    this.items = FXCollections.observableList(defaultEmptyList);
    
    this.fileManager = new FileManager();
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
    // TODO display picture
  }

  public void rename(ActionEvent e) {
    System.out.println(this.listView.getSelectionModel().getSelectedItem());
  }

  public void move(ActionEvent e) {
    // TODO
  }

  public void delete(ActionEvent e) {
    this.fileManager.deleteFile(this.listView.getSelectionModel().getSelectedItem());
    this.items.remove(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void setView(ListView<File> listView) {
    this.listView = listView;
  }
}
