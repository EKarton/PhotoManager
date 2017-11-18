package frontend.gui;

import backend.models.Picture;
import backend.models.Tag;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javax.imageio.ImageIO;

// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
public class PictureViewer extends BorderPane {

  private Picture picture;

  private Label title;
  private ImageView imageView;
  private TagsViewer tagsViewer;
  private PictureViewerController controller;
  private MainView mainView;

  public PictureViewer(MainView mainView){
    this.mainView = mainView;
    this.picture = null;
    initilizeComponents();
  }

  public PictureViewer(MainView mainView, Picture picture){
    this.mainView = mainView;
    this.picture = picture;
    initilizeComponents();
    renderPicture();
  }

  private void initilizeComponents(){
    controller = new PictureViewerController(mainView, this);

    this.setMaxHeight(mainView.HEIGHT);

    title = new Label();
    title.setFont(Font.font ("Verdana", 10));
    title.setPadding(new Insets(0, 0, 5, 0));

    // https://stackoverflow.com/questions/37010192/imageview-always-scaling-beyond-scene-size-cant-seem-to-force-it-to-fit

    imageView = new ImageView();
    imageView.setFitHeight(this.mainView.HEIGHT);
    imageView.setFitWidth((3 * this.mainView.WIDTH) / 4);
    imageView.setPreserveRatio(true); // this lets us nicely scale the image

    StackPane imageContainer = new StackPane(imageView);
    imageView.fitWidthProperty().bind(imageContainer.widthProperty());
    imageView.fitHeightProperty().bind(imageContainer.heightProperty());

    tagsViewer = new TagsViewer();

    this.setTop(title);
    this.setCenter(imageContainer);
    this.setBottom(tagsViewer);
  }

  public Picture getPicture() {
    return picture;
  }

  public void setPicture(Picture picture) {
    this.picture = picture;
    renderPicture();
  }

  public void renderPicture(){
    if (picture == null)
      return;

    tagsViewer.rerenderTags();

    title.setText(picture.getAbsolutePath());

    try {
      InputStream inputStream = new FileInputStream(picture.getAbsolutePath());

      BufferedImage bufferedImage = ImageIO.read(inputStream);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      imageView.setImage(image);

      inputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class TagsViewer extends Pane{

    private VBox outerBox;
    private HBox tagsInnerBox;
    private HBox tagToolsBox;

    private Button addTagButton;
    private Button deleteTagButton;
    private Button renameTagButton;

    private Button viewHistoricalTagsButton;
    private Button viewHistoricalNamesButton;

    public TagsViewer(){
      super();

      this.outerBox = new VBox();
      this.tagsInnerBox = new HBox();
      this.tagToolsBox = new HBox();
      this.addTagButton = new Button("Add Tag");
      this.deleteTagButton = new Button("Delete Tag");
      this.renameTagButton = new Button("Rename Tag");
      this.viewHistoricalTagsButton = new Button("View Historical Tags");
      this.viewHistoricalNamesButton = new Button("View Historical Names");

      this.addTagButton.setOnAction(controller::addTag);
      this.deleteTagButton.setOnAction(controller::deleteTag);
      this.renameTagButton.setOnAction(controller::renameTag);
      this.viewHistoricalTagsButton.setOnAction(controller::viewHistoricalTags);
      this.viewHistoricalNamesButton.setOnAction(controller::viewHistoricalNames);

      this.tagToolsBox.getChildren().add(addTagButton);
      this.tagToolsBox.getChildren().add(deleteTagButton);
      this.tagToolsBox.getChildren().add(renameTagButton);
      this.tagToolsBox.getChildren().add(viewHistoricalTagsButton);
      this.tagToolsBox.getChildren().add(viewHistoricalNamesButton);

      rerenderTags();

      this.outerBox.getChildren().addAll(tagsInnerBox, tagToolsBox);

      this.getChildren().add(outerBox);
    }

    public void rerenderTags(){

      if (picture == null)
        return;

      tagsInnerBox.getChildren().clear();
      for (Tag tag : picture.getTags()){
        Label newLabel = new Label(tag.getLabel());
        tagsInnerBox.getChildren().add(newLabel);
      }
    }
  }
}
