package backend.logger.configLogs;

import java.io.*;

import backend.BiDirectionalMap;
import backend.Picture;
import backend.Tag;
import backend.files.SerializableFileWriter;


public class ConfigMap extends SerializableFileWriter {
  private BiDirectionalMap<Picture, Tag> biDirectionalMap;

  public ConfigMap() {}

  public void setBiDirectionalMap(BiDirectionalMap<Picture, Tag> biDirectionalMap) {
    this.biDirectionalMap = biDirectionalMap;
  }

  /**
   * write the mapRepo as our configuration file so we can use it to initialize the program when
   * opened.
   * 
   * @throws IOException
   */

  public void writeConfig() throws IOException {
    super.write("/config.log", false, this.biDirectionalMap);

  }
}
