package tsp;

public class InvalidStructureException extends Throwable {
    public InvalidStructureException() {
        super("Invalid structure of file");
    }
}
