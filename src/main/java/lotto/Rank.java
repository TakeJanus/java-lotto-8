package lotto;

public enum Rank {
    FIRST(6, false, 2_000_000_000L), // 1등
    SECOND(5, true, 30_000_000L),  // 2등
    THIRD(5, false, 1_500_000L),   // 3등
    FOURTH(4, false, 50_000L),     // 4등
    FIFTH(3, false, 5_000L),      // 5등
    MISS(0, false, 0L);          // 꽝 (0~2개 일치)

    private final int matchCount;
    private final boolean requiresBonus;
    private final long prizeMoney;

    Rank(int matchCount, boolean requiresBonus, long prizeMoney) {
        this.matchCount = matchCount;
        this.requiresBonus = requiresBonus;
        this.prizeMoney = prizeMoney;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isRequiresBonus() {
        return requiresBonus;
    }

    /**
     * 일치하는 번호 개수와 보너스 번호 포함 여부로 당첨 등수를 반환합니다.
     * @param matchCount 일치하는 번호 개수
     * @param hasBonus 보너스 번호 포함 여부
     * @return 해당하는 Rank Enum
     */
    public static Rank valueOf(int matchCount, boolean hasBonus) {
        if (matchCount == FIRST.matchCount) {
            return FIRST;
        }
        if (matchCount == SECOND.matchCount && hasBonus == SECOND.requiresBonus) {
            return SECOND;
        }
        if (matchCount == THIRD.matchCount && hasBonus != SECOND.requiresBonus) {
            return THIRD;
        }
        if (matchCount == FOURTH.matchCount) {
            return FOURTH;
        }
        if (matchCount == FIFTH.matchCount) {
            return FIFTH;
        }
        return MISS;
    }
}