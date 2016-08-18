package by.gstu.computerdetails.form;

import by.gstu.computerdetails.entity.MatrixType;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.form.helper.FormHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class AddEditMonitorForm extends AbstractDataForm {

    private JPanel rootPanel;

    private JTextField nameField;
    private JTextField diagonalField;
    private JTextField guaranteeField;
    private JTextField priceField;

    private JButton saveBtn;
    private JComboBox<ScreenResolution> resolutionCb;
    private JComboBox<MatrixType> matrixCb;

    public AddEditMonitorForm(final Monitor oldMonitor) {
        List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
        List<MatrixType> types = MATRIX_TYPE_DAO.findAll();

        for (ScreenResolution resolution : resolutions) {
            resolutionCb.addItem(resolution);
        }

        for (MatrixType type : types) {
            matrixCb.addItem(type);
        }

        if (oldMonitor != null) {
            nameField.setText(oldMonitor.getName());
            diagonalField.setText(oldMonitor.getDiagonal() + "");
            guaranteeField.setText(oldMonitor.getGuaranteePeriod() + "");
            priceField.setText(oldMonitor.getPrice() + "");
            resolutionCb.setSelectedItem(oldMonitor.getScreenResolution());
            matrixCb.setSelectedItem(oldMonitor.getMatrixType());
        }

        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    double diagonal = Double.valueOf(diagonalField.getText());
                    int guarantee = Integer.valueOf(guaranteeField.getText());
                    BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceField.getText()));
                    ScreenResolution screenResolution = (ScreenResolution) resolutionCb.getSelectedItem();
                    MatrixType matrixType = (MatrixType) matrixCb.getSelectedItem();

                    Monitor newMonitor = new Monitor(name, price, diagonal, guarantee, screenResolution, matrixType);
                    if (oldMonitor != null) {
                        newMonitor.setId(oldMonitor.getId());
                    }

                    MONITOR_DAO.saveOrUpdate(newMonitor);
                } catch (RuntimeException exc) {
                    FormHelper.showError("Значения полей введены некорректно. Попробуйте еще раз.", "Некорректный ввод");
                    return;
                }

                if (oldMonitor == null) {
                    int success = FormHelper.showConfirm(
                            "Запись успешно сохранена. Хотите добавить еще одну?",
                            "Успешное добавление"
                    );

                    if (success == JOptionPane.NO_OPTION || success == JOptionPane.CLOSED_OPTION) {
                        closeForm(rootPanel);
                    } else {
                        resetFieldValues(rootPanel);
                    }
                } else {
                    FormHelper.showInfo("Запись успешно сохранена!", "Успешное сохранение");
                    closeForm(rootPanel);
                }

                MainForm.GET_INSTANCE().updateTables();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
