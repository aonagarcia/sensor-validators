/*     */ package Network;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtLID
/*     */   extends AgtLI
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  14 */   IntAgtLID agnet = null;
/*     */   
/*     */ 
/*     */   public AgtLID(IntAgtLID paramIntAgtLID, String paramString, Bridge paramBridge)
/*     */   {
/*  19 */     super(null, paramString, paramBridge);
/*  20 */     this.agnet = paramIntAgtLID;
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
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/*  34 */     switch (paramInt2) {
/*  35 */     case 3:  setOutLkgBufPoten(paramInt1); break;
/*  36 */     case 6:  this.msgb[paramInt1].setOutBody(this.agnet.getFullyObserved(paramInt1));
/*  37 */       break;
/*  38 */     default:  super.setMsgToPat(paramInt1, paramInt2, paramArrayOfString);
/*     */     }
/*  40 */     this.msgb[paramInt1].setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actOnPatMsg(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*  51 */     switch (paramInt2) {
/*  52 */     case 7:  this.agnet.enterFullyObserved(this.msgb[paramInt1].getInBody());
/*  53 */       return null; }
/*  54 */     return super.actOnPatMsg(paramInt1, paramInt2, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setBelief(int paramInt1, int paramInt2)
/*     */   {
/*  65 */     int[] arrayOfInt = null;
/*  66 */     for (int i = 0; i < this.agnet.getNodeCount(); i++) {
/*  67 */       if (this.agnet.isMarked(i)) { arrayOfInt = MATH.addMember(i, arrayOfInt);
/*     */       }
/*     */     }
/*  70 */     this.agnet.setPoten(arrayOfInt);
/*  71 */     this.agnet.clrBufferPoten();
/*  72 */     this.agnet.clrLkgBufPoten();
/*     */     
/*     */ 
/*  75 */     this.agnet.setMjfPoten(arrayOfInt);
/*  76 */     this.agnet.clrMjfBufPoten();
/*  77 */     this.agnet.clrMjfLkgBufPoten();
/*     */     
/*     */ 
/*     */ 
/*  81 */     setMsgToChd(paramInt1, paramInt2, null);
/*  82 */     for (i = 0; i < this.msgb.length; i++) {
/*  83 */       if (i != paramInt1)
/*  84 */         new Envoy(this.msgb[i]);
/*     */     }
/*  86 */     getChdMsg(paramInt1, paramInt2);
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
/*     */   int getLkgBufPotenLines(int[][] paramArrayOfInt, LazyBelSet[] paramArrayOfLazyBelSet)
/*     */   {
/* 105 */     int i = paramArrayOfLazyBelSet.length;
/* 106 */     int j = 1;
/* 107 */     for (int k = 0; k < paramArrayOfInt.length; k++) {
/* 108 */       j += 2;
/* 109 */       int m = paramArrayOfLazyBelSet[k].getPotenCount();
/* 110 */       if (m != 0)
/* 111 */         for (int n = 0; n < m; n++) {
/* 112 */           j += 4;
/* 113 */           j += (paramArrayOfLazyBelSet[k].getPotenSize(n) + 4) / 5;
/*     */         }
/*     */     }
/* 116 */     return j;
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
/*     */   void setOutLkgBufPoten(int paramInt)
/*     */   {
/* 139 */     int[][] arrayOfInt = this.agnet.getMjfLkg(paramInt);
/* 140 */     String[][] arrayOfString = this.agnet.getMjfLkgStr(paramInt);
/* 141 */     int i = arrayOfInt.length;
/* 142 */     LazyBelSet[] arrayOfLazyBelSet = this.agnet.getMjfBufPoten(paramInt);
/*     */     
/*     */ 
/* 145 */     int j = getLkgBufPotenLines(arrayOfInt, arrayOfLazyBelSet);
/* 146 */     this.msgb[paramInt].setDumbOutBody(j);
/*     */     
/* 148 */     int k = 0;
/* 149 */     this.msgb[paramInt].setOutBody(i + " #lkg", k++);
/*     */     
/* 151 */     for (int m = 0; m < i; m++) {
/* 152 */       String str = new String("");
/* 153 */       for (int n = 0; n < arrayOfString[m].length; n++)
/* 154 */         str = new String(str + " " + arrayOfString[m][n]);
/* 155 */       this.msgb[paramInt].setOutBody(str, k++);
/* 156 */       BayesNet localBayesNet = this.agnet.getNet();
/* 157 */       k = setOutLkgBufPoten(arrayOfLazyBelSet[m], localBayesNet, paramInt, k);
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
/*     */   void setInLkgBufPoten(int paramInt)
/*     */   {
/* 173 */     BayesNet localBayesNet = this.agnet.getNet();
/* 174 */     ArrayList localArrayList = getInLkgBufPoten(localBayesNet, paramInt);
/* 175 */     int[][] arrayOfInt = (int[][])localArrayList.get(0);
/* 176 */     int i = arrayOfInt.length;
/* 177 */     LazyBelSet[] arrayOfLazyBelSet = (LazyBelSet[])localArrayList.get(1);
/*     */     
/* 179 */     LJoinTreeM localLJoinTreeM = this.agnet.getJoinTree1();
/* 180 */     for (int j = 0; j < i; j++)
/* 181 */       if (arrayOfLazyBelSet[j].getPotenCount() != 0) {
/* 182 */         k = localLJoinTreeM.getCqHome(arrayOfInt[j]);
/* 183 */         localLJoinTreeM.addLkgBufPoten(k, arrayOfLazyBelSet[j]);
/*     */       }
/* 185 */     SpaceTime.showUsedMemory("Middle of\tsetInLkgBufPoten()");
/*     */     
/* 187 */     LJoinTreeM[][] arrayOfLJoinTreeM = this.agnet.getMsgJF();
/* 188 */     int k = arrayOfLJoinTreeM.length;
/* 189 */     for (int m = 0; m < k; m++) {
/* 190 */       if (m != paramInt)
/*     */       {
/* 192 */         int n = arrayOfLJoinTreeM[m].length;
/* 193 */         for (int i1 = 0; i1 < i; i1++) {
/* 194 */           for (int i2 = 0; i2 < n; i2++) {
/* 195 */             int i3 = arrayOfLJoinTreeM[m][i2].getCqHome(arrayOfInt[i1]);
/* 196 */             if (i3 != -1) {
/* 197 */               arrayOfLJoinTreeM[m][i2].addLkgBufPoten(i3, arrayOfLazyBelSet[i1]);
/* 198 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqCollectBef(int paramInt)
/*     */   {
/* 209 */     for (int i = 0; i < this.msgb.length; i++) {
/* 210 */       if (i != paramInt) {
/* 211 */         this.msgb[i].setOutMsgType(3);
/* 212 */         this.msgb[i].setEmptyOutBody();
/* 213 */         this.msgb[i].setRecved(false);
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
/*     */   void collectBelief(int paramInt1, int paramInt2)
/*     */   {
/* 237 */     SpaceTime.showUsedMemory("Before collectBelief");
/* 238 */     this.agnet.clrBufferPoten();
/* 239 */     this.agnet.clrLkgBufPoten();
/* 240 */     this.agnet.clrMjfBufPoten();
/* 241 */     this.agnet.clrMjfLkgBufPoten();
/* 242 */     SpaceTime.showUsedMemory("After clearing all buffers");
/*     */     
/* 244 */     int i = this.msgb.length;
/* 245 */     if ((paramInt1 != -1) && (i == 1)) {
/* 246 */       this.agnet.collectMJFPotential(paramInt1);
/* 247 */       return;
/*     */     }
/*     */     
/* 250 */     setMsgToChd(paramInt1, paramInt2, null);
/* 251 */     for (int j = 0; j < i; j++) {
/* 252 */       if (j != paramInt1)
/* 253 */         new Envoy(this.msgb[j]);
/*     */     }
/* 255 */     getChdMsg(paramInt1, paramInt2);
/* 256 */     SpaceTime.showUsedMemory("After getChdMsg()");
/* 257 */     if (paramInt1 != -1) { this.agnet.collectMJFPotential(paramInt1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqDistrBelief(int paramInt)
/*     */   {
/* 264 */     for (int i = 0; i < this.msgb.length; i++) {
/* 265 */       if (i != paramInt) {
/* 266 */         this.msgb[i].setOutMsgType(5);
/* 267 */         this.agnet.collectMJFPotential(i);
/* 268 */         setOutLkgBufPoten(i);
/* 269 */         this.msgb[i].setRecved(false);
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
/*     */   void distributeBelief(int paramInt1, int paramInt2)
/*     */   {
/* 282 */     if (paramInt1 != -1) {
/* 283 */       actOnPatMsg(paramInt1, paramInt2, null);
/*     */     }
/* 285 */     int i = this.msgb.length;
/* 286 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/*     */     
/* 289 */     setMsgToChd(paramInt1, paramInt2, null);
/* 290 */     for (int j = 0; j < i; j++) {
/* 291 */       if (j != paramInt1)
/* 292 */         new Envoy(this.msgb[j]);
/*     */     }
/* 294 */     getChdMsg(paramInt1, paramInt2);
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
/*     */   void collectObs(int paramInt1, int paramInt2)
/*     */   {
/* 316 */     int i = this.msgb.length;
/* 317 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 319 */     setMsgToChd(paramInt1, paramInt2, null);
/* 320 */     for (int j = 0; j < i; j++) {
/* 321 */       if (j != paramInt1)
/* 322 */         new Envoy(this.msgb[j]);
/*     */     }
/* 324 */     getChdMsg(paramInt1, paramInt2);
/* 325 */     for (j = 0; j < this.msgb.length; j++) {
/* 326 */       if (j != paramInt1) {
/* 327 */         this.agnet.enterFullyObserved(this.msgb[j].getInBody());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqDistrObs(int paramInt)
/*     */   {
/* 335 */     for (int i = 0; i < this.msgb.length; i++) {
/* 336 */       if (i != paramInt) {
/* 337 */         this.msgb[i].setOutMsgType(7);
/* 338 */         this.msgb[i].setOutBody(this.agnet.getFullyObserved(i));
/* 339 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtLID.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */