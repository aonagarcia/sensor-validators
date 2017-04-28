/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ public class AgGraphLTD
/*     */   extends AgGraphT
/*     */   implements IntAgtLTD
/*     */ {
/*  15 */   ChordalGraph[] mcg = null;
/*  16 */   HyperGrafM[][] mjf = (HyperGrafM[][])null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgGraph()
/*     */   {
/*  24 */     int i = getNeighborCount();
/*  25 */     this.mcg = new ChordalGraph[i];
/*  26 */     for (int j = 0; j < i; j++) this.mcg[j] = new ChordalGraph(this.cdg);
/*     */   }
/*     */   
/*     */   public ChordalGraph getMsgGraph(int paramInt)
/*     */   {
/*  31 */     return this.mcg[paramInt];
/*     */   }
/*     */   
/*     */   public int getMsgJTCount(int paramInt)
/*     */   {
/*  36 */     return this.mjf[paramInt].length;
/*     */   }
/*     */   
/*     */   public HyperGrafM getMessageJF(int paramInt1, int paramInt2)
/*     */   {
/*  41 */     return this.mjf[paramInt1][paramInt2];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void pickAddFillIn(String[][] paramArrayOfString, int paramInt)
/*     */   {
/*  48 */     this.mcg[paramInt].pickAddFillIn(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public void setChordalGraph()
/*     */   {
/*  53 */     this.cdg.setChordalGraph();
/*     */   }
/*     */   
/*     */   public void setChordalGraph(int paramInt, int[] paramArrayOfInt) {
/*  57 */     this.mcg[paramInt].setChordalGraph(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public String[][] nodesToLinksLabel(int paramInt, int[] paramArrayOfInt)
/*     */   {
/*  62 */     return this.mcg[paramInt].nodesToLinksLabel(paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setJoinTree(Rectangle paramRectangle)
/*     */   {
/*  69 */     HyperGraph localHyperGraph = HyperGraph.setJoinForest(this.cdg, paramRectangle);
/*  70 */     this.jt = new HyperGrafM(localHyperGraph);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMessageJF()
/*     */   {
/*  77 */     int i = getNeighborCount();
/*  78 */     this.mjf = new HyperGrafM[i][];
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
/*     */   public void setMessageJF(int paramInt)
/*     */   {
/* 116 */     ChordalGraph localChordalGraph1 = new ChordalGraph(this.mcg[paramInt]);
/* 117 */     int[] arrayOfInt1 = MATH.sort(getDsepsetLocalIndex(paramInt));
/*     */     
/* 119 */     ChordalGraph localChordalGraph2 = ChordalGraph.makeSubGraph(localChordalGraph1, arrayOfInt1);
/*     */     
/* 121 */     localChordalGraph2.setChordalGraph();
/* 122 */     int[][] arrayOfInt = localChordalGraph2.findClique();
/* 123 */     int i = arrayOfInt.length;
/* 124 */     for (int j = 0; j < i; j++) {
/* 125 */       for (int k = 0; k < arrayOfInt[j].length; k++) { arrayOfInt[j][k] = arrayOfInt1[arrayOfInt[j][k]];
/*     */       }
/*     */     }
/* 128 */     Rectangle localRectangle = new Rectangle(500, 500);
/*     */     
/* 130 */     if (i == 1) {
/* 131 */       this.mjf[paramInt] = new HyperGrafM[1];
/* 132 */       this.mjf[paramInt][0] = new HyperGrafM(HyperGraph.setJoinForest(localChordalGraph1, localRectangle));
/* 133 */       this.mjf[paramInt][0].setMark(this.mjf[paramInt][0].getCqHome(arrayOfInt1), true);
/* 134 */       return;
/*     */     }
/*     */     
/* 137 */     localChordalGraph1.completeNodeSet(arrayOfInt1);
/* 138 */     localChordalGraph1.setChordalGraph();
/* 139 */     HyperGrafM localHyperGrafM = new HyperGrafM(HyperGraph.setJoinForestAshg(localChordalGraph1));
/*     */     
/* 141 */     boolean[] arrayOfBoolean = new boolean[i];
/* 142 */     for (int m = 0; m < i; m++) { arrayOfBoolean[m] = false;
/*     */     }
/* 144 */     m = localHyperGrafM.getCqIndex(arrayOfInt1);
/* 145 */     int n = localHyperGrafM.getNeighborCount(m);
/* 146 */     HyperGrafM[] arrayOfHyperGrafM = new HyperGrafM[n + i];
/* 147 */     int i1 = 0;
/*     */     
/* 149 */     int i2 = 0;
/* 150 */     for (int i3 = 0; i3 < n; i3++) {
/* 151 */       int i4 = localHyperGrafM.getNeighbor(m, i3);
/* 152 */       int[] arrayOfInt2 = localHyperGrafM.getCqMember(i4);
/* 153 */       arrayOfHyperGrafM[i1] = HyperGrafM.getSubGraph(localHyperGrafM, m, i4);
/*     */       
/* 155 */       int[] arrayOfInt3 = MATH.getIntersection(arrayOfInt1, arrayOfInt2);
/* 156 */       int i5 = -1;
/* 157 */       for (int i6 = 0; i6 < i; i6++) {
/* 158 */         int[] arrayOfInt4 = MATH.getIntersection(arrayOfInt[i6], arrayOfInt2);
/* 159 */         if (MATH.isEqualSet(arrayOfInt4, arrayOfInt3)) {
/* 160 */           i5 = i6; break;
/*     */         } }
/*     */       int i7;
/* 163 */       if (arrayOfBoolean[i5] == 0) {
/* 164 */         arrayOfBoolean[i5] = true;
/* 165 */         i6 = arrayOfHyperGrafM[i1].getCqIndex(arrayOfInt2);
/* 166 */         if (!MATH.isSubset(arrayOfInt[i5], arrayOfInt2)) {
/* 167 */           i7 = arrayOfHyperGrafM[i1].getNodeCount();
/* 168 */           arrayOfHyperGrafM[i1] = HyperGrafM.addLeafCq(arrayOfHyperGrafM[i1], i6, arrayOfInt[i5]);
/* 169 */           arrayOfHyperGrafM[i1].setMark(i7, true);
/* 170 */           arrayOfHyperGrafM[i1].cq[i7].setLabel("Q" + i2++);
/*     */         }
/*     */         else {
/* 173 */           arrayOfHyperGrafM[i1].setMark(i6, true);
/*     */         }
/* 175 */         i1++;
/*     */       }
/*     */       else {
/* 178 */         for (i6 = 0; i6 < i1; i6++) {
/* 179 */           i7 = arrayOfHyperGrafM[i6].getCqIndex(arrayOfInt[i5]);
/* 180 */           if (i7 != -1) {
/* 181 */             String str = arrayOfHyperGrafM[i6].getLabel(i7);
/*     */             
/* 183 */             int i8 = arrayOfHyperGrafM[i1].getCqIndex(arrayOfInt2);
/* 184 */             arrayOfHyperGrafM[i1] = HyperGrafM.addLeafCq(arrayOfHyperGrafM[i1], i8, arrayOfInt[i5]);
/* 185 */             arrayOfHyperGrafM[i6] = HyperGrafM.mergeJT(arrayOfHyperGrafM[i6], arrayOfHyperGrafM[i1], arrayOfInt[i5]);
/*     */             
/* 187 */             i7 = arrayOfHyperGrafM[i6].getCqIndex(arrayOfInt[i5]);
/* 188 */             arrayOfHyperGrafM[i6].cq[i7].setLabel(str);
/* 189 */             arrayOfHyperGrafM[i6].setMark(i7, true);
/* 190 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 196 */     for (i3 = 0; i3 < i; i3++) {
/* 197 */       if (arrayOfBoolean[i3] == 0) {
/* 198 */         arrayOfHyperGrafM[i1] = new HyperGrafM(1);
/* 199 */         HNode localHNode = new HNode();
/* 200 */         localHNode.setCqMember(arrayOfInt[i3]);
/* 201 */         localHNode.setLabel("Q" + i2++);
/* 202 */         localHNode.setMark(true);
/* 203 */         arrayOfHyperGrafM[i1].cq[0] = localHNode;
/* 204 */         i1++;
/*     */       }
/*     */     }
/* 207 */     this.mjf[paramInt] = new HyperGrafM[i1];
/* 208 */     for (i3 = 0; i3 < i1; i3++) {
/* 209 */       this.mjf[paramInt][i3] = arrayOfHyperGrafM[i3];
/* 210 */       this.mjf[paramInt][i3].setPeerOrder();
/* 211 */       this.mjf[paramInt][i3].assignCqPos(localRectangle);
/* 212 */       this.mjf[paramInt][i3].tor = null;
/* 213 */       this.mjf[paramInt][i3].varCount = 0;
/* 214 */       this.mjf[paramInt][i3].varToCq = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveInferJT()
/*     */   {
/* 226 */     this.jt.save(new String(getPath() + ".djtt"));
/*     */   }
/*     */   
/*     */   public void saveNodeAssigned()
/*     */   {
/* 231 */     saveNodeAssigned(new String(getPath() + ".dnas"));
/*     */   }
/*     */   
/*     */ 
/*     */   void saveMsgJF(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
/*     */   {
/* 237 */     this.mjf[paramInt1][paramInt2].saveMsgJT(paramPrintWriter, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void saveMsgJF(PrintWriter paramPrintWriter, int paramInt) {
/* 241 */     int i = this.mjf[paramInt].length;
/* 242 */     paramPrintWriter.println(i + " #JTs_in_msg_JF_" + paramInt);
/* 243 */     for (int j = 0; j < i; j++) {
/* 244 */       paramPrintWriter.println();
/* 245 */       saveMsgJF(paramPrintWriter, paramInt, j);
/*     */     }
/*     */   }
/*     */   
/*     */   void saveMsgJF(PrintWriter paramPrintWriter) {
/* 250 */     int i = getNeighborCount();
/* 251 */     paramPrintWriter.println(i + "  #msg_JFs");
/* 252 */     for (int j = 0; j < i; j++) {
/* 253 */       paramPrintWriter.println();
/* 254 */       saveMsgJF(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveMsgJF()
/*     */   {
/* 260 */     String str = new String(getPath() + ".mjft");
/*     */     try {
/* 262 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 263 */       HelpPanel.addHelp("Saving " + str);
/* 264 */       saveMsgJF(localPrintWriter);
/* 265 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 267 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showAgGraphLTD()
/*     */   {
/* 274 */     System.out.println("  [AgGraphLTD]");
/*     */     
/*     */ 
/* 277 */     System.out.println("  Local\tchordal\tgraph:");
/* 278 */     this.cdg.showChordalGraph();
/*     */     
/* 280 */     System.out.println("  Msg graphs:");
/* 281 */     if (this.mcg == null) { System.out.println("\t null");
/*     */     } else {
/* 283 */       for (int i = 0; i < getNeighborCount(); i++) {
/* 284 */         System.out.println("  To nb " + i + ":");
/* 285 */         this.mcg[i].showChordalGraph();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void addFillinToMsgGraph(int paramInt, Point paramPoint)
/*     */   {
/* 292 */     this.mcg[paramInt].addLink(paramPoint);
/*     */   }
/*     */   
/*     */   public void addFillinToLocalGraph(Point paramPoint)
/*     */   {
/* 297 */     this.cdg.addLink(paramPoint);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphLTD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */