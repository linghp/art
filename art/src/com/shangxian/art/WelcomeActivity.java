package com.shangxian.art;


import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class WelcomeActivity extends Activity {

	private Timer timer_sys_check;
	
	  private Handler handler = new Handler() {
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case 1:
	            	   timer_sys_check.cancel();
	            	   finish();
	            	   overridePendingTransition(R.anim.anim_right_in, R.anim.slide_left_out);
	                   startMainActivity();
	                break;
	            }
	       
	        }

			private void startMainActivity() {
				Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(intent);
			}
	    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		timer_sys_check = new Timer();
		timer_sys_check.schedule(new Page_check_task(), 2000);
			
	}
	
	class Page_check_task extends java.util.TimerTask {
		@Override
		public void run() {
			Message ms = new Message();
			ms.what = 1;
			handler.sendMessage(ms);
		}
	}
}
