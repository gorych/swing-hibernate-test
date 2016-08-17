package by.gstu.computerdetails.form;

import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;
import by.gstu.computerdetails.form.helper.FormHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class AddEditResolutionForm extends AbstractDataForm {

    private JPanel rootPanel;
    private JTextField xField;
    private JTextField yField;
    private JButton saveBtn;

    public AddEditResolutionForm(final ScreenResolution oldResolution) {
        if (oldResolution != null) {
            xField.setText(oldResolution.getX() + "");
            yField.setText(oldResolution.getY() + "");
        }

        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = Integer.valueOf(xField.getText());
                    int y = Integer.valueOf(yField.getText());

                    ScreenResolution newResolution = new ScreenResolution(x, y);
                    if (oldResolution != null) {
                        newResolution.setId(oldResolution.getId());
                    }

                    SCREEN_RESOLUTION_DAO.saveOrUpdate(newResolution);
                } catch (RuntimeException exc) {
                    FormHelper.showError("Значения полей введены некорректно. Попробуйте еще раз.", "Некорректный ввод");
                    return;
                }

                if (oldResolution == null) {
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
