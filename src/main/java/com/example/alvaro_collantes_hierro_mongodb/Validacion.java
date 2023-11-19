package com.example.alvaro_collantes_hierro_mongodb;

public class Validacion {

    //Vamos a validar que en la clase genero no se inserta ningun numero.
    public static boolean noContieneNumeros(String cadena) {

        return cadena.matches("^[^0-9]*$");
    }
}
