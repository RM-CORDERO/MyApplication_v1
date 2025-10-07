package pupr.capstone.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Si el mensaje tiene notificación
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(String title, String message) {
        String channelId = "carMaintenanceChannel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Car Maintenance Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.audi_r8) // icono de tu app
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        // Aquí es donde se agrega sonido, vibración y luz LED
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        NotificationManagerCompat.from(this).notify(1, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("FCM", "Nuevo token: " + token);
        // 👉 Aquí puedes guardar el token en tu DB MySQL para enviar notificaciones dirigidas
    }
}
