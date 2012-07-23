
package compilador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 *
 * Proyecto Final PDL: TurboConpilar v4.73 REV: 2 
 * @author Jose Ruiz Jimenez, Pablo Calleja Ibañez, Jesus Mayor Marquez, Luis Alberto Lalueza Mayordomo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try{
            if(args.length<1){
                System.out.println("No encuentra ningun parametro. El programa cerrara");
                System.exit(0);
            }
            parser analizador = new parser (new AnalizadorMorfologico (new FileInputStream (new File(args[0]))));
            analizador.parse();
        }catch(FileNotFoundException e){
            System.out.println("No se ha podido leer el fichero");
        }catch(NullPointerException e){
            System.out.println("No se ha creado el analizador");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Se ha producido una excepcion en la ejecucion del Parser");
            e.printStackTrace();
        }
    }
}
