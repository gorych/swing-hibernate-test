package by.gstu.computerdetails.form;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ClusterInfoArea extends JTextArea {

    public ClusterInfoArea(String text) {
        this();
        setText(text);
    }

    public ClusterInfoArea() {
        setEditable(false);
        setMargin(new Insets(50, 50, 50, 50));
        setBorder(new LineBorder(Color.black));
    }
}
