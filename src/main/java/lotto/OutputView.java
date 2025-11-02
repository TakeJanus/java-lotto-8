package lotto;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    /**
     * [ERROR]로 시작하는 예외 메시지를 출력합니다.
     */
    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * 구매한 로또 수량과 로또 번호 목록을 출력합니다.
     * @param lottos 오름차순 정렬이 필요한 Lotto 리스트
     */
    public void printPurchasedLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");

        for (Lotto lotto : lottos) {
            System.out.println(formatLottoNumbers(lotto.getNumbers()));
        }
    }

    /**
     * 로또 번호 리스트를 오름차순으로 정렬하여 "[1, 2, 3, 4, 5, 6]" 형식으로 변환합니다.
     */
    private String formatLottoNumbers(List<Integer> numbers) {
        return numbers.stream()
                .sorted() // 요구 사항: 오름차순 정렬
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * 당첨 통계 (3~6개 일치)를 출력합니다.
     * @param result 통계 정보가 담긴 LottoResult 객체
     */
    public void printWinningStatistics(LottoResult result) {
        System.out.println("\n당첨 통계\n---");

        Map<Rank, Integer> statistics = result.getStatistics();
        DecimalFormat formatter = new DecimalFormat("#,###원");

        // 5등부터 1등 순서로 출력 (Rank Enum에 정의된 순서 활용)
        System.out.println(formatRankLine(Rank.FIFTH, statistics.get(Rank.FIFTH), formatter));
        System.out.println(formatRankLine(Rank.FOURTH, statistics.get(Rank.FOURTH), formatter));
        System.out.println(formatRankLine(Rank.THIRD, statistics.get(Rank.THIRD), formatter));
        System.out.println(formatRankLine(Rank.SECOND, statistics.get(Rank.SECOND), formatter));
        System.out.println(formatRankLine(Rank.FIRST, statistics.get(Rank.FIRST), formatter));
    }

    /**
     * 각 등수의 출력 라인을 형식에 맞게 생성합니다.
     * (예: "3개 일치 (5,000원) - 1개")
     */
    private String formatRankLine(Rank rank, int count, DecimalFormat formatter) {
        String prize = formatter.format(rank.getPrizeMoney());
        String matchInfo = rank.getMatchCount() + "개 일치";

        if (rank == Rank.SECOND) {
            matchInfo += ", 보너스 볼 일치";
        }

        return String.format("%s (%s) - %d개", matchInfo, prize, count);
    }

    /**
     * 총 수익률을 소수점 둘째 자리에서 반올림하여 출력합니다.
     * (예: "총 수익률은 62.5%입니다.")
     * @param rate LottoResult에서 계산된 수익률 (62.5)
     */
    public void printProfitRate(double rate) {
        // DecimalFormat이 소수점 반올림을 처리해 줌 (예: 62.5, 100.0)
        DecimalFormat rateFormatter = new DecimalFormat("#,##0.0");
        System.out.println("총 수익률은 " + rateFormatter.format(rate) + "%입니다.");
    }
}