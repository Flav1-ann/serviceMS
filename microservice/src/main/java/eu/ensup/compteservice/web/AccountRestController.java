package eu.ensup.compteservice.web;

import eu.ensup.compteservice.dto.VirementRequestDTO;
import eu.ensup.compteservice.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {

    @Autowired
    private CompteService compteService;

    @PutMapping("/comptes/virement")
    public void virement(@RequestBody VirementRequestDTO requestDTO){

        compteService.virement(requestDTO.getCodeSource(), requestDTO.getCodeDestination(),requestDTO.getMontant());

    }
}
