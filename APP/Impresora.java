package App;
import java.io.RandomAccessFile;

/**
 * Clase que representa una impresora y extiende de la clase Dispositivo.
 */
public class Impresora extends Dispositivo {
    private final String Ruta = "../Datos/Impresoras.dat";
    private final int tamRegistro = 10;
    private int id_Impresora; // 4 bytes
    private int tipoImpresora; // 4 bytes
    private boolean color; // 1 byte
    private boolean scanner; // 1 byte

    /**
     * Constructor de la clase Impresora.
     * 
     * @param marca         Marca de la impresora.
     * @param modelo        Modelo de la impresora.
     * @param estado        Estado de la impresora.
     * @param tipo          Tipo de dispositivo.
     * @param borrado        Indica si el dispositivo está borrado.
     * @param foreingKey    Clave foránea.
     * @param tipoImpresora Tipo de impresora.
     * @param color         Indica si la impresora es a color.
     * @param scanner       Indica si la impresora tiene escáner.
     */
    public Impresora(String marca, String modelo, boolean estado, boolean borrado,
            int tipoImpresora, boolean color, boolean scanner) {
        
        super(marca, modelo, estado, 2, borrado);
        this.id_Impresora = ultimoIdImpresora() + 1;
        setForeingKey(id_Impresora);
        this.tipoImpresora = tipoImpresora;
        this.color = color;
        this.scanner = scanner;
    }

    /**
     * Constructor de la clase Impresora.
     * 
     * @param id Identificador de la impresora.
     */
    public Impresora(int id) {
        super(id);
        this.tipoImpresora = 0;
        this.color = false;
        this.scanner = false;
    }

    /**
     * Establece el tipo de impresora.
     * 
     * @param tipoImpresora Tipo de impresora.
     */
    public void setTipoImpresora(int tipoImpresora) {
        this.tipoImpresora = tipoImpresora;
    }

    /**
     * Establece si la impresora es a color.
     * 
     * @param color Indica si la impresora es a color.
     */
    public void setColor(boolean color) {
        this.color = color;
    }

    /**
     * Establece si la impresora tiene escáner.
     * 
     * @param scanner Indica si la impresora tiene escáner.
     */
    public void setScanner(boolean scanner) {
        this.scanner = scanner;
    }

    /**
     * Obtiene el tipo de impresora.
     * 
     * @return Tipo de impresora.
     */
    public int getTipoImpresora() {
        return tipoImpresora;
    }

    /**
     * Obtiene si la impresora es a color.
     * 
     * @return Indica si la impresora es a color.
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Obtiene si la impresora tiene escáner.
     * 
     * @return Indica si la impresora tiene escáner.
     */
    public boolean getScanner() {
        return scanner;
    }

    /**
     * Devuelve una representación en cadena de la impresora.
     * 
     * @return Cadena que representa la impresora.
     */
    @Override
    public String toString() {
        String tipoimp = "";
        String impresion= "";
        String escaneo = "";
        if (tipoImpresora == 0) {
            tipoimp = "Láser";
        } else if (tipoImpresora == 1) {
            tipoimp = "Inyección de tinta";
        } else if (tipoImpresora == 2) {
            tipoimp = "Otros";
        }
        if (color == true) {
            impresion = "Si";
        } else {
            impresion = "No";
        }
        if (scanner == true) {
            escaneo = "Si";
        } else {
            escaneo = "No";
        }
        return super.toString() + "Tipo: " + tipoimp + " , Color: " + impresion + " , Scanner Incluido: "
                + escaneo + "]" + "\n";
    }

    /**
     * Guarda la información de la impresora en un archivo.
     * 
     * @return 0 si se guarda correctamente, 1 si ocurre un error.
     */
    @Override
    public int save() {
        super.save();
        try {
            RandomAccessFile raf = new RandomAccessFile(Ruta, "rw");
            if (this.id_Impresora > ultimoIdImpresora()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id_Impresora - 1) * tamRegistro);
            }
            raf.writeInt(id_Impresora);
            raf.writeInt(tipoImpresora);
            raf.writeBoolean(color);
            raf.writeBoolean(scanner);
            raf.close();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Carga la información de una impresora desde un archivo.
     * 
     * @param id Identificador de la impresora.
     * @return 0 si se carga correctamente, 1 si ocurre un error.
     */
    @Override
    public int load() {
        super.load();
        id_Impresora = super.getForeingKey();
        int idBuscado = this.id_Impresora;
        int idAnterior = ultimoIdImpresora();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile(Ruta, "r");
                raf.seek((idBuscado - 1) * tamRegistro);
                if (raf.readInt() == idBuscado) {
                    setTipoImpresora(raf.readInt());
                    setColor(raf.readBoolean());
                    setScanner(raf.readBoolean());
                    if (getBorrado() == false) {
                        resultado = 2;
                    } else {
                        resultado = 0;
                    }
                }
                raf.close();
            } catch (Exception e) {
                resultado = 1;
            }
            return resultado;
        }
    }

    /**
     * Obtiene el último identificador utilizado.
     * 
     * @return Último identificador utilizado.
     */
    
    public int ultimoIdImpresora() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(Ruta, "rw");
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
