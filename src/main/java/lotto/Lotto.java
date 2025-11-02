package lotto;

import java.util.Collections;
import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }

        long uniqueCount = numbers.stream()
                .distinct()
                .count();
        if (uniqueCount != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }

    /**
     * 당첨 번호와 몇 개의 번호가 일치하는지 계산
     * @param winningLotto 당첨 번호가 담긴 Lotto 객체
     * @return 일치하는 번호의 개수
     */
    public int calculateMatchCount(Lotto winningLotto) {
        List<Integer> winningNumbers = winningLotto.getNumbers();
        return (int) this.numbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }

    /**
     * 로또가 보너스 번호를 포함하고 있는지 확인
     * @param bonusNumber 보너스 번호
     * @return 포함 여부 (true/false)
     */
    public boolean containsBonus(int bonusNumber) {
        return this.numbers.contains(bonusNumber);
    }

    /**
     * 로또 번호 리스트를 반환합니다. (출력 및 비교용)
     * @return 정렬되지 않은 로또 번호 리스트 (수정 불가)
     */
    public List<Integer> getNumbers() {
        return Collections.unmodifiableList(this.numbers); // View(출력)에서 오름차순 정렬을 담당
    }
}
