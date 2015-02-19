package jp.co.ank.sample;

import java.util.Calendar;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class SampleSvc extends Service {
	private static final String PREVBTN_CLICK =
			"jp.co.ank.sample.SampleSvc.PREVBTN_CLICK";
	private static final String NEXTBTN_CLICK =
			"jp.co.ank.sample.SampleSvc.NEXTBTN_CLICK";
	private Calendar cal = Calendar.getInstance();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		RemoteViews rview =
				new RemoteViews(getPackageName(), R.layout.samplesvc);
		
		// ペンディングインテント（前へ）
		Intent previt = new Intent();
		previt.setAction(PREVBTN_CLICK);
		PendingIntent prevpit = PendingIntent.getService(this, 0, previt, 0);
		rview.setOnClickPendingIntent(R.id.btnPrev, prevpit);
		
		// ペンディングインテント（次へ）
		Intent nextit = new Intent();
		nextit.setAction(NEXTBTN_CLICK);
		PendingIntent nextpit = PendingIntent.getService(this, 0, nextit, 0);
		rview.setOnClickPendingIntent(R.id.btnNext, nextpit);
		
		// ボタンクリック次の処理
		if(PREVBTN_CLICK.equals(intent.getAction())){
			cal.add(Calendar.DATE, -1);
		}else if(NEXTBTN_CLICK.equals(intent.getAction())){
			cal.add(Calendar.DATE, +1);
		}
		Log.v("SampleSvc", cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DATE));
		rview.setTextViewText(R.id.tv1, 
				(cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE));
		
		// 画面更新
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName widget = new ComponentName(
				"jp.co.ank.sample", "jp.co.ank.sample.Sample");
		manager.updateAppWidget(widget, rview);
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
