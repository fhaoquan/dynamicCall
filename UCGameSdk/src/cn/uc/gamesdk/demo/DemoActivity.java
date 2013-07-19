package cn.uc.gamesdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.uc.gamesdk.ContainerActivity;
import cn.uc.gamesdk.DexLoader;
import cn.uc.gamesdk.R;
import cn.uc.gamesdk.iconst.CApi;
import cn.uc.gamesdk.iface.IDispatcher;
import cn.uc.gamesdk.ilistener.UCCallbackListener;
import cn.uc.gamesdk.tools.GlobalVars;
import cn.uc.gamesdk.tools.ReleaseJar;

public class DemoActivity extends Activity implements OnClickListener {
	private static final String CLASS_NAME = "DemoActivity";

	private Button btnRelease = null;
	private Button btnInit = null;
	private Button btnLogin = null;
	private Button btnClear = null;
	private Button btnActivity = null;

	private TextView tvMessage = null;

	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_dex_loader);

		GlobalVars.context = this;

		btnRelease = (Button) findViewById(R.id.btn_release);
		btnInit = (Button) findViewById(R.id.btn_init);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnClear = (Button) findViewById(R.id.btn_clear);
		btnActivity = (Button) findViewById(R.id.btn_activity);

		btnRelease.setOnClickListener(this);
		btnInit.setOnClickListener(this); 
		btnLogin.setOnClickListener(this);
		btnActivity.setOnClickListener(this);

		tvMessage = (TextView) findViewById(R.id.textView3);

	} 

	@Override  
	public void onClick(View view) {  
		if (view == btnRelease) {  
			tvMessage.setText("");

			DexLoader.getInstance().releaseControl(); 
			ReleaseJar.releaseJar();
		} else if (view == btnClear) {
			tvMessage.setText("");
		} else if (view == btnActivity) { 
			startActivity(new Intent(this, ContainerActivity.class));
		} else if (view == btnInit) {  
			//初始化DexLoader，包括配置及依赖注入等 
			
		} else if (view == btnLogin) {
			IDispatcher classDispatcher = DexLoader.getInstance().Creator(
					CApi.API_LOGIN);
			if (null != classDispatcher)
				classDispatcher.loadMethodFromClass(CApi.API_LOGIN,
						new UCCallbackListener<String>() {

							@Override
							public void callback(int statuscode, String data) {
								Log.d(CLASS_NAME, "callback from dex:" + data);
								tvMessage.append("callback from dex:" + data
										+ "\n");
							}

						}, new Object[] { String.valueOf(System
								.currentTimeMillis()) });
		}
	}
}
