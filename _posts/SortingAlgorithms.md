# Sorting Algorithms     
     
## 1. Bubble Sort     
+ 이웃하는 숫자를 비교하여 작은 수를 앞쪽으로 이동시키는 과정을 반복하여 정렬하는 알고리즘.   
+ 오름차순으로 정렬한다면, 작은 수는 배열의 앞부분으로 이동하는데, 배열을 좌우가 아니라 상하로 그려보면 정렬하는 과정에서작은 수가 마치 '거품'처럼 위로 올라가는 것을 연상케 한다.       

```java
import java.util.Scanner;

public class BubbleSort {
    static void bubblesort(int[] a, int n) {
        int temp;

        for (int i = 0; i < n; i++)
            for (int j = 1; j < n - i; j++)
                if (a[j] < a[j - 1]) {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;
        n = scanner.nextInt();

        int[] a = new int[n];

        for(int i=0; i<n; i++)
            a[i] = scanner.nextInt();

        bubblesort(a, n);

        for(int i=0; i<n; i++)
            System.out.print(a[i] + " ");


        scanner.close();
    }
}
```
     
## Performance Evaluation
> BubbleSort의 시간복잡도는 *n*(*n*-1)/2 × O(1) = O(*n*2) × O(1) = O(*n*2) 이다.


## 2. Selection Sort     
+ 입력 배열 전체에서 최솟값을 '선택'하여 배열의 0번 원소와 자리를 바꾸고, 다음엔 0번 원소를 제외한 나머지 원소에서 최솟값을 선택하여 배열의 1번 원소와 자리를 바꾼다.
+ 이러한 방식으로 마지막에 2개의 원소 중 최솟값을 선택하여 자리를 바꿈으로써 오름차순의 정렬을 마친다.     

```java
import java.util.Scanner;

public class SelectionSort {
    static void selectionsort(int[] a, int n) {
        int temp;

        for(int i=0; i<n-1; i++) {
            int min = i;
            for(int j=i; j<n; j++)
                if(a[min]>a[j])
                    min = j;

            temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }

    static public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;
        n = scanner.nextInt();

        int[] a = new int[n];

        for(int i=0; i<n; i++)
            a[i] = scanner.nextInt();

        selectionsort(a,n);

        for(int i=0; i<n; i++)
            System.out.print(a[i] + " ");


        scanner.close();
    }
}
```
     
## Performance Evaluation
> SelectionSort의 시간복잡도는 *n*(*n*-1)/2 × O(1) = O(*n*2) × O(1) = O(*n*2) 이다.


## 3. Insertion Sort     
+ 배열을 정렬된 부분(앞부분)과 정렬되지 않은 부분(뒷부분)으로 나누고, 정렬되지 않은 부분의 가장 왼쪽 원소를 정렬된 부분의 적절한 위치에 삽입하여 정렬하는 과정을 반복한다.     

```java
import java.util.Scanner;

public class InsertionSort {
    static public void insertionsort(int[] a, int n) {
        int current;

        for(int i=1; i<n; i++) {
            current = a[i];
            int j;
            for(j=i-1; j>=0; j--) {
                if(a[j]>current) {
                    a[j+1] = a[j];
                }
                else
                    break;
            }
            a[j+1] = current;
        }
    }

    static public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;
        n = scanner.nextInt();

        int[] a = new int[n];

        for(int i=0; i<n; i++)
            a[i] = scanner.nextInt();

        insertionsort(a,n);

        for(int i=0; i<n; i++)
            System.out.print(a[i] + " ");

        scanner.close();
    }
}
```
     
## Performance Evaluation
> InsertionSort의 시간복잡도는 *n*(*n*-1)/2 × O(1) = O(*n*2) × O(1) = O(*n*2) 이다.


## 4. Shell Sort     
+ BubbleSort와 InsertionSort의 단점을 보완하기 위해 InsertionSort를 이용하여 배열 뒷부분의 작은 숫자를 앞부분으로 '빠르게' 이동시키고, 동시에 앞부분의 큰 숫자는 뒷부분으로 이동시키고, 가장 마지막에는 InsertionSort를 수행하는 알고리즘.        

```java
import java.util.Scanner;

public class ShellSort {
    static public void insertionsort(int[] a, int n, int g, int start) {
        int current;

        for(int i=start+g; i<n; i+=g) {
            current = a[i];
            int j;
            for(j=i-g; j>=0; j-=g) {
                if(a[j]>current) {
                    a[j+g] = a[j];
                }
                else
                    break;
            }
            a[j+g] = current;
        }
    }

    static public void shellsort(int[] a, int n) {
        int g1 = 5, g2 = 3;

        for(int i=0; i<g1; i++)
            insertionsort(a, n, g1, i);

        for(int i=0; i<g2; i++)
            insertionsort(a, n, g2, i);

        insertionsort(a, n, 1, 0);
    }

    static public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;
        n = scanner.nextInt();

        int[] a = new int[n];

        for(int i=0; i<n; i++)
            a[i] = scanner.nextInt();

        shellsort(a,n);

        for(int i=0; i<n; i++)
            System.out.print(a[i] + " ");


        scanner.close();
    }
}
```
     
## Performance Evaluation
> ShellSort의 시간복잡도는 간격 선정에 따라 좌우되며, 최악의 경우 O(*n*2) 이다.