
package example;


import graphql.language.FieldDefinition;
import graphql.language.InterfaceTypeDefinition;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactory;
import graphql.schema.TypeResolver;
import graphql.schema.idl.FieldWiringEnvironment;
import graphql.schema.idl.InterfaceWiringEnvironment;
import graphql.schema.idl.WiringFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicWiringFactory implements WiringFactory {
    private static final Logger LOGGER = Logger.getLogger(DynamicWiringFactory.class.getName());

    private Map<String, DataFetcher> fieldNameToDataFetchers = new HashMap<>();

    public DynamicWiringFactory(){
        fieldNameToDataFetchers.put("shops", BatchCompareDataFetchers.shopsDataFetcher);
        fieldNameToDataFetchers.put("departments", BatchCompareDataFetchers.departmentsForShopDataLoaderDataFetcher);
        fieldNameToDataFetchers.put("customers", BatchCompareDataFetchers.customersForShopDataLoaderDataFetcher);
        fieldNameToDataFetchers.put("products", BatchCompareDataFetchers.productsForDepartmentDataLoaderDataFetcher);

    }

    @Override
    public boolean providesTypeResolver(InterfaceWiringEnvironment environment) {
        final InterfaceTypeDefinition interfaceTypeDefinition = environment.getInterfaceTypeDefinition();
        LOGGER.log(Level.INFO, "Interface Field Definition:[" + interfaceTypeDefinition + "]");

        return false;
    }

    @Override
    public TypeResolver getTypeResolver(InterfaceWiringEnvironment environment) {
        return null;
    }

    @Override
    public boolean providesDataFetcherFactory(FieldWiringEnvironment environment) {
//        final FieldDefinition fieldDefinition = environment.getFieldDefinition();
//        LOGGER.log(Level.INFO, "Datafetcher Factory for Field Definition:[" + fieldDefinition + "]");
        return false;
    }

    @Override
    public <T> DataFetcherFactory<T> getDataFetcherFactory(FieldWiringEnvironment environment) {
        return null;
    }

    public boolean providesDataFetcher(FieldWiringEnvironment environment) {
        FieldDefinition fieldDefinition = environment.getFieldDefinition();
        LOGGER.log(Level.INFO, "Field Definition:[" + fieldDefinition + "]");

        return fieldNameToDataFetchers.containsKey(fieldDefinition.getName());
    }

    public DataFetcher getDataFetcher(FieldWiringEnvironment environment) {

        FieldDefinition fieldDefinition = environment.getFieldDefinition();
        LOGGER.log(Level.INFO, "Fetch Field Definition:[" + fieldDefinition + "]");

        return fieldNameToDataFetchers.get(fieldDefinition.getName());
    }

    @Override
    public DataFetcher getDefaultDataFetcher(FieldWiringEnvironment environment) {
//        FieldDefinition fieldDefinition = environment.getFieldDefinition();
//        LOGGER.log(Level.INFO, "Default DataFecther for Field Definition:[" + fieldDefinition + "]");
        return null;
    }
}
