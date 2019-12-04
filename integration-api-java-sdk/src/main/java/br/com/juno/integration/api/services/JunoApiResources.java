package br.com.juno.integration.api.services;

public final class JunoApiResources {

    public DataService getDataService() {
        return dataService;
    }

    public ChargeService getChargeService() {
        return chargeService;
    }

    public BalanceService getBalanceService() {
        return balanceService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public TransferService getTransferService() {
        return transferService;
    }

    public CreditCardService getCreditCardService() {
        return creditCardService;
    }

    public BillPaymentService getBillPaymentService() {
        return billPaymentService;
    }

    public CredentialsService getCredentialsService() {
        return credentialsService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public DigitalAccountService getDigitalAccountService() {
        return digitalAccountService;
    }

    private DataService dataService = new DataService();
    private ChargeService chargeService = new ChargeService();
    private BalanceService balanceService = new BalanceService();
    private PaymentService paymentService = new PaymentService();
    private DocumentService documentService = new DocumentService();
    private TransferService transferService = new TransferService();
    private CreditCardService creditCardService = new CreditCardService();
    private BillPaymentService billPaymentService = new BillPaymentService();
    private CredentialsService credentialsService = new CredentialsService();
    private NotificationService notificationService = new NotificationService();
    private AuthorizationService authorizationService = new AuthorizationService();
    private DigitalAccountService digitalAccountService = new DigitalAccountService();
}