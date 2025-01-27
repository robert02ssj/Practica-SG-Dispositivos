import java.io.RandomAccessFile;

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

    public Dispositivo(String marca, String modelo, boolean estado, int tipo, boolean activo, int foreingKey) {
        this.id = ultimoId() + 1;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.tipo = tipo;
        this.activo = activo;
        this.foreingKey = foreingKey;

    }

    public Dispositivo(int id) {
        this.id = id;
        this.marca = "";
        this.modelo = "";
        this.estado = true;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setForeingKey(int foreingKey) {
        this.foreingKey = foreingKey;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean getEstado() {
        return estado;
    }

    public int getTipo() {
        return tipo;
    }

    public boolean getActivo() {
        return activo;
    }

    public int getForeingKey() {
        return foreingKey;
    }

    public String toString() {
        return "id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + ", tipo=" + tipo
                + ", activo=" + activo + ", foreingKey=" + foreingKey + '}';
    }

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

    public void delete() {
        setActivo(false);
        save();
    }

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
