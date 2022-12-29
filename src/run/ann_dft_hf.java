package run;

import Interface.Interface;
import java.util.HashMap;
import function.main_function;

public class ann_dft_hf {

    Interface a;

    public ann_dft_hf(Interface a) {
        this.a = a;
        main_function kernel = new main_function();
        this.kernel = kernel;
    }
    run main;
    main_function kernel;
    public HashMap<Integer, Double> jacobian = new HashMap<Integer, Double>();

    public void energi() throws ClassNotFoundException {
        kernel.status_int = 1;
        if (this.a.int_stat.isSelected() == false) {
            kernel.status_int = 0;
        }
        kernel.a = this.a;
        run main = new run(kernel);
        this.main = main;
        double r = 0;
        String name = a.data_geo.getText().split(";")[1];
        prepare_cmn();
        kernel.c_mix = Double.parseDouble(kernel.a.mix.getText());
        System.out.println("hh"+kernel.opti);
        r = main.run_scf(name, "DFT_ann_ann");
        System.out.println(r);
    }

    public void energi_dft() throws ClassNotFoundException {
        kernel.status_int = 1;
        if (this.a.int_stat.isSelected() == false) {
            kernel.status_int = 0;
        }
        kernel.a = this.a;
        run main = new run(kernel);
        this.main = main;
        double r = 0;
        String name = a.data_geo.getText().split(";")[1];
        prepare_cmn();
        kernel.c_mix = Double.parseDouble(kernel.a.mix.getText());
        r = main.run_scf(name, "DFT_ann");
        System.out.println(r);
    }

    public void energi_hf() throws ClassNotFoundException {
        kernel.status_int = 1;
        if (this.a.int_stat.isSelected() == false) {
            kernel.status_int = 0;
        }
        kernel.a = this.a;
        run main = new run(kernel);
        this.main = main;
        double r = 0;
        String name = a.data_geo.getText().split(";")[1];
        prepare_cmn();       
        r = main.run_scf(name, "HF");
        System.out.println(r);
    }

    public void prepare_cmn() throws ClassNotFoundException {
        this.kernel.tipebasis = a.basis.getSelectedItem().toString();
        this.kernel.verbose = 1; // 1 tambilkan proses
        String j[] = kernel.a.ann_conf.getText().split(",");
        int simpul[] = new int[j.length];
        int jk = 0;
        for (Object object : j) {
            simpul[jk++] = Integer.parseInt(object.toString());
        }
        this.kernel.c_node = simpul;
        this.kernel.c_node = simpul;
        this.kernel.URL_ANN = kernel.a.base.getText() + "/"+kernel.a.exc_name.getText();
        this.kernel.Ex = a.exc_tipe.getSelectedItem().toString();
        this.kernel.c_mix = 0.9;
        this.kernel.weight = new double[2][][][];
        main.init();
    }

}
