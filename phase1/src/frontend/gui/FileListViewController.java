package frontend.gui;

import backend.models.Picture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class FileListViewController extends Controller implements ChangeListener<Picture> {
  private ObservableList<Picture> items;
  private ListView<Picture> listView;

  private BackendService service;
  
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

    /*

    try {

      this.getMainView().getPictureViewer().setPicture(selectedPicture);

      InputStream inputStream = new FileInputStream(newValue.getAbsolutePath());
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      this.getMainView().getPictureImageView().setImage(image);
      this.getMainView().setPictureName(newValue.getFullFileName());

      inputStream.close();
    } catch (FileNotFoundException e) {
      // This should never happen but if it does just set the image to be nothing
      this.getMainView().getPictureImageView().setImage(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    */
  }

  public void rename(ActionEvent e) {
    this.listView.edit(this.listView.getSelectionModel().getSelectedIndex());
  }

  public void move(ActionEvent e) {
    String newDirectory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    Picture selectedPicture = this.listView.getSelectionModel().getSelectedItem();
    selectedPicture.setDirectoryPath(newDirectory);

    this.getMainView().getListViewController().setItems(this.service.pictureManager().getPictures());
  }

  public void delete(ActionEvent e) {
    Picture pictureSelected = this.listView.getSelectionModel().getSelectedItem();
    this.service.pictureManager().untrackPicture(pictureSelected);

    this.getMainView().getListViewController().setItems(this.service.pictureManager().getPictures());
  }

  public void setView(ListView<Picture> listView) {
    this.listView = listView;
  }
}
