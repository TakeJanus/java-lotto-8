package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호와 일치하는 개수를 정확히 반환한다.")
    @Test
    void calculateMatchCountTest() {
        // given
        Lotto myLotto = new Lotto(List.of(1, 2, 3, 10, 11, 12));
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 20, 21, 22));

        // when
        int matchCount = myLotto.calculateMatchCount(winningLotto);

        // then
        assertThat(matchCount).isEqualTo(3);
    }

    @DisplayName("보너스 번호가 로또 번호에 포함되어 있는지 확인한다. (포함 O)")
    @Test
    void containsBonusTest_True() {
        Lotto myLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 6;

        boolean hasBonus = myLotto.containsBonus(bonusNumber);

        assertThat(hasBonus).isTrue();
    }

    @DisplayName("보너스 번호가 로또 번호에 포함되어 있는지 확인한다. (포함 X)")
    @Test
    void containsBonusTest_False() {
        Lotto myLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        boolean hasBonus = myLotto.containsBonus(bonusNumber);

        assertThat(hasBonus).isFalse();
    }
}
