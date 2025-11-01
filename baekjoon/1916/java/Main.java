import java.io.*;
import java.util.*;

/*
 * 51572kb	504ms
 */
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

		// 1행: 도시 개수
		int N = Integer.parseInt(br.readLine());

		// 2행: 버스 개수
		int M = Integer.parseInt(br.readLine());

		// 인접 리스트
		List<List<Edge>> graph = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}

		// 3 ~ M+2행: 간선 정보
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			graph.get(u).add(new Edge(v, weight));
		}

		// M+3행: 출발점, 도착점
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		// 다익스트라
		final int INF = 100_000_000;
		int[] dist = new int[N + 1];
		Arrays.fill(dist, INF);

		PriorityQueue<State> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.offer(new State(start, 0));

		while (!pq.isEmpty()) {
			State current = pq.poll();

			if (current.dist > dist[current.v])
				continue;

			for (Edge next : graph.get(current.v)) {
				int newDist = current.dist + next.weight;

				if (newDist < dist[next.v]) {
					dist[next.v] = newDist;
					pq.offer(new State(next.v, newDist));
				}
			}
		}

		// 출력
		System.out.println(dist[end]);
	}
}