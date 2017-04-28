/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class HNode extends Node
/*     */ {
/*   9 */   public int[] clq = null;
/*  10 */   HLink[] nbc = null;
/*     */   
/*     */ 
/*     */   HNode() {}
/*     */   
/*     */ 
/*     */   HNode(HNode paramHNode)
/*     */   {
/*  18 */     super(paramHNode);
/*  19 */     setHNode(paramHNode);
/*     */   }
/*     */   
/*     */   void setHNode(HNode paramHNode)
/*     */   {
/*  24 */     setCqMember(paramHNode.getCqMember());
/*  25 */     setHyperLinks(paramHNode.getHyperLinks());
/*     */   }
/*     */   
/*     */   void reinit() {
/*  29 */     this.clq = null;
/*  30 */     this.nbc = null;
/*  31 */     setPeer(-1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setLabel(int paramInt)
/*     */   {
/*  38 */     setLabel("C" + paramInt);
/*     */   }
/*     */   
/*     */   void setLabel(int paramInt, String paramString)
/*     */   {
/*  43 */     setLabel(paramString + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getCqSize()
/*     */   {
/*  50 */     return this.clq == null ? 0 : this.clq.length;
/*     */   }
/*     */   
/*     */   void setCqMember(int[] paramArrayOfInt)
/*     */   {
/*  55 */     this.clq = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getCqMember()
/*     */   {
/*  60 */     return UTIL.getDuplicate(this.clq);
/*     */   }
/*     */   
/*     */   int getCqMember(int paramInt) {
/*  64 */     return this.clq[paramInt];
/*     */   }
/*     */   
/*     */   void delVarFromCq(int paramInt)
/*     */   {
/*  69 */     this.clq = MATH.delMember(paramInt, this.clq);
/*     */   }
/*     */   
/*     */   boolean isCqMember(int paramInt)
/*     */   {
/*  74 */     return MATH.member(paramInt, this.clq);
/*     */   }
/*     */   
/*     */   boolean isCqMember(int[] paramArrayOfInt) {
/*  78 */     for (int i = 0; i < paramArrayOfInt.length; i++)
/*  79 */       if (!MATH.member(paramArrayOfInt[i], this.clq)) return false;
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   boolean equalCqMember(int[] paramArrayOfInt)
/*     */   {
/*  85 */     return MATH.isEqualSet(paramArrayOfInt, this.clq);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void rmLink()
/*     */   {
/*  92 */     this.nbc = null;
/*     */   }
/*     */   
/*     */   void setNeighbor(int paramInt1, int paramInt2)
/*     */   {
/*  97 */     this.nbc[paramInt1].setNeighbor(paramInt2);
/*     */   }
/*     */   
/*     */   int getNeighborCount()
/*     */   {
/* 102 */     return this.nbc == null ? 0 : this.nbc.length;
/*     */   }
/*     */   
/*     */   int getNeighbor(int paramInt)
/*     */   {
/* 107 */     return this.nbc[paramInt].getNeighbor();
/*     */   }
/*     */   
/*     */   boolean isNeighbor(int paramInt)
/*     */   {
/* 112 */     if (this.nbc == null) return false;
/* 113 */     for (int i = 0; i < getNeighborCount(); i++)
/* 114 */       if (this.nbc[i].isNeighbor(paramInt)) return true;
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   boolean isNeighbor(int paramInt1, int paramInt2) {
/* 119 */     if (this.nbc == null) return false;
/* 120 */     return this.nbc[paramInt1].isNeighbor(paramInt2);
/*     */   }
/*     */   
/*     */   void delNeighbor(int paramInt)
/*     */   {
/* 125 */     int i = getNeighborCount();
/* 126 */     if (i == 1) {
/* 127 */       this.nbc = null;
/* 128 */       return;
/*     */     }
/* 130 */     HLink[] arrayOfHLink = new HLink[i - 1];
/* 131 */     int j = 0;
/* 132 */     for (int k = 0; k < i; k++) {
/* 133 */       if (getNeighbor(k) != paramInt)
/* 134 */         arrayOfHLink[(j++)] = this.nbc[k];
/*     */     }
/* 136 */     this.nbc = arrayOfHLink;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isCenter()
/*     */   {
/* 142 */     return !hasPeer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getSepsetSize(int paramInt)
/*     */   {
/* 149 */     return this.nbc[paramInt].getSepsetSize();
/*     */   }
/*     */   
/*     */   int[] getSepsetToPeer()
/*     */   {
/* 154 */     return getSepsetToNbr(getPeer());
/*     */   }
/*     */   
/*     */   int[] getSepsetToNbr(int paramInt)
/*     */   {
/* 159 */     for (int i = 0; i < getNeighborCount(); i++)
/* 160 */       if (isNeighbor(i, paramInt)) return this.nbc[i].getSepset();
/* 161 */     return null;
/*     */   }
/*     */   
/*     */   int[] getSepset(int paramInt)
/*     */   {
/* 166 */     return this.nbc[paramInt].getSepset();
/*     */   }
/*     */   
/*     */   int getSepset(int paramInt1, int paramInt2) {
/* 170 */     return this.nbc[paramInt1].getSepset(paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   void setHyperLinks(HLink[] paramArrayOfHLink)
/*     */   {
/* 176 */     if (paramArrayOfHLink == null) {
/* 177 */       this.nbc = null;return;
/*     */     }
/* 179 */     this.nbc = new HLink[paramArrayOfHLink.length];
/* 180 */     for (int i = 0; i < paramArrayOfHLink.length; i++) this.nbc[i] = new HLink(paramArrayOfHLink[i]);
/*     */   }
/*     */   
/*     */   HLink[] getHyperLinks() {
/* 184 */     if (this.nbc == null) return null;
/* 185 */     HLink[] arrayOfHLink = new HLink[getNeighborCount()];
/* 186 */     for (int i = 0; i < getNeighborCount(); i++) arrayOfHLink[i] = new HLink(this.nbc[i]);
/* 187 */     return arrayOfHLink;
/*     */   }
/*     */   
/*     */   HLink getHyperLinks(int paramInt) {
/* 191 */     if (this.nbc == null) return null;
/* 192 */     for (int i = 0; i < getNeighborCount(); i++)
/* 193 */       if (this.nbc[i].getNeighbor() == paramInt) return new HLink(this.nbc[i]);
/* 194 */     return null;
/*     */   }
/*     */   
/*     */   public void addHyperLink(int paramInt, int[] paramArrayOfInt)
/*     */   {
/*     */     int i;
/* 200 */     if (this.nbc == null) i = 1; else
/* 201 */       i = getNeighborCount() + 1;
/* 202 */     HLink[] arrayOfHLink = new HLink[i];
/* 203 */     for (int j = 0; j < i - 1; j++) arrayOfHLink[j] = this.nbc[j];
/* 204 */     arrayOfHLink[(i - 1)] = new HLink(paramInt, paramArrayOfInt);
/* 205 */     this.nbc = arrayOfHLink;
/*     */   }
/*     */   
/* 208 */   void addHyperLink(HLink paramHLink) { addHyperLink(paramHLink.getNeighbor(), paramHLink.getSepset()); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void modifyNodeIndex(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 216 */     int i = getNeighborCount();
/* 217 */     for (int j = 0; j < i; j++) {
/* 218 */       int k = getNeighbor(j);
/* 219 */       if ((k >= paramInt1) && (k <= paramInt2)) { setNeighbor(j, k + paramInt3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void loadCqMember(BufferedReader paramBufferedReader)
/*     */   {
/* 227 */     setCqMember(UTIL.loadIntList(paramBufferedReader));
/*     */   }
/*     */   
/*     */   void loadHyperLink(BufferedReader paramBufferedReader)
/*     */   {
/* 232 */     int i = UTIL.loadInt(paramBufferedReader);
/* 233 */     this.nbc = new HLink[i];
/* 234 */     for (int j = 0; j < i; j++) this.nbc[j] = HLink.loadHyperLink(paramBufferedReader);
/*     */   }
/*     */   
/*     */   static HNode loadHNode(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 239 */     HNode localHNode = new HNode();
/* 240 */     localHNode.loadCqMember(paramBufferedReader);
/* 241 */     localHNode.loadHyperLink(paramBufferedReader);
/* 242 */     localHNode.loadPeer(paramBufferedReader);
/* 243 */     localHNode.loadPos(paramBufferedReader);
/* 244 */     localHNode.setLabel(paramInt);
/* 245 */     return localHNode;
/*     */   }
/*     */   
/*     */   void loadLinkage(BufferedReader paramBufferedReader)
/*     */   {
/* 250 */     loadLabel(paramBufferedReader);
/* 251 */     loadPeer(paramBufferedReader);
/* 252 */     loadPos(paramBufferedReader);
/* 253 */     loadCqMember(paramBufferedReader);
/* 254 */     loadHyperLink(paramBufferedReader);
/*     */   }
/*     */   
/*     */   void saveCqMember(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 259 */     UTIL.saveIntList(paramPrintWriter, this.clq, new String("members_of_cq_" + paramInt));
/*     */   }
/*     */   
/*     */   void saveHyperLink(PrintWriter paramPrintWriter)
/*     */   {
/* 264 */     int i = getNeighborCount();
/* 265 */     paramPrintWriter.println(i + "  #_of_hyperlinks");
/* 266 */     for (int j = 0; j < i; j++) this.nbc[j].saveHyperLink(paramPrintWriter);
/*     */   }
/*     */   
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 271 */     saveCqMember(paramPrintWriter, paramInt);
/* 272 */     saveHyperLink(paramPrintWriter);
/* 273 */     savePeer(paramPrintWriter);
/* 274 */     savePos(paramPrintWriter);
/*     */   }
/*     */   
/*     */   void saveLinkage(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 279 */     saveLabel(paramPrintWriter);
/* 280 */     savePeer(paramPrintWriter);
/* 281 */     savePos(paramPrintWriter);
/* 282 */     saveCqMember(paramPrintWriter, paramInt);
/* 283 */     saveHyperLink(paramPrintWriter);
/*     */   }
/*     */   
/*     */ 
/*     */   void seeHNode()
/*     */   {
/* 289 */     seeNode();
/* 290 */     HelpPanel.showList("Clq members: ", this.clq);
/* 291 */     if (this.nbc != null) for (int i = 0; i < getNeighborCount(); i++) this.nbc[i].showHLink(); else
/* 292 */       HelpPanel.appendHelp("\tnbc=[] ");
/*     */   }
/*     */   
/* 295 */   void showHNode() { System.out.println("  [HNode]");
/* 296 */     showNode();
/* 297 */     UTIL.showList("clq:\t", this.clq);
/* 298 */     if (this.nbc != null) for (int i = 0; i < getNeighborCount(); i++) this.nbc[i].printHLink(); else {
/* 299 */       System.out.println(" nbc=[] ");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */