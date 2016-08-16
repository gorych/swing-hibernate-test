package by.gstu.computerdetails.form.helper;

import by.gstu.computerdetails.config.FormConfig;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormHelper {

    public static JFrame showWindow(JPanel jPanel, String formName, int width, int height) {
        final JFrame root = new JFrame(formName);

        if (jPanel != null) {
            root.setContentPane(jPanel);
        }

        root.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        root.setSize(width, height);

        root.setLocationRelativeTo(null);
        root.setVisible(true);

        root.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                JFrame frame = (JFrame) e.getComponent();
                if (frame != null && FormConfig.APP_NAME.equals(frame.getTitle())) {

                }
            }
        });

        return root;
    }

    public static int showWarning(String msg, String caption) {
        return JOptionPane.showConfirmDialog(null, msg, caption, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public static int showError(String msg, String caption) {
        return JOptionPane.showConfirmDialog(null, msg, caption, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public static int showConfirm(String msg, String caption) {
        return JOptionPane.showConfirmDialog(null, msg, caption, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}
