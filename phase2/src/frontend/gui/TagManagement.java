package frontend.gui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import backend.models.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TagManagement {
  private Stage window;
  private MainController mainController;

  @FXML
  ListView<Tag> tagListView;

  public TagManagement(String title, MainController mainController) {
    this.window = new Stage();

    this.window.setTitle(title);

    this.mainController = mainController;

    // this window has to be exited to go back to other program
    this.window.initModality(Modality.APPLICATION_MODAL);

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("TagManagement.fxml"));
      loader.setController(this);
      Parent root = loader.load();

      this.window.setTitle(title);

      Scene scene = new Scene(root);
      this.window.setScene(scene);

      setUpComponents();

    } catch (IOException e) {
      // other wise dialog fails to load
    }

  }

  private void setUpComponents() {
    this.tagListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.tagListView.getItems()
        .setAll(this.mainController.getBackendService().getPictureManager().getAvailableTags());
  }

  private void updateTagList() {
    this.tagListView.getItems()
        .setAll(this.mainController.getBackendService().getPictureManager().getAvailableTags());
  }

  public void addTag() {
    // Code derived from http://o7planning.org/en/11537/javafx-textinputdialog-tutorial
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Add Tag");
    dialog.setHeaderText("Add Tag");
    dialog.setContentText("Name of tag:");

    Optional<String> result = dialog.showAndWait();

    if (result.isPresent()) {
      Tag tag = new Tag(result.get());
      this.mainController.getBackendService().getPictureManager().addTagToCollection(tag);
      updateTagList();
    }
  }

  public void deleteTag() {
     List<Tag> selectedTags = this.tagListView.getSelectionModel().getSelectedItems();
    
     for (Tag tag : selectedTags) {
     this.mainController.getBackendService().getPictureManager().deleteTag(tag);
     }
    updateTagList();
  }

  public void show() {
    this.window.show();
  }
}
