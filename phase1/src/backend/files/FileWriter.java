package backend.files;

import java.io.IOException;
import java.util.ArrayList;

public interface FileWriter {

  /**
   * Writes the given data to a file
   * 
   * @param path the relative path to the file
   * @param append true if you want to append to the file, false otherwise
   * @param data the data being written to the file
   * @throws IOException
   */
  public <T> void write(String path, boolean append, T data) throws IOException;
  
  /**
   * Writes a collection of data to a file, each entry of a list at a time
   * 
   * @param path the relative path of the file
   * @param append true if you want to append to the file, false otherwise
   * @param dataCollection the list of data being written to the file 
   * @throws IOException
   */
  public <T> void writeAll(String path, boolean append, ArrayList<T> dataCollection) throws IOException;
}
