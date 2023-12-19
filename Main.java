package org.example;

import model.Adapter;
import model.Service;

import java.lang.reflect.Field;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        compareClasses(Adapter.class, Service.class);
    }

    private static void compareClasses(Class<?> adapterClass, Class<?> serviceCLass) {
        Field[] adapterFields = adapterClass.getDeclaredFields();
        Field[] serviceFields = serviceCLass.getDeclaredFields();

        Map<String, String> adapterFieldMap = mapFields(adapterFields);
        Map<String, String> serviceFieldMap = mapFields(serviceFields);

        printComparison(adapterFieldMap, serviceFieldMap, adapterClass.getSimpleName(), serviceCLass.getSimpleName());

        printSettersForAdapterFields(adapterFieldMap,serviceFieldMap);
        generateAssertStatements(adapterFieldMap,serviceFieldMap);
    }

    private static Map<String, String> mapFields(Field[] fields) {
        TreeMap<String, String> fieldMap = new TreeMap<>();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field.getType().getSimpleName());
        }
        return fieldMap;
    }

    private static void printComparison(Map<String, String> adapterCLassFields, Map<String, String> serviceClassFields, String className1, String className2) {
        System.out.println("| " + className1 + " Field | " + className1 + " Data Type | " + className2 + " Field | " + className2 + " Data Type | Camel Case Match |");
        System.out.println("|----------------|-------------------|----------------|-------------------|-----------------|");

        // Create a map for the original names in class2
        Map<String, String> class2OriginalNames = new HashMap<>();
        for (Map.Entry<String, String> entry : serviceClassFields.entrySet()) {
            class2OriginalNames.put(entry.getKey().toLowerCase(), entry.getKey());
        }

        // Printing shared and unique fields
        for (Map.Entry<String, String> class1Entry : adapterCLassFields.entrySet()) {
            String class1FieldLower = class1Entry.getKey();
            String class1Type = class1Entry.getValue();
            String class2Field = class2OriginalNames.getOrDefault(class1FieldLower, "");
            String class2Type = class2Field.isEmpty() ? "" : serviceClassFields.get(class1FieldLower);
            boolean isCamelCaseMatch = !class2Field.isEmpty() && !class2Field.equals(class1FieldLower);

            System.out.format("| %-14s | %-17s | %-14s | %-17s | %-15b |\n", class1FieldLower, class1Type, class2Field, class2Type, isCamelCaseMatch);
        }

        // Print fields unique to class2
        for (String class2FieldLower : serviceClassFields.keySet()) {
            if (!adapterCLassFields.containsKey(class2FieldLower)) {
                String originalName = class2OriginalNames.get(class2FieldLower);
                System.out.format("| %-14s | %-17s | %-14s | %-17s | %-15b |\n", "", "", originalName, serviceClassFields.get(class2FieldLower), false);
            }
        }
    }

    static void printSettersForAdapterFields(Map<String, String> adapterFields, Map<String, String> serviceFields) {
        Set<String> adapterFieldNamesLower = new HashSet<>();
        adapterFields.keySet().forEach(fieldName -> adapterFieldNamesLower.add(fieldName.toLowerCase()));

        Set<String> serviceFieldNamesLower = new HashSet<>();
        serviceFields.keySet().forEach(fieldName -> serviceFieldNamesLower.add(fieldName.toLowerCase()));

        Set<String> commonFields = new HashSet<>();
        for (String field : adapterFields.keySet()) {
            if (serviceFieldNamesLower.contains(field.toLowerCase())) {
                commonFields.add(field); // Use original case field name
            }
        }

        Set<String> sortedSetters = new TreeSet<>();
        for (String field : commonFields) {
            String dataType = determineDataType(adapterFields.get(field), serviceFields.get(field.toLowerCase()));
            String value = getSampleValue(dataType);

            String setterMethod = "adapter.set" + Character.toUpperCase(field.charAt(0)) + field.substring(1) + "(" + value + ");";
            sortedSetters.add(setterMethod);
        }

        System.out.println("Common Setters in Adapter and Service:");
        for (String setter : sortedSetters) {
            System.out.println(setter);
        }
    }

    private static String determineDataType(String type1, String type2) {
        if ("String".equals(type1) && "String".equals(type2)) {
            return "String";
        } else if ("String".equals(type1) && "int".equals(type2)) {
            return "int";
        }
        else if ("int".equals(type1) && "String".equals(type2)) {
            return "int";
        }
        return type1; // Default to type1 if there's no String
    }

    private static String getSampleValue(String dataType) {
        switch (dataType) {
            case "String":
                return "\"SampleString\"";
            case "int":
            case "Integer":
                return "\"123\""; // Integer as a String
            case "double":
            case "Double":
                return "\"123.45\""; // Double as a String
            default:
                return "null";
        }
    }

    static void generateAssertStatements(Map<String, String> adapterFields, Map<String, String> serviceFields) {
        Set<String> adapterFieldNamesLower = new HashSet<>();
        adapterFields.keySet().forEach(fieldName -> adapterFieldNamesLower.add(fieldName.toLowerCase()));

        Set<String> serviceFieldNamesLower = new HashSet<>();
        serviceFields.keySet().forEach(fieldName -> serviceFieldNamesLower.add(fieldName.toLowerCase()));

        Set<String> commonFields = new HashSet<>();
        for (String field : adapterFields.keySet()) {
            if (serviceFieldNamesLower.contains(field.toLowerCase())) {
                commonFields.add(field); // Use original case field name
            }
        }
        System.out.println();
        System.out.println("junit asserts");
        // Collect assert statements in a sorted set
        Set<String> sortedAsserts = new TreeSet<>();
        for (String field : commonFields) {
            String getterMethod = "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
            sortedAsserts.add("Assert.assertEquals(adapter." + getterMethod + "(), service." + getterMethod + "());");
        }

        // Print sorted assert statements
        System.out.println("Assert Statements for Common Fields:");
        for (String assertStatement : sortedAsserts) {
            System.out.println(assertStatement);
        }
    }

}