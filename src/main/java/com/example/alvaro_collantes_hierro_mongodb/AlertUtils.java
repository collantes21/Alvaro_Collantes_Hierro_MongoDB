package com.example.alvaro_collantes_hierro_mongodb;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtils {
    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Para evitar que se muestre un encabezado vacío
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // Botones de Confirmar y Cancelar
        ButtonType botonConfirmar = new ButtonType("Confirmar");
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
        alerta.getButtonTypes().setAll(botonConfirmar, botonCancelar);

        // Mostrar la alerta y esperar la respuesta del usuario
        return alerta.showAndWait().orElse(botonCancelar) == botonConfirmar;
    }
}
