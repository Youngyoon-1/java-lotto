package lotto.domain;

import static lotto.domain.LottoNumber.MAX;
import static lotto.domain.LottoNumber.MIN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LottoNumberGenerator {
    private static final String NUMBER_RANGE_ERROR = String.format("로또 숫자는 %s 이상 %s 이하의 숫자만 가능합니다.", MIN, MAX);
    private static final int FROM_INDEX = 0;

    private LottoNumberGenerator() {
    }

    static LottoNumber getLottoNumber(int lottoNumber) {
        return LottoNumbersCache.cache.stream()
                .filter(cachedLottoNumber -> cachedLottoNumber.isSameNumber(lottoNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NUMBER_RANGE_ERROR));
    }

    static List<LottoNumber> getShuffledNumbers(int lottoNumberCount) {
        List<LottoNumber> candidateNumbers = new ArrayList<>(LottoNumbersCache.cache);
        Collections.shuffle(candidateNumbers);
        return new ArrayList<>(candidateNumbers.subList(FROM_INDEX, lottoNumberCount));
    }

    private static class LottoNumbersCache {
        private static final String NOT_INSTANTIATION_ERROR = "LottoNumbersCache 객체를 생성할 수 없습니다.";

        static final List<LottoNumber> cache;

        static {
            List<LottoNumber> lottoNumbers = new ArrayList<>();
            for (int i = MIN; i <= MAX; i++) {
                lottoNumbers.add(new LottoNumber(i));
            }
            cache = Collections.unmodifiableList(lottoNumbers);
        }

        private LottoNumbersCache() throws InstantiationException {
            throw new InstantiationException(NOT_INSTANTIATION_ERROR);
        }
    }
}
