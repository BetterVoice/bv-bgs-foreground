package com.bettervoice.phonegap.plugin.foregroundService;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.app.PendingIntent;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.annotation.TargetApi;

import android.R;

import android.util.Log;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class ForegroundService extends BackgroundService {
	
	private final static String TAG = ForegroundService.class.getSimpleName();
	
	private int notif_id=100;
	private String notificationTitle = "App Service";
	private String notificationText = "Running";

	@Override
	protected JSONObject doWork() {
		JSONObject result = new JSONObject();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			String now = df.format(new Date(System.currentTimeMillis())); 

			String msg = "Hello " + this.notificationTitle + " - its currently " + now;
			result.put("Message", msg);

			Log.d(TAG, msg);
		} catch (JSONException e) {
		}
		
		return result;	
	}

	@Override
	protected JSONObject getConfig() {
		JSONObject result = new JSONObject();
		
		try {
			result.put("notificationTitle", this.notificationTitle);
			result.put("notificationText", this.notificationText);
		} catch (JSONException e) {
		}
		
		return result;
	}

	@Override
	protected void setConfig(JSONObject config) {
		try {
			if (config.has("notificationTitle")) {
				this.notificationTitle = config.getString("notificationTitle");
				Log.d("setConfig", "Title: " + this.notificationTitle);
			}
			if (config.has("notificationText")) {
				this.notificationText = config.getString("notificationText");
				Log.d("setConfig", "Text: " + this.notificationText);
			}
			updateNotification(this.notificationTitle, this.notificationText);
				
		} catch (JSONException e) {
		}
		
	}

	private Notification getActivityNotification(String title, String text){
		//Build a Notification required for running service in foreground.
        Intent main = getApplicationContext().getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName());
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1000, main,  PendingIntent.FLAG_UPDATE_CURRENT);

        int icon = R.drawable.star_big_on;
        int normalIcon = getResources().getIdentifier("icon", "drawable", getPackageName());
        int notificationIcon = getResources().getIdentifier("notificationicon", "drawable", getPackageName());         
        if(notificationIcon != 0) {
        	Log.d("ONSTARTCOMMAND", "Found Custom Notification Icon!");
        	icon = notificationIcon;
        }
        else if(normalIcon != 0) {
        	Log.d("ONSTARTCOMMAND", "Found normal Notification Icon!");
        	icon = normalIcon;
        }

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(icon);
        builder.setContentIntent(pendingIntent);        
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = buildForegroundNotification(builder);
        } else {
            notification = buildForegroundNotificationCompat(builder);
        }
        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_FOREGROUND_SERVICE | Notification.FLAG_NO_CLEAR;
        return notification;
	}

	private void updateNotification(String title, String text) {
        Notification notification = getActivityNotification(title, text);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        mNotificationManager.notify(notif_id, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		notif_id = startId;
		super.onStartCommand(intent, flags, startId);
		//handleOnStart(intent);
		Log.d("ONSTARTCOMMAND", "intent" + intent);

        startForeground(notif_id, getActivityNotification(this.notificationTitle, this.notificationText));
		return START_REDELIVER_INTENT;  
	}

	@TargetApi(16)
    private Notification buildForegroundNotification(Notification.Builder builder) {
        return builder.build();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(15)
    private Notification buildForegroundNotificationCompat(Notification.Builder builder) {
        return builder.getNotification();
    }

	@Override
	protected JSONObject initialiseLatestResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onTimerEnabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTimerDisabled() {
		// TODO Auto-generated method stub
		
	}


}
