package edu.christophstach.datasciencestudioustribble.repository;

import edu.christophstach.datasciencestudioustribble.model.Tweet;
import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


/**
 * Created by Christoph Stach on 5/24/17.
 * <p>
 * Implementation of the MongoDB Tweet repository
 */
@Repository
@Document(collection = "tweetsGeoBerlin")
public class TweetRepositoryImpl implements TweetRepositoryCustom {
  @Autowired
  private MongoTemplate mongo;

  @Override
  public List<HashTagOccurrence> getHashTagOccurrences() {
    Aggregation aggregation = newAggregation(
        unwind("entities.hashtags"),
        group("entities.hashtags.text").count().as("count"),
        sort(Sort.Direction.DESC, "count"),
        limit(50)
    );

    return mongo.aggregate(aggregation, Tweet.class, HashTagOccurrence.class).getMappedResults();
  }
}
