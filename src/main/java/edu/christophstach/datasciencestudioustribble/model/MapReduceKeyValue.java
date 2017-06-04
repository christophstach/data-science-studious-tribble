package edu.christophstach.datasciencestudioustribble.model;

import org.springframework.data.annotation.Id;

/**
 * Created by Christoph Stach on 6/4/17.
 * <p>
 * key value result from a map reduce query
 */
public class MapReduceKeyValue {
  @Id
  private int key;
  private int value;

  /**
   * Gets key
   *
   * @return value of key
   */
  public int getKey() {
    return key;
  }

  /**
   * Sets key
   *
   * @param key value for key
   */
  public void setKey(int key) {
    this.key = key;
  }

  /**
   * Gets value
   *
   * @return value of value
   */
  public int getValue() {
    return value;
  }

  /**
   * Sets value
   *
   * @param value value for value
   */
  public void setValue(int value) {
    this.value = value;
  }
}
