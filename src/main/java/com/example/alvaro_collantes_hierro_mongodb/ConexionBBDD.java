package com.example.alvaro_collantes_hierro_mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ConexionBBDD {

    public static MongoClient conectar()
    {

        try {

            final MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://root:password@localhost:27017/?authSource=admin"));

            System.out.println("Conectada correctamente a la BD");

            return conexion;
        }
        catch (Exception e) {
            System.out.println("Conexion Fallida");
            System.out.println(e);
            return null;
        }
    }
    public static void desconectar(MongoClient conexion)
    {

        conexion.close();
    }

}
