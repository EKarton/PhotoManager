package backend.logger.configLogs;

import java.io.*;
import backend.MapRepository;
import backend.Picture;
import backend.Tag;

public class ConfigMap {
  private MapRepository<Picture, Tag> mapRepository;

  public ConfigMap() {}

  public void setMapRepository(MapRepository<Picture, Tag> mapRepository) {
    this.mapRepository = mapRepository;
  }
  
  /**
   *  write the mapRepo as our configuration file so we can use it to initialize the program when opened.
   */
  public void writeConig() {
    try {
      FileOutputStream config = new FileOutputStream("/config.log");
      ObjectOutputStream configFile = new ObjectOutputStream(config);
      configFile.writeObject(this.mapRepository);
      configFile.close();
      config.close();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
}
