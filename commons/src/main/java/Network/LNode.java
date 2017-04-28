/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
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
/*     */ public class LNode
/*     */   extends HNode
/*     */ {
/*     */   static final int SYSTEM = -1;
/*  36 */   int[] sz = null;
/*  37 */   LazyBelSet belset = new LazyBelSet();
/*  38 */   LazyBelSet spinls = new LazyBelSet();
/*  39 */   LazyBelSet spoutls = new LazyBelSet();
/*     */   
/*     */ 
/*     */ 
/*     */   LNode() {}
/*     */   
/*     */ 
/*     */ 
/*  47 */   LNode(HNode paramHNode) { super(paramHNode); }
/*     */   
/*     */   LNode(LNode paramLNode) {
/*  50 */     super(paramLNode);
/*  51 */     this.sz = paramLNode.getStateCount();
/*  52 */     this.belset = new LazyBelSet(paramLNode.getPoten());
/*  53 */     this.spinls = new LazyBelSet(paramLNode.getBufferPoten(0));
/*  54 */     this.spoutls = new LazyBelSet(paramLNode.getBufferPoten(1));
/*     */   }
/*     */   
/*     */   void reinit() {
/*  58 */     this.sz = null;
/*  59 */     this.belset = new LazyBelSet();
/*  60 */     this.spinls = new LazyBelSet();
/*  61 */     this.spoutls = new LazyBelSet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setStateCount(int[] paramArrayOfInt)
/*     */   {
/*  68 */     this.sz = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getStateCount()
/*     */   {
/*  73 */     return UTIL.getDuplicate(this.sz);
/*     */   }
/*     */   
/*     */   int[] getStateCount(int[] paramArrayOfInt)
/*     */   {
/*  78 */     if (paramArrayOfInt == null) return null;
/*  79 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/*  80 */     for (int i = 0; i < paramArrayOfInt.length; i++) arrayOfInt[i] = getStateCount(paramArrayOfInt[i]);
/*  81 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   int getStateCount(int paramInt)
/*     */   {
/*  86 */     if ((this.sz == null) || (this.clq == null)) return -1;
/*  87 */     for (int i = 0; i < getCqSize(); i++) if (this.clq[i] == paramInt) return this.sz[i];
/*  88 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[] getPotenDomain(int paramInt)
/*     */   {
/*  95 */     return this.belset.getDomain(paramInt);
/*     */   }
/*     */   
/*     */   int[] getPotenHead(int paramInt)
/*     */   {
/* 100 */     return this.belset.getHead(paramInt);
/*     */   }
/*     */   
/*     */   float[] getPoten(int paramInt)
/*     */   {
/* 105 */     return this.belset.getBelief(paramInt);
/*     */   }
/*     */   
/*     */   LazyBelSet getPoten()
/*     */   {
/* 110 */     return new LazyBelSet(this.belset);
/*     */   }
/*     */   
/*     */   int getPotenCount()
/*     */   {
/* 115 */     return this.belset.getPotenCount();
/*     */   }
/*     */   
/*     */   int[] getPotenSize()
/*     */   {
/* 120 */     return this.belset.getPotenSize();
/*     */   }
/*     */   
/*     */   void setDumbPoten()
/*     */   {
/* 125 */     this.belset.btlist = null;
/*     */   }
/*     */   
/*     */ 
/*     */   void setPoten(LazyBelSet paramLazyBelSet)
/*     */   {
/* 131 */     this.belset = paramLazyBelSet;
/*     */   }
/*     */   
/*     */   void setPoten(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 136 */     this.belset.btlist[paramInt].belief = paramArrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void addPoten(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 143 */     int[] arrayOfInt = { paramArrayOfInt[(paramArrayOfInt.length - 1)] };
/* 144 */     LazyBelief localLazyBelief = new LazyBelief(paramArrayOfInt, arrayOfInt, paramArrayOfFloat);
/* 145 */     this.belset.addPotential(localLazyBelief);
/*     */   }
/*     */   
/*     */ 
/*     */   void addPotNoHead(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 151 */     LazyBelief localLazyBelief = new LazyBelief(paramArrayOfInt, null, paramArrayOfFloat);
/* 152 */     this.belset.addPotential(localLazyBelief);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   LazyBelSet getBufferPoten(int paramInt)
/*     */   {
/* 161 */     if (paramInt == 0) return this.spinls;
/* 162 */     return this.spoutls;
/*     */   }
/*     */   
/*     */   LazyBelSet getInBufferPoten(LNode[] paramArrayOfLNode)
/*     */   {
/* 167 */     LazyBelSet localLazyBelSet = new LazyBelSet();
/* 168 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 169 */       int j = getNeighbor(i);
/* 170 */       if (isPeer(j)) localLazyBelSet.unionPotential(this.spinls); else
/* 171 */         localLazyBelSet.unionPotential(paramArrayOfLNode[j].spoutls);
/*     */     }
/* 173 */     return localLazyBelSet;
/*     */   }
/*     */   
/*     */   void setBufferPoten()
/*     */   {
/* 178 */     if (!hasPeer()) return;
/* 179 */     this.spinls.btlist = null;
/* 180 */     this.spoutls.btlist = null;
/*     */   }
/*     */   
/*     */   void setBufferPoten(LazyBelSet paramLazyBelSet1, LazyBelSet paramLazyBelSet2) {
/* 184 */     this.spinls = paramLazyBelSet1;
/* 185 */     this.spoutls = paramLazyBelSet2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setMargin(float[] paramArrayOfFloat, int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 194 */     int i = 0;
/* 195 */     while (paramArrayOfInt[i] != paramInt) i++;
/* 196 */     int[] arrayOfInt1 = { i };
/*     */     
/* 198 */     int[] arrayOfInt2 = new int[paramArrayOfInt.length];
/* 199 */     for (int j = 0; j < paramArrayOfInt.length; j++) {
/* 200 */       for (int k = 0; k < this.clq.length; k++) {
/* 201 */         if (paramArrayOfInt[j] == this.clq[k]) {
/* 202 */           arrayOfInt2[j] = this.sz[k]; break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 207 */     float[] arrayOfFloat = MATH.margin(paramArrayOfFloat, arrayOfInt2, arrayOfInt1);
/* 208 */     return MATH.normalize(arrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setProdPotenPlusMsg(LNode[] paramArrayOfLNode)
/*     */   {
/* 216 */     LazyBelSet localLazyBelSet = new LazyBelSet(this.belset);
/* 217 */     localLazyBelSet.unionPotential(getInBufferPoten(paramArrayOfLNode));
/* 218 */     if (localLazyBelSet.btlist == null) { return null;
/*     */     }
/*     */     
/* 221 */     LazyBelief localLazyBelief = new LazyBelief(localLazyBelSet.btlist[0]);
/* 222 */     for (int i = 1; i < localLazyBelSet.btlist.length; i++)
/* 223 */       localLazyBelief.lazyProduct(localLazyBelSet.btlist[i], this.clq, getStateCount());
/* 224 */     return localLazyBelief.belief;
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
/*     */   void recvBufPoten(int paramInt1, int paramInt2, LNode[] paramArrayOfLNode)
/*     */   {
/* 238 */     LNode localLNode = paramArrayOfLNode[paramInt2];
/* 239 */     LazyBelSet localLazyBelSet = new LazyBelSet(localLNode.belset);
/* 240 */     for (int i = 0; i < localLNode.getNeighborCount(); i++) {
/* 241 */       int j = localLNode.getNeighbor(i);
/* 242 */       if (j != paramInt1) {
/* 243 */         if (localLNode.isPeer(j))
/* 244 */           localLazyBelSet.unionPotential(new LazyBelSet(localLNode.spinls)); else
/* 245 */           localLazyBelSet.unionPotential(new LazyBelSet(paramArrayOfLNode[j].spoutls));
/*     */       }
/*     */     }
/* 248 */     i = 0;
/* 249 */     if (isPeer(paramInt2)) {
/* 250 */       while (!isNeighbor(i, paramInt2)) i++;
/* 251 */       localLazyBelSet.setMargin(localLNode, getSepset(i));
/* 252 */       this.spinls = localLazyBelSet;
/*     */     }
/*     */     else {
/* 255 */       while (!localLNode.isNeighbor(i, paramInt1)) i++;
/* 256 */       localLazyBelSet.setMargin(localLNode, localLNode.getSepset(i));
/* 257 */       localLNode.spoutls = localLazyBelSet;
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
/*     */   void collectPotential(int paramInt1, int paramInt2, LNode[] paramArrayOfLNode)
/*     */   {
/* 270 */     if (paramArrayOfLNode.length == 1) return;
/* 271 */     if ((getNeighborCount() == 1) && (paramInt1 == getNeighbor(0)))
/* 272 */       return;
/* 273 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 274 */       int j = getNeighbor(i);
/* 275 */       if (j != paramInt1) {
/* 276 */         paramArrayOfLNode[j].collectPotential(paramInt2, j, paramArrayOfLNode);
/* 277 */         recvBufPoten(paramInt2, j, paramArrayOfLNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributePotential(int paramInt1, int paramInt2, LNode[] paramArrayOfLNode)
/*     */   {
/* 289 */     if (paramArrayOfLNode.length == 1) return;
/* 290 */     if (paramInt1 != -1) recvBufPoten(paramInt2, paramInt1, paramArrayOfLNode);
/* 291 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 292 */       int j = getNeighbor(i);
/* 293 */       if (j != paramInt1) {
/* 294 */         paramArrayOfLNode[j].distributePotential(paramInt2, j, paramArrayOfLNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void loadStateCount(BufferedReader paramBufferedReader)
/*     */   {
/* 302 */     setStateCount(UTIL.loadIntList(paramBufferedReader, getCqSize()));
/*     */   }
/*     */   
/*     */   void loadBelief(BufferedReader paramBufferedReader)
/*     */   {
/* 307 */     int i = UTIL.loadInt(paramBufferedReader);
/* 308 */     for (int j = 0; j < i; j++) {
/* 309 */       LazyBelief localLazyBelief = LazyBelief.load(paramBufferedReader);
/* 310 */       this.belset.addPotential(localLazyBelief);
/*     */     }
/*     */   }
/*     */   
/*     */   void load(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 316 */     loadCqMember(paramBufferedReader);
/* 317 */     loadStateCount(paramBufferedReader);
/* 318 */     loadHyperLink(paramBufferedReader);
/* 319 */     loadPeer(paramBufferedReader);
/* 320 */     loadPos(paramBufferedReader);
/* 321 */     loadBelief(paramBufferedReader);
/* 322 */     setLabel(paramInt);
/*     */   }
/*     */   
/*     */   void loadLkgWithPoten(BufferedReader paramBufferedReader)
/*     */   {
/* 327 */     loadLabel(paramBufferedReader);
/* 328 */     loadPeer(paramBufferedReader);
/* 329 */     loadPos(paramBufferedReader);
/* 330 */     loadCqMember(paramBufferedReader);
/* 331 */     loadStateCount(paramBufferedReader);
/* 332 */     loadHyperLink(paramBufferedReader);
/* 333 */     loadBelief(paramBufferedReader);
/*     */   }
/*     */   
/*     */   void saveStateCount(PrintWriter paramPrintWriter) {
/* 337 */     paramPrintWriter.print("\t  ");
/* 338 */     for (int i = 0; i < this.sz.length; i++) paramPrintWriter.print(this.sz[i] + " ");
/* 339 */     paramPrintWriter.println(" dimensions");
/*     */   }
/*     */   
/*     */ 
/*     */   void saveBelief(PrintWriter paramPrintWriter)
/*     */   {
/* 345 */     int i = getPotenCount();
/* 346 */     paramPrintWriter.println(i + "  #_of_potentials");
/* 347 */     for (int j = 0; j < i; j++) this.belset.btlist[j].save(paramPrintWriter);
/*     */   }
/*     */   
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 352 */     saveCqMember(paramPrintWriter, paramInt);
/* 353 */     saveStateCount(paramPrintWriter);
/* 354 */     saveHyperLink(paramPrintWriter);
/* 355 */     savePeer(paramPrintWriter);
/* 356 */     savePos(paramPrintWriter);
/* 357 */     saveBelief(paramPrintWriter);
/*     */   }
/*     */   
/*     */   void saveLkgWithPoten(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 362 */     saveLabel(paramPrintWriter);
/* 363 */     savePeer(paramPrintWriter);
/* 364 */     savePos(paramPrintWriter);
/* 365 */     saveCqMember(paramPrintWriter, paramInt);
/* 366 */     saveStateCount(paramPrintWriter);
/* 367 */     saveHyperLink(paramPrintWriter);
/* 368 */     saveBelief(paramPrintWriter);
/*     */   }
/*     */   
/*     */ 
/*     */   void showLNode()
/*     */   {
/* 374 */     System.out.println("  [LNode]");
/* 375 */     showHNode();
/* 376 */     UTIL.showList("sz=", this.sz);
/* 377 */     if (this.belset == null) { System.out.println("belnet=null");
/* 378 */     } else { System.out.println("\tbelset:");this.belset.showLazyBelSet(); }
/* 379 */     if (this.spinls == null) { System.out.println("spinls=null");
/* 380 */     } else { System.out.println("\tspinls:");this.spinls.showLazyBelSet(); }
/* 381 */     if (this.spoutls == null) { System.out.println("spoutls=null");
/* 382 */     } else { System.out.println("\tspoutls:");this.spoutls.showLazyBelSet();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */