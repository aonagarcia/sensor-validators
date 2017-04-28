/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgGraphLID
/*     */   extends AgGraphLI
/*     */   implements IntAgtLID
/*     */ {
/*  17 */   LJoinTreeM[][] mjf = (LJoinTreeM[][])null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LJoinTreeM[][] getMsgJF()
/*     */   {
/*  26 */     return this.mjf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStateCount()
/*     */   {
/*  33 */     int[] arrayOfInt = this.net.getStateCount();
/*  34 */     this.jt.setStateCount(arrayOfInt);
/*  35 */     for (int i = 0; i < this.mjf.length; i++) {
/*  36 */       for (int j = 0; j < this.mjf[i].length; j++) { this.mjf[i][j].setStateCount(arrayOfInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPoten(int[] paramArrayOfInt)
/*     */   {
/*  45 */     this.jt.setPotenRest(this.net, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int[][] getMjfLkg(int paramInt)
/*     */   {
/*  52 */     int[] arrayOfInt1 = this.sep[paramInt].getLocalIndex();
/*  53 */     int i = this.mjf[paramInt].length;
/*  54 */     int[][] arrayOfInt = new int[i][];
/*  55 */     for (int j = 0; j < i; j++) {
/*  56 */       int k = this.mjf[paramInt][j].getMarked();
/*  57 */       int[] arrayOfInt2 = this.mjf[paramInt][j].getCqMember(k);
/*  58 */       arrayOfInt[j] = MATH.getIntersection(arrayOfInt2, arrayOfInt1);
/*     */     }
/*  60 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[][] getMjfLkgStr(int paramInt)
/*     */   {
/*  66 */     int[][] arrayOfInt = getMjfLkg(paramInt);
/*  67 */     String[][] arrayOfString = new String[arrayOfInt.length][];
/*  68 */     for (int i = 0; i < arrayOfInt.length; i++) arrayOfString[i] = this.net.getLabel(arrayOfInt[i]);
/*  69 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMjfPoten(int[] paramArrayOfInt)
/*     */   {
/*  77 */     for (int i = 0; i < this.mjf.length; i++) {
/*  78 */       int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt);
/*  79 */       for (int j = 0; j < this.mjf[i].length; j++) {
/*  80 */         arrayOfInt = this.mjf[i][j].setPotenRest(this.net, arrayOfInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void clrMjfBufPoten()
/*     */   {
/*  87 */     for (int i = 0; i < this.mjf.length; i++) {
/*  88 */       for (int j = 0; j < this.mjf[i].length; j++) this.mjf[i][j].setBufferPoten();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clrMjfLkgBufPoten()
/*     */   {
/*  94 */     for (int i = 0; i < this.mjf.length; i++) {
/*  95 */       for (int j = 0; j < this.mjf[i].length; j++) this.mjf[i][j].setLkgBufPoten();
/*     */     }
/*     */   }
/*     */   
/*     */   public LazyBelSet[] getMjfBufPoten(int paramInt)
/*     */   {
/* 101 */     int[] arrayOfInt1 = this.sep[paramInt].getLocalIndex();
/* 102 */     int i = this.mjf[paramInt].length;
/* 103 */     LazyBelSet[] arrayOfLazyBelSet = new LazyBelSet[i];
/* 104 */     for (int j = 0; j < i; j++) {
/* 105 */       int k = this.mjf[paramInt][j].getMarked();
/* 106 */       int[] arrayOfInt2 = this.mjf[paramInt][j].getCqMember(k);
/* 107 */       int[] arrayOfInt3 = this.mjf[paramInt][j].getStateCount(k);
/* 108 */       int[] arrayOfInt4 = MATH.getIntersection(arrayOfInt2, arrayOfInt1);
/* 109 */       LazyBelSet localLazyBelSet = this.mjf[paramInt][j].getPotenPlusAllMsg(k);
/* 110 */       arrayOfLazyBelSet[j] = LazyBelSet.dupLazyBelSet(localLazyBelSet);
/* 111 */       arrayOfLazyBelSet[j].setMargin(arrayOfInt2, arrayOfInt3, arrayOfInt4);
/*     */     }
/* 113 */     return arrayOfLazyBelSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void collectMJFPotential(int paramInt)
/*     */   {
/* 123 */     for (int i = 0; i < this.mjf[paramInt].length; i++) {
/* 124 */       LJoinTreeM localLJoinTreeM = this.mjf[paramInt][i];
/* 125 */       int j = localLJoinTreeM.getNodeCount();
/* 126 */       for (int k = 0; k < j; k++) {
/* 127 */         if (localLJoinTreeM.isMarked(k)) {
/* 128 */           localLJoinTreeM.collectPotential1(k);
/* 129 */           break;
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void enterFullyObserved(String[] paramArrayOfString)
/*     */   {
/* 157 */     if (paramArrayOfString == null) return;
/* 158 */     int i = Integer.parseInt(paramArrayOfString[0]);
/* 159 */     for (int j = 0; j < i; j++) {
/* 160 */       int k = j * 2;
/* 161 */       int m = this.net.getIndex(paramArrayOfString[(k + 1)]);
/* 162 */       if (!MATH.member(m, this.jt.obsvar))
/*     */       {
/* 164 */         int n = Integer.parseInt(paramArrayOfString[(k + 2)]);
/* 165 */         int i1 = this.net.getStateCount(m);
/* 166 */         int[] arrayOfInt = new int[i1];
/* 167 */         for (int i2 = 0; i2 < i1; i2++) arrayOfInt[i2] = 0;
/* 168 */         arrayOfInt[n] = 1;
/*     */         
/* 170 */         this.jt.enterEvidenceToCq(m, arrayOfInt);
/* 171 */         enterEvidenceToMJF(m, arrayOfInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void enterEvidenceToMJF(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 178 */     for (int i = 0; i < this.mjf.length; i++) {
/* 179 */       for (int j = 0; j < this.mjf[i].length; j++) {
/* 180 */         this.mjf[i][j].enterEvidenceToCq(paramInt, paramArrayOfInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void loadJtTrunk()
/*     */   {
/* 188 */     this.jt = new LJoinTreeM(HyperGraph.loadJoinTreeTrunk(this.path + ".djtt"));
/*     */   }
/*     */   
/*     */   void loadMsgJFTrunk(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 193 */     int i = UTIL.loadInt(paramBufferedReader);
/* 194 */     this.mjf[paramInt] = new LJoinTreeM[i];
/* 195 */     for (int j = 0; j < i; j++) {
/* 196 */       UTIL.skipLine(paramBufferedReader);
/* 197 */       this.mjf[paramInt][j] = new LJoinTreeM(HyperGrafM.loadLinkageTreeTrunk(paramBufferedReader));
/* 198 */       UTIL.skipLine(paramBufferedReader);
/* 199 */       int k = UTIL.loadInt(paramBufferedReader);
/* 200 */       this.mjf[paramInt][j].setMark(k, true);
/*     */     }
/*     */   }
/*     */   
/*     */   void loadMsgJFTrunk(BufferedReader paramBufferedReader) {
/* 205 */     int i = UTIL.loadInt(paramBufferedReader);
/* 206 */     this.mjf = new LJoinTreeM[i][];
/* 207 */     for (int j = 0; j < i; j++) {
/* 208 */       UTIL.skipLine(paramBufferedReader);
/* 209 */       loadMsgJFTrunk(paramBufferedReader, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadMsgJFTrunk() {
/* 214 */     String str = new String(this.path + ".mjft");
/*     */     try {
/* 216 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(str));
/* 217 */       HelpPanel.addHelp("Loading message JFs from " + str);
/* 218 */       loadMsgJFTrunk(localBufferedReader);
/* 219 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 221 */       HelpPanel.showError("Unable to load " + str);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadNodeAssigned()
/*     */   {
/* 227 */     loadNodeAssigned(this.path + ".dnas");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadInferJT(BufferedReader paramBufferedReader)
/*     */   {
/* 237 */     this.jt = new LJoinTreeM();
/* 238 */     this.jt.loadInferJT(paramBufferedReader, this.net);
/*     */   }
/*     */   
/*     */   public void loadInferJT() {
/* 242 */     String str = new String(this.path + ".djt");
/*     */     try {
/* 244 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(str));
/* 245 */       HelpPanel.addHelp("Loading inference JT from " + str);
/* 246 */       loadInferJT(localBufferedReader);
/* 247 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 249 */       HelpPanel.showError("Unable to load " + str);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveInferJT()
/*     */   {
/* 255 */     String str = new String(getPath() + ".djt");
/*     */     try {
/* 257 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 258 */       HelpPanel.addHelp("Saving " + str);
/* 259 */       this.jt.saveInferJT(localPrintWriter);
/* 260 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 262 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadMsgJF(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 271 */     int i = UTIL.loadInt(paramBufferedReader);
/* 272 */     this.mjf[paramInt] = new LJoinTreeM[i];
/* 273 */     for (int j = 0; j < i; j++) {
/* 274 */       UTIL.skipLine(paramBufferedReader);
/* 275 */       this.mjf[paramInt][j] = new LJoinTreeM();
/* 276 */       this.mjf[paramInt][j].loadMsgJT(paramBufferedReader, this.net);
/* 277 */       UTIL.skipLine(paramBufferedReader);
/* 278 */       int k = UTIL.loadInt(paramBufferedReader);
/* 279 */       this.mjf[paramInt][j].setMark(k, true);
/*     */     }
/*     */   }
/*     */   
/*     */   void loadMsgJF(BufferedReader paramBufferedReader) {
/* 284 */     int i = UTIL.loadInt(paramBufferedReader);
/* 285 */     this.mjf = new LJoinTreeM[i][];
/* 286 */     for (int j = 0; j < i; j++) {
/* 287 */       UTIL.skipLine(paramBufferedReader);
/* 288 */       loadMsgJF(paramBufferedReader, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadMsgJF() {
/* 293 */     String str = new String(this.path + ".mjf");
/*     */     try {
/* 295 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(str));
/* 296 */       HelpPanel.addHelp("Loading message JFs from " + str);
/* 297 */       loadMsgJF(localBufferedReader);
/* 298 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 300 */       HelpPanel.showError("Unable to load " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void saveMsgJF(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
/*     */   {
/* 307 */     this.mjf[paramInt1][paramInt2].saveMsgJT(paramPrintWriter, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void saveMsgJF(PrintWriter paramPrintWriter, int paramInt) {
/* 311 */     int i = this.mjf[paramInt].length;
/* 312 */     paramPrintWriter.println(i + " #JTs_in_msg_JF_" + paramInt);
/* 313 */     for (int j = 0; j < i; j++) {
/* 314 */       paramPrintWriter.println();
/* 315 */       saveMsgJF(paramPrintWriter, paramInt, j);
/*     */     }
/*     */   }
/*     */   
/*     */   void saveMsgJF(PrintWriter paramPrintWriter) {
/* 320 */     int i = getNeighborCount();
/* 321 */     paramPrintWriter.println(i + "  #msg_JFs");
/* 322 */     for (int j = 0; j < i; j++) {
/* 323 */       paramPrintWriter.println();
/* 324 */       saveMsgJF(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveMsgJF()
/*     */   {
/* 330 */     String str = new String(getPath() + ".mjf");
/*     */     try {
/* 332 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 333 */       HelpPanel.addHelp("Saving " + str);
/* 334 */       saveMsgJF(localPrintWriter);
/* 335 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 337 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showAgGraphLID()
/*     */   {
/* 344 */     System.out.println("  [AgGraphLID]");
/* 345 */     System.out.println("  Local\tBN:");
/* 346 */     this.net.showBayesNet();
/* 347 */     System.out.println("  Local\tJT:");
/* 348 */     this.jt.showLJoinTreeM();
/* 349 */     for (int i = 0; i < this.mjf.length; i++) {
/* 350 */       System.out.println("  Msg\tJF " + i + ":");
/* 351 */       for (int j = 0; j < this.mjf[i].length; j++) {
/* 352 */         System.out.println("  Msg JF " + i + ",\tJT " + j + ":");
/* 353 */         this.mjf[i][j].showLJoinTreeM();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphLID.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */