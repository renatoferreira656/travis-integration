package com.ci.urlfetch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class UrlFecth extends Activity {

	private final SongHelper sh = new SongHelper(this);

	private UIUpdater ui;

	private WakeLock wakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url_fecth);

		sh.populateSpinnerMusic();
		ui = UIUpdater.create(sh);

		PowerManager mgr = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");

		selectMusicButton();
		playButton();
		stopButton();
		startMonitor();
		stopMonitor();
	}

	private void startMonitor() {
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ui.startUpdates();
				wakeLock.acquire();
			}
		});
	}

	private void stopMonitor() {
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ui.stopUpdates();
				wakeLock.release();
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

}
