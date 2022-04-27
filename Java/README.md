# OpenSrcSW
건국대학교 2022학년도 1학기: 오픈소스SW입문

### File structure
```
output: 실행결과가 저장되는 곳입니다.
resource: 실행에 필요한 html파일이 저장됩니다.
src ┌ kuir.java
    ├ HTMLParser.java
    ├ HTMLCollector.java
    ├ XMLParser.java
    ├ WordAnalyzer.java
    ├ FileHelper.java
    ├ TFIDFHashMap.java
    ├ Indexer.java
    └ Searcher.java

HTMLParser.java: HTML 파일을 Jsoup 라이브러리를 통해 파싱합니다.
HTMLCollector.java: collection.xml을 만들기 위한 클래스입니다.
XMLParser.java: xml 파일 저장 및 불러오기 기능을 구현한 클래스입니다.
WordAnalyzer.java: 형태소분석에 활용되는 클래스입니다.
FileHelper.java: 파일 관련 클래스입니다.
TFIDFHashMap.java: TF-IDF 해시맵 과제에 사용되는 클래스입니다. 해시맵 연산 관련 클래스입니다.
Indexer.java: TF-IDF 과제 수행에 사용되는 클래스입니다.
Searcher.java: 문서 유사도 과제 수행에 사용되는 클래스입니다.
```

### Run command
우측 [Release](https://github.com/donghoony/OpenSrcSW/releases)에 빌드된 jar파일이 첨부돼 있습니다.
```
java -jar kuir.jar -c <TARGET_DIRECTORY> // ./collection.xml이 생성됩니다
java -jar kuir.jar -k <COLLECTION_FILE> // ./index.xml이 생성됩니다
java -jar kuir.jar -i <INDEX_FILE> // ./index.post가 생성됩니다
java -jar kuir.jar -s <INDEX.POST_FILE> -q <query> // 쿼리문에 따른 문서 유사도를 출력합니다
```
