package com.example.empleadocorhuila;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelo.Empleado;
import com.example.util.General;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;


public class CrearEmpleadoActivity extends AppCompatActivity {


    TextView txtNombreCrear;
    TextView txtApellidoCrear;
    Spinner spinnerGeneroCrear;
    TextView txtFechaNacimientoCrear;
    TextView txtFotoCrear;
    TextView txtFechaIngresoEmpresaCrear;
    TextView numberSalarioBasicoCrear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Crear Empleado");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_empleado);

        txtNombreCrear = findViewById(R.id.txtNombreCrear);
        txtApellidoCrear = findViewById(R.id.txtApellidoCrear);
        spinnerGeneroCrear = findViewById(R.id.spinnerGeneroCrear);
        txtFechaNacimientoCrear = findViewById(R.id.txtFechaNacimientoCrear);
        txtFotoCrear = findViewById(R.id.txtFotoCrear);
        txtFechaIngresoEmpresaCrear = findViewById(R.id.txtFechaIngresoEmpresaCrear);
        numberSalarioBasicoCrear = findViewById(R.id.numberSalarioBasicoCrear);

        this.opcionesGenero();
    }

    private void opcionesGenero() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("-- Género --");
        options.add("Femenino");
        options.add("Masculino");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, options);
        spinnerGeneroCrear.setAdapter(adapter);
        spinnerGeneroCrear.setSelection(0);
    }


    public void crearEmpleado(View view) {

        String nombre = txtNombreCrear.getText().toString();
        String apellido = txtApellidoCrear.getText().toString();
        String genero = spinnerGeneroCrear.getSelectedItem().toString();
        String fechaNacimiento = txtFechaNacimientoCrear.getText().toString();
        String foto = txtFotoCrear.getText().toString();
        String fechaIngresoEmpresa = txtFechaIngresoEmpresaCrear.getText().toString();

        String salarioBasicoTexto = numberSalarioBasicoCrear.getText().toString();


        if (!nombre.equals("") && !apellido.equals("") && !genero.equals("-- Género --") &&
                !fechaNacimiento.equals("") && !foto.equals("") && !fechaIngresoEmpresa.equals("") &&
                !salarioBasicoTexto.equals("")) {

            int id = new Random().nextInt(10000000);
            int salarioBasicoNum = Integer.parseInt(salarioBasicoTexto);

            Empleado empleado = new Empleado();
            empleado.id = id;
            empleado.nombre = nombre;
            empleado.apellido = apellido;
            empleado.genero = genero;
            empleado.fechaNacimiento = General.convertirStringToDate(fechaNacimiento);
            empleado.foto = foto;
            empleado.fechaIngresoEmpresa = General.convertirStringToDate(fechaIngresoEmpresa);
            empleado.salarioBasico = salarioBasicoNum;

            General general = new General();
            ArrayList<Empleado> listaEmpleado = general.obtenerListaEmpleado();
            listaEmpleado.add(empleado);

            Gson gson = new Gson();
            String empleadoJson = gson.toJson(listaEmpleado);
            general.crearPreferences(empleadoJson);

            Toast.makeText(this, "Se ha creado el empleado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Tiene que llenar completamente el formulario", Toast.LENGTH_SHORT).show();
        }


    }

}