/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WNode
/*     */   extends LNode
/*     */ {
/*  12 */   LazyBelSet lkginls = new LazyBelSet();
/*     */   
/*     */ 
/*     */ 
/*  16 */   WNode() { this.lkginls = new LazyBelSet(); }
/*     */   
/*     */   WNode(HNode paramHNode) {
/*  19 */     super(paramHNode);
/*  20 */     this.lkginls = new LazyBelSet();
/*     */   }
/*     */   
/*  23 */   WNode(LNode paramLNode) { super(paramLNode);
/*  24 */     this.lkginls = new LazyBelSet();
/*     */   }
/*     */   
/*  27 */   WNode(WNode paramWNode) { super(paramWNode);
/*  28 */     this.lkginls = new LazyBelSet(paramWNode.getLkgBufPoten());
/*     */   }
/*     */   
/*     */   void reinit1() {
/*  32 */     reinit1();
/*  33 */     this.lkginls = new LazyBelSet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getLkgBufPotenCount()
/*     */   {
/*  40 */     return this.lkginls.getPotenCount();
/*     */   }
/*     */   
/*     */ 
/*     */   LazyBelSet getLkgBufPoten()
/*     */   {
/*  46 */     return this.lkginls;
/*     */   }
/*     */   
/*     */ 
/*     */   void setLkgBufPoten()
/*     */   {
/*  52 */     this.lkginls.btlist = null;
/*     */   }
/*     */   
/*     */   void setLkgBufPoten(LazyBelSet paramLazyBelSet)
/*     */   {
/*  57 */     this.lkginls = paramLazyBelSet;
/*     */   }
/*     */   
/*     */   void addLkgBufPoten(LazyBelief paramLazyBelief)
/*     */   {
/*  62 */     this.lkginls.addPotential(paramLazyBelief);
/*     */   }
/*     */   
/*     */   void addLkgBufPoten(LazyBelSet paramLazyBelSet)
/*     */   {
/*  67 */     this.lkginls.unionPotential(paramLazyBelSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   LazyBelSet getPotenPlusAllMsg(WNode[] paramArrayOfWNode)
/*     */   {
/*  75 */     LazyBelSet localLazyBelSet = new LazyBelSet(this.belset);
/*  76 */     localLazyBelSet.unionPotential(getInBufferPoten(paramArrayOfWNode));
/*  77 */     localLazyBelSet.unionPotential(this.lkginls);
/*  78 */     return localLazyBelSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setProdPotenPlusAllMsg(WNode[] paramArrayOfWNode)
/*     */   {
/*  86 */     LazyBelSet localLazyBelSet = getPotenPlusAllMsg(paramArrayOfWNode);
/*  87 */     if (localLazyBelSet.btlist == null) { return null;
/*     */     }
/*     */     
/*  90 */     LazyBelief localLazyBelief = new LazyBelief(localLazyBelSet.btlist[0]);
/*  91 */     for (int i = 1; i < localLazyBelSet.btlist.length; i++)
/*  92 */       localLazyBelief.lazyProduct(localLazyBelSet.btlist[i], this.clq, getStateCount());
/*  93 */     return localLazyBelief.belief;
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
/*     */   void recvBufPoten(int paramInt1, int paramInt2, WNode[] paramArrayOfWNode)
/*     */   {
/* 109 */     WNode localWNode = paramArrayOfWNode[paramInt2];
/* 110 */     LazyBelSet localLazyBelSet = new LazyBelSet(localWNode.belset);
/* 111 */     for (int i = 0; i < localWNode.getNeighborCount(); i++) {
/* 112 */       int j = localWNode.getNeighbor(i);
/* 113 */       if (j != paramInt1)
/* 114 */         if (localWNode.isPeer(j))
/* 115 */           localLazyBelSet.unionPotential(new LazyBelSet(localWNode.spinls)); else
/* 116 */           localLazyBelSet.unionPotential(new LazyBelSet(paramArrayOfWNode[j].spoutls));
/*     */     }
/* 118 */     localLazyBelSet.unionPotential(localWNode.lkginls);
/*     */     
/* 120 */     i = 0;
/* 121 */     if (isPeer(paramInt2)) {
/* 122 */       while (!isNeighbor(i, paramInt2)) i++;
/* 123 */       localLazyBelSet.setMargin(localWNode, getSepset(i));
/* 124 */       this.spinls = localLazyBelSet;
/*     */     }
/*     */     else {
/* 127 */       while (!localWNode.isNeighbor(i, paramInt1)) i++;
/* 128 */       localLazyBelSet.setMargin(localWNode, localWNode.getSepset(i));
/* 129 */       localWNode.spoutls = localLazyBelSet;
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
/*     */   void collectPotential1(int paramInt1, int paramInt2, WNode[] paramArrayOfWNode)
/*     */   {
/* 143 */     if (paramArrayOfWNode.length == 1) return;
/* 144 */     if ((getNeighborCount() == 1) && (paramInt1 == getNeighbor(0)))
/* 145 */       return;
/* 146 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 147 */       int j = getNeighbor(i);
/* 148 */       if (j != paramInt1) {
/* 149 */         paramArrayOfWNode[j].collectPotential1(paramInt2, j, paramArrayOfWNode);
/* 150 */         recvBufPoten(paramInt2, j, paramArrayOfWNode);
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
/*     */   void distributePotential1(int paramInt1, int paramInt2, WNode[] paramArrayOfWNode)
/*     */   {
/* 163 */     if (paramArrayOfWNode.length == 1) return;
/* 164 */     if (paramInt1 != -1) recvBufPoten(paramInt2, paramInt1, paramArrayOfWNode);
/* 165 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 166 */       int j = getNeighbor(i);
/* 167 */       if (j != paramInt1) {
/* 168 */         paramArrayOfWNode[j].distributePotential1(paramInt2, j, paramArrayOfWNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void loadCptRef(BufferedReader paramBufferedReader, BayesNet paramBayesNet)
/*     */   {
/* 176 */     int i = UTIL.loadInt(paramBufferedReader);
/* 177 */     for (int j = 0; j < i; j++) {
/* 178 */       int k = UTIL.loadInt(paramBufferedReader);
/* 179 */       int[] arrayOfInt = paramBayesNet.getFamily(k);
/* 180 */       float[] arrayOfFloat = paramBayesNet.refCondProb(k);
/* 181 */       addPoten(arrayOfInt, arrayOfFloat);
/*     */     }
/*     */   }
/*     */   
/*     */   void loadLkgBufPoten(BufferedReader paramBufferedReader)
/*     */   {
/* 187 */     int i = UTIL.loadInt(paramBufferedReader);
/* 188 */     for (int j = 0; j < i; j++) {
/* 189 */       LazyBelief localLazyBelief = LazyBelief.load(paramBufferedReader);
/* 190 */       this.lkginls.addPotential(localLazyBelief);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void loadInferJTNode(BufferedReader paramBufferedReader, int paramInt, BayesNet paramBayesNet)
/*     */   {
/* 197 */     loadCqMember(paramBufferedReader);
/* 198 */     loadStateCount(paramBufferedReader);
/* 199 */     loadHyperLink(paramBufferedReader);
/* 200 */     loadPeer(paramBufferedReader);
/* 201 */     loadPos(paramBufferedReader);
/* 202 */     loadCptRef(paramBufferedReader, paramBayesNet);
/* 203 */     loadLkgBufPoten(paramBufferedReader);
/* 204 */     setLabel(paramInt);
/*     */   }
/*     */   
/*     */   void loadMsgJTNode(BufferedReader paramBufferedReader, int paramInt, BayesNet paramBayesNet)
/*     */   {
/* 209 */     loadLabel(paramBufferedReader);
/* 210 */     loadPeer(paramBufferedReader);
/* 211 */     loadPos(paramBufferedReader);
/* 212 */     loadCqMember(paramBufferedReader);
/* 213 */     loadStateCount(paramBufferedReader);
/* 214 */     loadHyperLink(paramBufferedReader);
/* 215 */     loadCptRef(paramBufferedReader, paramBayesNet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void saveCptRef(PrintWriter paramPrintWriter)
/*     */   {
/* 223 */     int i = this.belset.getPotenCount();
/* 224 */     paramPrintWriter.println(i + "  #cpt_refs");
/* 225 */     for (int j = 0; j < i; j++) {
/* 226 */       int[] arrayOfInt1 = this.belset.getDomain(j);
/* 227 */       int[] arrayOfInt2 = this.belset.getHead(j);
/* 228 */       paramPrintWriter.println(arrayOfInt2[0] + "  head   domain=" + UTIL.listToStr("", arrayOfInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   void saveLkgBufPoten(PrintWriter paramPrintWriter)
/*     */   {
/* 234 */     int i = getLkgBufPotenCount();
/* 235 */     paramPrintWriter.println(i + "  #lkg_buf_potens");
/* 236 */     for (int j = 0; j < i; j++) this.lkginls.btlist[j].save(paramPrintWriter);
/*     */   }
/*     */   
/*     */   void saveInferJTNode(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 241 */     saveCqMember(paramPrintWriter, paramInt);
/* 242 */     saveStateCount(paramPrintWriter);
/* 243 */     saveHyperLink(paramPrintWriter);
/* 244 */     savePeer(paramPrintWriter);
/* 245 */     savePos(paramPrintWriter);
/* 246 */     saveCptRef(paramPrintWriter);
/* 247 */     saveLkgBufPoten(paramPrintWriter);
/*     */   }
/*     */   
/*     */   void saveMsgJTNode(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 252 */     saveLabel(paramPrintWriter);
/* 253 */     savePeer(paramPrintWriter);
/* 254 */     savePos(paramPrintWriter);
/* 255 */     saveCqMember(paramPrintWriter, paramInt);
/* 256 */     saveStateCount(paramPrintWriter);
/* 257 */     saveHyperLink(paramPrintWriter);
/* 258 */     saveCptRef(paramPrintWriter);
/*     */   }
/*     */   
/*     */ 
/*     */   void showWNode()
/*     */   {
/* 264 */     System.out.println("  [WNode]");
/* 265 */     showLNode();
/* 266 */     if (this.lkginls == null) { System.out.println("lkginls=null");
/* 267 */     } else { System.out.println("\tlkginls:");this.lkginls.showLazyBelSet();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/WNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */