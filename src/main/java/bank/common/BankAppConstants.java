package bank.common;

import org.springframework.data.util.Pair;

public class BankAppConstants {

    public static class UserRegisterWarnings{
        public static final Pair<String, String> MISSING_USERNAME = Pair.of("username", "Request is missing username");
        public static final Pair<String, String> MISSING_PASSWORD = Pair.of("password", "Request is missing password");
        public static final Pair<String, String> MISSING_EMAIL = Pair.of("email", "Request is missing email");
        public static final Pair<String, String> MISSING_PHONE_NUMBER = Pair.of("phone_number", "Request is missing phone number");
        public static final Pair<String, String> MISSING_NOTIFICATION_CHANNEL = Pair.of("notification_channel", "Request is missing notification channel");
        public static final Pair<String, String> USERNAME_INCORRECT_LENGTH = Pair.of("username", "Username length should be between 8 and 30 characters");
        public static final Pair<String, String> USERNAME_INCORRECT_CHARACTERS = Pair.of("username", "Username should contain only alphanumeric characters");
        public static final Pair<String, String> PASSWORD_INCORRECT_LENGTH = Pair.of("password", "Password length should be between 8 and 30 characters");
        public static final Pair<String, String> PHONE_NUMBER_INCORRECT_LENGTH = Pair.of("phone_number", "Phone number length should 9");
        public static final Pair<String, String> PHONE_NUMBER_INCORRECT_CHARACTERS = Pair.of("phone_number", "Phone number should only contain digits");
        public static final Pair<String, String> EMAIL_INCORRECT_FORMAT = Pair.of("email", "Given email is invalid email address");
        public static final Pair<String, String> NOTIFICATION_CHANNEL_UNRECOGNIZED = Pair.of("notification_channel", "Notification channel is incorrect");

    }

    public static int DAILY_TRANSFER_NUMBER_LIMIT = 3;

    public static class TransferWarnings {
        public static final Pair<String, String> MISSING_INCORRECT_FROM_ACCOUNT = Pair.of("from_account_number", "Request is missing or contain incorrect from account number");
        public static final Pair<String, String> MISSING_INCORRECT_TO_ACCOUNT = Pair.of("to_account_number", "Request is missing or contain incorrect to account number");
        public static final Pair<String, String> MISSING_AMOUNT = Pair.of("amount", "Request is missing amount");
        public static final Pair<String, String> TRANSFER_FROM_NOT_OWNED_ACCOUNT = Pair.of("from_account_number", "User is allowed to send transfers only from owned account");
        public static final Pair<String, String> AMOUNT_HIGHER_THAN_BALANCE = Pair.of("amount", "Transfer amount can't be higher than current account balance");
        public static final Pair<String, String> AMOUNT_LESS_EQUAL_ZERO = Pair.of("amount", "Transfer amount can't be less of equal zero");
        public static final Pair<String, String> DAILY_LIMIT_OF_TRANSFER_NUMBER_REACHED = Pair.of("transfer_limit", "Daily limit of 3 transfer already reached");
    }

    public static final String SENDER_TRANSFER_NOTIFICATION = "You have sent transfer to account: %s, for amount: %s";

    public static final String RECEIVER_TRANSFER_NOTIFICATION = "You have received transfer on your account: %s, for amount %s";

}
