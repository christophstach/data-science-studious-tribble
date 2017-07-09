package edu.christophstach.datasciencestudioustribble.controller;

import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import edu.christophstach.datasciencestudioustribble.model.result.HashTagResult;
import edu.christophstach.datasciencestudioustribble.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

  private List<String> excludedHashTags = Arrays.asList(
          "nowplaying", "ger", "berlin", "german", "brandenburg", "germany", "bbradio", "jobs", "dasauge", "repost",
          "schleswig", "rostock", "potsdam", "rockt", "kassel", "dassmacc", "schwerin", "bremerhaven", "stralsund", "youtube",
          "rocks", "koblenz", "hits", "bremen", "hro", "hst", "mecklenburg", "smaccforce", "radio", "mv", "webcam", "music",
          "mecklenburgvorpommern", "trndnl", "photo"
  );

  @Autowired
  private TweetRepository tweetRepository;

  @GetMapping("/twitter/most-used-hash-tags-all")
  public List<HashTagOccurrence> mostUsedHashTagsAll(
          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
          @RequestParam(value = "count", required = false, defaultValue = "50") Integer count
  ) {
    final long timeStart = System.currentTimeMillis();

    List<HashTagOccurrence> result = tweetRepository.getHashTagOccurrences(
            from,
            to
    );
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage 'most-used-hash-tags-all': %,d ms", (timeEnd - timeStart)));

    return result;
  }

  @GetMapping("/twitter/tweets-per-weekday")
  public int[] tweetsPerWeekday(
          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
  ) {
    final long timeStart = System.currentTimeMillis();
    int[] a = tweetRepository.getTweetsPerWeekday(
            from,
            to
    );
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage 'tweets-per-weekday': %,d ms", (timeEnd - timeStart)));

    //Remapping of new array where the first element is monday
    return new int[]{a[1], a[2], a[3], a[4], a[5], a[6], a[0]};
  }

  @GetMapping("/twitter/tweets-per-hour")
  public int[] tweetsPerHour(
          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
  ) {
    final long timeStart = System.currentTimeMillis();
    int[] a = tweetRepository.getTweetsPerHour(
            from,
            to
    );
    final long timeEnd = System.currentTimeMillis();

    logger.info(String.format(Locale.GERMANY, "Zeit der Anfrage 'tweets-per-hour': %,d ms", (timeEnd - timeStart)));

    return a;
  }

  @GetMapping("/twitter/weather")
  public void weather() {

  }

  @GetMapping("/twitter/relevant-hash-tags-daily")
  public List<HashTagResult> relevantHashTagsDaily() {
    List<HashTagResult> list = new LinkedList<>();
    Long days = ChronoUnit.DAYS.between(LocalDate.of(2017, Month.MAY, 18), LocalDate.now());

    for (int i = 0; i <= days; i++) {
      Instant from = LocalDate.now().minusDays(i).atTime(LocalTime.MIN).toInstant(ZoneOffset.ofHours(2));
      Instant to = LocalDate.now().minusDays(i).atTime(LocalTime.MAX).toInstant(ZoneOffset.ofHours(2));

      list.add(new HashTagResult(
              Date.from(from),
              Date.from(to),
              tweetRepository.getHashTagOccurrences(from, to, 10, excludedHashTags)
      ));
    }

    return list;
  }

  @GetMapping("/twitter/relevant-hash-tags-weekly")
  public List<HashTagResult> relevantHashTagsWeekly() {
    List<HashTagResult> list = new LinkedList<>();
    Long days = ChronoUnit.DAYS.between(LocalDate.of(2017, Month.MAY, 18), LocalDate.now(ZoneId.of("EuropeBerlin")));

    for (int i = 0; i <= days; i = i + 7) {
      LocalDate monday = LocalDate.now().minusDays(i);
      while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
        monday = monday.minusDays(1);
      }

      LocalDate sunday = LocalDate.now().minusDays(i);
      while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
        sunday = sunday.plusDays(1);
      }

      Instant from = monday.atTime(LocalTime.MIN).toInstant(ZoneOffset.ofHours(2));
      Instant to = sunday.atTime(LocalTime.MAX).toInstant(ZoneOffset.ofHours(2));

      list.add(new HashTagResult(
              Date.from(from),
              Date.from(to),
              tweetRepository.getHashTagOccurrences(from, to, 10, excludedHashTags)
      ));
    }

    return list;
  }


}
