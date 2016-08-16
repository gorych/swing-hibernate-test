package by.gstu.computerdetails.model;

import by.gstu.computerdetails.annotation.TableColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TableModelUtil {

    public static Map<Integer, String> getTableHeadersByClass(Class clazz) {
        Map<Integer, String> headers = new HashMap<Integer, String>();

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof TableColumn) {
                    TableColumn tableColumn = (TableColumn) annotation;
                    headers.put(tableColumn.index(), tableColumn.name());
                }
            }
        }

        return headers;
    }

}
