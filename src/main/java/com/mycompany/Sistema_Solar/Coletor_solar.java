package com.mycompany.Sistema_Solar;

public class Coletor_solar {

    double irradiacao_solar; // G (W/m²)
    private double TEMPERATURA_AMBIENTE; // T_amb (°C)
    private double TEMPERATURA_ENTRADA = TemperaturaAmbiente.chamarTemperaturaAmbiente() - 5; // T_inicial (°C)
    private double vazao; // ṁ (kg/s)
    double ALPHA = 0.5;
    private boolean primeiraExecucao = true;

    public Coletor_solar(double irradiacao_solar, double temperaturaAmbiente, double vazao) {
        this.irradiacao_solar = irradiacao_solar;
        this.TEMPERATURA_AMBIENTE = temperaturaAmbiente;
        this.vazao = vazao;
    }

    public double getIrradiacao(double horario) {
        if (horario >= 7 && horario < 11) {
            irradiacao_solar = 250 + (horario - 8) * 180;
        } else if (horario >= 11 && horario <= 12) {
            irradiacao_solar = 900 + (horario - 11) * 140;
        } else if (horario > 12 && horario <= 14) {
            irradiacao_solar = 1080 - (horario - 12) * 140;
        } else if (horario > 14 && horario <= 17) {
            irradiacao_solar = 950 - (horario - 14) * 130;
        } else if (horario > 17 && horario <= 19) {
            irradiacao_solar = 250 - (horario - 20) * 110;
        } else {
            irradiacao_solar = 50;
        }
        return irradiacao_solar;
    }

    public void setIrradiacaoSolar(double irradiacao) {
        this.irradiacao_solar = irradiacao;
    }

    public void setTemperaturaAmbiente(double temperaturaAmbiente) {
        // opcionalmente, atualize a entrada também
        this.TEMPERATURA_AMBIENTE = temperaturaAmbiente;
    }

    public void setVazao(double vazao) {
        this.vazao = vazao;
    }

    private double calcularTemperaturaEntrada(double tempSaida) {
        return (1 - ALPHA) * TEMPERATURA_AMBIENTE + ALPHA * tempSaida;
    }

    public double getTemperaturaEntrada() {
        return TEMPERATURA_ENTRADA;
    }

    public double calcularTemperaturaSaida() {
        final double CALOR_ESPECIFICO_AGUA = 4186;
        final double AREA_COLETOR = 2;
        final double FATOR_REMOVER_CALOR = 0.9;
        final double EFICIENCIA_OPTICA = 0.7;
        final double COEF_PERDA_TERMICA = 4;

        // Calcula energia útil
        double energiaUtil = FATOR_REMOVER_CALOR * AREA_COLETOR
                * (irradiacao_solar * EFICIENCIA_OPTICA - COEF_PERDA_TERMICA * (TEMPERATURA_ENTRADA - TEMPERATURA_AMBIENTE));
        System.out.println("Temperatura de entrada ANTES" + TEMPERATURA_ENTRADA);
        // Calcula saída com base na entrada atual
        double tempSaida = TEMPERATURA_ENTRADA + energiaUtil / (vazao * CALOR_ESPECIFICO_AGUA);

        // Atualiza a temperatura de entrada para a próxima chamada
        TEMPERATURA_ENTRADA = calcularTemperaturaEntrada(tempSaida);
        System.out.println("Temperatura de entrada depois" + TEMPERATURA_ENTRADA);
        return tempSaida;
    }
}
