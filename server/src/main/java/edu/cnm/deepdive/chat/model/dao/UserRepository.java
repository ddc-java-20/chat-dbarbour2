package edu.cnm.deepdive.chat.model.dao;

import edu.cnm.deepdive.chat.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOathKey(String oathKey);

  Optional<User> findByExternalKey (UUID externalKey);

  @Query("SELECT u FROM User AS u ORDER BY u.joined ASC")
  List<User> getAllByOrderByJoinedAsc();
}
