package com.huawei.iotplatform.Authentication.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshInDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.utils.AuthUtil;
import com.huawei.iotplatform.utils.PropertyUtil;

/**
 * 应用安全接入
 * 
 * 获取鉴权
 * 刷新token
 * 定时刷新token
 * 停止定时刷新token
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2009.html
 * @author Lando_Li
 *
 */
public class AuthenticationTest
{
    public static void main(String args[]) throws Exception
    {    	
    	/**---------------------初始化northApiClient------------------------*/
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        northApiClient.getVersion();
        
        /**----------------------获取鉴权-------------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2010.html
        System.out.println("======获取鉴权======");
        Authentication authentication = new Authentication(northApiClient);
        
        // 获取 access token
        AuthOutDTO authOutDTO = authentication.getAuthToken();        
        System.out.println(authOutDTO.toString());
        
        /**----------------------刷新token--------------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2011.html
        System.out.println("\n======刷新token======");
        AuthRefreshInDTO authRefreshInDTO = new AuthRefreshInDTO();
        
        authRefreshInDTO.setAppId(PropertyUtil.getProperty("appId"));
        authRefreshInDTO.setSecret(northApiClient.getClientInfo().getSecret());
        
        // 从Authentication的输出参数（即authOutDTO）获取refreshToken
        String refreshToken = authOutDTO.getRefreshToken();
        authRefreshInDTO.setRefreshToken(refreshToken);
        
        AuthRefreshOutDTO authRefreshOutDTO = authentication.refreshAuthToken(authRefreshInDTO);
        
        System.out.println(authRefreshOutDTO.toString());
        
        /**----------------------定时刷新token--------------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2012.html
        System.out.println("\n======定时刷新token======");
        authentication.startRefreshTokenTimer();
        
        /**----------------------停止定时刷新token--------------------------------*/
        //https://support.huaweicloud.com/sdkreference-iot/iot_06_2013.html
        System.out.println("\n======停止定时刷新token======");
        authentication.stopRefreshTokenTimer();
        
    }
}
