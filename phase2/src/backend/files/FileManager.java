package backend.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A static class that contains static methods used to manipulate files in the operating system.
 */
public class FileManager {

  /**
   * Returns a list of all image files under a directory (recursively up to depth).
   * 
   * @param directory the directory being searched under
   * @param depth the maximum number of directory levels to search
   * @return the list of files
   * @throws IOException
   */
  private static List<File> getImageList(String directory, int depth) throws IOException {
    List<File> files = Files.find(Paths.get(directory), depth,
        // make sure it is a file
        (filePath, fileAttr) -> fileAttr.isRegularFile()
            && fileIsImage(filePath.getFileName().toString())) // make sure file is an image
        .map(Path::toFile).collect(Collectors.toList()); // map each path to a file and it to a list

    return files;
  }

  /**
   * Returns a list of all files under a directory (recursively) that are images.
   * 
   * @param directory the directory being searched under
   * @return the list of files
   * @throws IOException
   */
  public static List<File> getImageListRec(String directory) throws IOException {
    return getImageList(directory, Integer.MAX_VALUE); // search with the maximum recursive depth
  }

  /**
   * Returns a list of all files directly under a directory (non-recursively) that are images.
   * 
   * @param directory the directory being searched under
   * @return the list of files
   * @throws IOException
   */
  public static List<File> getImageList(String directory) throws IOException {
    return getImageList(directory, 1); // search with depth 1, so no recursion
  }

  /**
   * Checks if this file is an image. This currently supports the extensions: png, jpg and jpeg.
   * 
   * @param fileName The name of the file, including its file extension.
   * @return true if the file is an image, false otherwise
   */
  private static boolean fileIsImage(String fileName) {
    return fileName.matches(".*\\.(png|jpe?g)");
  }

  /**
   * Deletes the file at a given path, if the file of the path exists in the OS.
   * 
   * @param path the path to the image deleted
   * @return true if the image was deleted correctly, false otherwise
   */
  public static boolean deleteFile(String path) {
    return deleteFile(new File(path));
  }

  /**
   * Deletes the given file if the file exists in the OS.
   * 
   * @param file A file object
   * @return true if the image was deleted correctly, false otherwise
   */
  public static boolean deleteFile(File file) {
    if (file.isFile()) {
      return file.delete();
    }
    return false;
  }

  /**
   * Returns the file extension of the file with the given path.
   * 
   * @param path the path of the file
   * @return the file extension of the file with the given path
   */
  private static String getFileExtension(String path) {
    return path.split("\\.")[1];
  }

  /**
   * Renames a file. It will rename the file if it exists in the OS.
   * 
   * @param path the path of the file
   * @param newName the new name of the file
   * @return true if the name was changed, false otherwise
   */
  public static boolean renameFile(String path, String newName) {
    File file = new File(path);

    if (file.isFile()) {

      File newFileDest = new File(file.getParent(), newName + "." + getFileExtension(path));

      return file.renameTo(newFileDest);
    }

    return false;
  }

  /**
   * Moves a file. It will move the file if the file exists on the OS.
   * 
   * @param path the path of the file
   * @param destination the path of the destination to move the file
   * @return true if the file was moved, false otherwise (this includes if the file was moved to the
   *         same place)
   */
  public static boolean moveFile(String path, String destination) {
    File file = new File(path);
    if (file.isFile() && !file.getParent().equals(destination)) {
      return file.renameTo(new File(destination, file.getName()));
    }
    return false;
  }
}
