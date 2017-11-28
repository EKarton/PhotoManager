package frontend.gui.windows;

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

/**
 * A class used to present the pictures in a Slideshow. It will be in another window and will
 * transition between different pictures by a fixed time interval.
 *
 * @author Emilio Kartono Shimi Smith
 * @version 1
 */
public class SlideShow {

  /**
   * The window of the current slideshow
   */
  private Stage window;

  /**
   * A list of pictures being used in the slideshow
   */
  private List<Picture> pictures;

  /**
   * The index of the current image being displayed in the slideshow
   */
  private int curImageIndex = 0;

  /**
   * The game loop used to display the images based on a time interval.
   */
  private Timeline gameLoop;

  /**
   * The component in the UI that displays the image on the window
   */
  @FXML
  private ImageView imageView;

  /**
   * The container in the FXML used to wrap around the window.
   */
  @FXML
  private StackPane imageContainer;

  /**
   * The button in the FXML used to play/pause the slideshow
   */
  @FXML
  private Button playPauseButton;

  /**
   * Stores whether the slideshow is currently being played or not.
   */
  private boolean isPlaying = false;

  /**
   * Constructs a new Slideshow instance given a list of pictures to display on the slideshow
   *
   * @param pictures A list of pictures to present on the slideshow
   */
  public SlideShow(List<Picture> pictures) {
    this.window = new Stage();
    this.window.setTitle("Slide Show");
    this.window.initModality(Modality.APPLICATION_MODAL);

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("views/SlideShowView.fxml"));
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

  /**
   * Toggles the slideshow; i.e, if it is currently playing, calling this method will pause it. On
   * the other hand, if it is currently paused, calling this method will resume and play the
   * slideshow
   */
  @FXML
  public void toggleSlideshow(ActionEvent event) {
    if (this.isPlaying) {
      this.gameLoop.stop();
      this.playPauseButton.setText("Resume");
    } else {
      this.gameLoop.play();
      this.playPauseButton.setText("Pause");
    }

    this.isPlaying = !this.isPlaying;
  }

  /**
   * Stops the slideshow
   */
  @FXML
  public void stopSlideshow() {
    this.gameLoop.stop();
    this.window.close();
  }

  /**
   * An event handler which is called when the window is closed.
   *
   * @param windowEvent The window that caused this method to be called.
   */
  private void onClose(WindowEvent windowEvent) {
    this.stopSlideshow();
  }

  /**
   * Plays the slideshow
   */
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

  /**
   * Obtain the next picture. If it reaches the end of the list of pictures, it will return the
   * first picture.
   * @return The next picture in the slideshow.
   */
  private Picture getNextPicture() {
    if (this.curImageIndex == this.pictures.size()) {
      this.curImageIndex = 0;
    }

    Picture nextPicture = this.pictures.get(this.curImageIndex);
    this.curImageIndex++;
    return nextPicture;
  }

  /**
   * An event handler used to play the slideshow
   * @param actionEvent The UI component that called this method.
   */
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
