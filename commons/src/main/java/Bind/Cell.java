/*     */ package Bind;
/*     */ 
/*     */ import ConstraintNet.BinaryConNet;
/*     */ import Network.BayesNet;
/*     */ import Network.DesignNet;
/*     */ import Network.UTIL;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ class Cell
/*     */ {
/*     */   MainFrameC mf;
/* 171 */   String name = null;
/* 172 */   String host = null;
/* 173 */   String addr = null;
/* 174 */   int port = 0;
/* 175 */   CellNb[] nb = null;
/* 176 */   String path = null;
/*     */   
/*     */   static final int REGISTRATION = 0;
/*     */   static final int REGISTERED = 1;
/*     */   static final int NB_INFO = 2;
/*     */   static final int NB_RECEIVED = 3;
/*     */   static final int ERROR = -1;
/*     */   
/*     */   Cell(String paramString1, String paramString2, String paramString3, int paramInt, MainFrameC paramMainFrameC)
/*     */   {
/* 186 */     this.mf = paramMainFrameC;
/* 187 */     this.name = new String(paramString1);
/* 188 */     this.path = new String(paramString2);
/*     */     try {
/* 190 */       this.host = InetAddress.getLocalHost().getHostName();
/* 191 */       this.addr = InetAddress.getLocalHost().getHostAddress();
/*     */     }
/*     */     catch (UnknownHostException localUnknownHostException) {}
/*     */     
/* 195 */     ServerSocket localServerSocket = null;
/*     */     try {
/* 197 */       localServerSocket = new ServerSocket(this.port);
/* 198 */       this.port = localServerSocket.getLocalPort();
/* 199 */       this.mf.println("\nAgent: " + this.host + " addr=" + this.addr + " port=" + this.port);
/*     */     } catch (IOException localIOException1) {
/* 201 */       this.mf.println("server socket: " + localIOException1.toString());
/*     */     }
/*     */     Object localObject1;
/*     */     String str1;
/*     */     Object localObject2;
/* 206 */     try { Socket localSocket1 = new Socket(paramString3, paramInt);
/* 207 */       localObject1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(localSocket1.getOutputStream())), true);
/*     */       
/* 209 */       str1 = new String("0 " + this.name + " " + this.host + " " + this.addr + " " + this.port);
/*     */       
/* 211 */       ((PrintWriter)localObject1).println(str1);this.mf.println("sent: " + str1);
/*     */       
/* 213 */       localObject2 = new BufferedReader(new InputStreamReader(localSocket1.getInputStream()));
/*     */       
/* 215 */       String str2 = new StringTokenizer(((BufferedReader)localObject2).readLine()).nextToken();
/* 216 */       int j = Integer.parseInt(str2);
/* 217 */       this.mf.println("Ack received: " + j);
/* 218 */       if (j != 1) {
/* 219 */         this.mf.println("Registration error.");System.exit(0);
/*     */       }
/*     */       
/* 222 */       ((BufferedReader)localObject2).close();((PrintWriter)localObject1).close();localSocket1.close();
/*     */     } catch (IOException localIOException2) {
/* 224 */       this.mf.println("client socket: " + localIOException2.toString());
/*     */     }
/*     */     
/*     */ 
/* 228 */     this.mf.println("Wait neighbor info from binder ...");
/*     */     try {
/* 230 */       Socket localSocket2 = localServerSocket.accept();
/* 231 */       this.mf.println("Binder connection accepted.");
/*     */       
/* 233 */       localObject1 = new BufferedReader(new InputStreamReader(localSocket2.getInputStream()));
/*     */       
/* 235 */       str1 = ((BufferedReader)localObject1).readLine();
/* 236 */       this.mf.println("Binder msg received: \n\t" + str1);
/*     */       
/* 238 */       localObject2 = new StringTokenizer(str1);
/* 239 */       if (Integer.parseInt(((StringTokenizer)localObject2).nextToken()) != 2) {
/* 240 */         this.mf.println("Err: expect nb info.");System.exit(0);
/*     */       }
/* 242 */       int i = Integer.parseInt(((StringTokenizer)localObject2).nextToken());
/*     */       
/* 244 */       String[] arrayOfString = new String[i];
/* 245 */       for (int k = 0; k < i; k++) {
/* 246 */         arrayOfString[k] = ((BufferedReader)localObject1).readLine();
/* 247 */         this.mf.println("\t" + arrayOfString[k]);
/*     */       }
/* 249 */       setNb(arrayOfString);
/* 250 */       this.mf.println("Nb info record set.");
/*     */       
/* 252 */       saveAdrFile();saveSepFile();
/* 253 */       this.mf.println(this.path + ".adr and " + this.path + ".nbr saved.");
/*     */       
/* 255 */       PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(localSocket2.getOutputStream())), true);
/*     */       
/* 257 */       localPrintWriter.println("3 " + this.name);
/*     */       
/* 259 */       ((BufferedReader)localObject1).close();localPrintWriter.close();localServerSocket.close();
/*     */     } catch (IOException localIOException3) {
/* 261 */       this.mf.println("Receive from binder error: " + localIOException3.toString());
/*     */     } catch (SecurityException localSecurityException) {
/* 263 */       this.mf.println("Receive from binder error: " + localSecurityException.toString());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 270 */     this.mf.println("Binding complete!");
/*     */   }
/*     */   
/*     */ 
/*     */   void setNb(String[] paramArrayOfString)
/*     */   {
/* 276 */     int i = paramArrayOfString.length;
/* 277 */     this.nb = new CellNb[i];
/* 278 */     for (int j = 0; j < i; j++) {
/* 279 */       String[] arrayOfString1 = UTIL.getStringList(paramArrayOfString[j]);
/* 280 */       this.nb[j] = new CellNb();
/* 281 */       this.nb[j].setName(arrayOfString1[0]);
/* 282 */       this.nb[j].setHost(arrayOfString1[1]);
/* 283 */       this.nb[j].setAddr(arrayOfString1[2]);
/* 284 */       this.nb[j].setPort(Integer.parseInt(arrayOfString1[3]));
/* 285 */       int k = Integer.parseInt(arrayOfString1[4]);
/* 286 */       String[] arrayOfString2 = new String[k];
/* 287 */       for (int m = 0; m < k; m++) arrayOfString2[m] = arrayOfString1[(m + 5)];
/* 288 */       this.nb[j].setSepset(arrayOfString2);
/*     */     }
/*     */   }
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
/*     */   void saveSepFile()
/*     */   {
/* 310 */     int i = 0;
/* 311 */     String[] arrayOfString1 = null;
/*     */     
/* 313 */     BayesNet localBayesNet = BayesNet.load(this.path + ".bn");
/* 314 */     Object localObject; int n; if (localBayesNet != null) {
/* 315 */       i = localBayesNet.getNodeCount();
/* 316 */       arrayOfString1 = new String[i];
/* 317 */       for (int j = 0; j < i; j++) arrayOfString1[j] = localBayesNet.getLabel(j);
/*     */     }
/*     */     else {
/* 320 */       localObject = DesignNet.loadDn(this.path + ".dn");
/* 321 */       if (localObject != null) {
/* 322 */         i = ((DesignNet)localObject).getNodeCount();
/* 323 */         arrayOfString1 = new String[i];
/* 324 */         for (int k = 0; k < i; k++) arrayOfString1[k] = ((DesignNet)localObject).getLabel(k);
/*     */       }
/*     */       else {
/* 327 */         BinaryConNet localBinaryConNet = BinaryConNet.load(this.path + ".bcn");
/* 328 */         if (localBinaryConNet != null) {
/* 329 */           i = localBinaryConNet.getNodeCount();
/* 330 */           arrayOfString1 = new String[i];
/* 331 */           for (n = 0; n < i; n++) arrayOfString1[n] = localBinaryConNet.getLabel(n);
/*     */         }
/*     */         else {
/* 334 */           this.mf.println("Err: Cannot load subnet file (.bn, .dn or .bcn).");
/* 335 */           System.exit(0);
/*     */         }
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 341 */       localObject = new PrintWriter(new FileWriter(this.path + ".nbr"));
/* 342 */       int m = this.nb.length;
/* 343 */       ((PrintWriter)localObject).println(m + "  #_of_neighbors");
/*     */       
/* 345 */       for (n = 0; n < m; n++) {
/* 346 */         ((PrintWriter)localObject).println();
/* 347 */         ((PrintWriter)localObject).println(this.nb[n].getName() + "  nb_" + n + "_name");
/* 348 */         int i1 = this.nb[n].getSepsetSize();
/* 349 */         ((PrintWriter)localObject).print(i1 + "  ");
/* 350 */         String[] arrayOfString2 = this.nb[n].getSepset();
/* 351 */         for (int i2 = 0; i2 < i1; i2++) {
/* 352 */           for (int i3 = 0; i3 < i; i3++) {
/* 353 */             if (arrayOfString2[i2].equals(arrayOfString1[i3])) ((PrintWriter)localObject).print(i3 + " ");
/*     */           }
/*     */         }
/* 356 */         ((PrintWriter)localObject).println("  sepset_size+local_index");
/* 357 */         UTIL.saveStringListMLine((PrintWriter)localObject, arrayOfString2);
/*     */       }
/* 359 */       ((PrintWriter)localObject).close();
/*     */     } catch (IOException localIOException) {
/* 361 */       this.mf.println("Save .nbr file: " + localIOException.toString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void saveAdrFile()
/*     */   {
/*     */     try
/*     */     {
/* 373 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(this.path + ".adr"));
/* 374 */       int i = this.nb.length;
/* 375 */       localPrintWriter.println(i + "  #_of_neighbors");
/*     */       
/* 377 */       localPrintWriter.println(this.addr + " " + this.port + " " + this.name + " " + this.host);
/* 378 */       for (int j = 0; j < i; j++) {
/* 379 */         localPrintWriter.println(this.nb[j].getAddr() + " " + this.nb[j].getPort() + " " + this.nb[j].getName() + " " + this.nb[j].getHost());
/*     */       }
/* 381 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 383 */       this.mf.println("Save .adr file: " + localIOException.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/Cell.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */