# standard1 

<details>
<summary>Thymeleaf</summary>

![모델](https://github.com/user-attachments/assets/a6085058-b202-4457-8679-860d6697021f)
모델은 컨트롤러와 뷰의 중간다리 역할을 한다.
## Thymeleaf 기본 예제
<details>
<summary>java</summary>

## java 코드 예제
`/thymeleaf/example` 엔드포인트는 데이터를 Thymeleaf 템플릿에 전달하고 이를 뷰로 렌더링하는 방식을 보여준다.

```java
@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) { // 뷰로 데이터를 넘겨주는 모델 객체

        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", examplePerson);
        model.addAttribute("today", LocalDate.now());

        return "example"; // example.html라는 뷰 조회
    }
    // (생략)
}
```

---

### 동작 과정
1. **컨트롤러 로직**
    - `ExampleController` 클래스에서 `@GetMapping`으로 `/thymeleaf/example` URL을 처리한다.
    - `Person` 객체 (`examplePerson`)를 생성하여 샘플 데이터를 초기화하고 `Model` 객체에 저장한다.
    - 현재 날짜를 `"today"`라는 키로 `Model`에 저장한다.

2. **데이터 바인딩**  
   뷰 템플릿에 전달되는 데이터는 다음과 같다:
    - `"person"`: `Person` 객체로, `id`, `name`, `age`, `hobbies` 정보를 포함한다.
    - `"today"`: 현재 날짜 (`LocalDate`).

3. **뷰 파일 처리**
    - 메서드가 `"example"` 문자열을 반환하면, 스프링 부트는 `resources/templates/example.html` 파일을 찾아 렌더링한다.
    - Thymeleaf는 전달된 데이터를 사용하여 HTML 템플릿을 처리하고 완성된 뷰를 브라우저에 전달한다.

### 주요 파일
- **컨트롤러**: `ExampleController.java`
- **템플릿**: `example.html` (`src/main/resources/templates` 디렉토리)

### 예제 URL
http://localhost:8080/thymeleaf/example



</details>

<details>

## html 코드 예제

- Thymeleaf 템플릿 엔진을 사용하여 HTML 페이지를 렌더링
- Thymeleaf는 동적 콘텐츠를 서버에서 HTML로 변환해 사용자에게 전달하는 도구

<summary>html</summary>

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>타임리프 익히기</h1>
<p th:text="${#temporals.format(today, 'yyyy-MM-dd')}"></p>
<div th:object="${person}">
    <p th:text="|이름 : *{name}|"></p>
    <p th:text="|나이 : *{age}|"></p>
    <p>취미</p>
    <ul th:each="hobby : *{hobbies}">
        <li th:text="${hobby}"></li>
        <span th:if="${hobby == '운동'}">(대표 취미)</span>
    </ul>
</div>
<a th:href="@{/api/articles/{id}(id=${person.id})}">글 보기</a>
</body>
</html>
```

---

### 1. 기본 구조

```html
<html xmlns:th="http://www.thymeleaf.org">
```

- `xmlns:th="http://www.thymeleaf.org"`: 이 속성은 HTML 문서에서 Thymeleaf의 문법(`th` 네임스페이스)을 사용할 수 있게 한다.

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
```

- 기본적인 HTML 헤더 정보다. `UTF-8`은 문자 인코딩 방식이다.

```html
<body>
<h1>타임리프 익히기</h1>
```

- `<body>` 안에 제목을 표시하는 `<h1>` 태그다.

---

### 2. 날짜 출력
```html
<p th:text="${#temporals.format(today, 'yyyy-MM-dd')}"></p>
```

- **기능**: 오늘 날짜를 `yyyy-MM-dd` 형식(예: 2024-11-17)으로 출력한다.
- `th:text`: HTML 태그 안의 내용을 동적으로 변경하는 Thymeleaf 속성이다.
   - `${#temporals.format(today, 'yyyy-MM-dd')}`는 `today`라는 변수에 저장된 날짜를 `#temporals.format` 메서드를 사용해 원하는 형식으로 변환한다.
   - `today`는 서버에서 전달된 변수로, 일반적으로 `LocalDate` 타입이다.

---

### 3. 객체 데이터 출력
```html
<div th:object="${person}">
```

- **기능**: `person`이라는 객체를 컨텍스트로 설정한다.
- `th:object`: 이 블록 안에서 `person` 객체를 간단하게 참조할 수 있게 한다. 이후에는 `*{필드명}`으로 객체의 필드에 접근한다.

#### 이름 출력
```html
<p th:text="|이름 : *{name}|"></p>
```

- **기능**: `person` 객체의 `name` 필드를 읽어 "이름 : [값]" 형식으로 출력한다.
- `*{name}`: 현재 `th:object`로 설정된 객체(`person`)의 `name` 필드를 참조한다.
- `| |`: 값을 문자열로 감싸 포맷팅한다.

#### 나이 출력
```html
<p th:text="|나이 : *{age}|"></p>
```

- `*{age}`: `person` 객체의 `age` 필드를 읽는다.

---

### 4. 리스트(배열) 처리
```html
<p>취미</p>
<ul th:each="hobby : *{hobbies}">
    <li th:text="${hobby}"></li>
    <span th:if="${hobby == '운동'}">(대표 취미)</span>
</ul>
```

- **기능**: `person` 객체의 `hobbies` 리스트(또는 배열)를 반복 처리하여 각 항목을 출력한다.
- `th:each="hobby : *{hobbies}"`:
   - `hobby`는 반복 중인 현재 항목을 가리킨다.
   - `*{hobbies}`는 `person` 객체의 `hobbies` 필드를 참조한다.

- `<li th:text="${hobby}"></li>`: 현재 `hobby` 항목의 값을 리스트 항목으로 출력한다.

- `<span th:if="${hobby == '운동'}">(대표 취미)</span>`:
   - `th:if`는 조건문으로, `hobby`가 "운동"일 때만 "대표 취미"라는 텍스트를 추가한다.

---

### 5. 링크 처리
```html
<a th:href="@{/api/articles/{id}(id=${person.id})}">글 보기</a>
```

- **기능**: `person` 객체의 `id` 필드를 사용하여 동적으로 링크를 생성한다.
- `th:href="@{/api/articles/{id}(id=${person.id})}"`:
   - `@{}`: Thymeleaf에서 URL을 생성하는 방식이다.
   - `/api/articles/{id}`: URL 경로에 `id`를 포함한다.
   - `(id=${person.id})`: `id` 값을 `person.id`로 치환한다.
   - 예: `person.id`가 `123`이라면, 최종 링크는 `/api/articles/123`이 된다.

---

### 코드 요약
이 Thymeleaf 템플릿은 다음과 같은 기능을 제공한다:
1. 오늘 날짜를 지정된 포맷으로 출력.
2. `person` 객체의 정보(이름, 나이, 취미)를 출력.
3. 특정 조건에 따라 텍스트(대표 취미)를 동적으로 추가.
4. `person` 객체의 ID를 포함한 URL을 생성.

이 코드를 서버와 함께 실행하면, 서버에서 `person`과 `today` 변수에 데이터를 전달하여 동적인 HTML 페이지를 생성한다.
</details>
</details>

<details>
<summary>REST API</summary>

## REST API
API(Application Programming Interface, 애플리케이션 프로그래밍 인터페이스)는 컴퓨터나 컴퓨터 프로그램 사이의 연결이다. 
일종의 소프트웨어 인터페이스이며 다른 종류의 소프트웨어에 서비스를 제공한다.

REST(Representational State Transfer)는 자원을 이름으로 구분해 자원의 상태를 주고받는 방식이다.

구체적인 개념 : HTTP URI(Uniform Resource Idenfier)를 통해 자원(Resource)을 명시하고, HTTP Method(POST, GET, PUT, DELETE)로 해당 자원에 대한 CRUD Operation을 적용하는 것을 의미한다.

즉, REST는 자원 기반 구조(ROA, Resource Oriented Architecture) 설계의 중심에 Resource가 있고 HTTP Method를 통해 Resource를 처리하도록 설계된 아키텍처를 의미한다. 웹 사이트의 이미지, 텍스트, DB 내용 등의 모든 자원에 고유한 ID인 HTTP URI를 부여한다.

REST API : REST 기반으로 서비스 API를 구현한 것

### HTTP Response Code

>200 OK : 요청이 성공적으로 수행되었음</br>
> 201 Created : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음</br>
> 400 Bad Request : 요청 값이 잘못되어 요청에 실패했음</br>
> 403 Forbidden : 권한이 없어 요청에 실패했음</br>
> 404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패했음</br>
> 500 Internal Server Error : 서버 상에 문제가 있어 요청에 실패했음</br>

### API 구현

![API구현 drawio](https://github.com/user-attachments/assets/f339ff7f-cd68-477f-8360-8b059594929a)

</details>

<details>
<summary>Repository</summary>

### BlogRepository의 구성

![blogRepository구성 drawio](https://github.com/user-attachments/assets/c99858f4-668d-4c78-bf02-f9deba4dea73)

</details>

<details>
<summary>Serialization</summary>

### Serialization (직렬화)

![Serialization drawio](https://github.com/user-attachments/assets/14c8768e-795c-4534-a4ad-8f148ca55eac)

@RestController : HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러

</details>