package by.gstu.computerdetails.forms;

import by.gstu.computerdetails.config.FormConfig;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.entity.Monitor;

import javax.swing.*;
import java.util.List;

public class MainForm {

    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame root = new JFrame(FormConfig.APP_NAME);

        root.setContentPane(new MainForm().mainPanel);
        root.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        root.setSize(FormConfig.DEFAULT_WINDOW_WIDTH, FormConfig.DEFAULT_WINDOW_HEIGHT);

        /*Null equals center screen here*/
        root.setLocationRelativeTo(null);
        root.setVisible(true);

        MonitorDaoImpl monitorDao = new MonitorDaoImpl();
        List<Monitor> monitors = monitorDao.findAll();
        System.out.println(monitors);
    }

}
