package com.example.alvaro_collantes_hierro_mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Alert;
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


    public void conectarse(){

        conexion=ConexionBBDD.conectar();
        db=conexion.getDatabase("peliculas");
    }

    public void desconectarse(MongoClient conexion){
        conexion.close();
    }


    //Working
    public void insertarPelicula(Pelicula pelicula) {

        boolean busca=existeTitulo(pelicula.getTitulo());

        if(busca==true){

            AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Titulo", "El titulo que esta intentando añadir ya existe");

        } else {

            conectarse();

            //Llamamos a una coleccion, si no esta creada la creara.
            MongoCollection<Document> collection = db.getCollection("peliculas");

            if (Validacion.noContieneNumeros(pelicula.getGenero())){

                Document documento = new Document()
                        .append("titulo", pelicula.getTitulo())
                        .append("genero", pelicula.getGenero())
                        .append("cine", pelicula.getCine())
                        .append("protagonista", pelicula.getProtagonista());


                collection.insertOne(documento);
                AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Pelicula", "La pelicula --> " + pelicula.getTitulo()+" ha sido añadida.");
                desconectarse(conexion);
                cargarPelis();

            } else {

                //Mostrar una alerta por si el campo genero tiene algun numero.
                AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Genero", "El campo genero no puede contener ninguno numero o caracter especial.");
            }

        }

    }

    //Working
    public void borrarPelicula(Pelicula peli){

        conectarse();

            //Obtenemos la coleccion por genero.
            MongoCollection<Document> collection = db.getCollection("peliculas");

            //Creamos un filtro para encontrar la pelicula por el titulo.
            Document filtroPeli = new Document("titulo", peli.getTitulo());

            collection.deleteOne(filtroPeli);
            AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Borrar", "La pelicula " + peli.getTitulo() + " ha sido borrada con exito.");

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

    public void modificarPelicula(Pelicula pelicula) {

        boolean busca=existeTitulo(pelicula.getTitulo());

        if(busca==false){

            AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Titulo", "El titulo que esta intentando modificar no existe");

        } else {

            conectarse();

            //Llamamos a una coleccion, si no esta creada la creara.
            MongoCollection<Document> collection = db.getCollection("peliculas");
            // Creamos un filtro para encontrar la película por el título
            Document filtroTitulo = new Document("titulo", pelicula.getTitulo());

            if (Validacion.noContieneNumeros(pelicula.getGenero())){

                Document nuevosDatos = new Document()
                        .append("titulo", pelicula.getTitulo())
                        .append("genero", pelicula.getGenero())
                        .append("cine", pelicula.getCine())
                        .append("protagonista", pelicula.getProtagonista());


                collection.updateOne(filtroTitulo, new Document("$set", nuevosDatos));
                AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Pelicula", "La pelicula --> " + pelicula.getTitulo()+" ha sido modificada.");
                desconectarse(conexion);
                cargarPelis();

            } else {

                //Mostrar una alerta por si el campo genero tiene algun numero.
                AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Genero", "El campo genero no puede contener ninguno numero o caracter especial.");
            }

        }

    }




    public boolean existeTitulo (String titulo) {
        conectarse();

        // Obtener la colección correspondiente al género
        MongoCollection<Document> collection = db.getCollection("peliculas");

        // Crear un filtro para encontrar documentos con el título específico
        Document filtroTitulo = new Document("titulo", titulo);

        // Obtener un cursor con los documentos que coinciden con el filtro
        MongoCursor<Document> cursor = collection.find(filtroTitulo).iterator();

        // Verificar si hay al menos un documento que coincide con el título
        boolean existeTitulo = cursor.hasNext();

        // Cerrar el cursor para liberar recursos
        cursor.close();

        desconectarse(conexion);
        return existeTitulo;
    }
}
