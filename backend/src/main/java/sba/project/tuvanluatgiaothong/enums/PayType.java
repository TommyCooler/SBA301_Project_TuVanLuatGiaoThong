package sba.project.tuvanluatgiaothong.enums;

public enum PayType {
    MOMO("MOMO"),
    VNPAY("VNPAY");

    private final String value;

    PayType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
