package com.Match_My_PC.domain.composant;

import com.Match_My_PC.infrastructure.ComposantDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class ComposantService {

    private ComposantDao composantDao;

    public ComposantService(ComposantDao composantDao) {
        this.composantDao = composantDao;
    }

    public Composant addComposants(Long pcId, Composant composant) { return composantDao.addComposants(pcId, composant); }

    public List<Composant> findComposantsByPCId(Long pcId) {
        return ComposantDao.findComposantsByPCId(pcId);
    }

}
