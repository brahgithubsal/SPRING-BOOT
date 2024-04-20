package tech.getarrays.employeemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.model.*;
import tech.getarrays.employeemanager.service.CommandService;
import tech.getarrays.employeemanager.service.ClientService;
import tech.getarrays.employeemanager.service.ServiceService;
import tech.getarrays.employeemanager.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/commands")
public class CommandController {

    private final CommandService commandService;
    private final ClientService clientService;
    private final ServiceService serviceService;
    private final UserService userService;

    @Autowired
    public CommandController(CommandService commandService, ClientService clientService, ServiceService serviceService, UserService userService) {
        this.commandService = commandService;
        this.clientService = clientService;
        this.serviceService = serviceService;
        this.userService = userService;
    }

    @PostMapping("/place-order")
    public ResponseEntity<Commande> placeOrder(@RequestBody PlaceOrderRequest request, HttpSession session) {
        // Récupérer l'utilisateur (prestataire) associé au service
        ServiceModel service = serviceService.findServiceById(request.getServiceId());
        User serviceUser = service.getUser();

        // Vérifier si l'utilisateur associé au service existe
        if (serviceUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Récupérer le client de la session
        Client currentClient = (Client) session.getAttribute("user");

        // Vérifier si le client est connecté
        if (currentClient == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Placer la commande en utilisant l'utilisateur associé au service
        Commande newCommand = commandService.placeOrder(serviceUser, service, currentClient);

        // Retourner la commande créée
        return ResponseEntity.ok(newCommand);
    }

    @GetMapping("/user-commands")
    public ResponseEntity<List<Commande>> getUserCommands(HttpSession session) {
        // Récupérer l'utilisateur (prestataire) à partir de la session
        User currentUser = (User) session.getAttribute("user");

        // Vérifier si l'utilisateur est connecté
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Vérifier si l'utilisateur est un prestataire

        // Récupérer les services publiés par le prestataire
        List<ServiceModel> services = serviceService.findServicesByUserId(currentUser.getId());

        // Initialiser une liste pour stocker les commandes associées
        List<Commande> userCommands = new ArrayList<>();
//bech tara kol userid chm9ablo service ou itaycha fil liste commande
        // Parcourir les services et récupérer les commandes associées à chaque service
        for (ServiceModel service : services) {
            List<Commande> commandsForService = commandService.getCommandsByServiceId(service.getId());
            userCommands.addAll(commandsForService);
        }

        // Retourner les commandes associées aux services du prestataire
        return ResponseEntity.ok(userCommands);
    }
}