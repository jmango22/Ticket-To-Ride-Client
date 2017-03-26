package goldenhammer.ticket_to_ride_client.ui.play;


import android.content.res.Resources;

import java.lang.reflect.Field;

/**
 * Created by rache on 3/24/2017.
 */

public class EndGamePresenter implements IEndGamePresenter {
    EndGameActivity owner;
    EndGamePresenter(EndGameActivity owner){
        this.owner = owner;
    }
    @Override
    public void setInfo() {
       /*try {
            Class c = Resources.get;
            Field field = c.getDeclaredField("");
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        //owner.findViewById()*/
    }

    @Override
    public void buttonClicked() {

    }
}
