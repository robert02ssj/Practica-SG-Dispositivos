package com.ssj;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * Controlador de la interfaz principal de la aplicación.
 */
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
    private ChoiceBox<String> tipoImpresora;

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
                    if(borradoDisp == false ){ListaDispositivos.add(ordenador);}
                    break;
                case "Impresora":
                    int tipoImpr = 0;
                    switch (tipoImpresora.getValue().toString()) {
                        case "Láser":
                            tipoImpr = 0;
                            break;
                        case "Inyección de tinta":
                            tipoImpr = 1;
                            break;
                        case "Otros":
                            tipoImpr = 2;
                            break;
                    }
                    boolean escaner = scanner.isSelected();
                    boolean color = this.color.isSelected();
                    Impresora impresora = new Impresora(marcaDisp, modeloDisp, estadoDisp, borradoDisp, tipoImpr,
                            escaner, color);
                    impresora.save();
                    if(borradoDisp == false ){ListaDispositivos.add(impresora);}
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
                    if(borradoDisp == false ){ListaDispositivos.add(smartphone);}
                    break;
                default:
                    Dispositivo dispositivo = new Dispositivo(marcaDisp, modeloDisp, estadoDisp, 0, borradoDisp);
                    dispositivo.save();
                    if(borradoDisp == false ){ListaDispositivos.add(dispositivo);}
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
        int idbuscado = Integer.parseInt(idBusc.getText()) - 1;
        ListaDispositivos.get(idbuscado);
        int tipodispo = ListaDispositivos.get(idbuscado).getTipo();
        marca.setText(ListaDispositivos.get(idbuscado).getMarca());
        modelo.setText(ListaDispositivos.get(idbuscado).getModelo());
        activo.setSelected(ListaDispositivos.get(idbuscado).getEstado());
        eliminado.setSelected(ListaDispositivos.get(idbuscado).getBorrado());
        switch (tipodispo) {
            case 0:
                tipo.setValue("Dispositivo");
                break;
            case 1:
                tipo.setValue("Ordenador");
                procesadorpc.setText(((Ordenador) ListaDispositivos.get(idbuscado)).getProcesador());
                rampc.setText(String.valueOf(((Ordenador) ListaDispositivos.get(idbuscado)).getRam()));
                almpc.setText(String.valueOf(((Ordenador) ListaDispositivos.get(idbuscado)).getTamDisco()));
                switch (((Ordenador) ListaDispositivos.get(idbuscado)).getTipoDisco()) {
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
                tipo.setValue("Impresora");
                switch (((Impresora) ListaDispositivos.get(idbuscado)).getTipoImpresora()) {
                    case 0:
                        tipoImpresora.setValue("Láser");
                        break;
                    case 1:
                        tipoImpresora.setValue("Inyección de tinta");
                        break;
                    case 2:
                        tipoImpresora.setValue("Otros");
                        break;
                }
                scanner.setSelected(((Impresora) ListaDispositivos.get(idbuscado)).getScanner());
                color.setSelected(((Impresora) ListaDispositivos.get(idbuscado)).getColor());
                break;
            case 3:
                tipo.setValue("Smartphone");
                procesadormovil.setText(((Smartphone) ListaDispositivos.get(idbuscado)).getProcesador());
                ramsm.setText(String.valueOf(((Smartphone) ListaDispositivos.get(idbuscado)).getRam()));
                almsmr.setText(String.valueOf(((Smartphone) ListaDispositivos.get(idbuscado)).gettamAlmacenamiento()));
                switch (((Smartphone) ListaDispositivos.get(idbuscado)).getsistemaOperativo()) {
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
    private void AceptarMod() throws IOException {
        if (idBusc.getText().length() > 0) {
            int idbuscado = Integer.parseInt(idBusc.getText()) - 1;
            ListaDispositivos.get(idbuscado).setMarca(marca.getText());
            ListaDispositivos.get(idbuscado).setModelo(modelo.getText());
            if (activo.isSelected()) {
                ListaDispositivos.get(idbuscado).setEstado(true);
            } else {
                ListaDispositivos.get(idbuscado).setEstado(false);

            }
            if (eliminado.isSelected()) {
                ListaDispositivos.get(idbuscado).setBorrado(true);
            } else {
                ListaDispositivos.get(idbuscado).setBorrado(false);
            }
            switch (tipo.getValue()) {
                case "Ordenador":
                    ((Ordenador) ListaDispositivos.get(idbuscado)).setProcesador(procesadorpc.getText());
                    ((Ordenador) ListaDispositivos.get(idbuscado)).setRam(Integer.parseInt(rampc.getText()));
                    ((Ordenador) ListaDispositivos.get(idbuscado)).setTamDisco(Integer.parseInt(almpc.getText()));
                    switch (tipodisco.getValue()) {
                        case "Mecanico":
                            ((Ordenador) ListaDispositivos.get(idbuscado)).setTipoDisco(0);
                            break;
                        case "SSD":
                            ((Ordenador) ListaDispositivos.get(idbuscado)).setTipoDisco(1);
                            break;
                        case "NVMe":
                            ((Ordenador) ListaDispositivos.get(idbuscado)).setTipoDisco(2);
                            break;
                        case "Otro":
                            ((Ordenador) ListaDispositivos.get(idbuscado)).setTipoDisco(3);
                            break;
                    }
                    ((Ordenador) ListaDispositivos.get(idbuscado)).save();
                    break;
                case "Impresora":
                switch (tipoImpresora.getValue()) {
                    case "Laser":
                        ((Impresora) ListaDispositivos.get(idbuscado)).setTipoImpresora(0);
                        break;
                    case "Inyección de tinta":
                        ((Impresora) ListaDispositivos.get(idbuscado)).setTipoImpresora(1);
                        break;
                    case "Otros":
                        ((Impresora) ListaDispositivos.get(idbuscado)).setTipoImpresora(2);
                        break;
                }
                    if (scanner.isSelected()) {
                        ((Impresora) ListaDispositivos.get(idbuscado)).setScanner(true);
                    } else {
                        ((Impresora) ListaDispositivos.get(idbuscado)).setScanner(false);
                    }
                    if (color.isSelected()) {
                        ((Impresora) ListaDispositivos.get(idbuscado)).setColor(true);
                    } else {
                        ((Impresora) ListaDispositivos.get(idbuscado)).setColor(false);
                    }
                    ((Impresora) ListaDispositivos.get(idbuscado)).save();
                    break;
                case "Smartphone":
                    ((Smartphone) ListaDispositivos.get(idbuscado)).setProcesador(procesadormovil.getText());
                    ((Smartphone) ListaDispositivos.get(idbuscado)).setRam(Integer.parseInt(ramsm.getText()));
                    ((Smartphone) ListaDispositivos.get(idbuscado))
                            .settamAlmacenamiento(Integer.parseInt(almsmr.getText()));
                    switch (sisop.getValue()) {
                        case "Android":
                            ((Smartphone) ListaDispositivos.get(idbuscado)).setsistemaOperativo(0);
                            break;
                        case "IOS":
                            ((Smartphone) ListaDispositivos.get(idbuscado)).setsistemaOperativo(1);
                            break;
                        case "Otro":
                            ((Smartphone) ListaDispositivos.get(idbuscado)).setsistemaOperativo(2);
                            break;
                    }
                    ((Smartphone) ListaDispositivos.get(idbuscado)).save();
                    break;
                case "Dispositivo":
                    ListaDispositivos.get(idbuscado).save();
                    break;
            }
        }
        App.setRoot("primary");
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
        boolean borrado = false;
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
                            borrado = ordenador.getBorrado();
                            if(borrado == false ){ListaDispositivos.add(ordenador);}
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de ordenador");
                        }
                        break;
                    case 2:
                        try {
                            Impresora impresora = new Impresora(id);
                            impresora.load();
                            borrado = impresora.getBorrado();
                            if(borrado == false ){ListaDispositivos.add(impresora);}
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de impresora");
                        }
                        break;
                    case 3:
                        try {
                            Smartphone smartphone = new Smartphone(id);
                            smartphone.load();
                            borrado = smartphone.getBorrado();
                            if(borrado == false ){ListaDispositivos.add(smartphone);}
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de smartphone");
                        }
                        break;
                    default:
                        Dispositivo dispositivo = new Dispositivo(id);
                        dispositivo.load();
                        borrado = dispositivo.getBorrado();
                        if(borrado == false ){ListaDispositivos.add(dispositivo);}
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
