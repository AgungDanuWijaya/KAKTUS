package jqc;

import java.util.HashMap;
import function.main_function;
import grid.grid;
import tools.matrix_operation;

/**
 *
 * @author Agung Danu Wijaya
 */
public class exc_functional {

    public double Vxc[][];
    public double Exc;

    public void LDA(double P[][], grid a, main_function kernel, double datagrid[][],
            HashMap<Integer, HashMap<Integer, double[]>> points) {
        double rho[] = new double[datagrid.length];
        for (int p = 0; p < datagrid.length; p++) {
            for (int i = 0; i < datagrid[p].length; i++) {
                for (int j = 0; j < datagrid[p].length; j++) {
                    rho[p] += datagrid[p][i] * datagrid[p][j] * P[i][j];
                }
            }
        }

        double alpha = 2.0 / 3.0;
        double fac = -2.25 * alpha * Math.pow(0.75 / Math.PI, 1. / 3.);
        double rho3[] = kernel.mp.powdot(rho, 1. / 3.);
        double Fx[] = kernel.mp.mdot(kernel.mp.mdot(rho, rho3), fac);
        double[] dFxdn = kernel.mp.mdot(rho3, (4. / 3.) * fac);

        HashMap<Integer, double[]> pointmap = a.pointsmap(points);
        double Vxc[][] = new double[datagrid[0].length][datagrid[0].length];
        for (int p = 0; p < datagrid.length; p++) {
            for (int i = 0; i < datagrid[p].length; i++) {
                for (int j = 0; j < datagrid[p].length; j++) {
                    double RG[] = pointmap.get(p);
                    Vxc[i][j] += RG[3] * dFxdn[p] * datagrid[p][i] * datagrid[p][j];
                }
            }
        }
        double w[] = new double[datagrid.length];
        for (int p = 0; p < datagrid.length; p++) {
            double RG[] = pointmap.get(p);
            w[p] = RG[3];
        }
        double Exc = kernel.mp.sum(kernel.mp.mdot(w, kernel.mp.mdot(Fx, 2)));
        this.Exc = Exc;
        this.Vxc = Vxc;
    }
 public void GGR1(double P[][], grid a, main_function kernel, double datagrid[][], double datagrid_kinetik[][],
            HashMap<Integer, HashMap<Integer, double[]>> points) {
        double rho[] = new double[datagrid.length];

        HashMap<Integer, double[]> pointmap = a.pointsmap(points);
        double ek[] = new double[datagrid.length];
        for (int p = 0; p < datagrid.length; p++) {
            double r[] = pointmap.get(p);
            for (int i = 0; i < datagrid[p].length; i++) {
                for (int j = 0; j < datagrid[p].length; j++) {
                    ek[p] += r[3] * datagrid[p][i] * datagrid_kinetik[p][j] * P[i][j];
                    //   System.out.println(datagrid_kinetik[p][j]);
                }
            }
        }

        double etot = 0;
        double v_ggr[][] = new double[datagrid[0].length][datagrid[0].length];
        for (int p_ = 0; p_ < datagrid.length; p_++) {
            double r_[] = pointmap.get(p_);
            for (int p = 0; p < datagrid.length; p++) {
                double r[] = pointmap.get(p);
                double besarr = Math.pow(r_[0] - r[0], 2) + Math.pow(r_[1] - r[1], 2) + Math.pow(r_[2] - r[2], 2);
                besarr = Math.pow(besarr, 0.5);
                
               // if (besarr > Math.pow(1.0/Math.abs(rho[p]), 1.0/3.0)) {
                       if (besarr > 0) {
                    if (besarr < 1) {
                        for (int i_ = 0; i_ < datagrid[p_].length; i_++) {
                            for (int j_ = 0; j_ < datagrid[p_].length; j_++) {
                                v_ggr[i_][j_] += Math.pow(10, -9.5) * r_[3] * datagrid[p_][i_] * (ek[p] / Math.pow(besarr, 1)) * datagrid[p_][j_] * P[i_][j_];
                                etot += v_ggr[i_][j_];
                            }
                        }
                    }
                }

            }
        }
        // this.Exc = etot;
        //this.Vxc = v_ggr;
        this.Exc += etot;
        this.Vxc = new matrix_operation().adddot(this.Vxc, v_ggr);

    }
    public void ann(double P[][], grid a, main_function kernel, double datagrid[][],
            HashMap<Integer, HashMap<Integer, double[]>> points) {
        double rho[] = new double[datagrid.length];
        for (int p = 0; p < datagrid.length; p++) {
            for (int i = 0; i < datagrid[p].length; i++) {
                for (int j = 0; j < datagrid[p].length; j++) {
                    rho[p] += datagrid[p][i] * datagrid[p][j] * P[i][j];
                }
            }
        }

        double delta = Math.pow(10, -7);
        //define your functional========================
        double Fx[] = kernel.ex_ann.Ex(kernel, rho);
        //==============================================		
        double[] dFxdn = kernel.mp.mdot(kernel.mp.adddot(kernel.mp.mdot(Fx, -1), kernel.ex_ann.Ex(kernel, kernel.mp.adddot(rho, delta))),
                1.0 / delta);
        HashMap<Integer, double[]> pointmap = a.pointsmap(points);
        double Vxc[][] = new double[datagrid[0].length][datagrid[0].length];
        for (int p = 0; p < datagrid.length; p++) {
            for (int i = 0; i < datagrid[p].length; i++) {
                for (int j = 0; j < datagrid[p].length; j++) {
                    double RG[] = pointmap.get(p);
                    Vxc[i][j] += RG[3] * dFxdn[p] * datagrid[p][i] * datagrid[p][j];
                }
            }
        }
        double w[] = new double[datagrid.length];
        for (int p = 0; p < datagrid.length; p++) {
            double RG[] = pointmap.get(p);
            w[p] = RG[3];
        }
        double Exc = kernel.mp.sum(kernel.mp.mdot(w, kernel.mp.mdot(Fx, 2)));
        this.Exc = Exc;
        this.Vxc = Vxc;
    }
}
