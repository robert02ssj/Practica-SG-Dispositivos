class prueba{
    public static void main(String[] args) {
        Ordenador ordenador = new Ordenador(2);
        ordenador.load();
        System.out.println(ordenador.toString());
        Ordenador ordenador2 = new Ordenador(3);
        ordenador2.load();
        System.out.println(ordenador2.toString());
        Ordenador ordenador3 = new Ordenador(4);
        ordenador3.load();
        System.out.println(ordenador3.toString());
        System.out.println(ordenador3.getRam());
    }
}