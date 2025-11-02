package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputView {

    private static final String COMMA_DELIMITER = ",";

    public long readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        String input = Console.readLine();
        validateNumeric(input);
        return Long.parseLong(input);
    }

    public List<Integer> readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        String input = Console.readLine();
        return parseWinningNumbers(input);
    }

    public int readBonusNumber() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        String input = Console.readLine();
        validateNumeric(input);
        return Integer.parseInt(input);
    }

    /**
     * 입력 문자열이 숫자로만 구성되었는지 검증합니다.
     */
    private void validateNumeric(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 입력값이 비어있습니다.");
        }
        try {
            Long.parseLong(input); // long 범위까지 허용
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 입력값이 숫자가 아닙니다.");
        }
    }

    /**
     * 쉼표(,)로 구분된 당첨 번호 문자열을 List<Integer>로 변환합니다.
     */
    private List<Integer> parseWinningNumbers(String input) {
        try {
            return Arrays.stream(input.split(COMMA_DELIMITER))
                    .map(String::trim)
                    .map(this::parseIntOrThrow) // 각 번호가 숫자인지 검증
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            // map(parseIntOrThrow)에서 발생한 예외를 다시 던집니다.
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 쉼표(,)로 구분된 숫자여야 합니다.");
        }
    }

    /**
     * Stream 내에서 NumberFormatException을 처리하기 위한 헬퍼 메서드입니다.
     */
    private int parseIntOrThrow(String numberStr) {
        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(); // parseWinningNumbers에서 메시지 처리
        }
    }
}