import java.io.*;
import java.util.*;

/*
 * 14732kb	124ms
 */
public class Main {
	static class State implements Comparable<State> { // pq
		int x, y, dist; // 벽 횟수는 곧 거리

		State(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		@Override
		public int compareTo(State o) {
			return Integer.compare(this.dist, o.dist);
		}
	}

	static int N, M;
	static int[][] map;
	static final int INF = 100_000_000;
	static int[][] dist;
	static int[] dx = { -1, 1, 0, 0 }; // 상하좌우
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력 1행
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken()); // 가로 크기
		N = Integer.parseInt(st.nextToken()); // 세로 크기

		// 미로 초기화 (0 또는 1)
		map = new int[N][M];
		dist = new int[N][M];

		// 입력 2 ~ N+1행 (미로 상태)
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = line.charAt(j) - '0';
			}
		}

		// 다익스트라 (0, 0) -> (N-1, M-1)
		int result = dijkstra(0, 0);

		// 출력
		System.out.println(result);
	}

	// 다익스트라
	static int dijkstra(int startX, int startY) {

		// dist 2차원 배열 초기화
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], INF);
		}

		PriorityQueue<State> pq = new PriorityQueue<>();
		dist[startX][startY] = 0;
		pq.offer(new State(startX, startY, 0));

		while (!pq.isEmpty()) {
			State current = pq.poll();

			if (current.x == N - 1 && current.y == M - 1) {
				return current.dist;
			}

			if (current.dist > dist[current.x][current.y]) {
				continue;
			}

			// 인접리스트 대체 : 4방향 이동
			for (int i = 0; i < 4; i++) {
				int nx = current.x + dx[i];
				int ny = current.y + dy[i];

				if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
					continue;
				}

				int newDist = dist[current.x][current.y] + map[nx][ny];

				if (newDist < dist[nx][ny]) {
					dist[nx][ny] = newDist;
					pq.offer(new State(nx, ny, newDist));
				}
			}
		}

		return dist[N - 1][M - 1];
	}
}