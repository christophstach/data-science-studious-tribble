package edu.christophstach.datasciencestudioustribble.repository;

import edu.christophstach.datasciencestudioustribble.javascript.MapReduceOperation;
import edu.christophstach.datasciencestudioustribble.javascript.TweetsPerHour;
import edu.christophstach.datasciencestudioustribble.javascript.TweetsPerWeekday;
import edu.christophstach.datasciencestudioustribble.model.MapReduceKeyValue;
import edu.christophstach.datasciencestudioustribble.model.Tweet;
import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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
  public List<HashTagOccurrence> getHashTagOccurrences(Instant from, Instant to) {
    return getHashTagOccurrences(from, to, 50, null);
  }

  @Override
  public List<HashTagOccurrence> getHashTagOccurrences(Instant from, Instant to, int count, List<String> excludes) {
    Aggregation aggregation;

    excludes = excludes != null ? excludes : new ArrayList<>();


    MatchOperation match = match(where("created_at_date").gte(from).lt(to));
    UnwindOperation unwind = unwind("entities.hashtags");
    ProjectionOperation project = project("entities.hashtags.text").and("entities.hashtags.text").toLower().as("text");
    MatchOperation notMatch = match(where("text").not().in(excludes));
    GroupOperation group = group("text").count().as("count");
    SortOperation sort = sort(Sort.Direction.DESC, "count");
    LimitOperation limit = limit(count);

    if (from != null && to != null) {
      aggregation = newAggregation(match, unwind, project, notMatch, group, sort, limit);
    } else {
      aggregation = newAggregation(unwind, project, notMatch, group, sort, limit);
    }

    return mongo.aggregate(aggregation, Tweet.class, HashTagOccurrence.class).getMappedResults();
  }

  @Override
  public int[] getTweetsPerHour(Instant from, Instant to) {
    int[] r = new int[24];
    MapReduceResults<MapReduceKeyValue> results;
    MapReduceOptions options = new MapReduceOptions().outputTypeInline();
    MapReduceOperation js = new TweetsPerHour();
    Query query = new Query();

    if (from != null && to != null) {
      query.addCriteria(
              where("created_at_date")
                      .gte(from)
                      .lt(to)
      );
    }

    results = mongo.mapReduce(query, collectionName, js.map(), js.reduce(), options, MapReduceKeyValue.class);
    results.forEach(mapReduceKeyValue -> r[mapReduceKeyValue.getKey()] = mapReduceKeyValue.getValue());

    return r;
  }

  @Override
  public int[] getTweetsPerWeekday(Instant from, Instant to) {
    int[] r = new int[7];
    MapReduceResults<MapReduceKeyValue> results;
    MapReduceOptions options = new MapReduceOptions().outputTypeInline();
    MapReduceOperation js = new TweetsPerWeekday();
    Query query = new Query();

    if (from != null && to != null) {
      query.addCriteria(
              where("created_at_date")
                      .gte(from)
                      .lt(to)
      );
    }

    results = mongo.mapReduce(query, collectionName, js.map(), js.reduce(), options, MapReduceKeyValue.class);
    results.forEach(mapReduceKeyValue -> r[mapReduceKeyValue.getKey()] = mapReduceKeyValue.getValue());

    return r;
  }
}
