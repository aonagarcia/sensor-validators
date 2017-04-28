/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ class XNode
/*     */   extends MNode
/*     */ {
/*  11 */   int[] pls = null;
/*  12 */   int[] cls = null;
/*  13 */   Point[] pCand = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   XNode() {}
/*     */   
/*     */ 
/*     */ 
/*     */   XNode(MNode paramMNode)
/*     */   {
/*  29 */     super(paramMNode);
/*     */   }
/*     */   
/*     */   XNode(XNode paramXNode) {
/*  33 */     super(paramXNode);
/*  34 */     this.pls = UTIL.getDuplicate(paramXNode.getParent());
/*  35 */     this.cls = UTIL.getDuplicate(paramXNode.getChild());
/*  36 */     this.pCand = UTIL.getDuplicate(paramXNode.getParentCand());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setParent(int[] paramArrayOfInt)
/*     */   {
/*  43 */     this.pls = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   boolean isParent(int paramInt)
/*     */   {
/*  48 */     if (this.pls == null) return false;
/*  49 */     if (MATH.member(paramInt, this.pls)) return true;
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   void addParent(int paramInt)
/*     */   {
/*  55 */     this.pls = MATH.addMember(paramInt, this.pls);
/*     */   }
/*     */   
/*     */   void delParent(int paramInt)
/*     */   {
/*  60 */     this.pls = MATH.delMember(paramInt, this.pls);
/*     */   }
/*     */   
/*     */   int[] getParent()
/*     */   {
/*  65 */     return UTIL.getDuplicate(this.pls);
/*     */   }
/*     */   
/*     */   int getParentCount()
/*     */   {
/*  70 */     if (this.pls == null) return 0;
/*  71 */     return this.pls.length;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getOutParent(int[] paramArrayOfInt)
/*     */   {
/*  77 */     if (this.pls == null) return null;
/*  78 */     return MATH.setDifference(this.pls, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getInParent(int[] paramArrayOfInt)
/*     */   {
/*  84 */     if (this.pls == null) return null;
/*  85 */     return MATH.getIntersection(this.pls, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setChild(int[] paramArrayOfInt)
/*     */   {
/*  92 */     this.cls = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void addChild(int paramInt)
/*     */   {
/*  97 */     this.cls = MATH.addMember(paramInt, this.cls);
/*     */   }
/*     */   
/*     */   void delChild(int paramInt)
/*     */   {
/* 102 */     this.cls = MATH.delMember(paramInt, this.cls);
/*     */   }
/*     */   
/*     */   int[] getChild()
/*     */   {
/* 107 */     return UTIL.getDuplicate(this.cls);
/*     */   }
/*     */   
/*     */   int getChildCount()
/*     */   {
/* 112 */     if (this.cls == null) return 0;
/* 113 */     return this.cls.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setParentCand(Point[] paramArrayOfPoint)
/*     */   {
/* 120 */     this.pCand = UTIL.getDuplicate(paramArrayOfPoint);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void addParentCand(int[] paramArrayOfInt)
/*     */   {
/* 127 */     int[] arrayOfInt = MATH.getIntersection(getNeighbor(), paramArrayOfInt);
/* 128 */     int i = arrayOfInt == null ? 0 : arrayOfInt.length;
/* 129 */     if (i < 2) { return;
/*     */     }
/* 131 */     for (int j = 0; j < i - 1; j++) {
/* 132 */       for (int k = j + 1; k < i; k++) {
/* 133 */         addParentCand(new Point(arrayOfInt[j], arrayOfInt[k]));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void addParentCand(Point paramPoint) {
/* 139 */     this.pCand = MATH.appendMember(paramPoint, this.pCand);
/*     */   }
/*     */   
/*     */   public void delParentCand(Point paramPoint)
/*     */   {
/* 144 */     if (MATH.member(paramPoint, this.pCand))
/* 145 */       this.pCand = MATH.delMember(paramPoint, this.pCand);
/*     */   }
/*     */   
/*     */   public void delParentCand(int paramInt) {
/* 149 */     if (this.pCand == null) return;
/* 150 */     Point[] arrayOfPoint = this.pCand;
/* 151 */     this.pCand = null;
/* 152 */     for (int i = 0; i < arrayOfPoint.length; i++) {
/* 153 */       if ((arrayOfPoint[i].x != paramInt) && (arrayOfPoint[i].y != paramInt)) addParentCand(arrayOfPoint[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   Point[] getParentCand()
/*     */   {
/* 159 */     return UTIL.getDuplicate(this.pCand);
/*     */   }
/*     */   
/*     */   int getParentCandCount()
/*     */   {
/* 164 */     if (this.pCand == null) return 0;
/* 165 */     return this.pCand.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static XNode loadXNodeFromMN(BufferedReader paramBufferedReader)
/*     */   {
/* 172 */     XNode localXNode = new XNode();
/* 173 */     load(paramBufferedReader);
/* 174 */     return localXNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public void showXNode()
/*     */   {
/* 180 */     HelpPanel.showList("pls", getParent());
/* 181 */     HelpPanel.showList("cls", getChild());
/* 182 */     HelpPanel.showList("nls", getNeighbor());
/* 183 */     HelpPanel.showList("pCand", getParentCand());
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/XNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */