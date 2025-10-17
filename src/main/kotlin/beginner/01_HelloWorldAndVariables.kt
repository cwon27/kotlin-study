package org.example.beginner

class `01_HelloWorldAndVariables` {
	fun helloWord() {
		println("Hello, World!")

		// 원시 문자열 (Raw String)
		println("=== 원시 문자열 ===")
		val rawString = """
			여러 줄에 걸쳐
			문자열 작성 가능
		""".trimIndent()
		println(rawString)
	}

	fun variables() {
		// 기본적으로 모든 변수는 val로 선언하는것이 좋다. 필요한 경우에만 var 사용
		// 읽기 전용 : val
		val popcorn = 5
		val hotdog = 7
		println("팝콘 상자가 ${popcorn}개 있다.")
		println("핫도그는 ${hotdog}개 있다.")

		// 변경 가능 : var
		var customer = 10
		println("초기 고객: $customer")

		customer = 20
		println("변경된 고객: $customer")
		println("변경된 고객 + 1: ${customer + 1}")

		// 실습 : Mary is 20 years old"프로그램을 표준 출력으로 인쇄하도록 코드를 완성하세요 .
		val name = "Mary"
		val age = 20
		println("$name is $age years old")

		// 타입 명시 -> TypeScript랑 비슷
		val year: Int = 2020
		val score: UInt = 100u
		val height: Double = 175.5
		val isStudent: Boolean = true
		val city: String = "서울"

		println("연도: ${year}년")
		println("점수: ${score}점")
		println("키: ${height}cm")
		println("학생 여부: $isStudent")
		println("도시: $city")

		// 실습 : 각 변수에 대해 올바른 유형을 명시적으로 선언합니다.
		val a: Int = 1000
		val b: String = "log message"
		val c: Double = 3.14
		val d: Long = 100_000_000_000_000
		val e: Boolean = false
		val f: Char = '\n'
	}
}