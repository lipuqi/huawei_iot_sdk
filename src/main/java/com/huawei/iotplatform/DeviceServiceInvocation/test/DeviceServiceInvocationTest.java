package com.huawei.iotplatform.DeviceServiceInvocation.test;

import java.util.HashMap;
import java.util.Map;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.CommandDTO2;
import com.huawei.iotplatform.client.dto.CommandNA2CloudHeader;
import com.huawei.iotplatform.client.dto.InvokeDeviceServiceOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceServiceInvocation;
import com.huawei.iotplatform.utils.AuthUtil;

/**
 * 设备服务调用
 * 
 * 此接口适用于使用MQTT协议接入的设备，例如集成了AgentLite SDK的设备。
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2069.html
 * @author Lando_Li
 *
 */
public class DeviceServiceInvocationTest {
	
    public static void main(String args[]) throws Exception
    {
    	/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
    	DeviceServiceInvocation deviceServiceInvocation = new DeviceServiceInvocation(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        /**---------------------设备服务调用（将服务命令发送到安装了agent / agentLite的设备）------------------------*/
        // 这是安装了agent / agentLite的测试设备，或者是代理网关下的子设备
        String deviceId = "09691354-872d-49a1-9c1c-a18512f10ae2";
        
        System.out.println("======设备服务调用======");
        InvokeDeviceServiceOutDTO idsOutDTO = modifyBrightness(deviceServiceInvocation, deviceId, 86, accessToken);
        if (idsOutDTO != null) {
        	System.out.println(idsOutDTO.toString());
		}
    }
    
    /**
     * 设备服务调用
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2069.html
     * @param deviceServiceInvocation
     * @param deviceId
     * @param brightness
     * @param accessToken
     * @return
     */
    private static InvokeDeviceServiceOutDTO modifyBrightness(DeviceServiceInvocation deviceServiceInvocation, String deviceId, int brightness,String accessToken) {
    	CommandDTO2 cmdDTO = new CommandDTO2();
        CommandNA2CloudHeader cmdHeader = new CommandNA2CloudHeader();
        cmdHeader.setMode("NOACK");// 根据业务任务将模式设置为NOACK或ACK
        cmdHeader.setMethod("PUT");// “ PUT”是配置文件中定义的命令名称
        cmdDTO.setHeader(cmdHeader);
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("brightness", brightness);//“brightness”（亮度）是配置文件中定义的命令参数名称
        cmdDTO.setBody(body);
        
        //"Brightness" 是配置文件中定义的serviceId
        InvokeDeviceServiceOutDTO idsOutDTO;
		try {
			/**---------------------调用设备服务------------------------*/
			String serviceId = "Brightness";//"Brightness" 是配置文件中定义的serviceId
			idsOutDTO = deviceServiceInvocation.invokeDeviceService(deviceId, serviceId, cmdDTO, null, accessToken);
			return idsOutDTO;
		} catch (NorthApiException e) {
			if ("100428".equals(e.getError_code())) {
				System.out.println("请确保设备在线");
			}
			System.out.println(e.toString());
		}
        return null;
    }
}
