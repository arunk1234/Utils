package com.example.junit.service;

import com.example.junit.model.LeprechaunFieldName;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JUnitTestClassGenerator {

    public static void main(String[] args) {
        Class<?> clazz = com.example.junit.service.ActClose.class; // Replace with your class
        StringBuilder testClassCode = new StringBuilder();

        appendPackageAndImports(testClassCode, clazz);
        appendLeprechaunFields(testClassCode, clazz);

        testClassCode.append("\n    // Test methods and logic go here\n").append("}\n");

        String outputFolderPath = "C:\\Users\\Arun\\Downloads\\New folder\\New folder\\junit\\src\\test\\java\\com\\example\\junit\\"; // Replace with your folder path
        String fileName = clazz.getSimpleName() + "Test.java";
        writeFile(outputFolderPath, fileName, testClassCode.toString());
    }
    private static void writeFile(String folderPath, String fileName, String content) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendPackageAndImports(StringBuilder builder, Class<?> clazz) {
        Package pkg = clazz.getPackage();
         builder.append("package com.example.junit").append(";\n\n")
                .append("import com.example.junit.model.LeprechaunFieldName;\n")
                .append("import ").append(clazz.getName()).append(";\n")
                // Add other necessary imports here
                .append("\n")
                .append("public class ").append(clazz.getSimpleName()).append("Test {\n\n");
    }

    private static void appendLeprechaunFields(StringBuilder builder, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (isLeprechaunFieldNameField(field)) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(null); // Assuming static field

                    String name = extractStringValue(fieldValue, "name");
                    String description = extractStringValue(fieldValue, "description");
                    boolean isEncryptedConstructorUsed = isEncryptedConstructorUsed(fieldValue);

                    builder.append("    public static final LeprechaunFieldName ")
                            .append(field.getName())
                            .append(" = new LeprechaunFieldName(\"")
                            .append(name)
                            .append("\", \"")
                            .append(description)
                            .append("\"");

                    if (isEncryptedConstructorUsed) {
                        builder.append(", ")
                                .append(false); // Placeholder or default value for 'encrypted'
                    }

                    builder.append(");\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        builder.append("\n");
    }
    private static boolean isEncryptedConstructorUsed(Object obj) {
        // Check if the encrypted constructor was used
        // This might be challenging without storing the 'encrypted' value in a field
        // You might need to use some heuristics or assumptions here
        return false; // Placeholder, adjust based on your use case
    }

    private static String extractStringValue(Object obj, String fieldName) {
        try {
            Field field = FieldUtils.getDeclaredField(obj.getClass(), fieldName, true);
            return (String) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "unknown"; // Default value in case of failure
        }
    }

    private static void appendPrivateVariables(StringBuilder builder, Class<?> clazz) {

    }



    private static boolean isLeprechaunFieldNameField(Field field) {
        return Modifier.isPublic(field.getModifiers()) &&
                Modifier.isStatic(field.getModifiers()) &&
                Modifier.isFinal(field.getModifiers()) &&
                field.getType().getSimpleName().equals("LeprechaunFieldName");
    }

    private static String getFieldNameValue(Field field) {
        // Placeholder for field name value extraction logic
        return "FIELD_NAME";
    }

    private static String getFieldDescriptionValue(Field field) {
        // Placeholder for field description value extraction logic
        return "FIELD_DESCRIPTION";
    }

}
