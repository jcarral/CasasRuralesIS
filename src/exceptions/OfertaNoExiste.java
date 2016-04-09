package exceptions;

/**
 * Created by JosebaPC on 09/04/2016.
 */
public class OfertaNoExiste extends Exception {

    public OfertaNoExiste(){super();}

    /**
     * La excepción salta si la oferta no está en la base de datos
     * @param s
     */
    public OfertaNoExiste(String s){
        super(s);
    }
}