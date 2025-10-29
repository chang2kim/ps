import java.io.*;
import java.util.*;

/*
 * 128292kb	560ms
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // 과목 수
		int M = Integer.parseInt(st.nextToken()); // 선수 조건 수

		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i <= N; i++)
			graph.add(new ArrayList<>());

		int[] indegree = new int[N + 1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			graph.get(A).add(B);
			indegree[B]++;
		}

		// dp[i] = i 과목을 들을 수 있는 최소 학기
		int[] dp = new int[N + 1];
		Queue<Integer> q = new ArrayDeque<>();

		// 진입차수 0인 과목 → 1학기부터 가능
		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				q.offer(i);
				dp[i] = 1;
			}
		}

		while (!q.isEmpty()) {
			int cur = q.poll();

			for (int next : graph.get(cur)) {
				// 선수 과목 중 가장 늦은 학기 + 1
				dp[next] = Math.max(dp[next], dp[cur] + 1);
				indegree[next]--;

				if (indegree[next] == 0)
					q.offer(next);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= N; i++)
			sb.append(dp[i]).append(" ");
		System.out.println(sb.toString().trim());
	}
}
