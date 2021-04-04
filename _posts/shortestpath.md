```import java.util.Scanner;

class graph {
    int n;
    int w[][];
    public graph(int n) {
        this.n = n;
        w = new int[n + 1][n + 1];
    }
    public void input(int i, int j, int k) {
        w[i][j] = k;
        w[j][i] = k;
    }
}

public class ShortestPath {
    public static int[] shortestpath(graph G, int s, int n) {
        int[] D = new int[n+1];
        for(int i=1; i<=n+1; i++) D[i] = Integer.MAX_VALUE;
        D[s] = 0;

        int[] check = new int[n+1];
        check[s] = 1;

        int vmin = Integer.MAX_VALUE;
        for(int i=1; i<n; i++) {
            if(G.w[s][i] != 0) {
                D[i] = G.w[s][i];
                vmin = G.w[s][i] < vmin ? i : vmin;
            }
        }
        check[vmin] = 1;

        for(int i=0; i<n-1; i++) {
            if(check[i] == 0) {

            }
        }

        return D;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int node, line;
        node = scanner.nextInt();
        line = scanner.nextInt();

        graph G = new graph(node);
        for(int a=0; a<line; a++) {
            int i, j, k;
            i = scanner.nextInt();
            j = scanner.nextInt();
            k = scanner.nextInt();
            G.input(i, j, k);
        }

        int[] D = new int[node + 1];
        D = shortestpath(G, 1, node);
        for(int i=1; i<node+1; i++)
            System.out.print(D[i] + " ");
    }
}
```