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
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;

public class GraficoTemperatura {

    private TimeSeries serieSaida, serieEntrada, serieAmbiente, serieIrradiacao; // Séries de dados
    private JFreeChart chartSaida, chartEntrada, chartAmbiente, chartIrradiacao; // Gráficos
    private ChartPanel panelSaida, panelEntrada, panelAmbiente, panelIrradiacao; // Painéis dos gráficos

    private int tempoSegundos = 0;

    private boolean usarHoraNoEixoX = false;

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

    // Define se o eixo X dos gráficos deve ser exibido em horas
    public void setHora(boolean usarHora) {
        this.usarHoraNoEixoX = usarHora;
    }

    // Retorna se o eixo X está configurado para exibir em horas
    public boolean isHora() {
        return usarHoraNoEixoX;
    }

    private String horaOrSegundos() {
        if (isHora()) {
            return "Tempo (h)";
        } else {
            return "Tempo (s)";
        }
    }

    public void atualizarRotuloEixoX(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLabel(horaOrSegundos());
    }

    // Métodos de criação dos gráficos
    private JFreeChart criarGraficoSaida() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieSaida);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura de Saída", horaOrSegundos(), "Temperatura (°C)", dataset,
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

    public JFreeChart getChartSaida() {
        return panelSaida.getChart();
    }

    public JFreeChart getChartEntrada() {
        return panelEntrada.getChart();
    }

    public JFreeChart getChartAmbiente() {
        return panelAmbiente.getChart();
    }

    public JFreeChart getChartIrradiacao() {
        return panelIrradiacao.getChart();
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void atualizarGrafico(double tempSaida, double tempEntrada, double tempAmbiente, double irradiacao, double tempoAtual) {
        // Calcula segundos, minutos e horas para o contador interno (se necessário)
        int segundos = tempoSegundos % 60;
        int minutos = (tempoSegundos / 60) % 60;
        int horas = (tempoSegundos / 3600) % 24;

        // Cria um objeto Second para o modo padrão
        Second segundo = new Second(segundos, minutos, horas, 1, 1, 2025); // Ajuste dia/mês/ano conforme necessário

        if (isHora()) {
            // Converte o tempoAtual (double horas) em um objeto RegularTimePeriod
            int horasInt = (int) tempoAtual; // Parte inteira (hora cheia)
            double fracaoHora = tempoAtual - horasInt; // Parte fracionária (0.0 a 0.99)
            int minutosInt = (int) (fracaoHora * 60); // Converte para minutos (0-59)

            // Cria um objeto Minute para representar o tempo com precisão de minutos
            Minute minuto = new Minute(minutosInt, new Hour(horasInt, 1, 1, 2025));

            // Adiciona os dados ao gráfico
            serieSaida.addOrUpdate(minuto, tempSaida);
            serieEntrada.addOrUpdate(minuto, tempEntrada);
            serieAmbiente.addOrUpdate(minuto, tempAmbiente);
            serieIrradiacao.addOrUpdate(minuto, irradiacao);
        } else {
            // Modo padrão (segundos)
            serieSaida.addOrUpdate(segundo, tempSaida);
            serieEntrada.addOrUpdate(segundo, tempEntrada);
            serieAmbiente.addOrUpdate(segundo, tempAmbiente);
            serieIrradiacao.addOrUpdate(segundo, irradiacao);
        }

        // Incrementa o contador de tempo (se necessário)
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
