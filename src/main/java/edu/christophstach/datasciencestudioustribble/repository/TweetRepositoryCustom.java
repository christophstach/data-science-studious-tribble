package edu.christophstach.datasciencestudioustribble.repository;

import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;

import java.io.IOException;
import java.util.List;

/**
 * Created by Christoph Stach on 5/24/17.
 * <p>
 * Custom methods for the Tweet repository
 */
public interface TweetRepositoryCustom {
  /**
   * Returns a list of how often hash tags occurred in tweets
   *
   * @return The list of has tag occurrences
   */
  public List<HashTagOccurrence> getHashTagOccurrences() throws IOException;

  /**
   * Returns tweets per hour
   *
   * @return An array of tweets per hour
   */
  public int[] getTweetsPerHour() throws IOException;

  /**
   * Returns tweets per weekday
   * @return An array of tweets per hour
   */
  public int[] getTweetsPerWeekday() throws IOException;
}
