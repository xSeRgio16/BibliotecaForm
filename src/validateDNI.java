import java.util.*;

public class validateDNI {
    public static void main(String[] args) {
        boolean validar;
        String[] asignarLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q",
                "V", "H", "L", "C", "K", "E"};
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("Introduce tu número de DNI, sólo el número");
                String x = sc.nextLine();
                if (x.length() == 8) {
                    int dni = Integer.parseInt(x);
                    int resto = dni % 23;
                    System.out.println(dni + asignarLetra[resto]);
                    validar = true;
                } else {
                    System.out.println("Formato inválido");
                    validar = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error. El valor introducido no son sólo números");
                validar = false;
            } catch (Exception e) {
                System.out.println("Error");
                validar = false;
            }
        } while (!validar);
    }
}