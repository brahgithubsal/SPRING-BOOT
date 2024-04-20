
package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.employeemanager.model.User;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by email
    User findByEmail(String email);
    User findBySessionId(String sessionId);


}