import java.io.*;
import java.util.*;

/*
 * 19192kb	200ms
 */
public class Main {
	static class State implements Comparable<State> {
		int x, y, dist;

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

	static int N;
	static int[][] map;
	static final int INF = 100_000_000;
	static int[][] dist;
	static int[] dx = { -1, 1, 0, 0 }; // 상하좌우
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int problemNum = 1;

		while (true) {
			// 입력 1행 - 동굴 크기
			N = Integer.parseInt(br.readLine());
			
			// 종료 조건
			if (N == 0) {
				break;
			}

			// 동굴 초기화 (도둑루피 0~9)
			map = new int[N][N];
			dist = new int[N][N];

			// 입력 2 ~ N+1행 (동굴 상태)
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// 다익스트라 (0, 0) -> (N-1, N-1)
			int result = dijkstra(0, 0);

			// 출력 형식
			sb.append("Problem ").append(problemNum++).append(": ").append(result).append('\n');
		}

		// 출력
		System.out.print(sb);
	}

	// 다익스트라
	static int dijkstra(int startX, int startY) {

		// dist 2차원 배열 초기화
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], INF);
		}

		PriorityQueue<State> pq = new PriorityQueue<>();
		dist[startX][startY] = map[startX][startY]; // 시작 칸의 도둑루피도 잃음
		pq.offer(new State(startX, startY, map[startX][startY]));

		while (!pq.isEmpty()) {
			State current = pq.poll();

			// 목적지 도착
			if (current.x == N - 1 && current.y == N - 1) {
				return current.dist;
			}

			// 이미 처리된 상태면 스킵
			if (current.dist > dist[current.x][current.y]) {
				continue;
			}

			// 인접리스트 대체 : 4방향 이동
			for (int i = 0; i < 4; i++) {
				int nx = current.x + dx[i];
				int ny = current.y + dy[i];

				// 범위 체크
				if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
					continue;
				}

				// 새로운 거리 = 현재까지 거리 + 다음 칸의 도둑루피
				int newDist = dist[current.x][current.y] + map[nx][ny];

				// 최단 거리 갱신
				if (newDist < dist[nx][ny]) {
					dist[nx][ny] = newDist;
					pq.offer(new State(nx, ny, newDist));
				}
			}
		}

		return dist[N - 1][N - 1];
	}
}