package com.mycompany.Sistema_Solar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe que representa a interface gráfica do sistema solar. Ela gerencia a
 * interação do usuário e a atualização automática das temperaturas do coletor
 * solar. Utiliza um gráfico para exibir a evolução das temperaturas ao longo do
 * tempo.
 */
public class Sistema_Solar_Interface extends javax.swing.JFrame {

    private boolean atualizacaoAutomaticaAtiva = false; // Variável de controle
    private Timer timer;
    double temperatura_ambiente = TemperaturaAmbiente.chamarTemperaturaAmbiente();
    double vazao = 0.048; // Vazão do fluido
    double t = 0; // Contador de tempo para o gráfico
    private Coletor_solar coletor;
    private GraficoTemperatura grafico;
    double temperatura_entrada = temperatura_ambiente;

    /**
     * Construtor da interface do sistema solar. Inicializa os componentes
     * gráficos e o gráfico de temperatura. Também configura o slider de hora do
     * dia para atualizar a hora exibida na interface.
     */
    public Sistema_Solar_Interface() {
        initComponents();
        jScrollPaneMain.getVerticalScrollBar().setUnitIncrement(13); // Aumenta a velocidade da rolagem

        grafico = new GraficoTemperatura();
        configurarGraficosTempoReal();
        input_vazao.setText(Double.toString(vazao));
        input_temp_ambiente.setText(Double.toString(temperatura_ambiente));

        // Cria e exibe o gráfico de temperatura
        // Adiciona um ouvinte para o slider de hora do dia
        //metodo que muda a vazao manualmente pelo input
        input_vazao.addActionListener(e -> {
            double vazao_manual = Double.parseDouble(input_vazao.getText());
            System.out.println("vazao manual: " + vazao_manual);
            vazao = vazao_manual; // Opcional: limpar o campo após enviar
        });
        //metodo que muda a temperatura ambiente manualmente pelo input
        input_temp_ambiente.addActionListener(e -> {
            double temperatura_ambiente_manual = Double.parseDouble(input_temp_ambiente.getText());
            System.out.println("Temp ambiente manual: " + temperatura_ambiente_manual);
            temperatura_ambiente = temperatura_ambiente_manual; // Opcional: limpar o campo após enviar
        });

        // Inicia a atualização automática das temperaturas
        iniciarAtualizacaoAutomatica();
        // Atualiza as temperaturas ao iniciar
        atualizarTemperaturaSaida();
    }

    /**
     * Atualiza o valor das labels da tabela "dados"
     */
    private void atualizarTabela(double tempSaida, double irradiacao) {
        vazao = Math.round(vazao * 1000.0) / 1000.0;
        value_vazao.setText(Double.toString(vazao));
        value_temp_saida.setText(Double.toString(tempSaida));
        value_temp_ambiente.setText(Double.toString(temperatura_ambiente));
        value_irradiacao.setText(Double.toString(irradiacao));
        value_temp_entrada.setText(Double.toString(temperatura_entrada));
    }

    private void configurarGraficosTempoReal() {
// Criar os painéis com gráficos (a partir do método criarGraficoSaidaPanel, etc.)
        javax.swing.JPanel painelGraficoSaida = grafico.criarGraficoSaidaPanel();
        javax.swing.JPanel painelGraficoEntrada = grafico.criarGraficoEntradaPanel();
        javax.swing.JPanel painelGraficoAmbiente = grafico.criarGraficoAmbientePanel();
        javax.swing.JPanel painelGraficoIrradiacao = grafico.criarGraficoIrradiacaoPanel();

// Ajustar o tamanho preferido para os painéis de gráfico
        painelGraficoSaida.setPreferredSize(new java.awt.Dimension(411, 300));
        painelGraficoEntrada.setPreferredSize(new java.awt.Dimension(411, 300));
        painelGraficoAmbiente.setPreferredSize(new java.awt.Dimension(411, 300));
        painelGraficoIrradiacao.setPreferredSize(new java.awt.Dimension(411, 300));

// Certifique-se de que os painéis de gráfico sejam corretamente adicionados
        temp_saida_graf.add(painelGraficoSaida);  // Painel gráfico de saída
        temp_ambiente_entrada.add(painelGraficoEntrada);  // Painel gráfico de entrada
        temp_ambiente_entrada.add(painelGraficoAmbiente);  // Painel gráfico de ambiente
        temp_saida_graf.add(painelGraficoIrradiacao);

// Atualizando os layouts
        temp_saida_graf.revalidate();
        temp_saida_graf.repaint();
        temp_ambiente_entrada.revalidate();
        temp_ambiente_entrada.repaint();

// Certifique-se de que o layout esteja configurado corretamente para exibir os painéis
        temp_saida_graf.setLayout(new java.awt.FlowLayout());  // Usando FlowLayout para adicionar os painéis de gráfico
        temp_ambiente_entrada.setLayout(new java.awt.FlowLayout());  // Usando FlowLayout para os gráficos

// Se o painel onde os gráficos estão sendo adicionados é um JTabbedPane ou um painel com abas, você pode precisar chamar:
// painel_de_exibicao.revalidate();
// painel_de_exibicao.repaint();
    }

    private void iniciarSimulacaoTempoDefinido() {
        pararAtualizacaoAutomatica();
        //chamar Definir_Dados_Interface.java
        // Passa a referência da janela principal (this) e define como modal (true)
        Definir_Dados_Interface definirDados = new Definir_Dados_Interface(this, true);
        definirDados.setVisible(true);

        Double horaInicial = definirDados.getHoraInicial();
        Double horaFinal = definirDados.getHoraFinal();

        if (horaInicial != null && horaFinal != null) {
            System.out.println("Hora Inicial: " + horaInicial);
            System.out.println("Hora Final: " + horaFinal);
        } else {
            System.out.println("Erro: Hora inicial ou final inválida.");
        }

    }

    /**
     * Inicia a atualização automática das temperaturas do coletor solar a cada
     * intervalo de tempo. Utiliza um Timer para agendar a execução periódica da
     * atualização.
     */
    private void iniciarAtualizacaoAutomatica() {

        if (atualizacaoAutomaticaAtiva) {
            return; // Se já estiver rodando, não cria outro timer
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Atualiza a temperatura de saída automaticamente
                atualizarTemperaturaSaida();
            }
        }, 0, 1500); // Atualização a cada 1500 ms (1.5 segundos)
        atualizacaoAutomaticaAtiva = true;
    }

    /**
     * Para a atualização automática das temperaturas, cancelando o timer.
     */
    private void pararAtualizacaoAutomatica() {
        if (timer != null) {
            timer.cancel();
            atualizacaoAutomaticaAtiva = false;
        }

    }

    // M
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        tipo_dia = new javax.swing.ButtonGroup();
        tipo_simulacao = new javax.swing.ButtonGroup();
        jScrollPaneMain = new javax.swing.JScrollPane();
        container_filho = new javax.swing.JPanel();
        container_tabela = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        value_vazao = new javax.swing.JLabel();
        value_irradiacao = new javax.swing.JLabel();
        value_temp_ambiente = new javax.swing.JLabel();
        value_temp_saida = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        value_temp_entrada = new javax.swing.JLabel();
        button_importar_dados = new javax.swing.JToggleButton();
        temp_saida_graf = new javax.swing.JPanel();
        container_definirhora = new javax.swing.JPanel();
        hora_dia = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        temp_ambiente_entrada = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        input_temp_ambiente = new javax.swing.JTextField();
        button_aumentar_temperatura_ambiente = new javax.swing.JToggleButton();
        button_diminuir_temperatura_ambiente = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();
        input_vazao = new javax.swing.JTextField();
        button_aumentar_vazaoin = new javax.swing.JToggleButton();
        button_diminuir_vazaoin = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        botao_tempo_real = new javax.swing.JRadioButton();
        botao_definir_tempo = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        botao_tipo_simulacao = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        tipo_dia_sol = new javax.swing.JRadioButton();
        tipo_dia_nublado = new javax.swing.JRadioButton();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(0, 0));
        setName("tela1"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 544));

        jScrollPaneMain.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneMain.setAlignmentX(0.0F);
        jScrollPaneMain.setAlignmentY(0.0F);
        jScrollPaneMain.setAutoscrolls(true);

        container_filho.setBackground(new java.awt.Color(255, 255, 255));
        container_filho.setAlignmentX(0.0F);
        container_filho.setAlignmentY(0.0F);
        container_filho.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        container_filho.setName(""); // NOI18N

        container_tabela.setBackground(new java.awt.Color(255, 255, 255));
        container_tabela.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel2.setText("Vazão");

        jLabel7.setText("Irradiância");

        jLabel8.setText("Temperatura Ambiente");

        jLabel9.setText("Temperatura de saída");

        value_vazao.setText("0");

        value_irradiacao.setText("0");

        value_temp_ambiente.setText("0");

        value_temp_saida.setText("0");

        jLabel14.setText("Temperatura de entrada");

        value_temp_entrada.setText("0");

        button_importar_dados.setText("Importar Dados Reais");
        button_importar_dados.setToolTipText("");
        button_importar_dados.setAlignmentX(0.5F);
        button_importar_dados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_importar_dadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout container_tabelaLayout = new javax.swing.GroupLayout(container_tabela);
        container_tabela.setLayout(container_tabelaLayout);
        container_tabelaLayout.setHorizontalGroup(
            container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container_tabelaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_importar_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(container_tabelaLayout.createSequentialGroup()
                        .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(value_irradiacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(value_temp_saida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(value_temp_ambiente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(value_vazao, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(value_temp_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        container_tabelaLayout.setVerticalGroup(
            container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container_tabelaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(value_vazao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(value_irradiacao))
                .addGap(18, 18, 18)
                .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(value_temp_ambiente))
                .addGap(18, 18, 18)
                .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(value_temp_saida))
                .addGap(18, 18, 18)
                .addGroup(container_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(value_temp_entrada))
                .addGap(15, 15, 15)
                .addComponent(button_importar_dados, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        temp_saida_graf.setBackground(new java.awt.Color(255, 255, 255));
        temp_saida_graf.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray));

        javax.swing.GroupLayout temp_saida_grafLayout = new javax.swing.GroupLayout(temp_saida_graf);
        temp_saida_graf.setLayout(temp_saida_grafLayout);
        temp_saida_grafLayout.setHorizontalGroup(
            temp_saida_grafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );
        temp_saida_grafLayout.setVerticalGroup(
            temp_saida_grafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        container_definirhora.setBackground(new java.awt.Color(255, 255, 255));
        container_definirhora.setAlignmentY(0.6F);

        hora_dia.setMajorTickSpacing(12);
        hora_dia.setMaximum(24);
        hora_dia.setMinorTickSpacing(3);
        hora_dia.setPaintLabels(true);
        hora_dia.setPaintTicks(true);
        hora_dia.setSnapToTicks(true);
        hora_dia.setToolTipText("");
        hora_dia.setValue(13);

        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Davil\\OneDrive\\Documentos\\UFSC\\Projeto_coletor\\modelo_coletor_solar\\src\\main\\java\\com\\mycompany\\Sistema_Solar\\relogiosol.png")); // NOI18N

        javax.swing.GroupLayout container_definirhoraLayout = new javax.swing.GroupLayout(container_definirhora);
        container_definirhora.setLayout(container_definirhoraLayout);
        container_definirhoraLayout.setHorizontalGroup(
            container_definirhoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(container_definirhoraLayout.createSequentialGroup()
                .addGroup(container_definirhoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hora_dia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(container_definirhoraLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        container_definirhoraLayout.setVerticalGroup(
            container_definirhoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container_definirhoraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(hora_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        temp_ambiente_entrada.setBackground(new java.awt.Color(255, 255, 255));
        temp_ambiente_entrada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray));

        javax.swing.GroupLayout temp_ambiente_entradaLayout = new javax.swing.GroupLayout(temp_ambiente_entrada);
        temp_ambiente_entrada.setLayout(temp_ambiente_entradaLayout);
        temp_ambiente_entradaLayout.setHorizontalGroup(
            temp_ambiente_entradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        temp_ambiente_entradaLayout.setVerticalGroup(
            temp_ambiente_entradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Temperatura Ambiente (Cº)");

        input_temp_ambiente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_temp_ambiente.setText("0");
        input_temp_ambiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_temp_ambienteActionPerformed(evt);
            }
        });

        button_aumentar_temperatura_ambiente.setText("▲");
        button_aumentar_temperatura_ambiente.setAlignmentY(0.6F);
        button_aumentar_temperatura_ambiente.setBorder(null);
        button_aumentar_temperatura_ambiente.setFocusPainted(false);
        button_aumentar_temperatura_ambiente.setFocusable(false);
        button_aumentar_temperatura_ambiente.setMargin(new java.awt.Insets(5, 14, 3, 14));
        button_aumentar_temperatura_ambiente.setMaximumSize(new java.awt.Dimension(210, 40));
        button_aumentar_temperatura_ambiente.setMinimumSize(new java.awt.Dimension(130, 25));
        button_aumentar_temperatura_ambiente.setPreferredSize(new java.awt.Dimension(200, 35));
        button_aumentar_temperatura_ambiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_aumentar_temperatura_ambienteActionPerformed(evt);
            }
        });

        button_diminuir_temperatura_ambiente.setText("▼");
        button_diminuir_temperatura_ambiente.setAlignmentY(0.0F);
        button_diminuir_temperatura_ambiente.setBorder(null);
        button_diminuir_temperatura_ambiente.setFocusPainted(false);
        button_diminuir_temperatura_ambiente.setFocusable(false);
        button_diminuir_temperatura_ambiente.setMargin(new java.awt.Insets(5, 14, 3, 14));
        button_diminuir_temperatura_ambiente.setMaximumSize(new java.awt.Dimension(210, 40));
        button_diminuir_temperatura_ambiente.setMinimumSize(new java.awt.Dimension(130, 25));
        button_diminuir_temperatura_ambiente.setPreferredSize(new java.awt.Dimension(200, 35));
        button_diminuir_temperatura_ambiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_diminuir_temperatura_ambienteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Vazão de Entrada (L/min)");

        input_vazao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_vazao.setText("0");
        input_vazao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_vazaoActionPerformed(evt);
            }
        });

        button_aumentar_vazaoin.setText("▲");
        button_aumentar_vazaoin.setAlignmentY(0.6F);
        button_aumentar_vazaoin.setBorder(null);
        button_aumentar_vazaoin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        button_aumentar_vazaoin.setFocusPainted(false);
        button_aumentar_vazaoin.setFocusable(false);
        button_aumentar_vazaoin.setMargin(new java.awt.Insets(5, 14, 3, 14));
        button_aumentar_vazaoin.setMaximumSize(new java.awt.Dimension(210, 40));
        button_aumentar_vazaoin.setMinimumSize(new java.awt.Dimension(130, 25));
        button_aumentar_vazaoin.setPreferredSize(new java.awt.Dimension(200, 35));
        button_aumentar_vazaoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_aumentar_vazaoinActionPerformed(evt);
            }
        });

        button_diminuir_vazaoin.setText("▼");
        button_diminuir_vazaoin.setBorder(null);
        button_diminuir_vazaoin.setFocusPainted(false);
        button_diminuir_vazaoin.setFocusable(false);
        button_diminuir_vazaoin.setMargin(new java.awt.Insets(5, 14, 3, 14));
        button_diminuir_vazaoin.setMaximumSize(new java.awt.Dimension(210, 40));
        button_diminuir_vazaoin.setMinimumSize(new java.awt.Dimension(130, 25));
        button_diminuir_vazaoin.setPreferredSize(new java.awt.Dimension(200, 35));
        button_diminuir_vazaoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_diminuir_vazaoinActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Modelo de entradas e saídas do coletor solar");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        tipo_simulacao.add(botao_tempo_real);
        botao_tempo_real.setSelected(true);
        botao_tempo_real.setText("Tempo Real");
        botao_tempo_real.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_tempo_realActionPerformed(evt);
            }
        });

        tipo_simulacao.add(botao_definir_tempo);
        botao_definir_tempo.setText("Definir Tempo");
        botao_definir_tempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_definir_tempoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Definir Tipo de Simulação");

        botao_tipo_simulacao.setText("Confirmar");
        botao_tipo_simulacao.setEnabled(false);
        botao_tipo_simulacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_tipo_simulacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botao_tempo_real, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botao_definir_tempo))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(botao_tipo_simulacao, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao_tempo_real)
                    .addComponent(botao_definir_tempo))
                .addGap(18, 18, 18)
                .addComponent(botao_tipo_simulacao)
                .addGap(18, 18, 18))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tipo_dia.add(tipo_dia_sol);
        tipo_dia_sol.setSelected(true);
        tipo_dia_sol.setText("Dia ensolarado");
        tipo_dia_sol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_dia_solActionPerformed(evt);
            }
        });

        tipo_dia.add(tipo_dia_nublado);
        tipo_dia_nublado.setText("Dia Nublado");
        tipo_dia_nublado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_dia_nubladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tipo_dia_sol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(tipo_dia_nublado, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo_dia_sol)
                    .addComponent(tipo_dia_nublado))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout container_filhoLayout = new javax.swing.GroupLayout(container_filho);
        container_filho.setLayout(container_filhoLayout);
        container_filhoLayout.setHorizontalGroup(
            container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container_filhoLayout.createSequentialGroup()
                .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container_filhoLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(jLabel1))
                    .addGroup(container_filhoLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(container_filhoLayout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(input_vazao, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(button_diminuir_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button_aumentar_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container_filhoLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(input_temp_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(button_aumentar_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button_diminuir_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(container_definirhora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(container_tabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(temp_ambiente_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(temp_saida_graf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 509, Short.MAX_VALUE))
        );
        container_filhoLayout.setVerticalGroup(
            container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container_filhoLayout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container_filhoLayout.createSequentialGroup()
                        .addComponent(temp_ambiente_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(temp_saida_graf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(container_filhoLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(container_filhoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(input_vazao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(container_filhoLayout.createSequentialGroup()
                                .addComponent(button_aumentar_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button_diminuir_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(container_filhoLayout.createSequentialGroup()
                                .addComponent(button_aumentar_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button_diminuir_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(container_filhoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(container_filhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(input_temp_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(container_definirhora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(container_tabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(256, Short.MAX_VALUE))
        );

        jScrollPaneMain.setViewportView(container_filho);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Atualiza a temperatura de saída do coletor solar com base nos parâmetros
     * atuais. Inclui o cálculo da temperatura de entrada com um ruído aleatório
     * para simulação. Atualiza também o gráfico com os novos valores de
     * temperatura.
     */
    private void atualizarTemperaturaSaida() {
        if (coletor == null) {
            coletor = new Coletor_solar(0, 0, 0);
        }
        // Cria uma nova instância do coletor solar com os parâmetros atualizados
        Coletor_solar novoColetor = new Coletor_solar(coletor.getIrradiacao(
                hora_dia.getValue()),
                temperatura_ambiente,
                vazao
        );

        // Calcula a temperatura de saída com o coletor atualizado
        double tempSaida = novoColetor.calcularTemperaturaSaida();
        double irradiacao = coletor.getIrradiacao(hora_dia.getValue());
        // Adiciona ruído aleatório à temperatura de entrada
        Random random = new Random();
        double ruido = (random.nextDouble() * 1.0) - 0.5; // Ruído entre -0.5 e 0.5
        temperatura_entrada = coletor.calcularTemperaturaEntrada(tempSaida) + ruido;

        // Atualiza o texto do rótulo da vazão
        System.out.println("tempSaida: " + tempSaida);

        //arredondamento dos valores antes de enviar para o gráfico
        tempSaida = Math.round(tempSaida * 100.0) / 100.0;
        temperatura_entrada = Math.round(temperatura_entrada * 100.0) / 100.0;
        temperatura_ambiente = Math.round(temperatura_ambiente * 100.0) / 100.0;

        grafico.atualizarGrafico(tempSaida, temperatura_entrada, temperatura_ambiente, irradiacao);

        // Incrementa o contador de tempo e atualiza o gráfico
        t++;
        atualizarTabela(tempSaida, irradiacao);

    }

    /**
     * Método acionado ao clicar no botão para diminuir a vazão. Reduz o valor
     * da vazão em 0.001, garantindo que ela não fique abaixo de 0.001. Atualiza
     * a temperatura de saída após a mudança.
     *
     * @param evt Evento gerado ao clicar no botão.
     */
    private void button_diminuir_vazaoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_diminuir_vazaoinActionPerformed
        vazao = vazao - 0.01;
        if (vazao <= 0) {
            vazao = 0.001;
        }
        vazao = Math.round(vazao * 1000.0) / 1000.0;

        input_vazao.setText(Double.toString(vazao));
        atualizarTemperaturaSaida();
    }//GEN-LAST:event_button_diminuir_vazaoinActionPerformed
    /**
     * Método acionado ao clicar no botão para aumentar a vazão. Incrementa o
     * valor da vazão em 0.01, garantindo que ela não fique abaixo de 0.
     * Atualiza a temperatura de saída após a mudança.
     *
     * @param evt Evento gerado ao clicar no botão.
     */
    private void button_aumentar_vazaoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_aumentar_vazaoinActionPerformed
        // TODO add your handling code here:
        vazao = vazao + 0.01;
        if (vazao < 0) {
            vazao = 0;
        }
        vazao = Math.round(vazao * 1000.0) / 1000.0;

        input_vazao.setText(Double.toString(vazao));
        atualizarTemperaturaSaida();
    }//GEN-LAST:event_button_aumentar_vazaoinActionPerformed
    /**
     * Método acionado ao clicar no botão para diminuir a temperatura ambiente.
     * Reduz o valor da temperatura ambiente em 1 grau Celsius. Atualiza a
     * temperatura de saída após a mudança.
     *
     * @param evt Evento gerado ao clicar no botão.
     */
    private void button_diminuir_temperatura_ambienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_diminuir_temperatura_ambienteActionPerformed
        // TODO add your handling code here:
        temperatura_ambiente = temperatura_ambiente - 1;
        input_temp_ambiente.setText(Double.toString(temperatura_ambiente));
        atualizarTemperaturaSaida();
    }//GEN-LAST:event_button_diminuir_temperatura_ambienteActionPerformed
    /**
     * Método acionado ao clicar no botão para aumentar a temperatura ambiente.
     * Incrementa o valor da temperatura ambiente em 1 grau Celsius. Atualiza a
     * temperatura de saída após a mudança.
     *
     * @param evt Evento gerado ao clicar no botão.
     */
    private void button_aumentar_temperatura_ambienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_aumentar_temperatura_ambienteActionPerformed
        // TODO add your handling code here:
        temperatura_ambiente = temperatura_ambiente + 1;
        input_temp_ambiente.setText(Double.toString(temperatura_ambiente));
        atualizarTemperaturaSaida();
    }//GEN-LAST:event_button_aumentar_temperatura_ambienteActionPerformed

    private void input_vazaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_vazaoActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_input_vazaoActionPerformed

    private void input_temp_ambienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_temp_ambienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_temp_ambienteActionPerformed

    private void button_importar_dadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_importar_dadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_importar_dadosActionPerformed

    private void tipo_dia_nubladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_dia_nubladoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipo_dia_nubladoActionPerformed

    private void tipo_dia_solActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_dia_solActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_tipo_dia_solActionPerformed

    private void botao_tempo_realActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_tempo_realActionPerformed
        // TODO add your handling code here:
        botao_tipo_simulacao.setEnabled(true);
    }//GEN-LAST:event_botao_tempo_realActionPerformed

    private void botao_definir_tempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_definir_tempoActionPerformed
        // TODO add your handling code here:
        botao_tipo_simulacao.setEnabled(true);
    }//GEN-LAST:event_botao_definir_tempoActionPerformed

    private void botao_tipo_simulacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_tipo_simulacaoActionPerformed
        // TODO add your handling code here:
        if (botao_definir_tempo.isSelected()) {//button group tipo_simulacao estiver com botao_definir tempo selecionado
            iniciarSimulacaoTempoDefinido();
        } else {
            //refresh na aplicação
            iniciarAtualizacaoAutomatica();
        }

    }//GEN-LAST:event_botao_tipo_simulacaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema_Solar_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema_Solar_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema_Solar_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema_Solar_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema_Solar_Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton botao_definir_tempo;
    private javax.swing.JRadioButton botao_tempo_real;
    private javax.swing.JToggleButton botao_tipo_simulacao;
    private javax.swing.JToggleButton button_aumentar_temperatura_ambiente;
    private javax.swing.JToggleButton button_aumentar_vazaoin;
    private javax.swing.JToggleButton button_diminuir_temperatura_ambiente;
    private javax.swing.JToggleButton button_diminuir_vazaoin;
    private javax.swing.JToggleButton button_importar_dados;
    private javax.swing.JPanel container_definirhora;
    private javax.swing.JPanel container_filho;
    private javax.swing.JPanel container_tabela;
    private javax.swing.JSlider hora_dia;
    private javax.swing.JTextField input_temp_ambiente;
    private javax.swing.JTextField input_vazao;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneMain;
    private javax.swing.JPanel temp_ambiente_entrada;
    private javax.swing.JPanel temp_saida_graf;
    private javax.swing.ButtonGroup tipo_dia;
    private javax.swing.JRadioButton tipo_dia_nublado;
    private javax.swing.JRadioButton tipo_dia_sol;
    private javax.swing.ButtonGroup tipo_simulacao;
    private javax.swing.JLabel value_irradiacao;
    private javax.swing.JLabel value_temp_ambiente;
    private javax.swing.JLabel value_temp_entrada;
    private javax.swing.JLabel value_temp_saida;
    private javax.swing.JLabel value_vazao;
    // End of variables declaration//GEN-END:variables
}
