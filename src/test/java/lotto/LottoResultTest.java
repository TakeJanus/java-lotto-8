package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {

    private LottoResult lottoResult;

    @BeforeEach
    void setUp() {
        lottoResult = new LottoResult();
    }

    @DisplayName("LottoResult 생성 시 통계 맵이 0으로 초기화된다.")
    @Test
    void initializeStatisticsTest() {
        Map<Rank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.get(Rank.FIRST)).isZero();
        assertThat(statistics.get(Rank.SECOND)).isZero();
        assertThat(statistics.get(Rank.THIRD)).isZero();
        assertThat(statistics.get(Rank.FOURTH)).isZero();
        assertThat(statistics.get(Rank.FIFTH)).isZero();
        assertThat(statistics.containsKey(Rank.MISS)).isFalse(); // MISS는 통계에 없음
    }

    @DisplayName("addRank가 통계를 정확히 누적한다.")
    @Test
    void addRankTest() {
        lottoResult.addRank(Rank.FIFTH);
        lottoResult.addRank(Rank.FIFTH);
        lottoResult.addRank(Rank.FOURTH);
        lottoResult.addRank(Rank.MISS); // MISS는 무시되어야 함

        Map<Rank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.get(Rank.FIFTH)).isEqualTo(2);
        assertThat(statistics.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(statistics.get(Rank.FIRST)).isZero();
    }

    @DisplayName("수익률 계산: 1000원 투자, 5000원(5등 1개) 당첨 -> 500.0% ")
    @Test
    void calculateProfitRate_500_Percent() {
        lottoResult.addRank(Rank.FIFTH); // 5,000원

        double profitRate = lottoResult.calculateProfitRate(1000L); // 1,000원 구매

        assertThat(profitRate).isEqualTo(500.0);
    }

    @DisplayName("수익률 계산: 8000원 투자, 5000원(5등 1개) 당첨 -> 62.5% (소수점 둘째 자리 반올림)")
    @Test
    void calculateProfitRate_62_5_Percent() {
        lottoResult.addRank(Rank.FIFTH); // 5,000원

        double profitRate = lottoResult.calculateProfitRate(8000L); // 8,000원 구매

        // (5000 / 8000) * 100 = 62.5
        assertThat(profitRate).isEqualTo(62.5);
    }

    @DisplayName("수익률 계산: 7000원 투자, 5000원(5등 1개) 당첨 -> 71.4% (소수점 둘째 자리 반올림)")
    @Test
    void calculateProfitRate_Rounding() {
        lottoResult.addRank(Rank.FIFTH); // 5,000원

        double profitRate = lottoResult.calculateProfitRate(7000L); // 7,000원 구매

        // (5000 / 7000) * 100 = 71.428... -> Math.round(714.28) / 10.0 -> 71.4
        assertThat(profitRate).isEqualTo(71.4);
    }

    @DisplayName("수익률 계산: 당첨금이 0원일 경우 0.0%를 반환한다.")
    @Test
    void calculateProfitRate_ZeroPrize() {
        lottoResult.addRank(Rank.MISS);

        double profitRate = lottoResult.calculateProfitRate(10000L);

        assertThat(profitRate).isZero();
    }

    @DisplayName("수익률 계산: 구매 금액이 0원일 경우 0.0%를 반환한다.")
    @Test
    void calculateProfitRate_ZeroPurchase() {
        lottoResult.addRank(Rank.FIRST); // 20억 당첨

        double profitRate = lottoResult.calculateProfitRate(0L); // 0원 구매

        assertThat(profitRate).isZero(); // 0으로 나누기 방지
    }
}