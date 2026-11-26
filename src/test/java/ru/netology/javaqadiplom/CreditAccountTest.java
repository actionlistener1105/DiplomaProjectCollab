package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAccountTest {

    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(
                0,
                5_000,
                15
        );

        account.add(3_000);

        assertEquals(3_000, account.getBalance());
    }


    @Test
    void validConstructorCreatesAccount() {
        CreditAccount ca = new CreditAccount(100, 50, 15);
        assertEquals(50, ca.getCreditLimit());
    }

    @Test
    void negativeRateThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CreditAccount(100, 50, -5));
    }

    @Test
    void rateZeroThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CreditAccount(100, 50, 0));
    }

    @Test
    void constructorShouldCheckInitialBalance() {
        // Баг: по контракту должно быть исключение, а объект создаётся c отрицательным балансом
        CreditAccount ca = new CreditAccount(-100, 50, 15);
        assertEquals(0, ca.getBalance());
    }

    @Test
    void constructorShouldCheckCreditLimit() {
        // Баг: можно задать отрицательный лимит
        CreditAccount ca = new CreditAccount(100, -50, 15);
        assertEquals(0, ca.getCreditLimit());
    }

    // --- pay() ---
    // Сначала меняет balance, потом проверяет.
    // В «успешном» случае ещё и перезаписывает balance = -amount.
    // В «неуспешном» тоже оставляет изменённое значение.

    @Test
    void shouldReturnBalance90WithAmount10() {
        CreditAccount ca = new CreditAccount(100, 50, 15);
        assertTrue(ca.pay(10));
        // По контракту должно быть 90, а получается -10
        assertEquals(90, ca.getBalance());
    }

    @Test
    void payWithNonPositiveAmountReturnsFalseAndShouldNotChangeBalance() {
        CreditAccount ca = new CreditAccount(100, 50, 15);
        assertFalse(ca.pay(0));
        assertEquals(100, ca.getBalance());
        assertFalse(ca.pay(-10));
        assertEquals(100, ca.getBalance());
    }

    @Test
    void payThatExceedsLimitReturnsFalseAndNotChangesBalance() {
        CreditAccount ca = new CreditAccount(10, 5, 15);
        assertFalse(ca.pay(15));
        // По контракту баланс должен остаться 10. А он изменился.
        assertEquals(10, ca.getBalance());
    }

    // --- add() ---
    // Просто присваивает balance = amount.
    @Test
    void addPositiveAmountSetsBalanceInsteadOfAdding() {
        CreditAccount ca = new CreditAccount(50, 50, 15);
        assertTrue(ca.add(100));
        // По контракту должно быть 150, а получается 100
        assertEquals(150, ca.getBalance());
    }

    @Test
    void addWithNonPositiveAmountReturnsFalse() {
        CreditAccount ca = new CreditAccount(50, 50, 15);
        assertFalse(ca.add(0));
        assertFalse(ca.add(-20));
    }

    // --- yearChange() ---
    // Считает для любого значения, в том числе для положительного.
    @Test
    void yearChangeForPositiveBalanceShouldBeZero() {
        CreditAccount ca = new CreditAccount(200, 50, 15);
        // По описанию задания при положительном балансе результат — 0.
        assertEquals(0, ca.yearChange()); // если тест упадёт — это фиксация бага
    }

    @Test
    void yearChangeForNegativeBalance() {
        CreditAccount ca = new CreditAccount(-200, 50, 15);
        assertEquals(-30, ca.yearChange());
    }

    @Test
    void yearChangeWithSmallNegativeBalance() {
        CreditAccount ca = new CreditAccount(-99, 50, 15);
        // В Java: -99 / 100 == 0, поэтому результат 0
        assertEquals(-14.85, ca.yearChange());
    }
}
