package by.gstu.computerdetails.form;

import by.gstu.computerdetails.config.FormConfig;
import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.model.UniversalTableModel;

import javax.swing.*;
import java.util.List;

public class MainForm {

    private static MonitorDao monitorDao;

    static {
        monitorDao = new MonitorDaoImpl();
    }

    private JTable monitorTable;
    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame root = new JFrame(FormConfig.APP_NAME);

        root.setContentPane(new MainForm().mainPanel);
        root.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        root.setSize(FormConfig.DEFAULT_WINDOW_WIDTH, FormConfig.DEFAULT_WINDOW_HEIGHT);

        /*Null equals center screen here*/
        root.setLocationRelativeTo(null);
        root.setVisible(true);
    }

    private void createUIComponents() {
        List<Monitor> monitors = monitorDao.findAll();
        UniversalTableModel<Monitor> model = new UniversalTableModel<Monitor>(monitors);

        monitorTable = new JTable(model);
        try {
            Class<?> clazz = Class.forName("by.gstu.computerdetails.entity.Monitor");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
