package pl.org.opi.ragdeterm.db.exception;

public class DbException extends RuntimeException {
    public DbException(String message) {
        super(message);
    }
    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
    public DbException(Throwable cause) {
        super(cause);
    }

}
