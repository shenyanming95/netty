package io.netty.microbenchmark.common;

import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.NetUtil;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Threads(1)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class IsValidIpV4Benchmark extends AbstractMicrobenchmark {

    @Param({"127.0.0.1", "255.255.255.255", "1.1.1.1", "127.0.0.256", "127.0.0.1.1", "127.0.0", "[2001::1]"})
    private String ip;

    public static boolean isValidIpV4AddressOld(String value) {

        int periods = 0;
        int i;
        int length = value.length();

        if (length > 15) {
            return false;
        }
        char c;
        StringBuilder word = new StringBuilder();
        for (i = 0; i < length; i++) {
            c = value.charAt(i);
            if (c == '.') {
                periods++;
                if (periods > 3) {
                    return false;
                }
                if (word.length() == 0) {
                    return false;
                }
                if (Integer.parseInt(word.toString()) > 255) {
                    return false;
                }
                word.delete(0, word.length());
            } else if (!Character.isDigit(c)) {
                return false;
            } else {
                if (word.length() > 2) {
                    return false;
                }
                word.append(c);
            }
        }

        if (word.length() == 0 || Integer.parseInt(word.toString()) > 255) {
            return false;
        }

        return periods == 3;
    }

    // Tests

    @Benchmark
    public boolean isValidIpV4AddressOld() {
        return isValidIpV4AddressOld(ip);
    }

    @Benchmark
    public boolean isValidIpV4AddressNew() {
        return NetUtil.isValidIpV4Address(ip);
    }
}
