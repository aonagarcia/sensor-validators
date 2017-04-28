/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Msdag
/*     */   extends UndirectGraph
/*     */ {
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public Msdag() {}
/*     */   
/*     */   public Msdag(Msdag paramMsdag)
/*     */   {
/*  24 */     super(paramMsdag);
/*  25 */     setMsdag(paramMsdag);
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  30 */     this.nd = new Subdag[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  35 */     setDumbNet(paramInt);
/*  36 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new Subdag();
/*     */   }
/*     */   
/*     */   void setMsdag(Msdag paramMsdag)
/*     */   {
/*  41 */     int i = paramMsdag.getNodeCount();
/*  42 */     setDumbNetPlus(i);
/*  43 */     for (int j = 0; j < i; j++) this.nd[j] = new Subdag(paramMsdag.getSubdag(j));
/*     */   }
/*     */   
/*     */   Subdag getSubdag(int paramInt)
/*     */   {
/*  48 */     return new Subdag((Subdag)this.nd[paramInt]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Msdag addNode(Msdag paramMsdag, Point paramPoint)
/*     */   {
/*  56 */     Msdag localMsdag = new Msdag();
/*  57 */     int i = paramMsdag.getNodeCount();
/*  58 */     localMsdag.setDumbNet(i + 1);
/*     */     
/*  60 */     for (int j = 0; j < i; j++) localMsdag.nd[j] = paramMsdag.nd[j];
/*  61 */     localMsdag.nd[i] = new Subdag();
/*  62 */     localMsdag.nd[i].setPos(paramPoint);
/*  63 */     return localMsdag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Msdag delNode(Msdag paramMsdag, int paramInt)
/*     */   {
/*  74 */     int i = paramMsdag.nd[paramInt].nls == null ? 0 : paramMsdag.nd[paramInt].nls.length;
/*  75 */     for (int j = 0; j < i; j++) {
/*  76 */       k = paramMsdag.nd[paramInt].nls[j];
/*  77 */       paramMsdag.nd[k].delNeighbor(paramInt);
/*     */     }
/*     */     
/*  80 */     Msdag localMsdag = new Msdag();
/*  81 */     int k = paramMsdag.getNodeCount();
/*  82 */     localMsdag.setDumbNet(k - 1);
/*     */     
/*  84 */     for (int m = 0; m < k - 1; m++) {
/*  85 */       if (m < paramInt) localMsdag.nd[m] = paramMsdag.nd[m]; else
/*  86 */         localMsdag.nd[m] = paramMsdag.nd[(m + 1)];
/*     */     }
/*  88 */     localMsdag.modifyNodeIndex(paramInt + 1, k - 1, -1);
/*  89 */     return localMsdag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPath(int paramInt, String paramString)
/*     */   {
/*  96 */     ((Subdag)this.nd[paramInt]).setPath(paramString);
/*     */   }
/*     */   
/*     */   public String getPath(int paramInt)
/*     */   {
/* 101 */     return ((Subdag)this.nd[paramInt]).getPath();
/*     */   }
/*     */   
/*     */   public String getName(int paramInt)
/*     */   {
/* 106 */     return ((Subdag)this.nd[paramInt]).getName();
/*     */   }
/*     */   
/*     */   public boolean hasPath()
/*     */   {
/* 111 */     for (int i = 0; i < getNodeCount(); i++)
/* 112 */       if (((Subdag)this.nd[i]).getPath() == null) return false;
/* 113 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isSubdagConnected()
/*     */   {
/* 120 */     for (int i = 0; i < getNodeCount(); i++)
/* 121 */       if (!((Subdag)this.nd[i]).isConnected()) return false;
/* 122 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isSubdagAcyclic()
/*     */   {
/* 127 */     for (int i = 0; i < getNodeCount(); i++)
/* 128 */       if (!((Subdag)this.nd[i]).isAcyclic()) return false;
/* 129 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isDsepset()
/*     */   {
/* 135 */     if (!((Subdag)this.nd[0]).isIdenConnected(-1, 0, (Subdag[])this.nd)) {
/* 136 */       return false;
/*     */     }
/* 138 */     return ((Subdag)this.nd[0]).noSplitParent(-1, 0, (Subdag[])this.nd);
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
/*     */   private boolean isLocalCovering(int paramInt)
/*     */   {
/* 160 */     Dsepset[] arrayOfDsepset = new Dsepset[paramInt];
/* 161 */     int i = this.tor[paramInt];
/* 162 */     DirectGraph localDirectGraph = ((Subdag)this.nd[i]).getDag();
/*     */     
/* 164 */     for (int j = 0; j < paramInt; j++) {
/* 165 */       k = this.tor[j];
/* 166 */       localObject = ((Subdag)this.nd[k]).getDag();
/* 167 */       arrayOfDsepset[j] = Dsepset.getSepset(localDirectGraph, (DirectGraph)localObject);
/*     */     }
/*     */     
/* 170 */     j = this.nd[i].getPeer();
/*     */     
/* 172 */     int k = 0;
/* 173 */     while (j != this.tor[k]) k++;
/* 174 */     Object localObject = arrayOfDsepset[k].getLabel();
/*     */     
/* 176 */     for (int m = 0; m < paramInt; m++) {
/* 177 */       if ((m != k) && 
/* 178 */         (!MATH.isSubset(arrayOfDsepset[m].getLabel(), (String[])localObject))) return false;
/*     */     }
/* 180 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isLocalCovering() {
/* 184 */     HelpPanel.addHelp("Warning:\tisLocalCovering() should only be used in specifying a MSBN.");
/*     */     
/*     */ 
/* 187 */     setPeerOrder();
/* 188 */     int i = getNodeCount();
/* 189 */     int[] arrayOfInt = new int[i];
/* 190 */     for (int j = 0; j < i; j++) arrayOfInt[j] = j;
/* 191 */     HelpPanel.showList("Node index: \t", arrayOfInt);
/* 192 */     HelpPanel.showList("Node peer: \t", getPeer());
/* 193 */     HelpPanel.showList("Tree order: \t", getTreeOrder());
/*     */     
/* 195 */     for (j = 2; j < i; j++)
/* 196 */       if (!isLocalCovering(j)) return false;
/* 197 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isAcyclic()
/*     */   {
/* 203 */     int i = 0;
/*     */     
/*     */ 
/* 206 */     HelpPanel.addHelp("Preprocess subDAGs");
/* 207 */     ((Subdag)this.nd[i]).preProcess(-1, i, (Subdag[])this.nd);
/*     */     
/*     */ 
/* 210 */     while (((Subdag)this.nd[i]).markNode(-1, i, (Subdag[])this.nd)) {}
/*     */     
/*     */ 
/* 213 */     HelpPanel.addHelp("Check mark");
/* 214 */     return ((Subdag)this.nd[i]).markedAll(-1, i, (Subdag[])this.nd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMoralGraph()
/*     */   {
/* 224 */     HelpPanel.addHelp("Local moralizing\tsubDAGs\t");
/* 225 */     for (int i = 0; i < getNodeCount(); i++) {
/* 226 */       HelpPanel.appendHelp(i + " ");
/* 227 */       ((Subdag)this.nd[i]).localMoralize();
/*     */     }
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 233 */       i = 0;
/* 234 */       HelpPanel.addHelp("Propagate moral links at subDAGs ");
/* 235 */       for (int j = 0; j < getNodeCount(); j++) {
/* 236 */         i = (i != 0) || (((Subdag)this.nd[j]).shipMoralLink(j, (Subdag[])this.nd)) ? 1 : 0;
/* 237 */         HelpPanel.appendHelp(j + " ");
/*     */       }
/* 239 */     } while (i != 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setChordalGraph()
/*     */   {
/* 246 */     HelpPanel.addHelp("Forward triangulating subnets ");
/* 247 */     ((Subdag)this.nd[0]).forwardTriangulate(-1, 0, (Subdag[])this.nd, (String[][])null);
/*     */     
/* 249 */     HelpPanel.addHelp("Forward propagating d-links at subnets ");
/* 250 */     ((Subdag)this.nd[0]).distributeDlink(-1, 0, (Subdag[])this.nd, (String[][])null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void checkEliminationOdr()
/*     */   {
/* 258 */     HelpPanel.addHelp("Check elimination compatibility at subnets.");
/* 259 */     if (((Subdag)this.nd[0]).checkEliminationOdr(-1, 0, (Subdag[])this.nd))
/* 260 */       HelpPanel.addHelp("Elimination orders are\tcompatible."); else {
/* 261 */       HelpPanel.showError("Incompatible elimination orders found!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setJoinTree(Rectangle paramRectangle) {
/* 266 */     HelpPanel.addHelp("Construct JTs for subDAGs ");
/* 267 */     for (int i = 0; i < getNodeCount(); i++) {
/* 268 */       HelpPanel.appendHelp(i + " ");
/* 269 */       ((Subdag)this.nd[i]).setJoinTree(paramRectangle);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setHostTree(Rectangle paramRectangle)
/*     */   {
/* 275 */     HelpPanel.addHelp("Construct host trees:");
/* 276 */     for (int i = 0; i < getNodeCount(); i++) {
/* 277 */       HelpPanel.addHelp("SubDAG\t" + i + " vs subDAG ");
/* 278 */       ((Subdag)this.nd[i]).setHostTree(paramRectangle);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLinkageTree(Rectangle paramRectangle)
/*     */   {
/* 284 */     HelpPanel.addHelp("Construct linkage trees:");
/* 285 */     for (int i = 0; i < getNodeCount(); i++) {
/* 286 */       HelpPanel.addHelp("Sdag " + i + "\tvs");
/* 287 */       ((Subdag)this.nd[i]).setLinkageTree(paramRectangle);
/*     */     }
/*     */   }
/*     */   
/*     */   public void assignSepnode()
/*     */   {
/* 293 */     HelpPanel.addHelp("Assigning sepnodes to subDAGs.");
/* 294 */     ((Subdag)this.nd[0]).assignSepnode(-1, 0, (Subdag[])this.nd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getNbrName(int paramInt1, int paramInt2)
/*     */   {
/* 301 */     return ((Subdag)this.nd[paramInt1]).getNbrName(paramInt2);
/*     */   }
/*     */   
/*     */   public void setNodeNbr()
/*     */   {
/* 306 */     for (int i = 0; i < getNodeCount(); i++) {
/* 307 */       int j = getNeighborCount(i);
/* 308 */       String[] arrayOfString = new String[j];
/* 309 */       for (int k = 0; k < j; k++) {
/* 310 */         int m = getNeighbor(i, k);
/* 311 */         arrayOfString[k] = new String(((Subdag)this.nd[m]).getPath());
/*     */       }
/* 313 */       ((Subdag)this.nd[i]).setNbrPath(arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSepset()
/*     */   {
/* 323 */     HelpPanel.addHelp("Warning:\tsetSepset() should only\tbe used\tin specifying a\tMSBN.");
/*     */     
/* 325 */     for (int i = 0; i < getNodeCount(); i++) setSepset(i);
/*     */   }
/*     */   
/*     */   private void setSepset(int paramInt) {
/* 329 */     DirectGraph localDirectGraph1 = ((Subdag)this.nd[paramInt]).getDag();
/*     */     
/* 331 */     int i = ((Subdag)this.nd[paramInt]).getNeighborCount();
/* 332 */     Dsepset[] arrayOfDsepset = new Dsepset[i];
/* 333 */     for (int j = 0; j < i; j++) {
/* 334 */       int k = this.nd[paramInt].getNeighbor(j);
/* 335 */       DirectGraph localDirectGraph2 = ((Subdag)this.nd[k]).getDag();
/* 336 */       arrayOfDsepset[j] = Dsepset.getSepset(localDirectGraph1, localDirectGraph2);
/*     */     }
/* 338 */     ((Subdag)this.nd[paramInt]).setSepset(arrayOfDsepset);
/*     */   }
/*     */   
/*     */   public String[] getSepset(int paramInt1, int paramInt2)
/*     */   {
/* 343 */     int i = getNeighbor(paramInt1, paramInt2);
/* 344 */     return ((Subdag)this.nd[paramInt1]).getSepset(i).getLabel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadNode(BufferedReader paramBufferedReader, String paramString)
/*     */   {
/* 352 */     int i = this.nd.length;
/*     */     try {
/* 354 */       for (int j = 0; j < i; j++) {
/* 355 */         paramBufferedReader.readLine();
/* 356 */         this.nd[j] = Subdag.load(paramBufferedReader, paramString);
/* 357 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 361 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadNodeDag()
/*     */   {
/* 367 */     for (int i = 0; i < this.nd.length; i++) loadNodeDag(i);
/*     */   }
/*     */   
/*     */   public void loadNodeDag(int paramInt) {
/* 371 */     ((Subdag)this.nd[paramInt]).loadDag();
/*     */   }
/*     */   
/*     */ 
/*     */   public static Msdag loadHypertree(BufferedReader paramBufferedReader, String paramString)
/*     */   {
/* 377 */     Msdag localMsdag = new Msdag();
/* 378 */     int i = UTIL.loadInt(paramBufferedReader);
/* 379 */     localMsdag.setDumbNet(i);
/* 380 */     localMsdag.loadNode(paramBufferedReader, paramString);
/* 381 */     localMsdag.setNodeNbr();
/* 382 */     return localMsdag;
/*     */   }
/*     */   
/*     */   public void loadDsepset()
/*     */   {
/* 387 */     for (int i = 0; i < this.nd.length; i++) ((Subdag)this.nd[i]).loadDsepset();
/*     */   }
/*     */   
/*     */   void loadDsepset(int paramInt) {
/* 391 */     ((Subdag)this.nd[paramInt]).loadDsepset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void collectObserved(int paramInt)
/*     */   {
/* 398 */     ((Subdag)this.nd[paramInt]).collectObserved(-1, paramInt, (Subdag[])this.nd);
/*     */   }
/*     */   
/*     */   void distributeObserved(int paramInt)
/*     */   {
/* 403 */     ((Subdag)this.nd[paramInt]).distributeObserved(null, -1, paramInt, (Subdag[])this.nd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 410 */     paramPrintWriter.println(this.nd.length + "  #_of_subnets");
/* 411 */     for (int i = 0; i < this.nd.length; i++) {
/* 412 */       paramPrintWriter.println();
/* 413 */       ((Subdag)this.nd[i]).save(paramPrintWriter, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveNbrDsep(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 419 */     int i = this.nd[paramInt].getNeighborCount();
/*     */     
/* 421 */     paramPrintWriter.println(i + "  #_of_neighbors");
/* 422 */     for (int j = 0; j < i; j++) {
/* 423 */       paramPrintWriter.println();
/* 424 */       int k = this.nd[paramInt].getNeighbor(j);
/* 425 */       paramPrintWriter.println(((Subdag)this.nd[k]).getPath());
/*     */       
/* 427 */       ((Subdag)this.nd[paramInt]).saveSepset(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChordalGraph getSkeleton(int paramInt)
/*     */   {
/* 435 */     return ((Subdag)this.nd[paramInt]).getSkeleton();
/*     */   }
/*     */   
/*     */   public HyperGraph getJoinTreeTrunk(int paramInt)
/*     */   {
/* 440 */     return ((Subdag)this.nd[paramInt]).getJoinTreeTrunk();
/*     */   }
/*     */   
/*     */   public HyperGrafM getHostTreeTrunk(int paramInt1, int paramInt2)
/*     */   {
/* 445 */     return ((Subdag)this.nd[paramInt1]).getHostTreeTrunk(paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void show()
/*     */   {
/* 452 */     for (int i = 0; i < getNodeCount(); i++) ((Subdag)this.nd[i]).showSubdag();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Msdag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */