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
    private TextField idDispo;

    @FXML
    private TextField marca;

    @FXML
    private TextField modelo;

    @FXML
    private CheckBox activo;

    @FXML
    private CheckBox eliminado;

    @FXML
    private ChoiceBox<String> tipo;

    @FXML
    private TextField idBusc;

    // Ordenador
    @FXML
    private TextField procesadorpc;

    @FXML
    private TextField rampc;

    @FXML
    private TextField almpc;

    @FXML
    private ChoiceBox<String> tipodisco;

    // Impresora
    @FXML
    private TextField tipoImpresora;

    @FXML
    private CheckBox scanner;

    @FXML
    private CheckBox color;

    // Smartphone
    @FXML
    private TextField procesadormovil;

    @FXML
    private TextField ramsm;

    @FXML
    private TextField almsmr;

    @FXML
    private ChoiceBox<String> sisop;

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
        int sistop = 0;
        int tipoDisco = 0;

        if (marcaDisp.length() > 0 && modeloDisp.length() > 0) {
            String TipoDisp = (String) tipo.getValue();
            Boolean estadoDisp = true;
            if (activo.isSelected()) {
                estadoDisp = true;
            } else {
                estadoDisp = false;
            }
            Boolean borradoDisp = true;
            if (eliminado.isSelected()) {
                borradoDisp = true;
            } else {
                borradoDisp = false;
            }
            switch (TipoDisp) {
                case "Ordenador":
                    String procesador = procesadorpc.getText();
                    int ram = Integer.parseInt(rampc.getText());
                    int almacenamiento = Integer.parseInt(almpc.getText());
                    switch (tipodisco.getValue().toString()) {
                        case "Mecanico":
                            tipoDisco = 0;
                            break;
                        case "SSD":
                            tipoDisco = 1;
                            break;
                        case "NVMe":
                            tipoDisco = 2;
                            break;
                        case "Otro":
                            tipoDisco = 3;
                            break;
                    }
                    Ordenador ordenador = new Ordenador(marcaDisp, modeloDisp, estadoDisp, borradoDisp, ram, procesador,
                            almacenamiento, tipoDisco);
                    ordenador.save();
                    ListaDispositivos.add(ordenador);
                    break;
                case "Impresora":
                    int tipoImpr = Integer.parseInt(tipoImpresora.getText());
                    boolean escaner = scanner.isSelected();
                    boolean color = this.color.isSelected();
                    Impresora impresora = new Impresora(marcaDisp, modeloDisp, estadoDisp, borradoDisp, tipoImpr,
                            escaner, color);
                    impresora.save();
                    ListaDispositivos.add(impresora);
                    break;
                case "Smartphone":
                    String procesadormv = procesadormovil.getText();
                    int ramsmartphone = Integer.parseInt(ramsm.getText());
                    int almacenamientoMovil = Integer.parseInt(almsmr.getText());
                    switch (sisop.getValue().toString()) {
                        case "Android":
                            sistop = 0;
                            break;
                        case "IOS":
                            sistop = 1;
                            break;
                        case "Otro":
                            sistop = 2;
                            break;
                    }

                    Smartphone smartphone = new Smartphone(marcaDisp, modeloDisp, estadoDisp, borradoDisp,
                            ramsmartphone, procesadormv, almacenamientoMovil, sistop);
                    smartphone.save();
                    ListaDispositivos.add(smartphone);
                    break;
                default:
                    Dispositivo dispositivo = new Dispositivo(marcaDisp, modeloDisp, estadoDisp, 0, borradoDisp);
                    dispositivo.save();
                    ListaDispositivos.add(dispositivo);
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
        App.setRoot("terciary");   
    }
    
    @FXML
    private void BuscarDisp() throws IOException {
        int idbuscado = Integer.parseInt(idBusc.getText());
        Dispositivo dispositivo = new Dispositivo(idbuscado);
        dispositivo.load();
        int tipodispo = dispositivo.getTipo();
        switch (tipodispo) {
            case 1:
                Ordenador ordenador = new Ordenador(idbuscado);
                ordenador.load();
                marca.setText(ordenador.getMarca());
                modelo.setText(ordenador.getModelo());
                activo.setSelected(ordenador.getEstado());
                eliminado.setSelected(ordenador.getBorrado());
                tipo.setValue("Ordenador");
                procesadorpc.setText(ordenador.getProcesador());
                rampc.setText(String.valueOf(ordenador.getRam()));
                almpc.setText(String.valueOf(ordenador.getTamDisco()));
                switch (ordenador.getTipoDisco()) {
                    case 0:
                        tipodisco.setValue("Mecanico");
                        break;
                    case 1:
                        tipodisco.setValue("SSD");
                        break;
                    case 2:
                        tipodisco.setValue("NVMe");
                        break;
                    case 3:
                        tipodisco.setValue("Otro");
                        break;
                }
                break;
            case 2:
                Impresora impresora = new Impresora(idbuscado);
                impresora.load();
                marca.setText(impresora.getMarca());
                modelo.setText(impresora.getModelo());
                activo.setSelected(impresora.getEstado());
                eliminado.setSelected(impresora.getBorrado());
                tipo.setValue("Impresora");
                tipoImpresora.setText(String.valueOf(impresora.getTipo()));
                scanner.setSelected(impresora.getScanner());
                color.setSelected(impresora.getColor());
                break;
            case 3:
                Smartphone smartphone = new Smartphone(idbuscado);
                smartphone.load();
                marca.setText(smartphone.getMarca());
                modelo.setText(smartphone.getModelo());
                activo.setSelected(smartphone.getEstado());
                eliminado.setSelected(smartphone.getBorrado());
                tipo.setValue("Smartphone");
                procesadormovil.setText(smartphone.getProcesador());
                ramsm.setText(String.valueOf(smartphone.getRam()));
                almsmr.setText(String.valueOf(smartphone.gettamAlmacenamiento()));
                switch (smartphone.getsistemaOperativo()) {
                    case 0:
                        sisop.setValue("Android");
                        break;
                    case 1:
                        sisop.setValue("IOS");
                        break;
                    case 2:
                        sisop.setValue("Otro");
                        break;
                }
                break;
            }
    }

    @FXML
    private void EliminarDisp() throws IOException {
        int idBorrar = Integer.parseInt(idDispo.getText());
        Dispositivo dispositivo = new Dispositivo(idBorrar);
        dispositivo.load();
        Boolean estaborrado = dispositivo.getBorrado();
        System.out.println(estaborrado);
        if (estaborrado == false) {
            dispositivo.delete();
        } else if (estaborrado) {
            dispositivo.setBorrado(false);
            dispositivo.save();
        }
        listaDisp.setText("");
        ListaDispositivos.clear();
        cargardatos();
        mostrarDispositivos();
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
