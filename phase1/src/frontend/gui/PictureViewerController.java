package frontend.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

public class PictureViewerController extends Controller implements ChangeListener<Boolean>{

  private MainView mainView;
  private PictureViewer pictureViewer;
  private String nameSelected;

  public PictureViewerController(MainView mainView, PictureViewer viewer) {
    super(mainView, mainView.getBackendService());
    this.mainView = mainView;
    this.pictureViewer = viewer;
  }
//
//  public void addTag(ActionEvent e){
//    System.out.println("Add tag");
//
//    // Show the dialog
//    TextInputDialog dialog = new TextInputDialog("Specify new tag name here");
//    dialog.showAndWait();
//    String newTagName = dialog.getEditor().getText();
//
//    Tag newTag = new Tag(newTagName);
//    viewer.getPicture().addTag(newTag);
//  }
//
//  public void deleteTag(ActionEvent e){
//    System.out.println("Deleted tag");
//  }
//
//  public void renameTag(ActionEvent e){
//    System.out.println("Renamed tag");
//
//    RenameInputDialog renameDialog = new RenameInputDialog();
//    renameDialog.showAndWait();
//
//    String oldTagName = renameDialog.getOldName();
//    String newTagName = renameDialog.getNewName();
//
//    // See if the old tag name exists
//    boolean isTagToReplaceValid = false;
//    for (Tag tag : viewer.getPicture().getTags()){
//      if (tag.getLabel().equals(oldTagName)){
//        tag.setLabel(newTagName);
//        isTagToReplaceValid = true;
//        break;
//      }
//    }
//
//    // Alert the user if the tag name to replace is not valid
//    if (!isTagToReplaceValid){
//      Alert alert = new Alert(AlertType.ERROR);
//      alert.setContentText("The tag to replace is not valid!\nPlease try again.");
//      alert.show();
//    }
//  }

  
  public List<String> getHistoricalNames(){
    List<String> names = new ArrayList<String>();
    
    for (String name : this.pictureViewer.getPicture().getHistoricalTaglessNames()){
      names.add(name);
    }
    
    return names;
  }
  
  public void changeName(ActionEvent e) {
    if(this.nameSelected != null) {
      this.mainView.getBackendService().rename(this.pictureViewer.getPicture(), this.nameSelected);
      this.mainView.getListViewController().setItems(this.mainView.getBackendService().getPictureManager().getPictures());
    }
  }
  
  public void setNameSelected(ActionEvent e) {
    this.nameSelected = this.pictureViewer.getOldNameSelected();
  }


  @Override
  public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
      Boolean newValue) {
   this.pictureViewer.showHideTags(newValue);
   this.getMainView().getListView().requestFocus();  // put focus back to ListView
  }
}
