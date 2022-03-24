# open-source-sw
건국대학교 2022학년도 1학기: 오픈소스SW입문

### File structure
```
output: 실행결과가 저장되는 곳입니다.
resource: 실행에 필요한 html파일이 저장됩니다.
src ┌ Main.java
    ├ HTMLParser.java
    ├ HTMLCollector.java
    ├ XMLParser.java
    ├ WordAnalyzer.java
    ├ FileHelper.java
    ├ TFIDFHashMap.java
    └ Indexer.java

HTMLParser.java: HTML 파일을 Jsoup 라이브러리를 통해 파싱합니다.
HTMLCollector.java: collection.xml을 만들기 위한 클래스입니다.
XMLParser.java: xml 파일 저장 및 불러오기 기능을 구현한 클래스입니다.
WordAnalyzer.java: 형태소분석에 활용되는 클래스입니다.
FileHelper.java: 파일 관련 클래스입니다. 현재는 파일의 확장자를 가져오는 메서드가 있습니다.
TFIDFHashMap.java: TF-IDF 해시맵 과제에 사용되는 클래스입니다. 해시맵 연산 관련 클래스입니다.
Indexer.java: TF-IDF 과제 수행에 사용되는 클래스입니다.
```

### Run command
우측 Release에 빌드된 jar파일이 첨부돼 있습니다.
```
java -jar kuir.jar -c <TARGET_DIRECTORY> // ./output/collection.xml이 생성됩니다
java -jar kuir.jar -k <COLLECTION_FILE> // ./output/index.xml이 생성됩니다
java -jar kuir.jar -i <INDEX_FILE> // ./output/index.post가 생성됩니다
```
