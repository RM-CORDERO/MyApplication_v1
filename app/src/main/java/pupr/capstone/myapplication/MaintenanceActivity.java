package pupr.capstone.myapplication;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.provider.Settings;

public class MaintenanceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MantenimientoAdapter adapter;
    List<Mantenimiento> listaMantenimientos = new ArrayList<>();
    private Button btnTest;
    private NotificationHelper notificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        recyclerView = findViewById(R.id.recyclerViewMaintenance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MantenimientoAdapter(listaMantenimientos);
        recyclerView.setAdapter(adapter);

        btnTest = findViewById(R.id.btnTest);
        notificationHelper = new NotificationHelper(this);

        // Create the notification channel
        notificationHelper.createNotificationChannel();
        requestNotificationPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }


        btnTest.setOnClickListener(v -> {

            // 1️⃣ Programar notificación
            scheduleTestNotification();

            // 2️⃣ Enviar correo (en segundo plano)
            new Thread(() -> {
                try {
                    JavaMailUtil.sendMail("jrdcacho8@gmail.com,esdrascordero@gmail.com,alejandro.texidor02@gmail.com");
                    runOnUiThread(() ->
                            Toast.makeText(this, "Correo enviado correctamente", Toast.LENGTH_SHORT).show());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

//        adapter.setOnItemClickListener(new MantenimientoAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Auto auto) {
//                Intent intent = new Intent(MaintenanceActivity.this, MaintenanceActivity.class);
//                intent.putExtra("marca", auto.getMarca());
//                startActivity(intent);
//            }
//        });

        // Capturar marca pasada desde GarageActivity
        String marca = getIntent().getStringExtra("marca");

        // Cargar mantenimientos de la BD usando la marca como nombre de tabla
        cargarMantenimientosDesdeBD(marca);
    }
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101
                );
            }
        }
    }
    private void scheduleTestNotification() {
        try {
            // Simulated saved date
            //String savedDate = "2025-10-10";

            // For testing quickly:
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 10);
            long time = calendar.getTimeInMillis();

//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            Date date = format.parse(savedDate);
//            long time = date.getTime();

            String title = "Mantenimiento programado";
            String message = "Saludos Cordiales.";

            notificationHelper.scheduleNotification(time, title, message);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al programar la notificación", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarMantenimientosDesdeBD(String marca) {
        try {
            MyJDBC myJDBC = new MyJDBC();
            Connection connection = myJDBC.obtenerConexion();

            if (connection != null) {
                String query = "SELECT TIPO_DE_MANTENIMIENTO FROM " + marca;
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String tipo = rs.getString("TIPO_DE_MANTENIMIENTO");


                    listaMantenimientos.add(new Mantenimiento(tipo,null));
                }

                statement.close();
                connection.close();
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void goBack(View v){
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

