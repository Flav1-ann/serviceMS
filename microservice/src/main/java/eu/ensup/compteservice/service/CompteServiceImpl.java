package eu.ensup.compteservice.service;

import eu.ensup.compteservice.domaine.Compte;
import eu.ensup.compteservice.repository.CompteRepository;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@ManagedResource(objectName = "fr.flavien.microservice:name=AccountServiceImpl")
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public void virement(Long source, Long dest, double montant) {
        Compte c1 = compteRepository.getById(source);
        Compte c2 = compteRepository.getById(dest);

        c1.setSolde(c1.getSolde() - montant);
        c2.setSolde(c2.getSolde() + montant);

        compteRepository.save(c1);
        compteRepository.save(c2);
    }
}
