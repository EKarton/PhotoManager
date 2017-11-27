package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import backend.models.Picture;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class SlideShow {
  private Stage window;
  private List<Picture> pictures;
  private int curImageIndex = 0;
  private Timeline gameLoop;

  @FXML
  private ImageView imageView;
  @FXML
  private StackPane imageContainer;
  
  @FXML
  private Button playPauseButton;

  private boolean isPlaying = false;

  public SlideShow(List<Picture> pictures) {
    this.window = new Stage();
    this.window.setTitle("Slide Show");
    this.window.initModality(Modality.APPLICATION_MODAL);
    
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("SlideShowView.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      
      Scene scene = new Scene(root);
      this.window.setScene(scene);
      
      this.imageView.fitWidthProperty().bind(imageContainer.widthProperty());
      this.imageView.fitHeightProperty().bind(imageContainer.heightProperty());
      
    } catch (IOException e) {
    }

    this.pictures = pictures;
    
    this.window.setOnCloseRequest(this::onClose);
  }

  public void toggleSlideshow() {
    if (this.isPlaying) {
      this.gameLoop.stop();
      this.playPauseButton.setText("Resume");
    } else {
      this.gameLoop.play();
      this.playPauseButton.setText("Pause");
    }

    this.isPlaying = !this.isPlaying;
  }

  public void stopSlideshow() {
    this.gameLoop.stop();
    this.window.close();
  }

  private void onClose(WindowEvent windowEvent) {
    this.stopSlideshow();
  }

  public void play() {
    // Set up the animation loop
    this.isPlaying = true;
    this.gameLoop = new Timeline();
    gameLoop.setCycleCount(Timeline.INDEFINITE);

    KeyFrame kf = new KeyFrame(Duration.seconds(2), this::runSlideshow);
    gameLoop.getKeyFrames().add(kf);
    gameLoop.play();
    this.window.show();
  }

  private Picture getNextPicture() {
    if (this.curImageIndex == this.pictures.size())
      this.curImageIndex = 0;

    Picture nextPicture = this.pictures.get(this.curImageIndex);
    this.curImageIndex += 1;
    return nextPicture;
  }

  private void runSlideshow(ActionEvent actionEvent) {
    Picture pictureToDisplay = getNextPicture();
    try {
      InputStream inputStream = new FileInputStream(pictureToDisplay.getAbsolutePath());
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);

      this.imageView.setImage(image);
      inputStream.close();
    } catch (IOException ignored) {
    }
  }
}
