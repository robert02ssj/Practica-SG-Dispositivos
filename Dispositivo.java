import java.io.RandomAccessFile;

/**
 * Clase que representa un dispositivo con varios atributos y métodos para
 * manipular su información.
 */
public class Dispositivo {
    private final int tamRegistro = 114;
    private final int tamCampo = 50;
    private int id; // 4 bytes
    private String marca; // 50 bytes
    private String modelo; // 50 bytes
    private boolean estado; // 1 byte
    private int tipo = 0; // 4 bytes
    private boolean activo; // 1 byte
    private int foreingKey = 0; // 4 bytes

    /**
     * Constructor que inicializa un dispositivo con los valores proporcionados.
     * 
     * @param marca      La marca del dispositivo.
     * @param modelo     El modelo del dispositivo.
     * @param estado     El estado del dispositivo.
     * @param tipo       El tipo de dispositivo.
     * @param activo     Indica si el dispositivo está activo.
     * @param foreingKey La clave foránea asociada al dispositivo.
     */
    public Dispositivo(String marca, String modelo, boolean estado, int tipo, boolean activo, int foreingKey) {
        this.id = ultimoId() + 1;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.tipo = tipo;
        this.activo = activo;
        this.foreingKey = foreingKey;

    }

    /**
     * Constructor que inicializa un dispositivo con un ID específico.
     * 
     * @param id El ID del dispositivo.
     */
    public Dispositivo(int id) {
        this.id = id;
        this.marca = "";
        this.modelo = "";
        this.estado = true;
    }

    /**
     * Establece la marca del dispositivo.
     * 
     * @param marca La nueva marca del dispositivo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Establece el modelo del dispositivo.
     * 
     * @param modelo El nuevo modelo del dispositivo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Establece el estado del dispositivo.
     * 
     * @param estado El nuevo estado del dispositivo.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Establece el tipo del dispositivo.
     * 
     * @param tipo El nuevo tipo del dispositivo.
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece si el dispositivo está activo.
     * 
     * @param activo El nuevo estado de actividad del dispositivo.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Establece la clave foránea del dispositivo.
     * 
     * @param foreingKey La nueva clave foránea del dispositivo.
     */
    public void setForeingKey(int foreingKey) {
        this.foreingKey = foreingKey;
    }

    /**
     * Obtiene la marca del dispositivo.
     * 
     * @return La marca del dispositivo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Obtiene el modelo del dispositivo.
     * 
     * @return El modelo del dispositivo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtiene el estado del dispositivo.
     * 
     * @return El estado del dispositivo.
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Obtiene el tipo del dispositivo.
     * 
     * @return El tipo del dispositivo.
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Obtiene si el dispositivo está activo.
     * 
     * @return true si el dispositivo está activo, false en caso contrario.
     */
    public boolean getActivo() {
        return activo;
    }

    /**
     * Obtiene la clave foránea del dispositivo.
     * 
     * @return La clave foránea del dispositivo.
     */
    public int getForeingKey() {
        return foreingKey;
    }

    public String toString() {
        return "id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + ", tipo=" + tipo
                + ", activo=" + activo + ", foreingKey=" + foreingKey + '}';
    }

    /**
     * Guarda la información del dispositivo en un archivo.
     * 
     * @return 0 si se guarda correctamente, 1 si ocurre un error.
     */
    public int save() {
        try {
            RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "rw");
            if (this.id > ultimoId()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id - 1) * tamRegistro);
            }
            raf.writeInt(id);
            limpiarCampo(raf, marca);
            limpiarCampo(raf, modelo);
            raf.writeBoolean(estado);
            raf.writeInt(tipo);
            raf.writeBoolean(activo);
            raf.writeInt(foreingKey);
            raf.close();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Carga la información de un dispositivo desde un archivo.
     * 
     * @param idBuscado El ID del dispositivo a buscar.
     * @return 0 si se carga correctamente, 1 si el ID no existe, 2 si el dispositivo no está activo.
     */
    public int load(int idBuscado) {
        int idAnterior = ultimoId();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            int prueba = idBuscado - 1;
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "r");
                raf.seek(prueba * tamRegistro);
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
                    setForeingKey(raf.readInt());
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
     * Marca el dispositivo como inactivo y guarda los cambios en el archivo.
     */
    public void delete() {
        setActivo(false);
        save();
    }

    /**
     * Limpia un campo en el archivo, rellenando con ceros hasta alcanzar el tamaño del campo.
     * 
     * @param raf   El archivo de acceso aleatorio.
     * @param Campo El valor del campo a limpiar.
     */
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
     * Obtiene el último ID utilizado en el archivo.
     * 
     * @return El último ID utilizado.
     */
    public int ultimoId() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "r");
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
