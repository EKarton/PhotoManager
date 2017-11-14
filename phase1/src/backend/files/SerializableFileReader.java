package backend.files;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SerializableFileReader implements FileReader{

  @Override
  public <T> ArrayList<T> readFile(String path) throws ClassNotFoundException, IOException {
    
    path = new File(path).getCanonicalPath();  // gets the absolute path from the relative path

    InputStream file = new FileInputStream(path);
    InputStream buffer = new BufferedInputStream(file);
    ObjectInput input = new ObjectInputStream(buffer);

    ArrayList<T> allData = new ArrayList<T>();

    Object data;
    while (true) {
      try {
        data = input.readObject();
      } catch (EOFException eofe) {
        break;
      }

      allData.add((T) data);
    }

    input.close();

    return allData;
  }

}
