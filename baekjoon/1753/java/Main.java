import java.io.*;
import java.util.*;

public class Main {
	static class Edge { // graph
		int v, weight;

		Edge(int v, int weight) {
			this.v = v;
			this.weight = weight;
		}
	}

	static class State implements Comparable<State> { // pq
		int v, dist;

		State(int v, int dist) {
			this.v = v;
			this.dist = dist;
		}

		@Override
		public int compareTo(State o) {
			return Integer.compare(this.dist, o.dist);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 1행 : 정점, 간선
		st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());

		// 2행 : 시작 정점
		int K = Integer.parseInt(br.readLine());

		// 인접 리스트
		List<List<Edge>> graph = new ArrayList<>();
		for (int i = 0; i <= V; i++) {
			graph.add(new ArrayList<>());
		}

		// 3 ~ 행 : 간선정보 업데이트
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			graph.get(u).add(new Edge(v, w));
		}

		// 다익스트라
		final int INF = 1_000_000_000; // dp 초기화
		int[] dist = new int[V + 1];
		Arrays.fill(dist, INF);

		PriorityQueue<State> pq = new PriorityQueue<>();
		dist[K] = 0; // dp 갱신
		pq.offer(new State(K, 0)); // 힙 초기화

		while (!pq.isEmpty()) {
			State current = pq.poll();

			// 처리된 정점은 PASS
			if (current.dist > dist[current.v])
				continue;

			for (Edge next : graph.get(current.v)) {
				int newDist = current.dist + next.weight;

				// dp가 감소하면 업데이트
				if (newDist < dist[next.v]) {
					dist[next.v] = newDist; // dp 갱신
					pq.offer(new State(next.v, newDist)); // 힙 추가
				}
			}
		}

		// 출력
		for (int i = 1; i <= V; i++) {
			if (dist[i] == INF) {
				sb.append("INF\n");
			} else {
				sb.append(dist[i]).append('\n');
			}
		}

		System.out.print(sb);
	}
}