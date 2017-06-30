/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Business.TreeBusiness;
import java.io.FileNotFoundException;

/**
 *
 * @author mary_
 */
public class Tree {

    public NodeTree root;
    private int pos;
    private boolean founded;

    public Tree() {
        pos = 1;
        founded = false;
    }

    public void insert(String word) {
        root = insert(word, root);
        pos++;
    }

    public int getAscii(String word, int pos) {
        char character = word.charAt(pos);
        int ascii = (int) character;
        return ascii;
    }

    private NodeTree insert(String word, NodeTree t) {
        if (t == null) {
            t = new NodeTree();
            String last = word.substring(word.length() - 1, word.length());
            if (last.equals(",") || last.equals(";") || last.equals(".") || last.equals(":")) {
                t.getPositions().add(new Position(pos, last));
                String newWord = word.substring(0, word.length() - 1);
                t.setWord(newWord);
            } else {
                t.getPositions().add(new Position(pos, ""));
                t.setWord(word);
            }

        } else if (getAscii(word, 0) < getAscii(t.getWord(), 0)) {
            t.setLeftNode(insert(word, t.getLeftNode()));
            if (height(t.getLeftNode()) - height(t.getRightNode()) == 2) {
                if (word.compareTo(t.getLeftNode().getWord()) < 0) {
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }
        } else if (getAscii(word, 0) > getAscii(t.getWord(), 0)) {
            t.setRightNode(insert(word, t.getRightNode()));
            if (height(t.getRightNode()) - height(t.getLeftNode()) == 2) {
                if (word.compareTo(t.getRightNode().getWord()) > 0) {
                    t = rotateWithRightChild(t);
                } else {
                    t = doubleWithRightChild(t);
                }
            }
        } else {
            String last = word.substring(word.length() - 1, word.length());
            String newWord = "";
            boolean charact = false;
            if (last.equals(",") || last.equals(";") || last.equals(".") || last.equals(":")) {
                newWord = word.substring(0, word.length() - 1);
                charact = true;
            } else {
                newWord = word;
            }
            if (newWord.equalsIgnoreCase(t.getWord())) {
                if (charact) {
                    t.getPositions().add(new Position(pos, last));
                } else {
                    t.getPositions().add(new Position(pos, ""));
                }
            } else {
                boolean ready = true;
                boolean min = false;
                String minLeng;
                if (word.length() > t.getWord().length()) {
                    minLeng = t.getWord();
                } else {
                    min = true;
                    minLeng = word;
                }
                for (int i = 1; i < minLeng.length() && ready == true; i++) {
                    if (getAscii(word, i) < getAscii(t.getWord(), i)) {
                        ready = false;
                        t.setLeftNode(insert(word, t.getLeftNode()));
                        if (height(t.getLeftNode()) - height(t.getRightNode()) == 2) {
                            if (word.compareTo(t.getLeftNode().getWord()) < 0) {
                                t = rotateWithLeftChild(t);
                            } else {
                                t = doubleWithLeftChild(t);
                            }
                        }
                    } else if (getAscii(word, i) > getAscii(t.getWord(), i)) {
                        t.setRightNode(insert(word, t.getRightNode()));
                        ready = false;
                        if (height(t.getRightNode()) - height(t.getLeftNode()) == 2) {
                            if (word.compareTo(t.getRightNode().getWord()) > 0) {
                                t = rotateWithRightChild(t);
                            } else {
                                t = doubleWithRightChild(t);
                            }
                        }
                    }
                    if (ready) {
                        if (min) {
                            t.setLeftNode(insert(word, t.getLeftNode()));
                            if (height(t.getLeftNode()) - height(t.getRightNode()) == 2) {
                                if (word.compareTo(t.getLeftNode().getWord()) < 0) {
                                    t = rotateWithLeftChild(t);
                                } else {
                                    t = doubleWithLeftChild(t);
                                }
                            }
                        } else {
                            t.setRightNode(insert(word, t.getRightNode()));
                            if (height(t.getRightNode()) - height(t.getLeftNode()) == 2) {
                                if (word.compareTo(t.getRightNode().getWord()) > 0) {
                                    t = rotateWithRightChild(t);
                                } else {
                                    t = doubleWithRightChild(t);
                                }
                            }
                        }
                    }
                }
            }
        }
        t.setHeight(max(height(t.getLeftNode()), height(t.getRightNode())) + 1);
        return t;
    }

    private int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    private NodeTree rotateWithLeftChild(NodeTree k2) {
        NodeTree k1 = k2.getLeftNode();
        k2.setLeftNode(k1.getRightNode());
        k1.setRightNode(k2);
        k2.setHeight(max(height(k2.getLeftNode()), height(k2.getRightNode()) + 1));
        k1.setHeight(max(height(k1.getLeftNode()), k2.getHeight()) + 1);
        return k1;
    }

    private NodeTree rotateWithRightChild(NodeTree k1) {
        NodeTree k2 = k1.getRightNode();
        k1.setRightNode(k2.getLeftNode());
        k2.setLeftNode(k1);
        k1.setHeight(max(height(k1.getLeftNode()), height(k1.getRightNode())) + 1);
        k2.setHeight(max(height(k2.getRightNode()), k1.getHeight()) + 1);
        return k2;
    }

    private NodeTree doubleWithLeftChild(NodeTree k3) {
        k3.setLeftNode(rotateWithRightChild(k3.getLeftNode()));
        return rotateWithLeftChild(k3);
    }

    private NodeTree doubleWithRightChild(NodeTree k1) {
        k1.setRightNode(rotateWithLeftChild(k1.getRightNode()));
        return rotateWithRightChild(k1);
    }

    private int height(NodeTree t) {
        return t == null ? -1 : t.getHeight();
    }

    public void saveNodes(NodeTree nodeTree, String path) throws FileNotFoundException {
        TreeBusiness treeBusiness = new TreeBusiness(path); //+ //".txt");
        if (nodeTree != null) {
            treeBusiness.saveTree(nodeTree);
            saveNodes(nodeTree.getLeftNode(), path);
            saveNodes(nodeTree.getRightNode(), path);
        }
    }

    public String searchWord(String searchWord) {
        founded = false;
        return search(root, searchWord);
    }

    private String search(NodeTree node, String searchWord) {
        String word = "This word not exists!";
        if (node == null) {
            return word;
        } else if (node.getWord().equals(searchWord)) {
            founded = true;
            String posisiones = "";
            for (int i = 0; i < node.getPositions().size(); i++) {
                posisiones += node.getPositions().get(i).getPos() + "-";
            }
            word = "ASCII: " + getAscii(node.getWord(), 0) + "/Word: " + node.getWord() + "/Positions: [" + posisiones.substring(0, posisiones.length() - 1) + "]";
            return word;
        }
        word = search(node.getLeftNode(), searchWord);
        if (founded) {
            return word;
        }
        word = search(node.getRightNode(), searchWord);

        return word;
    }
}
