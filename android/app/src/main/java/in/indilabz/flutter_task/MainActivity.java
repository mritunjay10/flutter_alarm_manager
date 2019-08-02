package in.indilabz.flutter_task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

public class MainActivity extends FlutterActivity {

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private static  FlutterView flutterView;
    public static final String CHANNEL = "indilabz.in/service";
    private static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flutterView=getFlutterView();
        GeneratedPluginRegistrant.registerWith(this);

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                (call, res) -> {

                    callFlutter();
                    Intent intent = new Intent(MainActivity.this, FlutterReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(this, 1989, intent, 0);
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis(), 1000, pendingIntent);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 30000, pendingIntent);
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmManager.cancel(pendingIntent);
    }

    public static FlutterView getFlutter(){

        return flutterView;
    }

    static void callFlutter(){
        i++;
        Log.d("TAG_HERE","HELLO_THERE - "+i);
        MethodChannel methodChannel=new MethodChannel(flutterView, CHANNEL);
        methodChannel.invokeMethod("HELLO_THERE - "+i,"");

    }}
