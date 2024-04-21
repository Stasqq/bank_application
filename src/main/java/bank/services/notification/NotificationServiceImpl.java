package bank.services.notification;

import bank.datamodel.entity.BankUserDetails;
import bank.datamodel.entity.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static bank.common.BankAppConstants.RECEIVER_TRANSFER_NOTIFICATION;
import static bank.common.BankAppConstants.SENDER_TRANSFER_NOTIFICATION;
import static bank.datamodel.NotificationChannelType.EMAIL;
import static bank.datamodel.NotificationChannelType.SMS;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    @Async
    public void asyncNotificationAboutTransfer(Transfer transfer) {
        notifyTransferSender(transfer);
        notifyTransferReceiver(transfer);
    }

    private void notifyTransferSender(Transfer transfer) {
        notifyUser(transfer.getFromAccount().getBankUserDetails(),
                String.format(SENDER_TRANSFER_NOTIFICATION, transfer.getToAccount().getAccountNumber(), transfer.getAmount()));
    }

    private void notifyTransferReceiver(Transfer transfer) {
        notifyUser(transfer.getToAccount().getBankUserDetails(),
                String.format(RECEIVER_TRANSFER_NOTIFICATION, transfer.getToAccount().getAccountNumber(), transfer.getAmount()));
    }

    private void notifyUser(BankUserDetails bankUserDetails, String notificationContent) {
        if (SMS.equals(bankUserDetails.getPreferredNotificationChannel())) {
            notifyBySMS(bankUserDetails.getPhoneNumber(), notificationContent);
        } else if (EMAIL.equals(bankUserDetails.getPreferredNotificationChannel())) {
            notifyByEmail(bankUserDetails.getEmail(), notificationContent);
        }
    }

    private void notifyByEmail(String email, String notificationContent) {
        LOGGER.info("Sending email to email addres: {}, email content: {}", email, notificationContent);
    }

    private void notifyBySMS(String phoneNumber, String notificationContent) {
        LOGGER.info("Sending sms to phone number: {}, message content: {}", phoneNumber, notificationContent);
    }
}
