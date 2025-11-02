package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThat;

class WinningLottoTest {

    @DisplayName("당첨 번호와 보너스 번호가 유효하면 생성에 성공한다.")
    @Test
    void createWinningLotto_Success() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;

        assertThatCode(() -> new WinningLotto(winningNumbers, bonusNumber))
                .doesNotThrowAnyException();
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void createWinningLotto_Fail_BonusDuplicate() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 6; // 당첨 번호 6과 중복

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다. (0)")
    @Test
    void createWinningLotto_Fail_BonusRange_Zero() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 0; // 범위 밖

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다. (46)")
    @Test
    void createWinningLotto_Fail_BonusRange_Over() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 46; // 범위 밖

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Lotto 생성자에서 당첨 번호 유효성 검사(개수, 중복)도 수행한다.")
    @Test
    void createWinningLotto_Fail_ByLottoValidate() {
        // Lotto의 validate(중복)가 실패해야 함
        List<Integer> duplicateNumbers = List.of(1, 1, 2, 3, 4, 5);
        int bonusNumber = 7;

        assertThatThrownBy(() -> new WinningLotto(duplicateNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("calculateRank가 2등(5개 + 보너스)을 정확히 반환한다.")
    @Test
    void calculateRank_SecondPlace() {
        WinningLotto winningLotto = new WinningLotto(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7)); // 5개 + 보너스

        Rank rank = winningLotto.calculateRank(userLotto);

        assertThat(rank).isEqualTo(Rank.SECOND);
    }

    @DisplayName("calculateRank가 3등(5개)을 정확히 반환한다.")
    @Test
    void calculateRank_ThirdPlace() {
        WinningLotto winningLotto = new WinningLotto(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8)); // 5개 (보너스 X)

        Rank rank = winningLotto.calculateRank(userLotto);

        assertThat(rank).isEqualTo(Rank.THIRD);
    }
}