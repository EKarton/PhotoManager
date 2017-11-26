package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import backend.models.Picture;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

// https://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm

public class PictureViewController extends BorderPane {

  @FXML
  private Label name;

  @FXML
  ImageView imageView;

  @FXML
  HBox imageControls;
  
  @FXML
  ComboBox<String> historicalNames;
  
  private Picture picture;
  
  private BackendService backEndService;
  private MainController mainController;
  
  private boolean showAbsoluteName;

  public PictureViewController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PictureView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      setUpImageView();
    } catch (IOException exception) {
    }

  }
  
  public void setBackendService(BackendService backendService) {
    this.backEndService = backendService;
  }
  
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  private void setUpImageView() {
    this.imageView.fitWidthProperty().bind(this.widthProperty());
    this.imageView.fitHeightProperty().bind(this.heightProperty());
  }

  public void swapName() {
    this.showAbsoluteName = !this.showAbsoluteName;
    
    this.setName();
  }
  
  private void setName() {
    if(showAbsoluteName) {
      this.name.setText(this.picture.getAbsolutePath());
    }
    else {
      this.name.setText(this.picture.getTaglessName());
    }
  }
  
  private List<String> getHistoricalNames() {
    List<String> names = new ArrayList<String>();
    
    for (String name : this.picture.getHistoricalTaglessNames()) {
      names.add(name);
    }

    return names;
  }
  
  public void rename() {
    String newName = this.historicalNames.getSelectionModel().getSelectedItem();
    this.backEndService.rename(picture, newName);
    this.mainController.getListView().getItems().setAll(this.backEndService.getPictureManager().getPictures());
    this.setName();
  }
  
  public void setPicture(Picture newPicture) {

    this.picture = newPicture;
    
    this.setName();

    try {
      FileInputStream inputStream = new FileInputStream(newPicture.getAbsolutePath());
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      inputStream.close();
      this.imageView.setImage(image);
      
      this.imageControls.setVisible(true);
     
      this.historicalNames.getItems().setAll(this.getHistoricalNames());
      
    } catch (IOException e) {
      // there is no image selected
      imageControls.setVisible(false);
    }


  }



}
