/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class BNode
/*     */   extends TNode
/*     */ {
/*  22 */   String[] state = { "false", "true" };
/*  23 */   int[] pScnt = null;
/*  24 */   float[] cpls = { 0.5F, 0.5F };
/*  25 */   float[] mpls = { 0.5F, 0.5F };
/*  26 */   int value = -1;
/*     */   
/*  28 */   String[] expn = { "m" };
/*     */   
/*     */   public BNode() {}
/*     */   
/*     */   public BNode(BNode paramBNode)
/*     */   {
/*  34 */     super(paramBNode);
/*  35 */     this.state = UTIL.getDuplicate(paramBNode.getState());
/*  36 */     this.pScnt = UTIL.getDuplicate(paramBNode.getParentStateCount());
/*  37 */     this.cpls = UTIL.getDuplicate(paramBNode.getCondProb());
/*  38 */     this.mpls = UTIL.getDuplicate(paramBNode.getMarginalProb());
/*     */   }
/*     */   
/*     */   BNode(XNode paramXNode) {
/*  42 */     setPos(paramXNode.getPos());
/*  43 */     setLabel(paramXNode.getLabel());
/*  44 */     this.state = UTIL.getDuplicate(paramXNode.getState());
/*  45 */     this.pls = UTIL.getDuplicate(paramXNode.getParent());
/*  46 */     this.cls = UTIL.getDuplicate(paramXNode.getChild());
/*     */   }
/*     */   
/*  49 */   public BNode(DNode paramDNode) { if (paramDNode.pls != null) {
/*  50 */       int i = paramDNode.pls.length;
/*  51 */       this.pScnt = new int[i];
/*  52 */       for (int j = 0; j < i; j++) this.pScnt[j] = 2;
/*  53 */       this.pls = UTIL.getDuplicate(paramDNode.pls);
/*     */     }
/*  55 */     if (paramDNode.cls != null) {
/*  56 */       this.cls = UTIL.getDuplicate(paramDNode.cls);
/*     */     }
/*  58 */     setPos(paramDNode.getPos());
/*  59 */     setLabel(paramDNode.getLabel());
/*  60 */     setCondProb(this.cpls);
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
/*     */   void setState(String[] paramArrayOfString)
/*     */   {
/*  74 */     this.state = UTIL.getDuplicate(paramArrayOfString);
/*     */   }
/*     */   
/*     */   void setState() {
/*  78 */     String[] arrayOfString = { "yes", "no" };
/*  79 */     this.state = arrayOfString;
/*     */   }
/*     */   
/*     */   int getStateCount()
/*     */   {
/*  84 */     return this.state.length;
/*     */   }
/*     */   
/*     */   int getStateIndex(String paramString)
/*     */   {
/*  89 */     for (int i = 0; i < this.state.length; i++) if (paramString.equals(this.state[i])) return i;
/*  90 */     return -1;
/*     */   }
/*     */   
/*     */   String getState(int paramInt)
/*     */   {
/*  95 */     return new String(this.state[paramInt]);
/*     */   }
/*     */   
/*     */   String[] getState() {
/*  99 */     return UTIL.getDuplicate(this.state);
/*     */   }
/*     */   
/*     */   char[] getStateInit()
/*     */   {
/* 104 */     char[] arrayOfChar = new char[this.state.length];
/* 105 */     for (int i = 0; i < this.state.length; i++) arrayOfChar[i] = this.state[i].charAt(0);
/* 106 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   void setParentStateCount(int[] paramArrayOfInt)
/*     */   {
/* 111 */     this.pScnt = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getParentStateCount()
/*     */   {
/* 116 */     return UTIL.getDuplicate(this.pScnt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[] getFamilyStateCount()
/*     */   {
/* 123 */     return UTIL.appendToArray(this.pScnt, getStateCount());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getVarValue()
/*     */   {
/* 130 */     return this.value;
/*     */   }
/*     */   
/*     */   String getStrValue()
/*     */   {
/* 135 */     return this.state[this.value];
/*     */   }
/*     */   
/*     */   void setVarValue(int paramInt)
/*     */   {
/* 140 */     if ((paramInt >= 0) && (paramInt < this.state.length)) this.value = paramInt; else {
/* 141 */       HelpPanel.addHelp("Invalid variable value!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setVarValue()
/*     */   {
/* 149 */     for (int i = 0; i < this.state.length; i++) {
/* 150 */       if (this.mpls[i] > 0.999D) {
/* 151 */         this.value = i;return;
/*     */       }
/*     */     }
/* 154 */     this.value = -1;
/*     */   }
/*     */   
/*     */   void delVarValue()
/*     */   {
/* 159 */     this.value = -1;
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
/*     */   void initExpn()
/*     */   {
/* 182 */     int i = 1;
/* 183 */     int j = this.pls == null ? 0 : this.pls.length;
/* 184 */     for (int k = 0; k < j; k++) { i *= this.pScnt[k];
/*     */     }
/* 186 */     this.expn = new String[i];
/* 187 */     for (k = 0; k < i; k++) this.expn[k] = "m";
/*     */   }
/*     */   
/*     */   String[] getExpn()
/*     */   {
/* 192 */     return this.expn;
/*     */   }
/*     */   
/*     */   void setExpn(String[] paramArrayOfString)
/*     */   {
/* 197 */     this.expn = paramArrayOfString;
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
/*     */   void setCptByExpn()
/*     */   {
/* 211 */     if (this.expn == null) {
/* 212 */       HelpPanel.showError("Expression undefined!");
/* 213 */       return;
/*     */     }
/* 215 */     int i = 1;
/* 216 */     int j = this.pls == null ? 0 : this.pls.length;
/* 217 */     for (int k = 0; k < j; k++) { i *= this.pScnt[k];
/*     */     }
/* 219 */     k = this.state.length;
/* 220 */     float[] arrayOfFloat1 = null;
/*     */     
/*     */ 
/* 223 */     if (this.expn[0].startsWith("sub")) {
/* 224 */       float[] arrayOfFloat2 = UTIL.getRealList(this.expn[0], 2, 1);
/* 225 */       setMinusCpt((int)arrayOfFloat2[0], (int)arrayOfFloat2[1]);
/* 226 */       return;
/*     */     }
/*     */     
/*     */ 
/* 230 */     for (int m = 0; m < i; m++) {
/* 231 */       if (this.expn[m].startsWith("r")) {
/* 232 */         arrayOfFloat1 = setRandCpd();
/* 233 */         System.arraycopy(arrayOfFloat1, 0, this.cpls, m * k, k);
/* 234 */         this.expn[m] = "m";
/*     */       }
/* 236 */       else if (this.expn[m].startsWith("u")) {
/* 237 */         arrayOfFloat1 = setUniformCpd();
/* 238 */         System.arraycopy(arrayOfFloat1, 0, this.cpls, m * k, k);
/*     */       } else { float[] arrayOfFloat3;
/* 240 */         if (this.expn[m].startsWith("tn")) {
/* 241 */           arrayOfFloat3 = UTIL.getRealList(this.expn[m], 2, 1);
/* 242 */           float f1 = arrayOfFloat3[0];float f2 = arrayOfFloat3[1];
/* 243 */           float f3 = Float.valueOf(this.state[0]).floatValue();
/* 244 */           float f4 = Float.valueOf(this.state[(k - 1)]).floatValue();
/* 245 */           float f5 = f3 + (f4 - f3) * k / (k - 1);
/* 246 */           if ((f1 < f3) || (f1 > f5) || (f2 > f5 - f3)) {
/* 247 */             HelpPanel.showError("Invalid mu and sig for the variable!");
/* 248 */             return;
/*     */           }
/* 250 */           arrayOfFloat1 = setTnormCpd(f1, f2, f3, f5);
/* 251 */           System.arraycopy(arrayOfFloat1, 0, this.cpls, m * k, k);
/*     */         }
/* 253 */         else if (this.expn[m].startsWith("bi")) {
/* 254 */           arrayOfFloat3 = UTIL.getRealList(this.expn[m], 2, 1);
/* 255 */           arrayOfFloat1 = setBinoCpd((int)arrayOfFloat3[0], arrayOfFloat3[1]);
/* 256 */           System.arraycopy(arrayOfFloat1, 0, this.cpls, m * k, k);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   float[] setRandCpd()
/*     */   {
/* 264 */     int i = this.state.length;
/* 265 */     float[] arrayOfFloat = new float[i];
/* 266 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = ((float)Math.random());
/* 267 */     return MATH.normalize(arrayOfFloat, i);
/*     */   }
/*     */   
/*     */   float[] setUniformCpd()
/*     */   {
/* 272 */     int i = this.state.length;
/* 273 */     float[] arrayOfFloat = new float[i];
/* 274 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = (1.0F / i);
/* 275 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setTnormCpd(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 284 */     int i = this.state.length;
/* 285 */     float[] arrayOfFloat1 = new float[i + 1];
/* 286 */     float f = (paramFloat4 - paramFloat3) / i;
/* 287 */     for (int j = 0; j <= i; j++) arrayOfFloat1[j] = MATH.normal(paramFloat1, paramFloat2, paramFloat3 + j * f);
/* 288 */     float[] arrayOfFloat2 = new float[i];
/* 289 */     for (int k = 0; k < i; k++) arrayOfFloat2[k] = ((arrayOfFloat1[k] + arrayOfFloat1[(k + 1)]) / 2.0F);
/* 290 */     return MATH.normalize(arrayOfFloat2, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setBinoCpd(int paramInt, float paramFloat)
/*     */   {
/* 298 */     int i = this.state.length;
/* 299 */     if (i <= paramInt) {
/* 300 */       HelpPanel.showError("Invalid n value: " + paramInt);
/* 301 */       return setUniformCpd();
/*     */     }
/* 303 */     float[] arrayOfFloat = new float[i];
/* 304 */     for (int j = 0; j <= paramInt; j++) arrayOfFloat[j] = MATH.binomial(paramInt, paramFloat, j);
/* 305 */     for (j = paramInt + 1; j < i; j++) arrayOfFloat[j] = 0.0F;
/* 306 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setMinusCpd(int paramInt1, int paramInt2)
/*     */   {
/* 315 */     if (paramInt1 < paramInt2) { return setUniformCpd();
/*     */     }
/* 317 */     int i = this.state.length;
/* 318 */     float[] arrayOfFloat = new float[i];
/* 319 */     for (int j = 0; j < i; j++) {
/* 320 */       int k = Integer.parseInt(this.state[j]);
/* 321 */       if (k == paramInt1 - paramInt2) arrayOfFloat[j] = 1.0F; else
/* 322 */         arrayOfFloat[j] = 0.0F;
/*     */     }
/* 324 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setMinusCpt(int paramInt1, int paramInt2)
/*     */   {
/* 334 */     if (this.pls.length != 2) {
/* 335 */       HelpPanel.showError("Invalid request");setUniformCpd();return;
/*     */     }
/* 337 */     int i = paramInt1 * paramInt2;
/* 338 */     int[] arrayOfInt1 = { paramInt1, paramInt2 };
/* 339 */     int j = this.state.length;
/*     */     
/* 341 */     for (int k = 0; k < i; k++) {
/* 342 */       int[] arrayOfInt2 = MATH.decToMix(k, arrayOfInt1);
/* 343 */       float[] arrayOfFloat = setMinusCpd(arrayOfInt2[0], arrayOfInt2[1]);
/* 344 */       System.arraycopy(arrayOfFloat, 0, this.cpls, k * j, j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setCondProb(float[] paramArrayOfFloat)
/*     */   {
/* 353 */     this.cpls = MATH.normalize(paramArrayOfFloat, this.state.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setCondProb()
/*     */   {
/* 365 */     setUniformCpt();
/*     */   }
/*     */   
/*     */ 
/*     */   void setUniformCpt()
/*     */   {
/* 371 */     int i = this.state.length;
/* 372 */     int j = i;
/* 373 */     int k = this.pls == null ? 0 : this.pls.length;
/* 374 */     for (int m = 0; m < k; m++) j *= this.pScnt[m];
/* 375 */     this.cpls = new float[j];
/* 376 */     for (m = 0; m < j; m++) { this.cpls[m] = (1.0F / i);
/*     */     }
/*     */   }
/*     */   
/*     */   void setRandCondProb(Random paramRandom)
/*     */   {
/* 382 */     int i = this.state.length;
/* 383 */     int j = this.pls == null ? 0 : this.pls.length;
/* 384 */     for (int k = 0; k < j; k++) i *= this.pScnt[k];
/* 385 */     this.cpls = new float[i];
/*     */     do
/*     */     {
/* 388 */       for (k = 0; k < i; k++) this.cpls[k] = paramRandom.nextFloat();
/* 389 */       setCondProb(this.cpls);
/* 390 */     } while (!isDependent());
/*     */   }
/*     */   
/*     */   void setRandCondProb()
/*     */   {
/* 395 */     int i = this.state.length;
/* 396 */     int j = this.pls == null ? 0 : this.pls.length;
/* 397 */     for (int k = 0; k < j; k++) i *= this.pScnt[k];
/* 398 */     this.cpls = new float[i];
/*     */     do
/*     */     {
/* 401 */       for (k = 0; k < i; k++) this.cpls[k] = ((float)Math.random());
/* 402 */       setCondProb(this.cpls);
/* 403 */     } while (!isDependent());
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
/*     */ 
/*     */ 
/*     */   boolean isDependent()
/*     */   {
/* 428 */     int i = this.state.length;
/* 429 */     int j = this.pls == null ? 0 : this.pls.length;
/* 430 */     int k = i;
/* 431 */     for (int m = 0; m < j; m++) { k *= this.pScnt[m];
/*     */     }
/* 433 */     m = 1;
/* 434 */     int n = 0;
/*     */     
/* 436 */     for (int i1 = 0; i1 < j; i1++) {
/* 437 */       m *= this.pScnt[(j - i1 - 1)];
/* 438 */       int i2 = i * this.pScnt[(j - i1 - 1)];
/* 439 */       int i3 = k / i2;
/* 440 */       int i4 = 0;
/*     */       
/*     */ 
/* 443 */       for (int i5 = 0; i5 < i3; i5++)
/*     */       {
/*     */ 
/* 446 */         for (int i6 = 1; i6 < this.pScnt[(j - i1 - 1)]; i6++)
/*     */         {
/*     */ 
/* 449 */           for (int i7 = 0; i7 < i; i7++) {
/* 450 */             if (this.cpls[(i5 * i2 + i6 * i + i7 + n)] != this.cpls[(i5 * i2 + i7)]) {
/* 451 */               i4 = 1;
/* 452 */               break;
/*     */             }
/*     */           }
/* 455 */           if (i4 == 1) break;
/*     */         }
/* 457 */         if (i4 == 1) break;
/*     */       }
/* 459 */       if (i4 == 0) return false;
/* 460 */       n = i * m;
/*     */     }
/*     */     
/* 463 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   float getCondProb(int paramInt)
/*     */   {
/* 469 */     return this.cpls[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */   float getCondProb(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 475 */     int[] arrayOfInt1 = UTIL.appendToArray(paramArrayOfInt, paramInt);
/* 476 */     int[] arrayOfInt2 = UTIL.appendToArray(this.pScnt, this.state.length);
/* 477 */     return this.cpls[MATH.mixToDec(arrayOfInt1, arrayOfInt2)];
/*     */   }
/*     */   
/*     */ 
/*     */   float getCondProb(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 483 */     int[] arrayOfInt = MATH.getSubsetDimen(paramArrayOfInt1, paramArrayOfInt2, this.pls);
/* 484 */     return getCondProb(paramInt, arrayOfInt);
/*     */   }
/*     */   
/*     */   float[] getCondProb() {
/* 488 */     return UTIL.getDuplicate(this.cpls);
/*     */   }
/*     */   
/*     */   float[] refCondProb() {
/* 492 */     return this.cpls;
/*     */   }
/*     */   
/*     */   int getCptSize()
/*     */   {
/* 497 */     return this.cpls.length;
/*     */   }
/*     */   
/*     */ 
/*     */   float[] getCondProb(int[] paramArrayOfInt)
/*     */   {
/* 503 */     if (paramArrayOfInt == null) { return this.cpls;
/*     */     }
/* 505 */     int[] arrayOfInt1 = UTIL.appendToArray(paramArrayOfInt, 0);
/* 506 */     int[] arrayOfInt2 = getFamilyStateCount();
/*     */     
/* 508 */     int i = getStateCount();
/* 509 */     float[] arrayOfFloat = new float[i];
/* 510 */     int j = MATH.mixToDec(arrayOfInt1, arrayOfInt2);
/* 511 */     for (int k = 0; k < i; k++) arrayOfFloat[k] = this.cpls[(j + k)];
/* 512 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getMostProbable(int[] paramArrayOfInt)
/*     */   {
/* 519 */     int[] arrayOfInt1 = UTIL.appendToArray(paramArrayOfInt, 0);
/* 520 */     int[] arrayOfInt2 = getFamilyStateCount();
/* 521 */     int i = MATH.mixToDec(arrayOfInt1, arrayOfInt2);
/* 522 */     int j = getStateCount();
/*     */     
/* 524 */     int k = 0;
/* 525 */     float f = this.cpls[i];
/* 526 */     for (int m = 1; m < j; m++) {
/* 527 */       if (this.cpls[(i + m)] > f) {
/* 528 */         k = m;f = this.cpls[(i + m)];
/*     */       }
/*     */     }
/* 531 */     return k;
/*     */   }
/*     */   
/*     */   void setMarginalProb(float[] paramArrayOfFloat)
/*     */   {
/* 536 */     this.mpls = UTIL.getDuplicate(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   float[] getMarginalProb()
/*     */   {
/* 541 */     return UTIL.getDuplicate(this.mpls);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void addBParent(int paramInt1, int paramInt2)
/*     */   {
/* 549 */     addParent(paramInt1);
/*     */     
/* 551 */     int i = this.pls == null ? 0 : this.pls.length;
/* 552 */     int[] arrayOfInt = new int[i];
/* 553 */     for (int j = 0; j < i; j++) {
/* 554 */       if (this.pls[j] < paramInt1) { arrayOfInt[j] = this.pScnt[j];
/* 555 */       } else if (this.pls[j] > paramInt1) arrayOfInt[j] = this.pScnt[(j - 1)]; else
/* 556 */         arrayOfInt[j] = paramInt2;
/*     */     }
/* 558 */     this.pScnt = arrayOfInt;
/*     */     
/* 560 */     setCondProb();
/* 561 */     initExpn();
/*     */   }
/*     */   
/*     */ 
/*     */   void delBParent(int paramInt)
/*     */   {
/* 567 */     delParent(paramInt);
/*     */     
/* 569 */     int i = this.pls == null ? 0 : this.pls.length;
/* 570 */     int[] arrayOfInt = new int[i];
/* 571 */     for (int j = 0; j < i; j++) {
/* 572 */       if (this.pls[j] < paramInt) { arrayOfInt[j] = this.pScnt[j];
/* 573 */       } else if (this.pls[j] > paramInt) arrayOfInt[j] = this.pScnt[(j + 1)]; else
/* 574 */         HelpPanel.addHelp("This parent should have been deleted!");
/*     */     }
/* 576 */     this.pScnt = arrayOfInt;
/*     */     
/* 578 */     setCondProb();
/* 579 */     initExpn();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadState(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 586 */     if (paramInt < 2) {
/* 587 */       HelpPanel.showError("Incorrect state count!");return;
/*     */     }
/* 589 */     setState(UTIL.loadStringList(paramBufferedReader, paramInt));
/*     */   }
/*     */   
/*     */   static BNode loadSkipPb(BufferedReader paramBufferedReader)
/*     */   {
/* 594 */     BNode localBNode = new BNode();
/* 595 */     int i = UTIL.loadInt(paramBufferedReader);
/* 596 */     localBNode.loadLabel(paramBufferedReader);
/* 597 */     localBNode.loadState(paramBufferedReader, i);
/* 598 */     localBNode.loadCP(paramBufferedReader);
/* 599 */     localBNode.loadPos(paramBufferedReader);
/* 600 */     localBNode.skipCondProb(paramBufferedReader);
/* 601 */     return localBNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadCondProb(BufferedReader paramBufferedReader)
/*     */   {
/* 613 */     String str = UTIL.loadStringLine(paramBufferedReader);
/* 614 */     StringTokenizer localStringTokenizer = new StringTokenizer(str);
/* 615 */     int i = Integer.parseInt(localStringTokenizer.nextToken());
/*     */     
/* 617 */     String[] arrayOfString = str.split(";");
/* 618 */     int j = arrayOfString.length - 1;
/* 619 */     int k; if (j == 0) {
/* 620 */       k = i / getStateCount();
/* 621 */       this.expn = new String[k];
/* 622 */       for (int m = 0; m < k; m++) this.expn[m] = "m";
/*     */     }
/*     */     else {
/* 625 */       this.expn = new String[j];
/* 626 */       for (k = 0; k < j; k++) { this.expn[k] = arrayOfString[(k + 1)];
/*     */       }
/*     */     }
/* 629 */     setCondProb(UTIL.loadRealList(paramBufferedReader, i));
/*     */   }
/*     */   
/*     */   void skipCondProb(BufferedReader paramBufferedReader)
/*     */   {
/* 634 */     int i = UTIL.loadInt(paramBufferedReader);
/* 635 */     UTIL.skipRealList(paramBufferedReader, i);
/*     */   }
/*     */   
/*     */ 
/*     */   static BNode load(BufferedReader paramBufferedReader)
/*     */   {
/* 641 */     BNode localBNode = new BNode();
/* 642 */     int i = UTIL.loadInt(paramBufferedReader);
/* 643 */     localBNode.loadLabel(paramBufferedReader);
/* 644 */     localBNode.loadState(paramBufferedReader, i);
/* 645 */     localBNode.loadCP(paramBufferedReader);
/* 646 */     localBNode.loadPos(paramBufferedReader);
/* 647 */     localBNode.loadCondProb(paramBufferedReader);
/* 648 */     return localBNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void saveStateCount(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 655 */     paramPrintWriter.println(this.state.length + "  #_of_states_var_" + paramInt);
/*     */   }
/*     */   
/*     */   void saveState(PrintWriter paramPrintWriter)
/*     */   {
/* 660 */     UTIL.saveStringList(paramPrintWriter, this.state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void saveCondProb(PrintWriter paramPrintWriter)
/*     */   {
/* 668 */     paramPrintWriter.print(this.cpls.length + "  cpt_size+expns; ");
/* 669 */     for (int i = 0; i < this.expn.length; i++) paramPrintWriter.print(this.expn[i] + ";");
/* 670 */     paramPrintWriter.println("");
/* 671 */     UTIL.saveRealList(paramPrintWriter, this.cpls);
/*     */   }
/*     */   
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 676 */     saveStateCount(paramPrintWriter, paramInt);
/* 677 */     saveLabel(paramPrintWriter, paramInt);
/* 678 */     saveState(paramPrintWriter);
/* 679 */     saveCP(paramPrintWriter);
/* 680 */     savePos(paramPrintWriter);
/* 681 */     saveCondProb(paramPrintWriter);
/*     */   }
/*     */   
/*     */ 
/*     */   public void showBNode(int paramInt)
/*     */   {
/* 687 */     System.out.println("  [BNode]");
/* 688 */     UTIL.showList("nd " + paramInt + " " + getLabel() + ": state=", this.state);
/* 689 */     UTIL.showList("  pls=", this.pls);
/* 690 */     UTIL.showList("  cls=", this.cls);
/* 691 */     UTIL.showList("  expn=", this.expn);
/* 692 */     UTIL.showList("  cpls=", this.cpls);
/* 693 */     UTIL.showList("  pScnt=", this.pScnt);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/BNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */