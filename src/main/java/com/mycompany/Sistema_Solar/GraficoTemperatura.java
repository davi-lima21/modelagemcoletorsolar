/**
 * Classe responsável por criar, exibir e atualizar um gráfico de evolução das temperaturas
 * utilizando a biblioteca JFreeChart. O gráfico apresenta as temperaturas de saída,
 * entrada e ambiente ao longo do tempo.
 */
package com.mycompany.Sistema_Solar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import org.jfree.data.time.Minute;

public class GraficoTemperatura {

    private TimeSeries serie1, serie2, serie3; // Séries de dados do gráfico
    private TimeSeriesCollection dataset; // Conjunto de dados do gráfico
    private JFreeChart chart; // Objeto do gráfico
    private ChartPanel chartPanel; // Painel do gráfico

    /**
     * Construtor da classe GraficoTemperatura.
     * Inicializa as séries de dados, o conjunto de dados e configura o gráfico.
     */
    public GraficoTemperatura() {
        // Inicializa o conjunto de dados
        dataset = new TimeSeriesCollection();

        // Cria as séries de dados
        serie1 = new TimeSeries("Temperatura de Saída");
        serie2 = new TimeSeries("Temperatura de Entrada");
        serie3 = new TimeSeries("Temperatura Ambiente");

        // Adiciona as séries ao conjunto de dados
        dataset.addSeries(serie1);
        dataset.addSeries(serie2);
        dataset.addSeries(serie3);

        // Cria o gráfico com múltiplas séries
        chart = ChartFactory.createTimeSeriesChart(
            "Evolução das Temperaturas", // Título do gráfico
            "Tempo (s)",                // Eixo X
            "Temperatura (°C)",         // Eixo Y
            dataset                     // Dados
        );
    }

    /**
     * Método para criar e exibir o gráfico em uma nova janela.
     * Configura o painel do gráfico e o exibe em um JFrame.
     */
    public void criarGrafico() {
        // Cria o painel do gráfico
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Cria uma nova janela para exibir o gráfico
        JFrame chartFrame = new JFrame("Gráfico de Temperatura");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(600, 400);
        chartFrame.setLocationRelativeTo(null); // Centraliza a janela
        chartFrame.add(chartPanel, BorderLayout.CENTER);

        // Torna a janela visível
        chartFrame.setVisible(true);
    }

    private int tempoSegundos = 0;

    /**
     * Atualiza o gráfico com novos valores de temperatura.
     * 
     * @param tempSaida    Temperatura de saída (°C).
     * @param tempEntrada  Temperatura de entrada (°C).
     * @param tempAmbiente Temperatura ambiente (°C).
     */
    public void atualizarGrafico(double tempSaida, double tempEntrada, double tempAmbiente) {
        // Cria um segundo artificialmente baseado no contador
        Second segundo = new Second(tempoSegundos, new Minute()); // A cada atualização aumenta-se o contador

        // Adiciona os novos valores às séries com o "tempo" artificial
        serie1.addOrUpdate(segundo, tempSaida);
        serie2.addOrUpdate(segundo, tempEntrada);
        serie3.addOrUpdate(segundo, tempAmbiente);

        // Incrementa o contador de segundos
        tempoSegundos++;

        // Atualiza o painel do gráfico
        chartPanel.repaint();
    }

    /**
     * Lê um valor de temperatura do terminal.
     * 
     * @param mensagem Mensagem a ser exibida ao solicitar a entrada do usuário.
     * @return O valor de temperatura informado pelo usuário.
     */
    public double lerTemperatura(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensagem);
        while (true) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.print("Entrada inválida! Digite novamente: ");
                scanner.next(); // Descarta entrada inválida
            }
        }
    }

}
