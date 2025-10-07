package pupr.capstone.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MantenimientoAdapter adapter;
    List<Mantenimiento> listaMantenimientos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        recyclerView = findViewById(R.id.recyclerViewMaintenance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MantenimientoAdapter(listaMantenimientos);
        recyclerView.setAdapter(adapter);

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

