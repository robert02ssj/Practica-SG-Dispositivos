package com.ssj;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class PrimaryController {
    private static final String Ruta = "Dispositivos.dat";
    private static ArrayList<Dispositivo> ListaDispositivos = new ArrayList<Dispositivo>();
    private final static int tamRegistroDisp = 114;

    @FXML
    private TextArea listaDisp;
    

    @FXML
    private void initialize() throws IOException {
        cargardatos();
    }

    @FXML
    private void AñadirDisp() throws IOException {
        
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void ModDisp() throws IOException {

    }

    @FXML
    private void EliminarDisp() throws IOException {

    }

    @FXML
    private void Salir() throws IOException {
        System.exit(0);
    }

    public  void cargardatos() {
        try {
            RandomAccessFile raf = new RandomAccessFile(Ruta, "rw");
            while (raf.getFilePointer() < raf.length()) {
                long inicio = raf.getFilePointer();
                int id = raf.readInt();
                int tipo = raf.readInt();
                raf.seek(inicio + (tamRegistroDisp));
                switch (tipo) {
                    case 1:
                        try {
                            Ordenador ordenador = new Ordenador(id);
                            ordenador.load();
                            ListaDispositivos.add(ordenador);
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de ordenador");
                        }
                        break;
                    case 2:
                        try {
                            Impresora impresora = new Impresora(id);
                            impresora.load();
                            ListaDispositivos.add(impresora);
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de impresora");
                        }
                        break;
                    case 3:
                        try {
                            Smartphone smartphone = new Smartphone(id);
                            smartphone.load();
                            ListaDispositivos.add(smartphone);
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de smartphone");
                        }
                        break;
                    default:
                        Dispositivo dispositivo = new Dispositivo(id);
                        dispositivo.load();
                        ListaDispositivos.add(dispositivo);
                        break;
                }
            }
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar los datos Dispositivo");
        }
    }
    public void mostrarDispositivos() {
        for (int i = 0; i < ListaDispositivos.size(); i++) {
            System.out.println(ListaDispositivos.get(i).toString());
            listaDisp.setText(listaDisp.getText() + ListaDispositivos.get(i).toString() + "\n");
        }
    }
}
