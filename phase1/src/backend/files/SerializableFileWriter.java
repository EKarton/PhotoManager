package backend.files;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SerializableFileWriter implements FileWriter {

  /**
   * This creates the ObjectOutput that will be used for writing data to the serial file. If the
   * file already exists and the function caller wants to append to it an
   * AppendingObjectOutputStream is returned. Otherwise an ObjectOutputStream is returned.
   * 
   * @param path the relative path to the file
   * @param append true if you want to append to the file, false otherwise
   * @return the ObjectOutput to be used for writing serial data to the file
   * @throws IOException
   */
  private ObjectOutput createObjectOutput(String path, boolean append) throws IOException {
    path = new File(path).getCanonicalPath();  // gets the absolute path from the relative path
    
    // this is true when the file already exists and we want to append to it
    boolean canAppend = new File(path).exists() && append;
    
    OutputStream file = new FileOutputStream(path, append);
    OutputStream buffer = new BufferedOutputStream(file);

    ObjectOutput output;
    
    if (canAppend) {  // if we can append we will
      output = new AppendingObjectOutputStream(buffer);
    } else {
      output = new ObjectOutputStream(buffer);
    }

    return output;
  }

  @Override
  public <T> void write(String path, boolean append, T data) throws IOException {
    ObjectOutput output = this.createObjectOutput(path, append);

    output.writeObject(data); // serialize the object and write it to the file

    output.close(); // close the stream
  }

  @Override
  public <T> void writeAll(String path, boolean append, ArrayList<T> dataCollection)
      throws IOException {
    ObjectOutput output = createObjectOutput(path, append);

    for (T data : dataCollection) {
      output.writeObject(data); // serialize the object and write it to the file
    }

    output.close(); // close the stream
  }

}
