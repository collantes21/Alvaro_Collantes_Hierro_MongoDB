package com.example.alvaro_collantes_hierro_mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PeliculasDAO {

    //Creamos las conexiones privadas contra nuestra base de datos.

    private MongoClient conexion;
    private MongoDatabase db;
    private static final String database_name="peliculas";

    Pelicula pelicula=new Pelicula();

    public void conectarse(){

        conexion=ConexionBBDD.conectar();
        db=conexion.getDatabase("peliculas");
    }

    public void insertarPelicula(Pelicula pelicula) {


        if(existeTitulo(pelicula.getTitulo())){
            //Llamamos a una coleccion, sino esta creada la creara.
            MongoCollection<Document> collection = db.getCollection("peliculas");

            Document documento = new Document()
                    .append("titulo", pelicula.getTitulo())
                    .append("genero", pelicula.getGenero())
                    .append("cine", pelicula.getCine())
                    .append("protagonista", pelicula.getProtagonista());

            collection.insertOne(documento);
            cargarPelis();
        } else {
            System.out.println("Eres un pringado");
        }
        

    }

    public void borrarPelicula(Pelicula peli){

        conectarse();
        //Comprobamos si existe el genero
        if (existeTitulo(peli.getTitulo())){
            //Obtenemos la coleccion por genero.
            MongoCollection<Document> collection=db.getCollection("peliculas");
            //Creamos un filtro para encontrar la pelicula por el titulo.
            Document filtroPeli=new Document("titulo", peli.getTitulo());

            collection.deleteOne(filtroPeli);
            System.out.println("sjkfdhlskgh fjsdfjglsdñkgjsdflkj");
        } else {
            System.out.println("Esto no fuciona pringado");
        }
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

    public boolean existeTitulo (String titulo) {
        // Obtener la colección correspondiente al género
        MongoCollection<Document> collection = db.getCollection("peliculas");

        // Obtener un cursor con todos los documentos de la colección
        MongoCursor<Document> cursor = collection.find().iterator();

        // Verificar si hay al menos un documento en la colección
        boolean existeTitulo = cursor.hasNext();

        // Cerrar el cursor para liberar recursos
        cursor.close();

        return existeTitulo;
    }
}
