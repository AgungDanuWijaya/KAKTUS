package run;

import Interface.Interface;
import com.google.gson.Gson;

/**
 *
 * @author Agung Danu Wijaya
 */
public class inversi_driver {

    public void manis(Interface gui) throws ClassNotFoundException {
        inversi a = new inversi(gui);
        Gson gson = new Gson();
        param_inv init = gson.fromJson(gui.data_geo.getText(), param_inv.class);

        a.run_train(init.name, init.re,init.simpul,init.name_ann);
        //a.test_all();
        //a.TE();
    }

}
