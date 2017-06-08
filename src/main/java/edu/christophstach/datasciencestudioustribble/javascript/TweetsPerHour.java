package edu.christophstach.datasciencestudioustribble.javascript;

/**
 * Created by Christoph Stach on 6/8/17.
 * <p>
 * Tweets per Hour
 */
public class TweetsPerHour implements MapReduceOperation {
  @Override
  public String map() {
    return "" +
            "function map() {\n" +
            "    var date = new Date(this.created_at);\n" +
            "    emit(date.getHours(), 1);\n" +
            "}";
  }

  @Override
  public String reduce() {
    return "" +
            "function reduce(key, values) {\n" +
            "    return Array.sum(values);\n" +
            "}";
  }
}
