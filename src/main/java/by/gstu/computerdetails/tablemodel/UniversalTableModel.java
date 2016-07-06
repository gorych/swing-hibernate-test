package by.gstu.computerdetails.tablemodel;

import by.gstu.computerdetails.entity.BaseEntity;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UniversalTableModel extends AbstractTableModel {

    private List<? extends BaseEntity> data;
    private List<String> headers;
    private Class clazz;

    public UniversalTableModel(List<? extends BaseEntity> entities, Class clazz) {
        super();
        this.data = entities;
        this.clazz = clazz;
        headers = TableModelUtil.getTableHeadersByClass(clazz);
    }

    public List getData() {
        return data;
    }

    public void setData(List<? extends BaseEntity> data) {
        this.data = data;
    }

    public int getRowCount() {
        return 1000;
    }

    public int getColumnCount() {
        return headers.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        BaseEntity baseEntity = data.get(rowIndex);

        return "test";
    }

    @Override
    public String getColumnName(int colIndex) {
        return headers.get(colIndex);
    }

}
