package frontend.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import backend.models.Picture;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
  private CheckBox showTags;

  public PictureViewer(MainView mainView) {
    this.controller = new PictureViewerController(mainView, this);
    this.mainView = mainView;
    this.createComponenets();
  }

  private void createComponenets() {
    this.pictureName = new Label();
    this.pictureName.setFont(Font.font("Verdana", 20));
    this.pictureName.setPadding(new Insets(0, 0, 5, 0));

    showTags = new CheckBox("Show Tags");
    showTags.selectedProperty().addListener(this.controller);

    BorderPane title = new BorderPane();
    title.setPrefWidth(3 * (MainView.WIDTH / 4));
    title.setLeft(pictureName);
    title.setRight(showTags);

    this.setTop(title);

    this.imageView = new ImageView();
    this.imageView.setPreserveRatio(true); // this lets us nicely scale the image

    // https://stackoverflow.com/a/37013902
    StackPane imageContainer = new StackPane(imageView);
    this.imageView.fitWidthProperty().bind(imageContainer.widthProperty());
    this.imageView.fitHeightProperty().bind(imageContainer.heightProperty());

    this.setCenter(imageContainer);
    
    updatePictureViewer(null);  // starts with nothing shown
  }

  public void updatePictureViewer(Picture newPicture) {
    this.picture = newPicture;

    if (this.picture == null) {
      this.setVisible(false);
    } else {
      this.setVisible(true);
      
      showTags.setSelected(false);  // set back to default
      
      try {
        Image image = new Image(new FileInputStream(this.picture.getAbsolutePath()));
        this.imageView.setImage(image);
        this.pictureName.setText(this.picture.getTaglessName());
      } catch (FileNotFoundException e) {
        this.picture = null;
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

}
