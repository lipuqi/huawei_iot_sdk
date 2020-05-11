package com.huawei.iotplatform.DeviceManagement.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ModifyDeviceInforInDTO;
import com.huawei.iotplatform.client.dto.ModifyDeviceShadowInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceShadowOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceStatusOutDTO;
import com.huawei.iotplatform.client.dto.RefreshDeviceKeyInDTO;
import com.huawei.iotplatform.client.dto.RefreshDeviceKeyOutDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO2;
import com.huawei.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.huawei.iotplatform.client.dto.ServiceDesiredDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.huawei.iotplatform.utils.AuthUtil;

/**
 * 设备管理
 * 
 * 注册设备（验证码方式）
 * 刷新设备密钥
 * 修改设备信息
 * 删除设备
 * 查询设备激活状态
 * 查询设备影子
 * 修改设备影子
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2014.html
 * @author Lando_Li
 *
 */
public class DeviceManagementTest
{
    public static void main(String args[]) throws Exception
    {
    	/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
    	DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        /**---------------------注册设备（验证码方式）------------------------*/
        // https://support.huaweicloud.com/sdkreference-iot/iot_06_2015.html
        System.out.println("======注册设备（验证码方式）======");
        RegDirectDeviceOutDTO rddod = registerDevice(deviceManagement, accessToken, 3000);
        
        if (rddod != null) {
        	String deviceId = rddod.getDeviceId();
        	
        	/**---------------------修改设备信息------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2017.html
        	// 使用verifyCode作为设备名称
        	System.out.println("\n======修改设备信息======");
            modifyDeviceInfo(deviceManagement, accessToken, deviceId, rddod.getVerifyCode());
            
            /**---------------------查询设备激活状态------------------------*/
            //https://support.huaweicloud.com/sdkreference-iot/iot_06_2019.html
            System.out.println("\n======查询设备激活状态======");
            QueryDeviceStatusOutDTO qdsOutDTO = deviceManagement.queryDeviceStatus(deviceId, null, accessToken);
            System.out.println(qdsOutDTO.toString());
            
            /**---------------------修改设备影子------------------------*/
            //https://support.huaweicloud.com/sdkreference-iot/iot_06_2023.html
            System.out.println("\n======修改设备影子======");
            modifyDeviceShadow(deviceManagement, accessToken, deviceId);
            
            /**---------------------查询设备影子------------------------*/  
            //https://support.huaweicloud.com/sdkreference-iot/iot_06_2022.html
            System.out.println("\n======查询设备影子======");
            QueryDeviceShadowOutDTO qdshadowOutDTO = deviceManagement.queryDeviceShadow(deviceId, null, accessToken);
            System.out.println(qdshadowOutDTO.toString());
            
            /**---------------------刷新设备密钥------------------------*/ 
            //https://support.huaweicloud.com/sdkreference-iot/iot_06_2016.html
            //注意：仅当设备处于离线状态时，才能进行刷新设备密钥操作。
            System.out.println("\n======刷新设备密钥======");
            refreshDeviceKey(deviceManagement, accessToken, deviceId);
            
            /**---------------------删除设备------------------------*/ 
            //https://support.huaweicloud.com/sdkreference-iot/iot_06_2018.html
            System.out.println("\n======删除设备======");
            deviceManagement.deleteDirectDevice(deviceId, true, null, accessToken);
            System.out.println("删除设备成功");
		}

    }
    
    /**
     * 注册设备（验证码方式）
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2015.html
     * @param deviceManagement
     * @param accessToken
     * @param timeout
     * @return
     */
    private static RegDirectDeviceOutDTO registerDevice(DeviceManagement deviceManagement, String accessToken, int timeout) {
    	
    	//填写输入参数
        RegDirectDeviceInDTO2 rddid = new RegDirectDeviceInDTO2();
        Random random = new Random();	
		String nodeid = "testdemo" + (random.nextInt(9000000) + 1000000); //这是个测试 imei
        String verifyCode = nodeid;
        rddid.setNodeId(nodeid);
        rddid.setVerifyCode(verifyCode);
//        rddid.setTimeout(timeout);
                
		try {
			RegDirectDeviceOutDTO rddod = deviceManagement.regDirectDevice(rddid, null, accessToken);
			System.out.println(rddod.toString());
			return rddod;
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
		return null;        
    }
    
    /**
     * 修改设备信息
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2017.html
     * @param deviceManagement
     * @param accessToken
     * @param deviceId
     * @param deviceName
     */
    private static void modifyDeviceInfo(DeviceManagement deviceManagement, String accessToken, String deviceId, String deviceName) {
    	ModifyDeviceInforInDTO mdiInDTO = new ModifyDeviceInforInDTO();
        mdiInDTO.setName(deviceName);
        mdiInDTO.setDeviceType("Bulb");
        mdiInDTO.setManufacturerId("AAAA");
        mdiInDTO.setManufacturerName("AAAA");
        mdiInDTO.setModel("AAAA");
        mdiInDTO.setProtocolType("CoAP");
        try {
			deviceManagement.modifyDeviceInfo(mdiInDTO, deviceId, null, accessToken);
			System.out.println("modify device info succeeded");
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
    }
    
    /**
     * 修改设备影子
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2023.html
     * @param deviceManagement
     * @param accessToken
     * @param deviceId
     */
    private static void modifyDeviceShadow(DeviceManagement deviceManagement, String accessToken, String deviceId) {
    	ModifyDeviceShadowInDTO mdsInDTO = new ModifyDeviceShadowInDTO();
        
        ServiceDesiredDTO sdDTO = new ServiceDesiredDTO();
        sdDTO.setServiceId("Brightness");        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("brightness", 100);
        sdDTO.setDesired(map);
        
        List<ServiceDesiredDTO> serviceDesireds = new ArrayList<ServiceDesiredDTO>();
        serviceDesireds.add(sdDTO);        
        mdsInDTO.setServiceDesireds(serviceDesireds);        
        
        try {
			deviceManagement.modifyDeviceShadow(mdsInDTO, deviceId, null, accessToken);
			System.out.println("修改设备影子成功");
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
    }
    
    /**
     * 刷新设备密钥
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2016.html
     * @param deviceManagement
     * @param accessToken
     * @param deviceId
     */
    private static void refreshDeviceKey(DeviceManagement deviceManagement, String accessToken, String deviceId) {
    	RefreshDeviceKeyInDTO rdkInDTO = new RefreshDeviceKeyInDTO();
    	Random random = new Random();	
		String nodeid = "testdemo" + (random.nextInt(9000000) + 1000000); //这是个测试 imei
		rdkInDTO.setNodeId(nodeid);
		rdkInDTO.setVerifyCode(nodeid);
    	rdkInDTO.setTimeout(3600);
    	
    	try {
    		RefreshDeviceKeyOutDTO rdkOutDTO = deviceManagement.refreshDeviceKey(rdkInDTO, deviceId, null, accessToken);
    		System.out.println(rdkOutDTO.toString());
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
    }
}
