package com.huawei.iotplatform.DataCollection.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.QueryBatchDevicesInfoInDTO;
import com.huawei.iotplatform.client.dto.QueryBatchDevicesInfoOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceCapabilitiesInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceCapabilitiesOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDesiredHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDesiredHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QuerySingleDeviceInfoOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.huawei.iotplatform.utils.AuthUtil;

/**
 * 数据采集
 * 
 * 查询单个设备信息
 * 批量查询设备信息列表
 * 查询设备历史数据
 * 查询设备影子历史数据
 * 查询设备服务能力
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2070.html
 * @author Lando_Li
 *
 */
public class DataCollectionTest
{
    public static void main(String args[]) throws Exception {
    	/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
    	DataCollection dataCollection = new DataCollection(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        /**---------------------查询单个设备信息------------------------*/
        // https://support.huaweicloud.com/sdkreference-iot/iot_06_2071.html
        System.out.println("======查询单个设备信息======");
        String deviceId = "cb15cbb6-04ce-4e2b-bf35-d6a264c74646";//这是一个测试设备
        QuerySingleDeviceInfoOutDTO qsdiOutDTO = dataCollection.querySingleDeviceInfo(deviceId, null, null, accessToken);
        if (qsdiOutDTO != null) {
        	System.out.println(qsdiOutDTO.toString());
		}
        
        /**---------------------批量查询设备信息列表------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2072.html
        System.out.println("\n======批量查询设备信息列表======");
        QueryBatchDevicesInfoInDTO qbdiInDTO = new QueryBatchDevicesInfoInDTO();
        qbdiInDTO.setPageNo(0);
        qbdiInDTO.setPageSize(10);
        QueryBatchDevicesInfoOutDTO qbdiOutDTO = dataCollection.queryBatchDevicesInfo(qbdiInDTO, accessToken);
        if (qbdiOutDTO != null) {
        	System.out.println(qbdiOutDTO.toString());
		}
        
        /**---------------------查询设备历史数据------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2073.html
        System.out.println("\n======查询设备历史数据======");
        QueryDeviceDataHistoryInDTO qddhInDTO = new QueryDeviceDataHistoryInDTO();
        qddhInDTO.setDeviceId(deviceId);
        qddhInDTO.setGatewayId(deviceId);//对于直接连接的设备，gatewayId是其自己的deviceId
        QueryDeviceDataHistoryOutDTO qddhOutDTO = dataCollection.queryDeviceDataHistory(qddhInDTO, accessToken);
        if (qddhOutDTO != null) {
        	System.out.println(qddhOutDTO.toString());        	
		}
        
        /**---------------------查询设备影子历史数据------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2074.html
        System.out.println("\n======查询设备影子历史数据======");
        QueryDeviceDesiredHistoryInDTO qddesiredhInDTO = new QueryDeviceDesiredHistoryInDTO();
        qddesiredhInDTO.setDeviceId(deviceId);
        qddesiredhInDTO.setGatewayId(deviceId);// 对于直接连接的设备，gatewayId是其自己的deviceId
        QueryDeviceDesiredHistoryOutDTO qddesiredhOutDTO = dataCollection.queryDeviceDesiredHistory(qddesiredhInDTO, accessToken);
        if (qddesiredhOutDTO != null) {
        	System.out.println(qddesiredhOutDTO.toString());        	
		}
        
        /**---------------------查询设备服务能力------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2075.html
        System.out.println("\n======查询设备服务能力======");
        QueryDeviceCapabilitiesInDTO qdcInDTO = new QueryDeviceCapabilitiesInDTO();
        qdcInDTO.setDeviceId(deviceId);
        QueryDeviceCapabilitiesOutDTO qdcOutDTO = dataCollection.queryDeviceCapabilities(qdcInDTO, accessToken); 
        if (qdcOutDTO != null) {
        	System.out.println(qdcOutDTO.toString());        	
		}
    }
    
}
