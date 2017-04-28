/*    */ package Network;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.PrintWriter;
/*    */ import java.net.Socket;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ class Bearer extends Thread
/*    */ {
/*    */   Socket sock;
/*    */   BearerMgr bmgr;
/* 12 */   int msgType = -2;
/* 13 */   int caller = -2;
/* 14 */   static boolean debug = false;
/*    */   
/*    */   public Bearer(Socket paramSocket, BearerMgr paramBearerMgr) {
/* 17 */     this.sock = paramSocket;
/* 18 */     this.bmgr = paramBearerMgr;
/* 19 */     this.bmgr.setCount(true);
/* 20 */     start();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 30 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(this.sock.getInputStream()));
/*    */       
/*    */ 
/* 33 */       this.bmgr.setCommFlag("communicate");
/* 34 */       String str1 = localBufferedReader.readLine();
/* 35 */       if (debug) System.out.println("Bearer recved: " + str1);
/* 36 */       StringTokenizer localStringTokenizer = new StringTokenizer(str1);
/*    */       
/* 38 */       this.msgType = Integer.parseInt(localStringTokenizer.nextToken());
/* 39 */       String str2 = localStringTokenizer.nextToken();
/* 40 */       int i = Integer.parseInt(localStringTokenizer.nextToken());
/* 41 */       int j = Integer.parseInt(localStringTokenizer.nextToken());
/* 42 */       this.caller = this.bmgr.getCaller(str2, i);
/*    */       
/*    */ 
/* 45 */       this.bmgr.receiveInBody(localBufferedReader, this.caller, j);
/*    */       
/*    */ 
/* 48 */       String[] arrayOfString = this.bmgr.actAll(this.caller, this.msgType);
/*    */       
/* 50 */       this.bmgr.setMsgToPat(this.caller, this.msgType, arrayOfString);
/*    */       
/* 52 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(this.sock.getOutputStream())), true);
/*    */       
/* 54 */       this.bmgr.sendReply(localPrintWriter, this.caller);
/*    */       
/* 56 */       if (BearerMgr.halt) { System.exit(2);
/*    */       }
/* 58 */       if (this.bmgr.getCount() == 1) this.bmgr.setCommFlag("end_of_communicate");
/* 59 */       this.bmgr.setCount(false);
/* 60 */       localBufferedReader.close();localPrintWriter.close();this.sock.close();
/*    */     } catch (java.io.IOException localIOException) {
/* 62 */       HelpPanel.addHelp("Exception at Bearer: Please continue.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Bearer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */