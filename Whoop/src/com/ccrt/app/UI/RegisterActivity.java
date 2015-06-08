package com.ccrt.app.UI;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ccrt.app.bean.Constant;
import com.ccrt.app.bean.User;
import com.ccrt.app.util.AppLogger;
import com.ccrt.app.util.HttpUtil;
import com.ccrt.app.util.StaticFeild;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 用户注册界面
 * @author Administrator
 *
 */
public class RegisterActivity extends Activity implements OnClickListener{
	private EditText editTextUsername;//用户名
	private EditText editTextPassword;//密码
	private EditText editTextAge;//年龄
	private EditText editTextPhone;//电话
	private RadioGroup radioGrouptSex;//
	private RadioButton radioButtonWomen;//性别--女
	private RadioButton radioButtonMen;//性别--男
	private Button buttonRegister;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findViewById();
	}


	private void findViewById() {
		editTextUsername = (EditText) findViewById(R.id.register_input_name);
		editTextPassword = (EditText) findViewById(R.id.register_input_password);
		editTextAge = (EditText) findViewById(R.id.editText_register_age);
		radioGrouptSex = (RadioGroup) findViewById(R.id.radioGroup_register_sex);
		radioButtonWomen = (RadioButton) findViewById(R.id.radio_register_women);
		radioButtonMen = (RadioButton) findViewById(R.id.radio_register_men);
	    editTextPhone = (EditText) findViewById(R.id.editText_register_phone);
	    buttonRegister = (Button) findViewById(R.id.button_register_register);
	    buttonRegister.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_register_register://注册用户
			String account = editTextUsername.getText().toString().trim();//账户
			String password = editTextPassword.getText().toString().trim();//密码
			User user = new User(); //这里用javabean或者Map都可以
			user.setAccount(account);
			user.setPassword(password);
			//异步请求，不需要再多开一个线程，如果想用同步请求，可以用HttpSupport
			HttpUtil.sendPost(RegisterActivity.this, Constant.REGISTER, user, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					try {
						String responseString = response.toString();
						JSONObject json = new JSONObject(responseString);
						String state = json.optString("state");
						String msg = json.optString("msg");
						AppLogger.d("resultRegister  :  " + responseString);
						super.onSuccess(statusCode, headers, response);
						
					} catch (Exception e) {
						e.printStackTrace();
						AppLogger.e(StaticFeild.TAG +  e.getMessage());
					}
					
				}
				
			});
			
			
			break;

		default:
			break;
		}
	}
	
	

}
