import java.io.*;
import java.util.*;

/*
 * 14044kb 100ms
 */
public class Main {
    static class Edge {
        int v; // 다음 정점
        int weight; // 가중치

        Edge(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 1행 : 정점의 수
        int N = Integer.parseInt(br.readLine());

        // 2행 : 간선의 수
        int M = Integer.parseInt(br.readLine());

        // M행 : 간선정보
        List<List<Edge>> graph = new ArrayList<>(); // 인접리스트
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[N + 1]; // 진입차수
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int X = Integer.parseInt(st.nextToken()); // v
            int Y = Integer.parseInt(st.nextToken()); // u
            int K = Integer.parseInt(st.nextToken()); // weight
            graph.get(Y).add(new Edge(X, K));
            indegree[X]++;
        }

        // 위상정렬
        Queue<Integer> queue = new ArrayDeque<>(); // Khan's 알고리즘
        int[][] dp = new int[N + 1][N + 1]; // dp[v][u] : u -> v에 필요한 weight 합
        for (int u = 1; u <= N; u++) {
            if (indegree[u] == 0) {
                queue.offer(u);
                dp[u][u] = 1; // dp[u][u] : u -> u 자기 자신은 가중치 1
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (Edge next : graph.get(u)) { // u -> v의 간선정보 next
                // 점화식 : 중간부품은 기본부품 ?의 weight 합
                for (int i = 1; i <= N; i++) {
                    dp[next.v][i] += dp[u][i] * next.weight;
                }
                // 진입차수 감소
                indegree[next.v]--;
                if (indegree[next.v] == 0) {
                    queue.offer(next.v);
                }
            }
        }

        // dp[N][?] : ? -> N에 필요한 weight 합의 나열
        for (int i = 1; i < N; i++) {
            if (dp[N][i] > 0) {
                sb
                        .append(i)
                        .append(" ")
                        .append(dp[N][i])
                        .append("\n");
            }
        }

        System.out.print(sb);
    }
}
