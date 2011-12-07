package pdlcompilerproyect;

import java.io.FileReader;

/**
 * Proyecto PDL - Compilador
 * 
 * @version 1
 * @author Jose Ruiz Jimenez
 */
public class Main {
    private final String FILE_TO_PARSE = "aux_files\\a.txt";

    /**
     * Lee de FILE_TO_PARSE el documento a parsear (y compilar)
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Yylex scanner = new Yylex(new FileReader(FILE_TO_PARSE));
        scanner.yylex();
    }

}
