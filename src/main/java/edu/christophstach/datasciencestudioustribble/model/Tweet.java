package edu.christophstach.datasciencestudioustribble.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Christoph Stach on 5/21/17.
 * <p>
 * A tweet
 */
@Document(collection = "tweetsBerlinGeo")
public class Tweet {
  @Id
  public String mongoId;
  public BigInteger id;
  public String id_str;
  public Date created_at;
  public String text;
  public Entities entities;
  public boolean truncated;
  public boolean retweeted;
  public String lang;
  public String source;
  public String timestamp_ms;
}
