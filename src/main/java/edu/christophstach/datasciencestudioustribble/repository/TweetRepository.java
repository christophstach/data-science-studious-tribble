package edu.christophstach.datasciencestudioustribble.repository;

import edu.christophstach.datasciencestudioustribble.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Created by Christoph Stach on 5/21/17.
 * <p>
 * A tweet repository
 */
public interface TweetRepository extends MongoRepository<Tweet, String>, TweetRepositoryCustom {

}
