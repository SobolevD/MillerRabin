package com.ssau.tests;

import java.math.BigInteger;
import java.util.Random;

import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

public class MillerRabinTest {
    // производится k раундов проверки числа n на простоту

    public static boolean run(BigInteger n, int k)
    {

        if (n.equals(TWO) || n.equals(BigInteger.valueOf(3L)))
            return true;

        // если n < 2 или n четное - возвращаем false
        if (n.equals(ZERO) || n.equals(ONE) ||
                n.remainder(TWO).equals(ZERO))
            return false;

        // представим n − 1 в виде (2^s)·t, где t нечётно, это можно сделать последовательным делением n - 1 на 2
        BigInteger t = n.subtract(ONE);

        int s = 0;

        while (t.remainder(TWO).equals(ZERO))
        {
            t = t.divide(TWO);
            s += 1;
        }

        // повторить k раз
        for (int i = 0; i < k; i++)
        {
            // выберем случайное целое число a в отрезке [1, n − 1]
            int rng = new Random().nextInt(n.subtract(TWO).intValue()) + 1;

            BigInteger a = BigInteger.valueOf(rng);

            // x ← a^t mod n, вычислим с помощью возведения в степень по модулю
            BigInteger x = a.modPow(t, n);

            // если x == 1 или x == n − 1, то перейти на следующую итерацию цикла
            if (x.equals(ONE) || x.equals(n.subtract(ONE)))
                continue;

            // повторить s − 1 раз
            for (int r = 1; r < s; r++)
            {
                // x ← x^2 mod n
                x = x.modPow(TWO, n);

                // если x == 1, то вернуть "составное"
                if (x.equals(ONE))
                    return false;

                // если x == n − 1, то перейти на следующую итерацию внешнего цикла
                if (x.equals(n.subtract(ONE)))
                    break;
            }

            if (!x.equals(n.subtract(ONE)))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {

        BigInteger bigInteger = new BigInteger("374548630572308673462356437548674367548658753502312438573465293752368236723065128563409712342363632787696790678457784659786756574685867452523967349732937236349923852936523906743076349673296523957237693476293652370582403673703846512");
        BigInteger prime = bigInteger.nextProbablePrime();

        boolean run = MillerRabinTest.run(prime, 10);
        System.out.println(run);
    }
}
