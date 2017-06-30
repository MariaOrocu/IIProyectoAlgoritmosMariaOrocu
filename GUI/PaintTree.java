/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Domain.NodeTree;
import Domain.Tree;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author mary_
 */
public class PaintTree extends JPanel {

    private Tree myTree;
    private NodeTree root;
    private Graphics2D graphics2D;
    private BufferedImage bufferedImage;
    private HashMap poscitionNodes = null;
    private HashMap subtreeSizes = null;
    private int x;
    private int y;
    private boolean next;
    private int parent2child = 35, child2child = 50;
    private Dimension empty;
    private FontMetrics fontMetrics = null;
    private boolean pintar;

    public PaintTree(Tree myTree) {
        super();
        this.myTree = myTree;
        this.root = this.myTree.root;
        this.empty = new Dimension(0, 0);
        this.setBackground(Color.white);
        this.bufferedImage = new BufferedImage(9000, 9000, BufferedImage.TYPE_INT_RGB);
        poscitionNodes = new HashMap();
        subtreeSizes = new HashMap();
        this.next = true;
        this.pintar = true;
    }

    private void calcularPosiciones() {
        poscitionNodes.clear();
        subtreeSizes.clear();
        if (root != null) {
            calcularTamañoSubarbol(root);
            calculatePosition(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    private Dimension calcularTamañoSubarbol(NodeTree n) {
        if (n == null) {
            return new Dimension(0, 0);
        }
        Dimension leftSon = calcularTamañoSubarbol(n.getLeftNode());
        Dimension rigthSon = calcularTamañoSubarbol(n.getRightNode());

        int heigth = fontMetrics.getHeight() + parent2child + Math.max(leftSon.height, rigthSon.height);
        int width = leftSon.width + child2child + rigthSon.width;

        Dimension d = new Dimension(width, heigth);
        subtreeSizes.put(n, d);

        return d;
    }

    private void calculatePosition(NodeTree n, int left, int right, int top) {
        if (n == null) {
            return;
        }

        Dimension dimension = (Dimension) subtreeSizes.get(n.getLeftNode());
        if (dimension == null) {
            dimension = empty;
        }

        Dimension rd = (Dimension) subtreeSizes.get(n.getRightNode());
        if (rd == null) {
            rd = empty;
        }

        int center = 0;

        if (right != Integer.MAX_VALUE) {
            center = right - rd.width - child2child / 2;
        } else if (left != Integer.MAX_VALUE) {
            center = left + dimension.width + child2child / 2;
        }
        String positions = "";
        for (int i = 0; i < n.getPositions().size(); i++) {
            positions += n.getPositions().get(i).getPos() + ",";
        }
        int width = fontMetrics.stringWidth(n.getWord() + "[" + positions.substring(0, positions.length()) + "]");

        poscitionNodes.put(n, new Rectangle(center - width / 2 - 3, top, width + 6, fontMetrics.getHeight()));

        calculatePosition(n.getLeftNode(), Integer.MAX_VALUE, center - child2child / 2, top + fontMetrics.getHeight() + parent2child);
        calculatePosition(n.getRightNode(), center + child2child / 2, Integer.MAX_VALUE, top + fontMetrics.getHeight() + parent2child);
    }

    private void drawTree(Graphics2D g, NodeTree n, int puntox, int puntoy, int yoffs) {
        if (n == null) {
            return;
        }
        Rectangle r = (Rectangle) poscitionNodes.get(n);

        g.setColor(Color.BLACK);
        String positions = "";
        for (int i = 0; i < n.getPositions().size(); i++) {
            positions += n.getPositions().get(i).getPos() + ",";
        }
        g.fillOval(r.x, r.y - 10, n.getWord().length() * 8 + positions.length() * 8, 30);
        g.setColor(Color.WHITE);

        g.drawString(n.getWord() + "[" + positions.substring(0, positions.length() - 1) + "]", r.x + 5, (r.y + yoffs) - 2);

        g.setColor(Color.BLACK);
        if (puntox != Integer.MAX_VALUE) {
            g.drawLine(puntox, puntoy, (int) (r.x + r.width / 2), r.y);
        }
        drawTree(g, n.getLeftNode(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
        drawTree(g, n.getRightNode(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        fontMetrics = g.getFontMetrics();

        if (pintar) {
            calcularPosiciones();
            pintar = false;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        drawTree(g2d, this.myTree.root, Integer.MAX_VALUE, Integer.MAX_VALUE,
                fontMetrics.getLeading() + fontMetrics.getAscent());
        fontMetrics = null;
    }

    public Tree getMyTree() {
        return myTree;
    }

    public void setMyTree(Tree myTree) {
        this.myTree = myTree;
    }
}

