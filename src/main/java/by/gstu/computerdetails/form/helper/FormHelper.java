package by.gstu.computerdetails.form.helper;

import javax.swing.*;

public class FormHelper {

    public static void showWindow(JPanel jPanel, String formName, int width, int height) {
        JFrame root = new JFrame(formName);

        if (jPanel != null) {
            root.setContentPane(jPanel);
        }

        root.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        root.setSize(width, height);

        root.setLocationRelativeTo(null);
        root.setVisible(true);
    }

}
