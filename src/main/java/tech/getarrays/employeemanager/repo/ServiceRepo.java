package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.employeemanager.model.ServiceModel;

import java.util.List;

public interface ServiceRepo extends JpaRepository<ServiceModel, Long> {
    List<ServiceModel> findByUserId(Long userId);

}
