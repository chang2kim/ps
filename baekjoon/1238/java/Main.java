import java.io.*;
import java.util.*;

/*
 * 17900kb	180ms
 */
public class Main {
	static class Edge { // vGraph, uGraph
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

	static int N, M, X;
	static List<List<Edge>> vGraph; // Node -> X
	static List<List<Edge>> uGraph; // X -> Node

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력 1행
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 정점
		M = Integer.parseInt(st.nextToken()); // 간선
		X = Integer.parseInt(st.nextToken()); // 출발점

		// 인접리스트 역방향, 정방향 초기화
		vGraph = new ArrayList<>();
		uGraph = new ArrayList<>();

		for (int i = 0; i <= N; i++) {
			vGraph.add(new ArrayList<>());
			uGraph.add(new ArrayList<>());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			vGraph.get(v).add(new Edge(u, w)); // u -> v, weight에서 v를 키로 추적한다
			uGraph.get(u).add(new Edge(v, w)); // u -> v, weight에서 u를 키로 추적한다
		}

		// 다익스트라 (마을 -> X -> 마을)
		int[] distToX = dijkstra(X, vGraph);
		int[] distFromX = dijkstra(X, uGraph);

		// 출력
		int maxTime = 0;
		for (int i = 1; i <= N; i++) {
			maxTime = Math.max(maxTime, distToX[i] + distFromX[i]);
		}

		System.out.println(maxTime);
	}

	// 다익스트라
	static int[] dijkstra(int start, List<List<Edge>> graph) {
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