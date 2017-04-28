/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Msbn
/*     */   extends Msdag
/*     */ {
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  16 */     this.nd = new Subnet[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  21 */     setDumbNet(paramInt);
/*  22 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new Subnet();
/*     */   }
/*     */   
/*     */   Subnet getSubnet(int paramInt) {
/*  26 */     return new Subnet((Subnet)this.nd[paramInt]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  33 */   void setBelief(int paramInt) { ((Subnet)this.nd[paramInt]).setBelief(); }
/*     */   
/*     */   public void setBelief() {
/*  36 */     for (int i = 0; i < getNodeCount(); i++) { setBelief(i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  41 */   void getSet(int paramInt) { ((Subnet)this.nd[paramInt]).getSet(); }
/*     */   
/*     */   public void getSet() {
/*  44 */     for (int i = 0; i < getNodeCount(); i++) { getSet(i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  49 */   void unifyBelief(int paramInt) { ((Subnet)this.nd[paramInt]).unifyBelief(); }
/*     */   
/*     */   public void unifyBelief() {
/*  52 */     for (int i = 0; i < getNodeCount(); i++) unifyBelief(i);
/*     */   }
/*     */   
/*     */   void collectBelief(int paramInt)
/*     */   {
/*  57 */     ((Subnet)this.nd[paramInt]).collectBelief(-1, paramInt, (Subnet[])this.nd);
/*     */   }
/*     */   
/*     */   void distributeBelief(int paramInt)
/*     */   {
/*  62 */     ((Subnet)this.nd[paramInt]).distributeBelief(-1, paramInt, (Subnet[])this.nd);
/*     */   }
/*     */   
/*     */   public void beliefInitialization()
/*     */   {
/*  67 */     int i = 0;
/*  68 */     beliefInitialization(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void beliefInitialization(int paramInt)
/*     */   {
/*  75 */     HelpPanel.addHelp("Collecting belief at subnets: \n");
/*  76 */     collectBelief(paramInt);
/*  77 */     HelpPanel.addHelp("Distributing belief at subnets: \n");
/*  78 */     distributeBelief(paramInt);
/*     */   }
/*     */   
/*     */   public void communicateBelief(int paramInt)
/*     */   {
/*  83 */     beliefInitialization(paramInt);
/*  84 */     HelpPanel.addHelp("Collecting observed at subnets: ");
/*  85 */     collectObserved(paramInt);
/*  86 */     HelpPanel.addHelp("Distributing observed at\tsubnets: ");
/*  87 */     distributeObserved(paramInt);
/*  88 */     HelpPanel.addHelp("Communication completed.");
/*     */   }
/*     */   
/*  91 */   public void communicateBelief() { int i = 0;
/*  92 */     communicateBelief(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDumbLinkageBelief(int paramInt)
/*     */   {
/*  99 */     ((Subnet)this.nd[paramInt]).setDumbLinkageBelief();
/*     */   }
/*     */   
/*     */   public void setDumbLinkageBelief() {
/* 103 */     for (int i = 0; i < getNodeCount(); i++) { setDumbLinkageBelief(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLinkageBelief()
/*     */   {
/* 109 */     HelpPanel.addHelp("Set linkage belief at subnets ");
/* 110 */     for (int i = 0; i < getNodeCount(); i++) {
/* 111 */       HelpPanel.appendHelp(" " + i);
/* 112 */       ((Subnet)this.nd[i]).setLinkageBelief();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BayesNet getNet(int paramInt)
/*     */   {
/* 120 */     return ((Subnet)this.nd[paramInt]).getNet();
/*     */   }
/*     */   
/*     */   public JoinForest getJoinTree(int paramInt)
/*     */   {
/* 125 */     return ((Subnet)this.nd[paramInt]).getJoinTree();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadNode(BufferedReader paramBufferedReader, String paramString)
/*     */   {
/* 133 */     int i = this.nd.length;
/*     */     try {
/* 135 */       for (int j = 0; j < i; j++) {
/* 136 */         paramBufferedReader.readLine();
/* 137 */         this.nd[j] = Subnet.loadHnode(paramBufferedReader, paramString);
/* 138 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 142 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Msbn loadHyperTree(BufferedReader paramBufferedReader, String paramString)
/*     */   {
/* 150 */     Msbn localMsbn = new Msbn();
/* 151 */     int i = UTIL.loadInt(paramBufferedReader);
/* 152 */     localMsbn.setDumbNet(i);
/* 153 */     localMsbn.loadNode(paramBufferedReader, paramString);
/* 154 */     localMsbn.setNodeNbr();
/* 155 */     return localMsbn;
/*     */   }
/*     */   
/*     */   public void loadNodeBn()
/*     */   {
/* 160 */     for (int i = 0; i < this.nd.length; i++) loadNodeBn(i);
/*     */   }
/*     */   
/*     */   public void loadNodeBn(int paramInt) {
/* 164 */     ((Subnet)this.nd[paramInt]).loadBn();
/*     */   }
/*     */   
/*     */   public void loadNodeJt()
/*     */   {
/* 169 */     for (int i = 0; i < this.nd.length; i++) loadNodeJt(i);
/*     */   }
/*     */   
/*     */   public void loadNodeJt(int paramInt) {
/* 173 */     ((Subnet)this.nd[paramInt]).loadJt();
/*     */   }
/*     */   
/*     */   public void loadLinkageTree(int paramInt)
/*     */   {
/* 178 */     ((Subnet)this.nd[paramInt]).loadLinkageTree();
/*     */   }
/*     */   
/*     */   public void loadLinkageTree()
/*     */   {
/* 183 */     for (int i = 0; i < this.nd.length; i++) loadLinkageTree(i);
/*     */   }
/*     */   
/*     */   public void saveJoinTree()
/*     */   {
/* 188 */     for (int i = 0; i < getNodeCount(); i++) ((Subnet)this.nd[i]).saveJoinTree();
/*     */   }
/*     */   
/*     */   public void saveLinkageTree()
/*     */   {
/* 193 */     for (int i = 0; i < getNodeCount(); i++) ((Subnet)this.nd[i]).saveLinkageTree();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Msbn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */