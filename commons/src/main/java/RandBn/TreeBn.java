/*    */ package RandBn;
/*    */ 
/*    */ import Network.BayesNet;
/*    */ import Network.DirectGraph;
/*    */ import Network.JoinForest;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreeBn
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */     throws IOException
/*    */   {
/* 19 */     if (paramArrayOfString.length != 3) {
/* 20 */       System.out.println("\nUse: java RandBn.TreeBn k m bn-path\n\tk: # parents per non-root node\n\tm: # of levels");
/*    */       
/*    */ 
/* 23 */       System.exit(-1);
/*    */     }
/* 25 */     int i = Integer.parseInt(paramArrayOfString[0]);
/* 26 */     int j = Integer.parseInt(paramArrayOfString[1]);
/*    */     
/* 28 */     DirectGraph localDirectGraph = new DirectGraph(i, j);
/*    */     
/*    */ 
/* 31 */     BayesNet localBayesNet = new BayesNet(localDirectGraph);
/* 32 */     localBayesNet.setRandCondProb();
/* 33 */     localBayesNet.save(paramArrayOfString[2]);
/*    */     
/* 35 */     System.out.println("\nTotal # of BN nodes = " + localBayesNet.getNodeCount());
/* 36 */     System.out.println("Total size of BN CPTs = " + localBayesNet.getCptSize());
/*    */     
/* 38 */     JoinForest localJoinForest = JoinForest.bnToJt(localBayesNet);
/* 39 */     localJoinForest.save(paramArrayOfString[2].replaceAll(".bn", ".jt"));
/*    */     
/* 41 */     System.out.println("Total # of JT clusters = " + localJoinForest.getNodeCount());
/* 42 */     System.out.println("Total JT potential size = " + localJoinForest.getPotSize());
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/RandBn/TreeBn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */