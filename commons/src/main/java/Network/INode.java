/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ class INode
/*     */   extends BNode
/*     */ {
/*  11 */   float[] vRange = null;
/*     */   
/*     */   INode() {}
/*     */   
/*     */   INode(INode paramINode)
/*     */   {
/*  17 */     super(paramINode);
/*  18 */     this.state = UTIL.getDuplicate(paramINode.getState());
/*  19 */     this.pScnt = UTIL.getDuplicate(paramINode.getParentStateCount());
/*  20 */     this.cpls = UTIL.getDuplicate(paramINode.getCondProb());
/*  21 */     this.mpls = UTIL.getDuplicate(paramINode.getMarginalProb());
/*  22 */     this.vRange = UTIL.getDuplicate(paramINode.vRange);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isValueNode()
/*     */   {
/*  30 */     return getStateCount() == 0;
/*     */   }
/*     */   
/*     */   int getDistCount()
/*     */   {
/*  35 */     if (this.cpls == null) return 0;
/*  36 */     return this.cpls.length;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isDecisionNode()
/*     */   {
/*  42 */     return getDistCount() == 0;
/*     */   }
/*     */   
/*     */   boolean isChanceNode()
/*     */   {
/*  47 */     return (getStateCount() > 0) && (getDistCount() > 0);
/*     */   }
/*     */   
/*     */ 
/*     */   boolean hasDecChild()
/*     */   {
/*  53 */     if (!isDecisionNode()) {
/*  54 */       HelpPanel.addHelp("Error:\tCheck child for\tnon-decision node.");
/*  55 */       return false;
/*     */     }
/*  57 */     return getChildCount() != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void toValueNode()
/*     */   {
/*  67 */     this.state = null;
/*  68 */     setUtil();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void toDecisionNode()
/*     */   {
/*  75 */     if (getStateCount() == 0) {
/*  76 */       String[] arrayOfString = { "yes", "no" };
/*  77 */       setState(arrayOfString);
/*     */     }
/*  79 */     this.cpls = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void toChanceNode()
/*     */   {
/*  86 */     if (getStateCount() == 0) {
/*  87 */       String[] arrayOfString = { "yes", "no" };
/*  88 */       setState(arrayOfString);
/*     */     }
/*  90 */     setCondProb();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void addParentToValueNode(int paramInt1, int paramInt2)
/*     */   {
/*  98 */     addParent(paramInt1);
/*     */     
/* 100 */     int i = this.pls == null ? 0 : this.pls.length;
/* 101 */     int[] arrayOfInt = new int[i];
/* 102 */     for (int j = 0; j < i; j++) {
/* 103 */       if (this.pls[j] < paramInt1) { arrayOfInt[j] = this.pScnt[j];
/* 104 */       } else if (this.pls[j] > paramInt1) arrayOfInt[j] = this.pScnt[(j - 1)]; else
/* 105 */         arrayOfInt[j] = paramInt2;
/*     */     }
/* 107 */     this.pScnt = arrayOfInt;
/*     */     
/* 109 */     toValueNode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   String[] getDecision()
/*     */   {
/* 116 */     if (isDecisionNode()) return this.state;
/* 117 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   float[] getValue()
/*     */   {
/* 123 */     if (!isValueNode()) return null;
/* 124 */     int i = this.cpls.length;
/* 125 */     float[] arrayOfFloat = new float[i];
/* 126 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = (this.cpls[j] * this.vRange[1] + this.vRange[0]);
/* 127 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */   void setUtil(float[] paramArrayOfFloat)
/*     */   {
/* 132 */     if (!isValueNode()) {
/* 133 */       HelpPanel.addHelp("Error:\tSet utility in wrong node.");return;
/*     */     }
/* 135 */     setVRange(paramArrayOfFloat);
/* 136 */     int i = paramArrayOfFloat.length;
/* 137 */     this.cpls = new float[i];
/* 138 */     for (int j = 0; j < i; j++) this.cpls[j] = ((paramArrayOfFloat[j] - this.vRange[0]) / this.vRange[1]);
/*     */   }
/*     */   
/*     */   void setUtil() {
/* 142 */     int i = 1;
/* 143 */     int j = this.pls.length;
/* 144 */     for (int k = 0; k < j; k++) i *= this.pScnt[k];
/* 145 */     this.cpls = new float[i];
/* 146 */     this.cpls[0] = 1.0F;
/* 147 */     for (k = 1; k < i; k++) { this.cpls[k] = 0.0F;
/*     */     }
/* 149 */     this.vRange = new float[2];
/* 150 */     this.vRange[0] = 0.0F;this.vRange[1] = 1.0F;
/*     */   }
/*     */   
/*     */   float[] getUtil()
/*     */   {
/* 155 */     return UTIL.getDuplicate(this.cpls);
/*     */   }
/*     */   
/*     */   void setVRange(float[] paramArrayOfFloat)
/*     */   {
/* 160 */     if (!isValueNode()) {
/* 161 */       HelpPanel.addHelp("Error:\tSet vRange in wrong node.");return;
/*     */     }
/* 163 */     this.vRange = new float[2];
/* 164 */     this.vRange[0] = MATH.min(paramArrayOfFloat);
/* 165 */     this.vRange[1] = (MATH.max(paramArrayOfFloat) - this.vRange[0]);
/*     */   }
/*     */   
/*     */   float[] getVRange()
/*     */   {
/* 170 */     if (!isValueNode()) {
/* 171 */       HelpPanel.addHelp("Error:\tRetrieve vRange\tin wrong node.");
/* 172 */       return null;
/*     */     }
/* 174 */     return UTIL.getDuplicate(this.vRange);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setState(String[] paramArrayOfString)
/*     */   {
/* 182 */     if (paramArrayOfString == null) {
/* 183 */       this.state = null;return;
/*     */     }
/* 185 */     this.state = new String[paramArrayOfString.length];
/* 186 */     for (int i = 0; i < paramArrayOfString.length; i++) { this.state[i] = new String(paramArrayOfString[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   int getStateCount()
/*     */   {
/* 192 */     if (this.state == null) return 0;
/* 193 */     return this.state.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadState(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 201 */     if ((paramInt == 1) || (paramInt < 0)) {
/* 202 */       HelpPanel.showError("Incorrect state count!");return;
/*     */     }
/* 204 */     setState(UTIL.loadStringList(paramBufferedReader, paramInt));
/*     */   }
/*     */   
/*     */   static INode loadINode(BufferedReader paramBufferedReader)
/*     */   {
/* 209 */     INode localINode = new INode();
/* 210 */     int i = UTIL.loadInt(paramBufferedReader);
/* 211 */     localINode.loadLabel(paramBufferedReader);
/* 212 */     localINode.loadState(paramBufferedReader, i);
/* 213 */     localINode.loadCP(paramBufferedReader);
/* 214 */     localINode.loadPos(paramBufferedReader);
/*     */     
/* 216 */     int j = UTIL.loadInt(paramBufferedReader);
/* 217 */     if (j > 0) {
/* 218 */       if (i == 0) {
/* 219 */         localINode.setUtil(UTIL.loadRealList(paramBufferedReader, j));
/*     */       } else
/* 221 */         localINode.setCondProb(UTIL.loadRealList(paramBufferedReader, j));
/*     */     } else {
/* 223 */       localINode.cpls = null;
/*     */     }
/* 225 */     return localINode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 232 */     saveStateCount(paramPrintWriter, paramInt);
/* 233 */     saveLabel(paramPrintWriter);
/* 234 */     if (getStateCount() > 0) saveState(paramPrintWriter);
/* 235 */     saveCP(paramPrintWriter);
/* 236 */     savePos(paramPrintWriter);
/* 237 */     if (isChanceNode()) { saveCondProb(paramPrintWriter);
/* 238 */     } else if (isValueNode()) saveValue(paramPrintWriter); else {
/* 239 */       paramPrintWriter.println("0\t distr_cardinality");
/*     */     }
/*     */   }
/*     */   
/*     */   void saveStateCount(PrintWriter paramPrintWriter, int paramInt) {
/* 244 */     paramPrintWriter.println(getStateCount() + "  #_of_states_var_" + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   void saveValue(PrintWriter paramPrintWriter)
/*     */   {
/* 250 */     paramPrintWriter.println(this.cpls.length + "\t distr_cardinality");
/* 251 */     UTIL.saveRealList(paramPrintWriter, getValue());
/*     */   }
/*     */   
/*     */ 
/*     */   public void showINode()
/*     */   {
/* 257 */     UTIL.showList("Dist=", this.cpls);
/* 258 */     if (isValueNode()) UTIL.showList("vRange=", this.vRange);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/INode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */