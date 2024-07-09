package util;

// 사용자 입력의 유효성을 검사하는 유틸리티 클래스
public class InputValidator {
    // 주어진 모든 입력이 유효한지 검사하는 메서드
    public static boolean isValidInput(String... inputs) {
        // 각 입력에 대해 반복
        for (String input : inputs) {
            // 입력이 null이거나 공백을 제거한 후 비어있으면 유효하지 않음
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        // 모든 검사를 통과하면 유효한 입력으로 판단
        return true;
    }
}