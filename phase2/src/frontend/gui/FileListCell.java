package frontend.gui;

import backend.models.Picture;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class defines how each cell in the ListView are displayed. In our program they are displayed
 * as the file name without tags. This class is also used to edit the cells, showing a text field.
 */
public class FileListCell extends ListCell<Picture> {

   /** The text field used for editing the cell */
   private TextField textField;

  /** The old name in the cell before an edit */
  private String oldName;
  
  private MainController mainController;
  
  public FileListCell(MainController mainController) {
    this.mainController = mainController;
  }

  @Override
  public void updateItem(Picture item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
    } else {
      setText(item.getTaglessName());
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();

    this.oldName = this.getText();

    this.setText(null); // remove display of text

    textField = new TextField(this.oldName); // initialize the text field
    textField.setOnKeyPressed(this::handleTextFieldInput); // add the handler

    this.setGraphic(textField); // display a text field

    textField.requestFocus(); // put focus on the text field
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setGraphic(null);
    this.setText(this.oldName);
  }

  /**
   * Handler for text field. Textfield data is used when enter key is pressed
   * 
   * @param keyEvent the key event
   */
  private void handleTextFieldInput(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();

    if (keyCode == KeyCode.ENTER) { // if the enter button is pressed
      String text = this.textField.getText(); // get the text

      if (text != "") {
        // if there is text update the cell and picture

//        Picture picture = this.getItem();
//        this.mainController.getBackendService().rename(picture, text);
        
//        System.out.println(mainController.getPictureViewController());
        mainController.getPictureViewController().rename(text);

        this.commitEdit(this.getItem());
      }
    } else if (keyCode == KeyCode.ESCAPE) { // if the esc key is pressed cancel the edit
      this.cancelEdit();
    }
  }
}
