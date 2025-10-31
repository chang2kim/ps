import java.io.*;
import java.util.*;

/*
 * 	49512kb	376ms
 */
public class Main {
	static class Edge {
		int v, weight;

		Edge(int v, int weight) {
			this.v = v;
			this.weight = weight;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 입력 1행
		int n = Integer.parseInt(br.readLine());

		// 입력 2행
		int m = Integer.parseInt(br.readLine());

		// 그래프 초기화
		List<List<Edge>> graph = new ArrayList<>();
		List<List<Edge>> reverseGraph = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
			reverseGraph.add(new ArrayList<>());
		}

		int[] indegree = new int[n + 1];

		// 입력 M행
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			graph.get(u).add(new Edge(v, w));
			reverseGraph.get(v).add(new Edge(u, w));
			indegree[v]++;
		}

		// 입력 M+3행
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		// 위상정렬: 진입차수 0으로 큐 초기화
		Queue<Integer> queue = new ArrayDeque<>();
		int[] dp = new int[n + 1]; // 각 도시까지의 최장 시간

		queue.offer(start);
		dp[start] = 0;

		while (!queue.isEmpty()) {
			int current = queue.poll();

			for (Edge next : graph.get(current)) {
				// 선행 도시 중 가장 늦게 도착하는 시간 + 현재 도로 시간
				dp[next.v] = Math.max(dp[next.v], dp[current] + next.weight);

				indegree[next.v]--;
				if (indegree[next.v] == 0) {
					queue.offer(next.v);
				}
			}
		}

		// 역추적: 임계 경로 간선 개수
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] visited = new boolean[n + 1];
		int criticalEdges = 0;

		q.offer(end);
		visited[end] = true;

		while (!q.isEmpty()) {
			int current = q.poll();

			for (Edge prev : reverseGraph.get(current)) {
				// 임계 경로 조건: dp[prev.v] + weight == dp[current]
				if (dp[prev.v] + prev.weight == dp[current]) {
					criticalEdges++;

					if (!visited[prev.v]) {
						visited[prev.v] = true;
						q.offer(prev.v);
					}
				}
			}
		}

		sb.append(dp[end]).append("\n");
		sb.append(criticalEdges).append("\n");

		System.out.print(sb);
	}
}