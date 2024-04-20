package tech.getarrays.employeemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class ServiceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String prestataire;
    private String name;
    private String subcategorie;
    private String categorie;
    private double prix;
    private String imageUrl;

    @Column(nullable = false, updatable = false, unique = true)
    private String serviceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public ServiceModel() {
        this.serviceCode = UUID.randomUUID().toString();
    }

    public ServiceModel(String prestataire, String name, String subcategorie, String categorie, double prix, String imageUrl, User user) {
        this.prestataire = prestataire;
        this.name = name;
        this.subcategorie = subcategorie;
        this.categorie = categorie;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.serviceCode = UUID.randomUUID().toString();
        this.user = user;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(String prestataire) {
        this.prestataire = prestataire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubcategorie() {
        return subcategorie;
    }

    public void setSubcategorie(String subcategorie) {
        this.subcategorie = subcategorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
