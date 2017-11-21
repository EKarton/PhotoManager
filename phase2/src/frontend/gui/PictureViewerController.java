package frontend.gui;

import backend.commands.AddTagToPictureCommand;
import backend.models.Picture;
import java.util.ArrayList;
import java.util.List;
import backend.models.Tag;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

public class PictureViewerController extends Controller implements ChangeListener<Boolean> {

  private MainView mainView;
  private PictureViewer pictureViewer;
  private String nameSelected;

  public PictureViewerController(MainView mainView, PictureViewer viewer) {
    super(mainView, mainView.getBackendService());
    this.mainView = mainView;
    this.pictureViewer = viewer;
  }


  public void deleteTag(ActionEvent e) {
    Tag tag = this.pictureViewer.getSelectedDeleteTag();
    if (tag != null) {
      this.getBackendService().getPictureManager().deleteTag(tag);
    }
    this.pictureViewer.updateDisplay();
  }

  public void removeTag(ActionEvent e) {
    Tag tag = this.pictureViewer.getSelectedRemoveTag();
    if (tag != null) {
      this.pictureViewer.getPicture().deleteTag(tag);
    }
    this.pictureViewer.updateDisplay();
  }

  public void createNewTag(ActionEvent e) {
    String text = this.pictureViewer.getNewTagText();

    if (text != "") {
      Tag tag = new Tag(text);
      this.mainView.getBackendService().getPictureManager().addTagToCollection(tag);
      this.pictureViewer.resetNewTagText();

      this.pictureViewer.updateDisplay();
    }
  }

  public void addTag(ActionEvent e) {
    Tag newTag = this.pictureViewer.getSelectedAddTag();

    if (newTag != null) {
      Picture picture = this.pictureViewer.getPicture();
      AddTagToPictureCommand cmd = new AddTagToPictureCommand(picture, newTag);
      this.mainView.getBackendService().getCommandManager().addCommand(cmd);
      cmd.execute();
    }

    this.pictureViewer.updateDisplay();
  }

  public List<String> getHistoricalNames() {
    List<String> names = new ArrayList<String>();

    for (String name : this.pictureViewer.getPicture().getHistoricalTaglessNames()) {
      names.add(name);
    }

    return names;
  }

  public void changeName(ActionEvent e) {
    if (this.nameSelected != null) {
      this.mainView.getBackendService().rename(this.pictureViewer.getPicture(), this.nameSelected);
      this.mainView.getListViewController()
          .setItems(this.mainView.getBackendService().getPictureManager().getPictures());
    }
  }

  public void setNameSelected(ActionEvent e) {
    this.nameSelected = this.pictureViewer.getOldNameSelected();
  }

  @Override
  public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
      Boolean newValue) {
    this.getMainView().getListView().requestFocus(); // put focus back to ListView
  }

  public void seeHistoricalTags(ActionEvent actionEvent) {
    String historicalTags_String = "";
    for (Tag t : this.pictureViewer.getPicture().getHistoricalTags()) {
      historicalTags_String += t.getLabel() + "\n";
    }
    TextDialog dialog = new TextDialog(historicalTags_String);
    dialog.show();
  }
}
