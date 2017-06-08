package edu.christophstach.datasciencestudioustribble.javascript;

/**
 * Created by Christoph Stach on 6/8/17.
 * <p>
 * A map Reduce interface
 */
public interface MapReduceOperation {
  /**
   * Returns a javascript map function for mongodb
   *
   * @return The map function
   */
  public String map();

  /**
   * Returns a javascript reduce function for mongodb
   *
   * @return The map function
   */
  public String reduce();
}
