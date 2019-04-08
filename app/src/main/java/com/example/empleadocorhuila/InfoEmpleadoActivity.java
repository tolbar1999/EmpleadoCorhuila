package com.example.empleadocorhuila;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelo.Empleado;
import com.example.util.General;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InfoEmpleadoActivity extends AppCompatActivity {

    int idEmpleado = 0;
    Empleado empleadoActual = null;

    TextView txtNombreInfo;
    TextView txtApellidoInfo;
    TextView txtGeneroInfo;
    TextView txtFechaNacimientoInfo;
    TextView txtFechaIngresoInfo;
    TextView numberSalarioInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Informacion empleado");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_empleado);

        txtNombreInfo = findViewById(R.id.txtNombreInfo);
        txtApellidoInfo = findViewById(R.id.txtApellidoInfo);
        txtGeneroInfo = findViewById(R.id.txtGeneroInfo);
        txtFechaNacimientoInfo = findViewById(R.id.txtFechaNacimientoInfo);
        txtFechaIngresoInfo = findViewById(R.id.txtFechaIngresoInfo);
        numberSalarioInfo = findViewById(R.id.numberSalarioInfo);

        idEmpleado = Integer.parseInt(getIntent().getStringExtra("idEmpleado"));
        traerEmpleado();
    }


    private void traerEmpleado() {

        General general = new General();
        ArrayList<Empleado> listaEmpleado = general.obtenerListaEmpleado();


        for (Empleado emp : listaEmpleado) {
            if (emp.id == idEmpleado) {
                empleadoActual = emp;
            }
        }

        txtNombreInfo.setText(empleadoActual.nombre);
        txtApellidoInfo.setText(empleadoActual.apellido);
        txtGeneroInfo.setText(empleadoActual.genero);
        txtFechaNacimientoInfo.setText(General.convertirDateToString(empleadoActual.fechaNacimiento));
        txtFechaIngresoInfo.setText(General.convertirDateToString(empleadoActual.fechaIngresoEmpresa));
        numberSalarioInfo.setText("" + empleadoActual.salarioBasico);
    }


    public void modificarSalario(View view) {

        String salarioBasicoTexto = numberSalarioInfo.getText().toString();

        if (!salarioBasicoTexto.equals("")) {
            int salarioBasicoNum = Integer.parseInt(salarioBasicoTexto);

            General general = new General();
            ArrayList<Empleado> listaEmpleado = general.obtenerListaEmpleado();

            for (Empleado emp : listaEmpleado) {
                if (emp.id == idEmpleado) {
                    emp.salarioBasico = salarioBasicoNum;
                }
            }

            Gson gson = new Gson();
            String empleadoJson = gson.toJson(listaEmpleado);
            general.crearPreferences(empleadoJson);

            Toast.makeText(this, "Ha modificado el salario correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {

            Toast.makeText(this, "Digite correctamente el salario", Toast.LENGTH_SHORT).show();
        }
    }

    public void calcularEdad(View view) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateActual = new Date();

        String fechaNacimiento = General.convertirDateToString(empleadoActual.fechaNacimiento);
        String fechaActual = General.convertirDateToString(dateActual);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = formatter.parse(fechaNacimiento);
            Date date2 = formatter.parse(fechaActual);
            long diff = date2.getTime() - date1.getTime();

            int edad = (int) (diff / (1000 * 60 * 60 * 24)) / 365;
            Toast.makeText(this, "El empleado tiene " + edad + " años de edad", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calcularAntiguedad(View view) {
        Toast.makeText(this, "El empleado tiene " + calculoInternoAntiguedad() + " años de antiguedad", Toast.LENGTH_SHORT).show();
    }

    private int calculoInternoAntiguedad() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateActual = new Date();

        String fechaIngresoEmpresa = General.convertirDateToString(empleadoActual.fechaIngresoEmpresa);
        String fechaActual = General.convertirDateToString(dateActual);

        int antiguedad = 0;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = formatter.parse(fechaIngresoEmpresa);
            Date date2 = formatter.parse(fechaActual);
            long diff = date2.getTime() - date1.getTime();

            antiguedad = (int) (diff / (1000 * 60 * 60 * 24)) / 365;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return antiguedad;
    }

    public void calcularPrestaciones(View view) {

        int antiguedad = this.calculoInternoAntiguedad();
        double prestaciones = (antiguedad * empleadoActual.salarioBasico) / 12;

        Toast.makeText(this, "Las prestaciones del empleado son de " + prestaciones, Toast.LENGTH_SHORT).show();
    }


}
