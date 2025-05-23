package com.mycompany.Sistema_Solar;

public class Main {
    public static void main(String[] args) {
        // Inicia a interface gráfica
        java.awt.EventQueue.invokeLater(() -> {
            new Sistema_Solar_Interface().setVisible(true);
        });
    }
}