package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class WinningNumbersTest {
    private WinningNumbers winningNumbers;

    @BeforeEach
    void setUp() {
        LottoTicket lottoNumbers = new LottoTicket("1,2,3,4,5,6");
        LottoNumber bonusNumber = LottoNumberGenerator.getLottoNumber(7);
        winningNumbers = new WinningNumbers(lottoNumbers, bonusNumber);
    }

    @Test
    @DisplayName("1등 당첨번호와 보너스 번호가 중복 될 경우 예외 발생")
    void test() {
        LottoTicket lottoNumbers = new LottoTicket("1,2,3,4,5,6");
        LottoNumber bonusNumber = LottoNumberGenerator.getLottoNumber(1);

        assertThatThrownBy(() -> new WinningNumbers(lottoNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2,3,4,5,6 : FIRST_PLACE",
            "1,2,3,4,5,7 : SECOND_PLACE",
            "1,2,3,4,5,8 : THIRD_PLACE",
            "1,2,3,4,8,9 : FOURTH_PLACE",
            "1,2,3,8,9,10 : FIFTH_PLACE"},
            delimiter = ':')
    @DisplayName("1,2,3,4,5 등 당첨 결과 반환")
    void calculateRanking(String lottoNumbers, Ranking expectedRanking) {
        LottoTicket myLotto = new LottoTicket(lottoNumbers);

        assertThat(winningNumbers.calculateRanking(myLotto)).isEqualTo(expectedRanking);
    }
}
