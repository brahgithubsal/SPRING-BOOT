package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.exception.ServiceNotFoundException;
import tech.getarrays.employeemanager.model.ServiceModel;
import tech.getarrays.employeemanager.repo.ServiceRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServiceService {
    private final ServiceRepo serviceRepo;

    @Autowired
    public ServiceService(ServiceRepo serviceRepo) {
        this.serviceRepo = serviceRepo;
    }

    public ServiceModel addService(ServiceModel service) {
        return serviceRepo.save(service);
    }

    public List<ServiceModel> findAllServices() {
        return serviceRepo.findAll();
    }

    public ServiceModel updateService(ServiceModel service) {
        return serviceRepo.save(service);
    }

    public ServiceModel findServiceById(Long id) {
        return serviceRepo.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service with id " + id + " was not found"));
    }

    public void deleteService(Long id) {
        serviceRepo.deleteById(id);
    }

    public List<ServiceModel> findServicesByUserId(Long userId) {
        return serviceRepo.findByUserId(userId);
    }

}
