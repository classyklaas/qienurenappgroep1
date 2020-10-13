package app.qienuren.exceptions;

public class GebruikerServiceException extends RuntimeException{

    private static final long serialVersionUID = 1348771109171435607L;

    public GebruikerServiceException(String message)
    {
        super(message);
    }
}