package com.mycompany.Sistema_Solar;



/**
 * A classe {@code Coletor_solar} representa o modelo de um coletor solar
 * térmico. Ela realiza cálculos relacionados à eficiência do coletor, ganho
 * energético, perda de calor e temperaturas de entrada e saída da água.
 *
 * @author [Seu Nome]
 */
public class Coletor_solar {


    /**
     * Irradiação solar incidente (W/m²).
     */
    double irradiacao_solar;

    /**
     * Temperatura ambiente (°C).
     */
    private final double TEMPERATURA_AMBIENTE;

    /**
     * Temperatura de entrada da água no coletor (°C).
     */
    private double TEMPERATURA_ENTRADA = TemperaturaAmbiente.chamarTemperaturaAmbiente() - 5;

    /**
     * Vazão da água através do coletor (kg/s).
     */
    private final double vazao;

    /**
     * Fator de ajuste para a temperatura de entrada.
     */
    double ALPHA = 0.5;

    /**
     * Indica se é a primeira execução do sistema.
     */
    private boolean primeiraExecucao = true;

    /**
     * Construtor para inicializar os parâmetros do coletor solar.
     *
     * @param irradiacao_solar Irradiação solar inicial (W/m²).
     * @param temperaturaAmbiente Temperatura ambiente inicial (°C).
     * @param vazao Vazão da água através do coletor (kg/s).
     */
    public Coletor_solar(double irradiacao_solar, double temperaturaAmbiente, double vazao) {
        this.irradiacao_solar = irradiacao_solar;
        this.TEMPERATURA_AMBIENTE = temperaturaAmbiente;
        this.vazao = vazao;
    }

    /**
     * Calcula e retorna a irradiância solar com base no horário do dia.
     *
     * @param horario Horário do dia (formato 24h).
     * @return A irradiância solar correspondente ao horário (W/m²).
     */
    public double getIrradiacao(double horario) {
        if (horario >= 7 && horario < 11) { // Manhã
            irradiacao_solar = 250 + (horario - 8) * 180;
        } else if (horario >= 11 && horario <= 12) { // Pico próximo ao meio-dia
            irradiacao_solar = 900 + (horario - 11) * 140;
        } else if (horario > 12 && horario <= 14) {
            irradiacao_solar = 1080 - (horario - 12) * 140;
        } else if (horario > 14 && horario <= 17) { // Declínio durante a tarde
            irradiacao_solar = 950 - (horario - 14) * 130;
        } else if (horario > 17 && horario <= 19) { // Final de tarde
            irradiacao_solar = 250 - (horario - 20) * 110;
        } else { // Noite
            irradiacao_solar = 50;
        }
        return irradiacao_solar;
    }

    /**
     * Calcula a temperatura de entrada da água no coletor.
     *
     * @param tempSaida Temperatura da água na saída do coletor (°C).
     * @return A nova temperatura de entrada da água (°C).
     */
    public double calcularTemperaturaEntrada(double tempSaida) {
        return (1 - ALPHA) * TEMPERATURA_AMBIENTE + ALPHA * tempSaida;
    }

    /**
     * Calcula a temperatura de saída da água no coletor com base nos parâmetros
     * atuais, como eficiência, perdas e ganho energético.
     *
     * @return A temperatura de saída da água (°C).
     */
    public double calcularTemperaturaSaida() {
        final double EFICIENCIA_COLETOR = 0.6; // Eficiência do coletor (60%)
        final double PERDA_CALOR_POR_AREA = 4; // Perda térmica por área (W/m²·°C)
        final double CALOR_ESPECIFICO_AGUA = 4186; // Calor específico da água (J/kg·°C)
        final double AREA_COLETOR = 12; // Área do coletor (m²)

        // Cálculo da perda de calor total
        double perdaCalorTotal = PERDA_CALOR_POR_AREA * AREA_COLETOR * (TEMPERATURA_ENTRADA - TEMPERATURA_AMBIENTE);

        // Cálculo do ganho energético total (em Watts)
        double ganhoEnergeticoTotal = irradiacao_solar * AREA_COLETOR * EFICIENCIA_COLETOR;

        // Cálculo da temperatura de saída (T_o(t))
        double tempSaida = TEMPERATURA_ENTRADA + (ganhoEnergeticoTotal - perdaCalorTotal) / (vazao * CALOR_ESPECIFICO_AGUA);

        // Atualiza a temperatura de entrada
        TEMPERATURA_ENTRADA = calcularTemperaturaEntrada(tempSaida);

        System.out.println("irradiacao: " + irradiacao_solar);
        return tempSaida;
    }
}
