package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.getarrays.employeemanager.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}
