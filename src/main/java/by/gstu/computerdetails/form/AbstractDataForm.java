package by.gstu.computerdetails.form;

import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.dao.impl.ScreenResolutionDaoImpl;

import javax.swing.*;
import java.awt.*;

public class AbstractDataForm {

    protected static final MonitorDao MONITOR_DAO;
    protected static final ScreenResolutionDao SCREEN_RESOLUTION_DAO;

    static {
        MONITOR_DAO = new MonitorDaoImpl();
        SCREEN_RESOLUTION_DAO = new ScreenResolutionDaoImpl();
    }

    protected void resetFieldValues(JPanel panel) {
        Component[] components = panel.getComponents();
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

    protected void closeForm(JPanel panel) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        topFrame.setVisible(false);
        topFrame.dispose();
    }

}
