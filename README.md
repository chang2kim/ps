# 온라인저지 코드를 CodeRunner로 테스트하는 저장소

## VSCode 설정

```json
{
  "code-runner.runInTerminal": true,
  "code-runner.executorMap": {
    "java": "cd $dir && javac $fileName && java $fileNameWithoutExt < ../input.txt > ../output.txt",
    "javascript": "cd $dir && node $fileName < ../input.txt > ../output.txt",
    "typescript": "cd $dir && ts-node --compiler-options '{\"module\":\"CommonJS\", \"lib\": [\"es2020\", \"dom\"]}' $fileName < ../input.txt > ../output.txt",
    "python": "cd $dir && python3 -u $fileName < ../input.txt > ../output.txt",
    "cpp": "cd $dir && g++ -std=c++17 -o $fileNameWithoutExt $fileName && ./$fileNameWithoutExt < ../input.txt > ../output.txt"
  }
}
```
