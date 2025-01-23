public class DispositivoTest {
    public static void main(String[] args) {
        //Dispositivo dispositivo = new Dispositivo("Hp", "Pavilion", true, 1, true, 1);
        //dispositivo.save();
        //Dispositivo dispositivo2 = new Dispositivo("Asus", "Zenbook", true, 1, true, 1);
        //dispositivo2.save();
        //Dispositivo dispositivo3 = new Dispositivo("Lenovo", "Thinkpad", true, 1, true, 1);
        //dispositivo3.save();
        //Dispositivo dispositivo4 = new Dispositivo("Dell", "XPS", true, 1, true, 1);
        //dispositivo4.save();
        //System.out.println(dispositivo.toString());
        //System.out.println(dispositivo2.toString());
        //System.out.println(dispositivo3.toString());
        //System.out.println(dispositivo4.toString());
        Dispositivo dispositivo5 = new Dispositivo(2);
        System.out.println(dispositivo5.load(2));
        System.out.println(dispositivo5.toString());
        
        
        
        
    }
}
