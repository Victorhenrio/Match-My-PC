package com.Match_My_PC.application;

import com.Match_My_PC.domain.PC;
import com.Match_My_PC.domain.PCService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(value = "afficher les PCS du catalogue")
public class Controller {

  private PCService pcService;
  private ObjectMapper objectMapper;

  public Controller(PCService pcService, ObjectMapper objectMapper) {
    this.pcService = pcService;
    this.objectMapper = objectMapper;
  }

 @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Successfully retrieved list"),
          @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})



  @RequestMapping(value = "/pcs", method = RequestMethod.GET)
  public ResponseEntity<List<PC>> getPCS() {
    return new ResponseEntity<>(pcService.getPCS(), HttpStatus.OK);
  }

  @ApiOperation("donne la liste des PCS")
  @RequestMapping(value = "/pcs/{id}", method = RequestMethod.GET)
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity<PC> getPCSById(@PathVariable(value = "id") Long id) {
    try {
      log.info("********************INSIDE THE CONTROLLER********************");
      return new ResponseEntity<>(pcService.getPCS(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PC Not Found", e);
    }
  }

  @RequestMapping(value = "/pcs", method = RequestMethod.POST)
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity<PC> createPCS(
      @RequestBody PC pc) {
    pc = pcService.addPC(pc);
    return new ResponseEntity<>(pc, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/pcs/{id}", method = RequestMethod.PUT)
  public ResponseEntity<PC> replacePCS(
      @PathVariable(value = "id") Long id,
      @RequestBody PC pc) {
    pc.setId(id);
    pcService.replacePC(pc);
    return new ResponseEntity<>(pc, HttpStatus.OK);
  }


  @RequestMapping(value = "/pcs/{id}", method = RequestMethod.DELETE)
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity<PC> deletePCS(@PathVariable(value = "id") Long id) {
    pcService.deletePCS(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/pcs/{id}", method = RequestMethod.PATCH, consumes = "application/json-patch+json")
  public ResponseEntity<String> patchPCS(
      @PathVariable(value = "id") Long id,
      @RequestBody JsonPatch patch)  {
    try {
      pcService.patchPCS(applyPatchToCustomer(patch, pcService.findPC(id)));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (JsonPatchException | JsonProcessingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private PC applyPatchToCustomer(JsonPatch patch, PC targetPC)
      throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(objectMapper.convertValue(targetPC, JsonNode.class));
    return objectMapper.treeToValue(patched, PC.class);
  }
}
