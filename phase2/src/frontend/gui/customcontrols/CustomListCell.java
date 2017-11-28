package frontend.gui.customcontrols;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class defines how each cell in the ListView is displayed. In our program they are displayed
 * as the file name without tags. This class is also used to edit the cells, showing a text field.
 * 
 * @param <T>
 */
public class CustomListCell<T> extends ListCell<T> {

  /** The text field used for editing the cell */
  private TextField textField;

  /** The old name in the cell before an edit */
  private String oldName;

  /** The object that will be renamed if this cell is renamed */
  private Renamable renamable;

  /**
   * Constructs a FileListCell
   * 
   * @param mainController the main controller
   * @param renamable the object that will be renamed if this cell is renamed
   */
  public CustomListCell(Renamable renamable) {
    this.renamable = renamable;
  }

  /**
   * Updates the name shown in the cell, to display the tagless name of the image
   */
  @Override
  public void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
    } else {
      setText(item.toString());
    }
  }

  /**
   * Removes the text display and displays a text field for taking input to rename the image
   */
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

  /**
   * Cancels the edit. Removes the text field and displays the name as before.
   */
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setGraphic(null);
    this.setText(this.oldName);
  }

  /**
   * Handler for text field. TextField data is used when enter key is pressed
   * 
   * @param keyEvent the key event
   */
  private void handleTextFieldInput(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();

    if (keyCode == KeyCode.ENTER) { // if the enter button is pressed
      String text = this.textField.getText(); // get the text

      if (text != "") {
        renamable.rename(text);

        this.commitEdit(this.getItem());
      }
    } else if (keyCode == KeyCode.ESCAPE) { // if the esc key is pressed cancel the edit
      this.cancelEdit();
    }
  }
}
