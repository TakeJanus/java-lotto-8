package lotto;

import java.util.List;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoController(InputView inputView, OutputView outputView, LottoMachine lottoMachine) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoMachine = lottoMachine;
    }

    /**
     * 로또 게임 전체 흐름을 제어합니다.
     */
    public void run() {
        List<Lotto> purchasedLottos = retryPurchaseLottos();
        outputView.printPurchasedLottos(purchasedLottos);

        long purchaseAmount = (long) purchasedLottos.size() * LottoMachine.LOTTO_PRICE;

        WinningLotto winningLotto = retryGetWinningLotto();

        LottoResult lottoResult = calculateResult(purchasedLottos, winningLotto);
        printFinalResult(lottoResult, purchaseAmount);
    }

    /**
     * 구입 금액 입력 및 로또 발행 단계를 예외 처리와 함께 반복합니다.
     */
    private List<Lotto> retryPurchaseLottos() {
        while (true) {
            try {
                long amount = inputView.readPurchaseAmount();
                return lottoMachine.purchaseLottos(amount); // validate와 발행을 동시에
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 당첨 번호 및 보너스 번호 입력 단계를 예외 처리와 함께 반복합니다.
     */
    private WinningLotto retryGetWinningLotto() {
        while (true) {
            try {
                List<Integer> numbers = inputView.readWinningNumbers();
                int bonus = inputView.readBonusNumber();
                return new WinningLotto(numbers, bonus); // 생성자에서 모든 유효성 검사
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 구매한 로또들과 당첨 번호를 비교하여 당첨 통계를 생성합니다.
     */
    private LottoResult calculateResult(List<Lotto> lottos, WinningLotto winningLotto) {
        LottoResult result = new LottoResult();
        for (Lotto lotto : lottos) {
            Rank rank = winningLotto.calculateRank(lotto);
            result.addRank(rank);
        }
        return result;
    }

    /**
     * 최종 당첨 통계와 수익률을 출력합니다.
     */
    private void printFinalResult(LottoResult result, long purchaseAmount) {
        outputView.printWinningStatistics(result);

        double profitRate = result.calculateProfitRate(purchaseAmount);
        outputView.printProfitRate(profitRate);
    }
}