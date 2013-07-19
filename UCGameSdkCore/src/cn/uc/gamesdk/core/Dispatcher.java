package cn.uc.gamesdk.core;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import cn.uc.gamesdk.iconst.CApi;
import cn.uc.gamesdk.iface.IActivityControl;
import cn.uc.gamesdk.iface.IDexClassLoader;
import cn.uc.gamesdk.iface.IDispatcher;
import cn.uc.gamesdk.ilistener.SdkCallbackListener;
import cn.uc.gamesdk.layout.ActivityLayout;

public class Dispatcher implements IDispatcher {
	private static final String CLASS_NAME = "DISPATCHER FROM CORE";

//	private static Dispatcher _dispatcher = null;
	private static IDexClassLoader _classLoader = null;

	private SdkCallbackListener sdkCallBackListener = null;
	private Map<String, IDispatcher> dispatcherMap = null;

//	public static Dispatcher getInstance() {
//		if (null == _dispatcher)
//			_dispatcher = new Dispatcher();
//
//		return _dispatcher;
//	}

	@Override
	public boolean loadClass(String clazz) {
		return false;
	}

	@Override
	public void invokeActivity(IActivityControl activity) {
		Log.d(CLASS_NAME, "invokeActivity");
		Activity mainActivity = (Activity) activity;
		ActivityLayout layout = new ActivityLayout(mainActivity);
		mainActivity.setContentView(layout);
	}

	@Override
	public Bundle apiInvoke(String apiName, Bundle data) {
		Bundle result = new Bundle();
		if (CApi.API_GET_SID.equals(apiName)) {
			result.putString("data", "this is from remote callback");
			sdkCallBackListener.callback(apiName, result);

			//调用不同的dex
			IDispatcher updateDispatcher = dispatcherMap.get(CApi.API_UPDATE);
			result = updateDispatcher.apiInvoke(CApi.API_UPDATE, result);
		}
		return result;
	}

	@Override
	public Bundle apiInvoke(String apiName) {
		return apiInvoke(apiName, null);
	}

	@Override
	public void register(SdkCallbackListener listener,
			Map<String, IDispatcher> dispatcherMap) {
		Log.d(CLASS_NAME, "set callback");
		sdkCallBackListener = listener;
		this.dispatcherMap = dispatcherMap;
	}

	public SdkCallbackListener getRegisterCallback() {
		return sdkCallBackListener;
	}

}
