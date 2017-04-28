/*     */ package Network;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
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
/*     */ public class DesNetPers
/*     */   extends DesignNet
/*     */ {
/*  97 */   String[] agtnm = null;
/*  98 */   Point[] loc = null;
/*  99 */   Point[] exloc = null;
/*     */   
/* 101 */   final String[] pos1 = { "(0,0)", "(1,0)", "(-1,0)", "(0,1)", "(0,-1)" };
/*     */   
/* 103 */   final String[] pos2 = { "(0,0)", "(1,0)", "(-1,0)", "(0,1)", "(0,-1)", "(2,0)", "(-2,0)", "(0,2)", "(0,-2)", "(1,1)", "(-1,1)", "(1,-1)", "(-1,-1)" };
/*     */   
/*     */ 
/*     */ 
/* 107 */   Point[] ppt2 = null;
/*     */   
/* 109 */   float[][] vloc = (float[][])null;
/*     */   
/*     */ 
/*     */ 
/* 113 */   boolean meet = false;
/* 114 */   char gdir = 'H';
/*     */   
/* 116 */   int self = -1;
/* 117 */   String[][] da = (String[][])null;
/* 118 */   final String[][] db = { { "E", "W", "N", "S", "H" }, { "E", "W", "N", "S" } };
/*     */   
/*     */ 
/* 121 */   float p1 = 0.84F;
/*     */   
/*     */ 
/*     */   DesNetPers(DesignNet paramDesignNet)
/*     */   {
/* 126 */     super(paramDesignNet);
/* 127 */     int[] arrayOfInt = paramDesignNet.getUtilNode();
/* 128 */     float[] arrayOfFloat = paramDesignNet.getWeight();
/* 129 */     setWeight(arrayOfInt, arrayOfFloat);
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/* 134 */     Frame localFrame = new Frame();
/* 135 */     localFrame.setSize(800, 130);
/* 136 */     HelpPanel localHelpPanel = new HelpPanel();
/* 137 */     localFrame.add("North", localHelpPanel);
/* 138 */     localFrame.setVisible(true);
/*     */     
/* 140 */     DesNetPers localDesNetPers = new DesNetPers(DesignNet.loadDn(paramArrayOfString[0] + ".dn"));
/* 141 */     localDesNetPers.update(paramArrayOfString[0], false);
/* 142 */     localDesNetPers.save(paramArrayOfString[0] + ".dn");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void update(String paramString, boolean paramBoolean)
/*     */   {
/* 150 */     int i = this.pos2.length;
/* 151 */     this.ppt2 = new Point[i];
/* 152 */     for (int j = 0; j < i; j++) { this.ppt2[j] = getxy(this.pos2[j]);
/*     */     }
/* 154 */     HelpPanel.addHelp("Loading file " + paramString + ".wrd");
/* 155 */     loadWorld(paramString + ".wrd");
/* 156 */     HelpPanel.showList("  Agt names = ", this.agtnm);
/* 157 */     HelpPanel.showList("  Agt locations = ", this.loc);
/* 158 */     HelpPanel.addHelp("  Index of this agt = " + this.self);
/* 159 */     HelpPanel.showList("  Locations of nearby ex-group agts = ", this.exloc);
/* 160 */     HelpPanel.showList("  Local area = ", this.pos2);
/* 161 */     HelpPanel.addHelp("  Area reward = ");
/* 162 */     HelpPanel.showList("  ", this.vloc);
/* 163 */     HelpPanel.addHelp("  Prob of intended action = " + this.p1);
/*     */     
/* 165 */     HelpPanel.addHelp("Set group direction");
/* 166 */     setGroupDirection();
/* 167 */     HelpPanel.addHelp("  Group direction = " + this.gdir);
/*     */     
/* 169 */     if (paramBoolean) {
/* 170 */       setJumpTarget(this.gdir);
/* 171 */       HelpPanel.addHelp("  Modified area reward = ");
/* 172 */       HelpPanel.showList("  ", this.vloc);
/*     */     }
/*     */     
/* 175 */     HelpPanel.addHelp("Set action/location domain");
/* 176 */     setVarDomain();
/* 177 */     HelpPanel.showList("  Agt0/2 act 1: ", this.da[0]);
/* 178 */     HelpPanel.showList("  Agt0/2 act 2: ", this.da[1]);
/* 179 */     HelpPanel.showList("  Agt1 act 1: ", this.db[0]);
/* 180 */     HelpPanel.showList("  Agt1 act 2: ", this.db[1]);
/* 181 */     HelpPanel.showList("  Location domain aft act 1: ", this.pos1);
/* 182 */     HelpPanel.showList("  Location domain aft act 2: ", this.pos2);
/*     */     
/* 184 */     if (this.self <= 1) {
/* 185 */       setState(getIndex("AB_A1"), this.da[0]);
/* 186 */       setState(getIndex("AB_A2"), this.da[1]);
/*     */     }
/* 188 */     if (this.self >= 1) {
/* 189 */       setState(getIndex("BC_C1"), this.da[0]);
/* 190 */       setState(getIndex("BC_C2"), this.da[1]);
/*     */     }
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
/* 230 */     float[] arrayOfFloat1 = null;
/* 231 */     int k; if (this.self == 0) {
/* 232 */       setCondProb(getIndex("A_Axy1"), setCpt(this.pos1, this.da[0]));
/* 233 */       setCondProb(getIndex("A_Bxy1"), setCpt(this.pos1, this.db[0]));
/*     */       
/* 235 */       k = getIndex("A_Axy2");
/* 236 */       localObject = getLabel(getParent(k, 0));
/* 237 */       if (((String)localObject).equals("A_Axy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.da[1]); else
/* 238 */         arrayOfFloat1 = setCpt2(this.pos2, this.da[1], this.pos1);
/* 239 */       setCondProb(k, arrayOfFloat1);
/*     */       
/* 241 */       k = getIndex("A_Bxy2");
/* 242 */       localObject = getLabel(getParent(k, 0));
/* 243 */       if (((String)localObject).equals("A_Bxy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.db[1]); else
/* 244 */         arrayOfFloat1 = setCpt2(this.pos2, this.db[1], this.pos1);
/* 245 */       setCondProb(k, arrayOfFloat1);
/*     */     }
/* 247 */     else if (this.self == 1) {
/* 248 */       setCondProb(getIndex("B_Axy1"), setCpt(this.pos1, this.da[0]));
/* 249 */       setCondProb(getIndex("B_Bxy1"), setCpt(this.pos1, this.db[0]));
/* 250 */       setCondProb(getIndex("B_Cxy1"), setCpt(this.pos1, this.da[0]));
/*     */       
/* 252 */       k = getIndex("B_Axy2");
/* 253 */       localObject = getLabel(getParent(k, 0));
/* 254 */       if (((String)localObject).equals("B_Axy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.da[1]); else
/* 255 */         arrayOfFloat1 = setCpt2(this.pos2, this.da[1], this.pos1);
/* 256 */       setCondProb(k, arrayOfFloat1);
/*     */       
/* 258 */       k = getIndex("B_Bxy2");
/* 259 */       localObject = getLabel(getParent(k, 0));
/* 260 */       if (((String)localObject).equals("B_Bxy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.db[1]); else
/* 261 */         arrayOfFloat1 = setCpt2(this.pos2, this.db[1], this.pos1);
/* 262 */       setCondProb(k, arrayOfFloat1);
/*     */       
/* 264 */       k = getIndex("B_Cxy2");
/* 265 */       localObject = getLabel(getParent(k, 0));
/* 266 */       if (((String)localObject).equals("B_Cxy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.da[1]); else
/* 267 */         arrayOfFloat1 = setCpt2(this.pos2, this.da[1], this.pos1);
/* 268 */       setCondProb(k, arrayOfFloat1);
/*     */     }
/*     */     else {
/* 271 */       setCondProb(getIndex("C_Bxy1"), setCpt(this.pos1, this.db[0]));
/* 272 */       setCondProb(getIndex("C_Cxy1"), setCpt(this.pos1, this.da[0]));
/*     */       
/* 274 */       k = getIndex("C_Bxy2");
/* 275 */       localObject = getLabel(getParent(k, 0));
/* 276 */       if (((String)localObject).equals("C_Bxy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.db[1]); else
/* 277 */         arrayOfFloat1 = setCpt2(this.pos2, this.db[1], this.pos1);
/* 278 */       setCondProb(k, arrayOfFloat1);
/*     */       
/* 280 */       k = getIndex("C_Cxy2");
/* 281 */       localObject = getLabel(getParent(k, 0));
/* 282 */       if (((String)localObject).equals("C_Cxy1")) arrayOfFloat1 = setCpt1(this.pos2, this.pos1, this.da[1]); else
/* 283 */         arrayOfFloat1 = setCpt2(this.pos2, this.da[1], this.pos1);
/* 284 */       setCondProb(k, arrayOfFloat1);
/*     */     }
/*     */     
/* 287 */     float[] arrayOfFloat2 = null;
/* 288 */     Object localObject = { 0, 1 };int[] arrayOfInt1 = { 1, 0 };
/* 289 */     int[] arrayOfInt2 = { this.pos1.length, this.pos1.length };
/* 290 */     int[] arrayOfInt3 = { this.pos2.length, this.pos2.length };
/*     */     int m;
/* 292 */     String str; if (this.self == 0) {
/* 293 */       arrayOfFloat2 = setUtil(this.pos1, this.pos1, this.loc[0], this.loc[1], this.exloc, this.loc[2], this.ppt2, this.vloc);
/* 294 */       m = getIndex("A_ABu1");
/* 295 */       str = getLabel(getParent(m, 0));
/* 296 */       if (!str.equals("A_Axy1")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt2, arrayOfFloat2, arrayOfInt1);
/* 297 */       setCptUtilFmUtil(m, arrayOfFloat2);
/*     */       
/* 299 */       arrayOfFloat2 = setUtil(this.pos2, this.pos2, this.loc[0], this.loc[1], this.exloc, this.loc[2], this.ppt2, this.vloc);
/* 300 */       m = getIndex("A_ABu2");
/* 301 */       str = getLabel(getParent(m, 0));
/* 302 */       if (!str.equals("A_Axy2")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt3, arrayOfFloat2, arrayOfInt1);
/* 303 */       setCptUtilFmUtil(m, arrayOfFloat2);
/*     */     }
/* 305 */     else if (this.self == 1) {
/* 306 */       arrayOfFloat2 = setUtil(this.pos1, this.pos1, this.loc[1], this.loc[0], this.exloc, null, this.ppt2, this.vloc);
/* 307 */       m = getIndex("B_ABu1");
/* 308 */       str = getLabel(getParent(m, 0));
/* 309 */       if (!str.equals("B_Bxy1")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt2, arrayOfFloat2, arrayOfInt1);
/* 310 */       setCptUtilFmUtil(m, arrayOfFloat2);
/* 311 */       setWeight(m, 0.25F);
/*     */       
/* 313 */       arrayOfFloat2 = setUtil(this.pos2, this.pos2, this.loc[1], this.loc[0], this.exloc, null, this.ppt2, this.vloc);
/* 314 */       m = getIndex("B_ABu2");
/* 315 */       str = getLabel(getParent(m, 0));
/* 316 */       if (!str.equals("B_Bxy2")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt3, arrayOfFloat2, arrayOfInt1);
/* 317 */       setCptUtilFmUtil(m, arrayOfFloat2);
/* 318 */       setWeight(m, 0.25F);
/*     */       
/* 320 */       arrayOfFloat2 = setUtil(this.pos1, this.pos1, this.loc[1], this.loc[2], this.exloc, null, this.ppt2, this.vloc);
/* 321 */       m = getIndex("B_BCu1");
/* 322 */       str = getLabel(getParent(m, 0));
/* 323 */       if (!str.equals("B_Bxy1")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt2, arrayOfFloat2, arrayOfInt1);
/* 324 */       setCptUtilFmUtil(m, arrayOfFloat2);
/* 325 */       setWeight(m, 0.25F);
/*     */       
/* 327 */       arrayOfFloat2 = setUtil(this.pos2, this.pos2, this.loc[1], this.loc[2], this.exloc, null, this.ppt2, this.vloc);
/* 328 */       m = getIndex("B_BCu2");
/* 329 */       str = getLabel(getParent(m, 0));
/* 330 */       if (!str.equals("B_Bxy2")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt3, arrayOfFloat2, arrayOfInt1);
/* 331 */       setCptUtilFmUtil(m, arrayOfFloat2);
/* 332 */       setWeight(m, 0.25F);
/*     */     }
/*     */     else {
/* 335 */       arrayOfFloat2 = setUtil(this.pos1, this.pos1, this.loc[2], this.loc[1], this.exloc, this.loc[0], this.ppt2, this.vloc);
/* 336 */       m = getIndex("C_BCu1");
/* 337 */       str = getLabel(getParent(m, 0));
/* 338 */       if (!str.equals("C_Cxy1")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt2, arrayOfFloat2, arrayOfInt1);
/* 339 */       setCptUtilFmUtil(m, arrayOfFloat2);
/* 340 */       setWeight(m, 0.5F);
/*     */       
/* 342 */       arrayOfFloat2 = setUtil(this.pos2, this.pos2, this.loc[2], this.loc[1], this.exloc, this.loc[0], this.ppt2, this.vloc);
/* 343 */       m = getIndex("C_BCu2");
/* 344 */       str = getLabel(getParent(m, 0));
/* 345 */       if (!str.equals("C_Cxy2")) arrayOfFloat2 = MATH.reorderBelief((int[])localObject, arrayOfInt3, arrayOfFloat2, arrayOfInt1);
/* 346 */       setCptUtilFmUtil(m, arrayOfFloat2);
/* 347 */       setWeight(m, 0.5F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setAgtnm(String[] paramArrayOfString)
/*     */   {
/* 355 */     String[] arrayOfString = new String[3];
/* 356 */     System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, 3);
/*     */     String str;
/* 358 */     if (arrayOfString[0].compareTo(arrayOfString[1]) > 0) {
/* 359 */       str = arrayOfString[1];arrayOfString[1] = arrayOfString[0];arrayOfString[0] = str;
/*     */     }
/* 361 */     if (arrayOfString[1].compareTo(arrayOfString[2]) > 0) {
/* 362 */       str = arrayOfString[2];arrayOfString[2] = arrayOfString[1];arrayOfString[1] = str;
/*     */     }
/* 364 */     if (arrayOfString[0].compareTo(arrayOfString[1]) > 0) {
/* 365 */       str = arrayOfString[1];arrayOfString[1] = arrayOfString[0];arrayOfString[0] = str;
/*     */     }
/* 367 */     this.agtnm = arrayOfString;
/*     */   }
/*     */   
/*     */   void setSelf(String paramString)
/*     */   {
/* 372 */     for (int i = 0; i < this.agtnm.length; i++) {
/* 373 */       if (paramString.equals(this.agtnm[i])) {
/* 374 */         this.self = i;return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setLoc(String[] paramArrayOfString, Point[] paramArrayOfPoint)
/*     */   {
/* 383 */     this.loc = new Point[3];
/* 384 */     for (int i = 0; i < this.agtnm.length; i++) {
/* 385 */       for (int j = 0; j < paramArrayOfString.length; j++) {
/* 386 */         if (this.agtnm[i].equals(paramArrayOfString[j])) {
/* 387 */           this.loc[i] = paramArrayOfPoint[j];
/* 388 */           break;
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
/*     */ 
/*     */   void setGroupDirection()
/*     */   {
/* 407 */     double d1 = this.loc[2].x - this.loc[0].x;
/* 408 */     double d2 = this.loc[2].y - this.loc[0].y;
/* 409 */     double d3 = 57.29564553093965D;
/* 410 */     double d4 = Math.atan2(d2, d1) * d3;
/* 411 */     setGroupDirection(d4);
/*     */   }
/*     */   
/*     */ 
/*     */   void setGroupDirection(double paramDouble)
/*     */   {
/* 417 */     HelpPanel.addHelp("  Angle (degree) of raw group direction = " + paramDouble);
/*     */     
/* 419 */     if ((paramDouble > -45.0D) && (paramDouble <= 45.0D)) { this.gdir = 'N';
/* 420 */     } else if ((paramDouble > 45.0D) && (paramDouble <= 135.0D)) { this.gdir = 'W';
/* 421 */     } else if ((paramDouble > -135.0D) && (paramDouble <= -45.0D)) this.gdir = 'E'; else {
/* 422 */       this.gdir = 'S';
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setVarDomain()
/*     */   {
/* 434 */     this.da = new String[2][];
/* 435 */     String[] arrayOfString1; String[] arrayOfString2; if (this.gdir == 'E') {
/* 436 */       arrayOfString1 = new String[] { "E", "N", "S", "H" };this.da[0] = arrayOfString1;
/* 437 */       arrayOfString2 = new String[] { "E", "N", "S" };this.da[1] = arrayOfString2;
/*     */     }
/* 439 */     else if (this.gdir == 'W') {
/* 440 */       arrayOfString1 = new String[] { "W", "N", "S", "H" };this.da[0] = arrayOfString1;
/* 441 */       arrayOfString2 = new String[] { "W", "N", "S" };this.da[1] = arrayOfString2;
/*     */     }
/* 443 */     else if (this.gdir == 'N') {
/* 444 */       arrayOfString1 = new String[] { "N", "E", "W", "H" };this.da[0] = arrayOfString1;
/* 445 */       arrayOfString2 = new String[] { "N", "E", "W" };this.da[1] = arrayOfString2;
/*     */     }
/*     */     else {
/* 448 */       arrayOfString1 = new String[] { "S", "E", "W", "H" };this.da[0] = arrayOfString1;
/* 449 */       arrayOfString2 = new String[] { "S", "E", "W" };this.da[1] = arrayOfString2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   Point getxy(String paramString)
/*     */   {
/* 456 */     String str = paramString.replace('(', ' ').replace(',', ' ').replace(')', ' ');
/* 457 */     StringTokenizer localStringTokenizer = new StringTokenizer(str);
/* 458 */     int i = Integer.parseInt(localStringTokenizer.nextToken());
/* 459 */     int j = Integer.parseInt(localStringTokenizer.nextToken());
/* 460 */     return new Point(i, j);
/*     */   }
/*     */   
/*     */   Point getNewxy(Point paramPoint, String paramString)
/*     */   {
/* 465 */     if (paramString.equals("E")) return new Point(paramPoint.x + 1, paramPoint.y);
/* 466 */     if (paramString.equals("W")) return new Point(paramPoint.x - 1, paramPoint.y);
/* 467 */     if (paramString.equals("N")) return new Point(paramPoint.x, paramPoint.y + 1);
/* 468 */     if (paramString.equals("S")) return new Point(paramPoint.x, paramPoint.y - 1);
/* 469 */     return paramPoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isUnintended(Point paramPoint1, Point paramPoint2)
/*     */   {
/* 479 */     if ((paramPoint2.equals(paramPoint1)) || (paramPoint2.equals(new Point(paramPoint1.x + 1, paramPoint1.y))) || (paramPoint2.equals(new Point(paramPoint1.x, paramPoint1.y + 1))) || (paramPoint2.equals(new Point(paramPoint1.x - 1, paramPoint1.y))) || (paramPoint2.equals(new Point(paramPoint1.x, paramPoint1.y - 1))))
/*     */     {
/*     */ 
/* 482 */       return true; }
/* 483 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setCpt(String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/* 493 */     int i = paramArrayOfString1.length;
/* 494 */     int j = paramArrayOfString2.length;
/* 495 */     float[] arrayOfFloat = new float[i * j];
/*     */     
/* 497 */     float f = (1.0F - this.p1) / 4.0F;
/* 498 */     for (int k = 0; k < j; k++) {
/* 499 */       Point localPoint1 = getNewxy(new Point(0, 0), paramArrayOfString2[k]);
/* 500 */       int m; Point localPoint2; if (paramArrayOfString2[k].equals("H")) {
/* 501 */         for (m = 0; m < i; m++) {
/* 502 */           localPoint2 = getxy(paramArrayOfString1[m]);
/* 503 */           if (localPoint2.equals(localPoint1)) arrayOfFloat[(k * i + m)] = 1.0F; else {
/* 504 */             arrayOfFloat[(k * i + m)] = 0.0F;
/*     */           }
/*     */         }
/*     */       } else
/* 508 */         for (m = 0; m < i; m++) {
/* 509 */           localPoint2 = getxy(paramArrayOfString1[m]);
/* 510 */           if (localPoint2.equals(localPoint1)) arrayOfFloat[(k * i + m)] = this.p1; else
/* 511 */             arrayOfFloat[(k * i + m)] = f;
/*     */         }
/*     */     }
/* 514 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setCpt1(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
/*     */   {
/* 524 */     int i = paramArrayOfString1.length;
/* 525 */     int j = paramArrayOfString2.length;
/* 526 */     int k = paramArrayOfString3.length;
/* 527 */     float[] arrayOfFloat = new float[i * j * k];
/* 528 */     float f = (1.0F - this.p1) / 4.0F;
/*     */     
/* 530 */     for (int m = 0; m < j; m++) {
/* 531 */       Point localPoint1 = getxy(paramArrayOfString2[m]);
/* 532 */       for (int n = 0; n < k; n++) {
/* 533 */         Point localPoint2 = getNewxy(localPoint1, paramArrayOfString3[n]);
/* 534 */         for (int i1 = 0; i1 < i; i1++) {
/* 535 */           Point localPoint3 = getxy(paramArrayOfString1[i1]);
/* 536 */           if (localPoint3.equals(localPoint2)) { arrayOfFloat[((m * k + n) * i + i1)] = this.p1;
/* 537 */           } else if (isUnintended(localPoint1, localPoint3)) arrayOfFloat[((m * k + n) * i + i1)] = f; else
/* 538 */             arrayOfFloat[((m * k + n) * i + i1)] = 0.0F;
/*     */         }
/*     */       }
/*     */     }
/* 542 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */   float[] setCpt2(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
/*     */   {
/* 548 */     int i = paramArrayOfString1.length;
/* 549 */     int j = paramArrayOfString3.length;
/* 550 */     int k = paramArrayOfString2.length;
/* 551 */     float[] arrayOfFloat = new float[i * j * k];
/* 552 */     float f = (1.0F - this.p1) / 4.0F;
/*     */     
/* 554 */     for (int m = 0; m < k; m++) {
/* 555 */       for (int n = 0; n < j; n++) {
/* 556 */         Point localPoint1 = getxy(paramArrayOfString3[n]);
/* 557 */         Point localPoint2 = getNewxy(localPoint1, paramArrayOfString2[m]);
/* 558 */         for (int i1 = 0; i1 < i; i1++) {
/* 559 */           Point localPoint3 = getxy(paramArrayOfString1[i1]);
/* 560 */           if (localPoint3.equals(localPoint2)) { arrayOfFloat[((m * j + n) * i + i1)] = this.p1;
/* 561 */           } else if (isUnintended(localPoint1, localPoint3)) arrayOfFloat[((m * j + n) * i + i1)] = f; else
/* 562 */             arrayOfFloat[((m * j + n) * i + i1)] = 0.0F;
/*     */         }
/*     */       }
/*     */     }
/* 566 */     return arrayOfFloat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] setUtil(String[] paramArrayOfString1, String[] paramArrayOfString2, Point paramPoint1, Point paramPoint2, Point[] paramArrayOfPoint1, Point paramPoint3, Point[] paramArrayOfPoint2, float[][] paramArrayOfFloat)
/*     */   {
/* 624 */     int i = paramArrayOfString1.length;
/* 625 */     int j = paramArrayOfString2.length;
/* 626 */     float[] arrayOfFloat = new float[i * j];
/*     */     
/*     */ 
/* 629 */     for (int m = 0; m < i; m++) {
/* 630 */       Point localPoint1 = getxy(paramArrayOfString1[m]);
/* 631 */       int n = 1;
/* 632 */       int i1 = UTIL.getArrayIndex(localPoint1, this.ppt2);
/*     */       int i2;
/* 634 */       int k; if (paramArrayOfPoint1 != null) {
/* 635 */         for (i2 = 0; i2 < paramArrayOfPoint1.length; i2++) {
/* 636 */           k = distSqr(localPoint1, paramPoint1, paramArrayOfPoint1[i2]);
/* 637 */           if (k < 100) {
/* 638 */             n = 0;
/* 639 */             for (int i3 = 0; i3 < j; i3++) arrayOfFloat[(m * j + i3)] = 0.0F;
/* 640 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 645 */       if (paramPoint3 != null) {
/* 646 */         k = distSqr(localPoint1, paramPoint1, paramPoint3);
/* 647 */         if (k <= 4) {
/* 648 */           n = 0;
/* 649 */           for (i2 = 0; i2 < j; i2++) { arrayOfFloat[(m * j + i2)] = 0.0F;
/*     */           }
/*     */         }
/*     */       }
/* 653 */       if (n != 0)
/*     */       {
/* 655 */         for (i2 = 0; i2 < j; i2++) {
/* 656 */           Point localPoint2 = getxy(paramArrayOfString2[i2]);
/* 657 */           k = distSqr(localPoint1, localPoint2, paramPoint1, paramPoint2);
/* 658 */           if (k > 16) {
/* 659 */             arrayOfFloat[(m * j + i2)] = 0.0F;
/*     */ 
/*     */ 
/*     */           }
/* 663 */           else if (equal(localPoint1, localPoint2, paramPoint1, paramPoint2)) arrayOfFloat[(m * j + i2)] = (paramArrayOfFloat[i1][1] / 2.0F); else
/* 664 */             arrayOfFloat[(m * j + i2)] = paramArrayOfFloat[i1][0];
/*     */         } }
/*     */     }
/* 667 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean equal(Point paramPoint1, Point paramPoint2, Point paramPoint3, Point paramPoint4)
/*     */   {
/* 676 */     return (paramPoint3.x + paramPoint1.x == paramPoint4.x + paramPoint2.x) && (paramPoint3.y + paramPoint1.y == paramPoint4.y + paramPoint2.y);
/*     */   }
/*     */   
/*     */   int distSqr(Point paramPoint1, Point paramPoint2, Point paramPoint3, Point paramPoint4)
/*     */   {
/* 681 */     int i = paramPoint3.x + paramPoint1.x;int j = paramPoint3.y + paramPoint1.y;
/* 682 */     int k = paramPoint4.x + paramPoint2.x;int m = paramPoint4.y + paramPoint2.y;
/* 683 */     int n = i - k;int i1 = j - m;
/* 684 */     return n * n + i1 * i1;
/*     */   }
/*     */   
/*     */   int distSqr(Point paramPoint1, Point paramPoint2, Point paramPoint3)
/*     */   {
/* 689 */     int i = paramPoint2.x + paramPoint1.x;int j = paramPoint2.y + paramPoint1.y;
/* 690 */     int k = i - paramPoint3.x;int m = j - paramPoint3.y;
/* 691 */     return k * k + m * m;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadWorld(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 727 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 728 */       HelpPanel.addHelp("Loading agent env from " + paramString);
/*     */       
/* 730 */       String[] arrayOfString = new String[3];
/* 731 */       Point[] arrayOfPoint = new Point[3];
/* 732 */       for (int i = 0; i < 3; i++) {
/* 733 */         StringTokenizer localStringTokenizer1 = new StringTokenizer(localBufferedReader.readLine());
/* 734 */         arrayOfString[i] = localStringTokenizer1.nextToken();
/* 735 */         arrayOfPoint[i] = new Point();
/* 736 */         arrayOfPoint[i].x = Integer.parseInt(localStringTokenizer1.nextToken());
/* 737 */         arrayOfPoint[i].y = Integer.parseInt(localStringTokenizer1.nextToken());
/*     */       }
/*     */       
/* 740 */       setAgtnm(arrayOfString);
/* 741 */       setSelf(arrayOfString[0]);
/* 742 */       setLoc(arrayOfString, arrayOfPoint);
/*     */       
/* 744 */       UTIL.skipLine(localBufferedReader);
/* 745 */       i = UTIL.loadInt(localBufferedReader);
/* 746 */       if (i > 0) {
/* 747 */         this.exloc = new Point[i];
/* 748 */         for (j = 0; j < i; j++) this.exloc[j] = UTIL.loadPoint(localBufferedReader);
/*     */       }
/* 750 */       UTIL.skipLine(localBufferedReader);
/*     */       
/* 752 */       this.vloc = new float[13][2];
/* 753 */       for (int j = 0; j < 13; j++) {
/* 754 */         StringTokenizer localStringTokenizer3 = new StringTokenizer(localBufferedReader.readLine());
/* 755 */         float f1 = Float.valueOf(localStringTokenizer3.nextToken()).floatValue();
/* 756 */         float f2 = Float.valueOf(localStringTokenizer3.nextToken()).floatValue();
/* 757 */         localStringTokenizer3.nextToken();
/* 758 */         String str = localStringTokenizer3.nextToken();
/* 759 */         int k = UTIL.getArrayIndex(str, this.pos2);
/* 760 */         this.vloc[k][0] = f1;this.vloc[k][1] = f2;
/*     */       }
/*     */       
/* 763 */       UTIL.skipLine(localBufferedReader);
/* 764 */       StringTokenizer localStringTokenizer2 = new StringTokenizer(localBufferedReader.readLine());
/* 765 */       this.p1 = Float.valueOf(localStringTokenizer2.nextToken()).floatValue();
/*     */       
/* 767 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 769 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/*     */   }
/*     */   
/*     */   public static DesNetPers loadDnPers(String paramString)
/*     */   {
/* 775 */     DesignNet localDesignNet = DesignNet.loadDn(paramString);
/* 776 */     return new DesNetPers(localDesignNet);
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
/*     */   void setJumpTarget(char paramChar)
/*     */   {
/* 813 */     Point[] arrayOfPoint = getJumpRegion(paramChar);
/*     */     
/* 815 */     int i = UTIL.getArrayIndex(arrayOfPoint[0], this.ppt2);
/* 816 */     if (this.vloc[i][0] > 1.0E-5D) {
/* 817 */       this.vloc[i][0] = 1.0F;
/*     */       
/* 819 */       i = UTIL.getArrayIndex(arrayOfPoint[3], this.ppt2);
/* 820 */       if (this.vloc[i][0] > 1.0E-5D) { this.vloc[i][0] = 1.0F;
/*     */       } else {
/* 822 */         i = UTIL.getArrayIndex(arrayOfPoint[4], this.ppt2);
/* 823 */         if (this.vloc[i][0] > 1.0E-5D) this.vloc[i][0] = 1.0F; else {
/* 824 */           HelpPanel.showError("Unexpected reward at cell 4.");
/*     */         }
/* 826 */         i = UTIL.getArrayIndex(arrayOfPoint[5], this.ppt2);
/* 827 */         if (this.vloc[i][0] > 1.0E-5D) this.vloc[i][0] = 1.0F; else {
/* 828 */           HelpPanel.showError("Unexpected reward at cell 5.");
/*     */         }
/*     */       }
/*     */     } else {
/* 832 */       i = UTIL.getArrayIndex(arrayOfPoint[1], this.ppt2);
/* 833 */       if (this.vloc[i][0] > 1.0E-5D) {
/* 834 */         this.vloc[i][0] = 1.0F;
/*     */         
/* 836 */         i = UTIL.getArrayIndex(arrayOfPoint[4], this.ppt2);
/* 837 */         if (this.vloc[i][0] > 1.0E-5D) this.vloc[i][0] = 1.0F; else
/* 838 */           HelpPanel.showError("Unexpected reward at cell 4.");
/*     */       } else {
/* 840 */         HelpPanel.showError("Unexpected reward at cell 1.");
/*     */       }
/* 842 */       i = UTIL.getArrayIndex(arrayOfPoint[2], this.ppt2);
/* 843 */       if (this.vloc[i][0] > 1.0E-5D) {
/* 844 */         this.vloc[i][0] = 1.0F;
/*     */         
/* 846 */         i = UTIL.getArrayIndex(arrayOfPoint[5], this.ppt2);
/* 847 */         if (this.vloc[i][0] > 1.0E-5D) this.vloc[i][0] = 1.0F; else
/* 848 */           HelpPanel.showError("Unexpected reward at cell 5.");
/*     */       } else {
/* 850 */         HelpPanel.showError("Unexpected reward at cell 2.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   Point[] getJumpRegion(char paramChar)
/*     */   {
/*     */     Point[] arrayOfPoint;
/*     */     
/* 861 */     if (paramChar == 'N') {
/* 862 */       arrayOfPoint = new Point[] { new Point(0, 1), new Point(1, 0), new Point(-1, 0), new Point(0, 2), new Point(1, 1), new Point(-1, 1) };
/* 863 */       return arrayOfPoint;
/*     */     }
/* 865 */     if (paramChar == 'S') {
/* 866 */       arrayOfPoint = new Point[] { new Point(0, -1), new Point(1, 0), new Point(-1, 0), new Point(0, -2), new Point(1, -1), new Point(-1, -1) };
/* 867 */       return arrayOfPoint;
/*     */     }
/* 869 */     if (paramChar == 'E') {
/* 870 */       arrayOfPoint = new Point[] { new Point(1, 0), new Point(0, 1), new Point(0, -1), new Point(2, 0), new Point(1, 1), new Point(1, -1) };
/* 871 */       return arrayOfPoint;
/*     */     }
/* 873 */     if (paramChar == 'W') {
/* 874 */       arrayOfPoint = new Point[] { new Point(-1, 0), new Point(0, 1), new Point(0, -1), new Point(-2, 0), new Point(-1, 1), new Point(-1, -1) };
/* 875 */       return arrayOfPoint;
/*     */     }
/* 877 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DesNetPers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */