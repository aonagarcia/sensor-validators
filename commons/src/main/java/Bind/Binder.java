/*     */ package Bind;
/*     */ 
/*     */ import Network.UTIL;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileReader;
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
/*     */ class Binder
/*     */ {
/*     */   MainFrame mf;
/* 162 */   String host = null;
/* 163 */   String addr = null;
/* 164 */   int port = 33333;
/* 165 */   CellMb[] cell = null;
/*     */   
/*     */   static final int REGISTRATION = 0;
/*     */   static final int REGISTERED = 1;
/*     */   static final int NB_INFO = 2;
/*     */   static final int NB_RECEIVED = 3;
/*     */   static final int ERROR = -1;
/*     */   
/*     */   public Binder(String paramString, MainFrame paramMainFrame)
/*     */   {
/* 175 */     this.mf = paramMainFrame;
/*     */     
/* 177 */     load(paramString);
/* 178 */     setHostAddr();
/* 179 */     registration();
/* 180 */     sendNbInfo();
/*     */   }
/*     */   
/*     */ 
/*     */   public void load(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 188 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/*     */       
/* 190 */       int i = UTIL.loadInt(localBufferedReader);
/* 191 */       this.cell = new CellMb[i];
/* 192 */       for (int j = 0; j < i; j++) { this.cell[j] = new CellMb();
/*     */       }
/* 194 */       for (j = 0; j < i; j++) {
/* 195 */         UTIL.skipLine(localBufferedReader);
/* 196 */         this.cell[j].setName(UTIL.loadString(localBufferedReader));
/* 197 */         this.cell[j].setNb(UTIL.loadStringListLine(localBufferedReader));
/* 198 */         UTIL.skipLine(localBufferedReader);
/*     */       }
/*     */       
/* 201 */       UTIL.skipLine(localBufferedReader);UTIL.skipLine(localBufferedReader);
/*     */       
/* 203 */       j = 0;
/* 204 */       for (int k = 0; k < i; k++) j += this.cell[k].getNbCount();
/* 205 */       k = j / 2;
/* 206 */       String[][] arrayOfString1 = new String[k][];
/* 207 */       String[][] arrayOfString2 = new String[k][];
/*     */       
/* 209 */       for (int m = 0; m < k; m++) {
/* 210 */         UTIL.skipLine(localBufferedReader);
/* 211 */         arrayOfString2[m] = UTIL.loadStringListLine(localBufferedReader, 2);
/* 212 */         int n = UTIL.loadInt(localBufferedReader);
/* 213 */         arrayOfString1[m] = UTIL.loadStringListMLine(localBufferedReader, n);
/*     */       }
/* 215 */       setSepset(arrayOfString2, arrayOfString1, this.cell);
/*     */       
/* 217 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 219 */       this.mf.println("Error: IOExeption reading .bdr file.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   int getCellIndex(String paramString)
/*     */   {
/* 226 */     if (this.cell == null) {
/* 227 */       this.mf.println("Error: null cell list.");return -1;
/*     */     }
/*     */     
/* 230 */     for (int i = 0; i < this.cell.length; i++) {
/* 231 */       if (this.cell[i].getName().equals(paramString)) return i;
/*     */     }
/* 233 */     this.mf.println("Error: invalid cell name.");return -1;
/*     */   }
/*     */   
/*     */   int getNbPairIndex(String[][] paramArrayOfString, String paramString1, String paramString2)
/*     */   {
/* 238 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 239 */       if (((paramArrayOfString[i][0].equals(paramString1)) && (paramArrayOfString[i][1].equals(paramString2))) || ((paramArrayOfString[i][1].equals(paramString1)) && (paramArrayOfString[i][0].equals(paramString2))))
/* 240 */         return i;
/*     */     }
/* 242 */     this.mf.println("Error: invalid cell names.");return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   void setSepset(String[][] paramArrayOfString1, String[][] paramArrayOfString2, CellMb[] paramArrayOfCellMb)
/*     */   {
/* 248 */     for (int i = 0; i < paramArrayOfCellMb.length; i++) {
/* 249 */       int j = paramArrayOfCellMb[i].getNbCount();
/* 250 */       String[][] arrayOfString = (String[][])null;
/* 251 */       String str1 = paramArrayOfCellMb[i].getName();
/* 252 */       for (int k = 0; k < j; k++) {
/* 253 */         String str2 = paramArrayOfCellMb[i].getNb(k);
/* 254 */         int m = getNbPairIndex(paramArrayOfString1, str1, str2);
/* 255 */         arrayOfString = UTIL.appendToArray(arrayOfString, paramArrayOfString2[m]);
/*     */       }
/* 257 */       paramArrayOfCellMb[i].setSepset(arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setHostAddr()
/*     */   {
/*     */     try
/*     */     {
/* 266 */       this.host = InetAddress.getLocalHost().getHostName();
/* 267 */       this.addr = InetAddress.getLocalHost().getHostAddress();
/*     */     } catch (UnknownHostException localUnknownHostException) {
/* 269 */       this.mf.println("Set host/addr: " + localUnknownHostException.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   void registration()
/*     */   {
/*     */     try {
/* 276 */       ServerSocket localServerSocket = new ServerSocket(this.port);
/* 277 */       this.port = localServerSocket.getLocalPort();
/* 278 */       this.mf.println("\nBinder: host=" + this.host + "\n addr=" + this.addr + " port=" + this.port);
/*     */       
/* 280 */       int i = 0;
/* 281 */       while (this.cell.length > i) {
/* 282 */         Socket localSocket = localServerSocket.accept();
/* 283 */         Registrar localRegistrar = new Registrar(localSocket, this);
/*     */         try
/*     */         {
/* 286 */           localRegistrar.join();
/*     */         } catch (InterruptedException localInterruptedException) {
/* 288 */           this.mf.println("Join error.");
/*     */         }
/*     */         
/* 291 */         i = registered();
/* 292 */         this.mf.println("Number of agents registered = " + i);
/*     */       }
/* 294 */       localServerSocket.close();
/*     */     } catch (IOException localIOException) {
/* 296 */       this.mf.println("Registration socket: " + localIOException.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   int registered()
/*     */   {
/* 302 */     int i = 0;
/* 303 */     for (int j = 0; j < this.cell.length; j++)
/* 304 */       if (this.cell[j].getHost() != null) i++;
/* 305 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void sendNbInfo()
/*     */   {
/* 312 */     int i = this.cell.length;
/* 313 */     int j = 0;
/*     */     try {
/* 315 */       for (int k = 0; k < i; k++)
/*     */       {
/* 317 */         Socket localSocket = new Socket(this.cell[k].getAddr(), this.cell[k].getPort());
/* 318 */         PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream())), true);
/*     */         
/*     */ 
/* 321 */         String[] arrayOfString = getNbMsg(k);
/* 322 */         for (int m = 0; m < arrayOfString.length; m++) localPrintWriter.println(arrayOfString[m]);
/* 323 */         this.mf.println("Sent nb info to agent " + k + " (" + this.cell[k].getAddr() + "," + this.cell[k].getPort() + ")");
/*     */         
/*     */ 
/* 326 */         BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
/*     */         
/* 328 */         StringTokenizer localStringTokenizer = new StringTokenizer(localBufferedReader.readLine());
/* 329 */         int n = Integer.parseInt(localStringTokenizer.nextToken());
/* 330 */         String str1 = localStringTokenizer.nextToken();
/* 331 */         String str2 = this.cell[k].getName();
/* 332 */         if ((n != 3) || (!str1.equals(str2))) {
/* 333 */           this.mf.println("Err: expect ack from " + str2);
/* 334 */           System.exit(0);
/*     */         }
/*     */         
/* 337 */         localPrintWriter.close();localSocket.close();
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 340 */       this.mf.println("Send nb info: " + localIOException.toString());j = 1;
/*     */     } catch (SecurityException localSecurityException) {
/* 342 */       this.mf.println("Socket security error: " + localSecurityException.toString());j = 1;
/*     */     }
/* 344 */     if (j == 0) { this.mf.println("Binding complete!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   String[] getNbMsg(int paramInt)
/*     */   {
/* 351 */     int i = this.cell[paramInt].getNbCount();
/* 352 */     String[] arrayOfString1 = new String[i + 1];
/*     */     
/* 354 */     arrayOfString1[0] = new String("2 " + i);
/* 355 */     for (int j = 0; j < i; j++) {
/* 356 */       String str1 = this.cell[paramInt].getNb(j);
/* 357 */       int k = getCellIndex(str1);
/* 358 */       String str2 = this.cell[k].getHost();
/* 359 */       String str3 = this.cell[k].getAddr();
/* 360 */       int m = this.cell[k].getPort();
/* 361 */       String str4 = new String(str1 + " " + str2 + " " + str3 + " " + m + " " + this.cell[paramInt].getSepsetSize(j) + " ");
/*     */       
/* 363 */       String str5 = "";
/* 364 */       String[] arrayOfString2 = this.cell[paramInt].getSepset(j);
/* 365 */       for (int n = 0; n < arrayOfString2.length; n++) str5 = new String(str5 + " " + arrayOfString2[n]);
/* 366 */       arrayOfString1[(j + 1)] = new String(str4 + str5);
/*     */     }
/* 368 */     return arrayOfString1;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/Binder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */