package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.employeemanager.model.Commande;

import java.util.List;

public interface CommandRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByClientId(Long clientId);
    List<Commande> findByServiceId(Long serviceId); // Ajoutez cette méthode pour rechercher les commandes par ID de service
}
