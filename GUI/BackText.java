/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.TreeBusiness;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author mary_
 */
public class BackText extends JDialog {

    private String text;
    private JTextArea message;
    private JScrollPane jscrollPane;
    private TreeBusiness treeBusiness;

    public BackText(String text) {

        this.setSize(600, 600);
        this.setLocation(250, 60);
        this.setLayout(null);
        this.text = text;
        init();
    }

    private void init() {

        this.treeBusiness = new TreeBusiness(text);
        this.message = new JTextArea();
        this.message.setEditable(false);
        this.jscrollPane = new JScrollPane();
        this.jscrollPane.setViewportView(this.message);
        this.jscrollPane.setBounds(0, 0, 600, 600);
        this.message.setEditable(false);
        this.add(this.jscrollPane);

        convert();
    }

    public void convert() {
        try {
            String b = this.treeBusiness.backToText();

            this.message.setText(b);
        } catch (IOException ex) {
            Logger.getLogger(BackText.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
