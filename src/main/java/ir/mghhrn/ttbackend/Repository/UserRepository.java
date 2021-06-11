package ir.mghhrn.ttbackend.Repository;

import ir.mghhrn.ttbackend.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCellphone(String cellphone);

    @EntityGraph(attributePaths = {"roles.permissions"})
    Optional<User> findUserById(Long userId);
}
