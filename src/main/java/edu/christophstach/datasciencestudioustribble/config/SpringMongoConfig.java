package edu.christophstach.datasciencestudioustribble.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created by Christoph Stach on 5/21/17.
 *
 * The configuration for the mongoDB
 */
@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration{
  @Override
  protected String getDatabaseName() {
    return "twitter";
  }

  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient();
  }
}
