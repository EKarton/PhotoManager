package frontend.gui;

import backend.models.Picture;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Handles callback for list view to add a context menu
 */
public class FileListViewCallback implements Callback<ListView<Picture>, ListCell<Picture>> {

  /**
   * The context menu. This is a menu for renaming and moving the files
   */
  private ContextMenu contextMenu;
  
  /** The list view controller*/
  private FileListViewController controller;

  /**
   * Constructs a FileListViewCallback
   * 
   * @param controller the list view's controller
   * @param contextMenu the context menu
   */
  public FileListViewCallback(FileListViewController controller, ContextMenu contextMenu) {
    this.contextMenu = contextMenu;
    this.controller = controller;
  }

  @Override
  public ListCell<Picture> call(ListView<Picture> param) {
    ListCell<Picture> cell = new FileListCell(this.controller);
    cell.setContextMenu(this.contextMenu);
    return cell;
  }

}
