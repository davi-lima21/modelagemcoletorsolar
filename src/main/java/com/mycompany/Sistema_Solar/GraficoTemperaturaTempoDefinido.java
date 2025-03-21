package com.mycompany.Sistema_Solar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.jfree.chart.axis.NumberAxis;

public class GraficoTemperaturaTempoDefinido {

    private TimeSeries serieSaida, serieEntrada, serieAmbiente, serieIrradiacao;
    private JFreeChart chartSaida, chartEntrada, chartAmbiente, chartIrradiacao;
    private ChartPanel panelSaida, panelEntrada, panelAmbiente, panelIrradiacao;

    public GraficoTemperaturaTempoDefinido() {
        serieSaida = new TimeSeries("Temperatura de Saída");
        serieEntrada = new TimeSeries("Temperatura de Entrada");
        serieAmbiente = new TimeSeries("Temperatura Ambiente");
        serieIrradiacao = new TimeSeries("Irradiação Solar");

        chartSaida = criarGrafico("Temperatura de Saída", "Tempo (s)", "Temperatura (°C)", serieSaida, 0, 80);
        chartEntrada = criarGrafico("Temperatura de Entrada", "Tempo (s)", "Temperatura (°C)", serieEntrada, 0, 80);
        chartAmbiente = criarGrafico("Temperatura Ambiente", "Tempo (s)", "Temperatura (°C)", serieAmbiente, 0, 80);
        chartIrradiacao = criarGrafico("Irradiação Solar", "Tempo (s)", "Wh/m^2", serieIrradiacao, 0, 1100);

        panelSaida = new ChartPanel(chartSaida);
        panelEntrada = new ChartPanel(chartEntrada);
        panelAmbiente = new ChartPanel(chartAmbiente);
        panelIrradiacao = new ChartPanel(chartIrradiacao);
    }

    private JFreeChart criarGrafico(String titulo, String eixoX, String eixoY, TimeSeries serie, int min, int max) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serie);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(titulo, eixoX, eixoY, dataset, true, true, false);
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setRange(min, max);
        return chart;
    }

    public void executarSimulacao(List<Double> tempSaida, List<Double> tempEntrada, List<Double> tempAmbiente, List<Double> irradiacao) {
        for (int i = 0; i < tempSaida.size(); i++) {
            Second segundo = new Second(i % 60, (i / 60) % 60, (i / 3600) % 24, 1, 1, 2025);
            serieSaida.addOrUpdate(segundo, round(tempSaida.get(i), 2));
            serieEntrada.addOrUpdate(segundo, round(tempEntrada.get(i), 2));
            serieAmbiente.addOrUpdate(segundo, round(tempAmbiente.get(i), 2));
            serieIrradiacao.addOrUpdate(segundo, round(irradiacao.get(i), 2));
        }
        panelSaida.repaint();
        panelEntrada.repaint();
        panelAmbiente.repaint();
        panelIrradiacao.repaint();
    }

    public ChartPanel getPanelSaida() { return panelSaida; }
    public ChartPanel getPanelEntrada() { return panelEntrada; }
    public ChartPanel getPanelAmbiente() { return panelAmbiente; }
    public ChartPanel getPanelIrradiacao() { return panelIrradiacao; }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
