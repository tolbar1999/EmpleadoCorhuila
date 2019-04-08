package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.empleadocorhuila.MainActivity;
import com.example.modelo.Empleado;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class General {

    Context applicationContext = MainActivity.getContextOfApplication();

    public static Date convertirStringToDate(String fechaString) {
        Date fecha = null;
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fecha;
    }

    public static String convertirDateToString(Date fechaDate) {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = formatter.format(fechaDate);
        return fecha;
    }


    public void crearPreferences(String empleadoJson) {
        SharedPreferences preferences = applicationContext.getSharedPreferences("datos_empleados", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("empleados", empleadoJson);
        editor.commit();
    }

    public ArrayList<Empleado> obtenerListaEmpleado() {
        SharedPreferences preferences = applicationContext.getSharedPreferences("datos_empleados", Context.MODE_PRIVATE);
        String jsonEmpleado = preferences.getString("empleados", "");

        ArrayList<Empleado> listaEmpleado = new ArrayList<>();

        if (!jsonEmpleado.equals("")) {
            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();

            JsonArray arrayFromString = jsonParser.parse(jsonEmpleado).getAsJsonArray();

            for (int i = 0; i < arrayFromString.size(); i++) {
                Empleado empleado = gson.fromJson(arrayFromString.get(i), Empleado.class);
                listaEmpleado.add(empleado);
            }
        }

        return listaEmpleado;
    }


    public void eliminarPreferences() {
        SharedPreferences preferences = applicationContext.getSharedPreferences("datos_empleados", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

}
