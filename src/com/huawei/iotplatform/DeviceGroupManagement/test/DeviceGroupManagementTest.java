package com.huawei.iotplatform.DeviceGroupManagement.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.CreateDeviceGroupInDTO;
import com.huawei.iotplatform.client.dto.CreateDeviceGroupOutDTO;
import com.huawei.iotplatform.client.dto.DeviceGroupWithDeviceListDTO;
import com.huawei.iotplatform.client.dto.ModifyDeviceGroupInDTO;
import com.huawei.iotplatform.client.dto.ModifyDeviceGroupOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceGroupMembersInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceGroupMembersOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceGroupsInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceGroupsOutDTO;
import com.huawei.iotplatform.client.dto.QuerySingleDeviceGroupOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceGroupManagement;
import com.huawei.iotplatform.utils.AuthUtil;

/**
 * 设备组管理
 * 
 * 创建设备组
 * 删除设备组
 * 修改设备组
 * 查询设备组详情
 * 查询指定设备组
 * 查询指定设备组成员
 * 增加设备组成员
 * 删除设备组成员
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2076.html
 * @author Lando_Li
 *
 */
public class DeviceGroupManagementTest {
	public static void main(String args[]) throws Exception {
		/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
    	DeviceGroupManagement groupManagement = new DeviceGroupManagement(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        
        String deviceId1 = "cb15cbb6-04ce-4e2b-bf35-d6a264c74646";
        String deviceId2 = "ab5dfcec-b156-40c7-86a9-0dc3cbb7bad6";
        String deviceId3 = "9d1d6a70-33c1-47e5-89e7-50a4369fbe95";
        
        /**---------------------创建设备组------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2078.html
        System.out.println("======创建设备组======");
        CreateDeviceGroupInDTO cdgInDTO = new CreateDeviceGroupInDTO();
        Random random = new Random();
        String groupName = "group" + (random.nextInt(9000000) + 1000000);//这是测试组名称
        cdgInDTO.setName(groupName);
        // 将两个设备添加到列表中
        List<String> deviceIdList = new ArrayList<String>();
        deviceIdList.add(deviceId1);
        deviceIdList.add(deviceId2);
        cdgInDTO.setDeviceIds(deviceIdList);
        
        CreateDeviceGroupOutDTO cdgOutDTO = groupManagement.createDeviceGroup(cdgInDTO, accessToken);
        if (cdgOutDTO != null) {
        	System.out.println(cdgOutDTO.toString());
        	String groupId = cdgOutDTO.getId();
        	
        	/**---------------------修改设备组------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2080.html
        	System.out.println("\n======修改设备组======");
        	ModifyDeviceGroupInDTO mdgInDTO = new ModifyDeviceGroupInDTO();        	
        	Random r = new Random();
            String name = "group" + (r.nextInt(9000000) + 1000000);//这是测试组名称
        	mdgInDTO.setName(name);
        	ModifyDeviceGroupOutDTO mdgOutDTO = groupManagement.modifyDeviceGroup(mdgInDTO, groupId, null, accessToken);
        	System.out.println(mdgOutDTO.toString());
        	
        	/**---------------------查询指定设备组------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2082.html
        	System.out.println("\n======查询指定设备组======");
        	QuerySingleDeviceGroupOutDTO qsdgOutDTO = groupManagement.querySingleDeviceGroup(groupId, null, accessToken);
        	System.out.println(qsdgOutDTO.toString());
        	
        	/**---------------------查询指定设备组成员------------------------*/
        	// https://support.huaweicloud.com/sdkreference-iot/iot_06_2083.html
        	System.out.println("\n======查询指定设备组成员======");
        	QueryDeviceGroupMembersInDTO qdgmInDTO = new QueryDeviceGroupMembersInDTO();
        	qdgmInDTO.setDevGroupId(groupId);
        	QueryDeviceGroupMembersOutDTO qdgmOutDTO = groupManagement.queryDeviceGroupMembers(qdgmInDTO, accessToken);
        	System.out.println(qdgmOutDTO.toString());
        	
        	/**---------------------增加设备组成员------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2084.html
        	System.out.println("\n======添加设备组成员======");
        	DeviceGroupWithDeviceListDTO dgwdlDTO = new DeviceGroupWithDeviceListDTO();
        	dgwdlDTO.setDevGroupId(groupId);
        	// 将新设备添加到列表
        	List<String> list = new ArrayList<String>();
        	list.add(deviceId3);
        	dgwdlDTO.setDeviceIds(list);
        	DeviceGroupWithDeviceListDTO dgwdlDTO_rsp = groupManagement.addDevicesToGroup(dgwdlDTO, null, accessToken);
        	System.out.println(dgwdlDTO_rsp.toString());
        	
        	/**---------------------删除设备组成员------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2085.html
        	System.out.println("\n======删除设备组成员======");
        	groupManagement.deleteDevicesFromGroup(dgwdlDTO, null, accessToken);
        	System.out.println("删除设备组成员成功");
		}
        
        /**---------------------查询设备组详情------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2081.html
        System.out.println("\n======查询设备组详情======");
        QueryDeviceGroupsInDTO qdgInDTO = new QueryDeviceGroupsInDTO();
        qdgInDTO.setPageNo(0);
        qdgInDTO.setPageSize(10);
        QueryDeviceGroupsOutDTO qdgOutDTO = groupManagement.queryDeviceGroups(qdgInDTO, accessToken);
        System.out.println(qdgOutDTO.toString());
        
        // 删除页面0的所有设备组
        List<QuerySingleDeviceGroupOutDTO> groupList = qdgOutDTO.getList();
        for (QuerySingleDeviceGroupOutDTO querySingleDeviceGroupOutDTO : groupList) {
        	/**---------------------删除设备组------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2079.html
        	System.out.println("\n======删除设备组======");
        	groupManagement.deleteDeviceGroup(querySingleDeviceGroupOutDTO.getId(), null, accessToken);
        	System.out.println("成功删除设备组");
		}
	}
}
