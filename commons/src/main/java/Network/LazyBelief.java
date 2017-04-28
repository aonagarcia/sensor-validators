/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LazyBelief
/*     */ {
/*     */   int[] domain;
/*     */   int[] head;
/*     */   float[] belief;
/*     */   
/*     */   LazyBelief()
/*     */   {
/*  18 */     this.domain = null;
/*  19 */     this.head = null;
/*  20 */     this.belief = null;
/*     */   }
/*     */   
/*  23 */   LazyBelief(int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat) { this.domain = paramArrayOfInt1;
/*  24 */     this.head = paramArrayOfInt2;
/*  25 */     this.belief = paramArrayOfFloat;
/*     */   }
/*     */   
/*  28 */   LazyBelief(LazyBelief paramLazyBelief) { this.domain = paramLazyBelief.domain;
/*  29 */     this.head = paramLazyBelief.head;
/*  30 */     this.belief = paramLazyBelief.belief;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static LazyBelief dupLazyBelief(LazyBelief paramLazyBelief)
/*     */   {
/*  37 */     LazyBelief localLazyBelief = new LazyBelief();
/*  38 */     localLazyBelief.domain = UTIL.getDuplicate(paramLazyBelief.domain);
/*  39 */     localLazyBelief.head = UTIL.getDuplicate(paramLazyBelief.head);
/*  40 */     localLazyBelief.belief = UTIL.getDuplicate(paramLazyBelief.belief);
/*  41 */     return localLazyBelief;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getDomain()
/*     */   {
/*  47 */     return this.domain;
/*     */   }
/*     */   
/*     */   int[] getHead() {
/*  51 */     return this.head;
/*     */   }
/*     */   
/*     */   float[] getBelief() {
/*  55 */     return this.belief;
/*     */   }
/*     */   
/*     */   int getBelSz() {
/*  59 */     return this.belief.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void lazyProduct(LazyBelief paramLazyBelief, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/*  78 */     if ((this.head == null) || (paramLazyBelief.head == null)) { this.head = null;
/*     */     }
/*     */     else {
/*  81 */       arrayOfInt1 = UTIL.getDuplicate(this.domain);
/*  82 */       for (int i = 0; i < this.head.length; i++)
/*  83 */         arrayOfInt1 = MATH.delMember(this.head[i], arrayOfInt1);
/*  84 */       arrayOfInt2 = UTIL.getDuplicate(paramLazyBelief.domain);
/*  85 */       for (j = 0; j < paramLazyBelief.head.length; j++) {
/*  86 */         arrayOfInt2 = MATH.delMember(paramLazyBelief.head[j], arrayOfInt2);
/*     */       }
/*  88 */       if ((MATH.isEqualSet(arrayOfInt1, paramLazyBelief.domain)) || (MATH.isEqualSet(arrayOfInt2, this.domain)))
/*  89 */         this.head = MATH.union(this.head, paramLazyBelief.head); else {
/*  90 */         this.head = null;
/*     */       }
/*     */     }
/*  93 */     int[] arrayOfInt1 = MATH.union(this.domain, paramLazyBelief.domain);
/*  94 */     int[] arrayOfInt2 = new int[arrayOfInt1.length];
/*  95 */     for (int j = 0; j < arrayOfInt1.length; j++) {
/*  96 */       for (int k = 0; k < paramArrayOfInt1.length; k++)
/*  97 */         if (arrayOfInt1[j] == paramArrayOfInt1[k]) {
/*  98 */           arrayOfInt2[j] = paramArrayOfInt2[k]; break;
/*     */         }
/*     */     }
/* 101 */     this.belief = MATH.productOfTwo(this.domain, this.belief, paramLazyBelief.domain, paramLazyBelief.belief, arrayOfInt1, arrayOfInt2);
/*     */     
/* 103 */     this.domain = UTIL.getDuplicate(arrayOfInt1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setMargin(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 114 */     int[] arrayOfInt1 = this.domain;
/* 115 */     int[] arrayOfInt2 = this.head;
/* 116 */     float[] arrayOfFloat1 = this.belief;
/*     */     
/* 118 */     int[] arrayOfInt3 = new int[paramArrayOfInt2.length];
/* 119 */     int i = 0;
/* 120 */     for (int j = 0; j < arrayOfInt1.length; j++) {
/* 121 */       if (MATH.member(arrayOfInt1[j], paramArrayOfInt2)) arrayOfInt3[(i++)] = j;
/*     */     }
/* 123 */     float[] arrayOfFloat2 = MATH.margin(arrayOfFloat1, paramArrayOfInt1, arrayOfInt3);
/* 124 */     this.belief = MATH.normalize(arrayOfFloat2);
/* 125 */     this.domain = UTIL.getDuplicate(paramArrayOfInt2);
/*     */     
/* 127 */     int[] arrayOfInt4 = MATH.setDifference(arrayOfInt1, paramArrayOfInt2);
/* 128 */     if (MATH.isSubset(arrayOfInt4, arrayOfInt2)) {
/* 129 */       this.head = MATH.setDifference(arrayOfInt2, arrayOfInt4);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static LazyBelief load(BufferedReader paramBufferedReader)
/*     */   {
/* 136 */     int[] arrayOfInt1 = UTIL.loadIntList(paramBufferedReader);
/* 137 */     int[] arrayOfInt2 = UTIL.loadIntList(paramBufferedReader);
/* 138 */     int i = UTIL.loadInt(paramBufferedReader);
/* 139 */     float[] arrayOfFloat = UTIL.loadRealList(paramBufferedReader, i);
/* 140 */     LazyBelief localLazyBelief = new LazyBelief(arrayOfInt1, arrayOfInt2, arrayOfFloat);
/* 141 */     return localLazyBelief;
/*     */   }
/*     */   
/*     */   void save(PrintWriter paramPrintWriter)
/*     */   {
/* 146 */     UTIL.saveIntList(paramPrintWriter, this.domain, "domain_vars");
/* 147 */     UTIL.saveIntList(paramPrintWriter, this.head, "head_vars");
/* 148 */     paramPrintWriter.println(this.belief.length + " #_belief_values");
/* 149 */     UTIL.saveRealList(paramPrintWriter, this.belief);
/*     */   }
/*     */   
/*     */ 
/*     */   void showLazyBelief()
/*     */   {
/* 155 */     UTIL.showList("domain=", this.domain);
/* 156 */     UTIL.showList("head=", this.head);
/* 157 */     UTIL.showList("belief=", this.belief);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LazyBelief.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */