package by.gstu.computerdetails.model;

import by.gstu.computerdetails.entity.BaseEntity;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UniversalTableModel extends AbstractTableModel {

    List<? extends BaseEntity> data;

    public UniversalTableModel(List<? extends BaseEntity> entities) {
        super();
        this.data = entities;
    }

    public List<? extends BaseEntity> getData() {
        return data;
    }

    public void setData(List<? extends BaseEntity> data) {
        this.data = data;
    }

    public int getRowCount() {
        return 1000;
    }

    public int getColumnCount() {
        return 10;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return "test";
    }

    @Override
    public String getColumnName(int column) {
        return "Unnamed";
    }

}
