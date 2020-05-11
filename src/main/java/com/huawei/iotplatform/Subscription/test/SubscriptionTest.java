package com.huawei.iotplatform.Subscription.test;


import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.DeleteBatchSubInDTO;
import com.huawei.iotplatform.client.dto.QueryBatchSubInDTO;
import com.huawei.iotplatform.client.dto.QueryBatchSubOutDTO;
import com.huawei.iotplatform.client.dto.SubDeviceDataInDTO;
import com.huawei.iotplatform.client.dto.SubDeviceManagementDataInDTO;
import com.huawei.iotplatform.client.dto.SubscriptionDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.SubscriptionManagement;
import com.huawei.iotplatform.utils.AuthUtil;
import com.huawei.iotplatform.utils.PropertyUtil;

/**
 * 订阅管理
 * 
 * 订阅平台业务数据
 * 订阅平台管理数据
 * 查询单个订阅
 * 批量查询订阅
 * 删除单个订阅
 * 批量删除订阅
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2035.html
 * @author Lando_Li
 *
 */
public class SubscriptionTest
{
    public static void main(String args[]) throws Exception
    {
    	/**---------------------初始化 northApiClient------------------------*/
    	NorthApiClient northApiClient = AuthUtil.initApiClient();
    	SubscriptionManagement subscriptionManagement = new SubscriptionManagement(northApiClient);
        
        /**---------------------首先获取accessToken------------------------*/
        Authentication authentication = new Authentication(northApiClient);        
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        String accessToken = authOutDTO.getAccessToken();
        
        /**---------------------订阅平台业务数据------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2036.html
        //注意：10.X.X.X是LAN IP，不是公共IP，因此订阅callbackUrl的IP不能是10.X.X.X
        System.out.println("======订阅平台业务数据通知======");
        String callbackUrl = "https://10.63.163.181:8099/v1.0.0/messageReceiver";// 这是一个测试callbackUrl       
        SubscriptionDTO subDTO = subDeviceData(subscriptionManagement, "deviceAdded", callbackUrl, accessToken);
        subDeviceData(subscriptionManagement, "deviceDataChanged", callbackUrl, accessToken);
        
        /**---------------------订阅平台管理数据------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2037.html
        System.out.println("\n======订阅平台管理数据通知======");
        subDeviceManagementData(subscriptionManagement, "swUpgradeResultNotify", callbackUrl, accessToken);
        
        if (subDTO != null) {
        	/**---------------------查询单个订阅------------------------*/
        	//https://support.huaweicloud.com/sdkreference-iot/iot_06_2038.html
        	System.out.println("\n======查询单个订阅======");
        	SubscriptionDTO subDTO2 = subscriptionManagement.querySingleSubscription(subDTO.getSubscriptionId(), null, accessToken);
        	System.out.println(subDTO2.toString());
        	
        	/**---------------------删除单个订阅------------------------*/
        	// https://support.huaweicloud.com/sdkreference-iot/iot_06_2040.html
        	System.out.println("\n======删除单个订阅======");
        	subscriptionManagement.deleteSingleSubscription(subDTO.getSubscriptionId(), null, accessToken);
        	System.out.println("删除单个订阅成功");
		}
        
        /**---------------------批量查询订阅------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2039.html
        System.out.println("\n======批量查询订阅======");
        QueryBatchSubInDTO qbsInDTO = new QueryBatchSubInDTO();
        qbsInDTO.setAppId(PropertyUtil.getProperty("appId"));
        QueryBatchSubOutDTO qbsOutDTO = subscriptionManagement.queryBatchSubscriptions(qbsInDTO, accessToken);
        System.out.println(qbsOutDTO.toString());
        
        /**---------------------批量删除订阅------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2041.html
        System.out.println("\n======批量删除订阅======");
        DeleteBatchSubInDTO dbsInDTO = new DeleteBatchSubInDTO();
        dbsInDTO.setAppId(PropertyUtil.getProperty("appId"));
        try {
        	subscriptionManagement.deleteBatchSubscriptions(dbsInDTO, accessToken);
        	System.out.println("批量删除订阅成功");
		} catch (NorthApiException e) {
			if ("200001".equals(e.getError_code())) {
				System.out.println("无任何订阅");
			}
		}
        
    }
    
    /**
     * 订阅平台业务数据
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2036.html
     * @param subscriptionManagement
     * @param notifyType
     * @param callbackUrl
     * @param accessToken
     * @return
     */
    private static SubscriptionDTO subDeviceData(SubscriptionManagement subscriptionManagement, 
    		String notifyType, String callbackUrl, String accessToken) {
    	SubDeviceDataInDTO sddInDTO = new SubDeviceDataInDTO();
    	sddInDTO.setNotifyType(notifyType);
    	sddInDTO.setCallbackUrl(callbackUrl);
    	try {
    		SubscriptionDTO subDTO = subscriptionManagement.subDeviceData(sddInDTO, null, accessToken);
    		System.out.println(subDTO.toString());
			return subDTO;
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
    	return null;
    }
    
    /**
     * 订阅平台管理数据
     * https://support.huaweicloud.com/sdkreference-iot/iot_06_2037.html
     * @param subscriptionManagement
     * @param notifyType
     * @param callbackUrl
     * @param accessToken
     */
    private static void subDeviceManagementData(SubscriptionManagement subscriptionManagement, 
    		String notifyType, String callbackUrl, String accessToken) {
    	SubDeviceManagementDataInDTO sddInDTO = new SubDeviceManagementDataInDTO();
    	sddInDTO.setNotifyType(notifyType);
    	sddInDTO.setCallbackurl(callbackUrl);
    	try {
			subscriptionManagement.subDeviceData(sddInDTO, accessToken);
			System.out.println("订阅平台管理数据成功");
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}
    	return;
    }
}
