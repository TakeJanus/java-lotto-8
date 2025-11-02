# java-lotto-precourse

# 로또 저장소 프로그램 기능 목록

## 1단계: 도메인 모델 구현
- [ ] Lotto: 당첨 번호와 일치하는 개수 반환 기능
- [ ] Lotto: 보너스 번호 포함 여부 반환 기능
- [ ] Rank: 당첨 기준(일치 개수, 보너스 여부) 및 상금을 관리하는 Enum
- [ ] Rank: 일치 개수와 보너스 여부로 적절한 Rank를 반환하는 기능

## 2단계: 당첨 번호 및 결과 객체 구현
- [ ] WinningLotto: 당첨 번호 6개와 보너스 번호 1개 저장
- [ ] WinningLotto: 당첨 번호 유효성 검사 (개수, 범위, 중복)
- [ ] WinningLotto: 보너스 번호 유효성 검사 (범위, 당첨 번호와 중복 여부)
- [ ] LottoResult: 당첨 통계(등수별 횟수) 저장 기능
- [ ] LottoResult: 총 당첨금 계산 기능
- [ ] LottoResult: 총 수익률 계산 기능 (소수점 둘째 자리 반올림)

## 3단계: 로또 발행 기능 구현
- [ ] LottoMachine: 구입 금액 유효성 검사 (1,000원 단위, 숫자 여부)
- [ ] LottoMachine: 구입 금액에 따른 로또 구매 개수 계산
- [ ] LottoMachine: Randoms API를 사용하여 구매 개수만큼 로또 발행
- [ ] LottoMachine: 발행된 로또 리스트(List<Lotto>) 반환

## 4단계: 입출력 View 구현
- [ ] InputView: 구입 금액 입력 기능
- [ ] InputView: 당첨 번호 (쉼표 구분) 입력 기능
- [ ] InputView: 보너스 번호 입력 기능
- [ ] InputView: 입력값 유효성 검사 (숫자, 범위, 형식 등)
- [ ] OutputView: 구매 로또 수량 출력
- [ ] OutputView: 구매 로또 번호 목록(오름차순 정렬) 출력
- [ ] OutputView: 당첨 통계(3개~6개 일치) 출력
- [ ] OutputView: 총 수익률 출력
- [ ] OutputView: [ERROR] 예외 메시지 출력

## 5단계: Controller 및 Application 통합
- [ ] LottoGameController: 전체 게임 흐름 제어 (입력 -> 발행 -> 비교 -> 출력)
- [ ] LottoGameController: 예외 발생 시 에러 메시지 출력 및 해당 입력 재시도
- [ ] Application: main 메서드에서 컨트롤러 실행