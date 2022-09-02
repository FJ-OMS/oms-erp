package org.skyer.gateway.ratelimit.switcher;

import java.util.List;

public interface ModeSwitcher {

    String execute(String key);

    Logic elseLogic();

    void switchMode(String mode, String listString);

}




@FunctionalInterface
interface Logic {
    String execute(String key, List<String> list);
}

