package org.example.intermediate

import java.net.http.HttpClient
import java.net.http.HttpResponse

class `01_ExtensionFunctions` {
	/*
	 원본 소스 코드를 변경하지 않고 클래스에 새로운 기능을 추가할 수 있는 기능
	 클래스를 확장하는 확장 함수!
	 왜 필요?
	 1. 서드파티 라이브러리의 클래스에 기능을 추가하고 싶을 경우
	 2. 기존 클래스를 상속하지 않고 기능을 확장하고 싶은 경우
	 3. 코드의 가독성을 높이고 싶은 경우
	*/

	fun main() {
		// Receiver Object (리시버 객체)
		val myList = listOf(1, 2, 3, 4, 5)
		myList.first() // myList가 Receiver Object

		// 확장 함수 실전 예제
		println("hello".bold()) // <b>hello</b>

		// 매개변수가 있는 확장 함수 실전 예제
		println("안녕".wrap("<<", ">>")) // <<안녕>>

		// Extension-Oriented Design
		val client = HttpClient()
		// 확장 함수 사용 - 간결함
		val response1 = client.get("https://api.example.com/users")
		println(response1)

		// 직접 호출 - 복잡함
		val response2 = client.request("https://api.example.com/users", "GET", null)
		println(response2)
	}

	// 확장 함수 문법
	/*
	 fun ReceiverType.functionName(parameters): ReturnType {
		// this를 사용해서 receiver object에 접근
		return result
	 }

	 1. ReceiverType: 확장할 클래스 이름 (receiver type)
	 2. functionName: 새로 만들 함수 이름
	 3. this: 함수 내부에서 receiver object를 참조하는 키워드
	 4. ReturnType: 함수의 반환 타입
	*/

	// 확장 함수 실전 예제
	// this는 함수를 호출한 문자열을 가리킴
	// $this를 사용해 문자열 템플릿으로 값 접근
	fun String.bold(): String = "<b>$this</b>"

	// 매개변수가 있는 확장 함수 실전 예제
	fun String.wrap(prefix: String, suffix: String): String = "$prefix$this$suffix"

	// Extension-Oriented Design
	// 핵심 기능과 부가 기능을 분리하여 코드를 더 읽기 쉽고 유지보수하기 쉽게 만드는 설계 방식
	// 모든 HTTP 요청을 처리하는 핵심 함수
	// 간단한 Response 클래스 직접 만들기
	data class Response(val body: String, val status: Int = 200)

	// HttpClient 클래스
	class HttpClient {
		fun request(url: String, method: String, body: Any?): Response {
			println("Requesting $method to $url")
			return Response("Response from $url")
		}
	}

	// GET 확장 함수
	fun HttpClient.get(url: String): Response {
		return this.request(url, "GET", null)
	}

	// POST 확장 함수
	fun HttpClient.post(url: String, body: Any): Response {
		return this.request(url, "POST", body)
	}
}