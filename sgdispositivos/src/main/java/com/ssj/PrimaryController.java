package com.ssj;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {
    private static final String Ruta = "Dispositivos.dat";
    public static ArrayList<Dispositivo> ListaDispositivos = new ArrayList<Dispositivo>();
    private final static int tamRegistroDisp = 114;

    @FXML
    private TextArea listaDisp;

    @FXML
    private TextField marca;

    @FXML
    private TextField modelo;

    @FXML
    private ChoiceBox<String> tipoDisp;

    //Ordenador
    @FXML
    private TextField procesadorpc;

    @FXML
    private TextField rampc;

    @FXML
    private TextField almpc;

    @FXML
    private TextField tipodisco;

    //Impresora
    @FXML
    private TextField tipoImpresora;

    @FXML
    private CheckBox scanner;

    @FXML
    private CheckBox color;

    //Smartphone
    @FXML
    private TextField procesadormovil;

    @FXML
    private TextField ramsm;

    @FXML
    private TextField almsmr;

    @FXML
    private TextField sisop;



    @FXML
    private void initialize() throws IOException {
        ListaDispositivos.clear();
        cargardatos();
        if (listaDisp != null) {
            mostrarDispositivos();
        }
    }

    @FXML
    private void AnadirDisp() throws IOException {
        String marcaDisp = marca.getText();
        String modeloDisp = modelo.getText();

        if (marcaDisp.length() > 0 && modeloDisp.length() > 0) {
            String tipo = tipoDisp.getValue().toString();
            switch (tipo) {
                case "Ordenador":
                    String procesador = procesadorpc.getText();
                    int ram = Integer.parseInt(rampc.getText());
                    int almacenamiento = Integer.parseInt(almpc.getText());
                    int tipoDisco = Integer.parseInt(tipodisco.getText());
                    Ordenador ordenador = new Ordenador(marcaDisp, modeloDisp, true,false, ram, procesador, almacenamiento,tipoDisco);
                    ordenador.save();
                    ListaDispositivos.add(ordenador);
                    break;
                case "Impresora":
                    int tipoImpr = Integer.parseInt(tipoImpresora.getText());
                    boolean escaner = scanner.isSelected();
                    boolean color = this.color.isSelected();
                    Impresora impresora = new Impresora(marcaDisp, modeloDisp, true, false, tipoImpr, escaner, color);
                    impresora.save();
                    ListaDispositivos.add(impresora);
                    break;
                case "Smartphone":
                    String procesadormv = procesadormovil.getText();
                    int ramsmartphone = Integer.parseInt(ramsm.getText());
                    int almacenamientoMovil = Integer.parseInt(almsmr.getText());
                    int sisop = Integer.parseInt(this.sisop.getText());
                    Smartphone smartphone = new Smartphone(marcaDisp, modeloDisp, true, false, ramsmartphone, procesadormv, almacenamientoMovil, sisop);
                    smartphone.save();
                    ListaDispositivos.add(smartphone);
                    break;
                default:
                    Dispositivo dispositivo = new Dispositivo(marcaDisp, modeloDisp, true, 0, false);
                    dispositivo.save();
                    ListaDispositivos.add(dispositivo);
                    listaDisp.setText(listaDisp.getText() + dispositivo.toString() + "\n");
                    break;
            }

        }
        App.setRoot("primary");
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

    public void cargardatos() {
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
        listaDisp.setText("");
        for (int i = 0; i < ListaDispositivos.size(); i++) {
            System.out.println(ListaDispositivos.get(i).toString());
            listaDisp.setText(listaDisp.getText() + ListaDispositivos.get(i).toString() + "\n");
        }
    }
}
