import java.util.Scanner;
import java.util.Random;

public class JobScheduling {
    public static int jobscheduling(int[] job, int n, int m) {
        int[] L = new int[m];
        int min = 0;

        for(int i=0; i<m; i++)
            L[i] = 0;

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++)
                if(L[j]<L[min])
                    min = j;
            L[min] += job[i];
        }

        int max = 0;
        for(int i=0; i<m; i++)
            if(L[i]>max)
                max = L[i];

        return max;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] job = new int[n];
        for(int i=0; i<n; i++)
            job[i] = random.nextInt(10)+1;

        int finish = jobscheduling(job, n, m);
        System.out.println("가장 늦은 작업의 종료 시간은 " + finish);


        scanner.close();
    }
}
