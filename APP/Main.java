import java.io.RandomAccessFile;
import java.util.ArrayList;


public class Main {
    private static final String Ruta = "../Datos/Dispositivos.dat";
    private static ArrayList<Dispositivo> ListaDispositivos = new ArrayList<Dispositivo>();
    private static int opcion = 0;
    private static boolean Fallo = false;
    private final static int tamRegistroDisp = 114;

    public static void main(String[] args) {
        Borrarpantalla();
        cargardatos();
        do {
            Borrarpantalla();
            menuPrincipal();
            SelectorOpcion();
            Borrarpantalla();
            switch (opcion) {
                case 1:
                    anadirDispositivo();
                    break;
                case 2:
                    mostrarDispositivos();
                    break;
                case 3:
                    System.out.println("Introduce el ID del dispositivo a buscar");
                    buscarDispositivo();
                    break;
                case 4:
                    System.out.println("Introduce el ID del dispositivo a borrar");
                    borrarDispositivo();
                    break;
                case 5:
                    System.out.println("Introduce el ID del dispositivo a cambiar el estado");
                    cambiarEstadoDispositivo();
                    break;
                case 6:
                    System.out.println("Introduce el ID del dispositivo a modificar");
                    modificarDispositivo();
                    break;
            }
        } while (opcion != 0);
        Borrarpantalla();
        Despedida();
    }

    /**
     * Este metodo se encarga de cargar los datos de los dispositivos
     * 
     */
    public static void cargardatos() {
        // EN PROCESO DE DESARROLLO
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

    public static void menuPrincipal() {
        System.out.print("""
                MENÚ PRINCIPAL
                --------------
                1. Añadir dispositivo
                2. Mostrar dispositivos
                3. Buscar dispositivo
                4. Borrar dispositivo
                5. Cambiar estado dispositivo
                6. Modificar dispositivo
                0. Salir
                --------------
                Introduce una opción:
                """);
    }

    /**
     * Añade un nuevo dispositivo a la lista de dispositivos.
     * Solicita al usuario los datos necesarios para crear un dispositivo,
     * ordenador o impresora, según el tipo especificado.
     */
    public static void anadirDispositivo() {
        System.out.println("Introduce la marca del dispositivo");
        String marca = System.console().readLine();
        System.out.println("Introduce el modelo del dispositivo");
        String modelo = System.console().readLine();
        System.out.println("El estado del dispositivo ha sido establecido por defecto");
        boolean estado = true;
        System.out.println("El dispositivo ha sido establecido por defecto como activo");
        boolean borrado = false;
        System.out.println("Introduce el tipo del dispositivo");
        int tipo = Integer.parseInt(System.console().readLine());
        switch (tipo) {
            case 1:
                System.out.println("Introduce la cantidad de memoria RAM");
                int ram = Integer.parseInt(System.console().readLine());
                System.out.println("Introduce el tipo de procesador");
                String procesador = System.console().readLine();
                System.out.println("Introduce el tamaño del disco");
                int tamDisco = Integer.parseInt(System.console().readLine());
                System.out.println("Introduce el tipo de disco");
                int tipoDisco = Integer.parseInt(System.console().readLine());
                Ordenador ordenador = new Ordenador(marca, modelo, estado, borrado, ram, procesador,
                        tamDisco, tipoDisco);
                if (ordenador.save() == 0) {
                    System.out.println("Ordenador añadido correctamente.");
                } else {
                    System.err.println("Error al añadir el Ordenador.");
                }
                ListaDispositivos.add(ordenador);
                break;
            case 2:
                System.out.println("Introduce el tipo de impresora");
                int tipoImpresora = Integer.parseInt(System.console().readLine());
                System.out.println("Introduce si la impresora es a color");
                boolean color = Boolean.parseBoolean(System.console().readLine());
                System.out.println("Introduce si la impresora tiene escáner");
                boolean scanner = Boolean.parseBoolean(System.console().readLine());
                Impresora impresora = new Impresora(marca, modelo, estado, borrado, tipoImpresora,
                        color,
                        scanner);
                if (impresora.save() == 0) {
                    System.out.println("Impresora añadida correctamente.");
                } else {
                    System.err.println("Error al añadir la Impresora.");
                }
                ListaDispositivos.add(impresora);
                break;
            case 3:
                System.out.println("Introduce la cantidad de memoria RAM");
                int ramSmartphone = Integer.parseInt(System.console().readLine());
                System.out.println("Introduce el tipo de procesador");
                String procesadorSmartphone = System.console().readLine();
                System.out.println("Introduce el tamaño del disco");
                int tamAlmacenamiento = Integer.parseInt(System.console().readLine());
                System.out.println("Introduce el sistema operativo");
                int sistemaOperativo = Integer.parseInt(System.console().readLine());
                Smartphone smartphone = new Smartphone(marca, modelo, estado, borrado, ramSmartphone, procesadorSmartphone,
                        tamAlmacenamiento, sistemaOperativo);
                if (smartphone.save() == 0) {
                    System.out.println("Smartphone añadido correctamente.");
                } else {
                    System.err.println("Error al añadir el Smartphone.");
                }
                ListaDispositivos.add(smartphone);
                break;
            default:
                Dispositivo dispositivo = new Dispositivo(marca, modelo, estado, tipo, borrado);
                if (dispositivo.save() == 0) {
                    System.out.println("Dispositivo añadido correctamente.");
                } else {
                    System.err.println("Error al añadir el Dispositivo.");
                }
                ListaDispositivos.add(dispositivo);
                break;
        }
        MenuPrincipal();
    }

    /**
     * Muestra los dispositivos almacenados en la lista de dispositivos.
     * 
     */
    public static void mostrarDispositivos() {
        for (int i = 0; i < ListaDispositivos.size(); i++) {
            System.out.println(ListaDispositivos.get(i).toString());
        }
        MenuPrincipal();
    }

    /**
     * Este metodo Busca un dispositivo en la lista de dispositivos.
     * 
     */
    public static void buscarDispositivo() {
        int id = Integer.parseInt(System.console().readLine());
        int result = ((Dispositivo)ListaDispositivos.get(id - 1)).load();
        if (result == 0) {
           System.out.println("Dispositivo encontrado: " + ((Dispositivo)ListaDispositivos.get(id - 1)).toString());
        } else if (result == 1) {
           System.out.println("Error: Dispositivo no encontrado.");
        } else if (result == 2) {
           System.out.println("Dispositivo encontrado pero esta borrado.");
        }
        MenuPrincipal();
    }

    /**
     * Este metodo se encarga de borrar un dispositivo de la lista de dispositivos.
     * 
     */
    public static void borrarDispositivo() {
        int id = Integer.parseInt(System.console().readLine());
        ListaDispositivos.get(id - 1).delete();
        System.out.println("Dispositivo borrado correctamente.");
        MenuPrincipal();

    }

    /**
     * Este metodo se encarga de cambiar el estado de un dispositivo de la lista de
     * dispositivos.
     * 
     */
    public static void cambiarEstadoDispositivo() {
        int id = Integer.parseInt(System.console().readLine());
        ListaDispositivos.get(id - 1).cambiarEstado();
        System.out.println("Estado del dispositivo cambiado correctamente.");
        MenuPrincipal();
    }

    /**
     * Este metodo se encarga de modificar un dispositivo de la lista de
     * dispositivos.
     * 
     */
    public static void modificarDispositivo() {
        int id = Integer.parseInt(System.console().readLine());
        System.out.println(ListaDispositivos.get(id - 1).toString());
        System.out.println("Introduce la marca del dispositivo");
        ListaDispositivos.get(id - 1).setMarca(System.console().readLine());
        System.out.println("Introduce el modelo del dispositivo");
        ListaDispositivos.get(id - 1).setModelo(System.console().readLine());
        System.out.println("Introduce el estado del dispositivo");
        ListaDispositivos.get(id - 1).setEstado(Boolean.parseBoolean(System.console().readLine()));
        System.out.println("¿Esta Borrado el dispositivo?");
        ListaDispositivos.get(id - 1).setBorrado(Boolean.parseBoolean(System.console().readLine()));
        switch (ListaDispositivos.get(id - 1).getTipo()) {
            case 1:
                System.out.println("Introduce la cantidad de memoria RAM");
                ((Ordenador) ListaDispositivos.get(id - 1)).setRam(Integer.parseInt(System.console().readLine()));
                System.out.println("Introduce el tipo de procesador");
                ((Ordenador) ListaDispositivos.get(id - 1)).setProcesador(System.console().readLine());
                System.out.println("Introduce el tamaño del disco");
                ((Ordenador) ListaDispositivos.get(id - 1)).setTamDisco(Integer.parseInt(System.console().readLine()));
                break;
            case 2:
                System.out.println("Introduce el tipo de impresora");
                ((Impresora) ListaDispositivos.get(id - 1)).setTipoImpresora(Integer.parseInt(System.console().readLine()));
                System.out.println("Introduce si la impresora es a color");
                ((Impresora) ListaDispositivos.get(id - 1)).setColor(Boolean.parseBoolean(System.console().readLine()));
                System.out.println("Introduce si la impresora tiene escáner");
                ((Impresora) ListaDispositivos.get(id - 1)).setScanner(Boolean.parseBoolean(System.console().readLine()));
                break;
            case 3:
                System.out.println("Introduce la cantidad de memoria RAM");
                ((Smartphone) ListaDispositivos.get(id - 1)).setRam(Integer.parseInt(System.console().readLine()));
                System.out.println("Introduce el tipo de procesador");
                ((Smartphone) ListaDispositivos.get(id - 1)).setProcesador(System.console().readLine());
                System.out.println("Introduce el tamaño del disco");
                ((Smartphone) ListaDispositivos.get(id - 1)).settamAlmacenamiento(Integer.parseInt(System.console().readLine()));
                System.out.println("Introduce el sistema operativo");
                ((Smartphone) ListaDispositivos.get(id - 1)).setsistemaOperativo(Integer.parseInt(System.console().readLine()));
                break;
        }
        if (ListaDispositivos.get(id - 1).save() == 0) {
            System.out.println("Dispositivo modificado correctamente.");
        } else {
            System.err.println("Error al modificar el Dispositivo.");
        }
        MenuPrincipal();
    }

    /**
     * Este metodo se encarga de verificar si la opcion es valida controlando las
     * excepciones y si la opcion esta entre las correctas.
     * 
     */
    public static void SelectorOpcion() {
        do {
            try {
                opcion = Integer.parseInt(System.console().readLine());
                if (opcion == 0 || opcion == 1 || opcion == 2 || opcion == 3 || opcion == 4 || opcion == 5
                        || opcion == 6) {
                    Fallo = false;
                } else {
                    System.out.println("Error, no has introducido una opción válida");
                    Fallo = true;
                }
            } catch (Exception e) {
                System.out.println("Debes introducir un número entero");
                Fallo = true;

            }
        } while (Fallo);
    }

    /**
     * Este metodo se encarga de mostrar un mensaje y esperar a pulsar enter
     */
    public static void MenuPrincipal() {
        System.out.println("Pulsa enter para volver al menu principal");
        System.console().readLine();
    }

    /**
     * Este metodo se encarga de borrar Pantalla
     */
    public static void Borrarpantalla() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    /**
     * Este metodo se encarga de mostrar un Mensaje de Despedida
     * 
     */
    public static void Despedida() {
        System.out.println("""
                Gracias por Confiar en SGD Robert.
                Vuelva Pronto!!!!!
                """);
    }
}
