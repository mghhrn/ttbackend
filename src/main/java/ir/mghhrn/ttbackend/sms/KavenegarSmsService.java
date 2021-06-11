package ir.mghhrn.ttbackend.sms;

import com.kavenegar.sdk.KavenegarApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KavenegarSmsService {

    private static final String smsMessageFormat = "سلام\nکد ورود شما به شنوا:\n%s";
    private final String otpTemplateName;
    private final KavenegarApi api;

    public KavenegarSmsService(@Value("${tt.kavenegar.api-key}") String apiKey,
                               @Value("${tt.kavenegar.otp-template-name}") String otpTemplateName) {
        this.api = new KavenegarApi(apiKey);
        this.otpTemplateName = otpTemplateName;
    }

    public void sendVerificationSms(String receptor, String verificationCode) {
        api.verifyLookup(receptor, verificationCode, otpTemplateName);
//        String message = String.format(smsMessageFormat, verificationCode);
//        api.send(null, receptor, message);
    }
}
