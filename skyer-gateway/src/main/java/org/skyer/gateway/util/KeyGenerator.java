package org.skyer.gateway.util;

import java.util.Random;

/**
 * @author XCXCXCXCX
 * @date 2019/10/17
 * @project skyer-gateway
 */
public class KeyGenerator {

    private static final Random RANDOM = new Random();

    private static final int BOUND = 100;

    public static String generate() {
        return System.currentTimeMillis() + "-" + RANDOM.nextInt(BOUND);
    }

}
