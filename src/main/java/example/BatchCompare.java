package example;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.dataloader.DataLoaderRegistry;

import java.io.InputStreamReader;
import java.io.Reader;

public class BatchCompare {
    public static void main(String[] args) throws Exception {
        BatchCompare batchCompare = new BatchCompare();
        System.out.println();
        System.out.println("=== AsyncExecutionStrategy with DataLoader ===");
        System.out.println("=== Static Wiring ===");
        GraphQLSchema schema = batchCompare.buildDataLoaderSchema();
        batchCompare.dataLoaderRun(schema);

        System.out.println("=== Dynamic Wiring ===");
        GraphQLSchema dynamicSchema = batchCompare.buildUsingDynamicWiring();
        batchCompare.dataLoaderRun(dynamicSchema);

    }



    private void dataLoaderRun(GraphQLSchema schema) {

        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
        dataLoaderRegistry.register("departments", BatchCompareDataFetchers.departmentsDataLoader);
        dataLoaderRegistry.register("subdepartments", BatchCompareDataFetchers.departmentsDataLoader);
        dataLoaderRegistry.register("customers", BatchCompareDataFetchers.customersForShopDataLoader);
        dataLoaderRegistry.register("products", BatchCompareDataFetchers.productsForDepartmentDataLoader);

        GraphQL graphQL = GraphQL
                .newGraphQL(schema)
                .instrumentation(new DataLoaderDispatcherInstrumentation(dataLoaderRegistry))
                .build();

        ExecutionInput executionInputOnlyCustomersSuccessUseCase = ExecutionInput.newExecutionInput()
                .query("query { shops { id name departments{id name } customers {id name}  } }")
                .build();


        ExecutionResult customersOnlyResults = graphQL.execute(executionInputOnlyCustomersSuccessUseCase);

        System.out.println("\nExecutionResult with Customers Only case: " + customersOnlyResults.toSpecification());

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("query { shops { id name departments{id name subdepartments{id name products{id name}} products{id name}} customers {id name}  } }")
                .build();

        ExecutionResult nestQueryResults = graphQL.execute(executionInput);

        System.out.println("\nExecutionResult with nest departments: " + nestQueryResults.toSpecification());


    }

    private GraphQLSchema buildUsingDynamicWiring() {
        Reader streamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sample.graphqls"));
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(streamReader);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .wiringFactory(new DynamicWiringFactory())
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
                        .dataFetcher("departments", BatchCompareDataFetchers.departmentsDataLoaderDataFetcher)
                        .dataFetcher("customers", BatchCompareDataFetchers.customersForShopDataLoaderDataFetcher)
                )
                .type(TypeRuntimeWiring.newTypeWiring("Department")
                        .dataFetcher("subdepartments", BatchCompareDataFetchers.departmentsDataLoaderDataFetcher)
                        .dataFetcher("products", BatchCompareDataFetchers.productsForDepartmentDataLoaderDataFetcher)
                )
                .build();

        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }
}

