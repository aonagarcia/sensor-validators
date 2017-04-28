/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ 
/*     */ public class AgtT
/*     */   extends AgtCom
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  11 */   IntAgtT agnet = null;
/*     */   
/*     */   static final int COLLECTPUBPINF = 0;
/*     */   
/*     */   public static final int COLLECTMLINK = 1;
/*     */   
/*     */   public static final int DISTRIBUTEMLINK = 2;
/*     */   
/*     */   public static final int DEPTHFELIMINATE = 3;
/*     */   
/*     */   public static final int DISTRIBUTEDLINK = 4;
/*     */   
/*     */   public static final int FINDNEWFILLIN = 5;
/*     */   
/*     */   public static final int CLOSESAFETRI = 6;
/*     */   
/*     */   public static final int SETJOINTREE = 7;
/*     */   
/*     */   public static final int SETHOSTTREE = 8;
/*     */   
/*     */   public static final int SETLINKAGETREE = 9;
/*     */   public static final int ASSIGNDSEPNODE = 10;
/*     */   static final int ASGNDSEPNDPRVPA = 11;
/*     */   static final int ASGDSEPNDWOUTPP = 12;
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public AgtT(IntAgtT paramIntAgtT, String paramString, Bridge paramBridge)
/*     */   {
/*  39 */     super(paramString, paramBridge);
/*  40 */     this.agnet = paramIntAgtT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBearerMgr()
/*     */   {
/*  48 */     this.bmgr = new BearerMgr(this.msgb, this);
/*  49 */     this.bmgr.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRoot(int paramInt)
/*     */   {
/*  57 */     RootCom localRootCom = new RootCom(this, paramInt);
/*  58 */     localRootCom.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  69 */     if (paramInt2 == 0) {
/*  70 */       int i = collectPubPaInfo(paramInt1, paramInt2, null);
/*  71 */       return UTIL.intToStrAry(i);
/*     */     }
/*     */     
/*  74 */     if (paramInt2 == 1) {
/*  75 */       HelpPanel.addHelp("Start moral link collection.");
/*  76 */       collectMLink(paramInt1, paramInt2);
/*  77 */       if (paramInt1 == -1) {
/*  78 */         HelpPanel.addHelp("Root: moral link collection done.");
/*     */       } else
/*  80 */         HelpPanel.addHelp("NonRoot: moral link collection done.");
/*  81 */       return null;
/*     */     }
/*  83 */     if (paramInt2 == 2) {
/*  84 */       HelpPanel.addHelp("Start moral link distribution.");
/*  85 */       distributeMLink(paramInt1, paramInt2);
/*  86 */       if (paramInt1 == -1) {
/*  87 */         HelpPanel.addHelp("Root: moral link distribution done.");
/*     */       } else {
/*  89 */         HelpPanel.addHelp("NonRoot: moral link distribution done.");
/*     */       }
/*  91 */       setCtrlFlag("paint_mode", "undirect_graph");
/*  92 */       this.panel.showNet();
/*  93 */       setCtrlFlag("operation_stage", "moralization");
/*  94 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  98 */     if (paramInt2 == 3) {
/*  99 */       HelpPanel.addHelp("Start depth first elimination.");
/* 100 */       depFstElim(paramInt1, paramInt2);
/* 101 */       if (paramInt1 == -1) {
/* 102 */         HelpPanel.addHelp("Root: depth first elimination done.");
/*     */       } else
/* 104 */         HelpPanel.addHelp("NonRoot: depth first elimination done.");
/* 105 */       return null;
/*     */     }
/* 107 */     if (paramInt2 == 4) {
/* 108 */       HelpPanel.addHelp("Start fill-in distribution.");
/* 109 */       distributeDLink(paramInt1, paramInt2);
/* 110 */       if (paramInt1 == -1) {
/* 111 */         HelpPanel.addHelp("Root: fill-in distribution done.");
/*     */       } else
/* 113 */         HelpPanel.addHelp("NonRoot: fill-in distribution done.");
/* 114 */       setCtrlFlag("paint_mode", "undirect_graph");
/* 115 */       this.panel.showNet();
/* 116 */       return null; }
/*     */     boolean bool;
/* 118 */     if (paramInt2 == 5) {
/* 119 */       HelpPanel.addHelp("Start checking elimination order.");
/* 120 */       bool = findNewFillin(paramInt1, paramInt2);
/* 121 */       if (paramInt1 == -1) {
/* 122 */         HelpPanel.addHelp("Root: elimination order checked.");
/* 123 */         if (bool == true) setCtrlFlag("new_fillin", "has_new_fillin"); else {
/* 124 */           setCtrlFlag("new_fillin", "no_new_fillin");
/*     */         }
/*     */       } else {
/* 127 */         HelpPanel.addHelp("NonRoot: elimination order checking done."); }
/* 128 */       return UTIL.boolToStrAry(bool);
/*     */     }
/* 130 */     if (paramInt2 == 6) {
/* 131 */       closeSafeTri(paramInt1, paramInt2);
/* 132 */       if (paramInt1 == -1) {
/* 133 */         HelpPanel.addHelp("Root: triangulation done.");
/*     */       } else
/* 135 */         HelpPanel.addHelp("NonRoot: triangulation done.");
/* 136 */       setCtrlFlag("paint_mode", "undirect_graph");
/* 137 */       this.panel.showNet();
/* 138 */       setCtrlFlag("operation_stage", "triangulation");
/* 139 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 143 */     if (paramInt2 == 7) {
/* 144 */       HelpPanel.addHelp("Start construction of JTs.");
/* 145 */       setJTree(paramInt1, paramInt2);
/* 146 */       if (paramInt1 == -1) {
/* 147 */         HelpPanel.addHelp("Root: JTs constructed.");
/*     */       } else
/* 149 */         HelpPanel.addHelp("NonRoot: JTs constructed .");
/* 150 */       setCtrlFlag("paint_mode", "join_tree");
/* 151 */       this.panel.showNet();
/* 152 */       return null;
/*     */     }
/* 154 */     if (paramInt2 == 8) {
/* 155 */       HelpPanel.addHelp("Start construction of host trees.");
/* 156 */       setHTree(paramInt1, paramInt2);
/* 157 */       if (paramInt1 == -1) {
/* 158 */         HelpPanel.addHelp("Root: host trees constructed.");
/*     */       } else
/* 160 */         HelpPanel.addHelp("NonRoot: host trees constructed.");
/* 161 */       return null;
/*     */     }
/* 163 */     if (paramInt2 == 9) {
/* 164 */       HelpPanel.addHelp("Start construction of linkage trees.");
/* 165 */       setLTree(paramInt1, paramInt2);
/* 166 */       if (paramInt1 == -1) {
/* 167 */         HelpPanel.addHelp("Root: linkage trees constructed.");
/*     */       } else
/* 169 */         HelpPanel.addHelp("NonRoot: linkage trees constructed.");
/* 170 */       setCtrlFlag("operation_stage", "link_join_trees");
/* 171 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 175 */     if (paramInt2 == 11) {
/* 176 */       bool = asgnDsepndWithPrvParent(paramInt1, paramInt2, null);
/* 177 */       return UTIL.boolToStrAry(bool);
/*     */     }
/* 179 */     if (paramInt2 == 12) {
/* 180 */       asgnDsepndWoutPrvParent(paramInt1, paramInt2, null);
/* 181 */       return null;
/*     */     }
/* 183 */     if (paramInt2 == 10) {
/* 184 */       HelpPanel.addHelp("Start d-sepnode assignment.");
/* 185 */       assignDsepnode(paramInt1, paramInt2);
/* 186 */       if (paramInt1 == -1) {
/* 187 */         HelpPanel.addHelp("Root: d-sepnodes assigned.");
/*     */       } else
/* 189 */         HelpPanel.addHelp("NonRoot: d-sepnodes assigned.");
/* 190 */       setCtrlFlag("paint_mode", "direct_graph");
/* 191 */       this.panel.showNet();
/* 192 */       setCtrlFlag("operation_stage", "assign_dsepnode");
/* 193 */       return null;
/*     */     }
/* 195 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 204 */     switch (paramInt2) {
/* 205 */     case 1:  setReqCollectMLink(paramInt1); break;
/* 206 */     case 2:  setReqDistrMLink(paramInt1); break;
/*     */     case 3: 
/* 208 */       setReqDepFstElim(paramInt1); break;
/* 209 */     case 4:  setReqDistrDLink(paramInt1); break;
/* 210 */     case 5:  setReqFdNewFillin(paramInt1); break;
/* 211 */     case 6:  setReqCloseSafeTri(paramInt1); break;
/*     */     case 7: 
/* 213 */       setReqSetJTree(paramInt1); break;
/* 214 */     case 8:  setReqSetHTree(paramInt1); break;
/* 215 */     case 9:  setReqSetLTree(paramInt1); break;
/*     */     case 10: 
/* 217 */       setReqAssignDsepnode(paramInt1); break;
/* 218 */     case 11:  setReqAsgnDsepndPrvPa(paramInt1, paramString); break;
/* 219 */     case 12:  setReqAsgnDsepndWoutPrvPa(paramInt1, paramString); break;
/* 220 */     case 0:  setReqCollectPubPa(paramInt1, paramString);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 227 */     switch (paramInt2) {
/* 228 */     case 1:  setMLinkMsg(paramInt1); break;
/* 229 */     case 2:  this.msgb[paramInt1].setEmptyOutBody(); break;
/*     */     case 3: 
/* 231 */       setDLinkMsg(paramInt1); break;
/* 232 */     case 4:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 233 */     case 5:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 234 */     case 6:  this.msgb[paramInt1].setEmptyOutBody(); break;
/*     */     case 7: 
/* 236 */       this.msgb[paramInt1].setEmptyOutBody(); break;
/* 237 */     case 8:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 238 */     case 9:  this.msgb[paramInt1].setEmptyOutBody(); break;
/*     */     case 10: 
/* 240 */       this.msgb[paramInt1].setEmptyOutBody(); break;
/* 241 */     case 11:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 242 */     case 12:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 243 */     case 0:  this.msgb[paramInt1].setOutBody(paramArrayOfString);
/*     */     }
/* 245 */     this.msgb[paramInt1].setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/* 255 */     int i = this.msgb.length;
/* 256 */     int j = this.msgb.length;
/* 257 */     boolean[] arrayOfBoolean = new boolean[i];
/* 258 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 259 */     if (paramInt1 >= 0) {
/* 260 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/* 262 */     while (j > 0) {
/* 263 */       for (k = 0; k < i; k++) {
/* 264 */         if ((arrayOfBoolean[k] == 0) && 
/* 265 */           (this.msgb[k].isRecved())) {
/* 266 */           arrayOfBoolean[k] = true;j--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actOnPatMsg(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 278 */     switch (paramInt2) {
/* 279 */     case 2:  receiveMLink(paramInt1);return null;
/*     */     case 3: 
/* 281 */       addDLink(paramInt1);return null;
/* 282 */     case 4:  addDLink(paramInt1);return null;
/*     */     }
/* 284 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqCollectMLink(int paramInt)
/*     */   {
/* 291 */     for (int i = 0; i < this.msgb.length; i++) {
/* 292 */       if (i != paramInt) {
/* 293 */         this.msgb[i].setOutMsgType(1);
/* 294 */         this.msgb[i].setOutBody(getNewMLink(i));
/* 295 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   String[] getNewMLink(int paramInt)
/*     */   {
/* 303 */     Dsepset localDsepset = this.agnet.getDsepset(paramInt);
/* 304 */     String[][] arrayOfString = localDsepset.getDlinks(this.agnet.getMfillIn());
/* 305 */     if (arrayOfString == null) return null;
/* 306 */     String[] arrayOfString1 = new String[arrayOfString.length * 2];
/* 307 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 308 */       arrayOfString1[(2 * i)] = arrayOfString[i][0];
/* 309 */       arrayOfString1[(2 * i + 1)] = arrayOfString[i][1];
/*     */     }
/* 311 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */   void setMLinkMsg(int paramInt)
/*     */   {
/* 316 */     this.msgb[paramInt].setOutBody(getNewMLink(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void collectMLink(int paramInt1, int paramInt2)
/*     */   {
/* 326 */     Point[] arrayOfPoint = UTIL.strToPoint(this.panel.getVector2(0));
/* 327 */     this.agnet.localMoralize(arrayOfPoint);
/* 328 */     int i = this.msgb.length;
/* 329 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 331 */     setMsgToChd(paramInt1, paramInt2, null);
/* 332 */     for (int j = 0; j < i; j++) {
/* 333 */       if (j != paramInt1)
/* 334 */         new Envoy(this.msgb[j]);
/*     */     }
/* 336 */     getChdMsg(paramInt1, paramInt2);
/* 337 */     for (j = 0; j < this.msgb.length; j++) {
/* 338 */       if (j != paramInt1) {
/* 339 */         receiveMLink(j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void receiveMLink(int paramInt) {
/* 345 */     String[] arrayOfString = this.msgb[paramInt].getInBody();
/* 346 */     if (arrayOfString == null) return;
/* 347 */     for (int i = 0; i < arrayOfString.length / 2; i++) {
/* 348 */       Point localPoint = new Point(this.agnet.getIndex(arrayOfString[(2 * i)]), this.agnet.getIndex(arrayOfString[(2 * i + 1)]));
/*     */       
/* 350 */       if (!this.agnet.isMfillIn(localPoint)) this.agnet.addMfillIn(localPoint);
/*     */     }
/*     */   }
/*     */   
/*     */   void setReqDistrMLink(int paramInt)
/*     */   {
/* 356 */     for (int i = 0; i < this.msgb.length; i++) {
/* 357 */       if (i != paramInt) {
/* 358 */         this.msgb[i].setOutMsgType(2);
/* 359 */         this.msgb[i].setOutBody(getNewMLink(i));
/* 360 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeMLink(int paramInt1, int paramInt2)
/*     */   {
/* 372 */     int i = this.msgb.length;
/* 373 */     if (paramInt1 != -1) actOnPatMsg(paramInt1, paramInt2, null);
/* 374 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 376 */     setMsgToChd(paramInt1, paramInt2, null);
/* 377 */     for (int j = 0; j < i; j++) {
/* 378 */       if (j != paramInt1)
/* 379 */         new Envoy(this.msgb[j]);
/*     */     }
/* 381 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void addDLink(int paramInt)
/*     */   {
/* 388 */     String[] arrayOfString = this.msgb[paramInt].getInBody();
/* 389 */     if (arrayOfString == null) return;
/* 390 */     String[][] arrayOfString1 = new String[arrayOfString.length / 2][2];
/* 391 */     for (int i = 0; i < arrayOfString1.length; i++) {
/* 392 */       arrayOfString1[i][0] = arrayOfString[(2 * i)];
/* 393 */       arrayOfString1[i][1] = arrayOfString[(2 * i + 1)];
/*     */     }
/* 395 */     this.agnet.pickAddFillIn(arrayOfString1);
/*     */   }
/*     */   
/*     */ 
/*     */   String[] getNewDLink(int paramInt)
/*     */   {
/* 401 */     Dsepset localDsepset = this.agnet.getDsepset(paramInt);
/* 402 */     int[] arrayOfInt = localDsepset.getLocalIndex();
/* 403 */     this.agnet.setChordalGraph(arrayOfInt);
/* 404 */     String[][] arrayOfString = this.agnet.nodesToLinksLabel(arrayOfInt);
/* 405 */     if (arrayOfString == null) return null;
/* 406 */     String[] arrayOfString1 = new String[arrayOfString.length * 2];
/* 407 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 408 */       arrayOfString1[(2 * i)] = arrayOfString[i][0];
/* 409 */       arrayOfString1[(2 * i + 1)] = arrayOfString[i][1];
/*     */     }
/* 411 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */   void setReqDepFstElim(int paramInt)
/*     */   {
/* 416 */     for (int i = 0; i < this.msgb.length; i++) {
/* 417 */       if (i != paramInt) {
/* 418 */         this.msgb[i].setOutMsgType(3);
/* 419 */         this.msgb[i].setOutBody(getNewDLink(i));
/* 420 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setDLinkMsg(int paramInt) {
/* 426 */     this.msgb[paramInt].setOutBody(getNewDLink(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void depFstElim(int paramInt1, int paramInt2)
/*     */   {
/* 435 */     int i = this.msgb.length;
/* 436 */     if (paramInt1 != -1) actOnPatMsg(paramInt1, paramInt2, null);
/* 437 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 439 */     setMsgToChd(paramInt1, paramInt2, null);
/* 440 */     for (int j = 0; j < i; j++) {
/* 441 */       if (j != paramInt1)
/* 442 */         new Envoy(this.msgb[j]);
/*     */     }
/* 444 */     getChdMsg(paramInt1, paramInt2);
/* 445 */     for (j = 0; j < this.msgb.length; j++) {
/* 446 */       if (j != paramInt1) {
/* 447 */         addDLink(j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String[] disNewDLink(int paramInt) {
/* 453 */     Dsepset localDsepset = this.agnet.getDsepset(paramInt);
/* 454 */     int[] arrayOfInt = localDsepset.getLocalIndex();
/* 455 */     String[][] arrayOfString = this.agnet.nodesToLinksLabel(arrayOfInt);
/* 456 */     if (arrayOfString == null) return null;
/* 457 */     String[] arrayOfString1 = new String[arrayOfString.length * 2];
/* 458 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 459 */       arrayOfString1[(2 * i)] = arrayOfString[i][0];
/* 460 */       arrayOfString1[(2 * i + 1)] = arrayOfString[i][1];
/*     */     }
/* 462 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */   void setReqDistrDLink(int paramInt)
/*     */   {
/* 467 */     for (int i = 0; i < this.msgb.length; i++) {
/* 468 */       if (i != paramInt) {
/* 469 */         this.msgb[i].setOutMsgType(4);
/* 470 */         this.msgb[i].setOutBody(disNewDLink(i));
/* 471 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void distributeDLink(int paramInt1, int paramInt2) {
/* 477 */     int i = this.msgb.length;
/* 478 */     if (paramInt1 != -1) actOnPatMsg(paramInt1, paramInt2, null);
/* 479 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 481 */     setMsgToChd(paramInt1, paramInt2, null);
/* 482 */     for (int j = 0; j < i; j++) {
/* 483 */       if (j != paramInt1)
/* 484 */         new Envoy(this.msgb[j]);
/*     */     }
/* 486 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void setReqFdNewFillin(int paramInt)
/*     */   {
/* 491 */     for (int i = 0; i < this.msgb.length; i++) {
/* 492 */       if (i != paramInt) {
/* 493 */         this.msgb[i].setOutMsgType(5);
/* 494 */         this.msgb[i].setEmptyOutBody();
/* 495 */         this.msgb[i].setRecved(false);
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
/*     */   boolean findNewFillin(int paramInt1, int paramInt2)
/*     */   {
/* 510 */     int i = this.msgb.length;
/* 511 */     setMsgToChd(paramInt1, paramInt2, null);
/* 512 */     for (int j = 0; j < i; j++) {
/* 513 */       if (j != paramInt1)
/* 514 */         new Envoy(this.msgb[j]);
/*     */     }
/* 516 */     getChdMsg(paramInt1, paramInt2);
/* 517 */     for (j = 0; j < this.msgb.length; j++) {
/* 518 */       if (j != paramInt1) {
/* 519 */         k = Integer.parseInt(this.msgb[j].getInBody(0));
/* 520 */         if (k == 1) return true;
/*     */       }
/*     */     }
/* 523 */     Point[] arrayOfPoint1 = this.agnet.getIntFillIn();
/* 524 */     this.agnet.delFillIn();
/* 525 */     for (int k = 0; k < i; k++) {
/* 526 */       Dsepset localDsepset = this.agnet.getDsepset(k);
/* 527 */       int[] arrayOfInt = localDsepset.getLocalIndex();
/* 528 */       this.agnet.setChordalGraph(arrayOfInt);
/* 529 */       Point[] arrayOfPoint2 = this.agnet.getIntFillIn();
/* 530 */       arrayOfPoint1 = MATH.union(arrayOfPoint1, arrayOfPoint2);
/* 531 */       if (arrayOfPoint2 != null) {
/* 532 */         HelpPanel.appendList(" dsep=", arrayOfInt);
/* 533 */         HelpPanel.appendList(" fln=", arrayOfPoint2);
/* 534 */         this.agnet.setFillIn(arrayOfPoint1);
/* 535 */         return true;
/*     */       }
/*     */     }
/* 538 */     this.agnet.setFillIn(arrayOfPoint1);
/* 539 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqCloseSafeTri(int paramInt)
/*     */   {
/* 545 */     for (int i = 0; i < this.msgb.length; i++) {
/* 546 */       if (i != paramInt) {
/* 547 */         this.msgb[i].setOutMsgType(6);
/* 548 */         this.msgb[i].setEmptyOutBody();
/* 549 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void closeSafeTri(int paramInt1, int paramInt2) {
/* 555 */     int i = this.msgb.length;
/* 556 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 558 */     setMsgToChd(paramInt1, paramInt2, null);
/* 559 */     for (int j = 0; j < i; j++) {
/* 560 */       if (j != paramInt1)
/* 561 */         new Envoy(this.msgb[j]);
/*     */     }
/* 563 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqSetJTree(int paramInt)
/*     */   {
/* 570 */     for (int i = 0; i < this.msgb.length; i++) {
/* 571 */       if (i != paramInt) {
/* 572 */         this.msgb[i].setOutMsgType(7);
/* 573 */         this.msgb[i].setEmptyOutBody();
/* 574 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setJTree(int paramInt1, int paramInt2) {
/* 580 */     int i = this.msgb.length;
/* 581 */     Rectangle localRectangle = new Rectangle(500, 500);
/* 582 */     this.agnet.setJoinTree(this.agnet.getSkeleton(), localRectangle);
/*     */     
/* 584 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 586 */     setMsgToChd(paramInt1, paramInt2, null);
/* 587 */     for (int j = 0; j < i; j++) {
/* 588 */       if (j != paramInt1)
/* 589 */         new Envoy(this.msgb[j]);
/*     */     }
/* 591 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void setReqSetHTree(int paramInt)
/*     */   {
/* 596 */     for (int i = 0; i < this.msgb.length; i++) {
/* 597 */       if (i != paramInt) {
/* 598 */         this.msgb[i].setOutMsgType(8);
/* 599 */         this.msgb[i].setEmptyOutBody();
/* 600 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setHTree(int paramInt1, int paramInt2) {
/* 606 */     int i = this.msgb.length;
/* 607 */     Rectangle localRectangle = new Rectangle(500, 500);
/* 608 */     this.agnet.setDumbLinkageTree(i);
/*     */     
/* 610 */     for (int j = 0; j < i; j++) {
/* 611 */       Dsepset localDsepset = this.agnet.getDsepset(j);
/* 612 */       int[] arrayOfInt = localDsepset.getLocalIndex();
/* 613 */       this.agnet.setHostTree(j, arrayOfInt, localRectangle);
/*     */     }
/*     */     
/* 616 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 618 */     setMsgToChd(paramInt1, paramInt2, null);
/* 619 */     for (j = 0; j < i; j++) {
/* 620 */       if (j != paramInt1)
/* 621 */         new Envoy(this.msgb[j]);
/*     */     }
/* 623 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void setReqSetLTree(int paramInt)
/*     */   {
/* 628 */     for (int i = 0; i < this.msgb.length; i++) {
/* 629 */       if (i != paramInt) {
/* 630 */         this.msgb[i].setOutMsgType(9);
/* 631 */         this.msgb[i].setEmptyOutBody();
/* 632 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setLTree(int paramInt1, int paramInt2)
/*     */   {
/* 639 */     int i = this.msgb.length;
/* 640 */     Rectangle localRectangle = new Rectangle(500, 500);
/*     */     
/* 642 */     for (int j = 0; j < i; j++) {
/* 643 */       Dsepset localDsepset = this.agnet.getDsepset(j);
/* 644 */       int[] arrayOfInt = localDsepset.getLocalIndex();
/* 645 */       this.agnet.setLinkageTree(j, arrayOfInt, localRectangle);
/*     */     }
/*     */     
/* 648 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 650 */     setMsgToChd(paramInt1, paramInt2, null);
/* 651 */     for (j = 0; j < i; j++) {
/* 652 */       if (j != paramInt1)
/* 653 */         new Envoy(this.msgb[j]);
/*     */     }
/* 655 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqAssignDsepnode(int paramInt)
/*     */   {
/* 662 */     for (int i = 0; i < this.msgb.length; i++) {
/* 663 */       if (i != paramInt) {
/* 664 */         this.msgb[i].setOutMsgType(10);
/* 665 */         this.msgb[i].setEmptyOutBody();
/* 666 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setReqAsgnDsepndPrvPa(int paramInt, String paramString)
/*     */   {
/* 673 */     int i = this.agnet.getIndex(paramString);
/* 674 */     for (int j = 0; j < this.msgb.length; j++) {
/* 675 */       if (j != paramInt) {
/* 676 */         if (MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 677 */           this.msgb[j].setOutMsgType(11);
/* 678 */           this.msgb[j].setDumbOutBody(1);
/* 679 */           this.msgb[j].setOutBody(paramString, 0);
/* 680 */           this.msgb[j].setRecved(false);
/*     */         } else {
/* 682 */           this.msgb[j].setRecved(true);
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
/*     */   boolean asgnDsepndWithPrvParent(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 698 */     int i = this.msgb.length;
/*     */     String str;
/* 700 */     if (paramInt1 == -1) str = new String(paramString); else {
/* 701 */       str = this.msgb[paramInt1].getInBody(0);
/*     */     }
/* 703 */     int j = this.agnet.getIndex(str);
/* 704 */     int[] arrayOfInt = this.agnet.getDsepsetUnion();
/* 705 */     String[] arrayOfString = this.agnet.getLabel(arrayOfInt);
/* 706 */     if (this.agnet.hasOutParent(str, arrayOfString)) {
/* 707 */       this.agnet.setMark(j);
/* 708 */       return true;
/*     */     }
/*     */     
/* 711 */     setMsgToChd(paramInt1, paramInt2, str);
/* 712 */     for (int k = 0; k < i; k++) {
/* 713 */       if ((k != paramInt1) && 
/* 714 */         (MATH.member(j, this.agnet.getDsepsetLocalIndex(k))))
/*     */       {
/* 716 */         new Envoy(this.msgb[k]); }
/*     */     }
/* 718 */     getChdMsg(paramInt1, paramInt2);
/* 719 */     for (k = 0; k < i; k++)
/* 720 */       if ((k != paramInt1) && 
/* 721 */         (MATH.member(j, this.agnet.getDsepsetLocalIndex(k)))) {
/* 722 */         int m = Integer.parseInt(this.msgb[k].getInBody(0));
/* 723 */         if (m == 1) return true;
/*     */       }
/* 725 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqAsgnDsepndWoutPrvPa(int paramInt, String paramString)
/*     */   {
/* 731 */     int i = this.agnet.getIndex(paramString);
/* 732 */     for (int j = 0; j < this.msgb.length; j++) {
/* 733 */       if (j != paramInt) {
/* 734 */         if (MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 735 */           this.msgb[j].setOutMsgType(12);
/* 736 */           this.msgb[j].setDumbOutBody(1);
/* 737 */           this.msgb[j].setOutBody(paramString, 0);
/* 738 */           this.msgb[j].setRecved(false);
/*     */         } else {
/* 740 */           this.msgb[j].setRecved(true);
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
/*     */   void asgnDsepndWoutPrvParent(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 762 */     if (paramInt1 == -1) str = new String(paramString); else
/* 763 */       str = new String(this.msgb[paramInt1].getInBody(0));
/* 764 */     int i = this.agnet.getIndex(str);
/*     */     
/* 766 */     int j = collectPubPaInfo(-1, 0, str);
/* 767 */     if (j == -1) {
/* 768 */       this.agnet.setMark(i);return;
/*     */     }
/*     */     
/* 771 */     setMsgToChd(-1, paramInt2, str);
/* 772 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 773 */     for (int k = 0; k < this.msgb.length; k++) {
/* 774 */       if (MATH.member(i, this.agnet.getDsepsetLocalIndex(k)))
/*     */       {
/* 776 */         arrayOfEnvoy[k] = new Envoy(this.msgb[k]); }
/*     */     }
/* 778 */     getChdMsg(paramInt1, paramInt2);
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
/*     */   void assignDsepnode(int paramInt1, int paramInt2)
/*     */   {
/* 794 */     int i = this.msgb.length;
/* 795 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 797 */     int[] arrayOfInt = this.agnet.getDsepsetUnion();
/* 798 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 799 */       k = arrayOfInt[j];
/* 800 */       if ((paramInt1 == -1) || (!MATH.member(k, this.agnet.getDsepsetLocalIndex(paramInt1))))
/*     */       {
/*     */ 
/* 803 */         String str = this.agnet.getLabel(k);
/* 804 */         if (!asgnDsepndWithPrvParent(-1, 11, str))
/*     */         {
/*     */ 
/*     */ 
/* 808 */           asgnDsepndWoutPrvParent(-1, 12, str); }
/*     */       }
/*     */     }
/* 811 */     setMsgToChd(-1, paramInt2, null);
/* 812 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 813 */     for (int k = 0; k < this.msgb.length; k++) {
/* 814 */       if (k != paramInt1)
/* 815 */         arrayOfEnvoy[k] = new Envoy(this.msgb[k]);
/*     */     }
/* 817 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqCollectPubPa(int paramInt, String paramString)
/*     */   {
/* 823 */     int i = this.agnet.getIndex(paramString);
/* 824 */     for (int j = 0; j < this.msgb.length; j++) {
/* 825 */       if (j != paramInt) {
/* 826 */         if (MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 827 */           this.msgb[j].setOutMsgType(0);
/* 828 */           this.msgb[j].setDumbOutBody(1);
/* 829 */           this.msgb[j].setOutBody(paramString, 0);
/* 830 */           this.msgb[j].setRecved(false);
/*     */         } else {
/* 832 */           this.msgb[j].setRecved(true);
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
/*     */   int collectPubPaInfo(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 860 */     if (paramInt1 == -1) str = new String(paramString); else
/* 861 */       str = this.msgb[paramInt1].getInBody(0);
/* 862 */     int i = this.agnet.getIndex(str);
/*     */     
/* 864 */     int[] arrayOfInt1 = null;
/* 865 */     for (int j = 0; j < this.msgb.length; j++) {
/* 866 */       if (j != paramInt1)
/* 867 */         arrayOfInt1 = MATH.union(arrayOfInt1, this.agnet.getDsepsetLocalIndex(j));
/*     */     }
/* 869 */     if (!MATH.member(i, arrayOfInt1)) { return -1;
/*     */     }
/* 871 */     setMsgToChd(paramInt1, 0, str);
/* 872 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 873 */     for (int k = 0; k < this.msgb.length; k++) {
/* 874 */       if ((k != paramInt1) && 
/* 875 */         (MATH.member(i, this.agnet.getDsepsetLocalIndex(k))))
/*     */       {
/* 877 */         arrayOfEnvoy[k] = new Envoy(this.msgb[k]); }
/*     */     }
/* 879 */     getChdMsg(paramInt1, 0);
/* 880 */     int[] arrayOfInt2 = null;
/* 881 */     for (int m = 0; m < this.msgb.length; m++)
/* 882 */       if ((m != paramInt1) && 
/* 883 */         (MATH.member(i, this.agnet.getDsepsetLocalIndex(m))))
/*     */       {
/* 885 */         n = Integer.parseInt(this.msgb[m].getInBody(0));
/* 886 */         if (n == 0) return 0;
/* 887 */         if (n == 1) arrayOfInt2 = MATH.addMember(m, arrayOfInt2);
/*     */       }
/*     */     String[] arrayOfString2;
/* 890 */     if (arrayOfInt2 != null) {
/* 891 */       if (arrayOfInt2.length > 1) { return 0;
/*     */       }
/* 893 */       arrayOfString1 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(arrayOfInt2[0])));
/*     */       
/*     */ 
/* 896 */       for (n = 0; n < this.msgb.length; n++)
/* 897 */         if (n != arrayOfInt2[0]) {
/* 898 */           arrayOfString2 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(n)));
/*     */           
/* 900 */           if (!MATH.isSubset(arrayOfString2, arrayOfString1)) return 0;
/*     */         }
/* 902 */       return 1;
/*     */     }
/*     */     
/*     */ 
/* 906 */     if (paramInt1 == -1) { return -1;
/*     */     }
/* 908 */     String[] arrayOfString1 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(paramInt1)));
/*     */     
/*     */ 
/* 911 */     for (int n = 0; n < this.msgb.length; n++)
/* 912 */       if (n != paramInt1) {
/* 913 */         arrayOfString2 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(n)));
/*     */         
/* 915 */         if (!MATH.isSubset(arrayOfString2, arrayOfString1)) return 1;
/*     */       }
/* 917 */     return -1;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */