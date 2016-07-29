package in.dineshsunny.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import in.dineshsunny.coolfirebase.MainActivity;
import in.dineshsunny.coolfirebase.R;

/**
 * Created by Macbook on 28/07/16.
 */

public class MyFirebaseMessagingService
        extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 1, intent, PendingIntent.FLAG_ONE_SHOT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Uri u = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getPackageName()+ "/raw/mb_die");

        builder
          .setSound(u)
          .setSmallIcon(R.drawable.speech)
          .setContentTitle(remoteMessage.getNotification().getTitle())
        .setContentText(remoteMessage.getNotification().getBody())
        .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        NotificationManager manager =
                (NotificationManager)
                        getSystemService(
                    Context.NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());

    }
}
