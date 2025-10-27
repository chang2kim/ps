import java.io.*;
import java.util.*;

/*
 * 83668kb	716ms
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력 1행
		int N = Integer.parseInt(br.readLine());

		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}

		// 입력 N행
		int[] time = new int[N + 1];
		int[] indegree = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			time[i] = Integer.parseInt(st.nextToken());
			int cnt = Integer.parseInt(st.nextToken());

			for (int j = 0; j < cnt; j++) {
				int prev = Integer.parseInt(st.nextToken());
				graph.get(prev).add(i); // prev -> i 간선
				indegree[i]++;
			}
		}

		// 위상 정렬
		Queue<Integer> queue = new ArrayDeque<>();
		int[] dp = new int[N + 1]; // 각 작업의 완료 시간

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
				dp[i] = time[i]; // 선행 작업이 없으면 바로 시작 가능
			}
		}

		while (!queue.isEmpty()) {
			int current = queue.poll();

			for (int next : graph.get(current)) {
				// next 작업은 current가 끝난 후에 시작 가능
				dp[next] = Math.max(dp[next], dp[current] + time[next]);

				indegree[next]--;
				if (indegree[next] == 0) {
					queue.offer(next);
				}
			}
		}

		// 모든 작업 중 가장 늦게 끝나는 시간 출력
		int answer = 0;
		for (int i = 1; i <= N; i++) {
			answer = Math.max(answer, dp[i]);
		}

		System.out.println(answer);
	}
}