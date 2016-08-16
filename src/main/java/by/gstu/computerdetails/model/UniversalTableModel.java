package by.gstu.computerdetails.model;

import by.gstu.computerdetails.annotation.TableColumn;
import by.gstu.computerdetails.entity.BaseEntity;

import javax.swing.table.AbstractTableModel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class UniversalTableModel<T extends BaseEntity> extends AbstractTableModel {

    private List<T> data;
    private Map<Integer, String> headers;

    public UniversalTableModel(List<T> entities, Class<T> clazz) {
        super();
        this.data = entities;
        headers = TableModelUtil.getTableHeadersByClass(clazz);
    }

    public List getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return headers.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return getCellValue(rowIndex, columnIndex);
    }

    private Object getCellValue(int rowIndex, int columnIndex) {
        Method[] methods = data.get(rowIndex).getClass().getDeclaredMethods();

        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof TableColumn) {
                    TableColumn tableColumn = (TableColumn) annotation;
                    if (tableColumn.index() == columnIndex) {
                        try {
                            return method.invoke(data.get(rowIndex), null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int colIndex) {
        return headers.get(colIndex);
    }

}
