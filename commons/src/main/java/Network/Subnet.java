/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class Subnet extends Subdag
/*     */ {
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public Subnet() {}
/*     */   
/*     */   Subnet(Subdag paramSubdag)
/*     */   {
/*  14 */     super(paramSubdag);
/*     */   }
/*     */   
/*  17 */   Subnet(Subnet paramSubnet) { super(paramSubnet); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setBelief()
/*     */   {
/*  24 */     this.jt.setBelief(this.net, getBeliefVars());
/*     */   }
/*     */   
/*     */   public void getSet()
/*     */   {
/*  29 */     this.jt.getSet(this.net);
/*     */   }
/*     */   
/*     */   void unifyBelief()
/*     */   {
/*  34 */     this.jt.setDumbSepsetBelief();
/*  35 */     this.jt.unifyBelief();
/*  36 */     this.jt.normalizeBelief();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] getLinkageBelief(int paramInt, String[] paramArrayOfString)
/*     */   {
/*  46 */     int[] arrayOfInt1 = this.net.getIndex(paramArrayOfString);
/*  47 */     int[] arrayOfInt2 = MATH.sort(arrayOfInt1);
/*     */     
/*  49 */     JoinTreeM localJoinTreeM = this.lt[getNbrIndex(paramInt)];
/*  50 */     int i = localJoinTreeM.getCqIndex(arrayOfInt1);
/*  51 */     int[] arrayOfInt3 = localJoinTreeM.getStateCount(i);
/*  52 */     float[] arrayOfFloat = localJoinTreeM.getBelief(i);
/*  53 */     return MATH.reorderBelief(arrayOfInt2, arrayOfInt3, arrayOfFloat, arrayOfInt1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void absorbThroughLinkage(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*     */   {
/*  63 */     int[] arrayOfInt1 = this.jt.getCqMember(paramInt);
/*  64 */     int[] arrayOfInt2 = this.jt.getStateCount(paramInt);
/*  65 */     float[] arrayOfFloat1 = this.jt.getBelief(paramInt);
/*  66 */     float[] arrayOfFloat2 = MATH.productOfTwo(arrayOfInt1, arrayOfFloat1, paramArrayOfInt, paramArrayOfFloat2, arrayOfInt1, arrayOfInt2);
/*  67 */     float[] arrayOfFloat3 = MATH.division(arrayOfInt1, arrayOfFloat2, paramArrayOfInt, paramArrayOfFloat1, arrayOfInt1, arrayOfInt2);
/*  68 */     this.jt.setBelief(paramInt, arrayOfFloat3);
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
/*     */   void updateBelief(int paramInt1, int paramInt2, Subnet[] paramArrayOfSubnet)
/*     */   {
/*  89 */     HelpPanel.appendHelp(paramInt1 + "<<" + paramInt2 + ":");
/*  90 */     int i = getNbrIndex(paramInt2);
/*  91 */     JoinTreeM localJoinTreeM = this.lt[i];
/*  92 */     int j = localJoinTreeM.getNodeCount();
/*  93 */     for (int k = 0; k < j; k++) {
/*  94 */       int[] arrayOfInt = localJoinTreeM.getCqMember(k);
/*  95 */       float[] arrayOfFloat1 = localJoinTreeM.getBelief(k);
/*  96 */       String[] arrayOfString = this.net.getLabel(arrayOfInt);
/*  97 */       int m = this.jt.getCqByLabel(localJoinTreeM.getLabel(k));
/*     */       
/*  99 */       float[] arrayOfFloat2 = paramArrayOfSubnet[paramInt2].getLinkageBelief(paramInt1, arrayOfString);
/*     */       
/*     */ 
/* 102 */       absorbThroughLinkage(m, arrayOfInt, arrayOfFloat1, arrayOfFloat2);
/* 103 */       localJoinTreeM.setBelief(k, arrayOfFloat2);
/*     */     }
/* 105 */     this.jt.unifyBelief();
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
/*     */   void collectBelief(int paramInt1, int paramInt2, Subnet[] paramArrayOfSubnet)
/*     */   {
/* 122 */     HelpPanel.appendHelp(paramInt1 + "<" + paramInt2 + ",");
/* 123 */     if ((this.nbr.length == 1) && (getNeighbor(0) == paramInt1)) {
/* 124 */       setLinkageBelief(paramInt1);
/* 125 */       return;
/*     */     }
/* 127 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 128 */       int j = getNeighbor(i);
/* 129 */       if (j != paramInt1) {
/* 130 */         paramArrayOfSubnet[j].collectBelief(paramInt2, j, paramArrayOfSubnet);
/* 131 */         HelpPanel.appendHelp(paramInt2 + "<" + j + "!,");
/* 132 */         updateBelief(paramInt2, j, paramArrayOfSubnet);
/*     */       } }
/* 134 */     if (paramInt1 != -1) { setLinkageBelief(paramInt1);
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
/*     */   void distributeBelief(int paramInt1, int paramInt2, Subnet[] paramArrayOfSubnet)
/*     */   {
/* 149 */     HelpPanel.appendHelp(paramInt1 + ">" + paramInt2 + ",");
/* 150 */     if (paramInt1 != -1) updateBelief(paramInt2, paramInt1, paramArrayOfSubnet);
/* 151 */     for (int i = 0; i < this.nbr.length; i++) {
/* 152 */       int j = getNeighbor(i);
/* 153 */       if (j != paramInt1) {
/* 154 */         setLinkageBelief(j);
/* 155 */         paramArrayOfSubnet[j].distributeBelief(paramInt2, j, paramArrayOfSubnet);
/* 156 */         HelpPanel.appendHelp(paramInt2 + ">" + j + "!,");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public ChordalGraph getChordalGraph()
/*     */   {
/* 164 */     return this.cdg;
/*     */   }
/*     */   
/*     */   public JoinForest getJoinTree()
/*     */   {
/* 169 */     return this.jt;
/*     */   }
/*     */   
/*     */   public JoinForest getLinkageTree(int paramInt) {
/* 173 */     return this.lt[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setLinkageBelief(int paramInt)
/*     */   {
/* 180 */     int i = getNbrIndex(paramInt);
/* 181 */     this.lt[i].setLinkageBelief(this.jt);
/*     */   }
/*     */   
/*     */   public void setLinkageBelief()
/*     */   {
/* 186 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 187 */       this.lt[i].setLinkageBelief(this.jt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setDumbLinkageBelief()
/*     */   {
/* 194 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 195 */       this.lt[i].setDumbLinkageBelief(this.jt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Subnet loadHnode(BufferedReader paramBufferedReader, String paramString)
/*     */   {
/* 205 */     return new Subnet(Subdag.load(paramBufferedReader, paramString));
/*     */   }
/*     */   
/*     */   public void loadBn()
/*     */   {
/* 210 */     this.net = BayesNet.load(this.path + ".bn");
/*     */   }
/*     */   
/*     */   public void loadJt()
/*     */   {
/* 215 */     this.jt = new JoinTreeM(JoinForest.load(this.path + ".jtm"));
/*     */   }
/*     */   
/*     */   void loadLinkageTree(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 220 */     this.lt[paramInt] = JoinTreeM.loadLinkageTree(paramBufferedReader);
/*     */   }
/*     */   
/*     */   void loadLinkageTree(BufferedReader paramBufferedReader) {
/* 224 */     int i = UTIL.loadInt(paramBufferedReader);
/* 225 */     this.lt = new JoinTreeM[i];
/*     */     
/* 227 */     for (int j = 0; j < i; j++) {
/* 228 */       UTIL.skipLine(paramBufferedReader);
/* 229 */       loadLinkageTree(paramBufferedReader, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadLinkageTree() {
/*     */     try {
/* 235 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(this.path + ".lkg"));
/* 236 */       HelpPanel.addHelp("Loading linkage tree from " + this.path + ".lkg");
/* 237 */       loadLinkageTree(localBufferedReader);
/* 238 */       localBufferedReader.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 240 */       HelpPanel.showError("Unable to load " + this.path + ".lkg");
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveJoinTree()
/*     */   {
/* 246 */     this.jt.save(new String(getPath() + ".jtm"));
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 251 */     this.lt[paramInt].saveLinkageTree(paramPrintWriter, paramInt);
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter) {
/* 255 */     int i = getNeighborCount();
/* 256 */     paramPrintWriter.println(i + "  #_of_lkg_trees");
/* 257 */     for (int j = 0; j < i; j++) {
/* 258 */       paramPrintWriter.println();
/* 259 */       saveLinkageTree(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveLinkageTree()
/*     */   {
/* 265 */     String str = new String(getPath() + ".lkg");
/*     */     try {
/* 267 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(str));
/* 268 */       HelpPanel.addHelp("Saving\t" + str);
/* 269 */       saveLinkageTree(localPrintWriter);
/* 270 */       localPrintWriter.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 272 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showSubnet()
/*     */   {
/* 279 */     for (int i = 0; i < this.lt.length; i++) this.lt[i].showJoinForest();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Subnet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */