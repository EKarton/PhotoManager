package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import backend.models.Picture;
import backend.models.Tag;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

// https://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm

/** The controller for the picture view */
public class PictureViewController extends BorderPane implements Renamable {

  /** The stack pane holding the image view */
  @FXML
  StackPane imageContainer;

  /** The label representing the name of the image */
  @FXML
  private Label name;
  
  /** Label for the picture's tags */
  @FXML
  private Label tags;

  /** The image view */
  @FXML
  private ImageView imageView;

  /** The picture view */
  @FXML
  private BorderPane pictureView;

  /** The combo box for historical names */
  @FXML
  private ComboBox<String> historicalNames;
  
  /** The check box to show tags */
  @FXML
  private CheckBox showTags;

  /** The picture currently being displayed */
  private Picture picture;

  /** The backend service being used by the program */
  private BackendService backEndService;

  /** The main controller being used by the program */
  private MainController mainController;

  /** True if we are showing the absolute path */
  private boolean showAbsoluteName;

  /**
   * Contructs the picture view controller. Loads the view from the fxml file and sets this as the
   * controller.
   */
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

  /**
   * Set the backend service
   * 
   * @param backendService the backend service
   */
  public void setBackendService(BackendService backendService) {
    this.backEndService = backendService;
  }

  /**
   * Set the main controller
   * 
   * @param mainController the main controller
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * Sets up the image view
   */
  private void setUpImageView() {
    // bind the dimensions of the image view
    this.imageView.fitWidthProperty().bind(this.imageContainer.widthProperty());
    this.imageView.fitHeightProperty().bind(this.imageContainer.heightProperty());
  }

  /**
   * Swap the name being displayed. Either showing absolute name or tagless name.
   */
  public void swapName() {
    this.showAbsoluteName = !this.showAbsoluteName;

    this.setName(); // set the name
  }

  /**
   * Update the name being displayed based on <code>showAbsoluteName</code>
   */
  private void setName() {
    if (showAbsoluteName) {
      this.name.setText(this.picture.getAbsolutePath());
    } else {
      this.name.setText(this.picture.getTaglessName());
    }
  }

  /**
   * Get the past names of the picture being displayed
   * 
   * @return a list of (strings) the past names of the image being displayed
   */
  private List<String> getHistoricalNames() {
    List<String> names = new ArrayList<String>();

    for (String name : this.picture.getHistoricalTaglessNames()) {
      names.add(name);
    }

    return names;
  }

  /**
   * Rename the picture being displayed with the current selection from the combo box
   */
  @FXML
  public void rename() {
    String newName = this.historicalNames.getSelectionModel().getSelectedItem();
    rename(newName);
  }

  /**
   * Rename the picture being displayed with <code>newName</code>
   * 
   * @param newName the new name
   */
  @Override
  public void rename(String newName) {
    this.backEndService.rename(picture, newName);
    this.mainController.getListView().getItems()
        .setAll(this.backEndService.getPictureManager().getPictures());
    this.setPicture(picture);
  }

  /**
   * Set the picture being displayed
   * 
   * @param newPicture the new picture to display
   */
  public void setPicture(Picture newPicture) {

    this.picture = newPicture;

    this.setName();

    try {
      FileInputStream inputStream = new FileInputStream(newPicture.getAbsolutePath());
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      inputStream.close();

      this.imageView.setImage(image);

      this.pictureView.setVisible(true);

      this.historicalNames.getItems().setAll(this.getHistoricalNames());

    } catch (IOException e) {
      // there is no image selected
      this.pictureView.setVisible(false);
    }
  }

  /**
   * Opens up the add tags pop up window for adding tags to the image
   */
  @FXML
  public void addTags() {
    SelectionWindow<Tag> tagSelection =
        new SelectionWindow<>(this.mainController.getStage(), "Add Tags", "Add Tags",
            this.mainController.getBackendService().getPictureManager().getAvailableTags());

    List<Tag> tags = tagSelection.show();
    for (Tag tag : tags) {
      this.picture.addTag(tag);
    }

  }

  /**
   * Opens up the remove tags pop up window for adding tags to the image
   */
  @FXML
  public void removeTags() {
    SelectionWindow<Tag> tagSelection =
        new SelectionWindow<>(this.mainController.getStage(), "Delete Tags", "Delete Tags",
            this.mainController.getBackendService().getPictureManager().getAvailableTags());

    List<Tag> tags = tagSelection.show();
    for (Tag tag : tags) {
      this.picture.addTag(tag);
    }
  }
  
  /**
   * Display the picture's tags
   */
  @FXML
  public void showTags() {
    if(this.showTags.isSelected()) {
      String tagText = "";
      for (Tag t : this.picture.getTags()) {
        tagText += t.getLabel();
      }
      this.tags.setText(tagText);
    }
  }

}
