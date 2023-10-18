package ba.edu.ibu.webengineering.core.repository;

import ba.edu.ibu.webengineering.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public final MongoTemplate mongoTemplate = null;
    @Aggregation(pipeline = """
        { $match: { _id: { $exists: true } } }
    """)
    public List<User> getAllUsersCustom();


    @Query(value="{email:'?0'}", fields="{'userId': 0, 'name' : 1, 'email' : 1, 'username': 1}")
    Optional<User> findUserByEmailCustom(String email);

    public Optional<User> findFirstByEmail(String email);

    public List<User> findUserByEmailAndTypeOrderByCreationDateDesc(String email);
}
