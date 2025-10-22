import java.io.*;
import java.util.*;

/*
 * 44444kb 456ms
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 입력 1행
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		List<List<Integer>> graph = new ArrayList<>();

		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}

		// 입력 M행 : A -> B
		int[] indegree = new int[N + 1];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			graph.get(A).add(B);
			indegree[B]++; // B로 들어오는 간선 추가
		}

		// 위상정렬 : 진입차수 0으로 큐 초기화
		Queue<Integer> queue = new ArrayDeque<>();

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		StringBuilder sb = new StringBuilder();

		while (!queue.isEmpty()) {
			int current = queue.poll();
			sb.append(current).append(" ");

			for (int next : graph.get(current)) {
				indegree[next]--; // B의 진입차수를 차감
				if (indegree[next] == 0) {
					queue.offer(next); // 진입차수가 0이면 큐에 세팅
				}
			}
		}

		System.out.println(sb.toString().trim());
	}
}
