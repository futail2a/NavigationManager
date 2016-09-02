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
	
	public PathFollowerDecorator(NavigationManagerImpl impl){
        System.out.println("Decorating Constructor called");
        this.m_PathFollowerBase = new CorbaConsumer<PathFollower>(PathFollower.class);
        this.impl = impl;
	}
	public RTC.RETURN_VALUE followPath(RTC.Path2D path) {
		RTC.RETURN_VALUE ret;
        System.out.println("Decorating followPath called");
    	m_PathFollowerBase.setObject(this.m_objref);
    	ret = this.m_PathFollowerBase._ptr().followPath(path);
        while(ret != RTC.RETURN_VALUE.RETVAL_OK){
        	this.impl.refreshPath(path);
        	ret = this.m_PathFollowerBase._ptr().followPath(path);
        }
    	return ret;
    }

    public RTC.RETURN_VALUE getState(RTC.FOLLOWER_STATEHolder state) {
        System.out.println("Decorating getState called");
    	m_PathFollowerBase.setObject(this.m_objref);
        return this.m_PathFollowerBase._ptr().getState(state);
    }

    public RTC.RETURN_VALUE followPathNonBlock(RTC.Path2D path) {
        System.out.println("Decorating followPathNoneBlack called");
    	m_PathFollowerBase.setObject(this.m_objref);
        return this.m_PathFollowerBase._ptr().followPathNonBlock(path);
    }
}