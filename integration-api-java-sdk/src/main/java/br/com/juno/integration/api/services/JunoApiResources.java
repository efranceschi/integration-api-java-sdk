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

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    private DataService dataService = new DataService();
    private ChargeService chargeService = new ChargeService();
    private BalanceService balanceService = new BalanceService();
    private AuthorizationService authorizationService = new AuthorizationService();
}
