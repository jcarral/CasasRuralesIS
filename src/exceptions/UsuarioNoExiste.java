package exceptions;

/**
 * Created by Joseba on 27/02/2016.
 */
public class UsuarioNoExiste extends Exception {

    public UsuarioNoExiste(){super();}

    /**
     * La excepción salta si el usuario no está en la base de datos
     * @param s
     */
    public UsuarioNoExiste(String s){
        super(s);
    }
}
