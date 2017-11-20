package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import backend.models.Picture;
import backend.models.Tag;
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

public class PictureViewer extends BorderPane {
  private Label pictureName;
  private ImageView imageView;
  private PictureViewerController controller;
  private Picture picture;
  private MainView mainView;
  private ComboBox<String> oldNames;
  private Button seeHistoricalTagsBttn;
  private TextArea tags;
  private TextField newTagTextField;
  private ComboBox<Tag> addTag;
  private ComboBox<Tag> removeTagSelect;
  private ComboBox<Tag> deleteTagSelect;
  private HBox nameControls;
  private HBox tagControlBox;

  
  public PictureViewer(MainView mainView) {
    this.controller = new PictureViewerController(mainView, this);
    this.mainView = mainView;
    this.createComponenets();
  }

  private void createComponenets() {
    this.pictureName = new Label();
    this.pictureName.setFont(Font.font("Verdana", 20));
    this.pictureName.setPadding(new Insets(0, 0, 5, 0));
   
    nameControls = new HBox();

    seeHistoricalTagsBttn = new Button("See historical tags");
    seeHistoricalTagsBttn.setOnAction(this.controller::seeHistoricalTags);

    oldNames = new ComboBox<String>();
    oldNames.setPadding(new Insets(0, 10, 0, 0));
    oldNames.setOnAction(this.controller::setNameSelected);

    Button changeName = new Button("Change Name");
    changeName.setOnAction(this.controller::changeName);
    
    nameControls.getChildren().addAll(seeHistoricalTagsBttn, oldNames, changeName);

    BorderPane title = new BorderPane();
    title.setPrefWidth(3 * (MainView.WIDTH / 4));
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
    
    BorderPane tagControls = new BorderPane();
    tags = new TextArea();
    tags.setEditable(false);
    tags.setPrefWidth(3 * (MainView.WIDTH / 4));
    tags.setPrefHeight(50);
    tagControls.setTop(tags);
    
    Label createTags = new Label("Create Tag:");
    newTagTextField = new TextField ();
    newTagTextField.onActionProperty().set(this.controller::createNewTag);

    tagControlBox = new HBox();
    
    Label addTagLabel = new Label("Add Tag");
    addTag = new ComboBox<Tag>();
    addTag.getItems().addAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
    addTag.setOnAction(this.controller::addTag);
    
    Label deleteTag = new Label("Delete Tag from Manager");
    this.deleteTagSelect = new ComboBox<Tag>();
    deleteTagSelect.getItems().addAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
    deleteTagSelect.setOnAction(this.controller::deleteTag);
    
    Label removeTag = new Label("Remove Tag from Image");
    this.removeTagSelect = new ComboBox<Tag>();
    this.removeTagSelect.setOnAction(this.controller::removeTag);


    tagControlBox.getChildren().addAll(createTags, newTagTextField, deleteTag, deleteTagSelect, removeTag, removeTagSelect, addTagLabel, addTag);
    tagControlBox.setSpacing(10);
    
    tagControls.setBottom(tagControlBox);
    
    this.setBottom(tagControls);
    
    updatePictureViewer(null);  // starts with nothing shown
  }
  
  public Tag getSelectedRemoveTag() {
    int index = this.removeTagSelect.getSelectionModel().getSelectedIndex();
    if (index == -1 || index >= this.picture.getTags().size())
      return null;
    return this.picture.getTags().get(index);
  }
  
  public Tag getSelectedDeleteTag() {

    int index = this.deleteTagSelect.getSelectionModel().getSelectedIndex();
    if (index == -1 || index >= this.mainView.getBackendService().getPictureManager().getAvailableTags().size())
      return null;
    return this.mainView.getBackendService().getPictureManager().getAvailableTags().get(index);
  }
  
  public String getNewTagText() {
    return this.newTagTextField.getText();
  }
  
  public void resetNewTagText() {
    this.newTagTextField.setText("");
  }

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
        //this.deleteTagSelect.setSelectionModel(null);
        //this.deleteTagSelect.getItems().clear();
        //this.deleteTagSelect.getItems().setAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
        this.deleteTagSelect.setItems(FXCollections.observableArrayList(this.mainView.getBackendService().getPictureManager().getAvailableTags()));

        // Display the tags for only this picture
        //this.removeTagSelect.setSelectionModel(null);
        //this.removeTagSelect.getItems().clear();
        this.removeTagSelect.setItems(FXCollections.observableArrayList(this.picture.getTags()));
        //this.removeTagSelect.getItems().setAll(this.picture.getTags());

        // Display the tags
        String tagsString = "";
        for (Tag tag : picture.getTags())
          tagsString += " " + tag.getLabel();
        this.tags.setText(tagsString);

        // Update the combo box with tags not in the picture
        List<Tag> tagsNotOnPic = new ArrayList<Tag>();
        List<Tag> availableTags = this.mainView.getBackendService().getPictureManager().getAvailableTags();
        for (Tag availTag : availableTags) {
          if (!picture.containsTag(availTag)) {
            tagsNotOnPic.add(availTag);
          }
        }

        addTag.setItems(FXCollections.observableArrayList(this.mainView.getBackendService().getPictureManager().getAvailableTags()));
        //addTag.getItems().setAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
        
      } catch (FileNotFoundException e) {
        this.picture = null;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void showHideTags(boolean showTags) {
    if (showTags) {
      this.pictureName.setText(this.picture.getTaggedName());
    } else {
      this.pictureName.setText(this.picture.getTaglessName());
    }
  }
  
  public Picture getPicture() {
    return this.picture;
  }
  
  public Tag getSelectedAddTag() {
    int index = this.addTag.getSelectionModel().getSelectedIndex();
    if (index == -1 || index >= this.mainView.getBackendService().getPictureManager().getAvailableTags().size())
      return null;
    return this.mainView.getBackendService().getPictureManager().getAvailableTags().get(index);
  }
  
  public String getOldNameSelected() {
    return this.oldNames.getSelectionModel().getSelectedItem();
  }

  public void updateDisplay(){
    updatePictureViewer(this.picture);
  }
}
