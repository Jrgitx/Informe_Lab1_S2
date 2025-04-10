import java.util.Scanner;

public class BigVigenere {
    private int[] key;
    private char[][] alphabet;
    private String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";

    // Constructor que solicita la key al usuario
    public BigVigenere() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la clave numérica: ");
        String inputKey = sc.nextLine();
        this.key = convertirKeyEnArreglo(inputKey);
        contruirAlphabet();
    }

    // Constructor que recibe la clave como string
    public BigVigenere(String numericKey) {
        this.key = convertirKeyEnArreglo(numericKey);
        contruirAlphabet();
    }

    // Convierte un string de números a un arreglo de enteros
    private int[] convertirKeyEnArreglo(String numericKey) {
        int[] k = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            k[i] = Character.getNumericValue(numericKey.charAt(i));
        }
        return k;
    }

    // Construye la matriz del alfabeto
    private void contruirAlphabet() {
        alphabet = new char[64][64];
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                alphabet[i][j] = caracteres.charAt((i + j) % 64);
            }
        }
    }

    // Metodo para cifrar
    public String encrypt(String mensaje) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < mensaje.length(); i++) {
            int row = caracteres.indexOf(mensaje.charAt(i));
            int col = key[i % key.length];
            encrypted.append(alphabet[row][col]);
        }
        return encrypted.toString();
    }

    // Metodo para descifrar
    public String decrypt(String encryptedMensaje) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < encryptedMensaje.length(); i++) {
            int col = key[i % key.length];
            int row = findRowForChar(encryptedMensaje.charAt(i), col);
            decrypted.append(caracteres.charAt(row));
        }
        return decrypted.toString();
    }

    // Busca la fila donde se encuentra el caracter cifrado, para descifrar
    private int findRowForChar(char c, int col) {
        for (int i = 0; i < 64; i++) {
            if (alphabet[i][col] == c) {
                return i;
            }
        }
        return -1; // no encontrado
    }

    // Metodo para reencriptar con una nueva clave
    public void reEncrypt() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese mensaje cifrado: ");
        String encrypted = sc.nextLine();
        String decrypted = decrypt(encrypted);
        System.out.println("Mensaje descifrado: " + decrypted);

        System.out.print("Ingrese nueva clave numérica: ");
        String newKey = sc.nextLine();
        this.key = convertirKeyEnArreglo(newKey);

        String reEncrypted = encrypt(decrypted);
        System.out.println("Nuevo mensaje cifrado: " + reEncrypted);
    }

    // Busqueda iterativa
    public char search(int position) {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                if (count == position) {
                    return alphabet[i][j];
                }
                count++;
            }
        }
        return '?';
    }

    // Busqueda optimizada
    public char optimalSearch(int position) {
        int row = position / 64;
        int col = position % 64;
        return alphabet[row][col];
    }

    public static void main(String[] args) {
        // Crear una instancia con clave dada como string
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la clave numérica para BigVigenere: ");
        String keyInput = sc.nextLine();
        BigVigenere vigenere = new BigVigenere(keyInput);

        //BigVigenere vigenere = new BigVigenere("314159");

        // Mensaje a cifrar
        System.out.print("Ingrese el mensaje a encriptar: ");
        String mensaje = sc.nextLine();

        // Cifrar el mensaje
        String mensajeCifrado = vigenere.encrypt(mensaje);
        System.out.println("Mensaje cifrado: " + mensajeCifrado);

        // Descifrar el mensaje
        String mensajeDescifrado = vigenere.decrypt(mensajeCifrado);
        System.out.println("Mensaje descifrado: " + mensajeDescifrado);

        System.out.println("\n--- Re-encriptación ---");
        vigenere.reEncrypt();

        // Probar busqueda
        System.out.println("search(2000): " + vigenere.search(2000));
        System.out.println("optimalSearch(4095): " + vigenere.optimalSearch(4095));
    }
}