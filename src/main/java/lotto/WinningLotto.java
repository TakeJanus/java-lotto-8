package lotto;

import java.util.List;

/**
 * 당첨 번호(Lotto)와 보너스 번호를 저장하는 불변(immutable) 데이터 객체입니다.
 * @param winningLotto 당첨 번호 6개를 담은 Lotto 객체
 * @param bonusNumber 보너스 번호 1개
 */
public record WinningLotto(Lotto winningLotto, int bonusNumber) {

    /**
     * 컴팩트 생성자 (Compact Constructor)
     * 정식 생성자가 호출된 직후, 필드 할당 전에 실행되어 유효성 검사를 수행합니다.
     */
    public WinningLotto {
        validateBonusNumber(winningLotto.getNumbers(), bonusNumber);
    }

    /**
     * 사용자 입력(List<Integer>)을 받아 정식 생성자를 호출하는 추가 생성자입니다.
     * @param winningNumbers 당첨 번호 6개 리스트
     * @param bonusNumber 보너스 번호
     */
    public WinningLotto(List<Integer> winningNumbers, int bonusNumber) {
        // 1. Lotto 객체 생성 (Lotto의 validate가 6개, 중복 검사)
        // 2. this()를 통해 정식 생성자 호출 -> 이어서 컴팩트 생성자(유효성 검사) 실행
        this(new Lotto(winningNumbers), bonusNumber);
    }

    /**
     * 보너스 번호의 유효성을 검사합니다. (컴팩트 생성자에서 사용)
     */
    private static void validateBonusNumber(List<Integer> winningNumbers, int bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    /**
     * 사용자 로또를 받아 당첨 등수(Rank)를 반환합니다.
     * @param userLotto 사용자의 로또 1장
     * @return Rank Enum (FIRST ~ MISS)
     */
    public Rank calculateRank(Lotto userLotto) {
        int matchCount = userLotto.calculateMatchCount(this.winningLotto);
        boolean hasBonus = userLotto.containsBonus(this.bonusNumber);

        return Rank.valueOf(matchCount, hasBonus);
    }
}