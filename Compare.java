import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        compareFields(fields.class, Fields1.class);
    }

    public static void compareFields(Class<?> class1, Class<?> class2) {
        List<Field> fieldsOfClass1 = FieldUtils.getAllFieldsList(class1);
        List<Field> fieldsOfClass2 = FieldUtils.getAllFieldsList(class2);

        Map<String, Object> classOne = new HashMap<>();
        Map<String, Object> classTwo = new HashMap<>();

        for (Field field : fieldsOfClass1) {
            classOne.put(field.getName(), field.getType().getSimpleName());
        }

        for (Field field : fieldsOfClass2) {
            classTwo.put(field.getName(), field.getType().getSimpleName());
        }

        int maxFieldNameLength = Math.max(getMaxLength(classOne.keySet()), getMaxLength(classTwo.keySet()));
        int maxClass1TypeLength = Math.max(getMaxLength(classOne.values()), "Class1 Data Type".length());
        int maxClass2TypeLength = Math.max(getMaxLength(classTwo.values()), "Class2 Data Type".length());

        System.out.println(String.format("%-" + maxClass1TypeLength + "s  %-" + maxFieldNameLength + "s  %-" + maxClass2TypeLength + "s",
                "Class1 Data Type", "Field Name", "Class2 Data Type"));
        System.out.println(String.format("%-" + maxClass1TypeLength + "s  %-" + maxFieldNameLength + "s  %-" + maxClass2TypeLength + "s",
                repeat("=", maxClass1TypeLength), repeat("=", maxFieldNameLength), repeat("=", maxClass2TypeLength)));

        for (String fieldName : classOne.keySet()) {
            String class1DataType = classOne.get(fieldName).toString();
            String class2DataType = classTwo.containsKey(fieldName) ? classTwo.get(fieldName).toString() : "-";

            System.out.println(String.format("%-" + maxClass1TypeLength + "s  %-" + maxFieldNameLength + "s  %-" + maxClass2TypeLength + "s",
                    class1DataType, fieldName, class2DataType));
        }

        System.out.println("\nProperties present in classOne but missing in classTwo:");
        for (String fieldName : classOne.keySet()) {
            if (!classTwo.containsKey(fieldName)) {
                System.out.println(fieldName);
            }
        }
    }

    private static int getMaxLength(Collection<?> values) {
        int maxLength = 0;
        for (Object value : values) {
            int length = value.toString().length();
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    private static String repeat(String str, int times) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < times; i++) {
            result.append(str);
        }
        return result.toString();
    }
}
