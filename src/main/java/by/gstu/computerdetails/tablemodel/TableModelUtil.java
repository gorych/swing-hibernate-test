package by.gstu.computerdetails.tablemodel;

import by.gstu.computerdetails.annotation.TableColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TableModelUtil {

    public static List<String> getTableHeadersByClass(Class clazz) {
        List<String> headers = new ArrayList<String>();

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof TableColumn) {
                    TableColumn tableColumn = (TableColumn) annotation;
                    headers.add(tableColumn.name());
                }
            }
        }

        return headers;
    }

}
