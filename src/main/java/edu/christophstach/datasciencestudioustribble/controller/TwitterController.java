package edu.christophstach.datasciencestudioustribble.controller;

import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import edu.christophstach.datasciencestudioustribble.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Christoph Stach on 5/21/17.
 * <p>
 * Serves the Twitter related requests
 */
@RestController
@CrossOrigin(origins = {"http://christoph-stach.de", "https://christophstach.github.io"})
public class TwitterController {
  private final static Logger logger = Logger.getLogger(TwitterController.class.getName());

  @Autowired
  private TweetRepository tweetRepository;

  @GetMapping("/twitter/most-used-hash-tags-all")
  public List<HashTagOccurrence> mostUsedHashTagsAll() {
    return tweetRepository.getHashTagOccurrences();
  }

  @GetMapping("/twitter/tweets-per-weekday")
  public int[] tweetsPerWeekday() {
    final long timeStart = System.currentTimeMillis();
    int[] a = tweetRepository.getTweetsPerWeekday();
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage: %,d ms", (timeEnd - timeStart)));

    //Remapping of new array where the first element is monday
    return new int[]{a[1], a[2], a[3], a[4], a[5], a[6], a[0]};
  }

  @GetMapping("/twitter/tweets-per-hour")
  public int[] tweetsPerHour() {
    final long timeStart = System.currentTimeMillis();
    int[] a = tweetRepository.getTweetsPerHour();
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage: %,d ms", (timeEnd - timeStart)));

    return a;
  }

}
