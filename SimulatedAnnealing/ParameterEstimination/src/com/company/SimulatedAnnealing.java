package com.company;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    private int niter;
    public ArrayList<Double> hist;

    public SimulatedAnnealing(int niter) {
        this.niter = niter;
        hist = new ArrayList<>();
    }

    public double[] solve(Problem p, double t, double a, double lower, double upper) {
        Random r = new Random();
        double[] x0 = {r.nextDouble() * (upper - lower) + lower, r.nextDouble() * (upper - lower) + lower, r.nextDouble() * (upper - lower) + lower};   //범위 내에서 랜덤한 수 x0
        return solve(p, t, a, x0, lower, upper);
    }

    public double[] solve(Problem p, double t, double a, double[] x0, double lower, double upper) {
        Random r = new Random();
        double f0 = p.fit(x0);
        hist.add(f0);

        for (int i=0; i<niter; i++) {
            int kt = (int) t;
            for(int j=0; j<kt; j++) {
                double[] x1 = {r.nextDouble() * (upper - lower) + lower, r.nextDouble() * (upper - lower) + lower, r.nextDouble() * (upper - lower) + lower};    //범위 내에서 랜덤한 수 x1
                double f1 = p.fit(x1);

                if(p.isNeighborBetter(f0, f1)) {    //f0이 f1 보다 우수해일 경우
                    x0 = x1;
                    f0 = f1;
                    hist.add(f0);
                } else {      //f1이 f0 보다 우수해일 경우
                    double d = Math.sqrt(Math.abs(f1 - f0));
                    double p0 = Math.exp(-d/t);      //탐색이 자유로워질 확률 p
                    if(r.nextDouble() < p0 * 0.001) {     //0~1의 수가 확률 p내에 들 경우 (임의로 0.001 곱해줌)
                        x0 = x1;
                        f0 = f1;
                        hist.add(f0);     //나쁜 이웃해로 탐색
                    }
                }
            }
            t *= a;
        }
        return x0;
    }
}
