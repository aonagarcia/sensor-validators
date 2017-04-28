/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class MarkovNet
/*     */   extends ChordalGLD
/*     */ {
/*     */   public MarkovNet() {}
/*     */   
/*     */   public MarkovNet(MarkovNet paramMarkovNet)
/*     */   {
/*  16 */     super(paramMarkovNet);
/*  17 */     if ((paramMarkovNet != null) && (paramMarkovNet.nd != null)) setMarkovNet(paramMarkovNet);
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  22 */     this.nd = new MNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  27 */     setDumbNet(paramInt);
/*  28 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new MNode();
/*     */   }
/*     */   
/*     */   public void setMarkovNet(MarkovNet paramMarkovNet) {
/*  32 */     int i = paramMarkovNet.getNodeCount();
/*  33 */     setDumbNetPlus(i);
/*  34 */     for (int j = 0; j < i; j++) this.nd[j] = new MNode(paramMarkovNet.getMNode(j));
/*     */   }
/*     */   
/*     */   MNode getMNode(int paramInt) {
/*  38 */     return new MNode((MNode)this.nd[paramInt]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getStateCount(int paramInt)
/*     */   {
/*  44 */     return ((MNode)this.nd[paramInt]).getStateCount();
/*     */   }
/*     */   
/*     */   public int[] getStateCount(int[] paramArrayOfInt) {
/*  48 */     if (paramArrayOfInt == null) return null;
/*  49 */     int i = paramArrayOfInt.length;
/*  50 */     int[] arrayOfInt = new int[i];
/*  51 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getStateCount(paramArrayOfInt[j]);
/*  52 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int[] getStateCount() {
/*  56 */     int i = this.nd.length;
/*  57 */     int[] arrayOfInt = new int[i];
/*  58 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getStateCount(j);
/*  59 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public char[] getStateInit(int paramInt)
/*     */   {
/*  64 */     return ((MNode)this.nd[paramInt]).getStateInit();
/*     */   }
/*     */   
/*     */   public String[] getState(int paramInt)
/*     */   {
/*  69 */     return ((MNode)this.nd[paramInt]).getState();
/*     */   }
/*     */   
/*     */   public String[][] getState() {
/*  73 */     int i = getNodeCount();
/*  74 */     String[][] arrayOfString = new String[i][];
/*  75 */     for (int j = 0; j < i; j++) arrayOfString[j] = getState(j);
/*  76 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public int getStateIndex(int paramInt, String paramString)
/*     */   {
/*  81 */     return ((MNode)this.nd[paramInt]).getStateIndex(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setObserved(int paramInt)
/*     */   {
/*  87 */     this.nd[paramInt].setMark();
/*     */   }
/*     */   
/*  90 */   public void setObserved(int paramInt, boolean paramBoolean) { this.nd[paramInt].setMark(paramBoolean); }
/*     */   
/*     */   public boolean isObserved(int paramInt)
/*     */   {
/*  94 */     return this.nd[paramInt].isMarked();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 101 */     paramPrintWriter.println(this.nd.length + "  #_of_nodes");
/* 102 */     for (int i = 0; i < this.nd.length; i++) {
/* 103 */       paramPrintWriter.println();
/* 104 */       ((MNode)this.nd[i]).save(paramPrintWriter, i);
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadNode(BufferedReader paramBufferedReader)
/*     */   {
/* 110 */     int i = this.nd.length;
/*     */     try {
/* 112 */       for (int j = 0; j < i; j++) {
/* 113 */         paramBufferedReader.readLine();
/* 114 */         this.nd[j] = MNode.load(paramBufferedReader);
/* 115 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 119 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public static MarkovNet load(BufferedReader paramBufferedReader)
/*     */   {
/* 125 */     MarkovNet localMarkovNet = new MarkovNet();
/* 126 */     int i = UTIL.loadInt(paramBufferedReader);
/* 127 */     localMarkovNet.setDumbNet(i);
/* 128 */     localMarkovNet.loadNode(paramBufferedReader);
/* 129 */     return localMarkovNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static MarkovNet makeMoralGraph(BayesNet paramBayesNet)
/*     */   {
/* 136 */     MarkovNet localMarkovNet = new MarkovNet();
/* 137 */     int i = paramBayesNet.getNodeCount();
/* 138 */     localMarkovNet.setDumbNetPlus(i);
/* 139 */     localMarkovNet.setMoralGraph(paramBayesNet);
/* 140 */     for (int j = 0; j < i; j++)
/* 141 */       ((MNode)localMarkovNet.nd[j]).setState(((BNode)paramBayesNet.nd[j]).getState());
/* 142 */     return localMarkovNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSkeleton(BayesNet paramBayesNet)
/*     */   {
/* 149 */     int i = paramBayesNet.getNodeCount();
/* 150 */     setDumbNetPlus(i);
/* 151 */     setSkeleton(paramBayesNet);
/* 152 */     for (int j = 0; j < i; j++) {
/* 153 */       ((MNode)this.nd[j]).setPos(((BNode)paramBayesNet.nd[j]).getPos());
/* 154 */       ((MNode)this.nd[j]).setState(((BNode)paramBayesNet.nd[j]).getState());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showMarkovNet()
/*     */   {
/* 161 */     System.out.println("MarkoveNet:");
/* 162 */     for (int i = 0; i < this.nd.length; i++) {
/* 163 */       Point localPoint = getPos(i);
/* 164 */       UTIL.showList(getLabel(i) + "\t(" + localPoint.x + "," + localPoint.y + ")  ", getState(i));
/* 165 */       System.out.print("    nd[" + i + "].nls[]=");
/* 166 */       this.nd[i].showUNode();
/* 167 */       System.out.println();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/MarkovNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */