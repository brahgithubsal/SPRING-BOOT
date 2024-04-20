package tech.getarrays.employeemanager.resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.model.ServiceModel;
import tech.getarrays.employeemanager.model.User;
import tech.getarrays.employeemanager.service.ServiceService;
import tech.getarrays.employeemanager.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceResource {
    private final ServiceService serviceService;
    private final UserService userService; // Inject UserService

    public ServiceResource(ServiceService serviceService,UserService userService) {
        this.serviceService = serviceService;
        this.userService = userService; // Initialize UserService

    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceModel>> getAllServices() {
        List<ServiceModel> services = serviceService.findAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ServiceModel> getServiceById(@PathVariable("id") Long id) {
        ServiceModel service = serviceService.findServiceById(id);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ServiceModel> addService(@RequestBody ServiceModel service, HttpSession session) {
        // Retrieve the currently logged-in user from the session
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            // If user is not logged in, return an error response
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Set the current user for the service
        service.setUser(currentUser);

        // Add the service
        ServiceModel newService = serviceService.addService(service);

        return new ResponseEntity<>(newService, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<ServiceModel> updateService(@RequestBody ServiceModel service) {
        ServiceModel updatedService = serviceService.updateService(service);
        return new ResponseEntity<>(updatedService, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id) {
        serviceService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Function to get services for the currently logged-in user
    @GetMapping("/user-services")
    public ResponseEntity<List<ServiceModel>> getServicesPerUser(HttpSession session) {
        // Retrieve the currently logged-in user from the session
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            // If user is not logged in, return an error response
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Retrieve the services for the current user
        List<ServiceModel> userServices = serviceService.findServicesByUserId(currentUser.getId());
        return new ResponseEntity<>(userServices, HttpStatus.OK);
    }

    // Dummy method to simulate retrieving the currently logged-in user ID
    private Long getCurrentUserId() {
        // Replace this with your actual implementation to retrieve user ID
        // For demonstration, let's return a dummy user ID
        return 1L;
    }
}
