package by.gstu.computerdetails.form;

import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.dao.impl.ScreenResolutionDaoImpl;
import by.gstu.computerdetails.entity.Monitor;
import by.gstu.computerdetails.entity.ScreenResolution;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class AddEditForm extends JFrame{

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

                String name = nameField.getText();
                double diagonal = Double.valueOf(diagonalField.getText());
                int guarantee = Integer.valueOf(guaranteeField.getText());
                BigDecimal price = BigDecimal.valueOf(Long.valueOf(priceField.getText()));
                ScreenResolution screenResolution = (ScreenResolution) resolutionCb.getSelectedItem();

                MONITOR_DAO.saveOrUpdate(new Monitor(name, price, diagonal, guarantee, screenResolution));

                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Запись успешно сохранена. Хотите добавить еще одну?",
                        "Успешное добавление",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {

                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
