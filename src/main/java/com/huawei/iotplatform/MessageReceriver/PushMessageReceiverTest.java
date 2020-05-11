package com.huawei.iotplatform.MessageReceriver;

import org.springframework.boot.SpringApplication;

import com.huawei.iotplatform.client.dto.NotifyBindDeviceDTO;
import com.huawei.iotplatform.client.dto.NotifyCommandRspDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceAddedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceDataChangedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceDatasChangedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceDeletedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceDesiredStatusChangedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceEventDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceInfoChangedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceModelAddedDTO;
import com.huawei.iotplatform.client.dto.NotifyDeviceModelDeletedDTO;
import com.huawei.iotplatform.client.dto.NotifyFwUpgradeResultDTO;
import com.huawei.iotplatform.client.dto.NotifyFwUpgradeStateChangedDTO;
import com.huawei.iotplatform.client.dto.NotifyMessageConfirmDTO;
import com.huawei.iotplatform.client.dto.NotifyNBCommandStatusChangedDTO;
import com.huawei.iotplatform.client.dto.NotifyServiceInfoChangedDTO;
import com.huawei.iotplatform.client.dto.NotifySwUpgradeResultDTO;
import com.huawei.iotplatform.client.dto.NotifySwUpgradeStateChangedDTO;
import com.huawei.iotplatform.client.invokeapi.PushMessageReceiver;

/**
 * 消息推送
 * 
 * 注册设备通知
 * 绑定设备通知
 * 设备信息变化通知
 * 设备数据变化通知
 * 批量设备数据变化通知
 * 设备服务信息变化通知
 * 删除设备通知
 * 设备消息确认通知
 * 设备命令响应通知
 * 设备事件通知
 * 增加设备模型通知
 * 删除设备模型通知
 * 设备影子状态变更通知
 * 软件升级状态变更通知
 * 软件升级结果变更通知
 * 固件升级状态变更通知
 * 固件升级结果变更通知
 * NB设备命令状态变化通知
 * 
 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2042.html
 * @author Lando_Li
 *
 */
public class PushMessageReceiverTest extends PushMessageReceiver{
	
	public static void main(String[] args) throws Exception {
		// api-client-demo使用SpringApplication运行此类
		SpringApplication.run(PushMessageReceiverTest.class);
	}
	
//	@Override
//	public void handleBody(String body) {
//		System.out.println("handleBody =====> " + body);
//	}
	
	/**
	 * 注册设备通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2043.html
	 */
	@Override
	public void handleDeviceAdded(NotifyDeviceAddedDTO body) {
		System.out.println("deviceAdded ==> " + body);
		//TODO 处理deviceAdded通知
	}

	/**
	 * 绑定设备通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2044.html
	 */
	@Override
	public void handleBindDevice(NotifyBindDeviceDTO body) {
		System.out.println("bindDevice ==> " + body);
		//TODO 处理BindDevice通知
	}

	/**
	 * 设备信息变化通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2045.html
	 */
	@Override
	public void handleDeviceInfoChanged(NotifyDeviceInfoChangedDTO body) {
		System.out.println("deviceInfoChanged ==> " + body);
		//TODO 处理DeviceInfoChanged通知
	}

	/**
	 * 设备数据变化通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2046.html
	 */
	@Override
	public void handleDeviceDataChanged(NotifyDeviceDataChangedDTO body) {
		System.out.println("deviceDataChanged ==> " + body);		
	}

	/**
	 * 批量设备数据变化通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2047.html
	 */
	@Override
	public void handleDeviceDatasChanged(NotifyDeviceDatasChangedDTO body) {
		System.out.println("deviceDatasChanged ==> " + body);
	}

	/**
	 * 设备服务信息变化通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2048.html
	 */
	@Override
	public void handleServiceInfoChanged(NotifyServiceInfoChangedDTO body) {
		System.out.println("serviceInfoChanged ==> " + body);
	}

	/**
	 * 删除设备通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2049.html
	 */
	@Override
	public void handleDeviceDeleted(NotifyDeviceDeletedDTO body) {
		System.out.println("deviceDeleted ==> " + body);
	}

	/**
	 * 设备消息确认通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2050.html
	 */
	@Override
	public void handleMessageConfirm(NotifyMessageConfirmDTO body) {
		System.out.println("messageConfirm ==> " + body);
	}

	/**
	 * 设备命令响应通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2051.html
	 */
	@Override
	public void handleCommandRsp(NotifyCommandRspDTO body) {
		System.out.println("commandRsp ==> " + body);
	}

	/**
	 * 设备事件通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2052.html
	 */
	@Override
	public void handleDeviceEvent(NotifyDeviceEventDTO body) {
		System.out.println("deviceEvent ==> " + body);
	}

	/**
	 * 增加设备模型通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2053.html
	 */
	@Override
	public void handleDeviceModelAdded(NotifyDeviceModelAddedDTO body) {
		System.out.println("deviceModelAdded ==> " + body);
	}

	/**
	 * 删除设备模型通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2054.html
	 */
	@Override
	public void handleDeviceModelDeleted(NotifyDeviceModelDeletedDTO body) {
		System.out.println("deviceModelDeleted ==> " + body);
	}


	/**
	 * 设备影子状态变更通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2056.html
	 */
	@Override
	public void handleDeviceDesiredStatusChanged(NotifyDeviceDesiredStatusChangedDTO body) {
		System.out.println("deviceDesiredStatusChanged ==> " + body);
	}

	/**
	 * 软件升级状态变更通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2057.html
	 */
	@Override
	public void handleSwUpgradeStateChanged(NotifySwUpgradeStateChangedDTO body) {
		System.out.println("swUpgradeStateChanged ==> " + body);
	}

	/**
	 * 软件升级结果变更通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2058.html
	 */
	@Override
	public void handleSwUpgradeResult(NotifySwUpgradeResultDTO body) {
		System.out.println("swUpgradeResult ==> " + body);
	}

	/**
	 * 固件升级状态变更通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2059.html
	 */
	@Override
	public void handleFwUpgradeStateChanged(NotifyFwUpgradeStateChangedDTO body) {
		System.out.println("fwUpgradeStateChanged ==> " + body);
	}

	/**
	 * 固件升级结果变更通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2060.html
	 */
	@Override
	public void handleFwUpgradeResult(NotifyFwUpgradeResultDTO body) {
		System.out.println("fwUpgradeResult ==> " + body);
	}

	/**
	 * NB设备命令状态变化通知
	 * https://support.huaweicloud.com/sdkreference-iot/iot_06_2061.html
	 */
	@Override
	public void handleNBCommandStateChanged(NotifyNBCommandStatusChangedDTO body) {
		System.out.println("NBCommandStateChanged ==> " + body);
	}
	
}
