import java.util.ArrayList;

public class Main {
    private ArrayList<Dispositivo> ListaDispositivos = new ArrayList<Dispositivo>();
    private static int opcion = 0;
    private static boolean Fallo = false;

    public static void main(String[] args) {
        // cargardatos();
        do {
            Borrarpantalla();
            menuPrincipal();
            SelectorOpcion();
            switch (opcion) {
                case 1:
                    //anadirDispositivo();
                    break;
                case 2:
                    // mostrarDispositivos();
                    break;
                case 3:
                    // buscarDispositivo();
                    break;
                case 4:
                    // borrarDispositivo();
                    break;
                case 5:
                    // cambiarEstadoDispositivo();
                    break;
                case 6:
                    // modificarDispositivo();
                    break;
            }
        } while (opcion != 0);
        Borrarpantalla();
        Despedida();
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
