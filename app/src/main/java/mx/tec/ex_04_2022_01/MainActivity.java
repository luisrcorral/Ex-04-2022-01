package mx.tec.ex_04_2022_01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;

    //Toast Object Manager (It does not require an adapter)
    private Toast myToast;

    //Dialog Adapter and Object Manager
    private AlertDialog.Builder dialogConf;
    private AlertDialog myDialog;

    //Notification Adapter and Object Manager
    private NotificationCompat.Builder myBuilder;
    private NotificationManager myNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We obtain the application context and store it in an object called context
        context = getApplicationContext();

        // 1. Creation of a toast. Constructor to the Toast class, and then calling to
        // a "makeText" method to set up the text. Do not forget the .show() callback.
        myToast = new Toast(context);
        myToast.makeText(context, "I am a toast", Toast.LENGTH_LONG).show();

        // 2. Creation of an Alert Dialog. First, we set up the adapter, feeding all the
        // properties and response buttons of the eventual Alert Dialog
        dialogConf = new AlertDialog.Builder(this);
        dialogConf.setTitle("Hi Dialog");
        dialogConf.setMessage("Are you sure?");

        // Here we set up the buttons
        dialogConf.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myToast.makeText(context, "You Clicked Yes", Toast.LENGTH_SHORT).show();
                        // Do whatever you want here
                    }
                });
        dialogConf.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myToast.makeText(context, "You Clicked No", Toast.LENGTH_SHORT).show();
                        // Do whatever you want here
                    }
                });

        dialogConf.setNeutralButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myToast.makeText(context, "You Clicked Close", Toast.LENGTH_SHORT).show();
                    }
                });

        // And lastly we create an instance of the real AlertDialog class, passing as
        // initialization parameter the Alert Dialog adapter. We show it using .show()
        myDialog = dialogConf.create();
        myDialog.show();

        // Note: this line prepares a bitmap object manager for further use
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.itesm);

        // 3. Creating a bar notification.
        // First, we set up a notification channel, per Android's requirement
        NotificationChannel myChannel = new NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);

        // Second, we create the Notification Adapter and assign the properties
        myBuilder = new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round) // notification icon
                .setContentTitle("Title of this notification")
                .setContentText("Text that describes the notification")
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bm))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Optional: in case we want to assign a clickable action, we create
        // a pending intent
        Intent intent = new Intent(getApplicationContext(), ChildActivity.class);
        PendingIntent myPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        myBuilder.setContentIntent(myPendingIntent);

        // Finally, we create the actual Notification method and assign it the channel
        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.createNotificationChannel(myChannel);

        // And we launch it assigning it the properties of the adapter
        myNotificationManager.notify(0, myBuilder.build());

    }
}