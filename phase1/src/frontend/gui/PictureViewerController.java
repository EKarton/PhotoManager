package frontend.gui;

import backend.models.Tag;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class PictureViewerController extends Controller {

  private MainView mainView;
  private PictureViewer viewer;

  public PictureViewerController(MainView mainView, PictureViewer viewer) {
    super(mainView);
    this.mainView = mainView;
    this.viewer = viewer;
  }

  public void addTag(ActionEvent e){
    System.out.println("Add tag");

    // Show the dialog
    TextInputDialog dialog = new TextInputDialog("Specify new tag name here");
    dialog.showAndWait();
    String newTagName = dialog.getEditor().getText();

    Tag newTag = new Tag(newTagName);
    viewer.getPicture().addTag(newTag);
  }

  public void deleteTag(ActionEvent e){
    System.out.println("Deleted tag");
  }

  public void renameTag(ActionEvent e){
    System.out.println("Renamed tag");

    RenameInputDialog renameDialog = new RenameInputDialog();
    renameDialog.showAndWait();

    String oldTagName = renameDialog.getOldName();
    String newTagName = renameDialog.getNewName();

    // See if the old tag name exists
    boolean isTagToReplaceValid = false;
    for (Tag tag : viewer.getPicture().getTags()){
      if (tag.getLabel().equals(oldTagName)){
        tag.setLabel(newTagName);
        isTagToReplaceValid = true;
        break;
      }
    }

    // Alert the user if the tag name to replace is not valid
    if (!isTagToReplaceValid){
      Alert alert = new Alert(AlertType.ERROR);
      alert.setContentText("The tag to replace is not valid!\nPlease try again.");
      alert.show();
    }
  }

  public void viewHistoricalNames(ActionEvent e){
    System.out.println("View historical names");

    String historicalTagsText = "";
    for (String name : viewer.getPicture().getHistoricalTaglessNames())
      historicalTagsText += name + "\n";

    TextDialog window = new TextDialog(historicalTagsText);
    window.setTitle("Historical names");
    window.setHeaderText("Complete historical names for this picture.");
    window.show();
  }

  public void viewHistoricalTags(ActionEvent e){
    System.out.println("View historical tags");

    String historicalTagsText = "";
    for (Tag tag : viewer.getPicture().getHistoricalTags())
      historicalTagsText += tag.getLabel() + "\n";

    TextDialog window = new TextDialog(historicalTagsText);
    window.setTitle("Historical tags");
    window.setHeaderText("Complete historical tags for this picture.");
    window.show();
  }
}
