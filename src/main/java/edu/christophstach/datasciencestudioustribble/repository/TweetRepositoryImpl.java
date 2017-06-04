package edu.christophstach.datasciencestudioustribble.repository;

import edu.christophstach.datasciencestudioustribble.model.MapReduceKeyValue;
import edu.christophstach.datasciencestudioustribble.model.Tweet;
import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


/**
 * Created by Christoph Stach on 5/24/17.
 * <p>
 * Implementation of the MongoDB Tweet repository
 */
@Repository
public class TweetRepositoryImpl implements TweetRepositoryCustom {
  @Value("${collection-name}")
  private String collectionName;

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

  @Override
  public int[] getTweetsPerHour() {
    int[] r = new int[24];
    MapReduceResults<MapReduceKeyValue> results;
    MapReduceOptions options = new MapReduceOptions();
    options.outputTypeInline();

    String mapFn = "/* map function */" +
            "function() {" +
            "  var date = new Date(this.created_at);" +
            "  emit(date.getHours(), 1);" +
            "}";

    String reduceFn = "/* reduce function */" +
            "function(key, values) {" +
            "  return Array.sum(values);" +
            "}";

    results = mongo.mapReduce(collectionName, mapFn, reduceFn, options, MapReduceKeyValue.class);
    results.forEach(mapReduceKeyValue -> r[mapReduceKeyValue.getKey()] = mapReduceKeyValue.getValue());

    return r;
  }

  @Override
  public int[] getTweetsPerWeekday() {
    int[] r = new int[7];
    MapReduceResults<MapReduceKeyValue> results;
    MapReduceOptions options = new MapReduceOptions();
    options.outputTypeInline();

    String mapFn = "/* map function */" +
            "function() {" +
            "  var date = new Date(this.created_at);" +
            "  emit(date.getDay(), 1);" +
            "}";

    String reduceFn = "/* reduce function */" +
            "function(key, values) {" +
            "  return Array.sum(values);" +
            "}";

    results = mongo.mapReduce(collectionName, mapFn, reduceFn, options, MapReduceKeyValue.class);
    results.forEach(mapReduceKeyValue -> r[mapReduceKeyValue.getKey()] = mapReduceKeyValue.getValue());

    return r;
  }
}
