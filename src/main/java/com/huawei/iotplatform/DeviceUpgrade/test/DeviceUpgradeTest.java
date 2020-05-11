package com.huawei.iotplatform.DeviceUpgrade.test;

import java.util.ArrayList;
import java.util.List;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.CreateUpgradeTaskInDTO;
import com.huawei.iotplatform.client.dto.CreateUpgradeTaskOutDTO;
import com.huawei.iotplatform.client.dto.OperateDevices;
import com.huawei.iotplatform.client.dto.QueryUpgradePackageListInDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradePackageListOutDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradePackageOutDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradeSubTaskInDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradeSubTaskOutDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradeTaskListInDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradeTaskListOutDTO;
import com.huawei.iotplatform.client.dto.QueryUpgradeTaskOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceUpgrade;
import com.huawei.iotplatform.utils.AuthUtil;

/**
 * 设备升级
 * 
 * 查询版本包列表
 * 查询指定版本包
 * 删除指定版本包
 * 创建软件升级任务
 * 创建固件升级任务
 * 查询指定升级任务结果
 * 查询指定升级任务子任务详情
 * 查询升级任务列表
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2086.html
 * @author Lando_Li
 *
 */
public class DeviceUpgradeTest {
	
	public static void main(String args[]) throws Exception {
    	/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceUpgrade deviceUpgrade = new DeviceUpgrade(northApiClient);
    	
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        String deviceId = "09691354-872d-49a1-9c1c-a18512f10ae2";//这是个测试设备
        
        /**---------------------查询版本包列表------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2087.html
        System.out.println("======查询版本包列表（固件和软件）======");
        QueryUpgradePackageListInDTO quplInDTO = new QueryUpgradePackageListInDTO();
        quplInDTO.setPageNo(0);
        quplInDTO.setPageSize(10);
        quplInDTO.setFileType("firmwarePackage");//查询固件包列表
        QueryUpgradePackageListOutDTO quplOutDTO_firmware = deviceUpgrade.queryUpgradePackageList(quplInDTO, accessToken);
        System.out.println(quplOutDTO_firmware.toString());
        
        if (quplOutDTO_firmware.getTotalCount()==0) {
        	System.out.println("请上传固件包");
        }
        
        quplInDTO.setFileType("softwarePackage");//查询软件包列表
        QueryUpgradePackageListOutDTO quplOutDTO_software = deviceUpgrade.queryUpgradePackageList(quplInDTO, accessToken);
        System.out.println(quplOutDTO_software.toString());
        
        if (quplOutDTO_software.getTotalCount()==0) {
        	System.out.println("请上传软件包");
        }
        
        List<QueryUpgradePackageOutDTO> packageList_firmware = quplOutDTO_firmware.getData();
        
        if (packageList_firmware != null && packageList_firmware.size() > 0) {
        	
			for (QueryUpgradePackageOutDTO queryUpgradePackageOutDTO : packageList_firmware) {
				/**---------------------查询指定版本包------------------------*/	
				//https://support.huaweicloud.com/sdkreference-iot/iot_06_2088.html
				System.out.println("\n======查询指定版本包======");
				QueryUpgradePackageOutDTO qupOutDTO = deviceUpgrade.queryUpgradePackage(queryUpgradePackageOutDTO.getFileId(), accessToken);
				System.out.println(qupOutDTO.toString());
			}
			
			/**---------------------创建固件升级任务------------------------*/
			//https://support.huaweicloud.com/sdkreference-iot/iot_06_2091.html
			System.out.println("\n======创建固件升级任务======");
			// 从列表中查找测试包
			QueryUpgradePackageOutDTO package0 = packageList_firmware.get(0);
			
			CreateUpgradeTaskOutDTO cutOutDTO_firmware = createFirmwareUpgradeTask(deviceUpgrade, package0.getFileId(), deviceId, accessToken);
		    
		    if (cutOutDTO_firmware != null) {
		    	System.out.println(cutOutDTO_firmware.toString());
		    	
		    	/**---------------------查询指定升级任务结果------------------------*/
		    	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2092.html
		    	System.out.println("\n======查询指定升级任务结果======");
		    	QueryUpgradeTaskOutDTO qutOutDTO = deviceUpgrade.queryUpgradeTask(cutOutDTO_firmware.getOperationId(), accessToken);
		    	System.out.println(qutOutDTO.toString());
		    	
		    	/**---------------------查询指定升级任务子任务详情------------------------*/
		    	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2093.html
		    	System.out.println("\n======查询指定升级任务子任务详情======");
		    	QueryUpgradeSubTaskInDTO qustInDTO = new QueryUpgradeSubTaskInDTO();
		    	qustInDTO.setPageNo(0);
		    	qustInDTO.setPageSize(10);
		    	QueryUpgradeSubTaskOutDTO qustOutDTO = deviceUpgrade.queryUpgradeSubTask(qustInDTO, cutOutDTO_firmware.getOperationId(), accessToken);
		    	System.out.println(qustOutDTO.toString());
			}
		    
		    // 删除第二个固件包
		    if (packageList_firmware.size() > 1) {
		    	QueryUpgradePackageOutDTO package1 = packageList_firmware.get(1);
		    	/**---------------------删除指定版本包------------------------*/
		    	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2089.html
		    	System.out.println("\n======删除指定版本包======");
		    	deviceUpgrade.deleteUpgradePackage(package1.getFileId(), accessToken);
		    	System.out.println("成功删除指定的升级包");
			}
		}
        
        /**---------------------查询升级任务列表------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2094.html
        System.out.println("\n======查询升级任务列表======");
        QueryUpgradeTaskListInDTO qutlInDTO = new QueryUpgradeTaskListInDTO();
        qutlInDTO.setPageNo(0);
        qutlInDTO.setPageSize(10);
        QueryUpgradeTaskListOutDTO qutlOutDTO = deviceUpgrade.queryUpgradeTaskList(qutlInDTO, accessToken);
        System.out.println(qutlOutDTO.toString());

        
        List<QueryUpgradePackageOutDTO> packageList_software = quplOutDTO_software.getData();
        if (packageList_software != null && packageList_software.size() > 0) {
        	/**---------------------创建软件升级任务------------------------*/
        	// https://support.huaweicloud.com/sdkreference-iot/iot_06_2090.html
        	System.out.println("\n======创建软件升级任务======");
			// 从列表中查找测试包
			QueryUpgradePackageOutDTO package0 = packageList_software.get(0);
			CreateUpgradeTaskInDTO cutInDTO = new CreateUpgradeTaskInDTO();
		    cutInDTO.setFileId(package0.getFileId());
		    // 设置目标设备
		    OperateDevices targets = new OperateDevices();
		    List<String> devices = new ArrayList<String>();
		    devices.add(deviceId); 
		    targets.setDevices(devices);
		    cutInDTO.setTargets(targets);
		    
		    CreateUpgradeTaskOutDTO cutOutDTO_software = deviceUpgrade.createSoftwareUpgradeTask(cutInDTO, accessToken);
		    System.out.println(cutOutDTO_software.toString());
        }
    }
	
	/**
	 * 创建固件升级任务
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2091.html
	 * @param deviceUpgrade
	 * @param FileId
	 * @param deviceId
	 * @param accessToken
	 * @return
	 */
	private static CreateUpgradeTaskOutDTO createFirmwareUpgradeTask(DeviceUpgrade deviceUpgrade, String FileId,
			String deviceId, String accessToken) {
		CreateUpgradeTaskInDTO cutInDTO = new CreateUpgradeTaskInDTO();
	    cutInDTO.setFileId(FileId);
	    // 设置目标设备
	    OperateDevices targets = new OperateDevices();
	    List<String> devices = new ArrayList<String>();
	    devices.add(deviceId); 
	    targets.setDevices(devices);
	    cutInDTO.setTargets(targets);
	    
	    try {
			CreateUpgradeTaskOutDTO cutOutDTO_firmware = deviceUpgrade.createFirmwareUpgradeTask(cutInDTO, accessToken);
			return cutOutDTO_firmware;
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
		return null;
	}
}
