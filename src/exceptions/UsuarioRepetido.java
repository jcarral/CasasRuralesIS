package exceptions;

/**
 * Created by Joseba on 27/02/2016.
 */
public class UsuarioRepetido extends Exception {
    public UsuarioRepetido(){
        super();
    }

    public UsuarioRepetido(String s){
        super(s);
    }
}
