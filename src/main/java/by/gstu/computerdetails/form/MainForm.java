package by.gstu.computerdetails.form;

import by.gstu.computerdetails.config.FormConfig;
import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.form.helper.FormHelper;
import by.gstu.computerdetails.listeners.ChangeTabListener;
import by.gstu.computerdetails.model.TableModelUtil;
import by.gstu.computerdetails.model.UniversalTableModel;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.List;

public class MainForm extends AbstractDataForm {

    private static MainForm instance;

    private JPanel mainPanel;

    private JTable monitorTable;
    private JTable resolutionTable;

    private JTabbedPane tabbedPane;
    private JButton addMonitorBtn;
    private JButton delMonitorBtn;
    private JButton editMonitorBtn;

    private MainForm() {
        Component mainTab = tabbedPane.getComponent(0);
        final Component monitorTab = tabbedPane.getComponent(1);
        Component resolutionTab = tabbedPane.getComponent(2);

        monitorTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<Monitor> monitors = MONITOR_DAO.findAll();
                monitorTable.setModel(new UniversalTableModel<Monitor>(monitors, Monitor.class));

                hideTableColumn(monitorTable, 0);
            }
        });

        resolutionTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
                resolutionTable.setModel(new UniversalTableModel<ScreenResolution>(resolutions, ScreenResolution.class));

                hideTableColumn(resolutionTable, 0);
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

        delMonitorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = monitorTable.getSelectedRowCount();
                if (selectedRowCount > 0) {
                    int selectedRows = monitorTable.getSelectedRows()[0];
                } else {
                    FormHelper.showWarning("Не выбрана запись для редактирования", "Предупреждение");
                }
            }
        });

        List<Integer> monitorHiddenColumns = TableModelUtil.getHiddenColumnIndexes(Monitor.class);
        List<Integer> resolutionHiddenColumns = TableModelUtil.getHiddenColumnIndexes(ScreenResolution.class);
    }

    public static MainForm GET_INSTANCE() {
        return instance == null ? new MainForm() : instance;
    }

    public static void main(String[] args) {
        FormHelper.showWindow(
                GET_INSTANCE().mainPanel,
                FormConfig.APP_NAME,
                FormConfig.DEFAULT_WINDOW_WIDTH,
                FormConfig.DEFAULT_WINDOW_HEIGHT
        );

        /*Get session at the opening of the main window*/
        Session session = HibernateUtil.getSession();

        /*Exit program if session not created*/
        if (session == null) {
            System.exit(0);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTable getMonitorTable() {
        return monitorTable;
    }

    public JTable getResolutionTable() {
        return resolutionTable;
    }
}
