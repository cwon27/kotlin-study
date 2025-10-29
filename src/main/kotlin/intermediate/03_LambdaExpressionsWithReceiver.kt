package org.example.intermediate

class `03_LambdaExpressionsWithReceiver` {
	// Lambda with Receiver
	// 리시버 객체를 가진 람다 표현식으로, 람다 내부에서 특정 객체의 멤버에 직접 접근할 수 있게 해주는 코틀린의 강력한 기능
	// 일반 람다 : 파라미터를 받아서 처리. 즉, 파라미터로 접근
	// Lambda with Receiver: 특정 객체(리시버) "위에서" 실행되는 람다

	fun main() {
		// 일반 람다
		// 형태 : (Parameters) -> ReturnType
		val sum: (Int, Int) -> Int = { a, b -> a + b }

		println(sum(3, 5)) // 8

		// 고차 함수에서 사용
		fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
			return operation(x, y)
		}

		val result = calculate(10, 5) { a, b -> a * b }
		println(result) // 50

		// Lambda with Receiver - StringBuilder를 리시버로
		// 형태 : ReceiverType.(Parameters) -> ReturnType - this로 접근
		// Unit : 반환 값 없음
		val buildString: StringBuilder.() -> Unit = {
			append("Hello")
			append(" ")
			append("Kotlin")
		}

		// 사용하기
		val sb = StringBuilder()
		sb.buildString()
		println(sb.toString()) // "Hello Kotlin"

		// 파라미터가 없는 Lambda with Receiver 함수 사용
		val html = buildHtml {
			// 람다 내부에서 this는 StringBuilder 인스턴스
			// append() 호출 시 this.append()와 동일
			append("<html>")
			append("<body>")
			append("Hello!")
			append("</body>")
			append("</html>")
		}

		println(html)

		// 파라미터가 있는 Lambda with Receiver 함수 사용
		val result2 = buildString { language ->
			append("I love ")
			append(language)
			append("!")
		}

		println(result2) // "I love Kotlin!"

		// 일반 람다 vs Lambda with Receiver
		// 일반 람다 - builder를 명시적으로 사용
		val buildStringNormalResult = buildStringNormal { sb, language ->
			sb.append("Learning ")
			sb.append(language)
		}

		// Lambda with Receiver - this(생략 가능)로 접근
		// 장점 : 코드 간결 / 읽기 쉬움 / 도메인 특화 언어(DSL) 스타일의 코드 작성 가능
		val buildStringWithReceiverResult = buildStringWithReceiver { language ->
			append("Learning ") // this.append()
			append(language) // this.append()
		}

		// 데이터베이스 설정 DSL 실습
		val dbConfig = database {
			host = "db.example.com"
			port = 3306
			username = "admin"
			password = "secret"
			database = "mydb"

			connection {
				maxConnections = 50
				timeout = 60
			}
		}

		println("Database: ${dbConfig.database} at ${dbConfig.host}:${dbConfig.port}")

		// 실무 스타일 실습 - API 요청 빌더
		val apiRequest = request {
			url = "https://api.example.com/users"
			method = "POST"
			header("Content-Type", "application/json")
			header("Authorization", "Bearer token123")
			param("page", "1")
			param("limit", "10")
		}

		apiRequest.execute()
	}

	// 파라미터가 없는 Lambda with Receiver 함수 정의
	fun buildHtml(content: StringBuilder.() -> Unit): String {
		val builder = StringBuilder()
		builder.content() // 또는 content(builder)
		return builder.toString()
	}

	// 파라미터가 있는 Lambda with Receiver 함수 정의
	fun buildString(init: StringBuilder.(String) -> Unit): String {
		val builder = StringBuilder()
		builder.init("Kotlin")
		return builder.toString()
	}

	// 일반 람다 vs Lambda with Receiver
	// 일반 람다 방식 함수 정의
	fun buildStringNormal(init: (StringBuilder, String) -> Unit): String {
		val builder = StringBuilder()
		init(builder, "Kotlin") // builder를 파라미터로 전달
		return builder.toString()
	}

	// Lambda with Receiver 방식 함수 정의
	fun buildStringWithReceiver(init: StringBuilder.(String) -> Unit): String {
		val builder = StringBuilder()
		builder.init("Kotlin") // builder가 리시버
		return builder.toString()
	}
}

// 데이터베이스 설정 DSL 실습
// 데이터베이스 설정 클래스
class DatabaseConfig {
	var host: String = "localhost"
	var port: Int = 5432
	var username: String = ""
	var password: String = ""
	var database: String = ""

	fun connection(init: ConnectionConfig.() -> Unit) {
		val config = ConnectionConfig()
		config.init()
		println("Connection config: ${config.maxConnections} connections")
	}
}

class ConnectionConfig {
	var maxConnections: Int = 10
	var timeout: Int = 30
}

// 설정 빌더
fun database(init: DatabaseConfig.() -> Unit): DatabaseConfig {
	val config = DatabaseConfig()
	config.init()
	return config
}

// 실무 스타일 실습 - API 요청 빌더
class ApiRequest {
	var url: String = ""
	var method: String = "GET"
	private val headers = mutableMapOf<String, String>()
	private val params = mutableMapOf<String, String>()

	fun header(key: String, value: String) {
		headers[key] = value
	}

	fun param(key: String, value: String) {
		params[key] = value
	}

	fun execute() {
		println("Executing $method request to $url")
		println("Headers: $headers")
		println("Params: $params")
	}
}

fun request(init: ApiRequest.() -> Unit): ApiRequest {
	val request = ApiRequest()
	request.init()
	return request
}