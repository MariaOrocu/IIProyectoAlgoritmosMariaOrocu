/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;

/**
 *
 * @author mary_
 */
public class NodeTree {

    private String word;
    private NodeTree leftNode, rightNode;
    private int height;
    private ArrayList<Position> positions;

    public NodeTree(String word, NodeTree leftNode, NodeTree rightNode, int height, ArrayList<Position> positions) {
        this.word = word;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.height = height;
        this.positions = positions;
    }

    public NodeTree() {
        this.word = "";
        this.leftNode = null;
        this.rightNode = null;
        this.height = -1;
        this.positions = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public NodeTree getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(NodeTree leftNode) {
        this.leftNode = leftNode;
    }

    public NodeTree getRightNode() {
        return rightNode;
    }

    public void setRightNode(NodeTree rightNode) {
        this.rightNode = rightNode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }
}
