package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
  private TextArea tags;
  private TextField newTagTextField;
  private ComboBox<Tag> addTag;
  
  public PictureViewer(MainView mainView) {
    this.controller = new PictureViewerController(mainView, this);
    this.mainView = mainView;
    this.createComponenets();
  }

  private void createComponenets() {
    this.pictureName = new Label();
    this.pictureName.setFont(Font.font("Verdana", 20));
    this.pictureName.setPadding(new Insets(0, 0, 5, 0));
   
    HBox nameControls = new HBox();
    oldNames = new ComboBox<String>();
    oldNames.setPadding(new Insets(0, 10, 0, 0));
    oldNames.setOnAction(this.controller::setNameSelected);

    Button changeName = new Button("Change Name");
    changeName.setOnAction(this.controller::changeName);
    
    nameControls.getChildren().addAll(oldNames, changeName);

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
    tags.setPrefHeight(MainView.HEIGHT / 8);
    tagControls.setTop(tags);
    
    Label createTags = new Label("Create Tag:");
    newTagTextField = new TextField ();
    newTagTextField.onActionProperty().set(this.controller::createNewTag);
    HBox hb = new HBox();
    
    Label addTagLabel = new Label("Add Tag");
    addTag = new ComboBox<Tag>();
    addTag.getItems().addAll(this.mainView.getBackendService().getPictureManager().getAvailableTags());
    addTag.setOnAction(this.controller::addTag);
    
    hb.getChildren().addAll(createTags, newTagTextField, addTagLabel, addTag);
    hb.setSpacing(10);
    
    
    tagControls.setBottom(hb);
    
    this.setBottom(tagControls);
    
    updatePictureViewer(null);  // starts with nothing shown
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
      
      this.oldNames.getItems().setAll(this.controller.getHistoricalNames());
      
      try {
        InputStream inputStream = new FileInputStream(picture.getAbsolutePath());

        BufferedImage bufferedImage = ImageIO.read(inputStream);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        this.imageView.setImage(image);
        inputStream.close();
        
        this.pictureName.setText(this.picture.getTaglessName());
        
        String tagText = "";
        for(Tag tag : this.picture.getTags()) {
          tagText += tag.getLabel() + " ";
        }
        
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
    return this.addTag.getSelectionModel().getSelectedItem();
  }
  
  public String getOldNameSelected() {
    return this.oldNames.getSelectionModel().getSelectedItem();
  }

}
