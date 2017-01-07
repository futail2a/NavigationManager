// -*-Java-*-
import jp.go.aist.rtm.RTC.port.ConnectionCallback;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.port.CorbaConsumerBase;
import jp.go.aist.rtm.RTC.port.CorbaPort;
import RTC.ConnectorProfileHolder;
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
public class PathFollowerPortDecorator extends CorbaPort{
    protected boolean isDisconnected = false;
    protected NavigationManagerImpl m_impl;
    protected RTC.Path2D m_targetPath;
    protected PathFollowerDecorator m_portbase;
    
	public PathFollowerPortDecorator(String arg0, NavigationManagerImpl impl) {	
		super(arg0);
		m_impl = impl;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public boolean registerConsumer(java.lang.String instance_name, java.lang.String type_name, CorbaConsumerBase consumer){
		m_portbase = (PathFollowerDecorator) consumer;	
		
		//add callback functions
		ConnectionCallback call;
		call = m_portbase.new RequestCallback();
		super.setOnConnected(call);
		
		ConnectionCallback discall;
		discall = m_portbase.new DisconnectedCallback();
		super.setOnDisconnected(discall);
		
		return super.registerConsumer(instance_name, type_name, consumer);
	}
	

}