/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class AgGraphI
/*     */   extends AgGraph implements IntAgtI
/*     */ {
/*  13 */   JoinTreeM jt = null;
/*  14 */   JoinTreeM[] lt = null;
/*     */   
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/*  18 */     getSumLkgtSss(paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStateCount()
/*     */   {
/*  29 */     int[] arrayOfInt = this.net.getStateCount();
/*  30 */     this.jt.setStateCount(arrayOfInt);
/*  31 */     for (int i = 0; i < this.lt.length; i++) this.lt[i].setStateCount(arrayOfInt);
/*     */   }
/*     */   
/*     */   static void getSumLkgtSss(String[] paramArrayOfString)
/*     */   {
/*  36 */     System.out.println("Usage: java Network.AgGraphI #files f1.lkg ...\n");
/*     */     
/*  38 */     int i = Integer.parseInt(paramArrayOfString[0]);
/*  39 */     int j = 0;
/*  40 */     for (int k = 1; k <= i; k++) {
/*  41 */       System.out.println("  ** File: " + paramArrayOfString[k] + " **");
/*  42 */       AgGraphI localAgGraphI = new AgGraphI();
/*  43 */       localAgGraphI.loadLinkageTree(paramArrayOfString[k]);
/*  44 */       for (int m = 0; m < localAgGraphI.lt.length; m++) {
/*  45 */         int[] arrayOfInt = localAgGraphI.lt[m].getCqSpaceSize();
/*  46 */         UTIL.showList("Lkg Tree\t" + m + " Cq Sizes:\t", arrayOfInt);
/*  47 */         int n = 0;
/*  48 */         for (int i1 = 0; i1 < arrayOfInt.length; i1++) n += arrayOfInt[i1];
/*  49 */         System.out.println("\t Sss = " + n + "\n");
/*  50 */         j += n;
/*     */       }
/*     */     }
/*  53 */     System.out.println(" < Sum of Lkg Tree SSS = " + j / 2 + "\t>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JoinTreeM getLinkageTree1(int paramInt)
/*     */   {
/*  60 */     return this.lt[paramInt];
/*     */   }
/*     */   
/*     */   public JoinTreeM getJoinTree() {
/*  64 */     return this.jt;
/*     */   }
/*     */   
/*     */   public int getCqByLabel(String paramString)
/*     */   {
/*  69 */     return this.jt.getCqByLabel(paramString);
/*     */   }
/*     */   
/*     */   public boolean isMarked(int paramInt)
/*     */   {
/*  74 */     return this.net.isMarked(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBelief(int[] paramArrayOfInt)
/*     */   {
/*  83 */     this.jt.setBelief(this.net, paramArrayOfInt);
/*  84 */     this.jt.setDumbSepsetBelief();
/*     */   }
/*     */   
/*     */   public void getSet()
/*     */   {
/*  89 */     this.jt.getSet(this.net);
/*     */   }
/*     */   
/*     */   public void setDumbLinkageBelief(int paramInt) {
/*  93 */     this.lt[paramInt].setDumbLinkageBelief(this.jt);
/*     */   }
/*     */   
/*     */   public void setLinkageBelief()
/*     */   {
/*  98 */     for (int i = 0; i < getNeighborCount(); i++) { this.lt[i].setLinkageBelief(this.jt);
/*     */     }
/*     */   }
/*     */   
/*     */   public float[] getPot(int[] paramArrayOfInt)
/*     */   {
/* 104 */     int i = this.jt.getCqHome(paramArrayOfInt);
/* 105 */     if (i == -1) {
/* 106 */       HelpPanel.showError("Err: No cluster contains given var set.");
/* 107 */       return null;
/*     */     }
/* 109 */     return this.jt.getVarMargin(paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void multiplyPot(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 115 */     int i = this.jt.getCqHome(paramArrayOfInt);
/* 116 */     if (i == -1) {
/* 117 */       HelpPanel.showError("Err: No cluster contains given var set.");
/* 118 */       return;
/*     */     }
/* 120 */     this.jt.setBeliefProduct(i, paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unifyBelief()
/*     */   {
/* 130 */     this.jt.unifyBelief();
/* 131 */     this.jt.normalizeBelief();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void absorbThroughLinkage(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*     */   {
/* 142 */     int[] arrayOfInt1 = this.jt.getCqMember(paramInt);
/* 143 */     int[] arrayOfInt2 = this.jt.getStateCount(paramInt);
/* 144 */     float[] arrayOfFloat1 = this.jt.getBelief(paramInt);
/* 145 */     float[] arrayOfFloat2 = MATH.productOfTwo(arrayOfInt1, arrayOfFloat1, paramArrayOfInt, paramArrayOfFloat2, arrayOfInt1, arrayOfInt2);
/* 146 */     float[] arrayOfFloat3 = MATH.division(arrayOfInt1, arrayOfFloat2, paramArrayOfInt, paramArrayOfFloat1, arrayOfInt1, arrayOfInt2);
/* 147 */     this.jt.setBelief(paramInt, arrayOfFloat3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadBn()
/*     */   {
/* 154 */     this.net = BayesNet.load(this.path + ".bn");
/*     */   }
/*     */   
/*     */   public void loadJtTrunk()
/*     */   {
/* 159 */     this.jt = new JoinTreeM(HyperGraph.loadJoinTreeTrunk(this.path + ".jtt"));
/*     */   }
/*     */   
/*     */   public void loadJt()
/*     */   {
/* 164 */     this.jt = new JoinTreeM(JoinForest.load(this.path + ".jtm"));
/*     */   }
/*     */   
/*     */   void loadLinkageTreeTrunk(BufferedReader paramBufferedReader)
/*     */   {
/* 169 */     int i = UTIL.loadInt(paramBufferedReader);
/* 170 */     this.lt = new JoinTreeM[i];
/* 171 */     for (int j = 0; j < i; j++) {
/* 172 */       UTIL.skipLine(paramBufferedReader);
/* 173 */       this.lt[j] = new JoinTreeM(HyperGrafM.loadLinkageTreeTrunk(paramBufferedReader));
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadLinkageTreeTrunk() {
/*     */     try {
/* 179 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.path + ".ltt"));
/* 180 */       HelpPanel.addHelp("Loading linkage tree from " + this.path + ".ltt");
/* 181 */       loadLinkageTreeTrunk(localBufferedReader);
/* 182 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 184 */       HelpPanel.showError("Unable to load " + this.path + ".ltt");
/*     */     }
/*     */   }
/*     */   
/*     */   void loadLinkageTree(BufferedReader paramBufferedReader)
/*     */   {
/* 190 */     int i = UTIL.loadInt(paramBufferedReader);
/* 191 */     this.lt = new JoinTreeM[i];
/*     */     
/* 193 */     for (int j = 0; j < i; j++) {
/* 194 */       UTIL.skipLine(paramBufferedReader);
/* 195 */       this.lt[j] = JoinTreeM.loadLinkageTree(paramBufferedReader);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadLinkageTree(String paramString)
/*     */   {
/*     */     try {
/* 202 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 203 */       HelpPanel.addHelp("Loading linkage tree from " + paramString);
/* 204 */       loadLinkageTree(localBufferedReader);
/* 205 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 207 */       HelpPanel.showError("Unable to load " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadLinkageTree()
/*     */   {
/* 213 */     loadLinkageTree(this.path + ".lkg");
/*     */   }
/*     */   
/*     */   public void loadNodeAssigned(String paramString)
/*     */   {
/*     */     try {
/* 219 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 220 */       HelpPanel.addHelp("Loading nodes assigned from " + paramString);
/*     */       
/* 222 */       int[] arrayOfInt = UTIL.loadIntList(localBufferedReader);
/* 223 */       this.net.setMark(false);
/* 224 */       for (int i = 0; i < arrayOfInt.length; i++) { this.net.setMark(arrayOfInt[i]);
/*     */       }
/* 226 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 228 */       HelpPanel.showError("Unable to load " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadNodeAssigned() {
/* 233 */     loadNodeAssigned(this.path + ".nas");
/*     */   }
/*     */   
/*     */   public void saveJoinTree()
/*     */   {
/* 238 */     this.jt.save(new String(getPath() + ".jtm"));
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 243 */     this.lt[paramInt].saveLinkageTree(paramPrintWriter, paramInt);
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter) {
/* 247 */     int i = getNeighborCount();
/* 248 */     paramPrintWriter.println(i + "  #_of_lkg_trees");
/* 249 */     for (int j = 0; j < i; j++) {
/* 250 */       paramPrintWriter.println();
/* 251 */       saveLinkageTree(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveLinkageTree()
/*     */   {
/* 257 */     String str = new String(getPath() + ".lkg");
/*     */     try {
/* 259 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 260 */       HelpPanel.addHelp("Saving\t" + str);
/* 261 */       saveLinkageTree(localPrintWriter);
/* 262 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 264 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */