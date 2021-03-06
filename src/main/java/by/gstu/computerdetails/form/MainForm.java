package by.gstu.computerdetails.form;

import by.gstu.computerdetails.algorithm.AlgorithmHelper;
import by.gstu.computerdetails.algorithm.DecisionFunctionBuilder;
import by.gstu.computerdetails.algorithm.K_Means;
import by.gstu.computerdetails.algorithm.Result;
import by.gstu.computerdetails.config.FormConfig;
import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.entity.Cluster;
import by.gstu.computerdetails.entity.MatrixType;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.form.helper.FormHelper;
import by.gstu.computerdetails.listeners.ChangeTabListener;
import by.gstu.computerdetails.model.TableModelUtil;
import by.gstu.computerdetails.model.UniversalTableModel;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainForm extends AbstractDataForm {

    //region Fields

    private static final int SETTINGS_TAB_INDEX = 4;

    private static MainForm instance = new MainForm();

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    //Monitor tab
    private JTable monitorTable;

    private JButton addMonitorBtn;
    private JButton editMonitorBtn;
    private JButton delMonitorBtn;

    //Resolution tab
    private JTable resolutionTable;

    private JButton addResolutionBtn;
    private JButton editResolutionBtn;
    private JButton delResolutionBtn;

    //Cluster tab
    private JTable clusterTable;

    private JButton addClusterBtn;
    private JButton editClusterBtn;
    private JButton delClusterBtn;

    //Main tab
    private JTextField priceField;
    private JTextField diagonalField;

    private JComboBox<MatrixType> matrixCb;
    private JComboBox<ScreenResolution> resolutionCb;
    private JTextField guaranteeField;

    private JButton analysisBtn;
    private JButton settingsBtn;

    //Matrix tab
    private JTable matrixTable;

    //endregion

    private MainForm() {
        final Component analyzeTab = tabbedPane.getComponent(0);
        final Component monitorTab = tabbedPane.getComponent(1);
        final Component resolutionTab = tabbedPane.getComponent(2);
        final Component matrixTab = tabbedPane.getComponent(3);
        final Component settingsTab = tabbedPane.getComponent(4);


        analysisBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Cluster> clusters = CLUSTER_DAO.findAll();
                List<Monitor> monitors = MONITOR_DAO.findAll();

                if (monitors.size() < 1 || clusters.size() < 2) {
                    return;
                }

                K_Means kMeans = new K_Means(monitors, clusters);
                Map<Cluster, List<Integer>> clusterMap = kMeans.divide();
                Map<Cluster, List<Result>> functions = DecisionFunctionBuilder.build(clusters, monitors, clusterMap);

                try {
                    double diagonal = Double.valueOf(diagonalField.getText());
                    int guarantee = Integer.valueOf(guaranteeField.getText());
                    BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceField.getText()));
                    ScreenResolution screenResolution = (ScreenResolution) resolutionCb.getSelectedItem();
                    MatrixType matrixType = (MatrixType) matrixCb.getSelectedItem();

                    Monitor monitor = new Monitor("analysed", price, diagonal, guarantee, screenResolution, matrixType);
                    List<Monitor> newMonitorList = new ArrayList<Monitor>();
                    newMonitorList.addAll(monitors);
                    newMonitorList.add(monitor);

                    double[] maxSignValue = AlgorithmHelper.findMaxSignValue(newMonitorList);
                    List<double[]> normalizeValues = AlgorithmHelper.normalizeByMax(newMonitorList, maxSignValue);

                    int monitorIndex = normalizeValues.size() - 1;
                    double[] monitorNormalValues = normalizeValues.get(monitorIndex);

                    List<Cluster> resultCluster = new ArrayList<Cluster>();
                    for (Cluster cluster : functions.keySet()) {
                        List<Result> results = functions.get(cluster);
                        int counter = 0;
                        for (Result result : results) {
                            double[] w = result.getW();
                            double sum = 0;
                            for (int i = 0; i < monitorNormalValues.length; i++) {
                                sum += w[i] * monitorNormalValues[i];
                            }
                            sum += result.getW0();
                            if ((result.isPositive() && sum < 0) || (!result.isPositive() && sum > 0)) {
                                break;
                            } else {
                                counter++;
                            }
                        }
                        if (counter == results.size()) {
                            resultCluster.add(cluster);
                        }
                    }
                    if (resultCluster.size() == 1) {
                        FormHelper.showInfo("Указанный монитор относится к категории \""
                                + resultCluster.get(0).getDescription() + "\"", "Результат");
                    } else {
                        FormHelper.showWarning("Распознование указанного монитора невозможно", "Результат");
                    }
                } catch (RuntimeException exc) {
                    FormHelper.showError("Значения полей введены некорректно. Попробуйте еще раз.", "Некорректный ввод");
                }
            }
        });

        //region Tab Listeners

        analyzeTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                fillComboBoxes();
            }
        });

        monitorTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateMonitorTable();
            }
        });

        matrixTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateMatrixTable();
            }
        });

        resolutionTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateResolutionTable();
            }
        });

        settingsTab.addComponentListener(new ChangeTabListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateClusterTable();
            }
        });

        //endregion

        //region Monitor Tab Listeners

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
                } else {
                    FormHelper.showInfo("Не выбрана запись для редактирования.", "Уведомление");
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

        //endregion

        //region Resolution Tab Listeners

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
                } else {
                    FormHelper.showInfo("Не выбрана запись для редактирования.", "Уведомление");
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
                            FormHelper.showError("Ошибка при выполнении операции", "Ошибка удаления");
                            return;
                        }
                    }

                    FormHelper.showInfo("Операция удаления выполнена успешно", "Успешное удаление");
                    updateTables();
                }
            }
        });

        //endregion

        //region Matrix Tab Listener

        matrixTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = matrixTable.getSelectedRow();
                if (selectedRow < 0) {
                    return;
                }

                int idCol = TableModelUtil.findIdColumn(matrixTable);
                Long id = (Long) matrixTable.getValueAt(selectedRow, idCol);

                MatrixType matrixType = MATRIX_TYPE_DAO.find(id);

                FormHelper.showWindow(
                        new MatrixWeightForm(matrixType).getRootPanel(),
                        FormConfig.EDIT_WEIGHT_FORM_NAME,
                        FormConfig.WEIGHT_FORM_DEFAULT_WIDTH,
                        FormConfig.WEIGHT_FORM_DEFAULT_HEIGHT
                );
            }
        });

        //endregion

        //region Cluster Tab Listener

        addClusterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FormHelper.showWindow(
                        new AddEditClusterForm(null).getRootPanel(),
                        FormConfig.ADD_CLUSTER_FORM_NAME,
                        FormConfig.ADD_CLUSTER_FORM_DEFAULT_WIDTH,
                        FormConfig.ADD_CLUSTER_FORM_DEFAULT_HEIGHT
                );
            }
        });

        editClusterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = clusterTable.getSelectedRowCount();
                if (selectedRowCount > 0) {
                    int selectedRow = clusterTable.getSelectedRows()[0];
                    int colIndex = TableModelUtil.findIdColumn(clusterTable);

                    Long id = (Long) clusterTable.getValueAt(selectedRow, colIndex);
                    Cluster cluster = CLUSTER_DAO.find(id);

                    FormHelper.showWindow(
                            new AddEditClusterForm(cluster).getRootPanel(),
                            FormConfig.EDIT_CLUSTER_FORM_NAME,
                            FormConfig.ADD_CLUSTER_FORM_DEFAULT_WIDTH,
                            FormConfig.ADD_CLUSTER_FORM_DEFAULT_HEIGHT
                    );
                } else {
                    FormHelper.showInfo("Не выбрана запись для редактирования.", "Уведомление");
                }
            }
        });

        delClusterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowCount = clusterTable.getSelectedRowCount();

                if (selectedRowCount > 0) {
                    int[] selectedRows = clusterTable.getSelectedRows();
                    int colIndex = TableModelUtil.findIdColumn(clusterTable);

                    for (int selectedRow : selectedRows) {
                        Long id = (Long) clusterTable.getValueAt(selectedRow, colIndex);
                        try {
                            Cluster cluster = CLUSTER_DAO.find(id);
                            CLUSTER_DAO.remove(cluster);
                        } catch (RuntimeException exc) {
                            FormHelper.showError("Ошибка при выполнении операции", "Ошибка удаления");
                            return;
                        }
                    }

                    FormHelper.showInfo("Операция удаления выполнена успешно", "Успешное удаление");
                    updateTables();
                }
            }
        });

        //endregion

        settingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(SETTINGS_TAB_INDEX);
            }
        });

        fillComboBoxes();
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

    private void fillComboBoxes() {
        List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
        List<MatrixType> types = MATRIX_TYPE_DAO.findAll();

        resolutionCb.removeAllItems();
        matrixCb.removeAllItems();

        for (ScreenResolution resolution : resolutions) {
            resolutionCb.addItem(resolution);
        }

        for (MatrixType type : types) {
            matrixCb.addItem(type);
        }
    }

    //region Update table methods

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

    private void updateClusterTable() {
        List<Cluster> clusters = CLUSTER_DAO.findAll();
        clusterTable.setModel(new UniversalTableModel<Cluster>(clusters, Cluster.class));
        TableModelUtil.hideIdColumns(clusterTable);
    }

    public void updateMatrixTable() {
        List<MatrixType> matrixTypes = MATRIX_TYPE_DAO.findAll();
        matrixTable.setModel(new UniversalTableModel<MatrixType>(matrixTypes, MatrixType.class));
        TableModelUtil.hideIdColumns(matrixTable);
    }

    public void updateTables() {
        updateMonitorTable();
        updateResolutionTable();
        updateClusterTable();
    }

    //endregion

}
