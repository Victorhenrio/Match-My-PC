package com.Match_My_PC;


import com.Match_My_PC.domain.composant.Composant;
import com.Match_My_PC.infrastructure.ComposantEntity;
import com.Match_My_PC.infrastructure.ComposantRepository;
import com.Match_My_PC.infrastructure.PCRepository;
import com.Match_My_PC.infrastructure.PCEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private PCRepository pcRepository;
  private ComposantRepository composantRepository;

  public DemoApplication(PCRepository pcRepository, ComposantRepository composantRepository) {
    this.pcRepository = pcRepository;
    this.composantRepository = composantRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    savePC(1L, "ASUS", "12/05/2019", "Ordinateur", Arrays.asList(Composant.builder().category("CPU").quantity(1).price(2.F).build()));
    savePC(2L, "MAC", "23/12/2018", "Ordinateur", Arrays.asList(Composant.builder().category("RAM").quantity(3).price(3.F).build()));
  }

  @Transactional
  private void savePC(long id, String marque, String date_sortie, String category, List<Composant> composants) {

    PCEntity pcEntity = this.pcRepository.save(
            PCEntity
                    .builder()
                    .id(id)
                    .marque(marque)
                    .date_sortie(date_sortie)
                    .category(category)
                    .build());

    composants.stream()
            .forEach(composant ->
                    composantRepository.save(
                            ComposantEntity
                                    .builder()
                                    .category(composant.getCategory())
                                    .price(composant.getPrice())
                                    .quantity(composant.getQuantity())
                                    .pcEntity(pcEntity)
                                    .build()
                    ));
  }

}
