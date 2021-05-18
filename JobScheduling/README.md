# Job Scheduling

작업 스케줄링 문제는 *n*개의 작업과 각 작업의 수행 시간 *t*, 그리고 *m*개의 동일한 기계가 주어질 때,     
모든 작업이 가장 빨리 종료되도록 작업을 기계에 배정하는 문제이다.     

--------
+ 입력 : *n*개의 작업, 각 작업의 수행 시간 *t*, *m*개의 기계
+ 출력 : 모든 작업이 종료된 시간     
      
1. 각 기계에 배정된 마지막 작업의 종료 시간 `L[j]`를 0으로 초기화 시킨다.

2. `for` 루프를 이용해 각 기계의 마지막 작업의 종료 시간을 검사하여 가장 일찍 끝나는 기계 번호인 `min`을 찾는다.

3. 작업을 `min`번 기계에 배정하고, 작업의 수행 시간을 더하여 종료 시간을 갱신한다.

4. 배열 `L`에서 가장 큰 값을 찾아 `return`한다.

전체 코드는 아래와 같다.     

```java
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
```
--------
## 시간복잡도
> *n* × Ο(*m*) + Ο(*m*) = Ο(*nm*)

*n*개의 작업을 하나씩 배정할 때 *m*개의 기계들 중 가장 빨리 끝낼 수 있는 기계를 찾기 위해 `for`루프가 (*m*-1)번 수행된다.
모든 기계의 마지막 작업 종료 시간을 살펴보아야 하므로 Ο(*m*) 시간이 걸린다.
*n*개의 작업을 배정해야 하므로 *n* × Ο(*m*) 시간이 걸리며, 마지막으로 종료 시간이 가장 큰 값을 탐색해야 하기 때문에 *n* × Ο(*m*) + Ο(*m*) = Ο(*nm*) 시간이 걸린다.     

--------
## 근사비율
> 작업 스케줄링 알고리즘의 근사해를 **OPT**'라 하고, 최적해를 **OPT**라고 할 때, **OPT' ≤ 2×OPT** 이다.

작업의 개수 *n*은 4, 8, 16이며, 기계의 개수 *m*은 모두 2로 같다.     
각 작업들의 수행 시간은 10초 이내의 랜덤값이다.

![제목 없음](https://user-images.githubusercontent.com/80511210/118661347-fcd63a00-b829-11eb-893a-b8cc58af83d3.png)

먼저, 작업의 개수가 4, 8, 16일 때의 알고리즘의 실행 시간(ms)은 각각 12, 13, 15로 나타났다.     
*n*의 개수가 증가함에 따라 실행 시간도 함께 증가하는 것을 확인할 수 있다.     
     
**OPT**'는 작업 스케줄링 알고리즘의 출력값, 즉 가장 늦은 작업의 종료 시간으로 구할 수 있다.     
작업의 개수가 4, 8, 16일 때의 **OPT**'는 11, 25, 43으로 나타났고, 작업의 개수는 증가하지만 이를 배정할 기계의 개수는 2로 같기 때문에 위와 같은 결과가 나타남을 확인할 수 있다.     
     
**OPT**는 모든 작업의 수행 시간의 합을 기계의 수 *m*으로 나누어 값을 구했다.     
작업의 개수가 4, 8, 16일 때의 **OPT**는 10, 24.5, 40으로 나타났다.     

따라서 작업의 개수가 각각 4, 8, 16일 때 모두 **OPT' ≤ 2×OPT**의 관계가 성립함을 확인할 수 있다.     
또한, **OPT**'와 **OPT**의 값을 비교하여 해당 작업 스케줄링 알고리즘의 근사 비율을 구한 결과,     
작업의 개수가 4, 8, 16일 때 근사비율이 각각 1.1, 1.02, 1.075으로 1.0에 가깝게 나타났고, 해당 알고리즘은 정확도가 높은 알고리즘임을 확인할 수 있다.     
