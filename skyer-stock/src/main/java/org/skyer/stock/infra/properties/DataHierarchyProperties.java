package org.skyer.stock.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yuelinsoft
 */
@ConfigurationProperties(prefix = "skyer.data-hierarchy.display")
public class DataHierarchyProperties {
    private int maxSelectCount = 3;

    public int getMaxSelectCount() {
        return maxSelectCount;
    }

    public void setMaxSelectCount(int maxSelectCount) {
        this.maxSelectCount = maxSelectCount;
    }
}
