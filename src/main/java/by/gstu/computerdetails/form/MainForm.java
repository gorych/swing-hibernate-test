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

    private static MainForm instance = new MainForm();

    private JPanel mainPanel;

    private JTable monitorTable;
    private JTable resolutionTable;

    private JTabbedPane tabbedPane;
    private JButton addMonitorBtn;
    private JButton editMonitorBtn;
    private JButton delMonitorBtn;
    private JButton addResolutionBtn;
    private JButton editResolutionBtn;
    private JButton delResolutionBtn;

    private MainForm() {
        Component mainTab = tabbedPane.getComponent(0);
        Component monitorTab = tabbedPane.getComponent(1);
        final Component resolutionTab = tabbedPane.getComponent(2);

        monitorTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateMonitorTable();
            }
        });

        resolutionTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateResolutionTable();
            }
        });

        addMonitorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FormHelper.showWindow(
                        new AddEditMonitorForm(null).getRootPanel(),
                        FormConfig.ADD_MONITOR_FORM_NAME,
                        FormConfig.ADD_FORM_DEFAULT_WIDTH,
                        FormConfig.ADD_FORM_DEFAULT_HEIGHT
                );
            }
        });

        editMonitorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = monitorTable.getSelectedRowCount();
                if (selectedRowCount > 0) {
                    int selectedRow = monitorTable.getSelectedRows()[0];
                    int colIndex = TableModelUtil.findIdColumn(monitorTable);

                    Long id = (Long) monitorTable.getValueAt(selectedRow, colIndex);
                    Monitor monitor = MONITOR_DAO.find(id);

                    FormHelper.showWindow(
                            new AddEditMonitorForm(monitor).getRootPanel(),
                            FormConfig.EDIT_MONITOR_FORM_NAME,
                            FormConfig.ADD_FORM_DEFAULT_WIDTH,
                            FormConfig.ADD_FORM_DEFAULT_HEIGHT
                    );
                }
            }
        });

        delMonitorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = monitorTable.getSelectedRowCount();

                if (selectedRowCount > 0) {
                    int[] selectedRows = monitorTable.getSelectedRows();
                    int colIndex = TableModelUtil.findIdColumn(monitorTable);

                    for (int selectedRow : selectedRows) {
                        Long id = (Long) monitorTable.getValueAt(selectedRow, colIndex);
                        try {
                            Monitor monitor = MONITOR_DAO.find(id);
                            MONITOR_DAO.remove(monitor);
                        } catch (RuntimeException exc) {
                            FormHelper.showError("Ошибка при выполении операции", "Ошибка удаления");
                            return;
                        }
                    }

                    FormHelper.showInfo("Операция удаления выполнена успешно", "Успешное удаление");
                    updateMonitorTable();
                }
            }
        });

        addResolutionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FormHelper.showWindow(
                        new AddEditResolutionForm(null).getRootPanel(),
                        FormConfig.ADD_SR_FORM_NAME,
                        FormConfig.ADD_SR_FORM_DEFAULT_WIDTH,
                        FormConfig.ADD_SR_FORM_DEFAULT_HEIGHT
                );
            }
        });

        editResolutionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = resolutionTable.getSelectedRowCount();
                if (selectedRowCount > 0) {
                    int selectedRow = resolutionTable.getSelectedRows()[0];
                    int colIndex = TableModelUtil.findIdColumn(resolutionTable);

                    Long id = (Long) resolutionTable.getValueAt(selectedRow, colIndex);
                    ScreenResolution resolution = SCREEN_RESOLUTION_DAO.find(id);

                    FormHelper.showWindow(
                            new AddEditResolutionForm(resolution).getRootPanel(),
                            FormConfig.EDIT_SR_FORM_NAME,
                            FormConfig.ADD_SR_FORM_DEFAULT_WIDTH,
                            FormConfig.ADD_SR_FORM_DEFAULT_HEIGHT
                    );
                }
            }
        });

        delResolutionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = resolutionTable.getSelectedRowCount();

                if (selectedRowCount > 0) {
                    int[] selectedRows = resolutionTable.getSelectedRows();
                    int colIndex = TableModelUtil.findIdColumn(resolutionTable);

                    for (int selectedRow : selectedRows) {
                        Long id = (Long) resolutionTable.getValueAt(selectedRow, colIndex);
                        try {
                            ScreenResolution resolution = SCREEN_RESOLUTION_DAO.find(id);
                            SCREEN_RESOLUTION_DAO.remove(resolution);
                        } catch (RuntimeException exc) {
                            FormHelper.showError("Ошибка при выполении операции", "Ошибка удаления");
                            return;
                        }
                    }

                    FormHelper.showInfo("Операция удаления выполнена успешно", "Успешное удаление");
                    updateResolutionTable();
                }
            }
        });
    }

    public static MainForm GET_INSTANCE() {
        return instance;
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

    private void updateResolutionTable() {
        List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
        resolutionTable.setModel(new UniversalTableModel<ScreenResolution>(resolutions, ScreenResolution.class));
        TableModelUtil.hideIdColumns(resolutionTable);
    }

    private void updateMonitorTable() {
        List<Monitor> monitors = MONITOR_DAO.findAll();
        monitorTable.setModel(new UniversalTableModel<Monitor>(monitors, Monitor.class));
        TableModelUtil.hideIdColumns(monitorTable);
    }

    public void updateTables() {
        updateMonitorTable();
        updateResolutionTable();
    }
}
