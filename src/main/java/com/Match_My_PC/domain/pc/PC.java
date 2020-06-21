package com.Match_My_PC.domain.pc;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PC {

  private Long id;
  private String marque;
  private String date_sortie;
  private String category;
  private List<Long> composants;

}
