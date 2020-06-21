package com.Match_My_PC.domain.pc;

import com.Match_My_PC.infrastructure.PCDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PCService {

  private PCDao pcDao;

  public PCService(PCDao pcDao) {
    this.pcDao = pcDao;
  }

  public List<PC> getPCS() { return pcDao.findPCS(); }

  @Cacheable("pcs")
  public PC getPCS(Long id) throws NotFoundException {
    log.info("********************INSIDE THE PCERVICE********************");
    return pcDao.findPCS(id);
  }

  public PC addPC(PC pc) throws NotFoundException {
    return pcDao.createPCS(pc);
  }

  public void deletePCS(Long id) {
    pcDao.deletePCS(id);
  }

  public void patchPCS(PC pc) {
    pcDao.updatePC(pc);
  }

  public PC findPC(Long id) throws NotFoundException {
    return pcDao.findPCS(id);
  }

  public PC replacePC(PC pc) {
    return pcDao.replacePC(pc);
  }
}
