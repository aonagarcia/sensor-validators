/*     */ package BnToJt;
/*     */ 
/*     */ import Network.HyperGraph;
/*     */ import Network.JoinForest;
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import Network.UndirectGraph;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
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
/*     */ public class DisJtByMst
/*     */ {
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/*  80 */     if (paramArrayOfString.length == 0) {
/*  81 */       System.out.println("Usage 1: java BnToJt.DisJtByMst jt_or_set_file");
/*  82 */       System.out.println("Usage 2: java BnToJt.DisJtByMst jt_or_set_file out_file");
/*  83 */       System.exit(0);
/*     */     }
/*     */     
/*  86 */     int i = 0;
/*  87 */     if (paramArrayOfString[0].endsWith(".set")) { i = 1;
/*     */     }
/*  89 */     int j = 1;
/*  90 */     if (paramArrayOfString.length == 2) {
/*  91 */       j = 0;
/*     */     }
/*     */     
/*     */ 
/*  95 */     Object localObject1 = new HyperGraph();
/*  96 */     int k = 0;
/*     */     Object localObject2;
/*  98 */     if (i == 0) {
/*  99 */       localObject2 = JoinForest.load(paramArrayOfString[0]);
/* 100 */       localObject1 = localObject2;
/* 101 */       ((HyperGraph)localObject1).rmLink();
/* 102 */       k = ((HyperGraph)localObject1).getNodeCount();
/*     */     }
/*     */     else {
/* 105 */       localObject2 = loadSetFile(paramArrayOfString[0]);
/* 106 */       k = localObject2.length;
/* 107 */       ((HyperGraph)localObject1).setDumbNetPlus(k);
/*     */       
/* 109 */       localObject4 = putAgtOnCircle(k);
/* 110 */       for (n = 0; n < k; n++) {
/* 111 */         ((HyperGraph)localObject1).setCqMember(n, localObject2[n]);
/* 112 */         ((HyperGraph)localObject1).setPos(n, localObject4[n]);
/*     */       }
/* 114 */       ((HyperGraph)localObject1).setLabel("A");
/*     */     }
/*     */     
/*     */ 
/* 118 */     for (int m = 0; m < k - 1; m++) {
/* 119 */       localObject4 = ((HyperGraph)localObject1).getCqMember(m);
/* 120 */       for (n = m + 1; n < k; n++) {
/* 121 */         int[] arrayOfInt1 = ((HyperGraph)localObject1).getCqMember(n);
/* 122 */         int[] arrayOfInt2 = MATH.getIntersection((int[])localObject4, arrayOfInt1);
/* 123 */         if (arrayOfInt2 != null) { ((HyperGraph)localObject1).addNeighbor(m, n, arrayOfInt2);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 130 */     if (j != 0) {
/* 131 */       localObject3 = new Frame();((Frame)localObject3).setSize(600, 350);
/* 132 */       ((Frame)localObject3).add(new HgCan2(new HyperGraph((HyperGraph)localObject1)));
/* 133 */       ((Frame)localObject3).setTitle("Junction graph JG from input file");((Frame)localObject3).setVisible(true);
/*     */     }
/*     */     
/*     */ 
/* 137 */     Object localObject3 = new UndirectGraph();
/* 138 */     ((UndirectGraph)localObject3).setDumbNetPlus(k);
/* 139 */     Object localObject4 = new int[k][];
/*     */     
/* 141 */     for (int n = 0; n < k; n++)
/*     */     {
/* 143 */       localObject4[n] = ((HyperGraph)localObject1).getNeighbor(n);
/* 144 */       ((UndirectGraph)localObject3).setNeighbor(n, localObject4[n]);
/* 145 */       ((UndirectGraph)localObject3).setPos(n, ((HyperGraph)localObject1).getPos(n));
/* 146 */       ((UndirectGraph)localObject3).setLabel(n, "V" + n);
/*     */       
/* 148 */       for (int i1 = 0; i1 < localObject4[n].length; i1++) {
/* 149 */         localObject4[n][i1] = ((HyperGraph)localObject1).getSepset(new Point(n, localObject4[n][i1])).length;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 154 */     if (j != 0) {
/* 155 */       localObject5 = new Frame();((Frame)localObject5).setSize(600, 350);((Frame)localObject5).setLocation(600, 0);
/* 156 */       ((Frame)localObject5).add(new WgCan(new UndirectGraph((UndirectGraph)localObject3), (int[][])localObject4));
/* 157 */       ((Frame)localObject5).setTitle("Weighted graph WG converted from JG");
/* 158 */       ((Frame)localObject5).setVisible(true);
/*     */     }
/*     */     
/*     */ 
/* 162 */     Object localObject5 = new Msger2();
/* 163 */     ANod[] arrayOfANod = new ANod[k];
/* 164 */     for (int i2 = 0; i2 < k; i2++) {
/* 165 */       arrayOfANod[i2] = new ANod(i2, ((HyperGraph)localObject1).getNeighbor(i2), localObject4[i2], (Msger2)localObject5);
/*     */     }
/* 167 */     ((Msger2)localObject5).setAgent(arrayOfANod);
/*     */     
/*     */ 
/* 170 */     Random localRandom = new Random();
/* 171 */     int i3 = MATH.getRandomInt(localRandom, 0, k - 1);
/* 172 */     ANod localANod = arrayOfANod[i3];
/* 173 */     localANod.start();
/* 174 */     int i4 = 1;
/* 175 */     while (localANod.state != 2) {
/* 176 */       System.out.println("Pass " + i4);
/* 177 */       for (int i5 = 0; i5 < k; i5++) arrayOfANod[i5].respond();
/* 178 */       i4++;
/*     */     }
/* 180 */     localANod.endExpansion();
/* 181 */     localANod.endDPMST();
/* 182 */     if (j == 0) {
/* 183 */       localObject6 = "java BnToJt.DisJtByMst " + paramArrayOfString[0];
/* 184 */       localANod.appendOutput(paramArrayOfString[1], (String)localObject6);
/*     */     }
/*     */     
/*     */ 
/* 188 */     Object localObject6 = new UndirectGraph();
/* 189 */     ((UndirectGraph)localObject6).setDumbNetPlus(k);
/* 190 */     for (int i6 = 0; i6 < k; i6++) {
/* 191 */       int[] arrayOfInt3 = MATH.addMember(arrayOfANod[i6].getTreeParent(), arrayOfANod[i6].getTreeChild());
/* 192 */       ((UndirectGraph)localObject6).setNeighbor(i6, arrayOfInt3);
/* 193 */       ((UndirectGraph)localObject6).setPos(i6, ((HyperGraph)localObject1).getPos(i6));
/* 194 */       ((UndirectGraph)localObject6).setLabel(i6, "V" + i6);
/*     */     }
/*     */     
/*     */ 
/* 198 */     if (j != 0) {
/* 199 */       localObject7 = new Frame();((Frame)localObject7).setSize(600, 350);((Frame)localObject7).setLocation(600, 350);
/* 200 */       ((Frame)localObject7).add(new UgCan(new UndirectGraph((UndirectGraph)localObject6)));
/* 201 */       ((Frame)localObject7).setTitle("Max spanning tree MST from WG");((Frame)localObject7).setVisible(true);
/*     */     }
/*     */     
/*     */ 
/* 205 */     Object localObject7 = new HyperGraph();
/* 206 */     ((HyperGraph)localObject7).setDumbNetPlus(k);
/* 207 */     for (int i7 = 0; i7 < k; i7++) {
/* 208 */       ((HyperGraph)localObject7).setCqMember(i7, ((HyperGraph)localObject1).getCqMember(i7));
/* 209 */       ((HyperGraph)localObject7).setPos(i7, ((HyperGraph)localObject1).getPos(i7));
/*     */     }
/* 211 */     ((HyperGraph)localObject7).setLabel();
/*     */     
/* 213 */     for (i7 = 0; i7 < k - 1; i7++) {
/* 214 */       for (int i8 = i7 + 1; i8 < k; i8++) {
/* 215 */         if (((UndirectGraph)localObject6).isLink(i7, i8)) {
/* 216 */           ((HyperGraph)localObject7).addNeighbor(i7, i8, ((HyperGraph)localObject1).getSepset(new Point(i7, i8)));
/*     */         }
/*     */       }
/*     */     }
/*     */     Object localObject8;
/* 221 */     if (j != 0) {
/* 222 */       localObject8 = new Frame();((Frame)localObject8).setSize(600, 350);((Frame)localObject8).setLocation(0, 350);
/* 223 */       ((Frame)localObject8).add(new HgCan2(new HyperGraph((HyperGraph)localObject7)));
/* 224 */       ((Frame)localObject8).setTitle("Junction tree JT from MST");((Frame)localObject8).setVisible(true);
/*     */     }
/*     */     
/*     */ 
/* 228 */     if (j != 0) {
/* 229 */       System.out.print("Enter to exit: ");
/* 230 */       localObject8 = new BufferedReader(new InputStreamReader(System.in));
/*     */       
/* 232 */       ((BufferedReader)localObject8).readLine();
/*     */     }
/* 234 */     System.exit(0);
/*     */   }
/*     */   
/*     */   static int[][] loadSetFile(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 241 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/*     */       
/* 243 */       System.out.println("Load from " + paramString);
/*     */       
/*     */ 
/* 246 */       int i = UTIL.loadInt(localBufferedReader);
/* 247 */       int[][] arrayOfInt = new int[i][];
/* 248 */       for (int j = 0; j < i; j++) {
/* 249 */         arrayOfInt[j] = UTIL.loadIntListLine(localBufferedReader);
/*     */       }
/*     */       
/* 252 */       localBufferedReader.close();
/*     */       
/* 254 */       return arrayOfInt;
/*     */     } catch (IOException localIOException) {
/* 256 */       System.out.println("Error loading " + paramString); }
/* 257 */     return (int[][])null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Point[] putAgtOnCircle(int paramInt)
/*     */   {
/* 265 */     Point[] arrayOfPoint = new Point[paramInt];
/* 266 */     float f1 = 0.0F;
/* 267 */     float f2 = 6.28F / paramInt;
/* 268 */     for (int i = 0; i < paramInt; i++) {
/* 269 */       double d1 = Math.cos(f1) * 200.0D + 250.0D;
/* 270 */       double d2 = Math.sin(f1) * 200.0D + 250.0D;
/* 271 */       arrayOfPoint[i] = new Point((int)d1, (int)d2);
/* 272 */       f1 += f2;
/*     */     }
/* 274 */     return arrayOfPoint;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/DisJtByMst.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */