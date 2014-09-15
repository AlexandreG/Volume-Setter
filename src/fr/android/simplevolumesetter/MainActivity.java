package fr.android.simplevolumesetter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import fr.android.volumesetter.R;

public class MainActivity extends Activity implements android.widget.SeekBar.OnSeekBarChangeListener {
	final protected String TAG = "Log";

	private AudioManager audioManager;

	/*
	 * protected RadioButton silent; protected RadioButton vibrate; protected
	 * RadioButton normal;
	 */
	private RadioGroup radioGroup;

	private SeekBar ring;
	private SeekBar notifs;
	private SeekBar media;
	private SeekBar alarm;
	private SeekBar system;
	private SeekBar call;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		initViews();
		initSeekBars();

		radioGroup.check(getPhoneState());
		if (getPhoneState() != R.id.radiobutton_normal) {
			ring.setEnabled(false);
			notifs.setEnabled(false);
			system.setEnabled(false);
		}

		radioGroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// Log.d(TAG, checkedId+"");
				if (checkedId == R.id.radiobutton_silent) {
					audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					ring.setEnabled(false);
					notifs.setEnabled(false);
					system.setEnabled(false);
				}
				if (checkedId == R.id.radiobutton_vibrate) {
					audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					ring.setEnabled(false);
					notifs.setEnabled(false);
					system.setEnabled(false);
				}
				if (checkedId == R.id.radiobutton_normal) {
					audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					ring.setEnabled(true);
					notifs.setEnabled(true);
					system.setEnabled(true);
				}
			}
		});

		initSeekBarListener();
	}

	private void initViews() {
		ring = (SeekBar) findViewById(R.id.sbring);
		notifs = (SeekBar) findViewById(R.id.sbnotif);
		media = (SeekBar) findViewById(R.id.sbmedia);
		alarm = (SeekBar) findViewById(R.id.sbalarm);
		system = (SeekBar) findViewById(R.id.sbsystem);
		call = (SeekBar) findViewById(R.id.sbcall);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
	}

	private void initSeekBars() {
		ring.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		ring.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));

		notifs.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
		notifs.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));

		media.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		media.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

		alarm.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
		alarm.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));

		system.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
		system.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));

		call.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
		call.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
	}

	private void initSeekBarListener() {
		ring.setOnSeekBarChangeListener(this);
		notifs.setOnSeekBarChangeListener(this);
		media.setOnSeekBarChangeListener(this);
		alarm.setOnSeekBarChangeListener(this);
		system.setOnSeekBarChangeListener(this);
		call.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int seekBarId = seekBar.getId();

		if (seekBarId == this.ring.getId()) {
			audioManager.setStreamVolume(AudioManager.STREAM_RING, progress, 0);
		} else if (seekBarId == this.notifs.getId()) {
			audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress, 0);
		} else if (seekBarId == this.media.getId()) {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
		} else if (seekBarId == this.alarm.getId()) {
			audioManager.setStreamVolume(AudioManager.STREAM_ALARM, progress, 0);
		} else if (seekBarId == this.system.getId()) {
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, 0);
		} else if (seekBarId == this.call.getId()) {
			audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress, 0);
		}
		radioGroup.check(getPhoneState());

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	private int getPhoneState() {
		int phoneState = audioManager.getRingerMode();
		// Log.d(TAG, phoneState+"");

		if (phoneState == AudioManager.RINGER_MODE_SILENT) {
			return R.id.radiobutton_silent;
		} else if (phoneState == AudioManager.RINGER_MODE_VIBRATE) {
			return R.id.radiobutton_vibrate;
		} else if (phoneState == AudioManager.RINGER_MODE_NORMAL) {
			return R.id.radiobutton_normal;
		} else {
			return -1;
		}
	}
}
