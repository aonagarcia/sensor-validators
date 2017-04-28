/*    */ package Network;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DBNet
/*    */   extends BayesNet
/*    */ {
/*    */   public DBNet() {}
/*    */   
/*    */   public DBNet(DBNet paramDBNet)
/*    */   {
/* 14 */     super(paramDBNet);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setRunTimeSlice()
/*    */   {
/* 22 */     int[] arrayOfInt = getBorder(1, 2);
/* 23 */     if ((MATH.isEqualSet(arrayOfInt, getBorder(0, 0))) || (MATH.isEqualSet(arrayOfInt, getBorder(0, 1))))
/*    */     {
/* 25 */       setRunTimeSliceByFwdBdr();
/*    */     } else {
/* 27 */       setRunTimeSliceByFwdBdr();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public int[][][] loadEvidence(BufferedReader paramBufferedReader)
/*    */   {
/* 34 */     int i = UTIL.loadInt(paramBufferedReader);
/* 35 */     int[][][] arrayOfInt = new int[i][][];
/*    */     
/* 37 */     UTIL.skipLine(paramBufferedReader);
/* 38 */     int j = UTIL.loadInt(paramBufferedReader);
/* 39 */     int[][] arrayOfInt1 = new int[j][2];
/* 40 */     for (int k = 0; k < j; k++) {
/* 41 */       String[] arrayOfString = UTIL.loadStringListLine(paramBufferedReader, 2);
/* 42 */       arrayOfInt1[k][0] = getNodeByLabelFrwd(arrayOfString[0]);
/* 43 */       arrayOfInt1[k][1] = ((BNode)this.nd[arrayOfInt1[k][0]]).getStateIndex(arrayOfString[1]);
/*    */     }
/*    */     
/* 46 */     for (k = 0; k < i; k++) {
/* 47 */       UTIL.skipLine(paramBufferedReader);
/* 48 */       j = UTIL.loadInt(paramBufferedReader);
/* 49 */       arrayOfInt[k] = new int[j][2];
/* 50 */       for (m = 0; m < j; m++) {
/* 51 */         localObject = UTIL.loadStringListLine(paramBufferedReader, 2);
/* 52 */         arrayOfInt[k][m][0] = getNodeByLabelBkwd(localObject[0]);
/* 53 */         arrayOfInt[k][m][1] = ((BNode)this.nd[arrayOfInt[k][m][0]]).getStateIndex(localObject[1]);
/*    */       }
/*    */     }
/*    */     
/* 57 */     k = arrayOfInt1.length;int m = arrayOfInt[0].length;
/* 58 */     Object localObject = new int[k + m][];
/* 59 */     for (int n = 0; n < k; n++) localObject[n] = arrayOfInt1[n];
/* 60 */     for (n = 0; n < m; n++) localObject[(n + k)] = arrayOfInt[0][n];
/* 61 */     arrayOfInt[0] = localObject;
/*    */     
/* 63 */     return arrayOfInt;
/*    */   }
/*    */   
/*    */   public static DBNet loadDbn(BufferedReader paramBufferedReader)
/*    */   {
/* 68 */     DBNet localDBNet = new DBNet();
/* 69 */     int i = localDBNet.loadNodeCount(paramBufferedReader);
/* 70 */     localDBNet.setDumbNet(i);
/* 71 */     localDBNet.loadNode(paramBufferedReader);
/* 72 */     localDBNet.setParentStateCount();
/* 73 */     return localDBNet;
/*    */   }
/*    */   
/*    */   public static DBNet loadDbnSkipPb(BufferedReader paramBufferedReader)
/*    */   {
/* 78 */     DBNet localDBNet = new DBNet();
/* 79 */     int i = localDBNet.loadNodeCount(paramBufferedReader);
/* 80 */     localDBNet.setDumbNet(i);
/* 81 */     localDBNet.loadNodeSkipPb(paramBufferedReader);
/* 82 */     localDBNet.setParentStateCount();
/* 83 */     return localDBNet;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DBNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */