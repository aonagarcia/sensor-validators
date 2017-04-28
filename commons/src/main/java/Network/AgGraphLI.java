/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgGraphLI
/*     */   extends AgGraphI
/*     */   implements IntAgtLI
/*     */ {
/*  17 */   LJoinTreeM jt = null;
/*  18 */   LJoinTreeM[] lt = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LJoinTreeM getLinkageTree2(int paramInt)
/*     */   {
/*  28 */     return this.lt[paramInt];
/*     */   }
/*     */   
/*     */   public LJoinTreeM getJoinTree1() {
/*  32 */     return this.jt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStateCount()
/*     */   {
/*  41 */     int[] arrayOfInt = this.net.getStateCount();
/*  42 */     this.jt.setStateCount(arrayOfInt);
/*  43 */     for (int i = 0; i < this.lt.length; i++) { this.lt[i].setStateCount(arrayOfInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public void getSet(DirectGraph paramDirectGraph)
/*     */   {
/*  49 */     this.jt.setVarCount(paramDirectGraph.getNodeCount());
/*  50 */     this.jt.setVarToCq(paramDirectGraph);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPoten(int[] paramArrayOfInt)
/*     */   {
/*  57 */     this.jt.setPoten(this.net, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clrBufferPoten()
/*     */   {
/*  65 */     this.jt.setBufferPoten();
/*     */   }
/*     */   
/*     */   public void clrLkgBufPoten()
/*     */   {
/*  70 */     this.jt.setLkgBufPoten();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clrLkgPoten()
/*     */   {
/*  77 */     for (int i = 0; i < this.lt.length; i++) { this.lt[i].setDumbPoten();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addLkgBufPoten(int paramInt)
/*     */   {
/*  86 */     this.jt.setLkgBufPoten();
/*  87 */     for (int i = 0; i < this.lt.length; i++) {
/*  88 */       if (i != paramInt) {
/*  89 */         this.jt.addLkgBufPoten(this.lt[i]);
/*     */       }
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
/*     */   public String[] getFullyObserved(int paramInt)
/*     */   {
/* 111 */     if (this.jt.obsvar == null) { return null;
/*     */     }
/* 113 */     int[] arrayOfInt = this.sep[paramInt].getLocalIndex();
/* 114 */     Point[] arrayOfPoint = this.jt.getFullyObserved(arrayOfInt);
/* 115 */     if (arrayOfPoint == null) { return null;
/*     */     }
/* 117 */     int i = arrayOfPoint.length;
/* 118 */     String[] arrayOfString = new String[i * 2 + 1];
/* 119 */     arrayOfString[0] = new String("" + i);
/* 120 */     for (int j = 0; j < i; j++) {
/* 121 */       int k = j * 2;
/* 122 */       arrayOfString[(k + 1)] = this.net.getLabel(arrayOfPoint[j].x);
/* 123 */       arrayOfString[(k + 2)] = new String("" + arrayOfPoint[j].y);
/*     */     }
/* 125 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void enterFullyObserved(String[] paramArrayOfString)
/*     */   {
/* 132 */     if (paramArrayOfString == null) return;
/* 133 */     int i = Integer.parseInt(paramArrayOfString[0]);
/* 134 */     for (int j = 0; j < i; j++) {
/* 135 */       int k = j * 2;
/* 136 */       int m = this.net.getIndex(paramArrayOfString[(k + 1)]);
/* 137 */       if (!MATH.member(m, this.jt.obsvar))
/*     */       {
/* 139 */         int n = Integer.parseInt(paramArrayOfString[(k + 2)]);
/* 140 */         int i1 = this.net.getStateCount(m);
/* 141 */         int[] arrayOfInt = new int[i1];
/* 142 */         for (int i2 = 0; i2 < i1; i2++) arrayOfInt[i2] = 0;
/* 143 */         arrayOfInt[n] = 1;
/*     */         
/* 145 */         this.jt.enterEvidenceToCq(m, arrayOfInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void unifyPotential()
/*     */   {
/* 153 */     this.jt.unifyPotential();
/*     */   }
/*     */   
/*     */   public void unifyPotential1()
/*     */   {
/* 158 */     this.jt.unifyPotential1();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadJtTrunk()
/*     */   {
/* 165 */     this.jt = new LJoinTreeM(HyperGraph.loadJoinTreeTrunk(this.path + ".jtt"));
/*     */   }
/*     */   
/*     */   public void loadJt()
/*     */   {
/* 170 */     this.jt = LJoinTreeM.load1(this.path + ".ljtm");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadLinkageTreeTrunk(BufferedReader paramBufferedReader)
/*     */   {
/* 177 */     int i = UTIL.loadInt(paramBufferedReader);
/* 178 */     this.lt = new LJoinTreeM[i];
/* 179 */     for (int j = 0; j < i; j++) {
/* 180 */       UTIL.skipLine(paramBufferedReader);
/* 181 */       this.lt[j] = new LJoinTreeM(HyperGrafM.loadLinkageTreeTrunk(paramBufferedReader));
/*     */     }
/*     */   }
/*     */   
/*     */   void loadLinkageTree(BufferedReader paramBufferedReader)
/*     */   {
/* 187 */     int i = UTIL.loadInt(paramBufferedReader);
/* 188 */     this.lt = new LJoinTreeM[i];
/* 189 */     for (int j = 0; j < i; j++) {
/* 190 */       UTIL.skipLine(paramBufferedReader);
/* 191 */       this.lt[j] = LJoinTreeM.loadLkgTreeWithPoten(paramBufferedReader);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void loadLkgTreeWithPoten()
/*     */   {
/* 198 */     loadLinkageTree(this.path + ".llkg");
/*     */   }
/*     */   
/*     */   public void saveJoinTree()
/*     */   {
/* 203 */     this.jt.save(new String(getPath() + ".ljtm"));
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 208 */     this.lt[paramInt].saveLinkageTree(paramPrintWriter, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void saveLkgTreeWithPoten()
/*     */   {
/* 215 */     String str = new String(getPath() + ".llkg");
/*     */     try {
/* 217 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 218 */       HelpPanel.addHelp("Saving " + str);
/* 219 */       saveLinkageTree(localPrintWriter);
/* 220 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 222 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showAgGraphLI()
/*     */   {
/* 229 */     System.out.println("  [AgGraphLI]");
/* 230 */     System.out.println("  Local\tBN:");
/* 231 */     this.net.showBayesNet();
/* 232 */     System.out.println("  Local\tJT:");
/* 233 */     this.jt.showLJoinTreeM();
/* 234 */     for (int i = 0; i < this.lt.length; i++) {
/* 235 */       System.out.println("  Linkage tree " + i + ":");
/* 236 */       this.lt[i].showLJoinTreeM();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphLI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */