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

  @GetMapping("/twitter")
  public List<Tweet> all() {
    return tweetRepository.findAll();
  }

  @CrossOrigin(origins = {"http://localhost:8081", "https://christophstach.github.io"})
  @GetMapping("/twitter/most-used-hash-tags-all")
  public List<HashTagOccurrence> mostUsedHashTagsAll() {
    return tweetRepository.getHashTagOccurrences();
    /*
    List<HashTagOccurrence> hashTagList = Collections.synchronizedList(new LinkedList<>());
    float sum = 0;



    tweetRepository
            .findAll()
            .parallelStream()
            .map(tweet -> tweet.entities.hashtags)
            .forEach(hashTags -> {

              hashTags.stream()
                      .map(hashTag -> hashTag.text)
                      //.map(String::toUpperCase)
                      .forEach(hashTag -> {
                        synchronized (hashTagList) {
                          boolean hashTagAlreadyExists = false;

                          for (HashTagOccurrence hashTagListItem : hashTagList) {
                            if (hashTagListItem.getText().equals(hashTag)) {
                              hashTagListItem.setCount(hashTagListItem.getCount() + 1);
                              hashTagAlreadyExists = true;
                              break;
                            }
                          }

                          if (!hashTagAlreadyExists) {
                            hashTagList.add(new HashTagOccurrence(hashTag, 0, 1));
                          }
                        }
                      });

            });


    for (HashTagOccurrence hashTag : hashTagList) {
      sum += hashTag.getCount();
    }

    for (HashTagOccurrence hashTag : hashTagList) {
      hashTag.setSize(Math.round(hashTag.getCount() / sum * 100));
    }

    Collections.sort(hashTagList);
    Collections.reverse(hashTagList);

    return hashTagList;
    */
  }

  @GetMapping("/twitter/most-used-hash-tags-only-re-tweets")
  public Map<String, Integer> mostUsedHashTagsOnlyReTweets() {
    Map<String, Integer> res = new HashMap<>();

    tweetRepository
            .findAll()
            .stream()
            .filter(tweet -> tweet.retweeted)
            .map(tweet -> tweet.entities.hashtags)
            .forEach(hashTags -> {

              hashTags.stream()
                      .map(hashTag -> hashTag.text)
                      .map(hashTag -> hashTag.toUpperCase())
                      .forEach(hashTag -> {
                        if (res.containsKey(hashTag)) {
                          res.put(hashTag, res.get(hashTag) + 1);
                        } else {
                          res.put(hashTag, 1);
                        }
                      });

            });

    Map<String, Integer> sorted = new TreeMap<>((a, b) -> {
      if (res.get(a) > res.get(b)) {
        return -1;
      } else {
        return 1;
      }
    });

    sorted.putAll(res);

    return sorted;
  }

  @GetMapping("/twitter/most-used-hash-tags-without-re-tweets")
  public Map<String, Integer> mostUsedHashTagsWithoutReTweets() {
    Map<String, Integer> res = new HashMap<>();

    tweetRepository
            .findAll()
            .stream()
            .filter(tweet -> !tweet.retweeted)
            .map(tweet -> tweet.entities.hashtags)
            .forEach(hashTags -> {

              hashTags.stream()
                      .map(hashTag -> hashTag.text)
                      .map(hashTag -> hashTag.toUpperCase())
                      .forEach(hashTag -> {
                        if (res.containsKey(hashTag)) {
                          res.put(hashTag, res.get(hashTag) + 1);
                        } else {
                          res.put(hashTag, 1);
                        }
                      });

            });

    Map<String, Integer> sorted = new TreeMap<>((a, b) -> {
      if (res.get(a) > res.get(b)) {
        return -1;
      } else {
        return 1;
      }
    });

    sorted.putAll(res);

    return sorted;
  }

  @GetMapping("/twitter/most-used-languages-all")
  public Map<String, Integer> mostUsedLanguagesAll() {
    Map<String, Integer> res = new HashMap<>();

    tweetRepository
            .findAll()
            .stream()
            .map(tweet -> tweet.lang)
            .forEach(lang -> {
              if (res.containsKey(lang)) {
                res.put(lang, res.get(lang) + 1);
              } else {
                res.put(lang, 1);
              }
            });

    Map<String, Integer> sorted = new TreeMap<>((a, b) -> {
      if (res.get(a) > res.get(b)) {
        return -1;
      } else {
        return 1;
      }
    });

    sorted.putAll(res);

    return sorted;
  }

}
