package com.mycompany.Sistema_Solar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.time.Minute;

public class GraficoTemperatura {

    private TimeSeries serieSaida, serieEntrada, serieAmbiente, serieIrradiacao; // Séries de dados
    private JFreeChart chartSaida, chartEntrada, chartAmbiente, chartIrradiacao; // Gráficos
    private ChartPanel panelSaida, panelEntrada, panelAmbiente, panelIrradiacao; // Painéis dos gráficos

    private int tempoSegundos = 0;

    public GraficoTemperatura() {
        // Criação das séries
        serieSaida = new TimeSeries("Temperatura de Saída");
        serieEntrada = new TimeSeries("Temperatura de Entrada");
        serieAmbiente = new TimeSeries("Temperatura Ambiente");
        serieIrradiacao = new TimeSeries("Irradiação Solar");
        // Criação dos gráficos
        chartSaida = criarGraficoSaida();
        chartEntrada = criarGraficoEntrada();
        chartAmbiente = criarGraficoAmbiente();
        chartIrradiacao = criarGraficoIrradiacao();
        // Criação dos painéis com os gráficos
        panelSaida = new ChartPanel(chartSaida);
        panelEntrada = new ChartPanel(chartEntrada);
        panelAmbiente = new ChartPanel(chartAmbiente);
        panelIrradiacao = new ChartPanel(chartIrradiacao);
    }

    public void limparDadosGraficos() {
        if (serieSaida != null) {
            serieSaida.clear();
        }
        if (serieEntrada != null) {
            serieEntrada.clear();
        }
        if (serieAmbiente != null) {
            serieAmbiente.clear();
        }
        if (serieIrradiacao != null) {
            serieIrradiacao.clear();
        }
    }

    // Métodos de criação dos gráficos
    private JFreeChart criarGraficoSaida() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieSaida);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura de Saída", "Tempo (s)", "Temperatura (°C)", dataset,
                true, true, false
        );
        // Configurar o eixo Y para definir um intervalo mínimo
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false); // Garante que o zero não seja sempre incluído
        yAxis.setRange(0, 80); // Defina um intervalo adequado, ajuste conforme necessário

        return chart;

    }
    // Métodos de criação dos gráficos

    private JFreeChart criarGraficoIrradiacao() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieIrradiacao);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Irradiancia Solar", "Tempo (s)", "Wh/m^2", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false); // Garante que o zero não seja sempre incluído
        yAxis.setRange(0, 1100); // Defina um intervalo adequado, ajuste conforme necessário

        return chart;
    }

    private JFreeChart criarGraficoEntrada() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieEntrada);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura de Entrada", "Tempo (s)", "Temperatura (°C)", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        return chart;
    }

    private JFreeChart criarGraficoAmbiente() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieAmbiente);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura Ambiente", "Tempo (s)", "Temperatura (°C)", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false); // Garante que o zero não seja sempre incluído
        yAxis.setRange(0, 80); // Defina um intervalo adequado, ajuste conforme necessário

        return chart;
    }

    // Métodos públicos para pegar os painéis
    public ChartPanel criarGraficoSaidaPanel() {
        return panelSaida;
    }

    public ChartPanel criarGraficoEntradaPanel() {
        return panelEntrada;
    }

    public ChartPanel criarGraficoAmbientePanel() {
        return panelAmbiente;
    }

    public ChartPanel criarGraficoIrradiacaoPanel() {
        return panelIrradiacao;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void atualizarGrafico(double tempSaida, double tempEntrada, double tempAmbiente, double irradiacao) {
        // Calcula corretamente os segundos, minutos e horas
        int segundos = tempoSegundos % 60;
        int minutos = (tempoSegundos / 60) % 60;
        int horas = (tempoSegundos / 3600) % 24; // Considerando um ciclo de 24h

        // Criar o tempo corretamente
        Second segundo = new Second(segundos, minutos, horas, 1, 1, 2025); // Ajuste o ano se necessário

        serieSaida.addOrUpdate(segundo, tempSaida);
        serieEntrada.addOrUpdate(segundo, tempEntrada);
        serieAmbiente.addOrUpdate(segundo, tempAmbiente);
        serieIrradiacao.addOrUpdate(segundo, irradiacao);

        // Incrementa o contador de tempo
        tempoSegundos++;

        // Atualiza os painéis
        panelSaida.repaint();
        panelEntrada.repaint();
        panelAmbiente.repaint();
        panelIrradiacao.repaint();
    }

    public double lerTemperatura(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensagem);
        while (true) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.print("Entrada inválida! Digite novamente: ");
                scanner.next();
            }
        }
    }

}
