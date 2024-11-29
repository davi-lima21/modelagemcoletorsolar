package com.mycompany.Sistema_Solar;

import java.util.LinkedList;
import java.util.Queue;

public class Coletor_solar {

    double irradiacao_solar; // Irradiação solar incidente (W/m²)
    private final double TEMPERATURA_AMBIENTE; // Temperatura ambiente (°C)
    private double TEMPERATURA_ENTRADA = TemperaturaAmbiente.chamarTemperaturaAmbiente() - 5; // Temperatura de entrada (°C)
    private final double vazao;
    double ALPHA = 0.5;
    private boolean primeiraExecucao = true;

    public Coletor_solar(double irradiacao_solar, double temperaturaAmbiente, double vazao) {
        this.irradiacao_solar = irradiacao_solar;
        this.TEMPERATURA_AMBIENTE = temperaturaAmbiente;
        this.vazao = vazao;
    }

    // Método para obter a irradiância
    public double getIrradiacao(double horario) {
        if (horario >= 7 && horario < 11) { // Manhã
            irradiacao_solar = 250 + (horario - 8) * 180; // de 500 a 800 entre 8h e 11h
        } else if (horario >= 11 && horario <= 12) { // Pico próximo ao meio-dia
            irradiacao_solar = 900 + (horario - 11) * 140; // de 800 a 1080 entre 11h e 12h
        } else if (horario > 12 && horario <= 14) {
            irradiacao_solar = 1080 - (horario - 12) * 140; // Corrige de 1080 para 800 entre 12h e 14h
        } else if (horario > 14 && horario <= 17) { // Declínio durante a tarde
            irradiacao_solar = 950 - (horario - 14) * 130; // de 800 a 250 entre 14h e 17h
        } else if (horario > 17 && horario <= 19) { // Final de tarde
            irradiacao_solar = 250 - (horario - 20) * 110; // de 250 a 50 entre 17h e 19h
        } else { // Noite
            irradiacao_solar = 50;
        }
        return irradiacao_solar;
    }

    // Método para calcular a temperatura de entrada
    public double calcularTemperaturaEntrada(double tempSaida) {

            return (1 - ALPHA) * TEMPERATURA_AMBIENTE + ALPHA * tempSaida;
        
    }

    public double calcularTemperaturaSaida() {
        final double EFICIENCIA_COLETOR = 0.6; // Eficiência do coletor (60%)
        final double PERDA_CALOR_POR_AREA = 4; // Coeficiente de perda térmica (W/m²·°C)
        final double CALOR_ESPECIFICO_AGUA = 4186; // Calor específico da água em J/kg·°C
        final double AREA_COLETOR = 12; // Área do coletor

        // Cálculo da perda de calor total
        double perdaCalorTotal = PERDA_CALOR_POR_AREA * AREA_COLETOR * (TEMPERATURA_ENTRADA - TEMPERATURA_AMBIENTE);

        // Cálculo do ganho energético total (em Watts)
        double ganhoEnergeticoTotal = irradiacao_solar * AREA_COLETOR * EFICIENCIA_COLETOR;

        // Cálculo da temperatura de saída (T_o(t)) usando as variáveis
        double tempSaida = TEMPERATURA_ENTRADA + (ganhoEnergeticoTotal - perdaCalorTotal) / (vazao * CALOR_ESPECIFICO_AGUA);
        // Atualiza a temperatura de entrada
        TEMPERATURA_ENTRADA = calcularTemperaturaEntrada(tempSaida);

        System.out.println("irradiacao: " + irradiacao_solar);
        return tempSaida;
    }

}
