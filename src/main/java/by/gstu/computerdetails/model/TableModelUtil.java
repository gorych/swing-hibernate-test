package by.gstu.computerdetails.model;

import by.gstu.computerdetails.annotation.TableColumn;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class TableModelUtil {

    private static final String ID_COL_NAME = "ID";

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

    public static int findIdColumn(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        Enumeration<javax.swing.table.TableColumn> columns = columnModel.getColumns();
        while (columns.hasMoreElements()) {
            javax.swing.table.TableColumn column = columns.nextElement();
            String colName = ((String) column.getHeaderValue()).toUpperCase();
            if (ID_COL_NAME.equals(colName)) {
                return column.getModelIndex();
            }
        }
        return -1;
    }

    public static void hideIdColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        Enumeration<javax.swing.table.TableColumn> columns = columnModel.getColumns();
        while (columns.hasMoreElements()) {
            javax.swing.table.TableColumn column = columns.nextElement();
            String colName = ((String) column.getHeaderValue()).toUpperCase();
            if ("ID".equals(colName)) {
                column.setMaxWidth(0);
                column.setMinWidth(0);
                column.setPreferredWidth(0);
            }
        }
    }

}
