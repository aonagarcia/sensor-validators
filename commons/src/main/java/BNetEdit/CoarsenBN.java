/*     */ package BNetEdit;
/*     */ 
/*     */ import Network.BNode;
/*     */ import Network.BayesNet;
/*     */ import Network.JoinForest;
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
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
/*     */ class CoarsenBN
/*     */ {
/*     */   static Random rng;
/*     */   
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/* 109 */     int i = paramArrayOfString.length;
/* 110 */     if (i < 2) {
/* 111 */       System.out.println("Use: java BNetEdit.CoarsenBN bn_file s_or_seed");
/* 112 */       System.exit(0);
/*     */     }
/*     */     
/* 115 */     String str1 = paramArrayOfString[0];
/* 116 */     BayesNet localBayesNet1 = BayesNet.load(str1);
/* 117 */     BayesNet localBayesNet2 = addCoarseChild(localBayesNet1);
/* 118 */     BayesNet localBayesNet3 = setCoarseCpt(localBayesNet1, localBayesNet2);
/*     */     
/* 120 */     if (paramArrayOfString[1].equals("s")) {
/* 121 */       String str2 = str1.substring(0, str1.lastIndexOf('.')) + "_coarse.bn";
/* 122 */       localBayesNet3.save(str2);
/* 123 */       System.out.println("Saved " + str2);
/*     */     }
/*     */     else {
/* 126 */       long l = Long.parseLong(paramArrayOfString[1]);
/* 127 */       if (l == 0L) {
/* 128 */         rng = new Random();l = (1000000.0D * rng.nextDouble());
/*     */       }
/* 130 */       rng = new Random(l);
/* 131 */       System.out.println("\nSeed used = " + l);
/*     */       
/* 133 */       int j = localBayesNet1.getNodeCount();
/* 134 */       comparePost(rng, localBayesNet2, localBayesNet3, j / 10, j / 2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void comparePost(Random paramRandom, BayesNet paramBayesNet1, BayesNet paramBayesNet2, int paramInt1, int paramInt2)
/*     */   {
/* 143 */     JoinForest localJoinForest1 = JoinForest.bnToJt(paramBayesNet1);
/* 144 */     JoinForest localJoinForest2 = JoinForest.bnToJt(paramBayesNet2);
/* 145 */     int i = paramBayesNet2.getNodeCount();
/*     */     
/* 147 */     int[] arrayOfInt1 = new int[i];
/* 148 */     for (int j = 0; j < i; j++) arrayOfInt1[j] = j;
/* 149 */     int[] arrayOfInt2 = MATH.getRandomSubset(arrayOfInt1, paramInt1, paramRandom);
/* 150 */     UTIL.showList("Vars observed in coarsened BN: ", arrayOfInt2);
/* 151 */     int[] arrayOfInt3 = new int[paramInt1];
/* 152 */     for (int k = 0; k < paramInt1; k++) { arrayOfInt2[k] += i;
/*     */     }
/* 154 */     int[] arrayOfInt4 = new int[paramInt1];
/* 155 */     for (int m = 0; m < paramInt1; m++) arrayOfInt4[m] = MATH.getRandomInt(paramRandom, 0, 1);
/* 156 */     int[] arrayOfInt5 = { 1, 0 };
/* 157 */     int[] arrayOfInt6 = { 0, 1 };
/*     */     
/* 159 */     for (int n = 0; n < paramInt1; n++) {
/* 160 */       if (arrayOfInt4[n] == 0) {
/* 161 */         localJoinForest1.enterEvidenceToCq(arrayOfInt3[n], arrayOfInt5);
/* 162 */         localJoinForest2.enterEvidenceToCq(arrayOfInt2[n], arrayOfInt5);
/*     */       }
/*     */       else {
/* 165 */         localJoinForest1.enterEvidenceToCq(arrayOfInt3[n], arrayOfInt6);
/* 166 */         localJoinForest2.enterEvidenceToCq(arrayOfInt2[n], arrayOfInt6);
/*     */       }
/*     */     }
/* 169 */     localJoinForest1.unifyBelief();localJoinForest2.unifyBelief();
/*     */     
/* 171 */     int[] arrayOfInt7 = MATH.getRandomSubset(arrayOfInt1, paramInt2, paramRandom);
/* 172 */     int[] arrayOfInt8 = new int[paramInt2];
/* 173 */     for (int i1 = 0; i1 < paramInt2; i1++) { arrayOfInt7[i1] += i;
/*     */     }
/* 175 */     float f1 = 0.0F;
/* 176 */     for (int i2 = 0; i2 < paramInt2; i2++) {
/* 177 */       float[] arrayOfFloat1 = localJoinForest1.getVarMargin(arrayOfInt8[i2]);
/* 178 */       float[] arrayOfFloat2 = localJoinForest2.getVarMargin(arrayOfInt7[i2]);
/* 179 */       float f2 = Math.abs(arrayOfFloat1[0] - arrayOfFloat2[0]);
/* 180 */       System.out.println("Posteriors over v" + arrayOfInt7[i2] + "' >> abs err = " + f2);
/*     */       
/*     */ 
/* 183 */       if (f2 > f1) f1 = f2;
/*     */     }
/* 185 */     System.out.println("\nMax abs error = " + f1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static float[] setCoarseCpt(BayesNet paramBayesNet, JoinForest paramJoinForest, int paramInt)
/*     */   {
/* 196 */     int i = paramBayesNet.getNodeCount();
/* 197 */     int[] arrayOfInt1 = paramBayesNet.getParent(paramInt);
/* 198 */     int j = arrayOfInt1.length;
/* 199 */     int[] arrayOfInt2 = new int[j];
/* 200 */     for (int k = 0; k < j; k++) { arrayOfInt1[k] += i;
/*     */     }
/* 202 */     k = (int)Math.pow(2.0D, j);
/* 203 */     float[] arrayOfFloat1 = new float[k * 2];
/* 204 */     int[] arrayOfInt3 = new int[j];
/* 205 */     for (int m = 0; m < j; m++) arrayOfInt3[m] = 2;
/* 206 */     int[] arrayOfInt4 = { 1, 0 };
/* 207 */     int[] arrayOfInt5 = { 0, 1 };
/*     */     
/* 209 */     for (int n = 0; n < k; n++) {
/* 210 */       JoinForest localJoinForest = new JoinForest(paramJoinForest);
/* 211 */       int[] arrayOfInt6 = MATH.decToMix(n, arrayOfInt3);
/* 212 */       for (int i1 = 0; i1 < j; i1++) {
/* 213 */         if (arrayOfInt6[i1] == 0) localJoinForest.enterEvidenceToCq(arrayOfInt2[i1], arrayOfInt4); else
/* 214 */           localJoinForest.enterEvidenceToCq(arrayOfInt2[i1], arrayOfInt5);
/*     */       }
/* 216 */       localJoinForest.unifyBelief();
/* 217 */       float[] arrayOfFloat2 = localJoinForest.getVarMargin(paramInt + i);
/* 218 */       arrayOfFloat1[(n * 2)] = arrayOfFloat2[0];arrayOfFloat1[(n * 2 + 1)] = arrayOfFloat2[1];
/*     */     }
/* 220 */     return arrayOfFloat1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static BayesNet setCoarseCpt(BayesNet paramBayesNet1, BayesNet paramBayesNet2)
/*     */   {
/* 227 */     int i = paramBayesNet1.getNodeCount();
/* 228 */     String[] arrayOfString = { "s0", "s1" };
/* 229 */     BayesNet localBayesNet = new BayesNet(paramBayesNet1);
/* 230 */     for (int j = 0; j < i; j++) {
/* 231 */       localBayesNet.setLabel(j, new String("v" + j + "'"));
/* 232 */       localBayesNet.setState(j, arrayOfString);
/*     */     }
/*     */     
/*     */ 
/* 236 */     JoinForest localJoinForest = JoinForest.bnToJt(paramBayesNet2);
/* 237 */     for (int k = 0; k < i; k++) {
/* 238 */       if (paramBayesNet1.isRoot(k)) localBayesNet.setCondProb(k, localJoinForest.getVarMargin(k + i)); else
/* 239 */         localBayesNet.setCondProb(k, setCoarseCpt(paramBayesNet1, localJoinForest, k));
/*     */     }
/* 241 */     return localBayesNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static BayesNet addCoarseChild(BayesNet paramBayesNet)
/*     */   {
/* 252 */     Point localPoint1 = paramBayesNet.getNodeCount();
/* 253 */     Point localPoint2 = localPoint1 * 2;
/* 254 */     BayesNet localBayesNet = new BayesNet();
/* 255 */     localBayesNet.setDumbNet(localPoint2);
/* 256 */     for (int i = 0; i < localPoint1; i++) { localBayesNet.nd[i] = new BNode(paramBayesNet.getBNode(i));
/*     */     }
/* 258 */     for (i = localPoint1; i < localPoint2; i++) {
/* 259 */       localBayesNet.nd[i] = new BNode();
/* 260 */       localBayesNet.setLabel(i, "v" + (i - localPoint1) + "'");
/* 261 */       localPoint3 = paramBayesNet.getPos(i - localPoint1);
/* 262 */       localBayesNet.setPos(i, new Point(localPoint3.x + 10, localPoint3.y + 20));
/* 263 */       localBayesNet.addArc(i - localPoint1, i);
/*     */     }
/*     */     
/* 266 */     int[] arrayOfInt = GetFalseState.getFalseVlu(paramBayesNet);
/*     */     
/* 268 */     for (Point localPoint3 = localPoint1; localPoint3 < localPoint2; localPoint3++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 274 */       float[] arrayOfFloat = setFalseSplitCpt(paramBayesNet.getStateCount(localPoint3 - localPoint1), arrayOfInt[(localPoint3 - localPoint1)]);
/*     */       
/* 276 */       localBayesNet.setCondProb(localPoint3, arrayOfFloat);
/*     */     }
/* 278 */     return localBayesNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static float[] setFalseSplitCpt(int paramInt1, int paramInt2)
/*     */   {
/* 287 */     float[] arrayOfFloat = new float[2 * paramInt1];
/* 288 */     for (int i = 0; i < paramInt1; i++) {
/* 289 */       if (i == paramInt2) {
/* 290 */         arrayOfFloat[(i * 2)] = 1.0F;arrayOfFloat[(i * 2 + 1)] = 0.0F;
/*     */       }
/*     */       else {
/* 293 */         arrayOfFloat[(i * 2)] = 0.0F;arrayOfFloat[(i * 2 + 1)] = 1.0F;
/*     */       }
/*     */     }
/* 296 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static float[] set1stSplitCpt(int paramInt)
/*     */   {
/* 307 */     float[] arrayOfFloat = new float[2 * paramInt];
/* 308 */     arrayOfFloat[0] = 1.0F;arrayOfFloat[1] = 0.0F;
/* 309 */     for (int i = 1; i < paramInt; i++) {
/* 310 */       arrayOfFloat[(i * 2)] = 0.0F;arrayOfFloat[(i * 2 + 1)] = 1.0F;
/*     */     }
/* 312 */     return arrayOfFloat;
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
/*     */   static float[] setMidSplitCpt(int paramInt)
/*     */   {
/* 326 */     int i = paramInt / 2;
/* 327 */     int j = i;
/* 328 */     if (paramInt % 2 == 1) { j++;
/*     */     }
/* 330 */     float[] arrayOfFloat = new float[2 * paramInt];
/* 331 */     for (int k = 0; k < i; k++) {
/* 332 */       arrayOfFloat[(k * 2)] = 1.0F;arrayOfFloat[(k * 2 + 1)] = 0.0F;
/*     */     }
/*     */     
/* 335 */     k = i * 2;
/* 336 */     for (int m = 0; m < j; m++) {
/* 337 */       arrayOfFloat[(k + m * 2)] = 0.0F;arrayOfFloat[(k + m * 2 + 1)] = 1.0F;
/*     */     }
/* 339 */     return arrayOfFloat;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BNetEdit/CoarsenBN.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */