# 백준 플래티넘 5 - 임계경로

https://www.acmicpc.net/problem/1948

## 위상정렬에 대한 AI와의 문답

```
나:
출발지점 start로부터 ?까지의 최대 이동 시간은 dp[v] = max로 관리를 한다
이것의 중복을 줄이기 위해 "선행 조건을 다 처리한 이후의 dp[v] 고점을 효율적으로 계산"하기 위해서 위상정렬 큐가 필요한것

즉, 내가 앞선 풀이들에서 짐작했던 "부분의 순서가 전체의 순서가 된다"와는 다른 이유인것
혹은 본질적으로는 이 점화식도 "부분의 순서가 전체의 순서가 된다"일지도 모르고
"부분의 순서가 전체의 순서가 된다"의 본질이 "선행조건이 다 처리되면 각 v의 최대 가치"를 정할 수 있는데, 일종의 가중치가 전부 1로 동일하게, 특수한 형태라고 볼수도 있고?

어쩌면 "위상정렬은 선행조건을 다 처리한 후에 현재 탐색중인 노드의 가중치 최종값을 정할 수 있다"이고, 키의 순서라든가 이건 가중치 +1로 max처리하는것과 본질적으로는 같다고 볼수도 있고?
```

```
AI:
"부분의 순서가 전체의 순서"
= 특수 케이스 (가중치 1, 단순 순서)

"선행조건 완료 후 현재 노드의 가중치 최종값 확정"
= 일반화된 본질
```

```java
// 공통구조
while (!queue.isEmpty()) {
    int current = queue.poll(); // indegree == 0
    
    // current의 "값"을 확정
    // (이미 모든 선행이 처리되어 최종값 결정 가능)
    
    for (Edge next : graph.get(current)) {
        // next의 "값"에 current의 영향 반영
        value[next.v] = f(value[next.v], value[current], weight);
        
        indegree[next.v]--;
        if (indegree[next.v] == 0) {
            queue.offer(next.v); // next 값 확정 가능
        }
    }
}
```

```java
// 2252
// 가중치: 없음 (또는 모두 1)
// 연산: 순서 나열

while (!queue.isEmpty()) {
    int current = queue.poll();
    result.add(current); // 순서 확정
    
    for (int next : graph.get(current)) {
        indegree[next]--;
        if (indegree[next] == 0) {
            queue.offer(next);
        }
    }
}
```

```java
// 14567
// 가중치: 1 (한 학기)
// 연산: MAX + 1

for (int next : graph.get(current)) {
    dp[next] = Math.max(dp[next], dp[current] + 1);
    //                              ↑ 가중치 1 더하기
    indegree[next]--;
    if (indegree[next] == 0) {
        queue.offer(next);
    }
}
```

```java
// 1005
// 가중치: time[v] (건물별 다름)
// 연산: MAX + weight

for (Edge next : graph.get(current)) {
    dp[next.v] = Math.max(dp[next.v], 
                          dp[current] + time[next.v]);
    //                                ↑ 가변 가중치
    indegree[next.v]--;
    if (indegree[next.v] == 0) {
        queue.offer(next.v);
    }
}
```

```java
// 1948
// 가중치: edge.weight (도로별 다름)
// 연산: MAX + weight

for (Edge next : graph.get(current)) {
    dp[next.v] = Math.max(dp[next.v], 
                          dp[current] + next.weight);
    //                                ↑ 간선 가중치
    indegree[next.v]--;
    if (indegree[next.v] == 0) {
        queue.offer(next.v);
    }
}
```

## 역추적은 위상정렬이 필요없음

```java
// visited만으로 충분한 이유
while (!q.isEmpty()) {
    int current = q.poll();
    
    for (Edge prev : reverseGraph.get(current)) {
        if (dp[prev.v] + prev.weight == dp[current]) {  // ← 즉시 판단 가능
            criticalEdges++;
            
            if (!visited[prev.v]) {  // ← 중복 방문만 체크
                visited[prev.v] = true;
                q.offer(prev.v);
            }
        }
    }
}
```
- **상황**: `dp` 값이 **이미 모두 확정됨**
- **목적**: 조건을 만족하는 간선 찾기 (값 계산 X)
- **필요**: 중복 방문 방지만 (`visited`)
- **불필요**: 선행조건 완료 확인 (`indegree`)