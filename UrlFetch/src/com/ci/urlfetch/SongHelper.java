package com.ci.urlfetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SongHelper implements OnPreparedListener {

	private static final String SONG_PATH = "songPath";

	private static final String SONG_TITLE = "songTitle";

	public static final String MUSIC_PATH = "music_path";

	private final MediaPlayer mp = new MediaPlayer();

	private Activity context;

	private List<Map<String, String>> spinner = new ArrayList<Map<String, String>>();

	public SongHelper(Activity context) {
		this.context = context;
		mp.setLooping(true);
		mp.setOnPreparedListener(this);
	}

	public void songPrepareASync() {
		String item = Utils.getValue(MUSIC_PATH, "", context);
		for (Map<String, String> map : spinner) {
			if (map.get(SONG_TITLE).equals(item)) {
				mp.reset();
				try {
					mp.setDataSource(map.get(SONG_PATH));
					mp.prepareAsync();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onPrepared(MediaPlayer player) {
		player.start();
	}

	public void stop() {
		mp.stop();
	}

	private ArrayAdapter<String> createArrayAdapter() {
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (Map<String, String> map : spinner) {
			adapter.add(map.get(SONG_TITLE));
		}
		return adapter;
	}

	private void createPlayList() {
		final Cursor mCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA }, null, null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

		String songs_name = "";
		String mAudioPath = "";
		if (mCursor.moveToFirst()) {
			do {

				songs_name = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
				mAudioPath = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				Map<String, String> song = new HashMap<String, String>();

				song.put(SONG_TITLE, songs_name);
				song.put(SONG_PATH, mAudioPath);

				spinner.add(song);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
	}

	public void populateSpinnerMusic() {
		createPlayList();
		final Spinner s = (Spinner) context.findViewById(R.id.spinnerMusic);
		s.setAdapter(createArrayAdapter());
	}

}
