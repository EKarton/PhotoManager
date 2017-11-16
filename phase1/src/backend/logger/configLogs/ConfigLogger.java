package backend.logger.configLogs;

import java.io.IOException;
import backend.models.PictureManager;
import backend.files.SerializableFileWriter;


public class ConfigLogger extends SerializableFileWriter {
  private PictureManager theManager;

  public ConfigLogger() {}

  public void setBiDirectionalMap(PictureManager manager) {
    this.theManager = manager;
  }

  /**
   * write the mapRepo as our configuration file so we can use it to initialize the program when
   * opened.
   * 
   * @throws IOException
   */

  public void writeConfig() throws IOException {
    super.write("/config.log", false, this.theManager);
  }
}
