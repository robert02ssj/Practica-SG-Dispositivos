import java.io.RandomAccessFile;

public class Impresora extends Dispositivo {
    private final int tamRegistro = 120;
    private final int tamCampo = 50;
    private int id; // 4 bytes
    private int id_Impresora; // 4 bytes
    private String marca; // 50 bytes
    private String modelo; // 50 bytes
    private boolean estado; // 1 byte
    private int tipo = 2; // 4 bytes
    private boolean activo; // 1 byte
    private int tipoImpresora; // 4 bytes
    private boolean color; // 1 byte
    private boolean scanner; // 1 byte

    public Impresora(String marca, String modelo, boolean estado, int tipo, boolean activo, int foreingKey,
            int tipoImpresora, boolean color, boolean scanner) {
        super(marca, modelo, estado, tipo, activo, foreingKey);
        this.tipoImpresora = tipoImpresora;
        this.color = color;
        this.scanner = scanner;
    }

    public Impresora(int id) {
        super(id);
        this.tipoImpresora = 0;
        this.color = false;
        this.scanner = false;
    }

    public void setTipoImpresora(int tipoImpresora) {
        this.tipoImpresora = tipoImpresora;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public void setScanner(boolean scanner) {
        this.scanner = scanner;
    }

    public int getTipoImpresora() {
        return tipoImpresora;
    }

    public boolean getColor() {
        return color;
    }

    public boolean getScanner() {
        return scanner;
    }

    @Override
    public String toString() {
        return "Impresora [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + ", tipo="
                + tipo + ", activo=" + activo + ", tipoImpresora=" + tipoImpresora + ", color=" + color + ", scanner="
                + scanner + "]";
    }

    @Override
    public int save() {
        try {
            RandomAccessFile raf = new RandomAccessFile("Impresoras.dat", "rw");
            if (this.id > ultimoId()) {
                raf.seek(raf.length());
            } else {
                raf.seek((id - 1) * tamRegistro);
            }
            raf.writeInt(id);
            raf.writeInt(id_Impresora);
            limpiarCampo(raf, marca);
            limpiarCampo(raf, modelo);
            raf.writeBoolean(estado);
            raf.writeInt(tipo);
            raf.writeBoolean(activo);
            raf.writeInt(tipoImpresora);
            raf.writeBoolean(color);
            raf.writeBoolean(scanner);
            raf.close();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
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
    @Override
    public int ultimoId() {
        int resultado = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Impresoras.dat", "r");
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
