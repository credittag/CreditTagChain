package com.browser.task;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.browser.service.BlockService;
import com.browser.service.RiskAddressReport;
import com.browser.service.RpcService;
import com.browser.service.impl.SyncService;

/**
 * Created by chengmaotao on 2018/3/16.
 * 每次同步一个块的定时任务
 */
@Component
public class SyncTaskSingle {

    private static Logger logger = LoggerFactory.getLogger(SyncTaskSingle.class);
    @Value("${wallet.socket.host}")
	private String host;

	@Value("${wallet.socket.port}")
	private int port;

	@Value("${wallet.socket.timeout}")
	private int timeout;
    private Socket socket = null;
    private boolean newConnection = false;
    @Autowired
    private BlockService blockService;

    @Autowired
    private RpcService rpcService;

    @Autowired
    private SyncService syncService;
    
    @Value("${system.syn.Scheduled}")
    private Boolean Scheduled;
    
 
    
    @Scheduled(cron="0/5 * * * * ? ")
    public void syncData(){
    	
    	if(Scheduled){
    		//查询本地数据库最大块号
	        Long blockNum = blockService.queryBlockNum();
	        if(null == blockNum){
	            blockNum = 0L;
	        }

	        try{
	        	logger.info("同步数据开始");
		        Socket rpcConnection = getSocketConnection();
		        if(rpcConnection!=null){
			        rpcService.setConnetion(rpcConnection);
			        boolean rpcLogin = true;
			        if(isNewConnection()){
			        	rpcLogin = rpcService.login();
			        }
			        if(rpcLogin){
				        Long total = rpcService.getBlockCount();
				        if(total>blockNum){
				        	syncService.setRpcService(rpcService);
				            for(Long i=blockNum; i<total; i++){
				                logger.info("同步"+(i+1)+"块");
				                syncService.blockSync(i+1);
				            }
				        }
			        }else{
			        	logger.error("RPC 登录失败 ！");
			        }
			        logger.info("同步数据结束");
		        }else{
		        	logger.error("Socket 未连接 ！");
		        }
	        }catch(Exception e){
	        	e.printStackTrace();
	        	releaseConnection();
	        }
        	
    	}
    }
    
    public synchronized Socket getSocketConnection()
    {
    	try {
    		newConnection = false;
			if(socket == null){
				socket = new Socket();
			}
			if(socket != null && !socket.isConnected()){
				SocketAddress remoteAddr = new InetSocketAddress(host, port);
				socket.connect(remoteAddr, timeout);
				newConnection = true;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			releaseConnection();
		}
		return socket;
    	
    }
    public boolean isNewConnection(){
    	return newConnection;
    }
    
    public void releaseConnection(){
		try{
			socket.close();
		}catch(Exception eo){
		}
		socket = null;
		newConnection = false;
    }
}
