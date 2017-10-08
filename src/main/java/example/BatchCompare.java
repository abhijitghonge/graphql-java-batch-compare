package example;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.batched.BatchedExecutionStrategy;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.dataloader.DataLoaderRegistry;

import java.io.InputStreamReader;
import java.io.Reader;

public class BatchCompare {
    public static void main(String[] args) throws Exception {
        BatchCompare batchCompare = new BatchCompare();
        batchCompare.batchedRun();
        System.out.println();
        batchCompare.dataLoaderRun();
    }

    private void batchedRun() {
        System.out.println("=== BatchedExecutionStrategy ===");
        GraphQLSchema schema = buildBatchedSchema();
        GraphQL graphQL = GraphQL
                .newGraphQL(schema)
                .queryExecutionStrategy(new BatchedExecutionStrategy())
                .build();
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("query { shops { id name departments { id name products { id name } } } }")
                .build();
        ExecutionResult result = graphQL.execute(executionInput);
        System.out.println("\nExecutionResult: " + result.toSpecification());
    }

    private void dataLoaderRun() {
        System.out.println("=== AsyncExecutionStrategy with DataLoader ===");
        GraphQLSchema schema = buildDataLoaderSchema();
        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
        dataLoaderRegistry.register("departments", BatchCompareDataFetchers.departmentsForShopDataLoader);
        dataLoaderRegistry.register("products", BatchCompareDataFetchers.productsForDepartmentDataLoader);
        GraphQL graphQL = GraphQL
                .newGraphQL(schema)
                .instrumentation(new DataLoaderDispatcherInstrumentation(dataLoaderRegistry))
                .build();
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("query { shops { id name departments { id name products { id name } } } }")
                .build();
        ExecutionResult result = graphQL.execute(executionInput);
        System.out.println("\nExecutionResult: " + result.toSpecification());
    }

    private GraphQLSchema buildBatchedSchema() {
        Reader streamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sample.graphqls"));
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(streamReader);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("shops", BatchCompareDataFetchers.shopsDataFetcher)
                )
                .type(TypeRuntimeWiring.newTypeWiring("Shop")
                        .dataFetcher("departments", BatchCompareDataFetchers.departmentsForShopsBatchedDataFetcher)
                )
                .type(TypeRuntimeWiring.newTypeWiring("Department")
                        .dataFetcher("products", BatchCompareDataFetchers.productsForDepartmentsBatchedDataFetcher)
                )
                .build();

        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private GraphQLSchema buildDataLoaderSchema() {
        Reader streamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sample.graphqls"));
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(streamReader);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("shops", BatchCompareDataFetchers.shopsDataFetcher)
                )
                .type(TypeRuntimeWiring.newTypeWiring("Shop")
                        .dataFetcher("departments", BatchCompareDataFetchers.departmentsForShopDataLoaderDataFetcher)
                )
                .type(TypeRuntimeWiring.newTypeWiring("Department")
                        .dataFetcher("products", BatchCompareDataFetchers.productsForDepartmentDataLoaderDataFetcher)
                )
                .build();

        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }
}

