package mes.cheveux.salon.data.cart;


import android.content.res.Resources;
import android.util.Log;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.data.ChargeCall;
import mes.cheveux.salon.data.booking.BookingForm;
import sqip.Call;
import sqip.CardDetails;
import sqip.CardEntryActivityCommand;
import sqip.CardNonceBackgroundHandler;

public class CardEntryBackgroundHandler implements CardNonceBackgroundHandler {
    private final ChargeCall.Factory chargeCallFactory;
    private final Resources resources;
    private final BookingForm bookingForm;

    public CardEntryBackgroundHandler(ChargeCall.Factory chargeCallFactory,
                                      Resources resources,BookingForm bookingForm) {
        this.chargeCallFactory = chargeCallFactory;
        this.resources = resources;
        this.bookingForm = bookingForm;
    }

    @Override
    public CardEntryActivityCommand handleEnteredCardInBackground(CardDetails cardDetails) {
        if (bookingForm != null) {
            Log.i("BOOKING_FORM","salon id "+bookingForm.getSalonId());
        } else {
            Log.i("BOOKING_FORM","NOT FOUND salon id ");
        }

        Call<ChargeResult> chargeCall = chargeCallFactory.create(cardDetails.getNonce(),bookingForm);
        ChargeResult chargeResult = chargeCall.execute();

        if (chargeResult.success) {
            return new CardEntryActivityCommand.Finish();
        } else if (chargeResult.networkError) {
            return new CardEntryActivityCommand.ShowError(resources.getString(R.string.network_failure));
        } else {
            return new CardEntryActivityCommand.ShowError(chargeResult.errorMessage);
        }
    }


}
