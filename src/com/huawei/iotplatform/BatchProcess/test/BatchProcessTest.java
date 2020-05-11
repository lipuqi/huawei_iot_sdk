package com.huawei.iotplatform.BatchProcess.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.BatchTaskCreateInDTO2;
import com.huawei.iotplatform.client.dto.BatchTaskCreateOutDTO;
import com.huawei.iotplatform.client.dto.CommandDTOV4;
import com.huawei.iotplatform.client.dto.DeviceCmd;
import com.huawei.iotplatform.client.dto.QueryOneTaskOutDTO;
import com.huawei.iotplatform.client.dto.QueryTaskDetailsInDTO;
import com.huawei.iotplatform.client.dto.QueryTaskDetailsOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.BatchProcess;
import com.huawei.iotplatform.utils.AuthUtil;
import com.huawei.iotplatform.utils.PropertyUtil;

/**
 * 批量处理
 * 
 * 创建批量任务
 * 查询指定批量任务信息
 * 查询批量任务的子任务信息
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2024.html
 * @author Lando_Li
 *
 */
public class BatchProcessTest
{
    public static void main(String args[]) throws Exception
    {
    	/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
    	BatchProcess batchProcess = new BatchProcess(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        /**---------------------创建批量任务------------------------*/
        // https://support.huaweicloud.com/sdkreference-iot/iot_06_2025.html
        System.out.println("======创建批量任务======");
        List<String> deviceList = new ArrayList<String>();
        
        deviceList.add("a5c7f6c9-5e81-4693-9c56-070123fc186a");
        deviceList.add("ae81fe57-9ac7-4d9c-9a42-206bc9545a19");
        
        BatchTaskCreateOutDTO task = createTask(batchProcess, deviceList, accessToken);
        
        
        if (task != null) {
        	System.out.println(task.toString());
        	
        	/**---------------------查询指定批量任务信息-----------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2026.html
        	System.out.println("\n======查询指定批量任务信息======");
            String taskId = task.getTaskID();
            QueryOneTaskOutDTO qotOutDTO = batchProcess.queryOneTask(taskId, null, null, accessToken);
            System.out.println(qotOutDTO.toString());
            
            /**---------------------查询批量任务的子任务信息------------------------*/
            //https://support.huaweicloud.com/sdkreference-iot/iot_06_2027.html
            System.out.println("\n======查询批量任务的子任务信息======");
            QueryTaskDetailsInDTO qtdInDTO = new QueryTaskDetailsInDTO();
            qtdInDTO.setTaskId(taskId);
            QueryTaskDetailsOutDTO qtdOutDTO = batchProcess.queryTaskDetails(qtdInDTO, accessToken);
            System.out.println(qtdOutDTO.toString());
		}
  
    }
    
    /**
     * 创建批量任务
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2025.html
     * @param batchProcess
     * @param deviceList
     * @param accessToken
     * @return
     * @throws NorthApiException
     */
    private static BatchTaskCreateOutDTO createTask(BatchProcess batchProcess, List<String> deviceList, String accessToken) throws NorthApiException {
    	
    	// 填写输入参数BatchTaskCreateInDTO
    	BatchTaskCreateInDTO2 btcInDTO = new BatchTaskCreateInDTO2();
    	
        Random random = new Random();	
		String taskName = "testTask" + (random.nextInt(9000000) + 1000000); // 这是一个测试任务名称
        btcInDTO.setTaskName(taskName);
        
        btcInDTO.setTimeout(300);
        btcInDTO.setAppId(PropertyUtil.getProperty("appId"));
        btcInDTO.setTaskType("DeviceCmd");
        
        // 设置DeviceCmd
        DeviceCmd deviceCmd = new DeviceCmd();
        deviceCmd.setType("DeviceList");
        
        deviceCmd.setDeviceList(deviceList);
        
        // 根据配置文件填充命令
        CommandDTOV4 command = new CommandDTOV4();
        command.setMethod("PUT"); //PUT是命令名称
        command.setServiceId("Brightness");
        
        Map<String, Object> cmdPara = new HashMap<String, Object>();
        cmdPara.put("brightness", 80); //亮度是命令参数
     
		command.setParas(cmdPara);        
		deviceCmd.setCommand(command);
		
		try {
			btcInDTO.setParam(deviceCmd);
			return batchProcess.createBatchTask(btcInDTO, accessToken);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}

        return null;
    }
}
