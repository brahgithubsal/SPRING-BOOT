package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.model.Client;
import tech.getarrays.employeemanager.model.Commande;
import tech.getarrays.employeemanager.model.ServiceModel;
import tech.getarrays.employeemanager.model.User;
import tech.getarrays.employeemanager.repo.CommandRepository;

import java.util.List;

@Service
public class CommandService {
    private final CommandRepository commandRepository;

    @Autowired
    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }
    public Commande placeOrder(User serviceUser, ServiceModel service, Client client) {
        // Créer et sauvegarder l'entité Commande
        Commande commande = new Commande(client, service);

        // Associer l'utilisateur (prestataire) au service
        commande.setUser(serviceUser);

        return commandRepository.save(commande);
    }


    public List<Commande> getCommandsByUserId(Long userId) {
        // Implement logic to retrieve commands by user ID from the repository
        return commandRepository.findByClientId(userId);
    }
    public List<Commande> getCommandsByServiceId(Long serviceId) {
        // Implémentez la logique pour récupérer les commandes associées au service
        return commandRepository.findByServiceId(serviceId);
    }}