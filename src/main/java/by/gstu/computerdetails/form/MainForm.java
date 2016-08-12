package by.gstu.computerdetails.form;

import by.gstu.computerdetails.config.FormConfig;
import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.dao.impl.ScreenResolutionDaoImpl;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.form.helper.FormHelper;
import by.gstu.computerdetails.listeners.ChangeTabListener;
import by.gstu.computerdetails.tablemodel.UniversalTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        final Component monitorTab = tabbedPane.getComponent(1);
        Component resolutionTab = tabbedPane.getComponent(2);

        monitorTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<Monitor> monitors = MONITOR_DAO.findAll();
                monitorTable.setModel(new UniversalTableModel<Monitor>(monitors, Monitor.class));
            }
        });

        resolutionTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
                resolutionTable.setModel(new UniversalTableModel<ScreenResolution>(resolutions, ScreenResolution.class));
            }
        });

        addMonitorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FormHelper.showWindow(
                        new AddEditForm().getRootPanel(),
                        FormConfig.ADD_MONITOR_FORM_NAME,
                        FormConfig.ADD_FORM_DEFAULT_WIDTH,
                        FormConfig.ADD_FORM_DEFAULT_HEIGHT
                );
            }
        });
    }

    public static void main(String[] args) {
        FormHelper.showWindow(
                new MainForm().mainPanel,
                FormConfig.APP_NAME,
                FormConfig.DEFAULT_WINDOW_WIDTH,
                FormConfig.DEFAULT_WINDOW_HEIGHT
        );
    }
}
