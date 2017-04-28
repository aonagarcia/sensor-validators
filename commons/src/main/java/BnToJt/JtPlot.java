/*    */ package BnToJt;
/*    */ 
/*    */ import Network.HyperGraph;
/*    */ import Network.JoinForest;
/*    */ import Network.MATH;
/*    */ import java.awt.Frame;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JtPlot
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */     throws IOException
/*    */   {
/* 19 */     if (paramArrayOfString.length != 1) {
/* 20 */       System.out.println("Use: java BnToJt.JtPlot jt_file");
/* 21 */       System.exit(0);
/*    */     }
/*    */     
/*    */ 
/* 25 */     JoinForest localJoinForest1 = JoinForest.load(paramArrayOfString[0]);
/* 26 */     JoinForest localJoinForest2 = localJoinForest1;
/*    */     
/*    */ 
/* 29 */     Frame localFrame1 = new Frame();
/* 30 */     localFrame1.setSize(600, 400);
/* 31 */     localFrame1.add(new HgCan(new HyperGraph(localJoinForest2)));
/* 32 */     localFrame1.setTitle("Cluster tree");
/* 33 */     localFrame1.setVisible(true);
/*    */     
/*    */ 
/* 36 */     int i = localJoinForest2.getNodeCount();
/* 37 */     for (int j = 0; j < i - 1; j++) {
/* 38 */       int[] arrayOfInt1 = localJoinForest2.getCqMember(j);
/* 39 */       for (int k = j + 1; k < i; k++) {
/* 40 */         int[] arrayOfInt2 = localJoinForest2.getCqMember(k);
/* 41 */         int[] arrayOfInt3 = MATH.getIntersection(arrayOfInt1, arrayOfInt2);
/* 42 */         if (arrayOfInt3 != null) { localJoinForest2.addNeighbor(j, k, arrayOfInt3);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 47 */     Frame localFrame2 = new Frame();
/* 48 */     localFrame2.setSize(600, 400);
/* 49 */     localFrame2.setLocation(600, 0);
/* 50 */     localFrame2.add(new HgCan(new HyperGraph(localJoinForest2)));
/* 51 */     localFrame2.setTitle("Junction graph");
/* 52 */     localFrame2.setVisible(true);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/JtPlot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */