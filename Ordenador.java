import java.io.RandomAccessFile;

public class Ordenador extends Dispositivo {
    final int tamCampo = 50;
    final int tamRegistro = 172;

    private int id; // 4 bytes
    private String marca; // 50 bytes
    private String modelo; // 50 bytes
    private boolean estado; // 1 byte
    private int tipo; // 4 bytes
    private boolean activo; // 1 byte
    private int ram; // 4 bytes
    private String procesador; // 50 bytes
    private int tamDisco; // 4 bytes
    private int tipoDisco; // 4 bytes

    public Ordenador(String marca, String modelo, boolean estado, int tipo, boolean activo, int foreingKey, int ram,
            String procesador, int tamDisco, int tipoDisco) {
        super(marca, modelo, estado, tipo, activo, foreingKey);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = tipoDisco;

    }

    public Ordenador(int id) {
        super(id);
        this.ram = 0;
        this.procesador = "";
        this.tamDisco = 0;
        this.tipoDisco = 0;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public void setTamDisco(int tamDisco) {
        this.tamDisco = tamDisco;

    }

    public void setTipoDisco(int tipoDisco) {
        this.tipoDisco = tipoDisco;
    }

    public int getRam() {
        return ram;
    }

    public String getProcesador() {
        return procesador;
    }

    public int getTamDisco() {
        return tamDisco;
    }

    public int getTipoDisco() {
        return tipoDisco;
    }

    @Override
    public String toString() {
        return "Ordenador [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + ", tipo="
                + tipo + ", activo=" + activo + ", ram=" + ram + ", procesador=" + procesador + ", tamDisco=" + tamDisco
                + ", tipoDisco=" + tipoDisco + "]";
    }

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
    @Override 
    public int load(int idBuscado) {
        int idAnterior = ultimoId();
        if (idBuscado > idAnterior) {
            return 1;
        } else {
            idBuscado = idBuscado - 1;
            int resultado = -1;
            try {
                RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "r");
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
