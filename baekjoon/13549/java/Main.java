import java.io.*;
import java.util.*;

/*
 * 	17416kb	156ms
 */
public class Main {
	static class State implements Comparable<State> {
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 1행 : 수빈, 동생
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 정점 범위
		final int MAX = 100_000;

		// 다익스트라
		final int INF = 1_000_000;
		int[] dist = new int[MAX + 1];
		Arrays.fill(dist, INF);

		PriorityQueue<State> pq = new PriorityQueue<>();
		dist[N] = 0; // 수빈이 출발점
		pq.offer(new State(N, 0));

		while (!pq.isEmpty()) {
			State current = pq.poll();

			// 만나면 종료
			if (current.v == K) {
				System.out.print(current.dist);
				return;
			}

			if (current.dist > dist[current.v]) {
				continue;
			}

			// 이동 x - 1 (비용 1)
			int nextV = current.v - 1;
			if (nextV >= 0 && current.dist + 1 < dist[nextV]) {
				dist[nextV] = current.dist + 1;
				pq.offer(new State(nextV, current.dist + 1));
			}

			// 이동 x + 1 (비용 1)
			nextV = current.v + 1;
			if (nextV <= MAX && current.dist + 1 < dist[nextV]) {
				dist[nextV] = current.dist + 1;
				pq.offer(new State(nextV, current.dist + 1));
			}

			// 이동 2 * x (비용 0)
			nextV = 2 * current.v;
			if (nextV <= MAX && current.dist < dist[nextV]) {
				dist[nextV] = current.dist;
				pq.offer(new State(nextV, current.dist));
			}
		}
	}
}
