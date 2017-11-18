package backend.models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppSettings implements Serializable {

  private List<Picture> historicalPictures = new ArrayList<>();

  public void addHistoricalPictures(PictureManager manager){
    for (Picture picture : historicalPictures){
      for (Picture picture1 : manager.getPictures()){
        if (picture1.equals(picture)){
          manager.untrackPicture(picture1);
          manager.addPicture(picture);
        }
      }
    }
  }

  public void save(String fileName) throws IOException {
    OutputStream buffer = new BufferedOutputStream(new FileOutputStream(fileName));
    ObjectOutput output = new ObjectOutputStream(buffer);

    // serialize the Map
    output.writeObject(this);
    output.close();
  }

  public static AppSettings loadFromFile(String fileName) throws IOException, ClassNotFoundException {
    InputStream buffer = new BufferedInputStream(new FileInputStream(fileName));
    ObjectInput input = new ObjectInputStream(buffer);

    // Deserialize the app settings
    AppSettings settings = (AppSettings) input.readObject();
    input.close();

    return settings;
  }
}
