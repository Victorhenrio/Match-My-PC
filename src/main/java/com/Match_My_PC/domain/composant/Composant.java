package com.Match_My_PC.domain.composant;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Composant {

    private Long id;
    private String category;
    private int quantity;
    private float price;

}