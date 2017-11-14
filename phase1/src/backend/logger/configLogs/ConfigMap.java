package backend.logger.configLogs;

import java.io.*;
import backend.MapRepository;
import backend.Picture;
import backend.Tag;
import backend.files.SerializableFileWriter;

public class ConfigMap extends SerializableFileWriter {
  private MapRepository<Picture, Tag> mapRepository;

  public ConfigMap() {}

  public void setMapRepository(MapRepository<Picture, Tag> mapRepository) {
    this.mapRepository = mapRepository;
  }

  /**
   * write the mapRepo as our configuration file so we can use it to initialize the program when
   * opened.
   * 
   * @throws IOException
   */
  public void writeConfig() throws IOException {
    super.write("/config.log", false, this.mapRepository);
  }
}
