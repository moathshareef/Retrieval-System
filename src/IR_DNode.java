public class IR_DNode {
    //double linked list with dummy node and circular end.

    public String word;
    public String stem;
    public double tf;
    public double df;
    public double wieght;
    public double idf;
    public double similarity;
    
    public IR_DNode next;
    public IR_DNode previous;

    public IR_DNode(String word,String stem,double tf,double df,double idf,double wieght){
        this.word = word;
        this.stem = stem;
        this.tf = tf;
        this.df = df;
        this.idf = idf;
        this.wieght = wieght;
        next = null;
        previous = null;
    }
    
    public IR_DNode(double similarity){
        this.similarity = similarity;
    }
}
