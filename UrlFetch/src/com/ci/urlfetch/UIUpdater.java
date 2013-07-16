package com.ci.urlfetch;

import android.os.Handler;

public class UIUpdater {

	private static final int TIME_MUSIC_PLAYING = 120000;

	private static final int TIME_MONITORING = 120000;

	private final static FetchTravis ft = new FetchTravis();

	private Handler mHandler = new Handler();

	private Runnable mStatusChecker;

	public static UIUpdater create(final SongHelper sh) {
		return new UIUpdater(new Runnable() {

			@Override
			public void run() {
				if (!ft.isBuildSuccess()) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							sh.stop();
						}
					}, TIME_MUSIC_PLAYING);
					sh.songPrepareASync();
				}
			}
		}, TIME_MONITORING);
	}

	public UIUpdater(final Runnable uiUpdater, final int updateInterval) {
		mStatusChecker = new Runnable() {
			@Override
			public void run() {
				uiUpdater.run();
				mHandler.postDelayed(this, updateInterval);
			}
		};
	}

	public void startUpdates() {
		mStatusChecker.run();
	}

	public void stopUpdates() {
		mHandler.removeCallbacks(mStatusChecker);
	}
}