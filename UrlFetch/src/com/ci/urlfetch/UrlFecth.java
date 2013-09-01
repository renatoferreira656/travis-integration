package com.ci.urlfetch;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class UrlFecth extends Activity {

	private String SENDER_ID = "197385157020";

	private final SongHelper sh = new SongHelper(this);

	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url_fecth);

		sh.populateSpinnerMusic();
		context = getApplicationContext();

		((EditText) findViewById(R.id.textView2)).setText(Utils.getValue(Utils.GCM_ID, "", getApplicationContext()));

		selectMusicButton();
		registerButton();
		playButton();
		stopButton();
		gcm = GoogleCloudMessaging.getInstance(this);
	}

	private void registerButton() {
		Button button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				registerBackground();
			}
		});
	}

	private void stopButton() {
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sh.stop();
			}
		});
	}

	private void playButton() {
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sh.songPrepareASync();
			}
		});
	}

	private void selectMusicButton() {
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner s = (Spinner) findViewById(R.id.spinnerMusic);
				Utils.putValue(SongHelper.MUSIC_PATH, (String) s.getSelectedItem(), getApplicationContext());
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void registerBackground() {
		new AsyncTask() {
			@Override
			protected String doInBackground(Object... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					String regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration id=" + regid;
					((EditText) findViewById(R.id.textView2)).setText(regid);

					Utils.putValue(Utils.GCM_ID, regid, context);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(Object result) {
				Toast toast = Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT);
				toast.show();
			}
		}.execute(null, null, null);
	}

}
