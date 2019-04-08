package com.example.empleadocorhuila;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelo.Empleado;
import com.example.util.General;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Context contextOfApplication;

    TableLayout tableEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contextOfApplication = getApplicationContext();
        setTitle("Listado De Empleados");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableEmpleado = findViewById(R.id.tableEmpleado);
    }

    @Override
    protected void onStop() {
        tableEmpleado.removeAllViews();
        super.onStop();
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public void abrirCrearEmpleado(View view) {
        Intent intent = new Intent(this, CrearEmpleadoActivity.class);
        startActivity(intent);
    }

    public void actualizarTabla(View view) {
        General general = new General();
        System.out.println(general.obtenerListaEmpleado());

        tableEmpleado.removeAllViews();
        ArrayList<Empleado> listaEmpleado = general.obtenerListaEmpleado();

        for (final Empleado empleado : listaEmpleado) {
            String nombre = empleado.nombre;
            String apellido = empleado.apellido;

            TableRow row = new TableRow(this);

            TextView colNombre = new TextView(this);
            colNombre.setText(nombre);
            colNombre.setGravity(Gravity.CENTER_HORIZONTAL);
            colNombre.setTextSize(15);

            TextView colApellido = new TextView(this);
            colApellido.setText(apellido);
            colApellido.setGravity(Gravity.CENTER_HORIZONTAL);
            colApellido.setTextSize(15);

            Button botonModificar = new Button(this);
            botonModificar.setText("VER");
            botonModificar.setGravity(Gravity.CENTER_HORIZONTAL);
            botonModificar.setTextSize(15);
            botonModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), InfoEmpleadoActivity.class);
                    intent.putExtra("idEmpleado", "" + empleado.id);
                    startActivity(intent);
                }
            });


            row.addView(colNombre, 200, 100);
            row.addView(colApellido, 200, 100);
            row.addView(botonModificar, 230, 100);

            tableEmpleado.addView(row);
        }
    }

    public void eliminarEmpleados(View view) {
        General general = new General();
        general.eliminarPreferences();
        tableEmpleado.removeAllViews();
    }
}
