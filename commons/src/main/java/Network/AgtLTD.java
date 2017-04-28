/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtLTD
/*     */   extends AgtT
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  16 */   IntAgtLTD agnet = null;
/*     */   
/*     */   public static final int COLLECTFILLIN = 20;
/*     */   
/*     */   public static final int DISTRIBUTEFILLIN = 21;
/*     */   
/*     */   public static final int SETLOCALCHORDAL = 22;
/*     */   
/*     */   public static final int SETINFERENCEJT = 23;
/*     */   
/*     */   public static final int SETMESSAGEJF = 24;
/*     */   
/*     */ 
/*     */   public AgtLTD(IntAgtLTD paramIntAgtLTD, String paramString, Bridge paramBridge)
/*     */   {
/*  31 */     super(paramIntAgtLTD, paramString, paramBridge);
/*  32 */     this.agnet = paramIntAgtLTD;
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
/*  43 */     if (paramInt2 == 20) {
/*  44 */       HelpPanel.addHelp("Start collecting fillins.");
/*  45 */       collectFillin(paramInt1, paramInt2);
/*  46 */       if (paramInt1 == -1) {
/*  47 */         HelpPanel.addHelp("Root: Fillins collected.");
/*     */       } else
/*  49 */         HelpPanel.addHelp("NonRoot: Fillins collected.");
/*  50 */       this.panel.showNet();
/*  51 */       return null;
/*     */     }
/*  53 */     if (paramInt2 == 21) {
/*  54 */       HelpPanel.addHelp("Start fill-in distribution.");
/*  55 */       distributeFillin(paramInt1, paramInt2);
/*  56 */       if (paramInt1 == -1) {
/*  57 */         HelpPanel.addHelp("Root: Fillins distributed.");
/*     */       } else
/*  59 */         HelpPanel.addHelp("NonRoot: Fillins distributed.");
/*  60 */       this.panel.showNet();
/*  61 */       return null;
/*     */     }
/*  63 */     if (paramInt2 == 22) {
/*  64 */       HelpPanel.addHelp("Start setting local chordal graph.");
/*  65 */       setLocalChordal(paramInt1, paramInt2);
/*  66 */       if (paramInt1 == -1) {
/*  67 */         HelpPanel.addHelp("Root: Local chordal graph set.");
/*     */       } else
/*  69 */         HelpPanel.addHelp("NonRoot: Local chordal graph set.");
/*  70 */       setCtrlFlag("paint_mode", "undirect_graph");
/*  71 */       this.panel.showNet();
/*  72 */       setCtrlFlag("operation_stage", "triangulation");
/*  73 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  77 */     if (paramInt2 == 23) {
/*  78 */       HelpPanel.addHelp("Start constructing inference JT.");
/*  79 */       setJTree(paramInt1, paramInt2);
/*  80 */       if (paramInt1 == -1) {
/*  81 */         HelpPanel.addHelp("Root: Inference JT constructed.");
/*     */       } else
/*  83 */         HelpPanel.addHelp("NonRoot: Inference JT constructed .");
/*  84 */       setCtrlFlag("paint_mode", "join_tree");
/*  85 */       this.panel.showNet();
/*  86 */       return null;
/*     */     }
/*  88 */     if (paramInt2 == 24) {
/*  89 */       HelpPanel.addHelp("Start constructing message JFs.");
/*  90 */       setMessageJF(paramInt1, paramInt2);
/*  91 */       if (paramInt1 == -1) {
/*  92 */         HelpPanel.addHelp("Root: Message JFs constructed.");
/*     */       } else
/*  94 */         HelpPanel.addHelp("NonRoot: Message JFs\tconstructed.");
/*  95 */       setCtrlFlag("operation_stage", "link_join_trees");
/*  96 */       return null;
/*     */     }
/*  98 */     return super.actAll(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 107 */     switch (paramInt2) {
/* 108 */     case 20:  setReqCollectFillin(paramInt1); break;
/* 109 */     case 21:  setReqDistrFillin(paramInt1); break;
/* 110 */     case 22:  setReqSetLocalChordal(paramInt1); break;
/*     */     case 23: 
/* 112 */       setReqSetJTree(paramInt1); break;
/* 113 */     case 24:  setReqSetMessageJF(paramInt1); break;
/* 114 */     default:  super.setMsgToChd(paramInt1, paramInt2, paramString);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 121 */     switch (paramInt2) {
/* 122 */     case 20:  setFillinMsg(paramInt1); break;
/* 123 */     case 21:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 124 */     case 22:  this.msgb[paramInt1].setEmptyOutBody(); break;
/*     */     case 23: 
/* 126 */       this.msgb[paramInt1].setEmptyOutBody(); break;
/* 127 */     case 24:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 128 */     default:  super.setMsgToPat(paramInt1, paramInt2, paramArrayOfString);
/*     */     }
/* 130 */     this.msgb[paramInt1].setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actOnPatMsg(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 139 */     switch (paramInt2) {
/* 140 */     case 21:  addFillin(paramInt1);return null; }
/* 141 */     return super.actOnPatMsg(paramInt1, paramInt2, paramString);
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
/*     */   void addFillin(int paramInt)
/*     */   {
/* 156 */     String[] arrayOfString = this.msgb[paramInt].getInBody();
/* 157 */     if (arrayOfString == null) return;
/* 158 */     String[][] arrayOfString1 = new String[arrayOfString.length / 2][2];
/* 159 */     for (int i = 0; i < arrayOfString1.length; i++) {
/* 160 */       arrayOfString1[i][0] = arrayOfString[(2 * i)];
/* 161 */       arrayOfString1[i][1] = arrayOfString[(2 * i + 1)];
/*     */     }
/* 163 */     this.agnet.pickAddFillIn(arrayOfString1);
/* 164 */     for (i = 0; i < this.msgb.length; i++) {
/* 165 */       if (i != paramInt) {
/* 166 */         this.agnet.pickAddFillIn(arrayOfString1, i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getNewFillin(int paramInt)
/*     */   {
/* 173 */     int[] arrayOfInt = this.agnet.getDsepsetLocalIndex(paramInt);
/* 174 */     this.agnet.setChordalGraph(paramInt, arrayOfInt);
/* 175 */     String[][] arrayOfString = this.agnet.nodesToLinksLabel(paramInt, arrayOfInt);
/* 176 */     if (arrayOfString == null) return null;
/* 177 */     String[] arrayOfString1 = new String[arrayOfString.length * 2];
/* 178 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 179 */       arrayOfString1[(2 * i)] = arrayOfString[i][0];
/* 180 */       arrayOfString1[(2 * i + 1)] = arrayOfString[i][1];
/*     */     }
/* 182 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */ 
/*     */   void setFillinMsg(int paramInt)
/*     */   {
/* 188 */     this.msgb[paramInt].setOutBody(getNewFillin(paramInt));
/*     */   }
/*     */   
/*     */   void setReqCollectFillin(int paramInt)
/*     */   {
/* 193 */     for (int i = 0; i < this.msgb.length; i++) {
/* 194 */       if (i != paramInt) {
/* 195 */         this.msgb[i].setOutMsgType(20);
/* 196 */         this.msgb[i].setEmptyOutBody();
/* 197 */         this.msgb[i].setRecved(false);
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
/*     */   void collectFillin(int paramInt1, int paramInt2)
/*     */   {
/* 215 */     this.agnet.setMsgGraph();
/*     */     
/* 217 */     int i = this.msgb.length;
/* 218 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 220 */     setMsgToChd(paramInt1, paramInt2, null);
/* 221 */     for (int j = 0; j < i; j++) {
/* 222 */       if (j != paramInt1)
/* 223 */         new Envoy(this.msgb[j]);
/*     */     }
/* 225 */     getChdMsg(paramInt1, paramInt2);
/* 226 */     for (j = 0; j < i; j++) {
/* 227 */       if (j != paramInt1) {
/* 228 */         addFillin(j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqDistrFillin(int paramInt)
/*     */   {
/* 237 */     for (int i = 0; i < this.msgb.length; i++) {
/* 238 */       if (i != paramInt) {
/* 239 */         this.msgb[i].setOutMsgType(21);
/* 240 */         this.msgb[i].setOutBody(getNewFillin(i));
/* 241 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeFillin(int paramInt1, int paramInt2)
/*     */   {
/* 253 */     int i = this.msgb.length;
/* 254 */     if (paramInt1 != -1) actOnPatMsg(paramInt1, paramInt2, null);
/* 255 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 257 */     setMsgToChd(paramInt1, paramInt2, null);
/* 258 */     for (int j = 0; j < i; j++) {
/* 259 */       if (j != paramInt1)
/* 260 */         new Envoy(this.msgb[j]);
/*     */     }
/* 262 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqSetLocalChordal(int paramInt)
/*     */   {
/* 270 */     for (int i = 0; i < this.msgb.length; i++) {
/* 271 */       if (i != paramInt) {
/* 272 */         this.msgb[i].setOutMsgType(22);
/* 273 */         this.msgb[i].setEmptyOutBody();
/* 274 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setLocalChordal(int paramInt1, int paramInt2)
/*     */   {
/* 282 */     this.agnet.setChordalGraph();
/*     */     
/* 284 */     int i = this.msgb.length;
/* 285 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 287 */     setMsgToChd(paramInt1, paramInt2, null);
/* 288 */     for (int j = 0; j < i; j++) {
/* 289 */       if (j != paramInt1)
/* 290 */         new Envoy(this.msgb[j]);
/*     */     }
/* 292 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqSetJTree(int paramInt)
/*     */   {
/* 299 */     for (int i = 0; i < this.msgb.length; i++) {
/* 300 */       if (i != paramInt) {
/* 301 */         this.msgb[i].setOutMsgType(23);
/* 302 */         this.msgb[i].setEmptyOutBody();
/* 303 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setJTree(int paramInt1, int paramInt2) {
/* 309 */     int i = this.msgb.length;
/* 310 */     Rectangle localRectangle = new Rectangle(500, 500);
/* 311 */     this.agnet.setJoinTree(localRectangle);
/*     */     
/* 313 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 315 */     setMsgToChd(paramInt1, paramInt2, null);
/* 316 */     for (int j = 0; j < i; j++) {
/* 317 */       if (j != paramInt1)
/* 318 */         new Envoy(this.msgb[j]);
/*     */     }
/* 320 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqSetMessageJF(int paramInt)
/*     */   {
/* 329 */     for (int i = 0; i < this.msgb.length; i++) {
/* 330 */       if (i != paramInt) {
/* 331 */         this.msgb[i].setOutMsgType(24);
/* 332 */         this.msgb[i].setEmptyOutBody();
/* 333 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setMessageJF(int paramInt1, int paramInt2) {
/* 339 */     this.agnet.setMessageJF();
/* 340 */     int i = this.msgb.length;
/* 341 */     for (int j = 0; j < i; j++) {
/* 342 */       HelpPanel.addHelp("  Setting " + j + "'th Msg\tJF ...");
/* 343 */       this.agnet.setMessageJF(j);
/*     */     }
/*     */     
/*     */ 
/* 347 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 349 */     HelpPanel.addHelp("\t Asking\tchild agents to\tset Msg\tJF ...");
/* 350 */     setMsgToChd(paramInt1, paramInt2, null);
/* 351 */     for (j = 0; j < i; j++) {
/* 352 */       if (j != paramInt1)
/* 353 */         new Envoy(this.msgb[j]);
/*     */     }
/* 355 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void addFillinToMsgGraph()
/*     */   {
/* 363 */     int i = this.msgb.length;
/* 364 */     for (int j = 0; j < i; j++) {
/* 365 */       System.out.println("Msg Graph [" + getName() + "->" + getNbrName(j) + "]:");
/* 366 */       System.out.print("Add fillin to this msg graph [0,1]? ");
/* 367 */       while (UTIL.readInt() == 1) {
/* 368 */         System.out.print("Enter\tendpoints x y: ");
/* 369 */         Point localPoint = UTIL.readPoint();
/* 370 */         System.out.println("Entered: " + localPoint);
/* 371 */         this.agnet.addFillinToMsgGraph(j, localPoint);
/* 372 */         System.out.print("Add fillin to\tthis msg graph [0,1]? ");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void addFillinToLocalGraph()
/*     */   {
/* 380 */     System.out.println("Local Graph [" + getName() + "]:");
/* 381 */     System.out.print("Add fillin to this graph [0,1]? ");
/* 382 */     while (UTIL.readInt() == 1) {
/* 383 */       System.out.print("Enter endpoints\tx y: ");
/* 384 */       Point localPoint = UTIL.readPoint();
/* 385 */       System.out.println("Entered: " + localPoint);
/* 386 */       this.agnet.addFillinToLocalGraph(localPoint);
/* 387 */       System.out.print("Add fillin to this graph [0,1]?\t");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtLTD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */