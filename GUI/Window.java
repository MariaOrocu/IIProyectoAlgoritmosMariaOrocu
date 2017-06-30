/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.TreeBusiness;
import Data.TreeData;
import Domain.Tree;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mary_
 */
public class Window extends JFrame implements ActionListener {

    private JButton search;
    private JButton load;
    private JButton create;
    private JButton saved;
    private JButton back;
    private PaintTree panel;
    private Tree tree;
    private JFileChooser chooser;
    private JScrollPane scroll;
    private TreeData filetree;
    private JTextArea texto;
    public String path;

    public Window() {

        super();
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();

    }

    private void init() {

        this.load = new JButton("Load File");
        this.load.setBounds(30, 30, 105, 30);
        this.load.addActionListener(this);

        this.create = new JButton("Create Tree");
        this.create.setBounds(30, 100, 105, 30);
        this.create.addActionListener(this);

        this.search = new JButton("Search");
        this.search.setBounds(30, 170, 105, 30);
        this.search.addActionListener(this);

        this.saved = new JButton("Save file");
        this.saved.setBounds(30, 240, 105, 30);
        this.saved.addActionListener(this);

        this.back = new JButton("Back to text");
        this.back.setBounds(30, 310, 105, 30);
        this.back.addActionListener(this);

        this.texto = new JTextArea();
        this.texto.setVisible(false);
        this.texto.setBounds(250, 80, 500, 200);
        this.texto.setEditable(false);
        this.add(texto);

        this.add(this.load);
        this.add(this.create);
        this.add(this.saved);
        this.add(this.back);
        this.add(this.search);
    }

    public void paint() {

        this.panel = new PaintTree(this.tree);
        this.scroll = new JScrollPane();
        this.scroll.setBounds(new Rectangle(150, 30, 800, 450));
        this.scroll.setViewportView(this.panel);
        this.scroll.getViewport().setView(this.panel);
        this.panel.setPreferredSize(new Dimension(1500, 1500));
        this.panel.repaint();
        this.panel.revalidate();
        this.add(this.scroll);
    }

    public String load() throws IOException {
        chooser = new JFileChooser();
        String rute = "";
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            rute = chooser.getSelectedFile().getAbsolutePath();
        }
        path = rute;
        return rute;
    }

    private void saveFileChooser() {
        String rute;
        chooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("txt", "TXT");
        chooser.setFileFilter(extensionFilter);
        try {
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                rute = chooser.getSelectedFile().getAbsolutePath();
                TreeBusiness treeBusiness = new TreeBusiness(rute);
                tree.saveNodes(tree.root, rute);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == load) {
            try {
                TreeBusiness tB = new TreeBusiness(load());
                String mensaje = tB.showFile();
                this.texto.setText(mensaje);
                this.texto.setVisible(true);

            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getSource() == create) {
            try {
                TreeBusiness tB = new TreeBusiness(path);
                this.tree = tB.buildTree();
                this.texto.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            paint();
            repaint();
            revalidate();
        } else if (e.getSource() == saved) {
            saveFileChooser();

        } else if (e.getSource() == back) {

            BackText backText;
            try {
                backText = new BackText(load());
                backText.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getSource() == search) {
            String searchWord = JOptionPane.showInputDialog("Type a word");
            JOptionPane.showMessageDialog(this, tree.searchWord(searchWord));
        }
    }
}
