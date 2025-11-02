package lotto;

public class Application {
    public static void main(String[] args) {
        // 의존성 생성
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LottoMachine lottoMachine = new LottoMachine();

        // 컨트롤러에 의존성 주입 및 실행
        LottoController lottoController = new LottoController(
                inputView,
                outputView,
                lottoMachine
        );

        lottoController.run();
    }
}
