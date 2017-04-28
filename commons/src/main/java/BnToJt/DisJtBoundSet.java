/*     */ package BnToJt;
/*     */ 
/*     */ import Network.HyperGraph;
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
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
/*     */ public class DisJtBoundSet
/*     */ {
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/*  72 */     String str1 = null;
/*  73 */     int i = 0;
/*  74 */     String str2 = null;
/*     */     
/*  76 */     if ((paramArrayOfString.length == 0) || (paramArrayOfString.length >= 3)) {
/*  77 */       System.out.println("Use: java BnToJt.DisJtBoundSet boundary_set_file");
/*  78 */       System.exit(0);
/*     */     }
/*  80 */     else if (paramArrayOfString.length == 1) { str1 = paramArrayOfString[0];
/*  81 */     } else if (paramArrayOfString.length == 2) {
/*  82 */       str1 = paramArrayOfString[0];
/*  83 */       if (paramArrayOfString[1].equals("debug")) i = 1; else {
/*  84 */         str2 = paramArrayOfString[1];
/*     */       }
/*     */     }
/*  87 */     int[][] arrayOfInt1 = loadSetFile(str1);
/*  88 */     UTIL.showList("Boundary set: ", arrayOfInt1);
/*     */     
/*     */ 
/*  91 */     if (isWellComposed(arrayOfInt1)) {
/*  92 */       System.out.println("\nBoundary set is well-composed.");
/*     */     } else {
/*  94 */       System.out.println("\nBoundary set is ill-composed.");
/*  95 */       System.exit(1);
/*     */     }
/*     */     
/*     */ 
/*  99 */     HyperGraph localHyperGraph = new HyperGraph();
/* 100 */     int j = arrayOfInt1.length;
/* 101 */     localHyperGraph.setDumbNetPlus(j);
/*     */     
/* 103 */     Point[] arrayOfPoint = putAgtOnCircle(j);
/* 104 */     for (int k = 0; k < j; k++) {
/* 105 */       localHyperGraph.setCqMember(k, arrayOfInt1[k]);
/* 106 */       localHyperGraph.setPos(k, arrayOfPoint[k]);
/*     */     }
/* 108 */     localHyperGraph.setLabel("A");
/*     */     
/* 110 */     for (k = 0; k < j - 1; k++) {
/* 111 */       localObject = localHyperGraph.getCqMember(k);
/* 112 */       for (int m = k + 1; m < j; m++) {
/* 113 */         int[] arrayOfInt2 = localHyperGraph.getCqMember(m);
/* 114 */         int[] arrayOfInt3 = MATH.getIntersection((int[])localObject, arrayOfInt2);
/* 115 */         if (arrayOfInt3 != null) { localHyperGraph.addNeighbor(k, m, arrayOfInt3);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 120 */     HgCan3 localHgCan3 = null;
/* 121 */     if (i != 0) {
/* 122 */       localObject = new Frame();((Frame)localObject).setSize(600, 400);
/* 123 */       localHgCan3 = new HgCan3(new HyperGraph(localHyperGraph));((Frame)localObject).add(localHgCan3);
/* 124 */       ((Frame)localObject).setTitle("Junction graph jg");((Frame)localObject).setVisible(true);
/*     */     }
/*     */     
/*     */ 
/* 128 */     Object localObject = new Msger();
/* 129 */     Agt[] arrayOfAgt = new Agt[j];
/* 130 */     int n = 0;
/* 131 */     int i1 = 0;
/* 132 */     for (int i2 = 0; i2 < j; i2++) {
/* 133 */       int[] arrayOfInt4 = localHyperGraph.getNeighbor(i2);
/* 134 */       int i4 = arrayOfInt4.length;
/* 135 */       int[][] arrayOfInt5 = new int[i4][];
/* 136 */       for (i6 = 0; i6 < i4; i6++) arrayOfInt5[i6] = localHyperGraph.getSepset(i2, i6);
/* 137 */       arrayOfAgt[i2] = new Agt(i2, arrayOfInt4, arrayOfInt1[i2], arrayOfInt5, (Msger)localObject);
/*     */       
/* 139 */       n += i4;
/* 140 */       if (i4 > i1) i1 = i4;
/*     */     }
/* 142 */     ((Msger)localObject).setAgent(arrayOfAgt);
/* 143 */     n /= j;
/*     */     
/*     */ 
/* 146 */     byte[] arrayOfByte = new byte[1];
/* 147 */     int i3 = 1;
/* 148 */     System.out.println();System.out.println("Round " + i3++);
/* 149 */     Random localRandom = new Random();
/* 150 */     int i5 = MATH.getRandomInt(localRandom, 0, j - 1);
/* 151 */     arrayOfAgt[i5].startNewDFT(-1, 0);
/* 152 */     if (i != 0) System.in.read(arrayOfByte);
/*     */     int i7;
/* 154 */     while (((Msger)localObject).isDone() < 0) {
/* 155 */       System.out.println("Round " + i3);
/* 156 */       for (i6 = 0; i6 < j; i6++) {
/* 157 */         arrayOfAgt[i6].respond();
/* 158 */         if (i != 0) { addCross(localHgCan3, arrayOfAgt);
/*     */         }
/* 160 */         if (((Msger)localObject).isElimSent()) {
/* 161 */           for (i7 = 0; i7 < j; i7++) arrayOfAgt[i7].handleEliminated();
/* 162 */           ((Msger)localObject).setElimSent(false);
/*     */         }
/*     */         
/* 165 */         if (i != 0) System.in.read(arrayOfByte);
/* 166 */         if (((Msger)localObject).isDone() >= 0)
/*     */           break;
/*     */       }
/* 169 */       i3++;
/*     */     }
/*     */     
/* 172 */     int i6 = ((Msger)localObject).msgcnt;
/* 173 */     while (!((Msger)localObject).noMail()) {
/* 174 */       i6++;
/* 175 */       System.out.println("Time " + i6);
/* 176 */       for (i7 = 0; i7 < j; i7++) { arrayOfAgt[i7].respondAnnounce(i6);
/*     */       }
/*     */     }
/*     */     
/* 180 */     if (((Msger)localObject).isDone() == 1) { showJtNb(arrayOfAgt);
/*     */     }
/* 182 */     if (str2 != null) {
/* 183 */       String str3 = "java BnToJt.DisJtBoundSet " + str1;
/* 184 */       appendOutput(str2, str3, j, n, i1, ((Msger)localObject).isDone(), i6);
/*     */     }
/*     */     
/* 187 */     System.out.println("\n  No. of boundaries = " + j);
/* 188 */     System.out.println("Avg # borders/agent = " + n);
/* 189 */     System.out.println("Max # borders/agent = " + i1);
/* 190 */     System.out.println("       HTBS runtime = " + i6);
/*     */     
/*     */ 
/* 193 */     if (i != 0) {
/* 194 */       System.out.print("Enter to exit: ");
/* 195 */       System.in.read(arrayOfByte);
/*     */     }
/* 197 */     System.exit(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static int[][] loadSetFile(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 206 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/*     */       
/* 208 */       System.out.println("Load from " + paramString);
/*     */       
/*     */ 
/* 211 */       int i = UTIL.loadInt(localBufferedReader);
/* 212 */       int[][] arrayOfInt = new int[i][];
/* 213 */       for (int j = 0; j < i; j++) {
/* 214 */         arrayOfInt[j] = UTIL.loadIntListLine(localBufferedReader);
/*     */       }
/*     */       
/* 217 */       localBufferedReader.close();
/*     */       
/* 219 */       return arrayOfInt;
/*     */     } catch (IOException localIOException) {
/* 221 */       System.out.println("Error loading " + paramString); }
/* 222 */     return (int[][])null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void appendOutput(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 234 */     String str1 = " bdct= " + paramInt1 + " avg_bdr/agt= " + paramInt2 + " max_bdr/agt= " + paramInt3;
/* 235 */     if (paramInt4 == 1) str1 = str1 + " has_ht"; else
/* 236 */       str1 = str1 + " no_ht";
/* 237 */     String str2 = " runtm= " + paramInt5;
/*     */     try
/*     */     {
/* 240 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString1, true));
/* 241 */       System.out.println("Saving file " + paramString1);
/* 242 */       localPrintWriter.println(paramString2 + str1 + str2);
/* 243 */       localPrintWriter.close();
/* 244 */     } catch (IOException localIOException) { System.out.println("Unable to save " + paramString1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static boolean isWellComposed(int[][] paramArrayOfInt)
/*     */   {
/* 251 */     int i = paramArrayOfInt.length;
/* 252 */     for (int j = 0; j < i; j++) {
/* 253 */       for (int k = 0; k < paramArrayOfInt[j].length; k++) {
/* 254 */         int m = 0;
/* 255 */         for (int n = 0; n < i; n++) {
/* 256 */           if ((n != j) && 
/* 257 */             (MATH.member(paramArrayOfInt[j][k], paramArrayOfInt[n]))) {
/* 258 */             m = 1; break;
/*     */           }
/*     */         }
/* 261 */         if (m == 0) return false;
/*     */       }
/*     */     }
/* 264 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isConnected(HyperGraph paramHyperGraph)
/*     */   {
/* 269 */     int i = paramHyperGraph.getNodeCount();
/* 270 */     for (int j = 1; j < i; j++) {
/* 271 */       if (!paramHyperGraph.isConnected(0, j)) return false;
/*     */     }
/* 273 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Point[] putAgtOnCircle(int paramInt)
/*     */   {
/* 281 */     Point[] arrayOfPoint = new Point[paramInt];
/* 282 */     float f1 = 0.0F;
/* 283 */     float f2 = 6.28F / paramInt;
/* 284 */     for (int i = 0; i < paramInt; i++) {
/* 285 */       double d1 = Math.cos(f1) * 200.0D + 250.0D;
/* 286 */       double d2 = Math.sin(f1) * 200.0D + 250.0D;
/* 287 */       arrayOfPoint[i] = new Point((int)d1, (int)d2);
/* 288 */       f1 += f2;
/*     */     }
/* 290 */     return arrayOfPoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static void addCross(HgCan3 paramHgCan3, Agt[] paramArrayOfAgt)
/*     */   {
/* 297 */     paramHgCan3.setCross(getOutAgtPos(paramArrayOfAgt, paramHgCan3));
/* 298 */     paramHgCan3.update();paramHgCan3.update();
/*     */   }
/*     */   
/*     */   static Point[] getOutAgtPos(Agt[] paramArrayOfAgt, HgCan3 paramHgCan3)
/*     */   {
/* 303 */     int i = 0;
/* 304 */     for (int j = 0; j < paramArrayOfAgt.length; j++) {
/* 305 */       if (paramArrayOfAgt[j].state == 0) i++;
/*     */     }
/* 307 */     if (i == 0) return null;
/* 308 */     Point[] arrayOfPoint = new Point[i];
/* 309 */     int k = 0;
/* 310 */     for (int m = 0; m < paramArrayOfAgt.length; m++) {
/* 311 */       if (paramArrayOfAgt[m].state == 0) arrayOfPoint[(k++)] = paramHgCan3.getPos(m);
/*     */     }
/* 313 */     return arrayOfPoint;
/*     */   }
/*     */   
/*     */   static void showJtNb(Agt[] paramArrayOfAgt)
/*     */   {
/* 318 */     System.out.println("\nHypertree nbs of agents: ");
/* 319 */     for (int i = 0; i < paramArrayOfAgt.length; i++) {
/* 320 */       System.out.print("  A" + i + ":");
/* 321 */       for (int j = 0; j < paramArrayOfAgt[i].jtnb.length; j++)
/* 322 */         System.out.print(" A" + paramArrayOfAgt[i].jtnb[j]);
/* 323 */       System.out.println();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/DisJtBoundSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */