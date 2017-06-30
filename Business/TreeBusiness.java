/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.TreeData;
import Domain.NodeTree;
import Domain.Tree;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author mary_
 */
public class TreeBusiness {

    private TreeData treeData;

    public TreeBusiness(String path) {
        this.treeData = new TreeData(path);
    }

    public Tree buildTree() throws IOException {
        return treeData.buildTree();
    }

    public String showFile() throws FileNotFoundException, IOException {
        return this.treeData.showFile();
    }

    public void saveTree(NodeTree nodeTree) throws FileNotFoundException {

        this.treeData.saveTree(nodeTree);
    }

    public String getTree() throws FileNotFoundException, IOException {

        return this.treeData.getTree();
    }

    public String backToText() throws IOException {
        return this.treeData.backToText();
    }
}
