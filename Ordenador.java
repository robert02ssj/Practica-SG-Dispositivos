import java.io.RandomAccessFile;

/**
 * La clase Ordenador representa un dispositivo de tipo ordenador.
 * Hereda de la clase Dispositivo y añade atributos específicos de un ordenador.
 */
public class Ordenador extends Dispositivo {
    private final int tamCampo = 50;
    private final int tamRegistro = 66;
    int id_Ordenador; // 4 bytes
    private int ram; // 4 bytes
    private String procesador; // 50 bytes
    private int tamDisco; // 4 bytes
    private int tipoDisco; // 4 bytes

    /**
     * Constructor de la clase Ordenador.
     * 
     * @param marca      Marca del ordenador.
     * @param modelo     Modelo del ordenador.
     * @param estado     Estado del ordenador.
     * @param tipo       Tipo del ordenador.
     * @param borrado    Indica si el ordenador está borrado.
     * @param ram        Cantidad de memoria RAM.
     * @param procesador Tipo de procesador.
     * @param tamDisco   Tamaño del disco.
     * @param tipoDisco  Tipo de disco.
     */
    public Ordenador(String marca, String modelo, boolean estado, boolean borrado, int ram,
            String procesador, int tamDisco, int tipoDisco) {
        super(marca, modelo, estado, 1, borrado);
        id_Ordenador = ultimoIdOrdenadores() + 1;
        setForeingKey(id_Ordenador);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = tipoDisco;
    }

    /**
     * Constructor de la clase Ordenador con ID.
     * 
     * @param id ID del ordenador.
     */
    public Ordenador(int id) {
        super(id);
        this.ram = 0;
        this.procesador = "";
        this.tamDisco = 0;
        this.tipoDisco = 0;
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
     * @param tamDisco Tamaño del disco.
     */
    public void setTamDisco(int tamDisco) {
        this.tamDisco = tamDisco;

    }

    /**
     * Establece el tipo de disco.
     * 
     * @param tipoDisco Tipo de disco.
     */
    public void setTipoDisco(int tipoDisco) {
        this.tipoDisco = tipoDisco;
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
    public int getTamDisco() {
        return tamDisco;
    }

    /**
     * Obtiene el tipo de disco.
     * 
     * @return Tipo de disco.
     */
    public int getTipoDisco() {
        return tipoDisco;
    }

    /**
     * Devuelve una representación en cadena del objeto Ordenador.
     * 
     * @return Cadena con la información del ordenador.
     */
    @Override
    public String toString() {
        String Almacenamiento = "";
        String discoduro = "";
        String tamañoDisco = "";
        if (tipoDisco == 0) {
            discoduro = "Mecánico";
        } else if (tipoDisco == 1) {
            discoduro = "SSD";
        } else if (tipoDisco == 2) {
            discoduro = "NVMe";
        } else if (tipoDisco == 3) {
            discoduro = "Otro";
        }
        if (tamDisco < 1024) {
            tamañoDisco = tamDisco + " GB]" + "\n";
        } else {
            tamañoDisco = tamDisco / 1024 + " TB]" + "\n";
        }
        Almacenamiento = ", Almacenamiento: " + discoduro + " " + tamañoDisco;
        return super.toString() + "Ram: " + ram + "GB , Procesador: " + procesador + Almacenamiento;
    }

    /**
     * Guarda la información del ordenador en un archivo.
     * 
     * @return 0 si se guarda correctamente, 1 en caso de error.
     */
    @Override
    public int save() {
        super.save();
        try {
            RandomAccessFile raf = new RandomAccessFile("Ordenadores.dat", "rw");
            if (this.id_Ordenador > ultimoIdOrdenadores()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id_Ordenador - 1) * tamRegistro);
            }
            raf.writeInt(id_Ordenador);
            raf.writeInt(ram);
            limpiarCampo(raf, procesador);
            raf.writeInt(tamDisco);
            raf.writeInt(tipoDisco);
            raf.close();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Carga la información del ordenador desde un archivo.
     * 
     * @param idBuscado ID del ordenador a buscar.
     * @return 0 si se carga correctamente, 1 si no se encuentra, 2 si no está
     *         borrado.
     */
    @Override
    public int load() {

        super.load();
        id_Ordenador = super.getForeingKey();
        int idBuscado = this.id_Ordenador;
        int idAnterior = ultimoIdOrdenadores();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile("Ordenadores.dat", "r");
                raf.seek((idBuscado - 1) * tamRegistro);
                if (raf.readInt() == idBuscado) {
                    setRam(raf.readInt());
                    long inicio = raf.getFilePointer();
                    setProcesador(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    setTamDisco(raf.readInt());
                    setTipoDisco(raf.readInt());
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

    public int ultimoIdOrdenadores() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Ordenadores.dat", "rw");
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
