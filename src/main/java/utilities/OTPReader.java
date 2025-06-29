package utilities;
import java.util.Scanner;

public class OTPReader {
    private static String otp = null;

    public static String getOTP(String platform) {
        if (otp == null) {
            System.out.print("Enter the OTP received on your phone for " + platform + ": ");
            Scanner sc = new Scanner(System.in);
            otp = sc.nextLine();
        }

        String tempOtp = otp;
        otp = null; // Clear after use
        return tempOtp;
    }
}

