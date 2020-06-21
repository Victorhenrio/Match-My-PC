package com.Match_My_PC.application;

import com.Match_My_PC.domain.composant.Composant;
import com.Match_My_PC.domain.composant.ComposantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")

public class ComposantController {

    private ComposantController composantController;

    public ComposantController(ComposantService composantService) {
        this.composantService = composantService;
    }

    @RequestMapping(value = "/pcs/{id}/composants", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Composant>> getComposants( @PathVariable(value = "id") Long pcId) {
        return new ResponseEntity<List<Composant>>(composantService.findComposantsByPCId(pcId), HttpStatus.OK);
    }

    @RequestMapping(value = "/pcs/{id}/composants", method = RequestMethod.POST)
    public ResponseEntity<Composant> createComposants(
            @PathVariable(value = "id") Long pcId,
            @RequestBody Composant composant)  {
        composant = composantService.addComposants(pcId, composant);
        return new ResponseEntity<>(composant, HttpStatus.CREATED);
    }
}
