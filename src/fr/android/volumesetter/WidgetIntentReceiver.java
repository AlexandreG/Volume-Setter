package fr.android.volumesetter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Jacek Milewski
 * looksok.wordpress.com
 */

public class WidgetIntentReceiver extends BroadcastReceiver {

	private static int clickCount = 0;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("volumesetter.action.LOAD_CONFIG")){
			updateWidgetPictureAndButtonListener(context);
		}
	}

	private void updateWidgetPictureAndButtonListener(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
//		remoteViews.setImageViewResource(R.id.widget_image, getImageToSet());

		MainActivity.widgetLoadBoolean(context);
		
		//REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.widget_image, WidgetProvider.buildButtonPendingIntent(context));
		
		WidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
	}



}
