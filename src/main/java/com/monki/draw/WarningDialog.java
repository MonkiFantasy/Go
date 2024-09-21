package com.monki.draw;

import javax.swing.*;
import java.awt.*;

public class WarningDialog extends JDialog {

    private JLabel warning;
    public WarningDialog(String text){
        initDialog(text);
    }

    private void initDialog(String text) {
        setBounds(500,500,500,200);
        setTitle("提示");
        setModal(true);
        //setLayout(new BorderLayout());
        warning = new JLabel(text);
        warning.setHorizontalAlignment(JLabel.CENTER);
        add(warning);
        warning.setFont(new Font("宋体", Font.ITALIC, 30));
        //warning.setText(text);
        setVisible(true);
    }

}
