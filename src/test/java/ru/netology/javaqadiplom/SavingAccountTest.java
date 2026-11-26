package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

    // Тесты конструктора

    @Test
    public void shouldCreateAccountWithValidParams() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertEquals(1_000, account.getMinBalance());
        Assertions.assertEquals(10_000, account.getMaxBalance());
        Assertions.assertEquals(5, account.getRate());
    }

    @Test
    public void shouldThrowExceptionIfRateIsNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SavingAccount(2_000, 1_000, 10_000, -5);
        });
    }

    @Test
    public void shouldThrowExceptionIfMinBalanceIsNegative() {
        SavingAccount account = new SavingAccount(2_000, -1_000, 10_000, 5);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertEquals(-1_000, account.getMinBalance());
        Assertions.assertEquals(10_000, account.getMaxBalance());
        Assertions.assertEquals(5, account.getRate());
    }

    @Test
    public void shouldCreateAccountIfMinBalanceMoreThanMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 10_000, 1_000, 5);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertEquals(10_000, account.getMinBalance());
        Assertions.assertEquals(1_000, account.getMaxBalance());
        Assertions.assertEquals(5, account.getRate());
    }

    @Test
    public void shouldThrowExceptionIfInitialBalanceLessThanMinBalance() {
        SavingAccount account = new SavingAccount(500, 1_000, 10_000, 5);

        Assertions.assertEquals(500, account.getBalance());
        Assertions.assertEquals(1_000, account.getMinBalance());
        Assertions.assertEquals(10_000, account.getMaxBalance());
        Assertions.assertEquals(5, account.getRate());
    }

    @Test
    public void shouldThrowExceptionIfInitialBalanceMoreThanMaxBalance() {
        SavingAccount account = new SavingAccount(15_000, 1_000, 10_000, 5);

        Assertions.assertEquals(15_000, account.getBalance());
        Assertions.assertEquals(1_000, account.getMinBalance());
        Assertions.assertEquals(10_000, account.getMaxBalance());
        Assertions.assertEquals(5, account.getRate());
    }

    // Тесты пополнения (add)

    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);

        boolean result = account.add(3_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(2_000 + 3_000, account.getBalance());
    }

    @Test
    public void shouldAddUpToMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);

        boolean result = account.add(8_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(10_000, account.getBalance());
    }

    @Test
    public void shouldNotAddMoreThanMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);

        boolean result = account.add(9_000);

        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldNotAddNegativeOrZeroAmount() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);

        Assertions.assertFalse(account.add(-500));
        Assertions.assertFalse(account.add(0));
        Assertions.assertEquals(2_000, account.getBalance());
    }

    // Тесты оплаты (pay)

    @Test
    public void shouldPayWithinLimit() {
        SavingAccount account = new SavingAccount(5_000, 1_000, 10_000, 5);

        boolean result = account.pay(2_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(3_000, account.getBalance());
    }

    @Test
    public void shouldPayDownToMinBalance() {
        SavingAccount account = new SavingAccount(5_000, 1_000, 10_000, 5);

        boolean result = account.pay(4_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayBelowMinBalance() {
        SavingAccount account = new SavingAccount(5_000, 1_000, 10_000, 5);

        boolean result = account.pay(4_500);

        Assertions.assertFalse(result);
        Assertions.assertEquals(5_000, account.getBalance());
    }

    @Test
    public void shouldNotPayNegativeOrZeroAmount() {
        SavingAccount account = new SavingAccount(5_000, 1_000, 10_000, 5);

        Assertions.assertFalse(account.pay(-500));
        Assertions.assertFalse(account.pay(0));
        Assertions.assertEquals(5_000, account.getBalance());
    }

    // Тесты расчёта процентов (yearChange)

    @Test
    public void shouldCalculateYearChange() {
        SavingAccount account = new SavingAccount(200, 0, 10_000, 15);

        Assertions.assertEquals(30, account.yearChange());
    }

    @Test
    public void shouldCalculateYearChangeForBalanceLessThan100() {
        SavingAccount account = new SavingAccount(99, 0, 10_000, 10);

        Assertions.assertEquals(9, account.yearChange());
    }
}
