package edu.christophstach.datasciencestudioustribble.controller;

import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import edu.christophstach.datasciencestudioustribble.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Christoph Stach on 5/21/17.
 * <p>
 * Serves the Twitter related requests
 */
@RestController
@CrossOrigin(origins = {"http://christoph-stach.de", "https://christophstach.github.io", "http://localhost:8081", "http://127.0.0.1:8081"})
public class TwitterController {
  private final static Logger logger = Logger.getLogger(TwitterController.class.getName());

  @Autowired
  private TweetRepository tweetRepository;

  @GetMapping("/twitter/most-used-hash-tags-all")
  public List<HashTagOccurrence> mostUsedHashTagsAll(
          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar from,
          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar to,
          @RequestParam(value = "count", required = false, defaultValue = "50") Integer count
  ) {
    List<HashTagOccurrence> result;
    final long timeStart = System.currentTimeMillis();
    if (from != null && to != null) {
      from.set(Calendar.HOUR, 0);
      from.set(Calendar.MINUTE, 0);
      from.set(Calendar.SECOND, 0);
      from.set(Calendar.MILLISECOND, 0);
      from.add(Calendar.HOUR, 24);
      to.set(Calendar.HOUR, 0);
      to.set(Calendar.MINUTE, 0);
      to.set(Calendar.SECOND, 0);
      to.set(Calendar.MILLISECOND, 0);
      to.add(Calendar.HOUR, 48);
      result = tweetRepository.getHashTagOccurrences(from.getTime(), to.getTime(), count);
    } else {
      result = tweetRepository.getHashTagOccurrences(null, null, count);
    }
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage 'most-used-hash-tags-all': %,d ms", (timeEnd - timeStart)));

    return result;
  }

  @GetMapping("/twitter/tweets-per-weekday")
  public int[] tweetsPerWeekday(
          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar from,
          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar to
  ) {
    int[] a;
    final long timeStart = System.currentTimeMillis();
    if (from != null && to != null) {
      from.set(Calendar.HOUR, 0);
      from.set(Calendar.MINUTE, 0);
      from.set(Calendar.SECOND, 0);
      from.set(Calendar.MILLISECOND, 0);
      from.add(Calendar.HOUR, 24);
      to.set(Calendar.HOUR, 0);
      to.set(Calendar.MINUTE, 0);
      to.set(Calendar.SECOND, 0);
      to.set(Calendar.MILLISECOND, 0);
      to.add(Calendar.HOUR, 48);
      a = tweetRepository.getTweetsPerWeekday(from.getTime(), to.getTime());
    } else {
      a = tweetRepository.getTweetsPerWeekday(null, null);
    }
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage 'tweets-per-weekday': %,d ms", (timeEnd - timeStart)));

    //Remapping of new array where the first element is monday
    return new int[]{a[1], a[2], a[3], a[4], a[5], a[6], a[0]};
  }

  @GetMapping("/twitter/tweets-per-hour")
  public int[] tweetsPerHour(
          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar from,
          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar to
  ) {
    int[] a;
    final long timeStart = System.currentTimeMillis();
    if (from != null && to != null) {
      from.set(Calendar.HOUR, 0);
      from.set(Calendar.MINUTE, 0);
      from.set(Calendar.SECOND, 0);
      from.set(Calendar.MILLISECOND, 0);
      from.add(Calendar.HOUR, 24);
      to.set(Calendar.HOUR, 0);
      to.set(Calendar.MINUTE, 0);
      to.set(Calendar.SECOND, 0);
      to.set(Calendar.MILLISECOND, 0);
      to.add(Calendar.HOUR, 48);
      a = tweetRepository.getTweetsPerHour(from.getTime(), to.getTime());
    } else {
      a = tweetRepository.getTweetsPerHour(null, null);
    }
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage 'tweets-per-hour': %,d ms", (timeEnd - timeStart)));

    return a;
  }

}
