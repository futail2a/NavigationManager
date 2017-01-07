// -*-Java-*-
import jp.go.aist.rtm.RTC.port.ConnectionCallback;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import RTC.ConnectorProfileHolder;
import RTC.Path2D;
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
	private boolean isDisconnected = false;
	private CorbaConsumer<PathFollower> m_PathFollowerBase;
	private NavigationManagerImpl m_impl;
	private RTC.RETURN_VALUE ret = null;
	private Path2D m_targetPath;
	private Thread requesting;

	public PathFollowerDecorator(NavigationManagerImpl impl){
        System.out.println("Decorated Constructor called");
        this.m_PathFollowerBase = new CorbaConsumer<PathFollower>(PathFollower.class);
        m_impl = impl;
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
	
	public RTC.RETURN_VALUE followPath(RTC.Path2D path) {
        System.out.println("Decorated followPath called");        
		m_targetPath = path;
    	requesting = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Follow Path");
				callFollowPath(m_targetPath);				
			}
		});

		requesting.start();
        					
        /*
        while(ret != RTC.RETURN_VALUE.RETVAL_OK){
        	m_impl.refreshPath(path);
        	callFollowPath(path);
        }
        */
        while(true){
        	if(ret !=null){
		        System.out.println("RETURN_VALUE = "+ret.value());
		    	return  ret;
        	}
    	}
       
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
    

	public class DisconnectedCallback implements ConnectionCallback{
		@Override
		public void run(ConnectorProfileHolder arg0) {
			isDisconnected = true;
		}
	};
	
	public class RequestCallback implements ConnectionCallback{
		
		@Override
		public void run(ConnectorProfileHolder arg0) {
			if(isDisconnected == true){
		        System.out.println("RequestCallback");
		        
		        m_targetPath = m_impl.refreshPath();		        
		        
		        requesting=null;
		    	requesting = new Thread(new Runnable() {
					@Override
					public void run() {
				        System.out.println("Follow Path");
				        ret = callFollowPath(m_targetPath);
					}
				});

				requesting.start();
		        							        
			}
			isDisconnected = false;
		}
	};

    
}