/*     */ package FrontEnd;
/*     */ 
/*     */ import Network.Bridge;
/*     */ import Network.HelpDialog;
/*     */ import Network.HelpPanel;
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import Network.VectorDialog;
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Panel;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
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
/*     */ class Menu
/*     */   extends Panel
/*     */   implements Bridge
/*     */ {
/*     */   Frame frame;
/*     */   MenuPanel panel;
/*     */   VectorDialog menuDialog;
/*  60 */   boolean loaded = false;
/*  61 */   String javaDir = null;
/*  62 */   String javaCmd = "java -cp ww4.jar ";
/*  63 */   String user = null;
/*  64 */   MenuItem[] menuItem = null;
/*  65 */   MenuItem clipItem = null;
/*  66 */   MenuItem lastItem = new MenuItem(this, "", "", 15, 1, "End of Menu");
/*     */   
/*  68 */   String itemName = null;
/*     */   int itemId;
/*     */   int itemIndex;
/*     */   
/*     */   public Menu(Frame paramFrame, MenuPanel paramMenuPanel) {
/*  73 */     this.frame = paramFrame;this.panel = paramMenuPanel;
/*  74 */     setLayout(new GridLayout(1, 1, 6, 2));
/*  75 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/*  82 */     if (this.loaded) {
/*  83 */       HelpPanel.addHelp("Loading has completed.");return;
/*     */     }
/*     */     try {
/*  86 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader("menu.dat"));
/*  87 */       loadMenuItem(localBufferedReader);
/*  88 */       localBufferedReader.close();
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/*  90 */       popDirDialog();
/*  91 */       setMenuEnd();
/*     */     } catch (IOException localIOException) {
/*  93 */       HelpPanel.showError("Unable to load file!");return;
/*     */     }
/*  95 */     updateMenu(null);
/*  96 */     this.loaded = true;
/*     */   }
/*     */   
/*  99 */   void popDirDialog() { String str = new String("Enter Java path: ");
/* 100 */     String[] arrayOfString1 = new String[1];arrayOfString1[0] = new String("Java path ");
/* 101 */     String[] arrayOfString2 = new String[1];arrayOfString2[0] = new String("");
/* 102 */     this.menuDialog = new VectorDialog(this.frame, this, str, arrayOfString1, arrayOfString2, 16, 100, 100);
/* 103 */     this.menuDialog.setVisible(true);
/*     */   }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {
/* 107 */     if (paramArrayOfString.length == 1) {
/* 108 */       this.javaDir = new String(paramArrayOfString[0]);
/*     */     }
/*     */     else {
/* 111 */       this.itemId = Integer.parseInt(paramArrayOfString[0]);
/* 112 */       this.itemIndex = Integer.parseInt(paramArrayOfString[1]);
/* 113 */       this.itemName = new String(paramArrayOfString[2]); } }
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector(int[] paramArrayOfInt) {}
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/* 122 */   public float[] getVector(int paramInt) { return null; }
/* 123 */   public String[] getVector2(int paramInt) { return null; }
/* 124 */   public String[] getVector3(int paramInt) { return null; }
/* 125 */   public void showNet() { update(getGraphics()); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void loadMenuItem(BufferedReader paramBufferedReader)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       this.javaDir = paramBufferedReader.readLine();
/*     */     } catch (IOException localIOException) {
/* 137 */       HelpPanel.showError("Unable to load java path.");
/*     */     }
/*     */     
/* 140 */     String[] arrayOfString = UTIL.loadStringListLine(paramBufferedReader, 2);
/* 141 */     int i = Integer.parseInt(arrayOfString[0]);
/* 142 */     this.menuItem = new MenuItem[i + 1];
/* 143 */     this.user = arrayOfString[1];
/* 144 */     if (this.user.equals("yx")) { this.javaCmd = "java ";
/*     */     }
/* 146 */     ButtonHandler localButtonHandler = new ButtonHandler();
/* 147 */     for (int j = 0; j < i; j++) {
/* 148 */       UTIL.skipLine(paramBufferedReader);
/* 149 */       String str1 = UTIL.loadString(paramBufferedReader);
/* 150 */       String str2 = UTIL.loadString(paramBufferedReader);
/* 151 */       int k = UTIL.loadInt(paramBufferedReader);
/* 152 */       int m = UTIL.loadInt(paramBufferedReader);
/* 153 */       String str3 = UTIL.loadStringLine(paramBufferedReader);
/*     */       
/* 155 */       this.menuItem[j] = new MenuItem(this, str1, str2, k, m, str3);
/* 156 */       this.menuItem[j].button.addActionListener(localButtonHandler);
/*     */     }
/* 158 */     this.menuItem[i] = this.lastItem;
/*     */   }
/*     */   
/*     */   void setMenuEnd()
/*     */   {
/* 163 */     this.menuItem = new MenuItem[1];
/* 164 */     this.menuItem[0] = this.lastItem;
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 169 */     if (!this.loaded) return;
/* 170 */     String str = new String("menu.dat");
/*     */     try {
/* 172 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 173 */       HelpPanel.addHelp("Saving menu to: " + str);
/* 174 */       saveMenuItem(localPrintWriter);
/* 175 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 177 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */   void saveMenuItem(PrintWriter paramPrintWriter)
/*     */   {
/* 183 */     paramPrintWriter.println(this.javaDir);
/* 184 */     int i = this.menuItem == null ? 0 : this.menuItem.length - 1;
/* 185 */     paramPrintWriter.println(i + " " + this.user);
/* 186 */     if (i == 0) { return;
/*     */     }
/* 188 */     for (int j = 0; j < i; j++) { this.menuItem[j].save(paramPrintWriter);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void add()
/*     */   {
/* 200 */     if (this.menuItem == null) { setMenuEnd();
/*     */     }
/* 202 */     FileDialog localFileDialog = new FileDialog(this.frame, "", 0);
/* 203 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/* 205 */     String str1 = localFileDialog.getDirectory();
/* 206 */     String str2 = localFileDialog.getFile();
/*     */     
/* 208 */     String str3 = UTIL.getLastToken(str1);
/* 209 */     String str4 = UTIL.removePostfix(str2);
/*     */     
/* 211 */     String str5 = new String("Menu item info: ");
/* 212 */     String[] arrayOfString1 = new String[3];arrayOfString1[0] = new String("Group ID");
/* 213 */     arrayOfString1[1] = new String("Index");arrayOfString1[2] = new String("Description");
/* 214 */     String[] arrayOfString2 = new String[3];arrayOfString2[0] = new String("");
/* 215 */     arrayOfString2[1] = new String("");arrayOfString2[2] = new String("");
/* 216 */     this.menuDialog = new VectorDialog(this.frame, this, str5, arrayOfString1, arrayOfString2, 16, 100, 100);
/* 217 */     this.menuDialog.setVisible(true);
/*     */     
/* 219 */     this.clipItem = new MenuItem(this, str3, str4, this.itemId, this.itemIndex, this.itemName);
/* 220 */     int i = this.menuItem.length;
/* 221 */     paste(i - 1);
/*     */   }
/*     */   
/*     */   void execute(int paramInt)
/*     */   {
/* 226 */     Runtime localRuntime = Runtime.getRuntime();
/*     */     try {
/* 228 */       Process localProcess = localRuntime.exec(this.javaDir + this.javaCmd + this.menuItem[paramInt].getDir() + "." + this.menuItem[paramInt].getFile());
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */ 
/*     */ 
/* 237 */       HelpPanel.showError("Unabel to run.\nEnter java path and try again.");
/*     */       
/* 239 */       popDirDialog();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void sort()
/*     */   {
/* 249 */     if (this.menuItem == null) return;
/* 250 */     MenuItem[] arrayOfMenuItem1 = this.menuItem;
/* 251 */     int i = this.menuItem.length;
/* 252 */     int[] arrayOfInt1 = new int[i];
/* 253 */     for (int j = 0; j < i; j++)
/* 254 */       arrayOfInt1[j] = (this.menuItem[j].getId() * 100 + this.menuItem[j].getIndex());
/* 255 */     int[] arrayOfInt2 = MATH.sort(arrayOfInt1);
/*     */     
/* 257 */     MenuItem[] arrayOfMenuItem2 = new MenuItem[i];
/* 258 */     for (int k = 0; k < i; k++) {
/* 259 */       for (int m = 0; m < i; m++) {
/* 260 */         int n = this.menuItem[m].getId() * 100 + this.menuItem[m].getIndex();
/* 261 */         if (arrayOfInt2[k] == n) {
/* 262 */           arrayOfMenuItem2[k] = this.menuItem[m]; break;
/*     */         }
/*     */       }
/*     */     }
/* 266 */     this.menuItem = arrayOfMenuItem2;
/* 267 */     updateMenu(arrayOfMenuItem1);
/*     */   }
/*     */   
/*     */ 
/*     */   void cut(int paramInt)
/*     */   {
/* 273 */     this.clipItem = this.menuItem[paramInt];
/* 274 */     MenuItem[] arrayOfMenuItem1 = this.menuItem;
/*     */     
/* 276 */     int i = this.menuItem.length;
/* 277 */     MenuItem[] arrayOfMenuItem2 = new MenuItem[i - 1];
/* 278 */     for (int j = 0; j < paramInt; j++) arrayOfMenuItem2[j] = this.menuItem[j];
/* 279 */     for (j = paramInt + 1; j < i; j++) { arrayOfMenuItem2[(j - 1)] = this.menuItem[j];
/*     */     }
/* 281 */     this.menuItem = arrayOfMenuItem2;
/* 282 */     updateMenu(arrayOfMenuItem1);
/*     */   }
/*     */   
/*     */ 
/*     */   void paste(int paramInt)
/*     */   {
/* 288 */     MenuItem[] arrayOfMenuItem1 = this.menuItem;
/* 289 */     int i = this.menuItem.length;
/* 290 */     MenuItem[] arrayOfMenuItem2 = new MenuItem[i + 1];
/* 291 */     for (int j = 0; j < paramInt; j++) arrayOfMenuItem2[j] = this.menuItem[j];
/* 292 */     arrayOfMenuItem2[paramInt] = this.clipItem;
/* 293 */     for (j = paramInt + 1; j <= i; j++) { arrayOfMenuItem2[j] = this.menuItem[(j - 1)];
/*     */     }
/* 295 */     this.menuItem = arrayOfMenuItem2;
/* 296 */     this.clipItem = null;
/* 297 */     updateMenu(arrayOfMenuItem1);
/*     */   }
/*     */   
/*     */   void showHelpFile(int paramInt)
/*     */   {
/* 302 */     File localFile = new File(this.menuItem[paramInt].getDir(), this.menuItem[paramInt].getFile() + ".hlp");
/* 303 */     String str2 = new String("");
/*     */     try {
/* 305 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
/* 306 */       String str1; while ((str1 = localBufferedReader.readLine()) != null) str2 = new String(str2 + str1 + "\n");
/* 307 */       localBufferedReader.close();
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/* 309 */       HelpPanel.showError("No help file for this item.");
/*     */     } catch (IOException localIOException) {
/* 311 */       HelpPanel.showError("Unable to read file.");
/*     */     }
/* 313 */     HelpDialog localHelpDialog = new HelpDialog(this.frame, str2, 10, 10);
/* 314 */     localHelpDialog.setVisible(true);
/*     */   }
/*     */   
/*     */   void updateMenu(MenuItem[] paramArrayOfMenuItem)
/*     */   {
/*     */     int i;
/*     */     int j;
/* 321 */     if (paramArrayOfMenuItem != null) {
/* 322 */       i = paramArrayOfMenuItem.length;
/* 323 */       for (j = 0; j < i; j++) { remove(paramArrayOfMenuItem[j]);
/*     */       }
/*     */     }
/* 326 */     if (this.menuItem != null) {
/* 327 */       i = this.menuItem.length;
/* 328 */       setLayout(new GridLayout(i, 1, 6, 2));
/* 329 */       for (j = 0; j < i; j++) add(this.menuItem[j]);
/*     */     }
/* 331 */     this.frame.pack();
/*     */   }
/*     */   
/*     */   class ButtonHandler implements ActionListener { ButtonHandler() {}
/*     */     
/* 336 */     public void actionPerformed(ActionEvent paramActionEvent) { if (Menu.this.menuItem == null) return;
/* 337 */       if (!(paramActionEvent.getSource() instanceof Button)) { return;
/*     */       }
/* 339 */       int i = Menu.this.menuItem.length;
/* 340 */       int j = 0;
/* 341 */       while ((j < i) && (paramActionEvent.getSource() != Menu.this.menuItem[j].button)) { j++;
/*     */       }
/*     */       
/* 344 */       if (Menu.this.panel.isCutMode()) {
/* 345 */         if (i == 1) {
/* 346 */           HelpPanel.showError("Nothing to cut!");return;
/*     */         }
/* 348 */         Menu.this.cut(j);
/*     */       }
/* 350 */       else if (Menu.this.panel.isPasteMode()) {
/* 351 */         if (Menu.this.clipItem == null) {
/* 352 */           HelpPanel.showError("Nothing in clipboard!");return;
/*     */         }
/* 354 */         Menu.this.paste(j);
/*     */       }
/* 356 */       else if (Menu.this.panel.isRunMode()) {
/* 357 */         if (i == 1) {
/* 358 */           HelpPanel.showError("Nothing to run!");return;
/*     */         }
/* 360 */         Menu.this.frame.setCursor(new Cursor(3));
/* 361 */         Menu.this.execute(j);
/* 362 */         Menu.this.frame.setCursor(new Cursor(0));
/* 363 */         Menu.this.repaint();
/*     */       }
/* 365 */       else if (Menu.this.panel.isHelpMode()) {
/* 366 */         if (i == 1) {
/* 367 */           HelpPanel.showError("No help available!");return;
/*     */         }
/* 369 */         Menu.this.showHelpFile(j);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/FrontEnd/Menu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */