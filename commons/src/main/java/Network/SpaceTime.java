/*    */ package Network;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpaceTime
/*    */ {
/*    */   public static void showUsedMemory(String paramString)
/*    */   {
/* 16 */     Runtime localRuntime = Runtime.getRuntime();
/* 17 */     localRuntime.gc();
/* 18 */     long l = localRuntime.totalMemory() - localRuntime.freeMemory();
/* 19 */     System.out.println(paramString + ": memory usage = " + l + " bytes");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static long showUsedTime(String paramString, long paramLong)
/*    */   {
/* 26 */     Date localDate = new Date();
/* 27 */     long l = localDate.getTime();
/* 28 */     if (paramLong == 0L) return l;
/* 29 */     System.out.println(paramString + ": t0=" + paramLong + "ms t1=" + l + "ms t1-t0=" + (l - paramLong) + "ms");
/* 30 */     return l - paramLong;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/SpaceTime.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */