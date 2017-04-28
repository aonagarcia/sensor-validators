/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class CNode
/*     */   extends HNode
/*     */ {
/*     */   static final int SYSTEM = -1;
/*  10 */   int[] sz = null;
/*  11 */   float[] bfls = null;
/*  12 */   float[] spls = null;
/*     */   
/*     */ 
/*     */ 
/*     */   CNode() {}
/*     */   
/*     */ 
/*     */ 
/*  20 */   CNode(HNode paramHNode) { super(paramHNode); }
/*     */   
/*     */   CNode(CNode paramCNode) {
/*  23 */     super(paramCNode);
/*  24 */     this.sz = paramCNode.getStateCount();
/*  25 */     this.bfls = paramCNode.getBelief();
/*  26 */     this.spls = paramCNode.getSepsetBelief();
/*     */   }
/*     */   
/*     */   void reinit() {
/*  30 */     this.sz = null;
/*  31 */     this.bfls = null;
/*  32 */     this.spls = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setStateCount(int[] paramArrayOfInt)
/*     */   {
/*  39 */     this.sz = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getStateCount()
/*     */   {
/*  44 */     return UTIL.getDuplicate(this.sz);
/*     */   }
/*     */   
/*     */   int[] getStateCount(int[] paramArrayOfInt)
/*     */   {
/*  49 */     if (paramArrayOfInt == null) return null;
/*  50 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/*  51 */     for (int i = 0; i < paramArrayOfInt.length; i++) arrayOfInt[i] = getStateCount(paramArrayOfInt[i]);
/*  52 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   int getStateCount(int paramInt)
/*     */   {
/*  57 */     if ((this.sz == null) || (this.clq == null)) return -1;
/*  58 */     for (int i = 0; i < getCqSize(); i++) if (this.clq[i] == paramInt) return this.sz[i];
/*  59 */     return -1;
/*     */   }
/*     */   
/*     */   int getSepsetSpaceSize()
/*     */   {
/*  64 */     if (!hasPeer()) { return 0;
/*     */     }
/*  66 */     int[] arrayOfInt = getSepsetToPeer();
/*  67 */     int i = 1;
/*  68 */     for (int j = 0; j < arrayOfInt.length; j++) {
/*  69 */       for (int k = 0; k < this.clq.length; k++) {
/*  70 */         if (arrayOfInt[j] == this.clq[k]) {
/*  71 */           i *= this.sz[k]; break;
/*     */         }
/*     */       }
/*     */     }
/*  75 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadStateCount(BufferedReader paramBufferedReader)
/*     */   {
/*  82 */     setStateCount(UTIL.loadIntList(paramBufferedReader, getCqSize()));
/*     */   }
/*     */   
/*     */   void loadBelief(BufferedReader paramBufferedReader)
/*     */   {
/*  87 */     int i = UTIL.loadInt(paramBufferedReader);
/*  88 */     setBelief(UTIL.loadRealList(paramBufferedReader, i));
/*     */   }
/*     */   
/*     */   static CNode load(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/*  93 */     CNode localCNode = new CNode();
/*  94 */     localCNode.loadCqMember(paramBufferedReader);
/*  95 */     localCNode.loadStateCount(paramBufferedReader);
/*  96 */     localCNode.loadHyperLink(paramBufferedReader);
/*  97 */     localCNode.loadPeer(paramBufferedReader);
/*  98 */     localCNode.loadPos(paramBufferedReader);
/*  99 */     localCNode.loadBelief(paramBufferedReader);
/*     */     
/* 101 */     localCNode.setLabel(paramInt);
/* 102 */     return localCNode;
/*     */   }
/*     */   
/*     */   void saveStateCount(PrintWriter paramPrintWriter) {
/* 106 */     paramPrintWriter.print("\t  ");
/* 107 */     for (int i = 0; i < this.sz.length; i++) paramPrintWriter.print(this.sz[i] + " ");
/* 108 */     paramPrintWriter.println(" dimensions");
/*     */   }
/*     */   
/*     */ 
/*     */   void saveBelief(PrintWriter paramPrintWriter)
/*     */   {
/* 114 */     paramPrintWriter.println(this.bfls.length + "\t #_of_belief");
/*     */     
/* 116 */     UTIL.saveRealList(paramPrintWriter, this.bfls);
/*     */   }
/*     */   
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 121 */     saveCqMember(paramPrintWriter, paramInt);
/* 122 */     saveStateCount(paramPrintWriter);
/* 123 */     saveHyperLink(paramPrintWriter);
/* 124 */     savePeer(paramPrintWriter);
/* 125 */     savePos(paramPrintWriter);
/* 126 */     saveBelief(paramPrintWriter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   float[] getBelief()
/*     */   {
/* 133 */     return UTIL.getDuplicate(this.bfls);
/*     */   }
/*     */   
/*     */ 
/*     */   int getPotSize()
/*     */   {
/* 139 */     int i = 1;
/* 140 */     for (int j = 0; j < this.sz.length; j++) i *= this.sz[j];
/* 141 */     return i;
/*     */   }
/*     */   
/*     */   float[] getSepsetBelief()
/*     */   {
/* 146 */     return UTIL.getDuplicate(this.spls);
/*     */   }
/*     */   
/*     */   void setDumbBelief()
/*     */   {
/* 151 */     int i = 1;
/* 152 */     for (int j = 0; j < this.sz.length; j++) i *= this.sz[j];
/* 153 */     this.bfls = new float[i];
/* 154 */     for (j = 0; j < i; j++) this.bfls[j] = 1.0F;
/*     */   }
/*     */   
/*     */   void setBelief(float[] paramArrayOfFloat)
/*     */   {
/* 159 */     this.bfls = UTIL.getDuplicate(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setBeliefProduct(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 168 */     float[] arrayOfFloat = MATH.productOfTwo(this.clq, this.bfls, paramArrayOfInt, paramArrayOfFloat, this.clq, this.sz);
/* 169 */     if (MATH.sum(arrayOfFloat) < 1.0E-8D) {
/* 170 */       for (int i = 0; i < paramArrayOfFloat.length; i++) paramArrayOfFloat[i] *= 1024.0F;
/* 171 */       arrayOfFloat = MATH.productOfTwo(this.clq, this.bfls, paramArrayOfInt, paramArrayOfFloat, this.clq, this.sz);
/* 172 */       HelpPanel.addHelp("Warning: Raised low potential by 1024!");
/*     */     }
/* 174 */     this.bfls = arrayOfFloat;
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
/*     */   void setBeliefProduct(int paramInt1, int paramInt2)
/*     */   {
/* 189 */     int[] arrayOfInt = { paramInt1 };
/* 190 */     int i = getStateCount(paramInt1);
/* 191 */     float[] arrayOfFloat = new float[i];
/* 192 */     for (int j = 0; j < i; j++) {
/* 193 */       if (j == paramInt2) arrayOfFloat[j] = 1.0F; else
/* 194 */         arrayOfFloat[j] = 0.0F;
/*     */     }
/* 196 */     this.bfls = MATH.productOfTwo(this.clq, this.bfls, arrayOfInt, arrayOfFloat, this.clq, this.sz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setDumbSepsetBelief()
/*     */   {
/* 204 */     if (!hasPeer()) { return;
/*     */     }
/* 206 */     int i = 1;
/* 207 */     for (int j = 0; j < getNeighborCount(); j++) {
/* 208 */       if (isPeer(getNeighbor(j))) {
/* 209 */         int[] arrayOfInt = MATH.getSubsetDimen(this.clq, this.sz, getSepset(j));
/* 210 */         for (int k = 0; k < getSepsetSize(j); k++) i *= arrayOfInt[k];
/* 211 */         break;
/*     */       }
/*     */     }
/* 214 */     this.spls = new float[i];
/* 215 */     for (j = 0; j < i; j++) { this.spls[j] = 1.0F;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setSepsetBelief()
/*     */   {
/* 222 */     if (!hasPeer()) { return;
/*     */     }
/* 224 */     int i = 0;
/* 225 */     while (!isPeer(getNeighbor(i))) { i++;
/*     */     }
/* 227 */     int j = getSepsetSize(i);
/* 228 */     int[] arrayOfInt = new int[j];
/* 229 */     for (int k = 0; k < j; k++) {
/* 230 */       int m = getSepset(i, k);
/* 231 */       for (int n = 0; n < getCqSize(); n++) {
/* 232 */         if (this.clq[n] == m) arrayOfInt[k] = n;
/*     */       }
/*     */     }
/* 235 */     this.spls = MATH.margin(this.bfls, this.sz, arrayOfInt);
/*     */   }
/*     */   
/* 238 */   void setSepsetBelief(float[] paramArrayOfFloat) { this.spls = paramArrayOfFloat; }
/*     */   
/*     */ 
/*     */   void normalizeBelief()
/*     */   {
/* 243 */     this.bfls = MATH.normalize(this.bfls);
/* 244 */     if (hasPeer()) this.spls = MATH.normalize(this.spls);
/*     */   }
/*     */   
/*     */   float[] getVarMargin(int paramInt)
/*     */   {
/* 249 */     int i = 0;
/* 250 */     while (this.clq[i] != paramInt) i++;
/* 251 */     int[] arrayOfInt = { i };
/*     */     
/* 253 */     float[] arrayOfFloat = MATH.margin(this.bfls, this.sz, arrayOfInt);
/* 254 */     return MATH.normalize(arrayOfFloat);
/*     */   }
/*     */   
/*     */   float[] getVarMargin(int[] paramArrayOfInt)
/*     */   {
/* 259 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/* 260 */     int i = 0;
/* 261 */     for (int j = 0; j < getCqSize(); j++) {
/* 262 */       if (MATH.member(this.clq[j], paramArrayOfInt)) arrayOfInt[(i++)] = j;
/*     */     }
/* 264 */     float[] arrayOfFloat = MATH.margin(this.bfls, this.sz, arrayOfInt);
/* 265 */     return MATH.normalize(arrayOfFloat);
/*     */   }
/*     */   
/*     */   boolean isImpossible()
/*     */   {
/* 270 */     float f = 0.0F;
/* 271 */     for (int i = 0; i < this.bfls.length; i++) f += this.bfls[i];
/* 272 */     if (f > 1.0E-6F) return false;
/* 273 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void absorbThroughSepset(int paramInt1, CNode paramCNode, int paramInt2)
/*     */   {
/* 283 */     int i = 0;
/* 284 */     float[][] arrayOfFloat = new float[2][];
/*     */     
/* 286 */     if (isPeer(paramInt2)) {
/* 287 */       while (!isNeighbor(i, paramInt2)) i++;
/* 288 */       arrayOfFloat = MATH.calibration(getBelief(), getCqMember(), getStateCount(), paramCNode.getBelief(), paramCNode.getCqMember(), paramCNode.getStateCount(), getSepsetBelief(), getSepset(i));
/*     */       
/*     */ 
/*     */ 
/* 292 */       setBelief(arrayOfFloat[0]);
/* 293 */       setSepsetBelief(arrayOfFloat[1]);
/*     */     }
/*     */     else {
/* 296 */       while (!paramCNode.isNeighbor(i, paramInt1)) i++;
/* 297 */       arrayOfFloat = MATH.calibration(getBelief(), getCqMember(), getStateCount(), paramCNode.getBelief(), paramCNode.getCqMember(), paramCNode.getStateCount(), paramCNode.getSepsetBelief(), paramCNode.getSepset(i));
/*     */       
/*     */ 
/*     */ 
/* 301 */       setBelief(arrayOfFloat[0]);
/* 302 */       paramCNode.setSepsetBelief(arrayOfFloat[1]);
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
/*     */   void collectEvidence(int paramInt1, int paramInt2, CNode[] paramArrayOfCNode)
/*     */   {
/* 316 */     if (paramArrayOfCNode.length == 1) return;
/* 317 */     if ((getNeighborCount() == 1) && (paramInt1 == getNeighbor(0)))
/* 318 */       return;
/* 319 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 320 */       int j = getNeighbor(i);
/* 321 */       if (j != paramInt1) {
/* 322 */         paramArrayOfCNode[j].collectEvidence(paramInt2, j, paramArrayOfCNode);
/* 323 */         absorbThroughSepset(paramInt2, paramArrayOfCNode[j], j);
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
/*     */   void distributeEvidence(int paramInt1, int paramInt2, CNode[] paramArrayOfCNode)
/*     */   {
/* 336 */     if (paramArrayOfCNode.length == 1) return;
/* 337 */     if (paramInt1 != -1) absorbThroughSepset(paramInt2, paramArrayOfCNode[paramInt1], paramInt1);
/* 338 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 339 */       int j = getNeighbor(i);
/* 340 */       if (j != paramInt1) {
/* 341 */         paramArrayOfCNode[j].distributeEvidence(paramInt2, j, paramArrayOfCNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadLinkage(BufferedReader paramBufferedReader)
/*     */   {
/* 350 */     loadLabel(paramBufferedReader);
/* 351 */     loadPeer(paramBufferedReader);
/* 352 */     loadPos(paramBufferedReader);
/* 353 */     loadCqMember(paramBufferedReader);
/* 354 */     loadStateCount(paramBufferedReader);
/* 355 */     loadHyperLink(paramBufferedReader);
/*     */   }
/*     */   
/*     */ 
/*     */   void saveLinkage(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 361 */     saveLabel(paramPrintWriter);
/* 362 */     savePeer(paramPrintWriter);
/* 363 */     savePos(paramPrintWriter);
/* 364 */     saveCqMember(paramPrintWriter, paramInt);
/* 365 */     saveStateCount(paramPrintWriter);
/* 366 */     saveHyperLink(paramPrintWriter);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/CNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */