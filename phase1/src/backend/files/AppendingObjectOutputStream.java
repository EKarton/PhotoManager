package backend.files;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * This is an ObjectOutputStream that allows for objects to be appended to the end of a serial file
 * I found this idea here, https://stackoverflow.com/a/1195078
 */
public class AppendingObjectOutputStream extends ObjectOutputStream{

  public AppendingObjectOutputStream(OutputStream out) throws IOException {
    super(out);
  }

  @Override
  protected void writeStreamHeader() throws IOException{
    this.reset();
  }

}
