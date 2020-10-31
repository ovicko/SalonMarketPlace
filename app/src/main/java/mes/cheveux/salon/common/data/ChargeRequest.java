package mes.cheveux.salon.common.data;


public class ChargeRequest {
    final String nonce;

    public ChargeRequest(String nonce) {
        this.nonce = nonce;
    }

    public  class ChargeErrorResponse {
        String errorMessage;
    }
}