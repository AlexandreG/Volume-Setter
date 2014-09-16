package fr.android.simplevolumesetter;

import android.app.Application;
import android.content.Context;

public class GlobalApp extends Application {

	private static GlobalApp instance;
	private Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		instance.initializeInstance();
	}

	private void initializeInstance() {
		context = getApplicationContext();
	}

	public static synchronized GlobalApp getInstance() {
		return instance;
	}

	public Context getContext() {
		return context;
	}

}
