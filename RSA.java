import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RSA {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter p value: ");
		int p = in.nextInt();
		System.out.print("Enter q value: ");
		int q = in.nextInt();
		System.out.print("Enter secret message (number): ");
		int message01 = in.nextInt();
		// Lets choose p=7 q=19
		// Result: Public key: n=133 e=5    Private key: p*q=n=133, d=65

		int m = (p - 1) * (q - 1);
		int n = p * q;
		// System.out.println("p: " + p + ", q: " + q);

		// System.out.println(gcd(12, 6));

		int coprimeToM = coprime(m);
		// System.out.println("Coprime to " + m + " is: " + coprimeToM);

		int d = deModM(m, coprimeToM);
		// System.out.println("d: " + d);
		System.out.println("Publick key is: " + n + ", " + coprimeToM);
		System.out.println("Private key is: " + n + ", " + d);

		// int message01 = 6;
		System.out.println("Initial Message: " + message01);
		int cipher = powerMod(message01, coprimeToM, n); // encription: Cipher = (message)^e mod n
		System.out.println("Crypted message: " + cipher);

		int message02 = powerMod(cipher, d, n); // decription: Message = (cipher)^d mod n
		System.out.println("Decrypted Message: " + message02);

		int message03 = bruteforce(cipher, n, coprimeToM);
		System.out.println("Bruteforce-Decrypted Message: " + message03);

		in.close();
	}

	public static int gcd(int a, int b) {
		if (a < b) {
			int tempA = a;
			a = b;
			b = tempA;
		}
		int reminder = a % b;
		if (reminder == 0)
			return b;
		a = b;
		b = reminder;

		return gcd(a, b);
	}

	public static int coprime(int m) {
		int e = 2;
		while (gcd(m, e) != 1) e++;
		return e;
	}

	public static int deModM(int m, int e) {
		int k = 0;
		while (true) {
			int temp = (1 + k * m);
			if (temp % e == 0) {
				return temp / e;
			}
			k++;
		}
	}

	public static int powerMod(int g, int secret, int p) {
		int squareG = g * g;
		int modG = squareG % p;
		int pxy = modG;

		if ((secret % 2) == 1) {
			for (int i = secret - 2; i > 0; i -= 2) {
				pxy = (pxy * modG) % p;
			}
			pxy = pxy * g % p;
		} else {
			pxy = (pxy * modG) % p;
		}

		// System.out.println(pxy);
		return pxy;
	}

	public static int bruteforce(int cipher, int n, int coprimeToM) {
		int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
		int highestPossibility = (int) Math.sqrt(n);
		// System.out.println("hp = " + highestPossibility);
		int p2 = 0;
		int q2 = 0;

		for (int i = 2; i < highestPossibility; i++) {
			for (int j = 0; j < primes.length; j++) {
				// System.out.println(primes[j]);
				if (primes[j] == i) {
					// System.out.println(i);
					if (n % i == 0) {
						p2 = i;
						q2 = (n / i);
					}
				}
			}
		}

		int m = (p2 - 1) * (q2 - 1);
		int d = deModM(m, coprimeToM);

		return powerMod(cipher, d, (p2 * q2));
	}
}
