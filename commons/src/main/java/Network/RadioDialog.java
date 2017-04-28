/*    */ package Network;
/*    */ 
/*    */ import java.awt.Button;
/*    */ import java.awt.Checkbox;
/*    */ import java.awt.CheckboxGroup;
/*    */ import java.awt.Dialog;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Panel;
/*    */ import java.awt.Point;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ 
/*    */ public class RadioDialog extends Dialog implements ActionListener
/*    */ {
/*    */   Bridge panel;
/* 16 */   String[] boxlab = null;
/*    */   Button button;
/*    */   CheckboxGroup cbg;
/*    */   
/*    */   public RadioDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString, int paramInt1, int paramInt2)
/*    */   {
/* 22 */     super(paramFrame, paramString, true);
/* 23 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt1, paramFrame.getLocationOnScreen().y + paramInt2);
/*    */     
/* 25 */     setFont(NetPaint.helvetica14);
/* 26 */     this.panel = paramBridge;
/* 27 */     this.boxlab = UTIL.getDuplicate(paramArrayOfString);
/*    */     
/* 29 */     setLayout(new java.awt.BorderLayout());
/* 30 */     this.button = new Button("Done");
/* 31 */     this.button.addActionListener(this);
/* 32 */     add("South", this.button);
/*    */     
/* 34 */     Panel localPanel = new Panel();
/* 35 */     add("North", localPanel);
/* 36 */     localPanel.setLayout(new java.awt.GridLayout(paramArrayOfString.length, 1));
/* 37 */     this.cbg = new CheckboxGroup();
/* 38 */     localPanel.add(new Checkbox(paramArrayOfString[0], true, this.cbg));
/* 39 */     for (int i = 1; i < paramArrayOfString.length; i++)
/* 40 */       localPanel.add(new Checkbox(paramArrayOfString[i], false, this.cbg));
/* 41 */     pack();
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 45 */     if (paramActionEvent.getSource() == this.button) {
/* 46 */       String str = this.cbg.getSelectedCheckbox().getLabel();
/*    */       
/* 48 */       for (int i = 0; (i < this.boxlab.length) && (!str.equals(this.boxlab[i])); i++) {}
/*    */       
/*    */ 
/* 51 */       int[] arrayOfInt = new int[1];
/* 52 */       arrayOfInt[0] = i;
/* 53 */       this.panel.setVector(arrayOfInt);
/*    */       
/* 55 */       dispose();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/RadioDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */