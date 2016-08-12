package by.gstu.computerdetails.form;

import javax.swing.*;
import java.awt.*;

public class AddEditMonitorForm extends JFrame {

    public AddEditMonitorForm(String name) throws HeadlessException {
        setName(name);
    }

    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JButton button1;
    private JComboBox comboBox1;
    private JTextField textField3;

    @Override
    public Container getContentPane() {
        return mainPanel;
    }


}
