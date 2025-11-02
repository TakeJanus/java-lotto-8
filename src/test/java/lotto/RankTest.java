package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @DisplayName("일치 개수와 보너스 여부에 따라 정확한 등수를 반환한다.")
    @ParameterizedTest(name = "{0}개 일치, 보너스 {1} -> {2}")
    @CsvSource({
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, false, FIFTH",
            "2, false, MISS",
            "1, true, MISS",
            "0, false, MISS"
    })
    void valueOfTest(int matchCount, boolean hasBonus, Rank expectedRank) {
        Rank result = Rank.valueOf(matchCount, hasBonus);

        assertThat(result).isEqualTo(expectedRank);
    }

    @DisplayName("각 등수에 맞는 상금을 정확히 반환한다.")
    @ParameterizedTest(name = "{0} 등은 {1}원")
    @CsvSource({
            "FIRST, 2000000000",
            "SECOND, 30000000",
            "THIRD, 1500000",
            "FOURTH, 50000",
            "FIFTH, 5000",
            "MISS, 0"
    })
    void getPrizeMoneyTest(Rank rank, long expectedPrize) {
        long prize = rank.getPrizeMoney();

        assertThat(prize).isEqualTo(expectedPrize);
    }
}