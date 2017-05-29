package edu.christophstach.datasciencestudioustribble.controller;

import edu.christophstach.datasciencestudioustribble.model.Tweet;
import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import edu.christophstach.datasciencestudioustribble.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Christoph Stach on 5/21/17.
 * <p>
 * Serves the Twitter related requests
 */
@RestController
@CrossOrigin(origins = {"http://christoph-stach.de", "https://christophstach.github.io"})
public class TwitterController {
  @Autowired
  private TweetRepository tweetRepository;


  @GetMapping("/twitter/most-used-hash-tags-all")
  public List<HashTagOccurrence> mostUsedHashTagsAll() {
    return tweetRepository.getHashTagOccurrences();
  }

  @GetMapping("/twitter/tweets-per-weekday")
  public int[] tweetsPerWeekday() {
    int[] a = new int[8];

    List<Tweet> tweets = tweetRepository.findAll();

    /*
    Wir können hier leider keine parallel Streams verweden.
    Ich muss erstmal rausfidnen wie ich das am besten mit parallel streams machen
    Benutzt man hier einfach parallelStream() bekommt man jedes mal ein anderes ergebnes
     */
    tweets.forEach(tweet -> {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(tweet.created_at);
      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

      a[dayOfWeek]++;
    });

    //Remapping of new array where the first element is not empty and that the week starts with monday
    return new int[]{a[2], a[3], a[4], a[5], a[6], a[7], a[1]};
  }

  @GetMapping("/twitter/tweets-per-hour")
  public int[] tweetsPerHour() {
    int[] a = new int[24];

    List<Tweet> tweets = tweetRepository.findAll();

    /*
    Wir können hier leider keine parallel Streams verweden.
    Ich muss erstmal rausfidnen wie ich das am besten mit parallel streams machen
    Benutzt man hier einfach parallelStream() bekommt man jedes mal ein anderes ergebnes
     */
    tweets.forEach(tweet -> {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(tweet.created_at);
      int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

      //Works correct, no remapping is needed, but I should test a bit more
      a[hourOfDay]++;
    });

    return a;
  }

}
