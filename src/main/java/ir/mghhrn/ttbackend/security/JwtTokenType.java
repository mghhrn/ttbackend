package ir.mghhrn.ttbackend.security;

public enum JwtTokenType {
    REFRESH, ACCESS;

    public static final String CLAIM_KEY = "t_type";
}
