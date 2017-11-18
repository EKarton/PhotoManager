package frontend.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RenameInputDialog extends Alert {

  private TextField leftTextField;
  private TextField rightTextField;

  public RenameInputDialog(){
    super(AlertType.INFORMATION);

    leftTextField = new TextField();
    rightTextField = new TextField();

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(leftTextField, 0, 0);
    expContent.add(rightTextField, 1, 0);

    // Set expandable Exception into the dialog pane.
    this.getDialogPane().setContent(expContent);
  }

  public String getOldName(){
    return leftTextField.getText();
  }

  public String getNewName(){
    return rightTextField.getText();
  }
}
