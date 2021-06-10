package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimulatedAnnealing sa = new SimulatedAnnealing(10);

        int n;
        n = scanner.nextInt();     //최고차항

        double[] coe = new double[n+1];
        for(int i=0; i<n+1; i++) {
            coe[i] = scanner.nextDouble();     //각 항의 계수
        }

        Problem p = new Problem() {
            @Override
            public double fit(double x) {
                double sum = 0;
                for(int i=0; i<n+1; i++) {
                    double tmp = 1;
                    for(int j=n-i; j>0; j--) {
                        tmp *= x;
                    }
                    tmp *= coe[i];
                    sum += tmp;
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

        double x = sa.solve(p, 100, 0.99, lower, upper);
        System.out.println(x);
        System.out.println(p.fit(x));
        System.out.println(sa.hist);
    }
}