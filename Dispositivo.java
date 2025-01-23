public class Dispositivo {
    private int id;
    private String marca;
    private String modelo;
    private boolean estado;
    private int tipo;
    private boolean activo;
    private int foreingKey;

    public Dispositivo(String marca, String modelo, boolean estado, int tipo, boolean activo) {
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

    public void getMarca(String marca) {
        this.marca = marca;
    }

    public void getModelo(String modelo) {
        this.modelo = modelo;
    }

    public void getEstado(boolean estado) {
        this.estado = estado;
    }

    public void getTipo(int tipo) {
        this.tipo = tipo;
    }

    public void getActivo(boolean activo) {
        this.activo = activo;
    }

    public void getForeingKey(int foreingKey) {
        this.foreingKey = foreingKey;
    }

    public String setMarca() {
        return marca;
    }

    public String setModelo() {
        return modelo;
    }

    public boolean setEstado() {
        return estado;
    }

    public int setTipo() {
        return tipo;
    }

    public boolean setActivo() {
        return activo;
    }

    public int setForeingKey() {
        return foreingKey;
    }

    public String toString() {
        return "id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + ", tipo=" + tipo
                + ", activo=" + activo + ", foreingKey=" + foreingKey + '}';
    }
    public void save() {
     
    }
}
