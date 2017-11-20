package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import backend.models.Picture;
import backend.models.Tag;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * The view that displays the picture and the controls
 */
public class PictureViewer extends BorderPane {
  /** The label for the name of the picture */
  private Label pictureName;

  /** The imageView used to display the image */
  private ImageView imageView;

  /** The controller for the picture view */
  private PictureViewerController controller;

  /** The picture being displayed */
  private Picture picture;

  /** The main view used by the application */
  private MainView mainView;

  /** The drop down menu of old names */
  private ComboBox<String> oldNames;

  /** The button to see historical tags */
  private Button seeHistoricalTagsBttn;

  /** The text area to display tags */
  private TextArea tags;

  /** The text field for creating new tags */
  private TextField newTagTextField;

  /** The drop down box for adding tags */
  private ComboBox<Tag> addTag;

  /** The drop down box for removing tags */
  private ComboBox<Tag> removeTagSelect;

  /** The drop down box for deleting tags */
  private ComboBox<Tag> deleteTagSelect;

  /** The hbox for name controls */
  private HBox nameControls;

  /** The hbox to hold tag controls */
  private HBox tagControlBox;


  /**
   * Creates a picture viewer
   * 
   * @param mainView the main view being used
   */
  public PictureViewer(MainView mainView) {
    this.controller = new PictureViewerController(mainView, this);
    this.mainView = mainView;
    this.createComponenets();
  }

  /**
   * Creates the components for the picture view
   */
  private void createComponenets() {
    // set the label
    this.pictureName = new Label();
    this.pictureName.setFont(Font.font("Verdana", 20));
    this.pictureName.setPadding(new Insets(0, 0, 5, 0));

    nameControls = new HBox(); // make hbox for name controls

    // create button to see historical tags
    seeHistoricalTagsBttn = new Button("See historical tags");
    seeHistoricalTagsBttn.setOnAction(this.controller::seeHistoricalTags);

    // create drop down menu to select old names
    oldNames = new ComboBox<String>();
    oldNames.setPadding(new Insets(0, 10, 0, 0));
    oldNames.setOnAction(this.controller::setNameSelected);

    // button to select the name
    Button changeName = new Button("Change Name");
    changeName.setOnAction(this.controller::changeName);
    
    nameControls.getChildren().addAll(seeHistoricalTagsBttn, oldNames, changeName);

    // create a border pane to hold the title information
    BorderPane title = new BorderPane();
    title.setPrefWidth(3 * (MainView.WIDTH / 4));
    // add in stuff
    title.setLeft(pictureName);
    title.setRight(nameControls);

    this.setTop(title);

    this.imageView = new ImageView();
    this.imageView.setPreserveRatio(true); // this lets us nicely scale the image

    // https://stackoverflow.com/a/37013902
    StackPane imageContainer = new StackPane(imageView);
    this.imageView.fitWidthProperty().bind(imageContainer.widthProperty());
    this.imageView.fitHeightProperty().bind(imageContainer.heightProperty());

    this.setCenter(imageContainer);

    // create a border pane to hold the tag controls
    BorderPane tagControls = new BorderPane();
    tags = new TextArea();
    tags.setEditable(false);
    tags.setPrefWidth(3 * (MainView.WIDTH / 4));
    tags.setPrefHeight(50);
    tagControls.setTop(tags);

    // add field for create tage
    Label createTags = new Label("Create Tag:");
    newTagTextField = new TextField();
    newTagTextField.onActionProperty().set(this.controller::createNewTag);

    // create hbox to hold tag controls
    tagControlBox = new HBox();

    // add label and ComboBox for adding tags
    Label addTagLabel = new Label("Add Tag");
    addTag = new ComboBox<Tag>();
    addTag.getItems()
        .addAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
    addTag.setOnAction(this.controller::addTag);

    // add label and ComboBox for deleting tags from the application
    Label deleteTag = new Label("Delete Tag from Manager");
    this.deleteTagSelect = new ComboBox<Tag>();
    deleteTagSelect.getItems()
        .addAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
    deleteTagSelect.setOnAction(this.controller::deleteTag);


    // add label and ComboBox for deleting tags from image
    Label removeTag = new Label("Remove Tag from Image");
    this.removeTagSelect = new ComboBox<Tag>();
    this.removeTagSelect.setOnAction(this.controller::removeTag);

    // add in all the components
    tagControlBox.getChildren().addAll(createTags, newTagTextField, deleteTag, deleteTagSelect,
        removeTag, removeTagSelect, addTagLabel, addTag);
    tagControlBox.setSpacing(10); // spacing to make it pretty

    tagControls.setBottom(tagControlBox);

    this.setBottom(tagControls);

    updatePictureViewer(null); // starts with nothing shown
  }

  /**
   * Gets the selected item in the remove tag drop down menu
   * 
   * @return the selected tag
   */
  public Tag getSelectedRemoveTag() {
    int index = this.removeTagSelect.getSelectionModel().getSelectedIndex();
    if (index == -1 || index >= this.picture.getTags().size())
      return null;
    return this.picture.getTags().get(index);
  }

  /**
   * Gets the selected item in the delete tag drop down menu
   * 
   * @return the selected tag
   */
  public Tag getSelectedDeleteTag() {

    int index = this.deleteTagSelect.getSelectionModel().getSelectedIndex();
    if (index == -1
        || index >= this.mainView.getBackendService().getPictureManager().getAvailableTags().size())
      return null;
    return this.mainView.getBackendService().getPictureManager().getAvailableTags().get(index);
  }

  /**
   * Gets the text from the create tag text field
   * 
   * @return the text
   */
  public String getNewTagText() {
    return this.newTagTextField.getText();
  }

  /**
   * Reset the text shown in the create tag text field
   */
  public void resetNewTagText() {
    this.newTagTextField.setText("");
  }

  /**
   * Update the picture viewer with the given picture
   * 
   * @param newPicture the picture to display
   */
  public void updatePictureViewer(Picture newPicture) {
    this.picture = newPicture;

    if (this.picture == null) {
      this.setVisible(false);
    } else {
      this.setVisible(true);

      this.oldNames.getItems().clear();
      this.oldNames.getItems().setAll(this.controller.getHistoricalNames());

      try {
        // Get the image
        InputStream inputStream = new FileInputStream(picture.getAbsolutePath());

        BufferedImage bufferedImage = ImageIO.read(inputStream);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        this.imageView.setImage(image);
        inputStream.close();

        // Set the title
        this.pictureName.setText(this.picture.getTaglessName());

        // Display the tags available for all (for removal)
        this.deleteTagSelect.getSelectionModel().clearSelection();

        this.deleteTagSelect.setItems(FXCollections.observableArrayList(
            this.mainView.getBackendService().getPictureManager().getAvailableTags()));

        // Display the tags for only this picture
        this.removeTagSelect.getSelectionModel().clearSelection();
        this.removeTagSelect.setItems(FXCollections.observableArrayList(this.picture.getTags()));

        // Display the tags
        String tagsString = "";
        for (Tag tag : picture.getTags())
          tagsString += " " + tag.getLabel();
        this.tags.setText(tagsString);

        // Update the combo box with tags not in the picture
        List<Tag> tagsNotOnPic = new ArrayList<Tag>();
        List<Tag> availableTags =
            this.mainView.getBackendService().getPictureManager().getAvailableTags();
        for (Tag availTag : availableTags) {
          if (!picture.containsTag(availTag)) {
            tagsNotOnPic.add(availTag);
          }
        }

        addTag.getSelectionModel().clearSelection();
        addTag.setItems(FXCollections.observableArrayList(
            this.mainView.getBackendService().getPictureManager().getAvailableTags()));

      } catch (FileNotFoundException e) {
        this.picture = null;
      } catch (IOException e) {
      }
    }
  }

  /**
   * Gets the picture being displayed
   * 
   * @return the picture being displayed
   */
  public Picture getPicture() {
    return this.picture;
  }

  /**
   * Get the selected tag in the add tag drop down menu
   * 
   * @return the selected tag in the add tag drop down menu
   */
  public Tag getSelectedAddTag() {
    int index = this.addTag.getSelectionModel().getSelectedIndex();
    if (index == -1
        || index >= this.mainView.getBackendService().getPictureManager().getAvailableTags().size())
      return null;
    return this.mainView.getBackendService().getPictureManager().getAvailableTags().get(index);
  }

  /**
   * Get the historical name selected
   * 
   * @return the historical name selected
   */
  public String getOldNameSelected() {
    return this.oldNames.getSelectionModel().getSelectedItem();
  }

  /**
   * Update the display using the current picture
   */
  public void updateDisplay() {
    updatePictureViewer(this.picture);
  }
}
