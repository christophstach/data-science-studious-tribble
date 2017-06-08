package edu.christophstach.datasciencestudioustribble.javascript;

/**
 * Created by Christoph Stach on 6/8/17.
 * <p>
 * Hash tag occurrences
 * Is pretty slow, we can better use the mongo aggregation framework
 */
public class HashTagOccurrences implements MapReduceOperation{
  @Override
  public String map() {
    return "" +
            "function map() {\n" +
            "    for (var i = 0; i < this.entities.hashtags.length; i++) {\n" +
            "        emit(this.entities.hashtags[i].text, 1);\n" +
            "    }\n" +
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
