package com.Match_My_PC.infrastructure;

import com.Match_My_PC.domain.pc.PC;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PCDao {

  private PCRepository pcRepository;
  private ComposantRepository composantRepository;

  public PCDao(PCRepository match_my_pcRepository, ComposantRepository composantRepository) {
    this.pcRepository = match_my_pcRepository;
    this.composantRepository = composantRepository;
  }

  public List<PC> findPCS() {
    return StreamSupport.stream(pcRepository.findAll().spliterator(), false)
        .map(pcEntitie -> buildPC(pcEntitie, composantRepository.findByPCEntity(pcEntitie)))
        .collect(Collectors.toList());
  }

  public PC findPCS(Long id) throws NotFoundException {
    PCEntity pcEntity = pcRepository.findById(id).orElseThrow(NotFoundException::new);
    return buildPC(pcEntity, composantRepository.findByPCEntity(pcEntity));
  }


  public PC createPCS(PC pc) throws NotFoundException {
    PCEntity pcEntity = pcRepository.save(buildPCEntity(pc));
    return buildPC(
        pcRepository.findById(pcEntity.getId()).orElseThrow(NotFoundException::new),
        composantRepository.findByPCEntity(pcEntity));
  }

  public void deletePCS(Long id) {
    pcRepository.delete(pcRepository.findById(id).get());
  }

  public PC updatePC(PC pc) {
    return buildPC(pcRepository.save(buildPCEntity(pc)),
      composantRepository.findByPCEntityId(pc.getId()));
  }

  public PC replacePC(PC pc) {
    PCEntity pcEntity = pcRepository.save(buildPCEntity(pc));
    return buildPC(pcEntity, composantRepository.findByPCEntity(pcEntity));
  }

  private PCEntity buildPCEntity(PC pc) {
    return PCEntity
        .builder()
        .id(pc.getId())
        .marque(pc.getMarque())
        .date_sortie(pc.getDate_sortie())
        .category(pc.getCategory())
        .build();
  }

  private PC buildPC(PCEntity pcEntity, List<ComposantEntity> composantEntities) {
    return PC.builder()
        .id(pcEntity.getId())
        .marque(pcEntity.getMarque())
        .date_sortie(pcEntity.getDate_sortie())
        .category(pcEntity.getCategory())
        .composants(composantEntities
            .stream()
            .map(composantEntity -> composantEntity.getId())
            .collect(Collectors.toList()))
        .build();
  }


}
