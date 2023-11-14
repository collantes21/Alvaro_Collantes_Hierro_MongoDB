package com.example.alvaro_collantes_hierro_mongodb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    @FXML
    private TableColumn col_cine;

    @FXML
    private TableColumn col_genero;

    @FXML
    private TableColumn col_protagonista;

    @FXML
    private TableColumn col_titulo;


    PeliculasDAO pelicula=new PeliculasDAO();


    @FXML
    void actualizarPelis(ActionEvent event) {


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
            // Opcional: Mostrar una alerta de éxito después de borrar
            AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Película borrada con éxito");
            vaciarCampos();
        } else {
            // Opcional: Mostrar una alerta de cancelación si el usuario cancela
            AlertUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelación", "Borrado cancelado por el usuario");
        }
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
    public void cargarDatos(){

        Pelicula cargarPelicula= tablaPelis.getSelectionModel().getSelectedItem();

        txtTitulo.setText(cargarPelicula.getTitulo());
        txtGenero.setText(cargarPelicula.getGenero());
        txtCine.setText(cargarPelicula.getCine());
        txtProtagonista.setText(cargarPelicula.getProtagonista());

    }

    private void fijarColumnasTabla() {
        Field[] fields = Pelicula.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id"))
                continue;

            TableColumn<Pelicula, String> column = new TableColumn<>("[" + field.getName() + "]");
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tablaPelis.getColumns().add(column);
        }

        tablaPelis.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//        ConexionBBDD.conectar();
//
//        List<Pelicula> peliculas=pelicula.cargarPelis();
//        for (Pelicula peli:peliculas){
//            System.out.println(peli);
//        }
//        tablaPelis.setItems(FXCollections.observableArrayList(peliculas).sorted());
//
//    }

}
