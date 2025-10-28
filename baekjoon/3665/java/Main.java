import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 입력 1행
		int T = Integer.parseInt(br.readLine());

		while (T-- > 0) {
			// 각 1행
			int N = Integer.parseInt(br.readLine()); // 팀의 수

			// 각 2행
			StringTokenizer st = new StringTokenizer(br.readLine());
			int[] rank = new int[N + 1]; // 작년 순위
			for (int i = 1; i <= N; i++) {
				rank[i] = Integer.parseInt(st.nextToken());
			}

			boolean[][] graph = new boolean[N + 1][N + 1]; // 인접 행렬
			int[] indegree = new int[N + 1]; // 진입차수
			for (int i = 1; i <= N; i++) {
				for (int j = i + 1; j <= N; j++) {
					int A = rank[i];
					int B = rank[j];

					graph[A][B] = true; // A -> B
					indegree[B]++; // B 차수증가
				}
			}

			// 각 3행
			int M = Integer.parseInt(br.readLine());

			// 각 M행
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int A = Integer.parseInt(st.nextToken());
				int B = Integer.parseInt(st.nextToken());

				if (graph[A][B]) {
					// 작년에 A -> B (A가 B보다 높았음). 올해는 B -> A로 변경
					graph[A][B] = false; // A -> B 제거
					indegree[B]--;

					graph[B][A] = true; // B -> A 추가
					indegree[A]++;
				} else {
					// 작년에 B -> A (B가 A보다 높았음). 올해는 A -> B로 변경
					graph[B][A] = false; // B -> A 제거
					indegree[A]--;

					graph[A][B] = true; // A -> B 추가
					indegree[B]++;
				}
			}

			// 위상정렬
			Queue<Integer> queue = new ArrayDeque<>();
			for (int i = 1; i <= N; i++) {
				if (indegree[i] == 0) {
					queue.offer(i);
				}
			}

			boolean isUnique = true; // 순위가 유일한지 판별
			List<Integer> result = new ArrayList<>();
			while (!queue.isEmpty()) {
				// '?' 판별: 큐에 2개 이상의 원소가 있으면 순위가 유일하지 않음
				if (queue.size() > 1) {
					isUnique = false;
				}

				int current = queue.poll();
				result.add(current);

				// 현재 팀(current)에서 나가는 모든 간선을 탐색하며 제거 (O(N))
				for (int next = 1; next <= N; next++) {
					if (graph[current][next]) {
						indegree[next]--;

						if (indegree[next] == 0) {
							queue.offer(next);
						}
					}
				}
			}

			if (result.size() != N) {
				// 사이클 감지: 정렬된 노드 개수가 N과 다르면 IMPOSSIBLE
				sb.append("IMPOSSIBLE").append("\n");
			} else if (!isUnique) {
				// 순위가 유일하지 않은 경우: '?'
				sb.append("?").append("\n");
			} else {
				// 확실한 순위가 있는 경우: 결과 출력
				for (int team : result) {
					sb.append(team).append(" ");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("\n");
			}
		}

		System.out.print(sb.toString());
	}
}