package pdlcompilerproyect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Proyecto PDL - Compilador
 * 
 * @version 2
 * @author Jose Ruiz Jimenez
 */
public class Main {
    private static final String FILE_TO_PARSE = "aux_files\\entrada.txt";

    /**
     * Lee de FILE_TO_PARSE el documento a parsear (y compilar)
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try{
            parser analizador = new parser(new AnalizadorMorfologico(
                    new FileInputStream(new File(FILE_TO_PARSE))));
            analizador.parse();
        } catch(FileNotFoundException e){
            System.out.println("No se ha podido leer el fichero");
        } catch(NullPointerException e){
            System.out.println("No se ha creado el analizador");
            e.printStackTrace();
        } catch(Exception e){
            System.out.println("Se ha producido una excepcion en la ejecucion del Parser");
            e.printStackTrace();
        }
    }

}
