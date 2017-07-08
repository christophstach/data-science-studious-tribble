package edu.christophstach.datasciencestudioustribble.model.result;

import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;

import java.util.Date;
import java.util.List;

/**
 * Created by Christoph Stach on 7/8/17.
 * <p>
 * The result of a hashTag REST query.
 */
public class HashTagResult {

  private Date from;
  private Date to;
  private List<HashTagOccurrence> hashTagOccurrence;

  public HashTagResult(Date from, Date to, List<HashTagOccurrence> hashTagOccurrence) {
    this.from = from;
    this.to = to;
    this.hashTagOccurrence = hashTagOccurrence;
  }

  /**
   * Gets from
   *
   * @return value of from
   */
  public Date getFrom() {
    return from;
  }

  /**
   * Sets from
   *
   * @param from value for from
   */
  public void setFrom(Date from) {
    this.from = from;
  }

  /**
   * Gets to
   *
   * @return value of to
   */
  public Date getTo() {
    return to;
  }

  /**
   * Sets to
   *
   * @param to value for to
   */
  public void setTo(Date to) {
    this.to = to;
  }

  /**
   * Gets hashTagOccurrence
   *
   * @return value of hashTagOccurrence
   */
  public List<HashTagOccurrence> getHashTagOccurrence() {
    return hashTagOccurrence;
  }

  /**
   * Sets hashTagOccurrence
   *
   * @param hashTagOccurrence value for hashTagOccurrence
   */
  public void setHashTagOccurrence(List<HashTagOccurrence> hashTagOccurrence) {
    this.hashTagOccurrence = hashTagOccurrence;
  }
}

