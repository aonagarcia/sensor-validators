/*    */ package Network;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ class MNode
/*    */   extends UNode
/*    */ {
/*  9 */   private String[] state = { "yes", "no" };
/*    */   
/*    */   MNode() {}
/*    */   
/*    */   MNode(MNode paramMNode)
/*    */   {
/* 15 */     super(paramMNode);
/* 16 */     this.state = UTIL.getDuplicate(paramMNode.getState());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   void setState(String[] paramArrayOfString)
/*    */   {
/* 23 */     this.state = UTIL.getDuplicate(paramArrayOfString);
/*    */   }
/*    */   
/*    */   String[] getState()
/*    */   {
/* 28 */     return UTIL.getDuplicate(this.state);
/*    */   }
/*    */   
/*    */   int getStateCount()
/*    */   {
/* 33 */     return this.state.length;
/*    */   }
/*    */   
/*    */   char[] getStateInit()
/*    */   {
/* 38 */     char[] arrayOfChar = new char[this.state.length];
/* 39 */     for (int i = 0; i < this.state.length; i++) arrayOfChar[i] = this.state[i].charAt(0);
/* 40 */     return arrayOfChar;
/*    */   }
/*    */   
/*    */   int getStateIndex(String paramString)
/*    */   {
/* 45 */     for (int i = 0; i < this.state.length; i++) if (paramString.equals(this.state[i])) return i;
/* 46 */     HelpPanel.showError("Invalid state name!");
/* 47 */     return -1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   void saveStateCount(PrintWriter paramPrintWriter, int paramInt)
/*    */   {
/* 54 */     paramPrintWriter.println(this.state.length + "  #_of_states_var_" + paramInt);
/*    */   }
/*    */   
/*    */   void saveState(PrintWriter paramPrintWriter)
/*    */   {
/* 59 */     UTIL.saveStringList(paramPrintWriter, this.state);
/*    */   }
/*    */   
/*    */   void save(PrintWriter paramPrintWriter, int paramInt)
/*    */   {
/* 64 */     saveStateCount(paramPrintWriter, paramInt);
/* 65 */     saveLabel(paramPrintWriter);
/* 66 */     saveState(paramPrintWriter);
/* 67 */     saveNeighbor(paramPrintWriter);
/* 68 */     savePos(paramPrintWriter);
/*    */   }
/*    */   
/*    */   void loadState(BufferedReader paramBufferedReader, int paramInt)
/*    */   {
/* 73 */     if (paramInt < 2) {
/* 74 */       HelpPanel.showError("Incorrect state count!");return;
/*    */     }
/* 76 */     setState(UTIL.loadStringList(paramBufferedReader, paramInt));
/*    */   }
/*    */   
/*    */   static MNode load(BufferedReader paramBufferedReader)
/*    */   {
/* 81 */     MNode localMNode = new MNode();
/* 82 */     int i = UTIL.loadInt(paramBufferedReader);
/* 83 */     localMNode.loadLabel(paramBufferedReader);
/* 84 */     localMNode.loadState(paramBufferedReader, i);
/* 85 */     localMNode.loadNeighbor(paramBufferedReader);
/* 86 */     localMNode.loadPos(paramBufferedReader);
/* 87 */     return localMNode;
/*    */   }
/*    */   
/*    */   static MNode loadLabelState(BufferedReader paramBufferedReader)
/*    */   {
/* 92 */     MNode localMNode = new MNode();
/* 93 */     localMNode.setLabel(UTIL.loadString(paramBufferedReader));
/* 94 */     int i = UTIL.loadInt(paramBufferedReader);
/* 95 */     localMNode.loadState(paramBufferedReader, i);
/* 96 */     return localMNode;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/MNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */