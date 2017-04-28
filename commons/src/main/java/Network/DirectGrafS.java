/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class DirectGrafS
/*     */   extends DirectGraph
/*     */ {
/*     */   public DirectGrafS(DirectGraph paramDirectGraph)
/*     */   {
/*  12 */     if ((paramDirectGraph != null) && (paramDirectGraph.nd != null)) { setDirectGraph(paramDirectGraph);
/*     */     }
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
/*     */   public void copySharedArc(DirectGraph paramDirectGraph, int[] paramArrayOfInt)
/*     */   {
/*  33 */     int i = paramDirectGraph.getNodeCount();
/*  34 */     int j = getNodeCount();
/*  35 */     int[] arrayOfInt1 = new int[i];
/*  36 */     int[] arrayOfInt2 = new int[j];
/*     */     
/*  38 */     for (int k = 0; k < j; k++) {
/*  39 */       localObject = getLabel(k).substring(1);
/*  40 */       arrayOfInt2[k] = Integer.parseInt((String)localObject);
/*     */     }
/*  42 */     for (k = 0; k < i; k++) {
/*  43 */       localObject = paramDirectGraph.getLabel(k).substring(1);
/*  44 */       arrayOfInt1[k] = Integer.parseInt((String)localObject);
/*     */     }
/*     */     
/*  47 */     k = paramArrayOfInt.length;
/*  48 */     Object localObject = new int[k];
/*  49 */     int[] arrayOfInt3 = new int[k];
/*  50 */     int n; for (int m = 0; m < k; m++) {
/*  51 */       for (n = 0; n < i; n++)
/*  52 */         if (paramArrayOfInt[m] == arrayOfInt1[n]) localObject[m] = n;
/*     */     }
/*  54 */     for (m = 0; m < k; m++) {
/*  55 */       for (n = 0; n < j; n++) {
/*  56 */         if (paramArrayOfInt[m] == arrayOfInt2[n]) arrayOfInt3[m] = n;
/*     */       }
/*     */     }
/*  59 */     for (m = 0; m < k; m++) {
/*  60 */       for (n = 0; n < k; n++) {
/*  61 */         if (m != n)
/*     */         {
/*  63 */           if ((paramDirectGraph.isArc(localObject[m], localObject[n])) && (!isArc(arrayOfInt3[m], arrayOfInt3[n])))
/*     */           {
/*  65 */             addArc(arrayOfInt3[m], arrayOfInt3[n]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  71 */     System.out.println();
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
/*     */   public static DirectGrafS[] connectGraph(DirectGraph paramDirectGraph1, DirectGraph paramDirectGraph2, HyperGraph[] paramArrayOfHyperGraph, int paramInt)
/*     */   {
/* 119 */     DirectGraph localDirectGraph1 = new DirectGraph(paramDirectGraph1);
/* 120 */     DirectGraph localDirectGraph2 = new DirectGraph(paramDirectGraph2);
/*     */     
/*     */ 
/* 123 */     int i = localDirectGraph1.getNodeCount();
/* 124 */     int[] arrayOfInt1 = new int[i];
/* 125 */     int[] arrayOfInt2 = new int[i];
/* 126 */     for (int j = 0; j < i; j++) {
/* 127 */       localObject = localDirectGraph1.getLabel(j).substring(1);
/* 128 */       arrayOfInt1[j] = Integer.parseInt((String)localObject);
/* 129 */       localObject = localDirectGraph2.getLabel(arrayOfInt1[j]).substring(1);
/* 130 */       arrayOfInt2[j] = Integer.parseInt((String)localObject);
/*     */     }
/* 132 */     Point[] arrayOfPoint = null;
/*     */     
/* 134 */     Object localObject = { 0 };
/*     */     
/*     */ 
/*     */     for (;;)
/*     */     {
/* 139 */       int k = 0;
/* 140 */       int m = localObject.length;
/* 141 */       int i3; for (int i1 = 0; i1 < m; i1++) {
/* 142 */         i3 = localObject[i1];
/* 143 */         int i5; for (int i4 = 0; i4 < localDirectGraph1.getParentCount(i3); i4++) {
/* 144 */           i5 = localDirectGraph1.getParent(i3, i4);
/* 145 */           if (!MATH.member(i5, (int[])localObject)) {
/* 146 */             localObject = MATH.addMember(i5, (int[])localObject);k = 1;
/*     */           }
/*     */         }
/* 149 */         for (i4 = 0; i4 < localDirectGraph1.getChildCount(i3); i4++) {
/* 150 */           i5 = localDirectGraph1.getChild(i3, i4);
/* 151 */           if (!MATH.member(i5, (int[])localObject)) {
/* 152 */             localObject = MATH.addMember(i5, (int[])localObject);k = 1;
/*     */           }
/*     */         }
/*     */       }
/* 156 */       if (k == 0)
/*     */       {
/* 158 */         if (localObject.length == i) {
/* 159 */           System.out.println(" The subnet is now connected.");
/* 160 */           DirectGraph[] arrayOfDirectGraph = new DirectGraph[2];
/* 161 */           if (arrayOfPoint != null) {
/* 162 */             arrayOfDirectGraph = addChildToPair(localDirectGraph1, localDirectGraph2, arrayOfPoint, arrayOfInt1);
/*     */           } else {
/* 164 */             arrayOfDirectGraph[0] = localDirectGraph1;arrayOfDirectGraph[1] = localDirectGraph2;
/*     */           }
/* 166 */           DirectGrafS[] arrayOfDirectGrafS = { new DirectGrafS(arrayOfDirectGraph[0]), new DirectGrafS(arrayOfDirectGraph[1]) };
/* 167 */           return arrayOfDirectGrafS;
/*     */         }
/*     */         
/*     */ 
/* 171 */         int n = 0;
/* 172 */         int i2 = 1;
/* 173 */         int[] arrayOfInt3; for (i3 = 0; i3 < i; i3++)
/* 174 */           if (MATH.member(i3, (int[])localObject)) {
/* 175 */             arrayOfInt3 = MATH.addMember(arrayOfInt1[i3], localDirectGraph2.getParent(arrayOfInt1[i3]));
/* 176 */             if (arrayOfInt3.length <= paramInt)
/*     */             {
/* 178 */               for (i2 = 1; i2 < i; i2++)
/* 179 */                 if (!MATH.member(i2, (int[])localObject)) {
/*     */                   int[] arrayOfInt4;
/* 181 */                   if (arrayOfInt2[i2] < arrayOfInt2[i3]) {
/* 182 */                     arrayOfInt4 = MATH.addMember(i2, arrayOfInt3);
/* 183 */                     if (HyperGrafS.isInCluster(arrayOfInt4, paramArrayOfHyperGraph)) {
/* 184 */                       localDirectGraph1.addArc(i2, i3);localDirectGraph2.addArc(arrayOfInt1[i2], arrayOfInt1[i3]);
/*     */                       
/*     */ 
/* 187 */                       n = 1; break;
/*     */                     }
/*     */                   }
/*     */                   else {
/* 191 */                     arrayOfInt4 = MATH.addMember(arrayOfInt1[i2], localDirectGraph2.getParent(arrayOfInt1[i2]));
/* 192 */                     if (arrayOfInt4.length <= paramInt)
/*     */                     {
/* 194 */                       int[] arrayOfInt5 = MATH.addMember(i3, arrayOfInt4);
/* 195 */                       if (HyperGrafS.isInCluster(arrayOfInt5, paramArrayOfHyperGraph)) {
/* 196 */                         localDirectGraph1.addArc(i3, i2);localDirectGraph2.addArc(arrayOfInt1[i3], arrayOfInt1[i2]);
/*     */                         
/*     */ 
/* 199 */                         n = 1; break;
/*     */                       }
/*     */                     }
/*     */                   } }
/* 203 */               if (n != 0) break;
/*     */             }
/*     */           }
/* 206 */         if (n != 0) { localObject = MATH.addMember(i2, (int[])localObject);
/*     */         } else {
/* 208 */           System.out.println(" Fail to connect subnet: add child.");
/* 209 */           for (i3 = 0; i3 < i; i3++) {
/* 210 */             if (MATH.member(i3, (int[])localObject)) {
/* 211 */               arrayOfInt3 = new int[] { i3, -1 };
/* 212 */               for (int i6 = 1; i6 < i; i6++)
/* 213 */                 if (!MATH.member(i6, (int[])localObject)) {
/* 214 */                   arrayOfInt3[1] = i6;
/* 215 */                   if (HyperGrafS.isInCluster(arrayOfInt3, paramArrayOfHyperGraph)) {
/* 216 */                     arrayOfPoint = UTIL.appendToArray(arrayOfPoint, new Point(i3, i6));
/* 217 */                     localObject = MATH.addMember(i6, (int[])localObject);
/* 218 */                     n = 1; break;
/*     */                   }
/*     */                 }
/* 221 */               if (n != 0) {
/*     */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static DirectGraph[] addChildToPair(DirectGraph paramDirectGraph1, DirectGraph paramDirectGraph2, Point[] paramArrayOfPoint, int[] paramArrayOfInt)
/*     */   {
/* 235 */     DirectGraph localDirectGraph1 = new DirectGraph(paramDirectGraph1);
/* 236 */     DirectGraph localDirectGraph2 = new DirectGraph(paramDirectGraph2);
/*     */     
/* 238 */     int i = localDirectGraph2.getNodeCount();
/* 239 */     int j = localDirectGraph1.getNodeCount();
/* 240 */     for (int k = 0; k < paramArrayOfPoint.length; k++) {
/* 241 */       int m = paramArrayOfPoint[k].x;int n = paramArrayOfPoint[k].y;
/* 242 */       Point localPoint1 = localDirectGraph1.getPos(m);Point localPoint2 = localDirectGraph1.getPos(n);
/* 243 */       Point localPoint3 = new Point((localPoint1.x + localPoint2.x) / 2, (localPoint1.y + localPoint2.y) / 2);
/* 244 */       localDirectGraph1 = addNode(localDirectGraph1, localPoint3, "v" + i);
/* 245 */       localDirectGraph2 = addNode(localDirectGraph2, localPoint3, "v" + i);
/* 246 */       localDirectGraph1.addArc(m, j);
/*     */       
/* 248 */       j++;
/* 249 */       localDirectGraph2.addArc(paramArrayOfInt[m], i);
/*     */       
/* 251 */       i++;
/*     */     }
/* 253 */     DirectGraph[] arrayOfDirectGraph = new DirectGraph[2];
/* 254 */     arrayOfDirectGraph[0] = localDirectGraph1;arrayOfDirectGraph[1] = localDirectGraph2;
/* 255 */     return arrayOfDirectGraph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DirectGraph addNode(DirectGraph paramDirectGraph, Point paramPoint, String paramString)
/*     */   {
/* 263 */     DirectGraph localDirectGraph = new DirectGraph();
/*     */     
/* 265 */     int i = paramDirectGraph.getNodeCount();
/* 266 */     localDirectGraph.setDumbNet(i + 1);
/*     */     
/* 268 */     for (int j = 0; j < i; j++) { localDirectGraph.nd[j] = paramDirectGraph.nd[j];
/*     */     }
/* 270 */     localDirectGraph.nd[i] = new DNode();
/* 271 */     localDirectGraph.setPos(i, paramPoint);
/* 272 */     localDirectGraph.setLabel(i, paramString);
/* 273 */     return localDirectGraph;
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
/*     */   public void augmentGraph(int[] paramArrayOfInt, DirectGraph paramDirectGraph, HyperGraph[] paramArrayOfHyperGraph, int paramInt, Random paramRandom, float paramFloat)
/*     */   {
/* 302 */     int i = getNodeCount();
/* 303 */     int[] arrayOfInt1 = new int[i];
/* 304 */     int[] arrayOfInt2 = new int[i];
/* 305 */     Object localObject; for (int j = 0; j < i; j++) {
/* 306 */       localObject = getLabel(j).substring(1);
/* 307 */       arrayOfInt1[j] = Integer.parseInt((String)localObject);
/* 308 */       localObject = paramDirectGraph.getLabel(arrayOfInt1[j]).substring(1);
/* 309 */       arrayOfInt2[j] = Integer.parseInt((String)localObject);
/*     */     }
/*     */     
/* 312 */     for (j = 0; j < i; j++) {
/* 313 */       localObject = new int[2];
/* 314 */       localObject[0] = arrayOfInt1[j];
/*     */       
/* 316 */       for (int k = 0; k < i; k++) {
/* 317 */         if (j != k) {
/* 318 */           localObject[1] = arrayOfInt1[k];
/* 319 */           if (!MATH.isSubset((int[])localObject, paramArrayOfInt)) { int[] arrayOfInt3;
/*     */             int[] arrayOfInt4;
/* 321 */             if (arrayOfInt2[j] < arrayOfInt2[k]) {
/* 322 */               arrayOfInt3 = MATH.addMember(arrayOfInt1[k], paramDirectGraph.getParent(arrayOfInt1[k]));
/* 323 */               if (arrayOfInt3.length <= paramInt)
/*     */               {
/* 325 */                 arrayOfInt4 = MATH.addMember(arrayOfInt1[j], arrayOfInt3);
/* 326 */                 if (HyperGrafS.isInCluster(arrayOfInt4, paramArrayOfHyperGraph)) {
/* 327 */                   if (paramRandom.nextFloat() > paramFloat) break;
/* 328 */                   addArc(j, k);
/* 329 */                   paramDirectGraph.addArc(arrayOfInt1[j], arrayOfInt1[k]);
/*     */                 }
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 335 */               arrayOfInt3 = MATH.addMember(arrayOfInt1[j], paramDirectGraph.getParent(arrayOfInt1[j]));
/* 336 */               if (arrayOfInt3.length <= paramInt)
/*     */               {
/* 338 */                 arrayOfInt4 = MATH.addMember(arrayOfInt1[k], arrayOfInt3);
/* 339 */                 if (HyperGrafS.isInCluster(arrayOfInt4, paramArrayOfHyperGraph)) {
/* 340 */                   if (paramRandom.nextFloat() > paramFloat) break;
/* 341 */                   addArc(k, j);
/* 342 */                   paramDirectGraph.addArc(arrayOfInt1[k], arrayOfInt1[j]);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DirectGrafS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */