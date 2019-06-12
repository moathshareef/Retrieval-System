
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;

public class IR_DLS {
    //double linked list with dummy node and circular end.

    public int allWord;
    
    
    public IR_DNode dummyNode = new IR_DNode("0", "0", 0, 0, 0,0);
    public IR_DNode similarityDummyNode = new IR_DNode(100.0);
    

    
    public IR_DLS() {
        dummyNode.next = dummyNode;
        dummyNode.previous = dummyNode;
        similarityDummyNode.next = similarityDummyNode;
        similarityDummyNode.previous = similarityDummyNode;
        allWord = 0;

    }
    
    IR_DNode freePointer = dummyNode;
   

    //----------DNode Function----------------
    public boolean isEmpty() {
        return dummyNode.next == dummyNode && dummyNode.previous == dummyNode;
    }
    public boolean stemIsNotExisting(String stem){
        IR_DNode k = dummyNode.next;
        while (k != dummyNode) {
            if (k.stem.equals(stem)) {
                return false;
            }
            k = k.next;
        }
        return true;
    }
    
    public String report() {
        String data = "";
        if (isEmpty()) {
            data = " There are no words . ";
        } 
        else {
            IR_DNode k = dummyNode.next;
            while (k != dummyNode) {
                data += " ( " + k.stem + " , " + k.wieght + " ) ";
                //data += k.word + " , " + k.stem + " , " + k.tf + " , " + k.df + " , " + k.idf + " , " + k.wieght +" , ";
                k=k.next;
            }
            
        }
        return data;
    }

    public void addNode(String word, String stem, double tf, double df, double idf,double wieght) {
        IR_DNode newNode = new IR_DNode(word, stem, tf, df, idf,wieght);
        
        IR_DNode pointer = dummyNode.previous;
        newNode.next = dummyNode;
        pointer.next = newNode;
        dummyNode.previous = newNode;
        newNode.previous = pointer;
        
    }
    

    public double tf(String root ,int docNum) {
        double count = 0;

        try {
            FileReader x = new FileReader("test.txt");
            BufferedReader y = new BufferedReader(x);
            String str = y.readLine();
            while (str != null) {
                int index = str.indexOf("-");
                int index2 = str.indexOf("-", index + 1);
                String numberOfDoc = str.substring(index2 + 1);
                String stem = str.substring(0, index);
                if (numberOfDoc.equals(docNum + "") && stem.equals(root)) {
                    count++;
                }
                str = y.readLine();
            }
        } 
        catch (IOException e) {
            System.out.print(e.getMessage());
        }

        return count;
    }

    public double df(String root) {
        double count = 0;
        try {
            for (int j = 2; j<243; j++) {
                FileReader x = new FileReader("test.txt");
                BufferedReader y = new BufferedReader(x);
                String str = y.readLine();
                while (str != null) {
                    int index = str.indexOf("-");
                    int index2 = str.indexOf("-", index + 1);
                    String numberOfDoc = str.substring(index2 + 1);
                    String stem = str.substring(0, index);
                    if (numberOfDoc.equals(j + "") && stem.equals(root)) {
                        count++;
                        break;
                    }
                    str = y.readLine();
                }
            }
        } 
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return count;
    }
    
    public double idf(double df) {
        return  Math.log10(19.0 / df); //241
    }

    public double wieght(double tf, double idf) {
        return tf * idf;
    }
    
    public void addSimilarityNode(double similarity) {
        IR_DNode newNode = new IR_DNode(similarity);
        IR_DNode pointer = dummyNode.previous;
        newNode.next = dummyNode;
        pointer.next = newNode;
        dummyNode.previous = newNode;
        newNode.previous = pointer;
    }
    
}
