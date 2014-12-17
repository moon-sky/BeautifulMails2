package com.dreamfac.beautifulmails;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements TextWatcher {
	AutoTextViewAdapter mAdapter;
	AutoCompleteTextView acTextView;
	public static String preference="EMAIL";
	private static final String[] AUTO_EMAILS = {"@163.com", "@sina.com", "@qq.com", "@126.com", "@gmail.com", "@apple.com"};
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.ac_login);
	acTextView=(AutoCompleteTextView)findViewById(R.id.acTextView);
	mAdapter = new AutoTextViewAdapter(this);
	acTextView.setAdapter(mAdapter);
	acTextView.addTextChangedListener(this);
	findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String input=acTextView.getText().toString();
			if(input.length()>0&&input.contains("@")){
				String account=input.substring(0, input.lastIndexOf("@"));
				if(account.length()>=3){
					Intent it=new Intent(LoginActivity.this,MainActivity.class);
					SharedPreferences preferences=getSharedPreferences(preference, MODE_PRIVATE);
					Editor editor=preferences.edit();
					editor.putString("account", input);
					editor.commit();
					startActivity(it);
					finish();
				}else{
					Toast.makeText(LoginActivity.this, "输入不对，请重新输入", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(LoginActivity.this, "输入不对，请重新输入", Toast.LENGTH_SHORT).show();
			}
		}
	});
}
/**
 * 自动填充邮箱列表
 * @param input
 */
private void autoAddEmails(String input) {
	System.out.println("input-->" + input);
	String autoEmail = "";
    if (input.length() > 0) {  
        for (int i = 0; i < AUTO_EMAILS.length; ++i) {  
        	if(input.contains("@")) {//包含“@”则开始过滤
        		String filter = input.substring(input.indexOf("@") + 1 , input.length());//获取过滤器，即根据输入“@”之后的内容过滤出符合条件的邮箱
        		System.out.println("filter-->" + filter);
        		if(AUTO_EMAILS[i].contains(filter)) {//符合过滤条件
        			autoEmail = input.substring(0, input.indexOf("@")) + AUTO_EMAILS[i];//用户输入“@”之前的内容加上自动填充的内容即为最后的结果
        			mAdapter.mList.add(autoEmail);
        		}
        	}else {
        		autoEmail = input + AUTO_EMAILS[i];
        		mAdapter.mList.add(autoEmail);
        	}
        }  
    } 
}
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	// TODO Auto-generated method stub
	String input = s.toString();  
    mAdapter.mList.clear();  
    autoAddEmails(input);
    mAdapter.notifyDataSetChanged();  
    acTextView.showDropDown();  
}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
	// TODO Auto-generated method stub
	
}
@Override
public void afterTextChanged(Editable s) {
	// TODO Auto-generated method stub
	
}
}
