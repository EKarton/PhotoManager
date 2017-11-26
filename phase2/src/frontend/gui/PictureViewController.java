package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import backend.models.Picture;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

// https://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm

public class PictureViewController extends BorderPane {

  @FXML
  private Label name;

  @FXML
  ImageView imageView;

  private Picture picture;

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

  private void setUpImageView() {
    this.imageView.fitWidthProperty().bind(this.widthProperty());
    this.imageView.fitHeightProperty().bind(this.heightProperty());
  }

  public void setPicture(Picture newPicture) {

    this.picture = newPicture;
    this.name.setText(this.picture.getTaglessName());

    try {
      FileInputStream inputStream = new FileInputStream(newPicture.getAbsolutePath());
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      inputStream.close();
      this.imageView.setImage(image);
    } catch (IOException e) {
      // there is no image selected
    }


  }



}
