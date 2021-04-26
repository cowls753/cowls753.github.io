# Matrix Multiplication

대수적으로 행렬의 곱셈은 각각의 원소들을 곱해 더한 값으로 나타낸다.

(사진 -A,B)
두 행렬 **A**, **B**의 곱인 행렬 **C**는 다음과 같이 나타난다.
(사진 -AxB=C)
(사진 -C)
2×2 행렬의 곱을 구하기 위해서 총 8번의 곱셈과 4번의 덧셈이 수행되는 것을 알 수 있다.

------

## Strassen
> 행렬 곱셈 알고리즘 중 하나로, 기존의 곱셈 알고리즘보다 곱셈 연산 횟수를 줄여 시간복잡도를 낮추는 알고리즘이다.


Strassen 알고리즘을 이용하여 두 행렬 **A**, **B**에 대하여 다음과 같이 표현할 수 있다.
(사진 -M1~M7)

Strassen 알고리즘은 이전의 연산을 조금씩 바꾸어 총 7번의 곱셈과 18번의 덧셈, 뺄셈으로 수행되도록 한다.
덧셈, 뺄셈 연산의 횟수를 늘리고 곱셈 연산의 횟수를 줄임으로써 시간복잡도를 감소시킨다.

행렬 **M1**~**M7**은 다음과 같이 나타낼 수 있고, 이러한 방식으로 두 행렬 **A**, **B**의 곱인 **C**를 얻을 수 있다.
(사진 -C11~C22)

------
0. n×n 행렬에 대해 각 행렬을 ⁿ/₂으로 분할하기 때문에 2의 거듭제곱으로 나타낼 수 있는 수로 n을 증가시킨다. (빈자리는 0으로 채운다.) 
``` java
int m;
        m = scanner.nextInt();     //행렬의 크기

        int n = m;
        while(n != (n&-n))       //2의 거듭제곱으로 맞춰주기
            n++;
```

1. 2×2 행렬에 대해서는 기존의 방식으로 곱셈 연산을 수행하도록 한다.
``` java
public static int[][] multiply(int[][] A, int[][] B) {      //2×2 행렬의 곱셈
        int[][] C = new int[2][2];

        C[0][0] = A[0][0]*B[0][0] + A[0][1]*B[1][0];
        C[0][1] = A[0][0]*B[0][1] + A[0][1]*B[1][1];
        C[1][0] = A[1][0]*B[0][0] + A[1][1]*B[1][0];
        C[1][1] = A[1][0]*B[0][1] + A[1][1]*B[1][1];

        return C;
    }
```

2. 두 행렬 **A**, **B**에 대해 재귀적으로 분할하며 행렬 **M1**~**M7**을 정의한다.
``` java
else {        //2×2 행렬이 아닐 경우, 재귀적으로 행렬을 분할
            int[][] t1 = new int[n][n];
            int[][] t2 = new int[n][n];

            for(int i=0; i<n/2; i++) {      //M1
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i][j] + A[i+n/2][j+n/2];
                    t2[i][j] = B[i][j] + B[i+n/2][j+n/2];
                }
            }
            M1 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M2
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i+n/2][j] + A[i+n/2][j+n/2];
                    t2[i][j] = B[i][j];
                }
            }
            M2 = strassen(t1, t2, n/2);
```

3. 두  행렬을 더하거나 빼는 메소드를 정의한다.
``` java
public static int[][] sum(int[][] A, int[][] B, int n) {      //두 행렬을 더하는 메소드
        int[][] C = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                C[i][j] = A[i][j] + B[i][j];      //C=A+B

        return C;
    }

    public static int[][] sub(int[][] A, int[][] B, int n) {      //한 행렬에서 다른 한 행렬을 빼는 메소드
        int[][] C = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                C[i][j] = A[i][j] - B[i][j];      //C=A-B

        return C;
    }
```

4. `sum`메소와 `sub`메소드를 이용해 **M1**~**M7**로부터 **C₁,₁**, **C₁,₂**, **C₂,₁**, **C₂,₂**을 얻고 이를 합쳐 두 행렬의 곱 **C**를 구한다.
``` java
public static void merge(int[][] C, int n) {
        if(n>2) {
            int[][] C11, C12, C21, C22;

            C11 = sum(sum(M1, M4, n/2), sub(M7, M5, n/2), n/2);    //C11=M1+M4-M5+M7
            C12 = sum(M3, M5, n/2);                                      //C12=M3+M5
            C21 = sum(M2, M4, n/2);                                      //C21=M2+M4
            C22 = sum(sub(M1, M2, n/2), sum(M3, M6, n/2),n/2);     //C22=M1-M2+M3+M6

            //C11, C12, C21, C22 행렬의 합병
            for(int i=0; i<n/2; i++)
                for(int j=0; j<n/2; j++)
                    C[i][j] = C11[i][j];

            for(int i=0; i<n/2; i++)
                for(int j=n/2; j<n; j++)
                    C[i][j] = C12[i][j-n/2];

            for(int i=n/2; i<n; i++)
                for(int j=0; j<n/2; j++)
                    C[i][j] = C21[i-n/2][j];

            for(int i=n/2; i<n; i++)
                for(int j=n/2; j<n; j++)
                    C[i][j] = C22[i-n/2][j-n/2];
        }
    }
```

------
전체 코드와 실행 결과는 다음과 같다.
``` java
import java.util.Scanner;

public class Strassen {
    static int[][] M1, M2, M3, M4, M5, M6, M7;

    public static int[][] multiply(int[][] A, int[][] B) {      //2×2 행렬의 곱셈
        int[][] C = new int[2][2];

        C[0][0] = A[0][0]*B[0][0] + A[0][1]*B[1][0];
        C[0][1] = A[0][0]*B[0][1] + A[0][1]*B[1][1];
        C[1][0] = A[1][0]*B[0][0] + A[1][1]*B[1][0];
        C[1][1] = A[1][0]*B[0][1] + A[1][1]*B[1][1];

        return C;
    }

    public static int[][] strassen(int[][] A, int[][] B, int n) {
        int[][] C = new int[n][n];

        if(n==2)      //2×2 행렬일 경우
            C = multiply(A, B);
        else {        //2×2 행렬이 아닐 경우, 재귀적으로 행렬을 분할
            int[][] t1 = new int[n][n];
            int[][] t2 = new int[n][n];

            for(int i=0; i<n/2; i++) {      //M1
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i][j] + A[i+n/2][j+n/2];
                    t2[i][j] = B[i][j] + B[i+n/2][j+n/2];
                }
            }
            M1 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M2
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i+n/2][j] + A[i+n/2][j+n/2];
                    t2[i][j] = B[i][j];
                }
            }
            M2 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M3
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i][j];
                    t2[i][j] = B[i][j+n/2] - B[i+n/2][j+n/2];
                }
            }
            M3 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M4
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i+n/2][j+n/2];
                    t2[i][j] = B[i+n/2][j] - B[i][j];
                }
            }
            M4 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M5
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i][j] + A[i][j+n/2];
                    t2[i][j] = B[i+n/2][j+n/2];
                }
            }
            M5 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M6
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i+n/2][j] - A[i][j];
                    t2[i][j] = B[i][j] + B[i][j+n/2];
                }
            }
            M6 = strassen(t1, t2, n/2);

            for(int i=0; i<n/2; i++) {     //M7
                for(int j=0; j<n/2; j++) {
                    t1[i][j] = A[i][j+n/2] - A[i+n/2][j+n/2];
                    t2[i][j] = B[i+n/2][j] + B[i+n/2][j+n/2];
                }
            }
            M7 = strassen(t1, t2, n/2);
        }

        return C;
    }

    public static int[][] sum(int[][] A, int[][] B, int n) {      //두 행렬을 더하는 메소드
        int[][] C = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                C[i][j] = A[i][j] + B[i][j];      //C=A+B

        return C;
    }

    public static int[][] sub(int[][] A, int[][] B, int n) {      //한 행렬에서 다른 한 행렬을 빼는 메소드
        int[][] C = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                C[i][j] = A[i][j] - B[i][j];      //C=A-B

        return C;
    }

    public static void merge(int[][] C, int n) {
        if(n>2) {
            int[][] C11, C12, C21, C22;

            C11 = sum(sum(M1, M4, n/2), sub(M7, M5, n/2), n/2);    //C11=M1+M4-M5+M7
            C12 = sum(M3, M5, n/2);                                      //C12=M3+M5
            C21 = sum(M2, M4, n/2);                                      //C21=M2+M4
            C22 = sum(sub(M1, M2, n/2), sum(M3, M6, n/2),n/2);     //C22=M1-M2+M3+M6

            //C11, C12, C21, C22 행렬의 합병
            for(int i=0; i<n/2; i++)
                for(int j=0; j<n/2; j++)
                    C[i][j] = C11[i][j];

            for(int i=0; i<n/2; i++)
                for(int j=n/2; j<n; j++)
                    C[i][j] = C12[i][j-n/2];

            for(int i=n/2; i<n; i++)
                for(int j=0; j<n/2; j++)
                    C[i][j] = C21[i-n/2][j];

            for(int i=n/2; i<n; i++)
                for(int j=n/2; j<n; j++)
                    C[i][j] = C22[i-n/2][j-n/2];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int m;
        m = scanner.nextInt();     //행렬의 크기

        int n = m;
        while(n != (n&-n))       //2의 거듭제곱으로 맞춰주기
            n++;

        int[][] A = new int[n][n];
        int[][] B = new int[n][n];

        for(int i=0; i<m; i++)         //A행렬
            for (int j=0; j<m; j++)
                A[i][j] = scanner.nextInt();

        for(int i=0; i<m; i++)        //B행렬
            for (int j=0; j<m; j++)
                B[i][j] = scanner.nextInt();

        int[][] C;
        C = strassen(A, B, n);        //C=A×B
        merge(C, n);

        for(int i=0; i<m; i++) {      //결과값 출력
            for(int j=0; j<m; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
```
(사진 -실행 결과)

------

## Performance Evaluation

``` java
import java.util.Scanner;

public class MatrixMultiplication {
    public static int[][] multiplication(int[][] A, int[][] B, int n) {
        int[][] C = new int[n][n];       //행렬 A와 행렬 B의 곱셈 결과를 저장할 행렬 C

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                for(int k=0; k<n; k++)
                    C[i][j] += A[i][k] * B[k][j];      //원소들을 하나하나 곱하고 더하는 방식으로 구하기

        return C;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();     //정방행렬의 크기

        int[][] A = new int[n][n];
        int[][] B = new int[n][n];

        for(int i=0; i<n; i++)         //A행렬
            for (int j=0; j<n; j++)
                A[i][j] = scanner.nextInt();

        for(int i=0; i<n; i++)         //B행렬
            for (int j=0; j<n; j++)
                B[i][j] = scanner.nextInt();

        int[][] C;
        C = multiplication(A, B, n);   //C=A×B

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
```
위와 같은 기존적인 행렬 곱셈 알고리즘과 Strassen 알고리즘을 비교해보았다.

(사진 -그래프)
(그래프 설명)

------

## Error

구현한 Strassen 알고리즘은 미완성된 알고리즘으로, 이는 크기가 큰 배열의 곱셈에 대해서는 정상적으로 결과값을 구하지 못한다.
2×2, 3×3, 4×4 행렬의 곱셈에 대해서만 가능하며, 이를 보완하기 위한 부분적인 코드 수정이 필요하다.