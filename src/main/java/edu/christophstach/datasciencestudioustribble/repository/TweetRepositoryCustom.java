package edu.christophstach.datasciencestudioustribble.repository;

import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;

import java.time.Instant;
import java.util.Date;
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
  public List<HashTagOccurrence> getHashTagOccurrences(Instant from, Instant to);

  /**
   * Returns a list of how often hash tags occurred in tweets
   *
   * @return The list of has tag occurrences
   */
  public List<HashTagOccurrence> getHashTagOccurrences(Instant from, Instant to, int count);

  /**
   * Returns tweets per hour
   *
   * @return An array of tweets per hour
   */
  public int[] getTweetsPerHour(Instant from, Instant to);

  /**
   * Returns tweets per weekday
   *
   * @return An array of tweets per hour
   */
  public int[] getTweetsPerWeekday(Instant from, Instant to);
}
