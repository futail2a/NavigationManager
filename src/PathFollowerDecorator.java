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
	
	public PathFollowerDecorator(){
        System.out.println("Decorating Constructor called");
        this.m_PathFollowerBase = new CorbaConsumer<PathFollower>(PathFollower.class);
	}
	public RTC.RETURN_VALUE followPath(RTC.Path2D path) {
        System.out.println("Decorating followPath called");
    	m_PathFollowerBase.setObject(this.m_objref);
        return  this.m_PathFollowerBase._ptr().followPath(path);
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