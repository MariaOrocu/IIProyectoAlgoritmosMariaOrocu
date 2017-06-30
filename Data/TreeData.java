/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Domain.BackToText;
import Domain.NodeTree;
import Domain.Tree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author mary_
 */
public class TreeData {

    private final String path;

    public TreeData(String path) {
        this.path = path;
    }

    public String showFile() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String linea = br.readLine();
        String mensaje = "";
        while (linea != null) {
            mensaje += linea + "\n";
            linea = br.readLine();
        }
        br.close();
        return mensaje;
    }

    public Tree buildTree() throws IOException {
        Tree tree = new Tree();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String linea = br.readLine();
        while (null != linea) {
            String lineM = linea.toLowerCase();
            lineM = lineM.replaceAll("\n", "");
            String[] words = lineM.split(" ");
            for (int i = 0; i < words.length; i++) {
                tree.insert(words[i]);
            }
            linea = br.readLine();
        }
        br.close();
        return tree;
    }

    public void saveTree(NodeTree nodeTree) throws FileNotFoundException {

        File file = new File(this.path);
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        PrintStream printStream = new PrintStream(fileOutputStream);
        String posisiones = "";
        for (int i = 0; i < nodeTree.getPositions().size(); i++) {
            posisiones += nodeTree.getPositions().get(i).getPos() + "-";
        }
        printStream.println(nodeTree.getWord() + "#" + posisiones);

    }

    public String getTree() throws FileNotFoundException, IOException {
        String result = "";
        File file = new File(this.path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {

            result += line + "&";

        }
        result = result.substring(0, result.length() - 1);

        return result;
    }

    public String backToText() throws IOException {
        String splitBar = this.getTree();
        String[] splitBars = splitBar.split("&");
        ArrayList<BackToText> bt = new ArrayList<BackToText>();

        for (int i = 0; i < splitBars.length; i++) {
            String[] temp = splitBars[i].split("#");
            String tempWord = temp[0], tempNumber = temp[1];
            String[] tempNumbers = tempNumber.split("-");

            for (int j = 0; j < tempNumbers.length; j++) {
                bt.add(new BackToText(tempWord, Integer.parseInt(tempNumbers[j])));
            }
        }

        BackToText[] finalResult = new BackToText[bt.size()];

        for (int i = 0; i < finalResult.length; i++) {
            finalResult[i] = bt.get(i);
        }
        return insertion(finalResult);
    }

    public String insertion(BackToText[] backToTexts) {
        String result = "";
        int j;
        BackToText aux;
        for (int i = 1; i < backToTexts.length; i++) {
            aux = backToTexts[i];
            j = i - 1;
            while ((j >= 0) && (aux.getPosition() < backToTexts[j].getPosition())) {

                backToTexts[j + 1] = backToTexts[j];
                j--;
            }
            backToTexts[j + 1] = aux;
        }
        for (int x = 0; x < backToTexts.length; x++) {
            result += backToTexts[x].getName() + " ";
        }

        return result;

    }
}
