package backend.logger;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This is an entry in the log
 */
public class Log implements Serializable{

  /** This is the time stamp */
  private String timeStamp;  
  
  public Log() {
    DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");  // the time stamp will be in this format
    Calendar calendar = Calendar.getInstance();
    this.timeStamp = format.format(calendar.getTime()); 
  }
  
  public String getTimeStamp() {
    return this.timeStamp;
  }
}
