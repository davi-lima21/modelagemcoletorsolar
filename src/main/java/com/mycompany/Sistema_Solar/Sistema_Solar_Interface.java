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
        grafico = new GraficoTemperatura();
        configurarGraficos();
        input_vazao.setText(Double.toString(vazao));
        input_temp_ambiente.setText(Double.toString(temperatura_ambiente));

        // Cria e exibe o gráfico de temperatura
        // Adiciona um ouvinte para o slider de hora do dia
        hora_dia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                // Atualiza o valor exibido quando o slider mudar
                int hora = hora_dia.getValue();
                valor_hora_dia.setText(String.valueOf(hora));
            }
        });

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

    private void configurarGraficos() {
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

    /**
     * Inicia a atualização automática das temperaturas do coletor solar a cada
     * intervalo de tempo. Utiliza um Timer para agendar a execução periódica da
     * atualização.
     */
    private void iniciarAtualizacaoAutomatica() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Atualiza a temperatura de saída automaticamente
                atualizarTemperaturaSaida();
            }
        }, 0, 1500); // Atualização a cada 1500 ms (1.5 segundos)
    }

    /**
     * Para a atualização automática das temperaturas, cancelando o timer.
     */
    private void pararAtualizacaoAutomatica() {
        if (timer != null) {
            timer.cancel();
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

        dia_tipo = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        button_diminuir_vazaoin = new javax.swing.JToggleButton();
        button_aumentar_vazaoin = new javax.swing.JToggleButton();
        button_diminuir_temperatura_ambiente = new javax.swing.JToggleButton();
        button_aumentar_temperatura_ambiente = new javax.swing.JToggleButton();
        valor_hora_dia = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        hora_dia = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        graficos = new javax.swing.JPanel();
        temp_ambiente_entrada = new javax.swing.JPanel();
        temp_saida_graf = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        input_vazao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        input_temp_ambiente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(0, 0));
        setName("tela1"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 544));

        button_diminuir_vazaoin.setText("▼");
        button_diminuir_vazaoin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        button_aumentar_vazaoin.setText("▲");
        button_aumentar_vazaoin.setAlignmentY(0.6F);
        button_aumentar_vazaoin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        button_diminuir_temperatura_ambiente.setText("▼");
        button_diminuir_temperatura_ambiente.setAlignmentY(0.6F);
        button_diminuir_temperatura_ambiente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        button_aumentar_temperatura_ambiente.setText("▲");
        button_aumentar_temperatura_ambiente.setAlignmentY(0.6F);
        button_aumentar_temperatura_ambiente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        jPanel1.setAlignmentY(0.6F);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hora_dia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(hora_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Modelo de entradas e saídas do coletor solar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout graficosLayout = new javax.swing.GroupLayout(graficos);
        graficos.setLayout(graficosLayout);
        graficosLayout.setHorizontalGroup(
            graficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        graficosLayout.setVerticalGroup(
            graficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );

        temp_ambiente_entrada.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout temp_ambiente_entradaLayout = new javax.swing.GroupLayout(temp_ambiente_entrada);
        temp_ambiente_entrada.setLayout(temp_ambiente_entradaLayout);
        temp_ambiente_entradaLayout.setHorizontalGroup(
            temp_ambiente_entradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );
        temp_ambiente_entradaLayout.setVerticalGroup(
            temp_ambiente_entradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        temp_saida_graf.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout temp_saida_grafLayout = new javax.swing.GroupLayout(temp_saida_graf);
        temp_saida_graf.setLayout(temp_saida_grafLayout);
        temp_saida_grafLayout.setHorizontalGroup(
            temp_saida_grafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        temp_saida_grafLayout.setVerticalGroup(
            temp_saida_grafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Vazão de Entrada (m^3/s)");

        input_vazao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_vazao.setText("0");
        input_vazao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_vazaoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Temperatura Ambiente (Cº)");

        input_temp_ambiente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_temp_ambiente.setText("0");
        input_temp_ambiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_temp_ambienteActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"aa", "aa", "aa"},
                {"aa", "aa", "aa"},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(input_temp_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(input_vazao, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(button_diminuir_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button_aumentar_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button_aumentar_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button_diminuir_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(temp_saida_graf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(temp_ambiente_entrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(181, 181, 181)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valor_hora_dia)
                    .addComponent(graficos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(282, 282, 282)
                                .addComponent(graficos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(input_vazao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(button_aumentar_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button_diminuir_vazaoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(button_aumentar_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button_diminuir_temperatura_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(input_temp_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(valor_hora_dia)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(125, 125, 125)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(temp_ambiente_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(temp_saida_graf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(463, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        // Adiciona ruído aleatório à temperatura de entrada
        Random random = new Random();
        double ruido = (random.nextDouble() * 1.0) - 0.5; // Ruído entre -0.5 e 0.5
        temperatura_entrada = coletor.calcularTemperaturaEntrada(tempSaida) + ruido;

        // Atualiza o texto do rótulo da vazão
        System.out.println("tempSaida: " + tempSaida);
        grafico.atualizarGrafico(tempSaida, temperatura_entrada, temperatura_ambiente, coletor.getIrradiacao(hora_dia.getValue()));
        // Incrementa o contador de tempo e atualiza o gráfico
        t++;

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
    private javax.swing.JToggleButton button_aumentar_temperatura_ambiente;
    private javax.swing.JToggleButton button_aumentar_vazaoin;
    private javax.swing.JToggleButton button_diminuir_temperatura_ambiente;
    private javax.swing.JToggleButton button_diminuir_vazaoin;
    private javax.swing.ButtonGroup dia_tipo;
    private javax.swing.JPanel graficos;
    private javax.swing.JSlider hora_dia;
    private javax.swing.JTextField input_temp_ambiente;
    private javax.swing.JTextField input_vazao;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel temp_ambiente_entrada;
    private javax.swing.JPanel temp_saida_graf;
    private javax.swing.JLabel valor_hora_dia;
    // End of variables declaration//GEN-END:variables
}
