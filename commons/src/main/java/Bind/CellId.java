/*    */ package Bind;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ public class CellId
/*    */ {
/*  8 */   String name = null;
/*  9 */   String host = null;
/* 10 */   String addr = null;
/* 11 */   int port = 0;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   String getName()
/*    */   {
/* 19 */     return new String(this.name);
/*    */   }
/*    */   
/*    */   String getHost() {
/* 23 */     if (this.host == null) return null;
/* 24 */     return new String(this.host);
/*    */   }
/*    */   
/*    */   String getAddr() {
/* 28 */     if (this.addr == null) return null;
/* 29 */     return new String(this.addr);
/*    */   }
/*    */   
/*    */   int getPort() {
/* 33 */     return this.port;
/*    */   }
/*    */   
/*    */ 
/*    */   void setName(String paramString)
/*    */   {
/* 39 */     this.name = new String(paramString);
/*    */   }
/*    */   
/*    */   void setHost(String paramString) {
/* 43 */     this.host = new String(paramString);
/*    */   }
/*    */   
/*    */   void setAddr(String paramString) {
/* 47 */     this.addr = new String(paramString);
/*    */   }
/*    */   
/* 50 */   void setPort(int paramInt) { this.port = paramInt; }
/*    */   
/*    */ 
/*    */ 
/*    */   void showCellId()
/*    */   {
/* 56 */     System.out.println(this.name + ": host=" + this.host + " addr=" + this.addr + " port=" + this.port);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/CellId.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */