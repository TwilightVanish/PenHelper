package lisa.penhelper.chat;

public interface ChatFilter {
    boolean isEnabled();
    boolean matches(String message);
}
