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
    protected CorbaConsumer<PathFollower> m_portbase;
    
	public PathFollowerPortDecorator(String arg0, NavigationManagerImpl impl) {
		super(arg0);
		
		m_impl = impl;		
		ConnectionCallback call;
		call = new RequestCallback();
		this.setOnConnected(call);
		
		ConnectionCallback discall;
		discall = new DisconnectedCallback();
		this.setOnDisconnected(discall);
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public boolean registerConsumer(java.lang.String instance_name, java.lang.String type_name, CorbaConsumerBase consumer){
		m_portbase = (CorbaConsumer<PathFollower>) consumer;		
		return super.registerConsumer(instance_name, type_name, consumer);
	}
	
	public class DisconnectedCallback implements ConnectionCallback{
		@Override
		public void run(ConnectorProfileHolder arg0) {
			isDisconnected = true;
		}
	};
	
	public class RequestCallback implements ConnectionCallback{
		private Thread requesting;
		
		@Override
		public void run(ConnectorProfileHolder arg0) {
			if(isDisconnected == true){
		        System.out.println("RequestCallback");
		        
		        m_impl.refreshPath(m_targetPath);
		        
		        requesting = new Thread(new Runnable() {
					@Override
					public void run() {
				        System.out.println("Follow Path");
				        try{
				        	m_portbase._ptr().followPath(m_targetPath);
				        }catch(org.omg.CORBA.SystemException e){
				         	System.out.println("Not connected port");
				   	 	}
					}
				});

		        requesting.start();
	
	
		        
			}
			isDisconnected = false;
		}
	};

}
