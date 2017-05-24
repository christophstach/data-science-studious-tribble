package edu.christophstach.datasciencestudioustribble.controller;

import edu.christophstach.datasciencestudioustribble.model.Tweet;
import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import edu.christophstach.datasciencestudioustribble.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Christoph Stach on 5/21/17.
 * <p>
 * Serves the Twitter related requests
 */
@RestController
public class TwitterController {
  @Autowired
  private TweetRepository tweetRepository;

  @CrossOrigin(origins = {"http://localhost:8081", "https://christophstach.github.io"})
  @GetMapping("/twitter/most-used-hash-tags-all")
  public List<HashTagOccurrence> mostUsedHashTagsAll() {
    return tweetRepository.getHashTagOccurrences();

  }

  @GetMapping("/twitter/most-used-hash-tags-only-re-tweets")
  public Map<String, Integer> mostUsedHashTagsOnlyReTweets() {
    Map<String, Integer> res = new HashMap<>();

    return res;
  }

  @GetMapping("/twitter/most-used-hash-tags-without-re-tweets")
  public Map<String, Integer> mostUsedHashTagsWithoutReTweets() {
    Map<String, Integer> res = new HashMap<>();

    return res;
  }

  @GetMapping("/twitter/most-used-languages-all")
  public Map<String, Integer> mostUsedLanguagesAll() {
    Map<String, Integer> res = new HashMap<>();

    return res;
  }

}
