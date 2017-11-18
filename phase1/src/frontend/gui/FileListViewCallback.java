package frontend.gui;

import backend.models.Picture;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FileListViewCallback implements Callback<ListView<Picture>, ListCell<Picture>> {
  
  private ContextMenu contextMenu;
  
  public FileListViewCallback(ContextMenu contextMenu) {
    this.contextMenu = contextMenu;
  }
  
  @Override
  public ListCell<Picture> call(ListView<Picture> param) {
    ListCell<Picture> cell = new FileListCell();
    cell.setContextMenu(this.contextMenu);
    return cell;
  }

}
