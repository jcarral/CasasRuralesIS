package exceptions;

/**
 * Created by Joseba on 27/02/2016.
 */
public class UsuarioRepetido extends Exception {
    public UsuarioRepetido(){
        super();
    }

    /**
     * La excepción salta si el usuario está repetido en la base de datos
     * @param s
     */
    public UsuarioRepetido(String s){
        super(s);
    }
}
