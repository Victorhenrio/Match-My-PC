package com.Match_My_PC;

import com.Match_My_PC.domain.composant.Composant;
import com.Match_My_PC.infrastructure.ComposantEntity;
import com.Match_My_PC.infrastructure.Match_My_PCRepository;
import com.Match_My_PC.infrastructure.PCEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private Match_My_PCRepository match_my_pcRepository;

  public DemoApplication(Match_My_PCRepository match_my_pcRepository) {
    this.match_my_pcRepository = match_my_pcRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    savePC(1L, "ASUS", "12/05/2019", "Ordinateur", Arrays.asList(Composant.builder().price(2.F).category("CPU").build()));
    savePC(2L, "MAC", "23/12/2018", "Ordinateur", Arrays.asList(Composant.builder().price(3.F).category("RAM").build()));
  }

  private void savePC(long id, String marque, String date_sortie, String category, List<Composant> composants) {
    this.match_my_pcRepository.save(

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
                                    .price(composant.getprice())
                                    .quantity(composant.getQuantity())
                                    .pcEntity(pcEntity)
                                    .build()
                    ));
  }

}
