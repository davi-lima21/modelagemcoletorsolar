/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Sistema_Solar;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Davil
 */
public class ImportarCSV {

    public static void main(String[] args) {
        String caminhoArquivo = "C:\\Users\\Davil\\OneDrive\\Documentos\\UFSC\\Projeto_coletor\\modelo_coletor_solar\\src\\main\\java\\com\\mycompany\\Sistema_Solar\\tabela.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                // Ignorar linhas que não contêm "="
                if (!linha.contains("=")) {
                    continue;
                }

                // Separar em chave e valores
                String[] partes = linha.split("=", 2);
                if (partes.length < 2) {
                    continue;
                }

                String[] valores = partes[1].split(";");

                if (valores.length >= 2) {
                    String hora = valores[1];
                    String temperatura = valores.length >= 3 ? valores[2] : "N/A";

                    System.out.println("Hora: " + hora + " | Temp: " + temperatura);
                    System.out.println("Linha original: " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
