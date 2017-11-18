package backend.files;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Custom formatter that formats log messages without source and function name.
 * 
 * code adapted from : https://kodejava.org/how-do-i-create-a-custom-logger-formatter/
 * 
 */
public class LogFormatter extends Formatter {
  private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

  @Override
  public String format(LogRecord record) {
    StringBuilder builder = new StringBuilder();
    builder.append(df.format(new Date(record.getMillis())));
    builder.append(" : ");
    builder.append(formatMessage(record));
    builder.append("\n");
    return builder.toString();
  }

}
