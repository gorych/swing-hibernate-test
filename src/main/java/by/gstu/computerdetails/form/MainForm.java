package by.gstu.computerdetails.form;

import by.gstu.computerdetails.config.FormConfig;
import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.dao.impl.ScreenResolutionDaoImpl;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.listeners.ChangeTabListener;
import by.gstu.computerdetails.tablemodel.UniversalTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.List;

public class MainForm {

    private static final MonitorDao MONITOR_DAO;
    private static final ScreenResolutionDao SCREEN_RESOLUTION_DAO;

    static {
        MONITOR_DAO = new MonitorDaoImpl();
        SCREEN_RESOLUTION_DAO = new ScreenResolutionDaoImpl();
    }

    private JPanel mainPanel;

    private JTable monitorTable;
    private JTable resolutionTable;

    private JTabbedPane tabbedPane;
    private JButton addMonitorBtn;

    public MainForm() {
        Component mainTab = tabbedPane.getComponent(0);
        Component monitorTab = tabbedPane.getComponent(1);
        Component resolutionTab = tabbedPane.getComponent(2);

        monitorTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<Monitor> monitors = MONITOR_DAO.findAll();
                UniversalTableModel<Monitor> model = new UniversalTableModel<Monitor>(monitors, Monitor.class);
                monitorTable.setModel(model);
            }
        });

        resolutionTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
                UniversalTableModel<ScreenResolution> model = new UniversalTableModel<ScreenResolution>(resolutions, ScreenResolution.class);
                resolutionTable.setModel(model);
            }
        });

        addMonitorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddEditMonitorForm addForm = new AddEditMonitorForm("Добавление нового монитора");
                addForm.setLocationRelativeTo(null);
                addForm.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame root = new JFrame(FormConfig.APP_NAME);

        root.setContentPane(new MainForm().mainPanel);
        root.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        root.setSize(FormConfig.DEFAULT_WINDOW_WIDTH, FormConfig.DEFAULT_WINDOW_HEIGHT);

        /*Null equals center screen here*/
        root.setLocationRelativeTo(null);
        root.setVisible(true);
    }
}
