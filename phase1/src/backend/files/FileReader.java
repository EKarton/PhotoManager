package backend.files;

import java.io.IOException;
import java.util.ArrayList;

public interface FileReader {
  public <T> ArrayList<T> readFile(String path) throws ClassNotFoundException, IOException;
}
