package compilador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * Proyecto Final PDL: TurboConpilar v4.73 REV: 2 
 * @author Jose Ruiz Jimenez, Pablo Calleja Ibañez, Jesus Mayor Marquez, Luis Alberto Lalueza Mayordomo
 */
public class EscritorFichero {
    private static boolean anadir = false;

    public static void escribir(String texto) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("codigoNasm.txt",anadir);
            pw = new PrintWriter(fichero);
            
            pw.println(texto);

            fichero.close();
            anadir = true;
        } catch (IOException ex) {
            System.out.println("Problema con el fichero. El programa cerrara");
            System.exit(0);
        }
    }
}
