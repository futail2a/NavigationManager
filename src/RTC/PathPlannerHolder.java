package RTC;

/**
* RTC/PathPlannerHolder.java .
* IDL-to-Java�R���p�C��(�|�[�^�u��)�A�o�[�W����"3.2"�ɂ���Đ�������܂���
* idl/MobileRobot.idl����
* 2014�N9��4�� 22��56��50�b JST
*/

public final class PathPlannerHolder implements org.omg.CORBA.portable.Streamable
{
  public RTC.PathPlanner value = null;

  public PathPlannerHolder ()
  {
  }

  public PathPlannerHolder (RTC.PathPlanner initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = RTC.PathPlannerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    RTC.PathPlannerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return RTC.PathPlannerHelper.type ();
  }

}