package pdlcompilerproyect;

import java.io.File;

/**
 * Generador del Lexer. Ejecutar antes que el Main del compilador
 * 
 * @version 1
 * @author Jose Ruiz Jimenez
 */
public class LexerGenerator {
    private final String LEXER_FILE = "aux_files\\lexer.flex";
    
    /**
     * Genera el Lexer a partir de un fichero FLEX
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFlex.Main.generate(new File(LEXER_FILE));
    }
}
