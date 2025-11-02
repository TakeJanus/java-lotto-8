package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoMachineTest {

    private LottoMachine lottoMachine;

    @BeforeEach
    void setUp() {
        lottoMachine = new LottoMachine();
    }

    @DisplayName("구입 금액에 해당하는 만큼 로또를 정상 발행한다.")
    @Test
    void purchaseLottos_Success() {
        // given
        long purchaseAmount = 8000L;

        // when
        List<Lotto> lottos = lottoMachine.purchaseLottos(purchaseAmount);

        // then
        assertThat(lottos).isNotNull();
        assertThat(lottos.size()).isEqualTo(8); // 8,000원 -> 8개
    }

    @DisplayName("구입 금액이 1,000원으로 나누어 떨어지지 않으면 예외가 발생한다.")
    @Test
    void purchaseLottos_Fail_InvalidUnit() {
        // given
        long purchaseAmount = 1500L; // 1,000원 단위가 아님

        // when & then
        assertThatThrownBy(() -> lottoMachine.purchaseLottos(purchaseAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 0원이면 예외가 발생한다.")
    @Test
    void purchaseLottos_Fail_ZeroAmount() {
        // given
        long purchaseAmount = 0L;

        // when & then
        assertThatThrownBy(() -> lottoMachine.purchaseLottos(purchaseAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 음수이면 예외가 발생한다.")
    @Test
    void purchaseLottos_Fail_NegativeAmount() {
        // given
        long purchaseAmount = -1000L;

        // when & then
        assertThatThrownBy(() -> lottoMachine.purchaseLottos(purchaseAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}