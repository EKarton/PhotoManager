package frontend.gui;

import java.io.File;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FileListViewCallback implements Callback<ListView<File>, ListCell<File>> {
  
  private ContextMenu contextMenu;
  
  public FileListViewCallback(ContextMenu contextMenu) {
    this.contextMenu = contextMenu;
  }
  
  @Override
  public ListCell<File> call(ListView<File> param) {
    ListCell<File> cell = new FileListCell();
    cell.setContextMenu(this.contextMenu);
    return cell;
  }

}
