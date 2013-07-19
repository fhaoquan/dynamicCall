package cn.uc.gamesdk;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import cn.uc.gamesdk.iconst.CApi;
import cn.uc.gamesdk.iface.IActivityControl;
import cn.uc.gamesdk.iface.IDispatcher;
import cn.uc.gamesdk.ilistener.UCCallbackListener;

public class UCGameSdk {

	private static final String CLASS_NAME = "UCGameSdk";

	private static UCGameSdk ucgamesdk = null;

	private Map<String, IDispatcher> dexLoaderMap = null;

	public static UCGameSdk getDefault() {
		if (null == ucgamesdk)
			ucgamesdk = new UCGameSdk();

		return ucgamesdk;
	}

	/*
	 * 获取配置列表并将其中各DEX的实例化接口类,并将配置表中的定义实例化
	 */
	public boolean init() {
		dexLoaderMap = DexLoader.getInstance().Creator();
		return false;
	}

	public void login(Activity activity, UCCallbackListener<String> listener) {
		IDispatcher loginDispatcher = dexLoaderMap.get(CApi.API_LOGIN);// 获取login接口对应的dex接口入口实例

		loginDispatcher.apiInvoke(CApi.API_LOGIN);

	}

	public void enterUI(IActivityControl activity) {
		Log.d(CLASS_NAME, "invokeActivity1");
		
		IDispatcher uiDispatcher = dexLoaderMap.get(CApi.API_WEBVIEW);
		uiDispatcher.invokeActivity(activity);
	}

	public String getSid() {
		IDispatcher sidDispatcher = dexLoaderMap.get(CApi.API_GET_SID);

		Bundle data = sidDispatcher.apiInvoke(CApi.API_GET_SID);
		return data.getString("sid");
	}

	public String update() {
		IDispatcher updateDispatcher = dexLoaderMap.get(CApi.API_UPDATE);
		if (null != updateDispatcher.apiInvoke(CApi.API_UPDATE)) {
			return "return true from update call";

		} else {
			return "return false from update call";
		}
	}
}
