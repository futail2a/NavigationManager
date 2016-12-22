// -*-Java-*-
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import RTC.PathFollower;

import _SDOPackage;
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
	
	public PathFollowerDecorator(NavigationManagerImpl impl){
        System.out.println("PathFollower Recoverable Mode");
        this.m_PathFollowerBase = new CorbaConsumer<PathFollower>(PathFollower.class);
        this.impl = impl;
	}
	
	public RTC.RETURN_VALUE followPath(RTC.Path2D path) {
        System.out.println("Follow path");

		RTC.RETURN_VALUE ret = RTC.RETURN_VALUE.RETVAL_UNKNOWN_ERROR;
		
    	//at-least-once semantics    	
        while(true){  	
        	try{
            	m_PathFollowerBase.setObject(this.m_objref);
            	ret = this.m_PathFollowerBase._ptr().followPath(path);
        	 }catch (Exception e){ //org.omg.CORBA.SystemException?
             	System.out.println("Not connected port");
             	m_PathFollowerBase.releaseObject();
               	this.impl.refreshPath(path);
               	continue;
        	 }
        	
        	if(ret != RTC.RETURN_VALUE.RETVAL_OK){
            	System.out.println("RETURN VALUE: " + ret.value());
               	this.impl.refreshPath(path);
        	}else{
            	System.out.println("RETURN VALUE: RETVAL_OK");
        		break;
        	}
        }     

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