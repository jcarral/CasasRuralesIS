package exceptions;

/**
 * Created by JosebaPC on 09/04/2016.
 */
public class OfertaRepetida extends Exception {

    public OfertaRepetida(){super();}

    /**
     * La excepción salta si la oferta ya está en la base de datos
     * @param s
     */
    public OfertaRepetida(String s){
        super(s);
    }
}