package exceptions;

/**
 * Created by Joseba on 27/02/2016.
 */
public class UsuarioNoExiste extends Exception {

    public UsuarioNoExiste(){super();}
    public UsuarioNoExiste(String s){
        super(s);
    }
}
