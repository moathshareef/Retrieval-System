public class IR_main2 {
    
    public static void main(String[] args) {
        
        IR_creatLinkedList x = new IR_creatLinkedList();
        
        String q = "استخدام الحاسب الآلي";
        x.fillQuery(q);
        x.similarityNode(q,'c'); // {i, c, j}
       
        System.out.println(x.report(2));
        x.similarityReport();
        
    }
}
