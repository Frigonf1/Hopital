package Socket;

@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String... args);
}
