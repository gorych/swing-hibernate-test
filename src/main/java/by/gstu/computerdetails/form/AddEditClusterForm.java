package by.gstu.computerdetails.form;

import by.gstu.computerdetails.entity.Cluster;
import by.gstu.computerdetails.entity.MatrixType;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.form.helper.FormHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class AddEditClusterForm extends AbstractDataForm {
    private JPanel rootPanel;

    private JTextField clusterNameField;
    private JTextField clusterDescriptionField;

    private JTextField diagonalField;
    private JTextField guaranteeField;
    private JTextField priceField;
    private JComboBox<ScreenResolution> resolutionCb;
    private JComboBox<MatrixType> matrixCb;

    private JButton saveBtn;

    public AddEditClusterForm(final Cluster oldCluster) {
        List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
        List<MatrixType> types = MATRIX_TYPE_DAO.findAll();

        for (ScreenResolution resolution : resolutions) {
            resolutionCb.addItem(resolution);
        }

        for (MatrixType type : types) {
            matrixCb.addItem(type);
        }

        if (oldCluster != null) {
            clusterNameField.setText(oldCluster.getName());
            clusterDescriptionField.setText(oldCluster.getDescription());

            Monitor proto = oldCluster.getPrototype();

            diagonalField.setText(proto.getDiagonal() + "");
            guaranteeField.setText(proto.getGuaranteePeriod() + "");
            priceField.setText(proto.getPrice() + "");
            resolutionCb.setSelectedItem(proto.getScreenResolution());
            matrixCb.setSelectedItem(proto.getMatrixType());
        }

        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = clusterNameField.getText();
                    String description = clusterDescriptionField.getText();

                    double diagonal = Double.valueOf(diagonalField.getText());
                    int guarantee = Integer.valueOf(guaranteeField.getText());
                    BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceField.getText()));
                    ScreenResolution screenResolution = (ScreenResolution) resolutionCb.getSelectedItem();
                    MatrixType matrixType = (MatrixType) matrixCb.getSelectedItem();

                    Cluster newCluster = new Cluster(name, description);
                    Monitor proto = oldCluster == null ? new Monitor() : oldCluster.getPrototype();

                    proto.setName("prototype");
                    proto.setGuaranteePeriod(guarantee);
                    proto.setDiagonal(diagonal);
                    proto.setPrice(price);
                    proto.setScreenResolution(screenResolution);
                    proto.setMatrixType(matrixType);
                    proto.setProto(true);

                    if (oldCluster != null) {
                        newCluster.setId(oldCluster.getId());
                    } else {
                        MONITOR_DAO.saveOrUpdate(proto);
                    }

                    newCluster.setPrototype(proto);
                    CLUSTER_DAO.saveOrUpdate(newCluster);
                } catch (RuntimeException exc) {
                    FormHelper.showError("Значения полей введены некорректно. Попробуйте еще раз.", "Некорректный ввод");
                    return;
                }

                if (oldCluster == null) {
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
