package edu.christophstach.datasciencestudioustribble.repository;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import edu.christophstach.datasciencestudioustribble.model.MapReduceKeyValue;
import edu.christophstach.datasciencestudioustribble.model.Tweet;
import edu.christophstach.datasciencestudioustribble.model.diagram.HashTagOccurrence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

  private String readJavaScript(String javaScriptFile) throws IOException {
    Path path = Paths.get(new ClassPathResource("javascript/" + javaScriptFile).getFile().getAbsolutePath());
    return Files.readAllLines(path)
            .stream()
            .reduce((concatenate, s) -> String.format("%s%n%s", concatenate, s))
            .orElseThrow(IOException::new);
  }

  @Override
  public List<HashTagOccurrence> getHashTagOccurrences() throws IOException {
    /*List<HashTagOccurrence> r = new LinkedList<>();
    MapReduceResults<HashTagOccurrence> results;
    MapReduceOptions options = new MapReduceOptions();
    options.outputTypeInline();

    String mapFn = readJavaScript("HashTagOccurrences.map.js");
    String reduceFn = readJavaScript("HashTagOccurrences.reduce.js");

    results = mongo.mapReduce(collectionName, mapFn, reduceFn, options, HashTagOccurrence.class);
    results.forEach(r::add);


    r.sort(Comparator.(HashTagOccurrence::getCount));
    r = r.subList(0, 50);
    return r;*/


    Aggregation aggregation;
    aggregation = newAggregation(
            match(Criteria.where("timestamp_ms_long").gte("5")),
            unwind("entities.hashtags"),
            group("entities.hashtags.text").count().as("count"),
            sort(Sort.Direction.DESC, "count"),
            limit(50)
    );

    return mongo.aggregate(aggregation, Tweet.class, HashTagOccurrence.class).getMappedResults();

  }

  @Override
  public int[] getTweetsPerHour() throws IOException {
    int[] r = new int[24];
    MapReduceResults<MapReduceKeyValue> results;
    MapReduceOptions options = new MapReduceOptions();
    options.outputTypeInline();

    String mapFn = readJavaScript("TweetsPerHour.map.js");
    String reduceFn = readJavaScript("TweetsPerHour.reduce.js");

    results = mongo.mapReduce(collectionName, mapFn, reduceFn, options, MapReduceKeyValue.class);
    results.forEach(mapReduceKeyValue -> r[mapReduceKeyValue.getKey()] = mapReduceKeyValue.getValue());

    return r;
  }

  @Override
  public int[] getTweetsPerWeekday() throws IOException {
    int[] r = new int[7];
    MapReduceResults<MapReduceKeyValue> results;
    MapReduceOptions options = new MapReduceOptions();
    options.outputTypeInline();

    String mapFn = readJavaScript("TweetsPerWeekday.map.js");
    String reduceFn = readJavaScript("TweetsPerWeekday.reduce.js");

    results = mongo.mapReduce(collectionName, mapFn, reduceFn, options, MapReduceKeyValue.class);
    results.forEach(mapReduceKeyValue -> r[mapReduceKeyValue.getKey()] = mapReduceKeyValue.getValue());

    return r;
  }
}
