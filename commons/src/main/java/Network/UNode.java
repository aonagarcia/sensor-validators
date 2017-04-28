/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ class UNode extends Node
/*     */ {
/*   9 */   int[] nls = null;
/*     */   
/*     */   UNode() {}
/*     */   
/*     */   UNode(UNode paramUNode)
/*     */   {
/*  15 */     super(paramUNode);
/*  16 */     this.nls = UTIL.getDuplicate(paramUNode.getNeighbor());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isNeighbor(int paramInt)
/*     */   {
/*  23 */     if (this.nls == null) return false;
/*  24 */     return MATH.member(paramInt, this.nls);
/*     */   }
/*     */   
/*     */   boolean isNeighbor(int[] paramArrayOfInt) {
/*  28 */     if (this.nls == null) return false;
/*  29 */     return MATH.isSubset(paramArrayOfInt, this.nls);
/*     */   }
/*     */   
/*     */   int getNeighborCount()
/*     */   {
/*  34 */     if (this.nls == null) return 0;
/*  35 */     return this.nls.length;
/*     */   }
/*     */   
/*     */   int getNeighbor(int paramInt)
/*     */   {
/*  40 */     if (this.nls == null) return -1;
/*  41 */     return this.nls[paramInt];
/*     */   }
/*     */   
/*     */   int[] getNeighbor() {
/*  45 */     return UTIL.getDuplicate(this.nls);
/*     */   }
/*     */   
/*     */   int getNbrIndex(int paramInt)
/*     */   {
/*  50 */     return UTIL.getArrayIndex(paramInt, this.nls);
/*     */   }
/*     */   
/*     */   int[] getOtherNeighbor(int[] paramArrayOfInt) {
/*  54 */     if (this.nls == null) return null;
/*  55 */     return MATH.setDifference(this.nls, paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void setNeighbor(int[] paramArrayOfInt) {
/*  59 */     this.nls = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void addNeighbor(int paramInt) {
/*  63 */     this.nls = MATH.addMember(paramInt, this.nls);
/*     */   }
/*     */   
/*     */   void delNeighbor()
/*     */   {
/*  68 */     this.nls = null;
/*     */   }
/*     */   
/*     */   void delNeighbor(int paramInt) {
/*  72 */     this.nls = MATH.delMember(paramInt, this.nls);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void renameNeighbor(int[] paramArrayOfInt)
/*     */   {
/*  79 */     if (this.nls == null) return;
/*  80 */     for (int i = 0; i < this.nls.length; i++) {
/*  81 */       for (int j = 0; j < paramArrayOfInt.length; j++) {
/*  82 */         if (this.nls[i] == paramArrayOfInt[j]) this.nls[i] = j;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void modifyNodeIndex(int paramInt1, int paramInt2, int paramInt3) {
/*  88 */     int i = this.nls == null ? 0 : this.nls.length;
/*  89 */     for (int j = 0; j < i; j++) if ((this.nls[j] >= paramInt1) && (this.nls[j] <= paramInt2)) this.nls[j] += paramInt3;
/*  90 */     MATH.qsort(this.nls);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadNeighbor(BufferedReader paramBufferedReader)
/*     */   {
/*  97 */     setNeighbor(UTIL.loadIntList(paramBufferedReader));
/*     */   }
/*     */   
/*     */   void saveNeighbor(PrintWriter paramPrintWriter)
/*     */   {
/* 102 */     UTIL.saveIntList(paramPrintWriter, this.nls, "neighbor");
/*     */   }
/*     */   
/*     */ 
/*     */   public void showUNode()
/*     */   {
/* 108 */     if (this.nls == null) return;
/* 109 */     for (int i = 0; i < this.nls.length; i++) {
/* 110 */       System.out.print(" " + this.nls[i]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/UNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */