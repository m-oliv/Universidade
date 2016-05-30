
public class DocumentNotFormattedException extends Exception {
	private static final long serialVersionUID = 1L;

	//Parameterless Constructor
    public DocumentNotFormattedException() {}

    //Constructor that accepts a message
    public DocumentNotFormattedException(String message)
    {
       super(message);
    }
}
