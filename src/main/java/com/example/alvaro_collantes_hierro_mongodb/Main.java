package com.example.alvaro_collantes_hierro_mongodb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 570, 550);
        stage.setTitle("Cartelera");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        PeliculasDAO peli=new PeliculasDAO();
        peli.conectarse();
        List<Pelicula>peliculas=peli.cargarPelis();

        for (int i=0; i< peliculas.size(); i ++){
            System.out.println(peliculas.get(i).getTitulo());
        }

    }
}