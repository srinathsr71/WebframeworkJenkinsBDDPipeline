package utilities;

import java.time.LocalDate;
import java.util.Random;

public class Helper {

    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static int maxstringlength = 3;
    public static String number = "1234567890";
    public static int maxNumberLength = 9;
    public static int invoiceLength = 8;
    public static int deliveryChallan = 6;

    public static String generateRandomName() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maxstringlength; i++) {

            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }

        return "Autogen" + sb.toString();
    }

    public static String generateRandomMobileNumber() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maxNumberLength; i++) {

            int index = random.nextInt(number.length());
            sb.append(number.charAt(index));
        }

        return "8" + sb.toString();
    }

    public static String generateRandomInvoiceNumber() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < invoiceLength; i++) {

            int index = random.nextInt(number.length());
            sb.append(number.charAt(index));
        }

        return sb.toString();
    }

    public String generateRandomDate(int minusdays) {
        LocalDate today = LocalDate.now();

        LocalDate date = today.minusDays(minusdays);
        return date.toString();
    }

    public static String generateRandomDeliveryChallanNumber() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < deliveryChallan; i++) {

            int index = random.nextInt(number.length());
            sb.append(number.charAt(index));
        }

        return sb.toString();
    }

}
