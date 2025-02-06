
    import java.io.RandomAccessFile;
/**
 * La clase Smartphone representa un dispositivo de tipo Smartphone.
 * Hereda de la clase Dispositivo y añade atributos específicos de un Smartphone.
 */
public class Smartphone extends Dispositivo {
    private final int tamCampo = 50;
    private final int tamRegistro = 66;
    int id_Smartphone; // 4 bytes
    private int ram; // 4 bytes
    private String procesador; // 50 bytes
    private int tamAlmacenamiento; // 4 bytes
    private int sistemaOperativo; // 4 bytes

    /**
     * Constructor de la clase Smartphone.
     * 
     * @param marca      Marca del Smartphone.
     * @param modelo     Modelo del Smartphone.
     * @param estado     Estado del Smartphone.
     * @param tipo       Tipo del Smartphone.
     * @param borrado    Indica si el Smartphone está borrado.
     * @param ram        Cantidad de memoria RAM.
     * @param procesador Tipo de procesador.
     * @param tamAlmacenamiento   Tamaño del disco.
     * @param sistemaOperativo  Sistema operativo del Smartphone.
     */
    public Smartphone(String marca, String modelo, boolean estado, boolean borrado, int ram,
            String procesador, int tamAlmacenamiento, int sistemaOperativo) {
        super(marca, modelo, estado, 3, borrado);
        id_Smartphone = ultimoIdSmartphonees() + 1;
        setForeingKey(id_Smartphone);
        this.ram = ram;
        this.procesador = procesador;
        this.tamAlmacenamiento = tamAlmacenamiento;
        this.sistemaOperativo = sistemaOperativo;
    }

    /**
     * Constructor de la clase Smartphone con ID.
     * 
     * @param id ID del Smartphone.
     */
    public Smartphone(int id) {
        super(id);
        this.ram = 0;
        this.procesador = "";
        this.tamAlmacenamiento = 0;
        this.sistemaOperativo = 0;
    }

    /**
     * Establece la cantidad de memoria RAM.
     * 
     * @param ram Cantidad de memoria RAM.
     */
    public void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * Establece el tipo de procesador.
     * 
     * @param procesador Tipo de procesador.
     */
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    /**
     * Establece el tamaño del disco.
     * 
     * @param tamAlmacenamiento Tamaño del disco.
     */
    public void settamAlmacenamiento(int tamAlmacenamiento) {
        this.tamAlmacenamiento = tamAlmacenamiento;

    }

    /**
     * Establece el tipo de disco.
     * 
     * @param sistemaOperativo Tipo de disco.
     */
    public void setsistemaOperativo(int sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    /**
     * Obtiene la cantidad de memoria RAM.
     * 
     * @return Cantidad de memoria RAM.
     */
    public int getRam() {
        return ram;
    }

    /**
     * Obtiene el tipo de procesador.
     * 
     * @return Tipo de procesador.
     */
    public String getProcesador() {
        return procesador;
    }

    /**
     * Obtiene el tamaño del disco.
     * 
     * @return Tamaño del disco.
     */
    public int gettamAlmacenamiento() {
        return tamAlmacenamiento;
    }

    /**
     * Obtiene el tipo de disco.
     * 
     * @return Tipo de disco.
     */
    public int getsistemaOperativo() {
        return sistemaOperativo;
    }

    /**
     * Devuelve una representación en cadena del objeto Smartphone.
     * 
     * @return Cadena con la información del Smartphone.
     */
    @Override
    public String toString() {
        String Almacenamiento = "";
        String so = "";
        String tamañoDisco = "";
        if (sistemaOperativo == 0) {
            so = "Android";
        } else if (sistemaOperativo == 1) {
            so = "IOS";
        } else if (sistemaOperativo == 2) {
            so = "Otro";
        }
        if (tamAlmacenamiento < 1024) {
            tamañoDisco = tamAlmacenamiento + " GB]" + "\n";
        } else {
            tamañoDisco = tamAlmacenamiento / 1024 + " TB]" + "\n";
        }
        Almacenamiento = ", Almacenamiento: " + tamañoDisco;
        return super.toString() + "Sistema Operativo: " + so + " Ram: " + ram + " GB , Procesador: " + procesador + Almacenamiento;
    }

    /**
     * Guarda la información del Smartphone en un archivo.
     * 
     * @return 0 si se guarda correctamente, 1 en caso de error.
     */
    @Override
    public int save() {
        super.save();
        try {
            RandomAccessFile raf = new RandomAccessFile("/Practica-SG-Dispositivos/Ficheros de Datos/Smartphonees.dat", "rw");
            if (this.id_Smartphone > ultimoIdSmartphonees()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id_Smartphone - 1) * tamRegistro);
            }
            raf.writeInt(id_Smartphone);
            raf.writeInt(ram);
            limpiarCampo(raf, procesador);
            raf.writeInt(tamAlmacenamiento);
            raf.writeInt(sistemaOperativo);
            raf.close();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Carga la información del Smartphone desde un archivo.
     * 
     * @param idBuscado ID del Smartphone a buscar.
     * @return 0 si se carga correctamente, 1 si no se encuentra, 2 si no está
     *         borrado.
     */
    @Override
    public int load() {

        super.load();
        id_Smartphone = super.getForeingKey();
        int idBuscado = this.id_Smartphone;
        int idAnterior = ultimoIdSmartphonees();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile("/Practica-SG-Dispositivos/Ficheros de Datos/Smartphonees.dat", "r");
                raf.seek((idBuscado - 1) * tamRegistro);
                if (raf.readInt() == idBuscado) {
                    setRam(raf.readInt());
                    long inicio = raf.getFilePointer();
                    setProcesador(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    settamAlmacenamiento(raf.readInt());
                    setsistemaOperativo(raf.readInt());
                    if (getBorrado() == false) {
                        resultado = 2;
                    } else {
                        resultado = 0;
                    }
                }
                raf.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error al abrir el archivo 3");
            }
            return resultado;
        }
    }

    /**
     * Limpia un campo en el archivo, rellenando con ceros si es necesario.
     * 
     * @param raf   Archivo de acceso aleatorio.
     * @param Campo Campo a limpiar.
     */
    @Override
    public void limpiarCampo(RandomAccessFile raf, String Campo) {
        try {
            long inicio = raf.getFilePointer();
            raf.writeUTF(Campo);
            long end = raf.getFilePointer();
            for (int i = 0; i < tamCampo - (end - inicio); i++) {
                raf.writeByte(0);
            }

        } catch (Exception e) {
            System.out.println("Error al abrir el documento");
        }
    }

    /**
     * Obtiene el último ID registrado en el archivo.
     * 
     * @return Último ID registrado.
     */

    public int ultimoIdSmartphonees() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("/Practica-SG-Dispositivos/Ficheros de Datos/Smartphonees.dat", "rw");
            long tam = raf.length();
            if (tam > 0) {
                raf.seek(tam - tamRegistro);
                resultado = raf.readInt();
            }
            raf.close();
        } catch (Exception e) {
            System.err.println("Error al abrir el archivo 4");
        }
        return resultado;
    }
}

