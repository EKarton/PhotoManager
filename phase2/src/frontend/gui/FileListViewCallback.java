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

  /** The main controller, the controller of the main view where this ListView is displayed */
  private MainController mainController;

  /**
   * Constructs a FileListViewCallback
   * 
   * @param contextMenu the context menu
   */
  public FileListViewCallback(ContextMenu contextMenu, MainController mainController) {
    this.contextMenu = contextMenu;
    this.mainController = mainController;
  }

  /**
   * Sets the context menu to this cell
   */
  @Override
  public ListCell<Picture> call(ListView<Picture> param) {
    ListCell<Picture> cell = new FileListCell(this.mainController);
    cell.setContextMenu(this.contextMenu);
    return cell;
  }

}
