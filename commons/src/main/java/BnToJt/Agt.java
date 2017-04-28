/*     */ package BnToJt;
/*     */ 
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import java.io.PrintStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Agt
/*     */ {
/* 331 */   int id = -1;
/* 332 */   int state = 1;
/* 333 */   int parent = -1;
/* 334 */   int curtok = -1;
/* 335 */   int[] bound = null;
/* 336 */   int[] actbd = null;
/*     */   
/* 338 */   int[] nb = null;
/* 339 */   int[][] border = (int[][])null;
/* 340 */   int[] nbsta = null;
/* 341 */   boolean[] visited = null;
/*     */   
/* 343 */   String[] mbox = null;
/*     */   
/*     */ 
/*     */ 
/* 347 */   Msger msger = null;
/*     */   
/* 349 */   int[] jtnb = null;
/*     */   
/*     */   Agt(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[][] paramArrayOfInt, Msger paramMsger)
/*     */   {
/* 353 */     this.id = paramInt;
/* 354 */     this.nb = paramArrayOfInt1;
/* 355 */     this.border = paramArrayOfInt;
/* 356 */     this.bound = UTIL.getDuplicate(paramArrayOfInt2);
/* 357 */     this.actbd = UTIL.getDuplicate(paramArrayOfInt2);
/* 358 */     int i = paramArrayOfInt1.length;
/* 359 */     this.nbsta = new int[i];
/* 360 */     this.visited = new boolean[i];
/* 361 */     this.mbox = new String[i];
/*     */     
/* 363 */     for (int j = 0; j < i; j++) {
/* 364 */       this.nbsta[j] = 1;
/* 365 */       this.visited[j] = false;
/* 366 */       this.mbox[j] = new String("");
/*     */     }
/*     */     
/* 369 */     this.msger = paramMsger;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void respond()
/*     */   {
/* 378 */     if (noMail()) { return;
/*     */     }
/* 380 */     int i = this.nb.length;
/* 381 */     for (int j = 0; j < i; j++) {
/* 382 */       if (!this.mbox[j].equals(""))
/*     */       {
/* 384 */         System.out.println("\tA" + this.id + ": Process " + this.mbox[j] + " from A" + this.nb[j]);
/* 385 */         StringTokenizer localStringTokenizer = new StringTokenizer(this.mbox[j]);
/* 386 */         this.mbox[j] = "";
/* 387 */         String str = localStringTokenizer.nextToken();
/*     */         
/* 389 */         if (str.equals("StartNewDFT")) {
/* 390 */           startNewDFT(this.nb[j], Integer.parseInt(localStringTokenizer.nextToken()));
/*     */ 
/*     */         }
/* 393 */         else if (str.equals("DFT")) {
/* 394 */           respondDFT(this.nb[j], Integer.parseInt(localStringTokenizer.nextToken()));
/* 395 */         } else if (str.equals("Report")) { respondReport(this.nb[j]);
/*     */         } else {
/* 397 */           System.out.println("Unknow message type: " + str);System.exit(0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void startNewDFT(int paramInt1, int paramInt2)
/*     */   {
/* 407 */     System.out.println("\tA" + this.id + ": StartNewDFT " + "tok=" + paramInt2);
/*     */     
/* 409 */     if (paramInt1 > -1) {
/* 410 */       this.jtnb = MATH.addMember(paramInt1, this.jtnb);
/* 411 */       int i = UTIL.getArrayIndex(paramInt1, this.nb);
/* 412 */       this.nbsta[i] = 0;
/*     */       
/* 414 */       if (!hasInNb()) {
/* 415 */         System.out.println("\tA" + this.id + ": Hypertree exists (" + "Runtime = " + this.msger.msgcnt + " time units)");
/*     */         
/* 417 */         this.msger.setDone(1);
/* 418 */         sendAnnounce(-1, this.msger.msgcnt);
/* 419 */         return;
/*     */       }
/*     */       
/* 422 */       this.actbd = null;
/* 423 */       for (int j = 0; j < this.nb.length; j++) {
/* 424 */         if (this.nbsta[j] == 1) this.actbd = MATH.union(this.actbd, this.border[j]);
/*     */       }
/* 426 */       UTIL.showList("\tA" + this.id + ": Reduced boundary = ", this.actbd);
/*     */     }
/*     */     
/* 429 */     this.curtok = paramInt2;
/* 430 */     this.parent = -1;
/* 431 */     DFT();
/*     */   }
/*     */   
/*     */ 
/*     */   void DFT()
/*     */   {
/* 437 */     int i = isEliminable();
/* 438 */     int j; if (i != -1) {
/* 439 */       this.state = 0;
/* 440 */       System.out.println("\tA" + this.id + ": Self eliminated");
/*     */       
/* 442 */       for (j = 0; j < this.nb.length; j++) {
/* 443 */         if ((this.nbsta[j] == 1) && (j != i))
/* 444 */           this.msger.deliver(this.id, this.nb[j], "Eliminated");
/*     */       }
/* 446 */       this.msger.setElimSent(true);
/* 447 */       this.jtnb = MATH.addMember(this.nb[i], this.jtnb);
/* 448 */       this.msger.deliver(this.id, this.nb[i], "StartNewDFT " + (this.curtok + 1));
/*     */     }
/*     */     else {
/* 451 */       j = 0;
/* 452 */       for (int k = 0; k < this.nb.length; k++) {
/* 453 */         if ((this.nbsta[k] == 1) && (this.nb[k] != this.parent)) {
/* 454 */           this.visited[k] = false;
/* 455 */           j++;
/*     */         }
/*     */       }
/*     */       
/* 459 */       if (j == 0) {
/* 460 */         this.msger.deliver(this.id, this.parent, "Report");
/* 461 */         return;
/*     */       }
/* 463 */       for (k = 0; k < this.nb.length; k++) {
/* 464 */         if ((this.nbsta[k] == 1) && (this.nb[k] != this.parent)) {
/* 465 */           this.msger.deliver(this.id, this.nb[k], "DFT " + this.curtok);
/* 466 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   boolean hasInNb()
/*     */   {
/* 474 */     for (int i = 0; i < this.nb.length; i++) {
/* 475 */       if (this.nbsta[i] == 1) return true;
/*     */     }
/* 477 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int isEliminable()
/*     */   {
/* 485 */     for (int i = 0; i < this.nb.length; i++) {
/* 486 */       if ((this.nbsta[i] == 1) && (MATH.isEqualSet(this.actbd, this.border[i]))) return i;
/*     */     }
/* 488 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void respondEliminated(int paramInt)
/*     */   {
/* 495 */     int i = UTIL.getArrayIndex(paramInt, this.nb);
/* 496 */     this.nbsta[i] = 0;
/*     */   }
/*     */   
/*     */ 
/*     */   void handleEliminated()
/*     */   {
/* 502 */     for (int i = 0; i < this.nb.length; i++) {
/* 503 */       if (!this.mbox[i].equals("")) {
/* 504 */         StringTokenizer localStringTokenizer = new StringTokenizer(this.mbox[i]);
/* 505 */         String str = localStringTokenizer.nextToken();
/* 506 */         if (str.equals("Eliminated")) {
/* 507 */           respondEliminated(this.nb[i]);
/* 508 */           this.mbox[i] = "";
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void respondDFT(int paramInt1, int paramInt2)
/*     */   {
/* 517 */     if (this.curtok == paramInt2) {
/* 518 */       this.msger.deliver(this.id, paramInt1, "Report");
/* 519 */       return;
/*     */     }
/*     */     
/* 522 */     this.curtok = paramInt2;
/* 523 */     this.parent = paramInt1;
/* 524 */     int i = UTIL.getArrayIndex(paramInt1, this.nb);
/* 525 */     this.visited[i] = true;
/* 526 */     DFT();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void respondReport(int paramInt)
/*     */   {
/* 533 */     int i = UTIL.getArrayIndex(paramInt, this.nb);
/* 534 */     this.visited[i] = true;
/*     */     
/* 536 */     int j = 1;
/* 537 */     for (int k = 0; k < this.nb.length; k++) {
/* 538 */       if ((this.nb[k] != this.parent) && (this.nbsta[k] == 1) && (this.visited[k] == 0)) {
/* 539 */         this.msger.deliver(this.id, this.nb[k], "DFT " + this.curtok);
/* 540 */         j = 0;
/* 541 */         break;
/*     */       }
/*     */     }
/* 544 */     if (j != 0) {
/* 545 */       if (this.parent == -1) {
/* 546 */         System.out.println("\tA" + this.id + ": No hypertree exists (" + "Runtime = " + this.msger.msgcnt + " time units)");
/*     */         
/* 548 */         sendAnnounce(-1, this.msger.msgcnt);
/* 549 */         this.msger.setDone(0);
/*     */       } else {
/* 551 */         this.msger.deliver(this.id, this.parent, "Report");
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
/*     */   void respondAnnounce(int paramInt)
/*     */   {
/* 564 */     int i = this.nb.length;
/* 565 */     if (this.state == -1) {
/* 566 */       for (j = 0; j < i; j++) this.mbox[j] = "";
/* 567 */       return;
/*     */     }
/*     */     
/* 570 */     for (int j = 0; j < i; j++) {
/* 571 */       if (!this.mbox[j].equals("")) {
/* 572 */         StringTokenizer localStringTokenizer = new StringTokenizer(this.mbox[j]);
/* 573 */         String str = localStringTokenizer.nextToken();
/* 574 */         int k = Integer.parseInt(localStringTokenizer.nextToken());
/* 575 */         if ((str.equals("Announce")) && (k == paramInt)) {
/* 576 */           System.out.println("\tA" + this.id + ": Process " + this.mbox[j] + " from A" + this.nb[j]);
/* 577 */           this.mbox[j] = "";
/* 578 */           sendAnnounce(this.nb[j], k);
/* 579 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void sendAnnounce(int paramInt1, int paramInt2)
/*     */   {
/* 587 */     int i = paramInt2 + 1;
/* 588 */     System.out.println("\tA" + this.id + ": Send Announce t=" + i + " and halt");
/* 589 */     for (int j = 0; j < this.nb.length; j++)
/* 590 */       if (this.nb[j] != paramInt1) {
/* 591 */         this.msger.deliver(this.id, this.nb[j], "Announce " + i);
/* 592 */         i++;
/*     */       }
/* 594 */     this.state = -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setMail(int paramInt, String paramString)
/*     */   {
/* 601 */     int i = UTIL.getArrayIndex(paramInt, this.nb);
/* 602 */     this.mbox[i] = paramString;
/*     */   }
/*     */   
/*     */   boolean noMail()
/*     */   {
/* 607 */     for (int i = 0; i < this.nb.length; i++) if (!this.mbox[i].equals("")) return false;
/* 608 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void showMail()
/*     */   {
/* 615 */     for (int i = 0; i < this.nb.length; i++) {
/* 616 */       if (!this.mbox[i].equals(""))
/* 617 */         System.out.println("\t\tA" + this.id + " msg from A" + this.nb[i] + ": " + this.mbox[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   void showAgt() {
/* 622 */     System.out.println("\t\tA" + this.id + " local data:");
/* 623 */     System.out.println("\t\tstate = " + this.state);
/* 624 */     System.out.println("\t\tparent = " + this.parent);
/* 625 */     System.out.println("\t\tcurtok = " + this.curtok);
/* 626 */     UTIL.showList("\t\tbound[] = ", this.bound);
/* 627 */     UTIL.showList("\t\tactbd[] = ", this.actbd);
/* 628 */     UTIL.showList("\t\tnb[] = ", this.nb);
/* 629 */     UTIL.showList("\t\tborder[][] = ", this.border);
/* 630 */     UTIL.showList("\t\tnbsta[] = ", this.nbsta);
/* 631 */     UTIL.showList("\t\tvisited[] = ", this.visited);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/Agt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */