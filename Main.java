import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Dispositivo> ListaDispositivos = new ArrayList<Dispositivo>();
    private static int opcion = 0;
    private static boolean Fallo = false;

    public static void main(String[] args) {
        cargardatos();
        do {
            menuPrincipal();
            SelectorOpcion();
            switch (opcion) {
                case 1:
                    anadirDispositivo();
                    break;
                case 2:
                    mostrarDispositivos();
                    menuPrincipal();
                    break;
                case 3:
                    buscarDispositivo();
                    break;
                case 4:
                    borrarDispositivo();
                    break;
                case 5:
                    cambiarEstadoDispositivo();
                    break;
                case 6:
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
            RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "rw");
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                long inicio = raf.getFilePointer();
                String marca = raf.readUTF();
                raf.seek(inicio + 50);
                inicio = raf.getFilePointer();
                String modelo = raf.readUTF();
                raf.seek(inicio + 50);
                boolean estado = raf.readBoolean();
                boolean activo = raf.readBoolean();
                int tipo = raf.readInt();
                switch (tipo) {
                    case 1:
                        try {
                            RandomAccessFile raf2 = new RandomAccessFile("Ordenadores.dat", "rw");
                            int ram = raf.readInt();
                            inicio = raf.getFilePointer();
                            String procesador = raf.readUTF();
                            raf.seek(inicio + 50);
                            int tamDisco = raf.readInt();
                            int tipoDisco = raf.readInt();
                            Ordenador ordenador = new Ordenador(marca, modelo, estado, activo, ram, procesador,
                                    tamDisco, tipoDisco);
                            ListaDispositivos.add(ordenador);
                            raf2.close();
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de ordenador");
                        }
                        break;
                    case 2:
                        try {
                            RandomAccessFile raf3 = new RandomAccessFile("Impresoras.dat", "rw");
                            int tipoImpresora = raf.readInt();
                            boolean color = raf.readBoolean();
                            boolean scanner = raf.readBoolean();
                            Impresora impresora = new Impresora(marca, modelo, estado, activo, tipoImpresora,
                                    color,
                                    scanner);
                            ListaDispositivos.add(impresora);
                            raf3.close();
                        } catch (Exception e) {
                            System.out.println("Error al cargar los datos de impresora");
                        }

                        break;
                    default:
                        Dispositivo dispositivo = new Dispositivo(marca, modelo, estado, tipo, activo);
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
        System.out.println("Introduce el estado del dispositivo");
        boolean estado = true;
        System.out.println("Introduce si el dispositivo está activo");
        boolean activo = true;
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
                Ordenador ordenador = new Ordenador(marca, modelo, estado, activo, ram, procesador,
                        tamDisco, tipoDisco);
                System.out.println(ordenador.save());
                ListaDispositivos.add(ordenador);
                break;
            case 2:
                System.out.println("Introduce el tipo de impresora");
                int tipoImpresora = Integer.parseInt(System.console().readLine());
                System.out.println("Introduce si la impresora es a color");
                boolean color = Boolean.parseBoolean(System.console().readLine());
                System.out.println("Introduce si la impresora tiene escáner");
                boolean scanner = Boolean.parseBoolean(System.console().readLine());
                Impresora impresora = new Impresora(marca, modelo, estado, activo, tipoImpresora,
                        color,
                        scanner);
                System.out.println(impresora.save());
                ListaDispositivos.add(impresora);
                break;
            default:
                Dispositivo dispositivo = new Dispositivo(marca, modelo, estado, tipo, activo);
                ListaDispositivos.add(dispositivo);
                System.out.println(dispositivo.save());
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
        System.out.println("Introduce el ID del dispositivo a buscar");
        int id = Integer.parseInt(System.console().readLine());
        ListaDispositivos.get(id).toString();
        MenuPrincipal();
    }

    /**
     * Este metodo se encarga de borrar un dispositivo de la lista de dispositivos.
     * 
     */
    public static void borrarDispositivo() {
        System.out.println("Introduce el ID del dispositivo a borrar");
        int id = Integer.parseInt(System.console().readLine());
        ListaDispositivos.get(id).delete();
        MenuPrincipal();

    }

    /**
     * Este metodo se encarga de cambiar el estado de un dispositivo de la lista de
     * dispositivos.
     * 
     */
    public static void cambiarEstadoDispositivo() {
        System.out.println("Introduce el ID del dispositivo a cambiar el estado");
        int id = Integer.parseInt(System.console().readLine());
        ListaDispositivos.get(id - 1).cambiarEstado();
        MenuPrincipal();
    }

    /**
     * Este metodo se encarga de modificar un dispositivo de la lista de
     * dispositivos.
     * 
     */
    public static void modificarDispositivo() {
        System.out.println("Introduce el ID del dispositivo a modificar");
        int id = Integer.parseInt(System.console().readLine());
        ListaDispositivos.get(id - 1).toString();
        anadirDispositivo();
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
