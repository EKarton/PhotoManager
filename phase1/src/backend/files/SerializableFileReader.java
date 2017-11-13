package backend.files;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SerializableFileReader implements FileReader{

  /**
   * Returns a list of all data in the file
   * 
   * @param <T> the type of data stored in the file
   * 
   * @param path the path to the file
   * @return list of all data in the file
   * @throws ClassNotFoundException
   * @throws IOException
   */
  @Override
  public <T> ArrayList<T> readFile(String path) throws ClassNotFoundException, IOException {

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
