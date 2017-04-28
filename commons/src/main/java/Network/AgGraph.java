/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgGraph
/*     */ {
/*  22 */   String path = null;
/*  23 */   BayesNet net = null;
/*  24 */   ChordalGraph cdg = null;
/*  25 */   HyperGrafM jt = null;
/*  26 */   Dsepset[] sep = null;
/*  27 */   HyperGrafM[] lt = null;
/*     */   
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */ 
/*     */   AgGraph() {}
/*     */   
/*     */ 
/*     */   AgGraph(AgGraph paramAgGraph)
/*     */   {
/*  37 */     setAgGraph(paramAgGraph);
/*     */   }
/*     */   
/*     */   void setAgGraph(AgGraph paramAgGraph) {
/*  41 */     this.path = new String(paramAgGraph.getPath());
/*  42 */     this.net = new BayesNet(paramAgGraph.getNet());
/*  43 */     this.cdg = new ChordalGraph(paramAgGraph.getSkeleton());
/*  44 */     this.jt = new HyperGrafM(paramAgGraph.getJoinTreeTrunk());
/*  45 */     this.sep = paramAgGraph.getDsepset();
/*  46 */     this.lt = paramAgGraph.getHostTree();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPath(String paramString)
/*     */   {
/*  53 */     if (paramString == null) return;
/*  54 */     this.path = new String(paramString);
/*     */   }
/*     */   
/*     */   public String getPath()
/*     */   {
/*  59 */     if (this.path == null) return null;
/*  60 */     return new String(this.path);
/*     */   }
/*     */   
/*     */   String getName()
/*     */   {
/*  65 */     if (this.path == null) return null;
/*  66 */     return new String(UTIL.removePath(this.path));
/*     */   }
/*     */   
/*     */ 
/*     */   DirectGraph getDag()
/*     */   {
/*  72 */     return this.net;
/*     */   }
/*     */   
/*     */   public BayesNet getNet() {
/*  76 */     return this.net;
/*     */   }
/*     */   
/*     */   public ChordalGraph getSkeleton() {
/*  80 */     return this.cdg;
/*     */   }
/*     */   
/*     */   public HyperGraph getJoinTreeTrunk() {
/*  84 */     return this.jt;
/*     */   }
/*     */   
/*     */   HyperGrafM getHostTreeTrunk(int paramInt) {
/*  88 */     return this.lt[paramInt];
/*     */   }
/*     */   
/*     */   public HyperGrafM getLinkageTree(int paramInt) {
/*  92 */     return this.lt[paramInt];
/*     */   }
/*     */   
/*     */   int getLkgCount(int paramInt) {
/*  96 */     return this.lt[paramInt].getNodeCount();
/*     */   }
/*     */   
/*     */   HyperGrafM[] getHostTree()
/*     */   {
/* 101 */     if (this.lt == null) return null;
/* 102 */     HyperGrafM[] arrayOfHyperGrafM = new HyperGrafM[this.lt.length];
/* 103 */     for (int i = 0; i < this.lt.length; i++) arrayOfHyperGrafM[i] = new HyperGrafM(this.lt[i]);
/* 104 */     return arrayOfHyperGrafM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getNodeCount()
/*     */   {
/* 111 */     return this.net.getNodeCount();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getIndex(String paramString)
/*     */   {
/* 117 */     return this.net.getIndex(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getIndex(String[] paramArrayOfString)
/*     */   {
/* 123 */     return this.net.getIndex(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public String getLabel(int paramInt)
/*     */   {
/* 128 */     return this.net.getLabel(paramInt);
/*     */   }
/*     */   
/*     */   public String[] getLabel(int[] paramArrayOfInt) {
/* 132 */     return this.net.getLabel(paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getNeighborCount()
/*     */   {
/* 139 */     int i = this.sep.length;
/* 140 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 145 */   public Dsepset getDsepset(int paramInt) { return this.sep[paramInt]; }
/*     */   
/*     */   Dsepset[] getDsepset() {
/* 148 */     if (this.sep == null) return null;
/* 149 */     Dsepset[] arrayOfDsepset = new Dsepset[this.sep.length];
/* 150 */     for (int i = 0; i < this.sep.length; i++) arrayOfDsepset[i] = new Dsepset(this.sep[i]);
/* 151 */     return arrayOfDsepset;
/*     */   }
/*     */   
/*     */   public int[] getDsepsetLocalIndex(int paramInt)
/*     */   {
/* 156 */     return this.sep[paramInt].getLocalIndex();
/*     */   }
/*     */   
/*     */   public int[] getDsepsetUnion()
/*     */   {
/* 161 */     int[] arrayOfInt = null;
/* 162 */     for (int i = 0; i < this.sep.length; i++)
/* 163 */       arrayOfInt = MATH.union(arrayOfInt, this.sep[i].getLocalIndex());
/* 164 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[][] getInParentLabel(String[] paramArrayOfString)
/*     */   {
/* 171 */     return this.net.getInParentLabel(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public String[] getInParentLabel(String paramString, String[] paramArrayOfString)
/*     */   {
/* 176 */     return this.net.getInParentLabel(paramString, paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasOutParent(String paramString, String[] paramArrayOfString)
/*     */   {
/* 182 */     return this.net.hasOutParent(paramString, paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[] getBeliefVars()
/*     */   {
/* 189 */     int[] arrayOfInt1 = getDsepsetUnion();
/* 190 */     int[] arrayOfInt2 = null;
/* 191 */     for (int i = 0; i < this.net.getNodeCount(); i++) {
/* 192 */       if (!MATH.member(i, arrayOfInt1)) { arrayOfInt2 = MATH.addMember(i, arrayOfInt2);
/* 193 */       } else if (this.net.isMarked(i)) arrayOfInt2 = MATH.addMember(i, arrayOfInt2);
/*     */     }
/* 195 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadDag()
/*     */   {
/* 202 */     this.net = BayesNet.loadSkipPb(this.path + ".bn");
/*     */   }
/*     */   
/*     */   void loadDsepset(BufferedReader paramBufferedReader)
/*     */   {
/* 207 */     int i = UTIL.loadInt(paramBufferedReader);
/* 208 */     this.sep = new Dsepset[i];
/*     */     
/* 210 */     for (int j = 0; j < i; j++) {
/* 211 */       UTIL.skipLine(paramBufferedReader, 2);
/* 212 */       int[] arrayOfInt = UTIL.loadIntList(paramBufferedReader);
/* 213 */       String[] arrayOfString = UTIL.loadStringListMLine(paramBufferedReader, arrayOfInt.length);
/* 214 */       this.sep[j] = new Dsepset(arrayOfString, arrayOfInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadDsepset(String paramString) {
/*     */     try {
/* 220 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.path + paramString));
/* 221 */       HelpPanel.addHelp("Loading d-sepset from " + this.path + paramString + ".");
/* 222 */       loadDsepset(localBufferedReader);
/* 223 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 225 */       HelpPanel.showError("Unable to load " + this.path + paramString + ".");
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadDsepset()
/*     */   {
/* 231 */     loadDsepset(".nbr");
/*     */   }
/*     */   
/*     */ 
/*     */   public void showAgGraph()
/*     */   {
/* 237 */     HelpPanel.addHelp("path: " + getPath());
/* 238 */     for (int i = 0; i < this.sep.length; i++) this.sep[i].show();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */