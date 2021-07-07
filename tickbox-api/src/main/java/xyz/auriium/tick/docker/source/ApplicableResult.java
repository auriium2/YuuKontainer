package xyz.auriium.tick.docker.source;

import java.util.Optional;

public class ApplicableResult {

    public boolean isApplicable() {
        return result;
    }

    public String getReason() {
        if (result) {
            throw new IllegalStateException("The result is successful, there will not be a reason!");
        } else {
            return reason;
        }
    }

    private final boolean result;
    private final String reason;

    ApplicableResult(boolean result, String reason) {
        this.result = result;
        this.reason = reason;
    }

    public static ApplicableResult success() {
        return new ApplicableResult(true,null);
    }

    public static ApplicableResult fail(String reason) {
        return new ApplicableResult(false,reason);
    }
}
