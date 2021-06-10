package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimulatedAnnealing sa = new SimulatedAnnealing(10);

        double[] tem = {-3.6, 0.4, 6.3, 13.8, 18.7, 23.2, 25.5, 26.8, 21.5, 14.4, 5.9, 0.3}; //평균 기온 데이터

        Problem p = new Problem() {
            @Override
            public double fit(double[] x) {
                double sum = 0;
                for(int i=0; i<12; i++) {
                    double fun;
                    fun = (x[0] * i * i) + (x[1] * i) + x[2];   //ax^2+bx+c
                    double result;
                    result = tem[i] - fun;     //오차 계산
                    if(result < 0)
                        result *= -1;
                    sum += result;
                }
                return sum;
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 > f1;    //최대값 or 최소값
            }
        };

        //탐색 범위 사용자로부터 입력
        int lower = scanner.nextInt();
        int upper = scanner.nextInt();

        double[] x = sa.solve(p, 50000, 0.99, lower, upper);
        System.out.println(x[0] + " " + x[1] + " " + x[2]);
        System.out.println(p.fit(x));
        System.out.println(sa.hist);
    }
}
