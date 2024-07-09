package util;

public class ColorUtil {
    // ANSI 이스케이프 코드 (텍스트 색상)
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // ANSI 이스케이프 코드 (텍스트 스타일)
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    public static String getColor(String colorName) {
        return switch (colorName.toUpperCase()) {
            case "RESET" -> RESET;
            case "BLACK" -> BLACK;
            case "RED" -> RED;
            case "GREEN" -> GREEN;
            case "YELLOW" -> YELLOW;
            case "BLUE" -> BLUE;
            case "PURPLE" -> PURPLE;
            case "CYAN" -> CYAN;
            case "WHITE" -> WHITE;
            case "BOLD" -> BOLD;
            case "UNDERLINE" -> UNDERLINE;
            default -> RESET;
        };
    }
}