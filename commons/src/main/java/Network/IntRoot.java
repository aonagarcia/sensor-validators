package Network;

public abstract interface IntRoot
{
  public static final int SYSTEM = -1;
  
  public abstract void setCtrlFlag(String paramString1, String paramString2);
  
  public abstract void setMsgFlag(int paramInt);
  
  public abstract void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString);
  
  public abstract String[] actAll(int paramInt1, int paramInt2);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntRoot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */