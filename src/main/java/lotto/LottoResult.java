package lotto;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class LottoResult {
    // EnumMap을 사용하여 Rank별 당첨 횟수를 관리합니다.
    private final Map<Rank, Integer> statistics;

    public LottoResult() {
        this.statistics = new EnumMap<>(Rank.class);
        initializeStatistics();
    }

    /**
     * 통계 맵을 0으로 초기화합니다. (MISS 제외)
     */
    private void initializeStatistics() {
        for (Rank rank : Rank.values()) {
            if (rank != Rank.MISS) {
                this.statistics.put(rank, 0);
            }
        }
    }

    /**
     * 당첨된 Rank를 통계에 추가합니다.
     * @param rank 당첨 등수
     */
    public void addRank(Rank rank) {
        if (rank == Rank.MISS) {
            return; // '꽝'은 통계에 포함하지 않습니다.
        }
        this.statistics.put(rank, this.statistics.get(rank) + 1);
    }

    /**
     * 총 당첨금을 계산합니다.
     * @return 총 당첨금 (long)
     */
    private long calculateTotalPrize() {
        long totalPrize = 0L;
        for (Rank rank : this.statistics.keySet()) {
            totalPrize += rank.getPrizeMoney() * this.statistics.get(rank);
        }
        return totalPrize;
    }

    /**
     * 총 수익률을 계산합니다. (소수점 둘째 자리에서 반올림)
     * @param purchaseAmount 총 구입 금액
     * @return 수익률 (double) (예: 62.5)
     */
    public double calculateProfitRate(long purchaseAmount) {
        long totalPrize = calculateTotalPrize();
        if (purchaseAmount == 0) {
            return 0.0; // 0으로 나누는 것을 방지
        }

        double rate = (double) totalPrize / purchaseAmount * 100;
        // 소수점 둘째 자리에서 반올림하여 첫째 자리까지 표시 (예: 62.5%)
        return Math.round(rate * 10.0) / 10.0;
    }

    /**
     * View에서 통계를 출력할 수 있도록 수정 불가능한 맵을 반환합니다.
     */
    public Map<Rank, Integer> getStatistics() {
        return Collections.unmodifiableMap(this.statistics);
    }
}