
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IR_creatLinkedList {

    IR_DLS object = new IR_DLS();
    private final IR_DLS[] ArrayOfLinked;
    private final IR_DLS similarityNode;
    public int totalWordInDoc = 0;
    
    public IR_creatLinkedList() {
        ArrayOfLinked = new IR_DLS[244];
        for (int i = 0; i <244 ; i++) {
            ArrayOfLinked[i] = new IR_DLS();
        }  
        similarityNode = new IR_DLS();
    }
    
    
    public String report(int numberOfDoc){
        return ArrayOfLinked[numberOfDoc].report();
    }
    public void similarityReport(){
        similarityNode.freePointer = similarityNode.freePointer.next;
        while (similarityNode.freePointer != similarityNode.dummyNode){
            System.out.println(similarityNode.freePointer.similarity);
            similarityNode.freePointer = similarityNode.freePointer.next;
        }
    }
    
    
    public void similarityNode(String query,char x){
        switch (x){
            case 'i':
                for (int i = 2; i < 243; i++) {
                    similarityNode.addSimilarityNode(innerProduct(query, i));
                } break;
            case 'c':
                for (int i = 2; i < 243; i++) {
                    similarityNode.addSimilarityNode(cosine(query, i));
                } break;
            case 'j':
                for (int i = 2; i < 243; i++) {
                    similarityNode.addSimilarityNode(jaccard(query, i));
                } break;
            default : System.out.println("Enter Valid Character");
                
        }
        
    }
    
    public double innerProduct(String query ,int index){
        double similarity = 0.0;
        String[] array = query.split("\\ ");
        int i = 0;
        while (i<array.length){
            double dWieght = 0.0;
            double qWieght = 0.0;
            String stem = array[i];
            if (!ArrayOfLinked[index].isEmpty()) {
                ArrayOfLinked[index].freePointer = ArrayOfLinked[index].freePointer.next;
                while (ArrayOfLinked[index].freePointer != ArrayOfLinked[index].dummyNode) {
                    if (ArrayOfLinked[index].freePointer.stem.equals(stem)) {
                        dWieght = ArrayOfLinked[index].freePointer.wieght;
                    }
                    ArrayOfLinked[index].freePointer = ArrayOfLinked[index].freePointer.next;
                }
            }
            if (!ArrayOfLinked[243].isEmpty()) {
                ArrayOfLinked[243].freePointer = ArrayOfLinked[243].freePointer.next;
                while (ArrayOfLinked[243].freePointer != ArrayOfLinked[243].dummyNode) {
                    if (ArrayOfLinked[243].freePointer.stem.equals(stem)) {
                        qWieght = ArrayOfLinked[243].freePointer.wieght;
                    }
                    ArrayOfLinked[243].freePointer = ArrayOfLinked[243].freePointer.next;
                }
            }
            similarity += (dWieght * qWieght);
            i++;
        }
        return similarity; 
    }
    
    public double cosine(String query, int index){
        double cosinSimilarity = 0.0;
        double innerProduct = innerProduct(query , index);
        double dWieght = 0.0;
        double qWieght = 0.0;
            if (!ArrayOfLinked[index].isEmpty()) {
                ArrayOfLinked[index].freePointer = ArrayOfLinked[index].freePointer.next;
                while (ArrayOfLinked[index].freePointer != ArrayOfLinked[index].dummyNode) {
                    dWieght += Math.pow(ArrayOfLinked[index].freePointer.wieght, 2);
                    ArrayOfLinked[index].freePointer = ArrayOfLinked[index].freePointer.next;
                }
            }
            if (!ArrayOfLinked[243].isEmpty()) {
                ArrayOfLinked[243].freePointer = ArrayOfLinked[243].freePointer.next;
                while (ArrayOfLinked[243].freePointer != ArrayOfLinked[243].dummyNode) {
                    qWieght += Math.pow(ArrayOfLinked[243].freePointer.idf, 2);//idf is the cosine wieght
                    ArrayOfLinked[243].freePointer = ArrayOfLinked[243].freePointer.next;
                }
            }
        double docLength = Math.sqrt(dWieght);
        double queLength = Math.sqrt(qWieght);
        cosinSimilarity += innerProduct/(docLength * queLength);
        return cosinSimilarity;
    }
    
    public double jaccard(String query, int index){
        double jaccardSimilarity = 0.0;
        double innerProduct = innerProduct(query , index);
        double dWieght = 0.0;
        double qWieght = 0.0;
        if (!ArrayOfLinked[index].isEmpty()) {
            ArrayOfLinked[index].freePointer = ArrayOfLinked[index].freePointer.next;
            while (ArrayOfLinked[index].freePointer != ArrayOfLinked[index].dummyNode) {
                dWieght += Math.pow(ArrayOfLinked[index].freePointer.wieght, 2);
                ArrayOfLinked[index].freePointer = ArrayOfLinked[index].freePointer.next;
            }
        }
        if (!ArrayOfLinked[243].isEmpty()) {
            ArrayOfLinked[243].freePointer = ArrayOfLinked[243].freePointer.next;
            while (ArrayOfLinked[243].freePointer != ArrayOfLinked[243].dummyNode) {
                qWieght += Math.pow(ArrayOfLinked[243].freePointer.wieght, 2);
                ArrayOfLinked[243].freePointer = ArrayOfLinked[243].freePointer.next;
            }
        }
        jaccardSimilarity += innerProduct / ((dWieght + qWieght) - innerProduct);
        return jaccardSimilarity;
    }
    
    
    public void fillQuery(String query){
        double maxTF = maxTF();
        String [] array = query.split("\\ ");
        for (int i=0; i<array.length; i++){
            double tf = 0.0;
            String stem = array[i];
            for (int j=0; j<array.length; j++){
                if (array[j].equals(stem)){
                    tf++;
                }
            }
            double df = object.df(array[i]);
            double idf = object.idf(df);
            double wieght = object.wieght(tf, idf);
            double cosinWieght = ((tf/maxTF)*idf);
            //cosineWieght replace with idf on linkedlist
            //maxTF replace with df on linkedlist
            if(ArrayOfLinked[243].stemIsNotExisting(stem)){
                ArrayOfLinked[243].addNode(array[i], array[i], tf, maxTF, cosinWieght, wieght);
            }
        }
    }
    
    public double maxTF(){
        double count = 0;
        for (int i=2; i<243; i++){
            ArrayOfLinked[i].freePointer = ArrayOfLinked[i].freePointer.next;
            while (ArrayOfLinked[i].freePointer != ArrayOfLinked[i].dummyNode) {
               if (ArrayOfLinked[i].freePointer.tf > count){
                   count = ArrayOfLinked[i].freePointer.tf;
               } 
               ArrayOfLinked[i].freePointer = ArrayOfLinked[i].freePointer.next; 
            } 
        }
        return count;
    }
    
    
    public void fillTerms(int i) {
        try {
            FileReader x = new FileReader("allText\\"+i+".txt");
            BufferedReader y = new BufferedReader(x);
            String str = y.readLine();
            while (str != null) {
                int index = str.indexOf("-");
                int index2 = str.indexOf("-", index + 1);
                
                String word = str.substring(index + 1, index2);
                String numberOfDoc = str.substring(index2 + 1);
                String stem = str.substring(0, index);
                int numberOfDocINT = Integer.parseInt(numberOfDoc);
                
                double tf = object.tf(stem,numberOfDocINT);
                double df = object.df(stem);
                double idf = object.idf(df);
                double weight = object.wieght(tf,idf);
                if(ArrayOfLinked[numberOfDocINT].stemIsNotExisting(stem)){
                    ArrayOfLinked[numberOfDocINT].addNode(word, stem, tf, df, idf, weight);
                }
                str = y.readLine();
            }
        } 
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
