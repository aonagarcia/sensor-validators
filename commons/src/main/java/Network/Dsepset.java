/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ 
/*     */ class Dsepset
/*     */ {
/*   7 */   private String[] labl = null;
/*   8 */   private int[] indl = null;
/*     */   
/*     */   Dsepset(String[] paramArrayOfString, int[] paramArrayOfInt) {
/*  11 */     this.labl = paramArrayOfString;this.indl = paramArrayOfInt;
/*     */   }
/*     */   
/*  14 */   Dsepset(Dsepset paramDsepset) { this(paramDsepset.getLabel(), paramDsepset.getLocalIndex()); }
/*     */   
/*     */ 
/*     */   int getDsepSize()
/*     */   {
/*  19 */     if (this.indl == null) return 0;
/*  20 */     return this.indl.length;
/*     */   }
/*     */   
/*     */   int getLocalIndex(int paramInt)
/*     */   {
/*  25 */     return this.indl[paramInt];
/*     */   }
/*     */   
/*     */   int[] getLocalIndex() {
/*  29 */     return UTIL.getDuplicate(this.indl);
/*     */   }
/*     */   
/*     */   String getLabel(int paramInt)
/*     */   {
/*  34 */     return new String(this.labl[paramInt]);
/*     */   }
/*     */   
/*     */   String[] getLabel() {
/*  38 */     return UTIL.getDuplicate(this.labl);
/*     */   }
/*     */   
/*     */   String getNodeLabel(int paramInt)
/*     */   {
/*  43 */     for (int i = 0; i < this.indl.length; i++)
/*  44 */       if (this.indl[i] == paramInt) return new String(this.labl[i]);
/*  45 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void show()
/*     */   {
/*  52 */     HelpPanel.showList("sepset:\t", this.labl);
/*  53 */     HelpPanel.showList("local index: ", this.indl);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Dsepset getSepset(DirectGraph paramDirectGraph1, DirectGraph paramDirectGraph2)
/*     */   {
/*  60 */     String[] arrayOfString = null;
/*  61 */     int[] arrayOfInt = null;Object localObject = null;
/*     */     
/*  63 */     for (int i = 0; i < paramDirectGraph1.getNodeCount(); i++) {
/*  64 */       for (int j = 0; j < paramDirectGraph2.getNodeCount(); j++) {
/*  65 */         String str = paramDirectGraph1.getLabel(i);
/*  66 */         if (str.equals(paramDirectGraph2.getLabel(j))) {
/*  67 */           arrayOfString = MATH.appendMember(str, arrayOfString);
/*  68 */           arrayOfInt = MATH.appendMember(i, arrayOfInt);
/*  69 */           break;
/*     */         }
/*     */       }
/*     */     }
/*  73 */     return new Dsepset(arrayOfString, arrayOfInt);
/*     */   }
/*     */   
/*     */   static Dsepset getSepset(String paramString1, String paramString2) {
/*  77 */     BayesNet localBayesNet1 = BayesNet.loadSkipPb(paramString1);
/*  78 */     BayesNet localBayesNet2 = BayesNet.loadSkipPb(paramString2);
/*  79 */     return getSepset(localBayesNet1, localBayesNet2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isDsepnode(int paramInt)
/*     */   {
/*  86 */     return MATH.member(paramInt, this.indl);
/*     */   }
/*     */   
/*     */   boolean isDsepnode(Point paramPoint) {
/*  90 */     return (MATH.member(paramPoint.x, this.indl)) && (MATH.member(paramPoint.y, this.indl));
/*     */   }
/*     */   
/*     */   boolean isDsepnode(String paramString1, String paramString2) {
/*  94 */     return (MATH.member(paramString1, this.labl)) && (MATH.member(paramString2, this.labl));
/*     */   }
/*     */   
/*     */ 
/*     */   String[][] getDlinks(Point[] paramArrayOfPoint)
/*     */   {
/* 100 */     if (paramArrayOfPoint == null) return (String[][])null;
/* 101 */     int i = 0;
/* 102 */     for (int j = 0; j < paramArrayOfPoint.length; j++) if (isDsepnode(paramArrayOfPoint[j])) i++;
/* 103 */     if (i == 0) { return (String[][])null;
/*     */     }
/* 105 */     String[][] arrayOfString = new String[i][2];
/* 106 */     int k = 0;
/* 107 */     for (int m = 0; m < paramArrayOfPoint.length; m++) {
/* 108 */       if (isDsepnode(paramArrayOfPoint[m])) {
/* 109 */         arrayOfString[k][0] = getNodeLabel(paramArrayOfPoint[m].x);
/* 110 */         arrayOfString[(k++)][1] = getNodeLabel(paramArrayOfPoint[m].y);
/*     */       }
/*     */     }
/* 113 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Dsepset.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */