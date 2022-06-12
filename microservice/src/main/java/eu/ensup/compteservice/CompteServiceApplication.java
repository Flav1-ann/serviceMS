package eu.ensup.compteservice;

import eu.ensup.compteservice.config.PasswordConfig;
import eu.ensup.compteservice.domaine.Compte;
import eu.ensup.compteservice.domaine.User;
import eu.ensup.compteservice.enums.Roles;
import eu.ensup.compteservice.enums.TypeCompte;
import eu.ensup.compteservice.repository.CompteRepository;
import eu.ensup.compteservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class CompteServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CompteServiceApplication.class, args);

    }
    @Bean
    CommandLineRunner start(CompteRepository compteRepository, UserRepository userRepository, PasswordConfig passwordConfig, RepositoryRestConfiguration repositoryRestConfiguration){
        return args -> {

            userRepository.save(new User(null,"flavien", Roles.ADMIN,passwordConfig.passwordEncoder().encode("flavien")));
            userRepository.save(new User(null,"test", Roles.USER,passwordConfig.passwordEncoder().encode("test")));

            repositoryRestConfiguration.exposeIdsFor(Compte.class);
            compteRepository.save(new Compte(null,98000,new Date(), TypeCompte.COURANT));
            compteRepository.save(new Compte(null,2727,new Date(), TypeCompte.EPARGNE));
            compteRepository.save(new Compte(null,22222,new Date(), TypeCompte.COURANT));
            compteRepository.findAll().forEach(cp -> {
                System.out.println(cp.getType());
                System.out.println(cp.getSolde());
            });
        };
    }

}
