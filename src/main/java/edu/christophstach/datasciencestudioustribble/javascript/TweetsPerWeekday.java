package edu.christophstach.datasciencestudioustribble.javascript;

/**
 * Created by Christoph Stach on 6/8/17.
 * <p>
 * Tweets per weekday
 */
public class TweetsPerWeekday implements MapReduceOperation {
  @Override
  public String map() {
    return "" +
            "function map() {\n" +
            "    var date = new Date(this.created_at);\n" +
            "    emit(date.getDay(), 1);\n" +
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
