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

        // 당첨 번호와 보너스 번호를 순차적으로 입력받고 검증
        WinningLotto winningLotto = getFinalWinningLotto();

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
                return lottoMachine.purchaseLottos(amount);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 당첨 번호와 보너스 번호 입력을 순차적으로 처리합니다.
     */
    private WinningLotto getFinalWinningLotto() {
        // 1. 당첨 번호가 유효할 때까지 반복
        Lotto winningLotto = retryGetWinningNumbers();

        // 2. 보너스 번호가 유효할 때까지 반복
        return retryGetBonusNumber(winningLotto);
    }

    /**
     * (신규) 당첨 번호 입력을 받고, Lotto 객체 생성을 통해 유효성 검사를 반복합니다.
     * @return 유효성이 검증된 Lotto 객체
     */
    private Lotto retryGetWinningNumbers() {
        while (true) {
            try {
                List<Integer> numbers = inputView.readWinningNumbers();
                return new Lotto(numbers); // 6개, 중복, 1~45 범위 검사
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 보너스 번호 입력을 받고, WinningLotto 객체 생성을 통해 유효성 검사를 반복합니다.
     * @param winningLotto 앞 단계에서 검증된 당첨 번호
     * @return 유효성이 검증된 WinningLotto 객체
     */
    private WinningLotto retryGetBonusNumber(Lotto winningLotto) {
        while (true) {
            try {
                int bonus = inputView.readBonusNumber();
                // WinningLotto 생성자가 보너스 번호(범위, 중복)를 검증
                return new WinningLotto(winningLotto, bonus);
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