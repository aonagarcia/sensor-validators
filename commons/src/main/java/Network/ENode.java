/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ENode
/*     */   extends BNode
/*     */ {
/*  15 */   char type = ' ';
/*  16 */   float weight = 0.0F;
/*     */   
/*     */   ENode() {}
/*     */   
/*     */   ENode(ENode paramENode)
/*     */   {
/*  22 */     super(paramENode);
/*  23 */     this.state = UTIL.getDuplicate(paramENode.getState());
/*  24 */     this.pScnt = UTIL.getDuplicate(paramENode.getParentStateCount());
/*  25 */     this.cpls = UTIL.getDuplicate(paramENode.getCondProb());
/*  26 */     this.mpls = UTIL.getDuplicate(paramENode.getMarginalProb());
/*  27 */     this.type = paramENode.type;
/*  28 */     this.weight = paramENode.getWeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isDesignNode()
/*     */   {
/*  35 */     return this.type == 'd';
/*     */   }
/*     */   
/*     */   boolean isPerfNode()
/*     */   {
/*  40 */     return this.type == 'm';
/*     */   }
/*     */   
/*     */   boolean isEnvNode()
/*     */   {
/*  45 */     return this.type == 't';
/*     */   }
/*     */   
/*     */   boolean isUtilNode()
/*     */   {
/*  50 */     return this.type == 'u';
/*     */   }
/*     */   
/*     */   int getDistCount()
/*     */   {
/*  55 */     if (this.cpls == null) return 0;
/*  56 */     return this.cpls.length;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean designHasChild()
/*     */   {
/*  62 */     return getChildCount() != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean perfHasChild()
/*     */   {
/*  68 */     return getChildCount() != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean perfHasPa()
/*     */   {
/*  74 */     return getParentCount() != 0;
/*     */   }
/*     */   
/*     */   boolean envNotAlone()
/*     */   {
/*  79 */     int i = getChildCount() + getParentCount();
/*  80 */     return i != 0;
/*     */   }
/*     */   
/*     */   boolean utilHasChild()
/*     */   {
/*  85 */     return getChildCount() != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean utilHasPa()
/*     */   {
/*  91 */     return getParentCount() != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean utilHasRightRange()
/*     */   {
/*  97 */     int i = this.cpls.length / 2;
/*  98 */     float[] arrayOfFloat = new float[i];
/*  99 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = this.cpls[(j * 2)];
/* 100 */     for (j = 0; j < i; j++) {
/* 101 */       if ((arrayOfFloat[j] < 0.0F) || (arrayOfFloat[j] > 1.0F)) { return false;
/*     */       }
/*     */     }
/* 104 */     j = 0;int k = 0;
/* 105 */     for (int m = 0; m < i; m++) {
/* 106 */       if (arrayOfFloat[m] < 1.0E-5F) j = 1;
/* 107 */       if (arrayOfFloat[m] > 0.99999F) k = 1;
/*     */     }
/* 109 */     return (j != 0) && (k != 0);
/*     */   }
/*     */   
/*     */ 
/*     */   void setWeight(float paramFloat)
/*     */   {
/* 115 */     this.weight = paramFloat;
/*     */   }
/*     */   
/*     */   float getWeight() {
/* 119 */     return this.weight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void toUtilNode()
/*     */   {
/* 129 */     this.type = 'u';
/* 130 */     setState();
/* 131 */     setCptUtil();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void toDesignNode()
/*     */   {
/* 139 */     this.type = 'd';
/* 140 */     if (getStateCount() == 0) {
/* 141 */       String[] arrayOfString = { "yes", "no" };
/* 142 */       setState(arrayOfString);
/*     */     }
/* 144 */     setCondProb();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void toEnvFacNode()
/*     */   {
/* 152 */     this.type = 't';
/* 153 */     if (getStateCount() == 0) {
/* 154 */       String[] arrayOfString = { "yes", "no" };
/* 155 */       setState(arrayOfString);
/*     */     }
/* 157 */     setCondProb();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void toPerfNode()
/*     */   {
/* 165 */     this.type = 'm';
/* 166 */     if (getStateCount() == 0) {
/* 167 */       String[] arrayOfString = { "yes", "no" };
/* 168 */       setState(arrayOfString);
/*     */     }
/* 170 */     setCondProb();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void addParenttoUtilNode(int paramInt1, int paramInt2)
/*     */   {
/* 178 */     addParent(paramInt1);
/*     */     
/* 180 */     int i = this.pls == null ? 0 : this.pls.length;
/* 181 */     int[] arrayOfInt = new int[i];
/* 182 */     for (int j = 0; j < i; j++) {
/* 183 */       if (this.pls[j] < paramInt1) { arrayOfInt[j] = this.pScnt[j];
/* 184 */       } else if (this.pls[j] > paramInt1) arrayOfInt[j] = this.pScnt[(j - 1)]; else
/* 185 */         arrayOfInt[j] = paramInt2;
/*     */     }
/* 187 */     this.pScnt = arrayOfInt;
/*     */     
/* 189 */     toUtilNode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setCptUtil(float[] paramArrayOfFloat)
/*     */   {
/* 198 */     this.cpls = UTIL.getDuplicate(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   void setCptUtil()
/*     */   {
/* 203 */     int i = 2;
/* 204 */     int j = this.pls.length;
/* 205 */     for (int k = 0; k < j; k++) i *= this.pScnt[k];
/* 206 */     this.cpls = new float[i];
/*     */     
/* 208 */     this.cpls[0] = 1.0F;this.cpls[1] = (1.0F - this.cpls[0]);
/* 209 */     for (k = 2; k < i; k++) {
/* 210 */       if (k % 2 == 0) this.cpls[k] = 0.0F; else {
/* 211 */         this.cpls[k] = (1.0F - this.cpls[(k - 1)]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   float[] getCptUtil() {
/* 217 */     return UTIL.getDuplicate(this.cpls);
/*     */   }
/*     */   
/*     */   void setCptUtilFmUtil(float[] paramArrayOfFloat)
/*     */   {
/* 222 */     int i = paramArrayOfFloat.length;
/* 223 */     float[] arrayOfFloat = new float[i * 2];
/* 224 */     for (int j = 0; j < i; j++) {
/* 225 */       arrayOfFloat[(j * 2)] = paramArrayOfFloat[j];
/* 226 */       arrayOfFloat[(j * 2 + 1)] = (1.0F - paramArrayOfFloat[j]);
/*     */     }
/* 228 */     this.cpls = arrayOfFloat;
/*     */   }
/*     */   
/*     */   float[] getUtilFmCptUtil()
/*     */   {
/* 233 */     int i = this.cpls.length / 2;
/* 234 */     float[] arrayOfFloat = new float[i];
/* 235 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = this.cpls[(j * 2)];
/* 236 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */   float getUtilFmCptUtil(int paramInt)
/*     */   {
/* 241 */     return this.cpls[(paramInt * 2)];
/*     */   }
/*     */   
/*     */   int getUtilFuncSize()
/*     */   {
/* 246 */     return this.cpls.length / 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setState(String[] paramArrayOfString)
/*     */   {
/* 254 */     if (paramArrayOfString == null) {
/* 255 */       this.state = null;return;
/*     */     }
/* 257 */     this.state = new String[paramArrayOfString.length];
/* 258 */     for (int i = 0; i < paramArrayOfString.length; i++) { this.state[i] = new String(paramArrayOfString[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   int getStateCount()
/*     */   {
/* 264 */     if (this.state == null) return 0;
/* 265 */     return this.state.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadState(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 273 */     if ((paramInt == 1) || (paramInt < 0)) {
/* 274 */       HelpPanel.showError("Incorrect state count!");return;
/*     */     }
/* 276 */     setState(UTIL.loadStringList(paramBufferedReader, paramInt));
/*     */   }
/*     */   
/*     */   void loadLabelTypeWeight(BufferedReader paramBufferedReader)
/*     */   {
/* 281 */     String[] arrayOfString = UTIL.loadStringListLine(paramBufferedReader, 3);
/* 282 */     setLabel(arrayOfString[0]);
/* 283 */     char c = arrayOfString[1].charAt(0);
/* 284 */     if ((c == 'd') || (c == 'm') || (c == 't') || (c == 'u')) { this.type = c;
/*     */     } else {
/* 286 */       HelpPanel.showError("Invalid node type found!");return;
/*     */     }
/* 288 */     this.weight = Float.valueOf(arrayOfString[2]).floatValue();
/* 289 */     if ((this.weight < 0.0F) || (this.weight > 1.0F)) {
/* 290 */       HelpPanel.showError("Invalid weight found!");return;
/*     */     }
/*     */   }
/*     */   
/*     */   static ENode loadENode(BufferedReader paramBufferedReader)
/*     */   {
/* 296 */     ENode localENode = new ENode();
/* 297 */     int i = UTIL.loadInt(paramBufferedReader);
/* 298 */     localENode.loadLabelTypeWeight(paramBufferedReader);
/* 299 */     localENode.loadState(paramBufferedReader, i);
/* 300 */     localENode.loadCP(paramBufferedReader);
/* 301 */     localENode.loadPos(paramBufferedReader);
/* 302 */     int j = UTIL.loadInt(paramBufferedReader);
/* 303 */     localENode.setCondProb(UTIL.loadRealList(paramBufferedReader, j));
/* 304 */     return localENode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 311 */     saveStateCount(paramPrintWriter, paramInt);
/* 312 */     saveLabelTypeWeight(paramPrintWriter);
/* 313 */     saveState(paramPrintWriter);
/* 314 */     saveCP(paramPrintWriter);
/* 315 */     savePos(paramPrintWriter);
/* 316 */     saveCondProb(paramPrintWriter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void saveLabelTypeWeight(PrintWriter paramPrintWriter)
/*     */   {
/* 323 */     paramPrintWriter.println(getLabel() + "  " + this.type + "  " + this.weight + "  label/type/weight");
/*     */   }
/*     */   
/*     */   void saveStateCount(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 328 */     paramPrintWriter.println(getStateCount() + "  #_of_states_var_" + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void showENode()
/*     */   {
/* 334 */     UTIL.showList(" " + getLabel() + "\ndomain=", this.state);
/* 335 */     UTIL.showList("cpls=", this.cpls);
/* 336 */     UTIL.showList("pScnt=", this.pScnt);
/* 337 */     if (isUtilNode()) UTIL.showList("weight= " + this.weight + " EU=", this.mpls);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/ENode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */