package com.Match_My_PC.infrastructure;

import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.Match_My_PC.domain.composant.Composant;
import java.util.List;


@Service
public class ComposantDao {

    private PCRepository pcRepository;
    private ComposantRepository composantRepository;

    public ComposantDao(PCRepository pcRepository, ComposantRepository composantRepository) {
        this.pcRepository = pcRepository;
        this.composantRepository = composantRepository;
    }

    public Composant addComposants(Long pcId, Composant composant) {
        ComposantEntity composantEntity = buildComposantEntity(composant, pcRepository.findById(pcId).orElse(null));
        return buildComposant(composantRepository.save(composantEntity));
    }

    public List<Composant> findComposantsByPCId() {
        return StreamSupport.stream(composantRepository.findAll().spliterator(), false)
                .map(composantEntity -> buildComposant(composantEntity))
                .collect(Collectors.toList());
    }

    public Composant findComposantsById(Long pcId) {
        return buildComposant(composantRepository.findById(pcId).orElse(null));
    }

    public List<Composant> findComposantsByPCId(Long pcId) {
        return composantRepository.findByPCEntityId(pcId)
                .stream()
                .map(composantEntity -> buildComposant(composantEntity))
                .collect(Collectors.toList());
    }

    private ComposantEntity buildComposantEntity(Composant composant, PCEntity pcEntity) {
        return ComposantEntity.builder()
                .category(composant.getCategory())
                .price(composant.getPrice())
                .quantity(composant.getQuantity())
                .pcEntity(pcEntity)
                .build();
    }

    private Composant buildComposant(ComposantEntity composantEntity) {
        return Composant.builder()
                .id(ComposantEntity.getId())
                .price(ComposantEntity.getPrice())
                .category(ComposantEntity.getCategory())
                .quantity(ComposantEntity.getQuantity())
                .build();
    }
}
