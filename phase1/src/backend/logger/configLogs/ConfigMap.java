package backend.logger.configLogs;

import java.io.*;

import backend.BiDirectionalMap;
import backend.Picture;
import backend.Tag;

public class ConfigMap {
  private BiDirectionalMap<Picture, Tag> biDirectionalMap;

  public ConfigMap() {}

  public void setBiDirectionalMap(BiDirectionalMap<Picture, Tag> biDirectionalMap) {
    this.biDirectionalMap = biDirectionalMap;
  }
  
  /**
   *  write the mapRepo as our configuration file so we can use it to initialize the program when opened.
   */
  public void writeConig() {
    try {
      FileOutputStream config = new FileOutputStream("/config.log");
      ObjectOutputStream configFile = new ObjectOutputStream(config);
      configFile.writeObject(this.biDirectionalMap);
      configFile.close();
      config.close();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
}
