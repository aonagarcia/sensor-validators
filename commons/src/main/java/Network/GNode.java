/*    */ package Network;
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
/*    */ public class GNode
/*    */   extends HNode
/*    */ {
/* 24 */   int[] sz = null;
/* 25 */   float[] weud = null;
/*    */   
/*    */ 
/*    */ 
/*    */   void setStateCount(int[] paramArrayOfInt)
/*    */   {
/* 31 */     this.sz = UTIL.getDuplicate(paramArrayOfInt);
/*    */   }
/*    */   
/*    */   int[] getStateCount()
/*    */   {
/* 36 */     return UTIL.getDuplicate(this.sz);
/*    */   }
/*    */   
/*    */   int[] getStateCount(int[] paramArrayOfInt)
/*    */   {
/* 41 */     if (paramArrayOfInt == null) return null;
/* 42 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/* 43 */     for (int i = 0; i < paramArrayOfInt.length; i++) arrayOfInt[i] = getStateCount(paramArrayOfInt[i]);
/* 44 */     return arrayOfInt;
/*    */   }
/*    */   
/*    */   int getStateCount(int paramInt)
/*    */   {
/* 49 */     if ((this.sz == null) || (this.clq == null)) return -1;
/* 50 */     for (int i = 0; i < getCqSize(); i++) if (this.clq[i] == paramInt) return this.sz[i];
/* 51 */     return -1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   float[] getWeud()
/*    */   {
/* 58 */     return UTIL.getDuplicate(this.weud);
/*    */   }
/*    */   
/*    */   void setWeud(float[] paramArrayOfFloat)
/*    */   {
/* 63 */     this.weud = UTIL.getDuplicate(paramArrayOfFloat);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/GNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */