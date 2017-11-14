package backend.files;

import java.io.IOException;
import java.util.ArrayList;

public interface FileReader {
  
  /**
   * Returns a list of all data in the file
   * 
   * @param <T> the type of data stored in the file
   * 
   * @param path the relative path to the file
   * @return list of all data in the file
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public <T> ArrayList<T> readFile(String path) throws ClassNotFoundException, IOException;
}
