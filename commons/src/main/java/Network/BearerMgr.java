/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BearerMgr
/*     */   extends Thread
/*     */ {
/*  23 */   ServerSocket ssock = null;
/*  24 */   int caller = -2;
/*     */   IntBMgr agent;
/*  26 */   MsgBox[] msgb = null;
/*  27 */   int msgType = -2;
/*  28 */   boolean stop = false;
/*  29 */   private int count = 0;
/*  30 */   static boolean halt = false;
/*     */   
/*  32 */   static boolean debug = false;
/*     */   
/*     */   BearerMgr(MsgBox[] paramArrayOfMsgBox, IntBMgr paramIntBMgr)
/*     */   {
/*  36 */     this.msgb = paramArrayOfMsgBox;
/*  37 */     this.agent = paramIntBMgr;
/*  38 */     this.caller = -2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  47 */       this.ssock = new ServerSocket(this.agent.getPort());
/*  48 */       HelpPanel.addHelp("Nonroot socket server ready.");
/*  49 */       while (!this.stop) {
/*  50 */         Socket localSocket = this.ssock.accept();
/*  51 */         new Bearer(localSocket, this);
/*     */       }
/*     */     } catch (IOException localIOException) {
/*  54 */       HelpPanel.addHelp("Exception at BearerMgr\trun().");
/*     */     }
/*     */   }
/*     */   
/*     */   void setStop(boolean paramBoolean) {
/*  59 */     this.stop = paramBoolean;
/*     */   }
/*     */   
/*     */   synchronized void setCount(boolean paramBoolean)
/*     */   {
/*  64 */     if (paramBoolean) this.count += 1; else {
/*  65 */       this.count -= 1;
/*     */     }
/*     */   }
/*     */   
/*     */   int getCount() {
/*  70 */     return this.count;
/*     */   }
/*     */   
/*     */   void setCommFlag(String paramString)
/*     */   {
/*  75 */     this.agent.setCtrlFlag("communication", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  81 */     String[] arrayOfString = this.agent.actAll(paramInt1, paramInt2);
/*  82 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/*  87 */     this.agent.setMsgToPat(paramInt1, paramInt2, paramArrayOfString);
/*     */   }
/*     */   
/*     */   void stopSock()
/*     */   {
/*     */     try {
/*  93 */       if (this.ssock != null) this.ssock.close();
/*  94 */       this.ssock = null;
/*     */     } catch (IOException localIOException) {
/*  96 */       HelpPanel.showError("BearerMgr: socket closing error.");
/*     */     }
/*     */   }
/*     */   
/*     */   void receiveInBody(BufferedReader paramBufferedReader, int paramInt1, int paramInt2)
/*     */   {
/* 102 */     if (paramInt2 == 0) { this.msgb[paramInt1].setEmptyInBody();
/*     */     } else {
/* 104 */       this.msgb[paramInt1].setDumbInBody(paramInt2);
/*     */       try {
/* 106 */         for (int i = 0; i < paramInt2; i++) this.msgb[paramInt1].setInBody(paramBufferedReader.readLine(), i);
/*     */       } catch (IOException localIOException) {
/* 108 */         HelpPanel.showError("BearerMgr buffer reading error.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void sendReply(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 115 */     String str = this.msgb[paramInt].getOutMsgType() + " " + this.agent.getHost() + " " + this.agent.getPort() + " " + this.msgb[paramInt].getOutBodyCount();
/*     */     
/* 117 */     paramPrintWriter.println(str);
/* 118 */     if (debug) System.out.println("\nHeader to caller: " + str);
/* 119 */     if (this.msgb[paramInt].getOutBodyCount() != 0) {
/* 120 */       for (int i = 0; i < this.msgb[paramInt].getOutBodyCount(); i++) {
/* 121 */         str = this.msgb[paramInt].getOutBody(i);
/* 122 */         paramPrintWriter.println(str);
/* 123 */         if (debug) System.out.println("Reply to caller: " + str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   int getCaller(String paramString, int paramInt)
/*     */   {
/* 130 */     for (int i = 0; i < this.msgb.length; i++) {
/* 131 */       if ((this.msgb[i].getDestHost().equals(paramString)) && (this.msgb[i].getDestPort() == paramInt))
/* 132 */         return i;
/*     */     }
/* 134 */     HelpPanel.showError("BearerMgr: Unknown caller found.");
/* 135 */     return -2;
/*     */   }
/*     */   
/*     */   void showMsgBox()
/*     */   {
/* 140 */     for (int i = 0; i < this.msgb.length; i++) this.msgb[i].showMsgBox();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/BearerMgr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */