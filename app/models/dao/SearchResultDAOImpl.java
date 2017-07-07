package models.dao;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import models.SearchResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import play.Logger;
import services.db.MongoDBService;

import java.util.List;

/**
 * Created by Pasquale on 11/05/2017.
 */
public class SearchResultDAOImpl extends BasicDAO<SearchResult,ObjectId> implements SearchResultDAO{


    public SearchResultDAOImpl( Class<SearchResult> entityClass, Datastore ds ) {
        super(entityClass, ds);
    }

    @Override
    public SearchResult get(String id) {
        Logger.info("Search result Id:" + id);
        return this.get(new ObjectId(id));
    }

    @Override
    public List<SearchResult> findByUsername(String username) {
        return MongoDBService.getDatastore().createQuery(SearchResult.class).filter("username = ", username).asList();
    }

    @Override
    public List<SearchResult> findByKeywords(String keywords) {
        return MongoDBService.getDatastore().createQuery(SearchResult.class)
                .search("keywords")
                .order("date")
                .asList();
    }

    @Override
    public void saveAll(List<SearchResult> searchResults) {
        searchResults.stream().forEach( l -> MongoDBService.getDatastore().save(l));
    }
}
