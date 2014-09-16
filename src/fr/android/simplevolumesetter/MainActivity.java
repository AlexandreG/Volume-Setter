package fr.android.simplevolumesetter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import fr.android.volumesetter.R;

public class MainActivity extends Activity implements android.widget.SeekBar.OnSeekBarChangeListener {
	final protected String TAG = "Log";

	private static final String IS_CONFIG_SAVED = "is_config_saved";

	private static final String PHONE_STATE = "phone_state";
	private static final String STREAM_RING = "stream_ring";
	private static final String STREAM_NOTIFICATION = "stream_notification";
	private static final String STREAM_MUSIC = "stream_music";
	private static final String STREAM_ALARM = "stream_alarm";
	private static final String STREAM_SYSTEM = "stream_system";
	private static final String STREAM_VOICE_CALL = "stream_voice_call";

	private AudioManager audioManager;
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

		initViewsId();

		initRadioGroup();

		initSeekBarsLayout();
		initSeekBarListener();

	}

	/**
	 * Initialize the layout and the listeners of the radiogroup
	 */
	private void initRadioGroup() {
		// initialize the radiogroup
		radioGroup.check(getCorrectRadioButtonId());

		// initialize the listeners
		radioGroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				checkRadioButton(checkedId);
			}
		});
	}

	/**
	 * Check the radioButton and update the mode of the phone
	 * 
	 * @param checkedId
	 */
	private void checkRadioButton(int checkedId) {
		radioGroup.check(checkedId);
		Log.v(TAG, "Check id : " + checkedId);
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

	/**
	 * Initialize the view objects
	 */
	private void initViewsId() {
		ring = (SeekBar) findViewById(R.id.sbring);
		notifs = (SeekBar) findViewById(R.id.sbnotif);
		media = (SeekBar) findViewById(R.id.sbmedia);
		alarm = (SeekBar) findViewById(R.id.sbalarm);
		system = (SeekBar) findViewById(R.id.sbsystem);
		call = (SeekBar) findViewById(R.id.sbcall);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
	}

	/**
	 * Initialize the seekbars
	 */
	private void initSeekBarsLayout() {

		int ringerMode = audioManager.getRingerMode();
		// set normal ringermode just to get stream_ring, otherwise it is 0
		audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

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

		audioManager.setRingerMode(ringerMode);

		disableUselessSeekbars();
	}

	private void disableUselessSeekbars() {
		// if silent mode we disable useless seekbars
		if (getCorrectRadioButtonId() != R.id.radiobutton_normal) {
			ring.setEnabled(false);
			notifs.setEnabled(false);
			system.setEnabled(false);
		}
	}

	/**
	 * Initialize the seekbars listeners
	 */
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

		// on seek bar changment, we update the volume of the phone
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
		radioGroup.check(getCorrectRadioButtonId());

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * Return the id of the radiobutton associed to the phone of the state
	 * 
	 */
	private int getCorrectRadioButtonId() {
		int phoneState = audioManager.getRingerMode();
		Log.v(TAG, "PhoneState : " + phoneState);

		return getRadioButtonId(phoneState);
	}

	/**
	 * Return the radio button id associated to the given mode
	 * 
	 * @param the
	 *            given ringMode
	 */
	private int getRadioButtonId(int ringMode) {
		if (ringMode == AudioManager.RINGER_MODE_SILENT) {
			return R.id.radiobutton_silent;
		} else if (ringMode == AudioManager.RINGER_MODE_VIBRATE) {
			return R.id.radiobutton_vibrate;
		} else if (ringMode == AudioManager.RINGER_MODE_NORMAL) {
			return R.id.radiobutton_normal;
		} else {
			return -1;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if no config, we disable the load button
		if (SharedPref.loadBoolean(IS_CONFIG_SAVED)) {
			menu.getItem(1).setEnabled(true);
		} else {
			menu.getItem(1).setEnabled(false);
		}

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		boolean handled = false;

		switch (item.getItemId()) {
		case R.id.action_load:
			loadConfig();
			handled = true;
			break;

		case R.id.action_save:
			saveConfig();
			handled = true;
			break;
		}
		return handled;
	}

	/**
	 * Save the current configuration in the shared prefs
	 */
	private void saveConfig() {

		int ringerMode = audioManager.getRingerMode();
		SharedPref.saveInt(PHONE_STATE, ringerMode);

		// set normal ringermode just to get stream_ring, otherwise it is 0
		audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

		SharedPref.saveInt(STREAM_RING, audioManager.getStreamVolume(AudioManager.STREAM_RING));
		SharedPref.saveInt(STREAM_NOTIFICATION, audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
		SharedPref.saveInt(STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		SharedPref.saveInt(STREAM_ALARM, audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
		SharedPref.saveInt(STREAM_SYSTEM, audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
		SharedPref.saveInt(STREAM_VOICE_CALL, audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));

		audioManager.setRingerMode(ringerMode);

		Toast.makeText(getApplicationContext(), "Config saved", Toast.LENGTH_SHORT).show();

		SharedPref.saveBoolean(IS_CONFIG_SAVED, true);
	}

	/**
	 * Load the configurations from the shared prefs
	 */
	private void loadConfig() {
		if (SharedPref.loadBoolean(IS_CONFIG_SAVED) == false) {
			Log.e(TAG, "Unable to load the config");
			Toast.makeText(getApplicationContext(), "Unable to load the config", Toast.LENGTH_SHORT).show();
			return;
		}

		// load the values
		int phoneState = SharedPref.loadInt(PHONE_STATE);

		int ringValue = SharedPref.loadInt(STREAM_RING);
		int notificationValue = SharedPref.loadInt(STREAM_NOTIFICATION);
		int musicValue = SharedPref.loadInt(STREAM_MUSIC);
		int alarmValue = SharedPref.loadInt(STREAM_ALARM);
		int systemValue = SharedPref.loadInt(STREAM_SYSTEM);
		int voiceCallValue = SharedPref.loadInt(STREAM_VOICE_CALL);

		// load the values in the system
		checkRadioButton(getRadioButtonId(phoneState));
		audioManager.setStreamVolume(AudioManager.STREAM_RING, ringValue, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationValue, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicValue, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarmValue, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, systemValue, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, voiceCallValue, 0);

		// load the values in the layout
		enableAllSeekBars();
		ring.setProgress(ringValue);
		notifs.setProgress(notificationValue);
		media.setProgress(musicValue);
		alarm.setProgress(alarmValue);
		system.setProgress(systemValue);
		call.setProgress(voiceCallValue);
		disableUselessSeekbars();

		Toast.makeText(getApplicationContext(), "Config loaded", Toast.LENGTH_SHORT).show();

	}

	/**
	 * Enable all the seekbars in the layout
	 */
	private void enableAllSeekBars() {
		ring.setEnabled(true);
		notifs.setEnabled(true);
		media.setEnabled(true);
		alarm.setEnabled(true);
		system.setEnabled(true);
		call.setEnabled(true);
	}

}
