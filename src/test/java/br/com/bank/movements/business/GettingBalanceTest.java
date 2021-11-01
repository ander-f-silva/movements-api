package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventType;
import br.com.bank.movements.repository.MockEventRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GettingBalanceTest {
    private static GetBalance getBalance;

    private static Stream<Arguments> getBalance() {
        return Stream.of(
                Arguments.of("Get balance with success", "100", Optional.of(BigDecimal.TEN)),
                Arguments.of("Account not found", "200", Optional.empty())
        );
    }

    @BeforeAll
    static void setUp() {
        var fakeAccountId = "100";

        var list = new ArrayList<Event>();
        list.add( new Event(EventType.DEPOSIT, "100", null, BigDecimal.TEN));

        getBalance = new GettingBalance(new MockEventRepository(fakeAccountId, list));
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("getBalance")
    @DisplayName("should get balance of a account")
    void testGetBalance(String title, String fakeAccountId, Optional<BigDecimal> expectedBalance) {
        var result = getBalance.findByAccount(fakeAccountId);

        assertThat(result, equalTo(expectedBalance));
    }
}