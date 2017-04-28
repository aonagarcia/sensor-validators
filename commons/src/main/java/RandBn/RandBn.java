/*    */ package RandBn;
/*    */ 
/*    */ import Network.BayesNet;
/*    */ import Network.DirectGraph;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ public class RandBn
/*    */ {
/*    */   public static void main(String[] paramArrayOfString) throws IOException
/*    */   {
/* 14 */     BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(System.in));
/*    */     int i;
/*    */     int j;
/*    */     int k;
/* 18 */     String str; if (paramArrayOfString.length < 1) {
/* 19 */       System.out.println("Enter # nodes, # roots, max indegree and full bn file path:");
/*    */       
/* 21 */       localObject = Network.UTIL.getStringList(localBufferedReader.readLine());
/*    */       
/* 23 */       i = Integer.parseInt(localObject[0]);
/* 24 */       j = Integer.parseInt(localObject[1]);
/* 25 */       k = Integer.parseInt(localObject[2]);
/* 26 */       str = new String(localObject[3]);
/*    */     }
/*    */     else {
/* 29 */       i = Integer.parseInt(paramArrayOfString[0]);
/* 30 */       j = Integer.parseInt(paramArrayOfString[1]);
/* 31 */       k = Integer.parseInt(paramArrayOfString[2]);
/* 32 */       str = new String(paramArrayOfString[3]);
/*    */     }
/* 34 */     System.out.println("n=" + i + " r=" + j + " m=" + k + " file=" + str);
/*    */     
/* 36 */     Object localObject = null;
/* 37 */     localObject = new BayesNet(new DirectGraph(i, k, j));
/* 38 */     int m = ((BayesNet)localObject).getNodeCount();
/* 39 */     if (m > 0) {
/* 40 */       ((BayesNet)localObject).setPos(450, 400);
/* 41 */       System.out.println("Positions set.");
/* 42 */       ((BayesNet)localObject).setRandCondProb();
/* 43 */       System.out.println("Rand probs set.");
/* 44 */       for (int n = 0; n < m; n++)
/* 45 */         ((BayesNet)localObject).setLabel(n, new String("v" + n));
/* 46 */       System.out.println("Labels set.");
/*    */       try {
/* 48 */         PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(str));
/* 49 */         System.out.println("Saving the current network into " + str);
/* 50 */         ((BayesNet)localObject).save(localPrintWriter);
/* 51 */         localPrintWriter.close();
/*    */       } catch (IOException localIOException) {
/* 53 */         System.out.println("Unable to save " + str);
/*    */       }
/* 55 */       System.out.println("Saving completed.");
/*    */     } else {
/* 57 */       System.out.println("Invalid input entered.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/RandBn/RandBn.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */