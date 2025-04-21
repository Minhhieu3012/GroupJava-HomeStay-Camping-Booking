package ut.edu.database.exception;

public class StorageFileNotFoundException extends RuntimeException {
  public StorageFileNotFoundException(String message) {
    super(message);
  }
}
