package org.skyer.gateway.ratelimit.switcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双模式切换器，用于黑白名单切换
 * 
 * @author xcxcxcxcx
 */
public class DoubleModeSwitcher implements ModeSwitcher {

    private static final String BLACK_LIST = "black-list";
    private static final String WHITE_LIST = "white-list";

    private String mode;

    private List<String> list = new ArrayList<>();

    public DoubleModeSwitcher() {}

    public DoubleModeSwitcher(String mode, String listString) {
        this.mode = mode;
        String[] parts = listString.split(",");
        list.addAll(Arrays.asList(parts));
    }

    @Override
    public void switchMode(String mode, String listString) {
        this.mode = mode;
        if (listString != null) {
            String[] parts = listString.split(",");
            list.addAll(Arrays.asList(parts));
        }
    }

    @Override
    public String execute(String key) {
        if (BLACK_LIST.equals(this.mode)) {
            return blackLogic().execute(key, list);
        } else if (WHITE_LIST.equals(this.mode)) {
            return whiteLogic().execute(key, list);
        } else {
            return elseLogic().execute(key, list);
        }
    }

    public Logic blackLogic() {
        return (key, list) -> {
            if (list.contains(key)) {
                return null;
            } else {
                return key;
            }
        };
    }

    public Logic whiteLogic() {
        return (key, list) -> {
            if (list.contains(key)) {
                return key;
            } else {
                return null;
            }
        };
    }

    @Override
    public Logic elseLogic() {
        return (key, list) -> key;
    }
}
