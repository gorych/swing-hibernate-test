package by.gstu.computerdetails.form;

import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.dao.impl.ScreenResolutionDaoImpl;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class AddEditForm extends JFrame {

    private static final MonitorDao MONITOR_DAO;
    private static final ScreenResolutionDao SCREEN_RESOLUTION_DAO;

    static {
        MONITOR_DAO = new MonitorDaoImpl();
        SCREEN_RESOLUTION_DAO = new ScreenResolutionDaoImpl();
    }

    private JPanel rootPanel;

    private JTextField nameField;
    private JTextField diagonalField;
    private JTextField guaranteeField;
    private JTextField priceField;

    private JButton saveBtn;
    private JComboBox<ScreenResolution> resolutionCb;

    public AddEditForm() {
        List<ScreenResolution> resolutions = SCREEN_RESOLUTION_DAO.findAll();
        for (ScreenResolution resolution : resolutions) {
            resolutionCb.addItem(resolution);
        }

        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String name = nameField.getText();
                    double diagonal = Double.valueOf(diagonalField.getText());
                    int guarantee = Integer.valueOf(guaranteeField.getText());
                    BigDecimal price = BigDecimal.valueOf(Long.valueOf(priceField.getText()));
                    ScreenResolution screenResolution = (ScreenResolution) resolutionCb.getSelectedItem();

                    MONITOR_DAO.saveOrUpdate(new Monitor(name, price, diagonal, guarantee, screenResolution));
                } catch (RuntimeException exc) {
                    JOptionPane.showConfirmDialog(
                            null,
                            "Значения полей введены некорректно. Попробуйте еще раз.",
                            "Некорректный ввод",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int success = JOptionPane.showConfirmDialog(
                        null,
                        "Запись успешно сохранена. Хотите добавить еще одну?",
                        "Успешное добавление",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                );

                if (success == JOptionPane.NO_OPTION || success == JOptionPane.CLOSED_OPTION) {
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    topFrame.setVisible(false);
                    topFrame.dispose();
                } else {
                    resetFieldValues();
                }
            }

            private void resetFieldValues() {
                Component[] components = rootPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JTextField) {
                        JTextField field = (JTextField) component;
                        field.setText(org.apache.commons.lang3.StringUtils.EMPTY);
                    }

                    if (component instanceof JComboBox) {
                        JComboBox cb = (JComboBox) component;
                        if (cb.getItemCount() > 0) {
                            cb.setSelectedIndex(0);
                        }
                    }
                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
