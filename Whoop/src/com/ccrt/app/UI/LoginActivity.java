package com.ccrt.app.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * 用户登录界面
 * @author Administrator
 *
 */
public class LoginActivity extends Activity implements OnClickListener{
	private EditText editTextUsername;
	private EditText editTextPassword;
	private CheckBox mCheckBox;
	private Button buttonLogin;
	private Button buttonRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById();
		
	}

	/**
	 * 初始化控件
	 */
	private void findViewById() {
		editTextUsername = (EditText) findViewById(R.id.login_input_name);
		editTextPassword = (EditText) findViewById(R.id.login_input_password);
		mCheckBox = (CheckBox) findViewById(R.id.remember_user);
		buttonLogin = (Button) findViewById(R.id.login_comfirm_button);
		buttonLogin.setOnClickListener(this);
		buttonRegister = (Button) findViewById(R.id.register_link);
		buttonRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.login_comfirm_button://登录按钮
			intent = new Intent(this.getApplicationContext(), MainActivity.class);
			break;
		case R.id.register_link://注册按钮
			intent = new Intent(this.getApplicationContext(), RegisterActivity.class);
			break;

		default:
			break;
		}
		
		
		startActivity(intent);
	}
	
	

}
