package frontend.gui;

import backend.models.Picture;
import backend.models.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

public class AddTagWindow extends Dialog{

  // Obtained from https://docs.oracle.com/javafx/2/ui_controls/list-view.htm
  private ObservableList<String> resultsList = FXCollections.observableArrayList();

  public AddTagWindow(BackendService service, Picture currentPicture){
    super();

    this.setTitle("Add tag");
    this.setResizable(true);

    ListView listView = new ListView();
    listView.setItems(resultsList);

    TextField textbox = new TextField();
    textbox.setOnAction(this::onTextChanged);

    StackPane pane = new StackPane();
    pane.getChildren().add(listView);
    pane.getChildren().add(textbox);

    // Add the OK button (from http://code.makery.ch/blog/javafx-dialogs-official/)
    this.initStyle(StageStyle.UTILITY);
  }

  public void onTextChanged(ActionEvent e){
    
  }
}
