package utils;

public class Channels {
    public static String UPDATE_VIEW = "updateView";
    // View take a command and send it to Engine
    public static String SEND_COMMAND = "sendCommand";
    // Engine give back to View what to write on the terminal
    public static String RETURN_COMMAND = "returnCommand";
}
