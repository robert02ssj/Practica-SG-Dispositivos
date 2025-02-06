package App;
import java.io.RandomAccessFile;

/**
 * Clase que representa un dispositivo con varios atributos y métodos para
 * manipular su información.
 */
public class Dispositivo {
    private final int tamRegistro = 114;
    private final int tamCampo = 50;
    private int id; // 4 bytes
    private int tipo = 0; // 4 bytes
    protected int foreingKey = -1; // 4 bytes
    private String marca; // 50 bytes
    private String modelo; // 50 bytes
    private boolean estado; // 1 byte
    private boolean borrado; // 1 byte

    /**
     * Constructor que inicializa un dispositivo con los valores proporcionados.
     * 
     * @param marca   La marca del dispositivo.
     * @param modelo  El modelo del dispositivo.
     * @param estado  El estado del dispositivo.
     * @param tipo    El tipo de dispositivo.
     * @param borrado Indica si el dispositivo está borrado.
     *
     */
    public Dispositivo(String marca, String modelo, boolean estado, int tipo, boolean borrado) {
        this.id = ultimoIdDisp() + 1;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.tipo = tipo;
        this.borrado = borrado;
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
        this.estado = false;
        this.tipo = 0;
        this.borrado = false;
        this.foreingKey = 0;
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
     * Establece si el dispositivo está borrado.
     * 
     * @param borrado El nuevo estado de actividad del dispositivo.
     */
    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
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
     * Obtiene el ID del dispositivo.
     * 
     * @return El ID del dispositivo.
     */
    public int getId() {
        return id;
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
     * Obtiene si el dispositivo está borrado.
     * 
     * @return true si el dispositivo está borrado, false en caso contrario.
     */
    public boolean getBorrado() {
        return borrado;
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
        String funciona = "";
        if (estado == true) {
            funciona = "Funciona";
        } else {
            funciona = "No Funciona";
        }
        String tipodispo = "";
        if (tipo == 0) {
            tipodispo = "Dispositivo";
        } else if (tipo == 1) {
            tipodispo = "Ordenador";
        } else if (tipo == 2) {
            tipodispo = "Impresora";
        } else if (tipo == 3) {
            tipodispo = "Smartphone";
        }
        String eliminado = "";
        if (borrado) {
            eliminado = "Eliminado";
        } else {
            eliminado = "No eliminado";
        }
        String claveAjena = "";
        if (foreingKey == -1) {
            claveAjena = "]" + "\n";
        } else {
            claveAjena = " , Clave Ajena: " + foreingKey + " , ";
        }

        return "[ID: " + id + " , Marca: " + marca + " , Modelo: " + modelo + " , Estado: " + funciona + ", Tipo: "
                + tipodispo
                + ", " + eliminado + claveAjena;
    }

    /**
     * Guarda la información del dispositivo en un archivo.
     * 
     * @return 0 si se guarda correctamente, 1 si ocurre un error.
     */
    public int save() {
        try {
            RandomAccessFile raf = new RandomAccessFile("Dispositivos.dat", "rw");
            if (this.id > ultimoIdDisp()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id - 1) * tamRegistro);
            }
            raf.writeInt(id);
            raf.writeInt(tipo);
            raf.writeInt(foreingKey);
            limpiarCampo(raf, marca);
            limpiarCampo(raf, modelo);
            raf.writeBoolean(estado);
            raf.writeBoolean(borrado);
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
     * @return 0 si se carga correctamente, 1 si el ID no existe, 2 si el
     *         dispositivo no está borrado.
     */
    public int load() {
        int idBuscado = this.id;
        int idAnterior = ultimoIdDisp();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            int prueba = idBuscado - 1;
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile("Dispositivos.dat", "r");
                raf.seek(prueba * tamRegistro);
                if (raf.readInt() == idBuscado) {
                    setTipo(raf.readInt());
                    setForeingKey(raf.readInt());
                    long inicio = raf.getFilePointer();
                    setMarca(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    inicio = raf.getFilePointer();
                    setModelo(raf.readUTF());
                    raf.seek(inicio + tamCampo);
                    setEstado(raf.readBoolean());
                    setBorrado(raf.readBoolean());
                    if (borrado == false) {
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
     * Marca el dispositivo como inborrado y guarda los cambios en el archivo.
     */
    public void delete() {
        setBorrado(true);
        save();
    }

    /**
     * Cambia el estado del dispositivo Alternando el mismo y guarda los cambios en
     * el archivo.
     */
    public void cambiarEstado() {
        if (estado == true) {
            setEstado(false);
        } else {
            setEstado(true);
        }
        save();
    }

    /**
     * Limpia un campo en el archivo, rellenando con ceros hasta alcanzar el tamaño
     * del campo.
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
    public int ultimoIdDisp() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Dispositivos.dat", "rw");
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
