package org.skyer.gateway.ratelimit.switcher;

/**
 * @author XCXCXCXCX
 * @version 1.0
 * @date 2019/11/19 4:04 下午
 */
public class EmptyModeSwitcher implements ModeSwitcher {

    public static final ModeSwitcher EMPTY_INSTANCE = new EmptyModeSwitcher();

    @Override
    public String execute(String key) {
        return key;
    }

    @Override
    public Logic elseLogic() {
        return null;
    }

    @Override
    public void switchMode(String mode, String listString) {}
}
