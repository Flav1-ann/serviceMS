package eu.ensup.compteservice.web;

import eu.ensup.compteservice.domaine.Compte;
import eu.ensup.compteservice.repository.CompteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController

public class CompteRestController {

    private final CompteRepository compteRepository;

    public CompteRestController(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

//    @GetMapping(path = "/comptes")
//    public List<Compte> listComptes()
//    {
//        return compteRepository.findAll();
//    }
//
//    @GetMapping(path = "/comptes/{id}")
//    public Compte getCompte(@PathVariable Long id)
//    {
//        return compteRepository.findById(id).get();
//    }
//
//    @PostMapping(path = "/comptes")
//    public Compte save(@RequestBody Compte compte){
//        return compteRepository.save(compte);
//    }
//
//    @PutMapping(path = "/comptes/{code}")
//    public Compte update(@PathVariable Long code,@RequestBody Compte compte){
//        compte.setCode(code);
//        return compteRepository.save(compte);
//    }
//
//    @DeleteMapping(path = "/comptes/{code}")
//    public void delete(@PathVariable Long code){
//        compteRepository.deleteById(code);
//    }


}
