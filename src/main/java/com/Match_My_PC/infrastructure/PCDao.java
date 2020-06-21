package com.Match_My_PC.infrastructure;

import com.Match_My_PC.domain.pc.PC;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PCDao {

  private Match_My_PCRepository match_my_pcRepository;

  public PCDao(Match_My_PCRepository match_my_pcRepository) {
    this.match_my_pcRepository = match_my_pcRepository;
  }

  public List<PC> findPCS() {
    return StreamSupport.stream(match_my_pcRepository.findAll().spliterator(), false)
        .map(pcEntitie -> buildPC(pcEntitie))
        .collect(Collectors.toList());
  }

  public PC findPCS(Long id) throws NotFoundException {
    return buildPC(match_my_pcRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  public PC createPCS(PC pc) {
    return buildPC(match_my_pcRepository.save(buildEntity(pc)));
  }

  public void deletePCS(Long id) {
    match_my_pcRepository.delete(match_my_pcRepository.findById(id).get());
  }

  public void updatePC(PC pc) {
    match_my_pcRepository.save(buildEntity(pc));
  }

  public PC replacePC(PC pc) {
    return buildPC(match_my_pcRepository.save(buildEntity(pc)));
  }

  private PCEntity buildEntity(PC pc) {
    return PCEntity
        .builder()
        .id(pc.getId())
        .marque(pc.getMarque())
        .date_sortie(pc.getDate_sortie())
        .category(pc.getCategory())
        .build();
  }

  private PC buildPC(PCEntity pcEntity) {
    return PC.builder()
        .id(pcEntity.getId())
        .marque(pcEntity.getMarque())
        .date_sortie(pcEntity.getDate_sortie())
        .category(pcEntity.getCategory())
        .build();
  }


}
