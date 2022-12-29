package jqc;

import java.util.HashMap;
import java.util.Map;

import basis.data_json;
import function.main_function;
import geo_molecule.data_geo;
import run.driver_abinitio;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getdata {

	main_function kernel;

	public getdata(main_function kernel) {
		this.kernel = kernel;
	}

	public class datakHF {

		public String discibe; // discribe number electron, atom's name, state
		public double[] R; // position atom
		public double[] c;
		public double[] l;
		public double[] alpa;
		public double batom;

		public datakHF(String discribe, double[] R, double[] c, double[] l, double[] alpa, double batom) {
			this.discibe = discribe;
			this.R = R;
			this.c = c;
			this.l = l;
			this.alpa = alpa;
			this.batom = batom;
		}
	}

	public Map<Integer, datakHF> get(String namageo) {
		Map<Integer, datakHF> datahf = new HashMap<>();
		data_geo geo = kernel.geo.data.get(namageo);
		double[][] R = geo.R;
		int[] batom = geo.numProton;
		String[] naom = geo.atom;
		int index = 0;
		for (int k = 0; k < batom.length; k++) {
			kernel.basisdata_json.data_Json_get(batom[k], kernel);
			data_json.basis[] dbasis = kernel.basisdata_json.data.get(batom[k]);
			for (data_json.basis dbasi : dbasis) {
				double[][] coefexp = dbasi.b;
				String kulit = dbasi.a;
				double lmn[][] = kernel.pkt.data.get(kulit);
				double[] coef = new double[coefexp.length];
				double[] exp = new double[coefexp.length];
				for (int i = 0; i < coefexp.length; i++) {
					coef[i] = coefexp[i][1];
					exp[i] = coefexp[i][0];
				}
				for (int i = 0; i < lmn.length; i++) {
					datahf.put(index,
							new datakHF(batom[k] + " " + naom[k] + " " + kulit, R[k], coef, lmn[i], exp, batom[k]));
					index++;
				}
			}
		}
		kernel.num_orbital = datahf.size();
		return datahf;
	}

	public static void main(String[] args) {
		main_function kernel = new main_function();
		String name_molecule = "H2O";
		double[][] R = { { 0.0000, 0.0000, 0.1173 }, { 0.0000, 0.7572, -0.4692 }, { 0.0000, 0.7572, -0.4692 } };
		int numE[] = { 1, 1, 8 };
		String atom[] = new String[numE.length];
		for (int i = 0; i < atom.length; i++) {
			atom[i] = kernel.atom[numE[i] - 1];
		}
		data_geo mole = new data_geo(numE, atom, kernel.mp.mdot(R, kernel.geo.bondLenght));
		kernel.geo.data.put(name_molecule, mole);
		kernel.tipebasis = "sto-3g";

		getdata b = new getdata(kernel);
		Map<Integer, datakHF> datahf = b.get("H2O");
		for (int i = 0; i < datahf.size(); i++) {
			kernel.mp.disp(datahf.get(i).discibe);
			kernel.mp.disp(datahf.get(i).R);
			kernel.mp.disp(datahf.get(i).alpa);
			kernel.mp.disp(datahf.get(i).l);
			kernel.mp.disp(datahf.get(i).c);
			kernel.mp.disp("------------");
		}
	}

}
