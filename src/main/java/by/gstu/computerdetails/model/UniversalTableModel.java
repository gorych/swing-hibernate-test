package by.gstu.computerdetails.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UniversalTableModel<T> extends AbstractTableModel {

    private List<T> data;

    public UniversalTableModel(List<T> entities) {
        super();
        this.data = entities;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
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
