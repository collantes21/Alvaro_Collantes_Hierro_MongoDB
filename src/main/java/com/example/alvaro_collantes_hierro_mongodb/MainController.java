package com.example.alvaro_collantes_hierro_mongodb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;
    @FXML
    private Button btnNueva;

    @FXML
    private Button btnLimpiar;

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

    @FXML
    private TableColumn col_cine;

    @FXML
    private TableColumn col_genero;

    @FXML
    private TableColumn col_protagonista;

    @FXML
    private TableColumn col_titulo;

    PeliculasDAO pelicula=new PeliculasDAO();


    private void actualizarTableView(){
        vaciarCampos();
        List<Pelicula> peliculas = pelicula.cargarPelis();
        ObservableList<Pelicula> data = FXCollections.observableArrayList(peliculas);

        // Agregar datos a la TableView
        tablaPelis.setItems(data);

        this.col_titulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.col_genero.setCellValueFactory(new PropertyValueFactory("genero"));
        this.col_cine.setCellValueFactory(new PropertyValueFactory("cine"));
        this.col_protagonista.setCellValueFactory(new PropertyValueFactory("protagonista"));

    }

    @FXML
    void borrarPeli(ActionEvent event) {

        String titulo=txtTitulo.getText();
        String genero=txtGenero.getText();
        String cine=txtCine.getText();
        String protagonista=txtProtagonista.getText();

        Pelicula borrarPelicula = new Pelicula();
        borrarPelicula.setTitulo(titulo);
        borrarPelicula.setGenero(genero);
        borrarPelicula.setCine(cine);
        borrarPelicula.setProtagonista(protagonista);

        boolean confirmado = AlertUtils.mostrarConfirmacion("Confirmación", "¿Estás seguro de borrar esta película?");

        if (confirmado) {
            // Borrar la película solo si el usuario confirma
            pelicula.borrarPelicula(borrarPelicula);
            vaciarCampos();
        } else {
            // Opcional: Mostrar una alerta de cancelación si el usuario cancela
            AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelación", "Borrado cancelado por el usuario");
        }

        vaciarCampos();
        actualizarTableView();
        configBotones();

    }

    @FXML
    void guardarPeli(ActionEvent event) {

        String titulo=txtTitulo.getText();
        String genero=txtGenero.getText();
        String cine=txtCine.getText();
        String protagonista=txtProtagonista.getText();

        if (titulo.equals("")){

            //Otra forma de arrojar mensajes.
            JOptionPane.showMessageDialog(null, "El campo titulo debe estar relleno.");
            vaciarCampos();
            configBotones();

        } else {

            Pelicula nuevaPelicula = new Pelicula();

            nuevaPelicula.setTitulo(titulo);
            nuevaPelicula.setGenero(genero);
            nuevaPelicula.setCine(cine);
            nuevaPelicula.setProtagonista(protagonista);

            pelicula.insertarPelicula(nuevaPelicula);

            vaciarCampos();
            actualizarTableView();
            configBotones();
        }

    }

    @FXML
    void modificarPeli(ActionEvent event) {

        String titulo=txtTitulo.getText();
        String genero=txtGenero.getText();
        String cine=txtCine.getText();
        String protagonista=txtProtagonista.getText();

        Pelicula modificarPelicula = new Pelicula();
        modificarPelicula.setTitulo(titulo);
        modificarPelicula.setGenero(genero);
        modificarPelicula.setCine(cine);
        modificarPelicula.setProtagonista(protagonista);

        pelicula.modificarPelicula(modificarPelicula);

        vaciarCampos();
        actualizarTableView();
        configBotones();

    }
    @FXML
    void nuevaPeli(ActionEvent event) {

        vaciarCampos();
        btnGuardar.setDisable(false);
        btnBorrar.setDisable(true);
        btnModificar.setDisable(true);
        tablaPelis.setDisable(true);
        txtTitulo.setDisable(false);
        txtGenero.setDisable(false);
        txtCine.setDisable(false);
        txtProtagonista.setDisable(false);
        actualizarTableView();
    }


    public void vaciarCampos() {
        txtTitulo.setText("");
        txtGenero.setText("");
        txtCine.setText("");
        txtProtagonista.setText("");

    }

    @FXML
    void limpiarCampos(ActionEvent event) {

        vaciarCampos();
        actualizarTableView();
        configBotones();

    }

    public void cargarDatos(){

        Pelicula cargarPelicula= tablaPelis.getSelectionModel().getSelectedItem();

        txtTitulo.setText(cargarPelicula.getTitulo());
        txtGenero.setText(cargarPelicula.getGenero());
        txtCine.setText(cargarPelicula.getCine());
        txtProtagonista.setText(cargarPelicula.getProtagonista());

        btnGuardar.setDisable(true);
        btnBorrar.setDisable(false);
        btnModificar.setDisable(false);
        txtTitulo.setDisable(false);
        txtGenero.setDisable(false);
        txtCine.setDisable(false);
        txtProtagonista.setDisable(false);

    }

    private void configBotones(){

        btnGuardar.setDisable(true);
        btnBorrar.setDisable(true);
        btnModificar.setDisable(true);
        tablaPelis.setDisable(false);
        btnNueva.setDisable(false);
        txtTitulo.setDisable(true);
        txtGenero.setDisable(true);
        txtCine.setDisable(true);
        txtProtagonista.setDisable(true);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        List<Pelicula> peliculas = pelicula.cargarPelis();
        ObservableList<Pelicula> data = FXCollections.observableArrayList(peliculas);

        // Agregar datos a la TableView
        tablaPelis.setItems(data);

        this.col_titulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.col_genero.setCellValueFactory(new PropertyValueFactory("genero"));
        this.col_cine.setCellValueFactory(new PropertyValueFactory("cine"));
        this.col_protagonista.setCellValueFactory(new PropertyValueFactory("protagonista"));

        configBotones();


    }

}
