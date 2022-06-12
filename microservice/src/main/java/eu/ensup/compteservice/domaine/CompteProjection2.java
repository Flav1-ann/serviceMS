package eu.ensup.compteservice.domaine;

import eu.ensup.compteservice.enums.TypeCompte;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="p2", types = Compte.class)
public interface CompteProjection2 {
     Long getCode();
     TypeCompte getType();
}
