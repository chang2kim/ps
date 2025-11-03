import java.io.*;
import java.util.*;

/*
 * 69424kb	520ms
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
		public int compareTo(Main.State o) {
			return Integer.compare(this.dist, o.dist);
		}
	}

	static int N, E;
	static List<List<Edge>> graph;
	static final int INF = 200_000_000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력: 1행
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 정점
		E = Integer.parseInt(st.nextToken()); // 간선

		// 인접리스트
		graph = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}

		// 입력: 2 ~ E+1행
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			graph.get(a).add(new Edge(b, c)); // u -> v, weight
			graph.get(b).add(new Edge(a, c)); // v -> u, weight
		}

		// 입력: E+2행
		st = new StringTokenizer(br.readLine());
		int v1 = Integer.parseInt(st.nextToken());
		int v2 = Integer.parseInt(st.nextToken());

		// 다익스트라
		// 1 -> v1 -> v2 -> N, 1 -> v2 -> v1 -> N
		int[] distFrom1 = dijkstra(1);
		int[] distFromV1 = dijkstra(v1);
		int[] distFromV2 = dijkstra(v2);

		// 출력
		long path1 = (long) distFrom1[v1] + distFromV1[v2] + distFromV2[N];
		long path2 = (long) distFrom1[v2] + distFromV2[v1] + distFromV1[N];

		long result = Math.min(path1, path2);
		if (result >= INF)
			System.out.println(-1);
		else
			System.out.println(result);
	}

	// 다익스트라
	static int[] dijkstra(int start) {
		int[] dist = new int[N + 1];
		Arrays.fill(dist, INF);

		PriorityQueue<State> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.offer(new State(start, 0));

		while (!pq.isEmpty()) {
			State current = pq.poll();

			if (current.dist > dist[current.v]) {
				continue;
			}

			for (Edge next : graph.get(current.v)) {
				int newDist = dist[current.v] + next.weight;

				if (newDist < dist[next.v]) {
					dist[next.v] = newDist;
					pq.offer(new State(next.v, newDist));
				}
			}
		}

		return dist;
	}
}
