/*     */ package BnToJt;
/*     */ 
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ANod
/*     */ {
/* 280 */   int id = -1;
/* 281 */   boolean root = false;
/*     */   
/* 283 */   int[] nb = null;
/* 284 */   int[] nbsta = null;
/* 285 */   int[] w = null;
/* 286 */   int[] bowt = null;
/*     */   
/* 288 */   String[] mbox = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 294 */   int tpa = -1;
/* 295 */   int[] tch = null;
/* 296 */   int state = 0;
/*     */   
/* 298 */   Msger2 msger = null;
/*     */   
/*     */ 
/* 301 */   int d = 0;
/* 302 */   int h = 0;
/* 303 */   int r = 0;
/* 304 */   int rtime = 0;
/*     */   
/*     */ 
/*     */   ANod(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2, Msger2 paramMsger2)
/*     */   {
/* 309 */     this.id = paramInt;
/*     */     
/* 311 */     this.nb = paramArrayOfInt1;
/* 312 */     this.w = paramArrayOfInt2;
/* 313 */     int i = paramArrayOfInt1.length;
/* 314 */     this.nbsta = new int[i];
/* 315 */     for (int j = 0; j < i; j++) this.nbsta[j] = 0;
/* 316 */     this.mbox = new String[i];
/* 317 */     for (j = 0; j < i; j++) { this.mbox[j] = new String("");
/*     */     }
/* 319 */     this.msger = paramMsger2;
/*     */   }
/*     */   
/*     */   int getTreeParent()
/*     */   {
/* 324 */     return this.tpa;
/*     */   }
/*     */   
/*     */   int[] getTreeChild()
/*     */   {
/* 329 */     return this.tch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void start()
/*     */   {
/* 339 */     this.state = 1;
/* 340 */     this.root = true;
/* 341 */     System.out.println("\tV" + this.id + ": Added to MST as root.");
/*     */     
/* 343 */     this.bowt = UTIL.getDuplicate(this.w);
/* 344 */     System.out.print("\tV" + this.id + ": Create ");
/* 345 */     echoBowt();
/*     */     
/* 347 */     for (int i = 0; i < this.nb.length; i++) this.msger.deliver(this.id, this.nb[i], "Announce");
/* 348 */     expand(null);
/*     */   }
/*     */   
/*     */   void endExpansion() {
/* 352 */     int i = this.d + this.h + this.r - 1;
/* 353 */     System.out.println("\tEnd of one expansion: " + i + " time units");
/* 354 */     this.rtime += i;
/*     */   }
/*     */   
/*     */   void endDPMST() {
/* 358 */     System.out.println("\n\tDPMST runtime = " + this.rtime);
/*     */   }
/*     */   
/*     */   void appendOutput(String paramString1, String paramString2)
/*     */   {
/*     */     try {
/* 364 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString1, true));
/* 365 */       System.out.println("Saving file " + paramString1);
/* 366 */       localPrintWriter.println(paramString2 + " runtm= " + this.rtime);
/* 367 */       localPrintWriter.close();
/* 368 */     } catch (IOException localIOException) { System.out.println("Unable to save " + paramString1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void respond()
/*     */   {
/* 378 */     if ((this.root) && (this.msger.noMail()) && (this.state != 2)) {
/* 379 */       endExpansion();
/* 380 */       expand(null);
/* 381 */       return;
/*     */     }
/*     */     
/* 384 */     int i = this.nb.length;
/* 385 */     for (int j = 0; j < i; j++) {
/* 386 */       if (!this.mbox[j].equals(""))
/*     */       {
/* 388 */         StringTokenizer localStringTokenizer = new StringTokenizer(this.mbox[j]);
/* 389 */         this.mbox[j] = "";
/* 390 */         String str = localStringTokenizer.nextToken();
/* 391 */         System.out.println("\tV" + this.id + ": Process " + str + " from V" + this.nb[j]);
/*     */         
/* 393 */         if (str.equals("Announce")) { respondAnnounce(j);
/* 394 */         } else if (str.equals("Expand")) { expand(localStringTokenizer);
/* 395 */         } else if (str.equals("Notify")) { respondNotify(j, localStringTokenizer);
/* 396 */         } else if (str.equals("Report")) { respondReport(j, localStringTokenizer);
/*     */         } else {
/* 398 */           System.out.println("Unknow message type: " + str);
/* 399 */           System.exit(0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void expand(StringTokenizer paramStringTokenizer)
/*     */   {
/* 411 */     int i = 0;
/* 412 */     if (this.root) System.out.println("\tV" + this.id + ": Root starts Expand"); else
/* 413 */       i = Integer.parseInt(paramStringTokenizer.nextToken());
/* 414 */     int j = i + 1;
/*     */     
/* 416 */     System.out.print("\tV" + this.id + ": ");echoBowt();
/* 417 */     int k = MATH.maxIndex(this.bowt);
/* 418 */     int m = this.nb[k];
/* 419 */     if (this.nbsta[k] == 0) {
/* 420 */       this.msger.deliver(this.id, m, "Notify " + j);
/* 421 */       this.nbsta[k] = 1;
/* 422 */       this.tch = MATH.addMember(m, this.tch);
/*     */     }
/*     */     else {
/* 425 */       this.msger.deliver(this.id, m, "Expand " + j);
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
/*     */   void respondNotify(int paramInt, StringTokenizer paramStringTokenizer)
/*     */   {
/* 438 */     this.state = 1;
/* 439 */     System.out.println("\tV" + this.id + ": Added to MST.");
/*     */     
/* 441 */     this.nbsta[paramInt] = 1;
/* 442 */     this.tpa = this.nb[paramInt];
/*     */     
/* 444 */     this.bowt = UTIL.getDuplicate(this.w);
/* 445 */     for (int i = 0; i < this.nb.length; i++) if (this.nbsta[i] == 1) this.bowt[i] = -1;
/* 446 */     System.out.print("\tV" + this.id + ": Create ");echoBowt();
/*     */     
/* 448 */     i = this.nb.length;
/* 449 */     for (int j = 0; j < i; j++) {
/* 450 */       if (this.nb[j] != this.tpa) {
/* 451 */         this.msger.deliver(this.id, this.nb[j], "Announce");
/*     */       }
/*     */     }
/* 454 */     j = Integer.parseInt(paramStringTokenizer.nextToken());
/* 455 */     inform("N " + j + " " + i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void respondAnnounce(int paramInt)
/*     */   {
/* 465 */     this.nbsta[paramInt] = 1;
/*     */     
/* 467 */     if (this.bowt != null) this.bowt[paramInt] = -1;
/* 468 */     System.out.print("\tV" + this.id + ": ");echoBowt();
/*     */     
/* 470 */     if (this.state == 1) { inform("A 0 0");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void inform(String paramString)
/*     */   {
/* 482 */     tryToEnd(paramString);
/* 483 */     if ((this.state != 2) && (!this.root)) {
/* 484 */       int i = MATH.max(this.bowt);
/* 485 */       String str = "Report " + paramString + " 1 " + i;
/* 486 */       this.msger.deliver(this.id, this.tpa, str);
/* 487 */       System.out.println("\tV" + this.id + ": Send " + str + " to V" + this.tpa);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void tryToEnd(String paramString)
/*     */   {
/* 497 */     int i = 1;
/* 498 */     for (int j = 0; j < this.nb.length; j++) if (this.nbsta[j] == 0) i = 0;
/* 499 */     if (this.tch != null) {
/* 500 */       for (j = 0; j < this.tch.length; j++) {
/* 501 */         int k = UTIL.getArrayIndex(this.tch[j], this.nb);
/* 502 */         if (this.nbsta[k] != 2) { i = 0;
/*     */         }
/*     */       }
/*     */     }
/* 506 */     if (i != 0) {
/* 507 */       this.state = 2;
/* 508 */       System.out.println("\tV" + this.id + ": Done!");
/* 509 */       if (!this.root) {
/* 510 */         String str = "Report " + paramString + " 2 0";
/* 511 */         this.msger.deliver(this.id, this.tpa, str);
/* 512 */         System.out.println("\tV" + this.id + ": Send " + str + " to V" + this.tpa);
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
/*     */   void respondReport(int paramInt, StringTokenizer paramStringTokenizer)
/*     */   {
/* 535 */     String[] arrayOfString = new String[6];
/* 536 */     for (int i = 1; i <= 5; i++) arrayOfString[i] = paramStringTokenizer.nextToken();
/* 537 */     String str1 = arrayOfString[1] + " " + arrayOfString[2] + " " + arrayOfString[3];
/*     */     int j;
/* 539 */     if (Integer.parseInt(arrayOfString[4]) == 2) {
/* 540 */       this.nbsta[paramInt] = 2;
/* 541 */       this.bowt[paramInt] = -1;
/* 542 */       tryToEnd(str1);
/* 543 */       if (this.state != 2) {}
/*     */     }
/*     */     else {
/* 546 */       j = Integer.parseInt(arrayOfString[5]);
/* 547 */       if (this.bowt[paramInt] != j) this.bowt[paramInt] = j;
/*     */     }
/* 549 */     System.out.print("\tV" + this.id + ": ");echoBowt();
/*     */     
/* 551 */     if (!this.root) {
/* 552 */       j = MATH.max(this.bowt);
/* 553 */       if (j != -1) {
/* 554 */         String str2 = "Report " + str1 + " 1 " + j;
/* 555 */         this.msger.deliver(this.id, this.tpa, str2);
/* 556 */         System.out.println("\tV" + this.id + ": Send " + str2 + " to V" + this.tpa);
/*     */       }
/*     */       
/*     */     }
/* 560 */     else if (arrayOfString[1].equals("N")) {
/* 561 */       this.h = Integer.parseInt(arrayOfString[2]);
/* 562 */       this.r = Integer.parseInt(arrayOfString[3]);
/* 563 */       this.d = Math.max(this.d, this.h);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   boolean noMail()
/*     */   {
/* 570 */     for (int i = 0; i < this.nb.length; i++) if (!this.mbox[i].equals("")) return false;
/* 571 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setMail(int paramInt, String paramString)
/*     */   {
/* 578 */     int i = UTIL.getArrayIndex(paramInt, this.nb);
/* 579 */     this.mbox[i] = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void echoBowt()
/*     */   {
/* 589 */     if (this.bowt == null) {
/* 590 */       System.out.println("Bowt> not yet created");return;
/*     */     }
/*     */     
/* 593 */     int i = 1;
/* 594 */     for (int j = 0; j < this.nb.length; j++) {
/* 595 */       if (this.bowt[j] != -1) {
/* 596 */         i = 0; break;
/*     */       }
/*     */     }
/* 599 */     if (i != 0) {
/* 600 */       System.out.println("Bowt> all rows are deleted");return;
/*     */     }
/*     */     
/* 603 */     System.out.print("Bowt>");
/* 604 */     for (j = 0; j < this.nb.length; j++) {
/* 605 */       if (this.bowt[j] != -1)
/* 606 */         System.out.print(" V" + this.nb[j] + ":" + this.bowt[j]);
/*     */     }
/* 608 */     System.out.println();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/ANod.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */