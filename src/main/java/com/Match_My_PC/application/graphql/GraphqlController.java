package com.Match_My_PC.application.graphql;


import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@GraphQLApi
@RequestMapping("/graphql/v1")
public class GraphqlController {

    //Declarer une instance de la classe GraphQL
    private GraphQL graphQL;

    //Approche code first (le schéma est généré après le code)
    public GraphqlController(PCResolver pcResolver) {

        GraphQLSchema schema = new GraphQLSchemaGenerator()
//        .withBasePackages("com.match_my_pc.graphql")
                .withOperationsFromSingletons(pcResolver)
                .generate();
        graphQL = GraphQL.newGraphQL(schema).build();

        log.info("Generated GraphQL schema using SPQR");
    }


    // Tout les appels de graphql ce font avec cette URI :
    @PostMapping(value = "/graphql/v1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @ResponseBody
    public Map<String, Object> executeQuery(@RequestBody Map<String, String> request, HttpServletRequest raw) {
        ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
                .query(request.get("query"))
                .operationName(request.get("operationName"))
                .context(raw)
                .build());
        return executionResult.toSpecification();
    }

}
