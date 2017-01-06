// -*-Java-*-
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import RTC.PathFollower;
/*!
 * @file  PathFollowerSVC_impl.java
 * @brief Service implementation code of MobileRobot.idl
 *
 */
import RTC.PathFollowerPOA;
/*!
 * @class PathFollowerSVC_impl
 * Example class implementing IDL interface RTC::PathFollower
 */
public class PathFollowerDecorator extends CorbaConsumer<PathFollower>{
	
	private CorbaConsumer<PathFollower> m_PathFollowerBase;
	private NavigationManagerImpl impl;
	private RTC.RETURN_VALUE ret = RTC.RETURN_VALUE.RETVAL_UNKNOWN_ERROR;
	
	public PathFollowerDecorator(NavigationManagerImpl impl){
        System.out.println("Decorated Constructor called");
        this.m_PathFollowerBase = new CorbaConsumer<PathFollower>(PathFollower.class);
        this.impl = impl;
	}
	
	public RTC.RETURN_VALUE callFollowPath(RTC.Path2D path){
    	try{
        	m_PathFollowerBase.setObject(this.m_objref);
        	ret = this.m_PathFollowerBase._ptr().followPath(path);
    	 }catch (org.omg.CORBA.SystemException e){
         	System.out.println("Not connected port");
    	 }
    	return ret;
	}
    
	public RTC.Path2D refreshPath(RTC.Path2D path){
    	this.impl.refreshPath(path);
    	m_PathFollowerBase.releaseObject();
    	return path;
	}
	
	public RTC.RETURN_VALUE followPath(RTC.Path2D path) {
        System.out.println("Decorated followPath called");        
		
    	m_PathFollowerBase.setObject(this.m_objref);
    	ret = callFollowPath(path);
    	
    	//at-least once semantics
        while(ret != RTC.RETURN_VALUE.RETVAL_OK){
        	this.impl.refreshPath(path);
        	callFollowPath(path);
        }
        
        System.out.println("RETURN_VALUE = RETVAL_OK");
    	return  RTC.RETURN_VALUE.RETVAL_OK;
    }

    public RTC.RETURN_VALUE getState(RTC.FOLLOWER_STATEHolder state) {
        System.out.println("Decorated getState called");
    	m_PathFollowerBase.setObject(this.m_objref);
        return this.m_PathFollowerBase._ptr().getState(state);
    }

    public RTC.RETURN_VALUE followPathNonBlock(RTC.Path2D path) {
        System.out.println("Decorated followPathNoneBlack called");
    	m_PathFollowerBase.setObject(this.m_objref);
        return this.m_PathFollowerBase._ptr().followPathNonBlock(path);
    }
}