
public class DiffieHellman {
    public static void main(String args[]) {
        int p = 23; // prime number (modulo)
        int g = 5; // base number
        // int a = (int) Math.pow(5, 15); // secret number person X
        int a = 6; // secret number person X
        int b = 15; // secret number person Y

        int xPart = powerMod(g, a, p);
        int yPart = powerMod(g, b, p);

        int xShared = powerMod(yPart, a, p);
        int yShared = powerMod(xPart, b, p);
        System.out.println("----");
        System.out.println("Shared secret person X: " + xShared);
        System.out.println("Shared secret person Y: " + yShared);
    }

    public static int powerMod(int g, int secret, int p) {
        int squareG = g * g;
        int modG = squareG % p;
        int pxy = modG;

        if ((secret % 2) == 1) {
            for (int i = secret - 2; i > 1; i -= 2) {
                pxy = (pxy * modG) % p;
            }
            pxy = pxy * g % p;
        }

        if ((secret % 2) == 0) {
            for (int i = secret - 2; i > 0; i -= 2) {
                pxy = (pxy * modG) % p;
            }
        }

        System.out.println(pxy);
        return pxy;
    }
}
