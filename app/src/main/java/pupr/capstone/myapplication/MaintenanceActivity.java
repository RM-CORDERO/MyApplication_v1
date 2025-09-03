package pupr.capstone.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MaintenanceActivity extends AppCompatActivity {

    ImageView imagen;
    TextView nombre, tablilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_auto);

        imagen = findViewById(R.id.imagenAuto);
        nombre = findViewById(R.id.nombreAuto);
        tablilla = findViewById(R.id.tablillaAuto);

        // Obtener datos del intent
        Intent intent = getIntent();
        nombre.setText(intent.getStringExtra("nombre"));
        tablilla.setText(intent.getStringExtra("tablilla"));
        imagen.setImageResource(intent.getIntExtra("imagen", R.drawable.ic_launcher_foreground));
    }
    }

