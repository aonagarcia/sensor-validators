/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Node
/*     */ {
/* 156 */   private String label = null;
/* 157 */   private Point pos = new Point(0, 0);
/* 158 */   private boolean mark = false;
/* 159 */   private boolean stamp = false;
/* 160 */   private int peer = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Node()
/*     */   {
/* 168 */     this.label = new String("label");
/*     */   }
/*     */   
/*     */   Node(Node paramNode)
/*     */   {
/* 173 */     setNode(paramNode);
/*     */   }
/*     */   
/*     */   void setNode(Node paramNode)
/*     */   {
/* 178 */     this.label = new String(paramNode.label);
/* 179 */     this.pos = new Point(paramNode.pos);
/* 180 */     this.mark = paramNode.mark;
/* 181 */     this.peer = paramNode.peer;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setLabel(String paramString)
/*     */   {
/* 187 */     if (paramString == null) return;
/* 188 */     this.label = new String(paramString);
/*     */   }
/*     */   
/*     */   String getLabel() {
/* 192 */     return new String(this.label);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setPos(int paramInt1, int paramInt2)
/*     */   {
/* 199 */     this.pos.move(paramInt1, paramInt2);
/*     */   }
/*     */   
/* 202 */   void setPos(Point paramPoint) { this.pos.move(paramPoint.x, paramPoint.y); }
/*     */   
/*     */   Point getPos()
/*     */   {
/* 206 */     return new Point(this.pos);
/*     */   }
/*     */   
/*     */   int isClose(Point paramPoint, int paramInt)
/*     */   {
/* 211 */     return MATH.isClose(this.pos, paramPoint, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setMark()
/*     */   {
/* 218 */     this.mark = true;
/*     */   }
/*     */   
/* 221 */   void setMark(boolean paramBoolean) { this.mark = paramBoolean; }
/*     */   
/*     */ 
/*     */   boolean isMarked()
/*     */   {
/* 226 */     return this.mark;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setStamp()
/*     */   {
/* 233 */     this.stamp = true;
/*     */   }
/*     */   
/* 236 */   void setStamp(boolean paramBoolean) { this.stamp = paramBoolean; }
/*     */   
/*     */ 
/*     */   boolean isStamped()
/*     */   {
/* 241 */     return this.stamp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setPeer(int paramInt)
/*     */   {
/* 248 */     this.peer = paramInt;
/*     */   }
/*     */   
/*     */   int getPeer()
/*     */   {
/* 253 */     return this.peer;
/*     */   }
/*     */   
/*     */   boolean hasPeer()
/*     */   {
/* 258 */     if (this.peer >= 0) return true;
/* 259 */     return false;
/*     */   }
/*     */   
/*     */   boolean isPeer(int paramInt)
/*     */   {
/* 264 */     if (this.peer == paramInt) return true;
/* 265 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   void loadLabel(BufferedReader paramBufferedReader)
/*     */   {
/* 271 */     setLabel(UTIL.loadString(paramBufferedReader));
/*     */   }
/*     */   
/*     */   void loadPeer(BufferedReader paramBufferedReader) {
/* 275 */     setPeer(UTIL.loadInt(paramBufferedReader));
/*     */   }
/*     */   
/*     */   void loadPos(BufferedReader paramBufferedReader) {
/* 279 */     setPos(UTIL.loadPoint(paramBufferedReader));
/*     */   }
/*     */   
/*     */   void saveLabel(PrintWriter paramPrintWriter) {
/* 283 */     paramPrintWriter.println(this.label + "  node_label");
/*     */   }
/*     */   
/* 286 */   void saveLabel(PrintWriter paramPrintWriter, int paramInt) { paramPrintWriter.println(this.label + "  node_label_" + paramInt); }
/*     */   
/*     */   void savePeer(PrintWriter paramPrintWriter)
/*     */   {
/* 290 */     paramPrintWriter.println(this.peer + "  node_peer");
/*     */   }
/*     */   
/*     */   void savePos(PrintWriter paramPrintWriter) {
/* 294 */     UTIL.savePoint(paramPrintWriter, this.pos);
/*     */   }
/*     */   
/*     */ 
/*     */   void seeNode()
/*     */   {
/* 300 */     HelpPanel.addHelp("Nd: label=" + this.label);
/* 301 */     HelpPanel.appendHelp(" mk=" + this.mark);
/* 302 */     HelpPanel.appendHelp(" peer=" + this.peer);
/*     */   }
/*     */   
/* 305 */   void showNode() { System.out.println("  [Node]");
/* 306 */     System.out.print(" label=" + this.label);
/* 307 */     System.out.print(" mk=" + this.mark);
/* 308 */     System.out.println(" peer=" + this.peer);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Node.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */