package eu.ensup.compteservice.service;

public interface CompteService {
    void virement(Long source,Long dest, double montant );
}
