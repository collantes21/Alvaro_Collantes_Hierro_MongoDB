package com.example.alvaro_collantes_hierro_mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PeliculasDAO {

    //Creamos las conexiones privadas contra nuestra base de datos.

    private MongoClient conexion;
    private MongoDatabase db;
    private static final String database_name="peliculas";


    public void conectarse(){

        conexion=ConexionBBDD.conectar();
        db=conexion.getDatabase("peliculas");
    }

    public void insertarPelicula(Pelicula pelicula) {
        //Cre
        MongoCollection<Document> collection = db.getCollection("peliculas");

        Document documento = new Document()
                .append("titulo", pelicula.getTitulo())
                .append("genero", pelicula.getGenero())
                .append("cine", pelicula.getCine())
                .append("protagonista", pelicula.getProtagonista());

        collection.insertOne(documento);
    }

    public List<Pelicula> cargarPelis() {
        this.conectarse();
        List<Pelicula> peliculas = new ArrayList<>();
        Document documento = new Document();

        //Se realiza una consulta find con un documento vacio, donde se recuperaran toda la coleccion de peliculas.
        FindIterable findIterable = db.getCollection("peliculas").find(documento);

        //Se crea un iterador para recorrer los resultados de las consultas.
        Iterator<Document> iter = findIterable.iterator();
        //Iniciamos un bucle para iterar sobre los resultados de las consultas
        while (iter.hasNext()) {
            Document doc = iter.next();
            Pelicula pelicula = new Pelicula();
            pelicula.setId(doc.getObjectId("_id"));
            pelicula.setTitulo(doc.getString("titulo"));
            pelicula.setGenero(doc.getString("genero"));
            pelicula.setCine(doc.getString("cine"));
            pelicula.setProtagonista(doc.getString("protagonista"));
            peliculas.add(pelicula);
        }

        return peliculas;

    }
}
