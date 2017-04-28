/*     */ package Network;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class LazyBelSet
/*     */ {
/*     */   LazyBelief[] btlist;
/*     */   
/*     */   LazyBelSet()
/*     */   {
/*  11 */     this.btlist = null;
/*     */   }
/*     */   
/*  14 */   LazyBelSet(LazyBelief[] paramArrayOfLazyBelief) { this.btlist = paramArrayOfLazyBelief; }
/*     */   
/*     */   LazyBelSet(LazyBelSet paramLazyBelSet) {
/*  17 */     this.btlist = paramLazyBelSet.btlist;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static LazyBelSet dupLazyBelSet(LazyBelSet paramLazyBelSet)
/*     */   {
/*  24 */     LazyBelSet localLazyBelSet = new LazyBelSet();
/*  25 */     if (paramLazyBelSet.btlist == null) { return localLazyBelSet;
/*     */     }
/*  27 */     int i = paramLazyBelSet.btlist.length;
/*  28 */     localLazyBelSet.btlist = new LazyBelief[i];
/*  29 */     for (int j = 0; j < i; j++) localLazyBelSet.btlist[j] = LazyBelief.dupLazyBelief(paramLazyBelSet.btlist[j]);
/*  30 */     return localLazyBelSet;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getDomain(int paramInt)
/*     */   {
/*  36 */     return this.btlist[paramInt].getDomain();
/*     */   }
/*     */   
/*     */   int[] getHead(int paramInt) {
/*  40 */     return this.btlist[paramInt].getHead();
/*     */   }
/*     */   
/*     */   float[] getBelief(int paramInt) {
/*  44 */     return this.btlist[paramInt].getBelief();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getPotenCount()
/*     */   {
/*  51 */     if (this.btlist == null) return 0;
/*  52 */     return this.btlist.length;
/*     */   }
/*     */   
/*     */ 
/*     */   int getPotenSize(int paramInt)
/*     */   {
/*  58 */     return this.btlist[paramInt].getBelSz();
/*     */   }
/*     */   
/*     */   int[] getPotenSize()
/*     */   {
/*  63 */     if (this.btlist == null) return null;
/*  64 */     int i = getPotenCount();
/*  65 */     int[] arrayOfInt = new int[i];
/*  66 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getPotenSize(j);
/*  67 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   void addPotential(LazyBelief paramLazyBelief)
/*     */   {
/*     */     int i;
/*     */     
/*  75 */     if (this.btlist == null) i = 0; else
/*  76 */       i = this.btlist.length;
/*  77 */     LazyBelief[] arrayOfLazyBelief = new LazyBelief[i + 1];
/*     */     
/*  79 */     for (int j = 0; j < i; j++) arrayOfLazyBelief[j] = this.btlist[j];
/*  80 */     arrayOfLazyBelief[i] = paramLazyBelief;
/*  81 */     this.btlist = arrayOfLazyBelief;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void delPotential(int paramInt)
/*     */   {
/*  88 */     if (this.btlist.length == 1) { this.btlist = null;
/*     */     } else {
/*  90 */       LazyBelief[] arrayOfLazyBelief = new LazyBelief[this.btlist.length - 1];
/*  91 */       int i = 0;
/*  92 */       for (int j = 0; j < this.btlist.length; j++)
/*  93 */         if (paramInt != j) arrayOfLazyBelief[(i++)] = this.btlist[j];
/*  94 */       this.btlist = arrayOfLazyBelief;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void delPotential(int[] paramArrayOfInt)
/*     */   {
/* 102 */     if ((paramArrayOfInt == null) || (this.btlist == null)) { return;
/*     */     }
/* 104 */     int i = this.btlist.length - paramArrayOfInt.length;
/* 105 */     if (i == 0) {
/* 106 */       this.btlist = null;return;
/*     */     }
/*     */     
/* 109 */     LazyBelief[] arrayOfLazyBelief = new LazyBelief[i];
/* 110 */     int j = 0;
/* 111 */     for (int k = 0; k < this.btlist.length; k++)
/* 112 */       if (!MATH.member(k, paramArrayOfInt)) arrayOfLazyBelief[(j++)] = this.btlist[k];
/* 113 */     this.btlist = arrayOfLazyBelief;
/*     */   }
/*     */   
/*     */ 
/*     */   void unionPotential(LazyBelSet paramLazyBelSet)
/*     */   {
/* 119 */     if ((paramLazyBelSet == null) || (paramLazyBelSet.btlist == null)) { return;
/*     */     }
/* 121 */     if (this.btlist == null) {
/* 122 */       this.btlist = paramLazyBelSet.btlist;
/* 123 */       return;
/*     */     }
/*     */     
/* 126 */     int i = this.btlist.length + paramLazyBelSet.btlist.length;
/* 127 */     LazyBelief[] arrayOfLazyBelief = new LazyBelief[i];
/* 128 */     int j = 0;
/* 129 */     for (int k = 0; k < this.btlist.length; k++)
/* 130 */       arrayOfLazyBelief[(j++)] = this.btlist[k];
/* 131 */     for (k = 0; k < paramLazyBelSet.btlist.length; k++)
/* 132 */       arrayOfLazyBelief[(j++)] = paramLazyBelSet.btlist[k];
/* 133 */     this.btlist = arrayOfLazyBelief;
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
/*     */   LazyBelSet setMargin(int[] paramArrayOfInt)
/*     */   {
/* 150 */     LazyBelSet localLazyBelSet = new LazyBelSet();
/* 151 */     int i = 1;
/*     */     
/*     */ 
/* 154 */     while ((i != 0) && (this.btlist != null)) {
/* 155 */       i = 0;
/* 156 */       int[] arrayOfInt = null;
/* 157 */       for (int j = 0; j < this.btlist.length; j++) {
/* 158 */         if (this.btlist[j].head != null)
/*     */         {
/* 160 */           int k = this.btlist[j].head[0];
/* 161 */           if ((this.btlist[j].head.length == 1) && (!MATH.member(k, paramArrayOfInt))) {
/* 162 */             int m = 1;
/*     */             
/* 164 */             for (int n = 0; n < this.btlist.length; n++) {
/* 165 */               if ((n != j) && 
/* 166 */                 (MATH.member(k, this.btlist[n].domain))) {
/* 167 */                 m = 0; break;
/*     */               }
/*     */             }
/* 170 */             if (m != 0) {
/* 171 */               arrayOfInt = MATH.appendMember(j, arrayOfInt);
/* 172 */               localLazyBelSet.addPotential(this.btlist[j]);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 177 */       if (arrayOfInt != null) {
/* 178 */         delPotential(arrayOfInt);
/* 179 */         i = 1;
/*     */       }
/*     */     }
/* 182 */     return localLazyBelSet;
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
/*     */ 
/*     */   void setMargin(LNode paramLNode, int[] paramArrayOfInt)
/*     */   {
/* 202 */     int[] arrayOfInt1 = paramLNode.getCqMember();
/* 203 */     int[] arrayOfInt2 = paramLNode.getStateCount();
/* 204 */     setMargin(arrayOfInt1, arrayOfInt2, paramArrayOfInt);
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
/*     */   void setMargin(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*     */   {
/* 222 */     setMargin(paramArrayOfInt3);
/*     */     
/*     */ 
/* 225 */     LazyBelSet localLazyBelSet = new LazyBelSet();
/* 226 */     while (this.btlist != null) {
/* 227 */       LazyBelief localLazyBelief = new LazyBelief(this.btlist[0]);
/* 228 */       delPotential(0);
/*     */       
/* 230 */       int i = 0;int j = 0;
/* 231 */       while ((i < localLazyBelief.domain.length) && (MATH.member(j = localLazyBelief.domain[i], paramArrayOfInt3))) i++;
/* 232 */       if (i == localLazyBelief.domain.length) {
/* 233 */         localLazyBelSet.addPotential(localLazyBelief);
/*     */       }
/*     */       else {
/* 236 */         int[] arrayOfInt1 = null;
/* 237 */         if (this.btlist != null) {
/* 238 */           for (int k = 0; k < this.btlist.length; k++) {
/* 239 */             if (MATH.member(j, this.btlist[k].domain)) {
/* 240 */               localLazyBelief.lazyProduct(this.btlist[k], paramArrayOfInt1, paramArrayOfInt2);
/* 241 */               arrayOfInt1 = MATH.appendMember(k, arrayOfInt1);
/*     */             }
/*     */           }
/* 244 */           if (arrayOfInt1 != null) { delPotential(arrayOfInt1);
/*     */           }
/*     */         }
/* 247 */         int[] arrayOfInt2 = MATH.delMember(j, localLazyBelief.domain);
/* 248 */         if (arrayOfInt2 != null)
/*     */         {
/* 250 */           int[] arrayOfInt3 = MATH.getSubsetDimen(paramArrayOfInt1, paramArrayOfInt2, localLazyBelief.domain);
/* 251 */           localLazyBelief.setMargin(arrayOfInt3, arrayOfInt2);
/*     */           
/* 253 */           int m = 1;
/* 254 */           for (i = 0; i < localLazyBelief.domain.length; i++) {
/* 255 */             if (!MATH.member(localLazyBelief.domain[i], paramArrayOfInt3)) {
/* 256 */               addPotential(localLazyBelief);
/* 257 */               m = 0; break;
/*     */             }
/*     */           }
/* 260 */           if (m != 0) localLazyBelSet.addPotential(localLazyBelief);
/*     */         }
/*     */       } }
/* 263 */     this.btlist = localLazyBelSet.btlist;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void divByMargin(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*     */   {
/* 363 */     LazyBelSet localLazyBelSet = setMargin(paramArrayOfInt3);
/*     */     
/* 365 */     while (this.btlist != null) {
/* 366 */       LazyBelief localLazyBelief1 = this.btlist[0];
/* 367 */       delPotential(0);
/*     */       
/* 369 */       int[] arrayOfInt1 = null;
/* 370 */       for (int i = 0; i < localLazyBelief1.domain.length; i++) {
/* 371 */         int j = localLazyBelief1.domain[i];
/* 372 */         if (!MATH.member(j, paramArrayOfInt3)) arrayOfInt1 = MATH.appendMember(j, arrayOfInt1);
/*     */       }
/* 374 */       if (arrayOfInt1 != null)
/*     */       {
/* 376 */         i = 1;
/* 377 */         Object localObject; while (i != 0) {
/* 378 */           i = 0;
/* 379 */           if (this.btlist != null)
/*     */           {
/* 381 */             arrayOfInt2 = null;
/* 382 */             for (k = 0; k < this.btlist.length; k++) {
/* 383 */               localObject = this.btlist[k];
/* 384 */               if (MATH.getIntersection(arrayOfInt1, ((LazyBelief)localObject).domain) != null) {
/* 385 */                 i = 1;
/* 386 */                 localLazyBelief1.lazyProduct((LazyBelief)localObject, paramArrayOfInt1, paramArrayOfInt2);
/* 387 */                 arrayOfInt2 = MATH.appendMember(k, arrayOfInt2);
/* 388 */                 for (int m = 0; m < localLazyBelief1.domain.length; m++) {
/* 389 */                   int n = localLazyBelief1.domain[m];
/* 390 */                   if (!MATH.member(n, paramArrayOfInt3)) arrayOfInt1 = MATH.appendMember(n, arrayOfInt1);
/*     */                 }
/*     */               } }
/* 393 */             if (arrayOfInt2 != null) delPotential(arrayOfInt2);
/*     */           }
/*     */         }
/* 396 */         int[] arrayOfInt2 = UTIL.getDuplicate(localLazyBelief1.domain);
/* 397 */         for (int k = 0; k < arrayOfInt1.length; k++) arrayOfInt2 = MATH.delMember(arrayOfInt1[k], arrayOfInt2);
/* 398 */         if (arrayOfInt2 == null) {
/* 399 */           localLazyBelSet.addPotential(localLazyBelief1);
/*     */         }
/*     */         else {
/* 402 */           LazyBelief localLazyBelief2 = new LazyBelief(localLazyBelief1);
/* 403 */           localObject = MATH.getSubsetDimen(paramArrayOfInt1, paramArrayOfInt2, localLazyBelief1.domain);
/* 404 */           localLazyBelief2.setMargin((int[])localObject, arrayOfInt2);
/* 405 */           localLazyBelief1.belief = MATH.division(localLazyBelief1.domain, localLazyBelief1.belief, localLazyBelief2.domain, localLazyBelief2.belief, localLazyBelief1.domain, (int[])localObject);
/*     */           
/* 407 */           localLazyBelief1.head = null;
/* 408 */           localLazyBelSet.addPotential(localLazyBelief1);
/*     */         } } }
/* 410 */     this.btlist = localLazyBelSet.btlist;
/*     */   }
/*     */   
/*     */ 
/*     */   void showLazyBelSet()
/*     */   {
/* 416 */     if (this.btlist == null) {
/* 417 */       System.out.println("emtpy");return;
/*     */     }
/* 419 */     for (int i = 0; i < this.btlist.length; i++) this.btlist[i].showLazyBelief();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LazyBelSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */