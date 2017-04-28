/*    */ package Network;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.net.Socket;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Envoy
/*    */   extends Thread
/*    */ {
/*    */   MsgBox mb;
/* 21 */   static boolean debug = false;
/*    */   
/*    */   Envoy(MsgBox paramMsgBox)
/*    */   {
/* 25 */     this.mb = paramMsgBox;
/* 26 */     start();
/*    */   }
/*    */   
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 33 */       Socket localSocket = new Socket(this.mb.getDestHost(), this.mb.getDestPort());
/*    */       
/* 35 */       PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream())), true);
/*    */       
/* 37 */       BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
/*    */       
/*    */ 
/* 40 */       int i = this.mb.getOutBodyCount();
/* 41 */       String str1 = new String(this.mb.getOutMsgType() + " " + this.mb.getHost() + " " + this.mb.getPort() + " " + i);
/*    */       
/* 43 */       localPrintWriter.println(str1);
/* 44 */       for (int j = 0; j < i; j++) { localPrintWriter.println(this.mb.getOutBody(j));
/*    */       }
/* 46 */       String str2 = localBufferedReader.readLine();
/* 47 */       if (debug) System.out.println("Reply recved: " + str2);
/* 48 */       StringTokenizer localStringTokenizer = new StringTokenizer(str2);
/* 49 */       localStringTokenizer.nextToken();localStringTokenizer.nextToken();localStringTokenizer.nextToken();
/* 50 */       i = Integer.parseInt(localStringTokenizer.nextToken());
/*    */       
/* 52 */       this.mb.setDumbInBody(i);
/* 53 */       for (int k = 0; k < i; k++) {
/* 54 */         str2 = localBufferedReader.readLine();
/* 55 */         if (debug) System.out.println("\nReply recved: " + str2);
/* 56 */         this.mb.setInBody(str2, k);
/*    */       }
/* 58 */       this.mb.setRecved(true);
/* 59 */       localPrintWriter.close();localBufferedReader.close();localSocket.close();
/*    */     } catch (Exception localException) {
/* 61 */       HelpPanel.showError("Envoy exception: " + localException);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Envoy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */