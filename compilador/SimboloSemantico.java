package compilador;

/**
 *
 * Proyecto Final PDL: TurboConpilar v4.73 REV: 2 
 * @author Jose Ruiz Jimenez, Pablo Calleja Ibañez, Jesus Mayor Marquez, Luis Alberto Lalueza Mayordomo
 */
public class SimboloSemantico {

    public enum Tipo {

        FUNCTION, INT, FLOAT, BOOLEAN, ARRAY_INT, ARRAY_FLOAT, ARRAY_BOOLEAN
    };
    private Tipo tipo;
    private String nombre;
    private int posicion;
    private int tam;

    public SimboloSemantico(String nombre, Tipo tipo) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.posicion = 0;
        this.tam = 1;
    }

    public SimboloSemantico(String nombre, Tipo tipo, int posicion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.posicion = posicion;
        this.tam = 1;
    }

    public SimboloSemantico(int tam, String nombre, Tipo tipo) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.posicion = 0;
        this.tam = tam;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getTam() {
        return tam;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
