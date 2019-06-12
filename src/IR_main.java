public class IR_main {

    public static void main(String[] args) {
        
        IR_creatLinkedList x = new IR_creatLinkedList();
        
        for (int i=2; i<20; i++){//243
            x.fillTerms(i);
        }
        
        System.out.println(x.report(2));
        
        String q = "استخدام الحاسب الآلي";
        x.fillQuery(q);
        x.similarityNode(q,'i'); // {i, c, j}
       
        System.out.println(x.report(243));
        x.similarityReport();
       
    }
  
 }

