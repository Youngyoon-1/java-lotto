package lotto.domain;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Ranking {
    FIRST_PLACE(2000000000, Constants.FIRST_PLACE_HIT_COUNT,
            (hitCount, hasBonusNumber) -> hitCount == Constants.FIRST_PLACE_HIT_COUNT),
    SECOND_PLACE(30000000, Constants.SECOND_THIRD_PLACE_HIT_COUNT,
            (hitCount, hasBonusNumber) -> hitCount == Constants.SECOND_THIRD_PLACE_HIT_COUNT && hasBonusNumber),
    THIRD_PLACE(1500000, Constants.SECOND_THIRD_PLACE_HIT_COUNT,
            (hitCount, hasBonusNumber) -> hitCount == Constants.SECOND_THIRD_PLACE_HIT_COUNT && !hasBonusNumber),
    FOURTH_PLACE(50000, Constants.FOURTH_PLACE_HIT_COUNT,
            (hitCount, hasBonusNumber) -> hitCount == Constants.FOURTH_PLACE_HIT_COUNT),
    FIFTH_PLACE(5000, Constants.FIFTH_PLACE_HIT_COUNT,
            (hitCount, hasBonusNumber) -> hitCount == Constants.FIFTH_PLACE_HIT_COUNT),
    NONE_PLACE(0, 0,
            (hitCount, hasBonusNumber) -> hitCount < Constants.FIFTH_PLACE_HIT_COUNT);

    private static final String ERROR_HIT_COUNT_OVER_THRESHOLD = "최대 맞은 개수는 6개 입니다.";
    private final int prize;
    private final int hitCount;
    private final BiPredicate<Integer, Boolean> mapper;

    Ranking(int prize, int hitCount, BiPredicate<Integer, Boolean> mapper) {
        this.prize = prize;
        this.hitCount = hitCount;
        this.mapper = mapper;
    }

    public static Ranking of(int hitCount, boolean hasBonusNumber) {
        return Arrays.stream(Ranking.values())
                .filter(ranking -> ranking.mapper.test(hitCount, hasBonusNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_HIT_COUNT_OVER_THRESHOLD));
    }

    public long multiplyPrizeWithCount(int count) {
        return (long) prize * count;
    }

    public boolean isSecond() {
        return this == SECOND_PLACE;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getPrize() {
        return prize;
    }

    private static final class Constants {
        private static final int FIRST_PLACE_HIT_COUNT = 6;
        private static final int SECOND_THIRD_PLACE_HIT_COUNT = 5;
        private static final int FOURTH_PLACE_HIT_COUNT = 4;
        private static final int FIFTH_PLACE_HIT_COUNT = 3;
    }
}
