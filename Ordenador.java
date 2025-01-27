import java.io.RandomAccessFile;

/**
 * La clase Ordenador representa un dispositivo de tipo ordenador.
 * Hereda de la clase Dispositivo y añade atributos específicos de un ordenador.
 */
public class Ordenador extends Dispositivo {
    final int tamCampo = 50;
    final int tamRegistro = 176;

    private int id; // 4 bytes
    int id_Ordenador; // 4 bytes
    private String marca; // 50 bytes
    private String modelo; // 50 bytes
    private boolean estado; // 1 byte
    private int tipo = 1; // 4 bytes
    private boolean activo; // 1 byte
    private int ram; // 4 bytes
    private String procesador; // 50 bytes
    private int tamDisco; // 4 bytes
    private int tipoDisco; // 4 bytes

    /**
     * Constructor de la clase Ordenador.
     * 
     * @param marca       Marca del ordenador.
     * @param modelo      Modelo del ordenador.
     * @param estado      Estado del ordenador.
     * @param tipo        Tipo del ordenador.
     * @param activo      Indica si el ordenador está activo.
     * @param foreingKey  Clave foránea.
     * @param ram         Cantidad de memoria RAM.
     * @param procesador  Tipo de procesador.
     * @param tamDisco    Tamaño del disco.
     * @param tipoDisco   Tipo de disco.
     */
    public Ordenador(String marca, String modelo, boolean estado, int tipo, boolean activo, int foreingKey, int ram,
            String procesador, int tamDisco, int tipoDisco) {
        
        super(marca, modelo, estado, tipo, activo, foreingKey);
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
        return "Ordenador [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + ", tipo="
                + tipo + ", activo=" + activo + ", ram=" + ram + ", procesador=" + procesador + ", tamDisco=" + tamDisco
                + ", tipoDisco=" + tipoDisco + "]";
    }

    /**
     * Guarda la información del ordenador en un archivo.
     * 
     * @return 0 si se guarda correctamente, 1 en caso de error.
     */
    @Override
    public int save() {
        try {
            RandomAccessFile raf = new RandomAccessFile("Ordenadores.dat", "rw");
            if (this.id > ultimoId()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id - 1) * tamRegistro);
            }
            raf.writeInt(id);
            raf.writeInt(id_Ordenador);
            limpiarCampo(raf, marca);
            limpiarCampo(raf, modelo);
            raf.writeBoolean(estado);
            raf.writeInt(tipo);
            raf.writeBoolean(activo);
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
     * @return 0 si se carga correctamente, 1 si no se encuentra, 2 si no está activo.
     */
    @Override
    public int load(int idBuscado) {
        int idAnterior = ultimoId();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            idBuscado = idBuscado - 1;
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile("Ordenadores.dat", "r");
                raf.seek(idBuscado * tamRegistro);
                if (raf.readInt() == idBuscado) {
                    System.out.println("Encontrado el id");
                    long inicio = raf.getFilePointer();
                    setMarca(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    inicio = raf.getFilePointer();
                    setModelo(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    setEstado(raf.readBoolean());
                    setTipo(raf.readInt());
                    setActivo(raf.readBoolean());
                    setRam(raf.readInt());
                    inicio = raf.getFilePointer();
                    setProcesador(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    setTamDisco(raf.readInt());
                    setTipoDisco(raf.readInt());
                    if (activo == false) {
                        resultado = 2;
                    } else {
                        resultado = 0;
                    }
                }
                raf.close();
            } catch (Exception e) {
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
    @Override
    public int ultimoId() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Ordenadores.dat", "r");
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
