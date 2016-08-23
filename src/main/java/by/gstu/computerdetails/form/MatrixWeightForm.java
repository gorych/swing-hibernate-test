package by.gstu.computerdetails.form;

import by.gstu.computerdetails.entity.MatrixType;
import by.gstu.computerdetails.form.helper.FormHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MatrixWeightForm extends AbstractDataForm {

    private JTextField weightField;
    private JButton saveWeightBtn;
    private JPanel rootPanel;

    public MatrixWeightForm(final MatrixType matrixType) {
        saveWeightBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double weight;
                try {
                    weight = Double.valueOf(weightField.getText());
                    matrixType.setWeight(weight);
                } catch (RuntimeException exc) {
                    FormHelper.showError("Вес матрицы должен быть в диапозоне от 0 до 1.", "Некорректный ввод веса");
                    return;
                }

                if (!isNormalWeight(matrixType, weight)) {
                    FormHelper.showError("Сумма весов всех типов матриц не должна превышать 1.", "Некорректный ввод веса");
                    return;
                }

                MATRIX_TYPE_DAO.saveOrUpdate(matrixType);
                MainForm.GET_INSTANCE().updateMatrixTable();

                closeForm(rootPanel);
            }
        });
    }

    private boolean isNormalWeight(MatrixType changedMatrix, double weight) {
        List<MatrixType> matrixTypes = MATRIX_TYPE_DAO.findAll();
        double sum = 0;
        for (MatrixType matrixType : matrixTypes) {
            if (changedMatrix.equals(matrixType)) {
                continue;
            }
            sum += matrixType.getWeight();
        }
        sum += weight;

        return sum <= 1;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
