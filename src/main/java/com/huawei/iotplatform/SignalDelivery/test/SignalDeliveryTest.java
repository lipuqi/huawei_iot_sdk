package com.huawei.iotplatform.SignalDelivery.test;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.CommandDTOV4;
import com.huawei.iotplatform.client.dto.CreateDeviceCmdCancelTaskInDTO;
import com.huawei.iotplatform.client.dto.CreateDeviceCmdCancelTaskOutDTO;
import com.huawei.iotplatform.client.dto.PostDeviceCommandInDTO2;
import com.huawei.iotplatform.client.dto.PostDeviceCommandOutDTO2;
import com.huawei.iotplatform.client.dto.QueryDeviceCmdCancelTaskInDTO2;
import com.huawei.iotplatform.client.dto.QueryDeviceCmdCancelTaskOutDTO2;
import com.huawei.iotplatform.client.dto.QueryDeviceCommandInDTO2;
import com.huawei.iotplatform.client.dto.QueryDeviceCommandOutDTO2;
import com.huawei.iotplatform.client.dto.UpdateDeviceCommandInDTO;
import com.huawei.iotplatform.client.dto.UpdateDeviceCommandOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.SignalDelivery;
import com.huawei.iotplatform.utils.AuthUtil;

/**
 * 命令下发
 * 
 * 创建设备命令
 * 查询设备命令
 * 修改设备命令
 * 创建设备命令撤销任务
 * 查询设备命令撤销任务
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2062.html
 * @author Lando_Li
 *
 */
public class SignalDeliveryTest {
	
    public static void main(String args[]) throws Exception {
    	/**---------------------初始化 northApiClient------------------------*/
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        SignalDelivery signalDelivery = new SignalDelivery(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        /**---------------------创建设备命令------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2063.html
        String deviceId = "42517443-8c1b-4a58-b65e-194c00f77d4e";//这是一个测试NB-IoT设备
        System.out.println("======创建设备命令======");
        PostDeviceCommandOutDTO2 pdcOutDTO = postCommand(signalDelivery, deviceId, accessToken);
        if (pdcOutDTO != null) {
        	System.out.println(pdcOutDTO.toString());
        	String commandId = pdcOutDTO.getCommandId();
        	
        	/**---------------------修改设备命令------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2065.html
        	System.out.println("\n======修改设备命令======");
            UpdateDeviceCommandInDTO udcInDTO = new UpdateDeviceCommandInDTO();
            udcInDTO.setStatus("CANCELED");
            UpdateDeviceCommandOutDTO udcOutDTO = signalDelivery.updateDeviceCommand(udcInDTO, commandId, null, accessToken);
            System.out.println(udcOutDTO.toString());
		}
        
        /**---------------------查询设备命令------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2064.html
        System.out.println("\n======查询设备命令======");
        QueryDeviceCommandInDTO2 qdcInDTO = new QueryDeviceCommandInDTO2();
        qdcInDTO.setPageNo(0);
        qdcInDTO.setPageSize(10);               
        QueryDeviceCommandOutDTO2 qdcOutDTO = signalDelivery.queryDeviceCommand(qdcInDTO, accessToken);
        System.out.println(qdcOutDTO.toString());
        
        /**---------------------创建设备命令撤销任务------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2066.html
        System.out.println("\n======创建设备命令撤销任务======");
        CreateDeviceCmdCancelTaskInDTO cdcctInDTO = new CreateDeviceCmdCancelTaskInDTO();
        cdcctInDTO.setDeviceId(deviceId);        
        CreateDeviceCmdCancelTaskOutDTO cdcctOutDTO = signalDelivery.createDeviceCmdCancelTask(cdcctInDTO, null, accessToken);
        System.out.println(cdcctOutDTO.toString());
        
        /**---------------------查询设备命令撤销任务------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2067.html
        System.out.println("\n======查询设备命令撤销任务======");
    	QueryDeviceCmdCancelTaskInDTO2 qdcctInDTO = new QueryDeviceCmdCancelTaskInDTO2();
        qdcctInDTO.setDeviceId(deviceId);
        qdcctInDTO.setPageNo(0);
        qdcctInDTO.setPageSize(10);
        QueryDeviceCmdCancelTaskOutDTO2 qdcctOutDTO = signalDelivery.queryDeviceCmdCancelTask(qdcctInDTO, accessToken);
        System.out.println(qdcctOutDTO.toString());
        
    }
    
    /**
     * 创建设备命令
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2063.html
     * @param signalDelivery
     * @param deviceId
     * @param accessToken
     * @return
     */
    @SuppressWarnings("unchecked")
	private static PostDeviceCommandOutDTO2 postCommand(SignalDelivery signalDelivery, String deviceId, String accessToken) {
    	PostDeviceCommandInDTO2 pdcInDTO = new PostDeviceCommandInDTO2();
        pdcInDTO.setDeviceId(deviceId);
        
        CommandDTOV4 cmd = new CommandDTOV4();
        cmd.setServiceId("RawData");
        cmd.setMethod("RawData"); // “RawData”是配置文件中定义的命令名称
        Map<String, Object> cmdParam = new HashedMap();
        cmdParam.put("rawData", "AQ==");//"rawData" 是配置文件中定义的命令参数名称
        
        cmd.setParas(cmdParam);
        pdcInDTO.setCommand(cmd);
        
        try {
			return signalDelivery.postDeviceCommand(pdcInDTO, null, accessToken);
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
        return null;
    }
}
