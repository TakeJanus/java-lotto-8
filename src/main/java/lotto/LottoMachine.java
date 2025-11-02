package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoMachine {

    public static final int LOTTO_PRICE = 1000;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    /**
     * 구입 금액으로 로또를 발행합니다.
     * @param purchaseAmount 1,000원 단위의 구입 금액
     * @return 발행된 로또 리스트
     */
    public List<Lotto> purchaseLottos(long purchaseAmount) {
        validatePurchaseAmount(purchaseAmount);
        int lottoCount = calculateLottoCount(purchaseAmount);
        return issueLottos(lottoCount);
    }

    /**
     * 구입 금액이 1,000원 단위인지, 0보다 큰지 검사합니다.
     */
    private void validatePurchaseAmount(long purchaseAmount) {
        if (purchaseAmount <= 0 || purchaseAmount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위의 양수여야 합니다.");
        }
    }

    /**
     * 구입 금액으로 구매할 로또 개수를 계산합니다.
     */
    private int calculateLottoCount(long purchaseAmount) {
        return (int) (purchaseAmount / LOTTO_PRICE);
    }

    /**
     * 요청된 개수만큼 로또를 발행합니다.
     */
    private List<Lotto> issueLottos(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateLotto())
                .collect(Collectors.toList());
    }

    /**
     * Randoms API를 사용하여 로또 1개를 생성합니다.
     * (Lotto 클래스에서 번호 중복 및 개수를 검증합니다.)
     */
    private Lotto generateLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                MIN_LOTTO_NUMBER,
                MAX_LOTTO_NUMBER,
                LOTTO_NUMBER_COUNT
        );
        return new Lotto(numbers); // Lotto 생성자가 validate 수행
    }
}