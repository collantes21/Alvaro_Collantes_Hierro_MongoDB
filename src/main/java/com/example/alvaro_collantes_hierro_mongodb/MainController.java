package com.example.alvaro_collantes_hierro_mongodb;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<Pelicula> tablaPelis;

    @FXML
    private TextField txtCine;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtProtagonista;

    @FXML
    private TextField txtTitulo;


    PeliculasDAO pelicula=new PeliculasDAO();


    @FXML
    void actualizarPelis(ActionEvent event) {


        vaciarCampos();

        List<Pelicula> peliculas=pelicula.cargarPelis();

        tablaPelis.setItems(FXCollections.observableArrayList(peliculas));

    }

    @FXML
    void borrarPeli(ActionEvent event) {

    }

    @FXML
    void camposVacios(ActionEvent event) {

        txtTitulo.setText("");
        txtGenero.setText("");
        txtCine.setText("");
        txtProtagonista.setText("");

    }

    @FXML
    void guardarPeli(ActionEvent event) {



        String titulo=txtTitulo.getText();
        String genero=txtGenero.getText();
        String cine=txtCine.getText();
        String protagonista=txtProtagonista.getText();

            Pelicula nuevaPelicula = new Pelicula();
            nuevaPelicula.setTitulo(titulo);
            nuevaPelicula.setGenero(genero);
            nuevaPelicula.setCine(cine);
            nuevaPelicula.setProtagonista(protagonista);

            pelicula.conectarse();
            pelicula.insertarPelicula(nuevaPelicula);

    }


    @FXML
    void modificarPeli(ActionEvent event) {

        String titulo=txtTitulo.getText();
        String genero=txtGenero.getText();
        String cine=txtCine.getText();
        String protagonista=txtProtagonista.getText();


    }

    private void vaciarCampos() {
        txtTitulo.setText("");
        txtGenero.setText("");
        txtCine.setText("");
        txtProtagonista.setText("");

    }

}
