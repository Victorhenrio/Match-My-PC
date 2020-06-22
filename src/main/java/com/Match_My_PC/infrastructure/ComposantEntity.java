package com.Match_My_PC.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;



@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="COMPOSANT_ENTITY")
public class ComposantEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "CATEGORY", length = 50, nullable = false)
    private String category;
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
    @Column(name = "PRICE", length = 50, nullable = false)
    private float price;

    @ManyToOne
    private PCEntity pcEntity;
}

