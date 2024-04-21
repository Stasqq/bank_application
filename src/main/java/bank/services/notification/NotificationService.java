package bank.services.notification;

import bank.datamodel.entity.Transfer;

public interface NotificationService {

    void asyncNotificationAboutTransfer(Transfer transfer);

}
