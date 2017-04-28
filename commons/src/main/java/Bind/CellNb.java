/*    */ package Bind;
/*    */ 
/*    */ import Network.UTIL;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class CellNb
/*    */   extends CellId
/*    */ {
/*  9 */   String[] sepset = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   String[] getSepset()
/*    */   {
/* 18 */     return UTIL.getDuplicate(this.sepset);
/*    */   }
/*    */   
/*    */   int getSepsetSize() {
/* 22 */     if (this.sepset == null) return 0;
/* 23 */     return this.sepset.length;
/*    */   }
/*    */   
/*    */ 
/*    */   void setSepset(String[] paramArrayOfString)
/*    */   {
/* 29 */     this.sepset = UTIL.getDuplicate(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */   void showCellNb()
/*    */   {
/* 35 */     showCellId();
/* 36 */     System.out.println(UTIL.listToStr(" sepset=", this.sepset));
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/CellNb.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */