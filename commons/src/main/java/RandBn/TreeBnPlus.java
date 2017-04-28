/*    */ package RandBn;
/*    */ 
/*    */ import Network.BayesNet;
/*    */ import Network.DirectGraph;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreeBnPlus
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */     throws IOException
/*    */   {
/* 42 */     if (paramArrayOfString.length != 5) {
/* 43 */       System.out.println("\nUse: Java RandBn.TreeBnPlus n k m w bn-file\n\tn: # nodes\n\tk: Max # parents per node\n\tm: # values per node\n\tw: # links adding to tree");
/*    */       
/*    */ 
/*    */ 
/*    */ 
/* 48 */       System.exit(-1);
/*    */     }
/* 50 */     int i = Integer.parseInt(paramArrayOfString[0]);
/* 51 */     int j = Integer.parseInt(paramArrayOfString[1]);
/* 52 */     int k = Integer.parseInt(paramArrayOfString[2]);
/* 53 */     int m = Integer.parseInt(paramArrayOfString[3]);
/* 54 */     String str = paramArrayOfString[4];
/*    */     
/* 56 */     BayesNet localBayesNet = null;
/* 57 */     int[] arrayOfInt = null;
/*    */     do {
/* 59 */       localObject = new DirectGraph();
/* 60 */       ((DirectGraph)localObject).setTreePlus(i, j, m);
/* 61 */       localBayesNet = new BayesNet((DirectGraph)localObject);
/* 62 */       arrayOfInt = getMaxPaCount(localBayesNet);
/* 63 */       System.out.println("\nMax # parent/node = " + arrayOfInt[0] + ",  # max fmly = " + arrayOfInt[1]);
/* 64 */     } while ((arrayOfInt[0] != j) || (arrayOfInt[1] <= 0));
/*    */     
/* 66 */     Object localObject = new String[k];
/* 67 */     for (int n = 0; n < k; n++) localObject[n] = new String("s" + n);
/* 68 */     for (n = 0; n < i; n++) { localBayesNet.setState(n, (String[])localObject);
/*    */     }
/* 70 */     localBayesNet.setRandCondProb();
/* 71 */     localBayesNet.save(str);
/* 72 */     System.out.println("\nSave " + str);
/*    */   }
/*    */   
/*    */   static int[] getMaxPaCount(BayesNet paramBayesNet)
/*    */   {
/* 77 */     int[] arrayOfInt = { 0, 0 };
/* 78 */     int j; for (int i = 0; i < paramBayesNet.getNodeCount(); i++) {
/* 79 */       j = paramBayesNet.getParentCount(i);
/* 80 */       if (j > arrayOfInt[0]) arrayOfInt[0] = j;
/*    */     }
/* 82 */     for (i = 0; i < paramBayesNet.getNodeCount(); i++) {
/* 83 */       j = paramBayesNet.getParentCount(i);
/* 84 */       if (j == arrayOfInt[0]) arrayOfInt[1] += 1;
/*    */     }
/* 86 */     return arrayOfInt;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/RandBn/TreeBnPlus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */