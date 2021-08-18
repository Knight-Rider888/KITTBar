package knight.rider.kitt.bar.config;

import knight.rider.kitt.bar.attr.BarTitleGravity;

public class BarConfig {

    private final static BarConfig INSTANCE = new BarConfig();

    private static Builder builder;

    private BarConfig() {
        builder = new Builder();
    }

    public static BarConfig getInstance() {
        return INSTANCE;
    }


    public BarConfig setBarTitleGravity(BarTitleGravity titleGravity) {
        builder.setBarTitleGravity(titleGravity);
        return this;
    }

    // 返回builder
    public BarConfig.Builder getBuilder() {
        return builder;
    }

    public static class Builder {

        private BarTitleGravity bar_title_gravity;

        public BarTitleGravity getBarTitleGravity() {
            return bar_title_gravity == null ? BarTitleGravity.LEFT : bar_title_gravity;
        }

        public void setBarTitleGravity(BarTitleGravity bar_title_gravity) {
            this.bar_title_gravity = bar_title_gravity;
        }
    }

}
