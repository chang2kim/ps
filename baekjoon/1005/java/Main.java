import java.io.*;
import java.util.*;

/*
 * 247004kb 772ms
 */
public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());
    StringBuilder sb = new StringBuilder();

    while (T-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());

      // 입력 1행
      int N = Integer.parseInt(st.nextToken());
      int K = Integer.parseInt(st.nextToken());

      // 건설 시간
      int[] time = new int[N + 1];
      st = new StringTokenizer(br.readLine());
      for (int i = 1; i <= N; i++) {
        time[i] = Integer.parseInt(st.nextToken());
      }

      List<List<Integer>> graph = new ArrayList<>();
      for (int i = 0; i <= N; i++) {
        graph.add(new ArrayList<>());
      }

      // 입력 K행 : X -> Y
      int[] indegree = new int[N + 1];

      for (int i = 0; i < K; i++) {
        st = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        graph.get(X).add(Y);
        indegree[Y]++; // Y로 들어오는 간선 추가
      }

      int W = Integer.parseInt(br.readLine());

      // 위상정렬 : 진입차수 0으로 큐 초기화
      Queue<Integer> queue = new ArrayDeque<>();
      int[] dp = new int[N + 1]; // 각 건물까지의 최소 완성 시간

      for (int i = 1; i <= N; i++) {
        if (indegree[i] == 0) {
          queue.offer(i);
          dp[i] = time[i]; // 선행 건물이 없으면 자기 시간만
        }
      }

      while (!queue.isEmpty()) {
        int current = queue.poll();

        for (int next : graph.get(current)) {
          // 선행 건물 중 가장 늦게 완성되는 시간 + 현재 건물 시간
          dp[next] = Math.max(dp[next], dp[current] + time[next]);

          indegree[next]--; // next의 진입차수를 차감
          if (indegree[next] == 0) {
            queue.offer(next); // 진입차수가 0이면 큐에 세팅
          }
        }
      }

      sb.append(dp[W]).append("\n");
    }

    System.out.print(sb);
  }
}