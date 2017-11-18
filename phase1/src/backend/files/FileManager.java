package backend.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

  /**
   * Returns a list of all files under a directory (recursively up to depth)
   * 
   * @param directory The directory being searched under
   * @param depth the maximum number of directory levels to search
   * @return the list of files
   * @throws IOException
   */
  private List<File> getImageList(String directory, int depth) throws IOException {
    List<File> files = Files
        .find(Paths.get(directory), depth,
            (filePath, fileAttr) -> fileAttr.isRegularFile()
                && fileIsImage(filePath.getFileName().toString()))
        .map(Path::toFile).collect(Collectors.toList());

    return files;
  }

  /**
   * Returns a list of all files under a directory (recursively) that are images
   * 
   * @param directory The directory being searched under
   * @return the list of files
   * @throws IOException
   */
  public List<File> getImageListRec(String directory) throws IOException {
    return getImageList(directory, Integer.MAX_VALUE);
  }

  /**
   * Returns a list of all files directly under a directory (non-recursively) that are images
   * 
   * @param directory The directory being searched under
   * @return the list of files
   * @throws IOException
   */
  public List<File> getImageList(String directory) throws IOException {
    return getImageList(directory, 1);
  }

  /**
   * Checks if this file is an image
   * 
   * @param fileName
   * @return
   */
  private boolean fileIsImage(String fileName) {
    return fileName.matches(".*\\.(png|jpe?g)");
  }

  /**
   * Deletes the file at a given path
   * 
   * @param path the path to the image deleted
   * @return true if the image was deleted correctly, false otherwise
   */
  public boolean deleteFile(String path) {
    return deleteFile(new File(path));
  }

  public boolean deleteFile(File file) {
    if (file.isFile()) {
      return file.delete();
    }

    return false;
  }

  /**
   * Returns the file extension of the file with the given path
   * 
   * @param path the path of the file
   * @return the file extension of the file with the gien path
   */
  private String getFileExtension(String path) {
    return path.split("\\.")[1];
  }

  /**
   * Renames a file
   * 
   * @param path the path of the file
   * @param newName the new name of the file
   * @return true if the name was changed, false otherwise
   */
  public boolean renameFile(String path, String newName) {
    return renameFile(new File(path), newName);
  }

  public boolean renameFile(File file, String newName) {
    if (file.isFile()) {
      return file.renameTo(this.getRenamedFile(file, newName));
    }

    return false;
  }

  /**
   * Returns a new file which is <code>file</code> but with its name changed to <code>newName</code>
   * 
   * @param file
   * @param newName
   * @return
   */
  public File getRenamedFile(File file, String newName) {
    return new File(file.getParent(), newName + "." + getFileExtension(file.getAbsolutePath()));
  }

  /**
   * Moves a file
   * 
   * @param path the path of the file
   * @param destination the path of the destination to move the file
   * @return true if the file was moved, false otherwise (this includes if the file was moved to the
   *         same place)
   */
  public boolean moveFile(String path, String destination) {
    return moveFile(new File(path), destination);
  }

  /**
   * Moves the given file to given directory
   * 
   * @param file
   * @param destination
   * @return false if trying to move a file to its current location
   */
  public boolean moveFile(File file, String destination) {
    /*
     * if the user is trying to move it to the same place we simply won't move it and return false.
     * This is because a return false of true means the file was "moved", as in changed it's
     * location
     */
    if (file.isFile() && !file.getParent().equals(destination)) {
      return file.renameTo(new File(destination, file.getName()));
    }

    return false;
  }
}
