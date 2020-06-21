package com.Match_My_PC.application.graphql;

import com.Match_My_PC.domain.pc.PC;
import com.Match_My_PC.domain.pc.PCService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PCResolver {

    private PCService pcService ;

    public PCResolver(PCService pcService) {
        this.pcService = pcService;
    }

    @GraphQLQuery
    public List<PC> getPCS (@GraphQLArgument(name = "first", defaultValue = "null") Integer first) {
        if (Objects.isNull(first)) {
            return pcService.getPCS();
        }
        return pcService.getPCS()
                .stream()
                .limit(first)
                .collect(Collectors.toList());
        }
}
