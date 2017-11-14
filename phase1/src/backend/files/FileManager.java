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
   * Returns a list of all files under a directory (recursively)
   * @param directory The directory being searched under
   * @return the list of files
   * @throws IOException
   */
  public List<File> getFileList(String directory) throws IOException{

    List<File> files = Files.find(Paths.get(directory), Integer.MAX_VALUE,
            (filePath, fileAttr) -> fileAttr.isRegularFile() && fileIsImage(filePath.getFileName().toString()))
            .map(Path::toFile)
            .collect(Collectors.toList());

     return files;
  }
  
  /**
   * Checks if this file is an image
   * @param fileName
   * @return
   */
  private boolean fileIsImage(String fileName) {
    return fileName.matches(".*\\.(png|jpe?g)");
  }
  
  /**
   * Deletes the file at a given path
   * @param path the path to the image deleted
   * @return true if the image was deleted correctly, false otherwise
   */
  public boolean deleteFile(String path) {
    File file = new File(path);
    
    if(file.isFile()) {
      return file.delete();
    }
    
    return false;
  }
  
  /**
   * Returns the file extension of the file with the given path
   * @param path the path of the file
   * @return the file extension of the file with the gien path
   */
  private String getFileExtension(String path) {
    return path.split("\\.")[1];
  }
  
  /**
   * Renames a file
   * @param path the path of the file
   * @param newName the new name of the file
   * @return true if the name was changed, false otherwise
   */
  public boolean renameFile(String path, String newName) {
    File file = new File(path);

    if(file.isFile()) {
      return file.renameTo(new File(file.getParent() + "/" + newName + "." + getFileExtension(path)));
    }
    
    return false;
  }
  
  /**
   * Moves a file
   * @param path the path of the file
   * @param destination the path of the destination to move the file
   * @return true if the file was moved, false otherwise
   */
  public boolean moveFile(String path, String destination) {
    File file = new File(path);
    
    if(file.isFile()) {
      return file.renameTo(new File(destination, file.getName()));
    }
    
    return false;
  }
}
