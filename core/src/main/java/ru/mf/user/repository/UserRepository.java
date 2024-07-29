package ru.mf.user.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mf.user.model.User;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByFirstName(@Param("first_name") String firstName);

    List<User> findAll();
}
