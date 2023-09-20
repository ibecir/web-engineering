package ba.edu.ibu.webengineering.core.repository;

import ba.edu.ibu.webengineering.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public final MongoTemplate mongoTemplate = null;
    @Aggregation(pipeline = """
        { $match: { _id: { $exists: true } } }
    """)
    public List<User> getAllUsersCustom();
}
