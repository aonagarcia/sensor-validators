/*    */ package Bind;
/*    */ 
/*    */ import Network.UTIL;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class CellMb extends CellId
/*    */ {
/*  8 */   String[] nb = null;
/*  9 */   String[][] sepset = (String[][])null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   String[] getNb()
/*    */   {
/* 18 */     return UTIL.getDuplicate(this.nb);
/*    */   }
/*    */   
/*    */   String getNb(int paramInt) {
/* 22 */     return new String(this.nb[paramInt]);
/*    */   }
/*    */   
/*    */   int getNbCount() {
/* 26 */     return this.nb == null ? 0 : this.nb.length;
/*    */   }
/*    */   
/*    */   String[][] getSepset() {
/* 30 */     return UTIL.getDuplicate(this.sepset);
/*    */   }
/*    */   
/*    */   String[] getSepset(int paramInt) {
/* 34 */     return UTIL.getDuplicate(this.sepset[paramInt]);
/*    */   }
/*    */   
/*    */   int getSepsetSize(int paramInt)
/*    */   {
/* 39 */     return this.sepset[paramInt] == null ? 0 : this.sepset[paramInt].length;
/*    */   }
/*    */   
/*    */ 
/*    */   void setNb(String[] paramArrayOfString)
/*    */   {
/* 45 */     this.nb = UTIL.getDuplicate(paramArrayOfString);
/*    */   }
/*    */   
/*    */   void setSepset(String[][] paramArrayOfString) {
/* 49 */     this.sepset = UTIL.getDuplicate(paramArrayOfString);
/*    */   }
/*    */   
/*    */   void addSepset(String[] paramArrayOfString)
/*    */   {
/* 54 */     this.sepset = Network.MATH.appendMember(paramArrayOfString, this.sepset);
/*    */   }
/*    */   
/*    */ 
/*    */   void showCellMb()
/*    */   {
/* 60 */     showCellId();
/* 61 */     System.out.println(UTIL.listToStr(" nb=", this.nb));
/* 62 */     for (int i = 0; i < getNbCount(); i++) {
/* 63 */       System.out.println(UTIL.listToStr(" sepset " + i + "=", this.sepset[i]));
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/CellMb.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */