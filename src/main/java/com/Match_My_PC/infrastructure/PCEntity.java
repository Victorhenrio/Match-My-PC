package com.Match_My_PC.infrastructure;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PCEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "MARQUE", length = 50, nullable = false)
  private String marque;
  @Column(name = "DATE_SORTIE", length = 10, nullable = false)
  private String date_sortie;
  @Column(name = "CATEGORY", length = 50, nullable = false)
  private String category;

  @OneToMany(mappedBy = "pcEntity", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
  private List<ComposantEntity> composantEntityList;
}
