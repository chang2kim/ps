import java.io.*;
import java.util.*;

/*
 * 14656kb 120ms
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 입력 1행
		int N = Integer.parseInt(st.nextToken()); // 가수의 수
		int M = Integer.parseInt(st.nextToken()); // 보조 PD의 수

		List<List<Integer>> graph = new ArrayList<>();

		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}

		// 입력 M행 : 보조 PD가 정한 순서
		int[] indegree = new int[N + 1];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int cnt = Integer.parseInt(st.nextToken()); // 담당 가수 수

			int[] singers = new int[cnt];
			for (int j = 0; j < cnt; j++) {
				singers[j] = Integer.parseInt(st.nextToken());
			}

			// 순서대로 간선 생성 (singers[0] -> singers[1] -> ... -> singers[cnt-1])
			for (int j = 0; j < cnt - 1; j++) {
				int A = singers[j];
				int B = singers[j + 1];
				graph.get(A).add(B);
				indegree[B]++; // B로 들어오는 간선 추가
			}
		}

		// 위상정렬 : 진입차수 0으로 큐 초기화
		Queue<Integer> queue = new ArrayDeque<>();

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		StringBuilder sb = new StringBuilder();
		int sorted = 0; // 정렬된 노드 개수

		while (!queue.isEmpty()) {
			int current = queue.poll();
			sb.append(current).append("\n");
			sorted++;

			for (int next : graph.get(current)) {
				indegree[next]--; // next의 진입차수를 차감
				if (indegree[next] == 0) {
					queue.offer(next); // 진입차수가 0이면 큐에 세팅
				}
			}
		}

		// 사이클 감지 : 정렬된 노드 개수가 N과 다르면 불가능
		if (sorted != N) {
			System.out.println(0);
		} else {
			System.out.print(sb.toString().trim());
		}
	}
}