package fr.android.volumesetter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class WidgetIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("volumesetter.action.LOAD_CONFIG")){
			updateWidgetPictureAndButtonListener(context);
		}
	}

	private void updateWidgetPictureAndButtonListener(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
		MainActivity.widgetLoadBoolean(context);
		
		remoteViews.setOnClickPendingIntent(R.id.widget_image, WidgetProvider.buildButtonPendingIntent(context));
		WidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
	}



}
