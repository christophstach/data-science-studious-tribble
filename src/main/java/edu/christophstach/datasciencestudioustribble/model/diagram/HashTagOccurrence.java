package edu.christophstach.datasciencestudioustribble.model.diagram;

import org.springframework.data.annotation.Id;

/**
 * Created by Christoph Stach on 5/22/17.
 * <p>
 * A HashTagOccurrence for the word cloud
 */
public class HashTagOccurrence {
  @Id
  private String text;
  private int count;

  /**
   * Gets text
   *
   * @return value of text
   */
  public String getText() {
    return text;
  }

  /**
   * Sets text
   *
   * @param text value for text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Gets count
   *
   * @return value of count
   */
  public int getCount() {
    return count;
  }

  /**
   * Sets count
   *
   * @param count value for count
   */
  public void setCount(int count) {
    this.count = count;
  }

  /**
   * Gets weight
   *
   * @return value of wegiht
   */
  public int getWeight() {
    return count;
  }
}
