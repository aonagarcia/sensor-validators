package Network;

public abstract interface IntBMgr
{
  public abstract String getHost();
  
  public abstract int getPort();
  
  public abstract void setCtrlFlag(String paramString1, String paramString2);
  
  public abstract void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString);
  
  public abstract String[] actAll(int paramInt1, int paramInt2);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntBMgr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */