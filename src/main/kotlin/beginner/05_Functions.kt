package org.example.beginner

import kotlin.math.PI

class `05_Functions` {
	val registeredUsernames = mutableListOf("john_doe", "jane_smith")
	val registeredEmails = mutableListOf("john@example.com", "jane@example.com")

	// 조기반환
	fun registerUser(username: String, email: String): String { // 이렇게 매개변수랑 함수에 타입 지정해주는 것이 좋다!
		if(username in registeredUsernames) {
			return "이미 존재하는 이름"
		}

		if(email in registeredEmails) {
			return "이미 존재하는 이메일"
		}

		// 사용중이 아니면 추가
		registeredUsernames.add(username)
		registeredEmails.add(email)

		return "사용자가 성공적으로 등록됨"
	}

	// exercise1
	fun circleArea(radius: Int): Double {
		return radius * radius * PI
	}

	// exercise2
	fun circleArea2(radius: Int): Double = radius * radius * PI

	// 람다 표현식
	fun uppercaseString(txt: String): String {
		return txt.uppercase()
	}

	// 함수에서 반환
	fun toSeconds(time: String): (Int) -> Int = when (time) {
		"hour" -> { value -> value * 60 * 60 }
		"minute" -> { value -> value * 60 }
		"second" -> { value -> value }
		else -> { value -> value }
	}

		fun main() {
		// 조기반환
		println(registerUser("john_doe", "newjohn@example.com"))
		println(registerUser("new_user", "newjohn@example.com"))

		// exercise1
		println(circleArea(3))
		// exercise2
		println(circleArea2(3))

		// 람다 표현식
		uppercaseString("hello") // 람다 X
		//						매개변수		함수 본문
		val uppercaseString2 = {txt: String -> txt.uppercase() }
		uppercaseString2("hi") // 람다 O	
		
		// 					매개변수타입 함수타입	함수 본문
		val uppercaseString3: (String) -> String = {txt -> txt.uppercase() }
		

		// 람다 표현식을 사용하는 다양한 방법
		// 1. 다른 함수로 전달 - 좋은 예) filter 컬렉션에서 사용
		val numbers = listOf(1, -2, 3, -4, 5, -6)

		val positives = numbers.filter ({ x -> x > 0 })
		// 람다 표현식이 유일한 함수 매개변수인 경우 함수 괄호 삭제 가능 : 후행 람다
		// val positives = numbers.filter { x -> x > 0 }

		val isNegative = { x: Int -> x < 0 }
		val negatives = numbers.filter(isNegative)

		println(positives) // [1, 3, 5]
		println(negatives) // [-2, -4, -6]

		// 2. 함수에서 반환
		val timesInMinutes = listOf(2, 10, 15, 1)
		val min2sec = toSeconds("minute")
		val totalTimeInSeconds = timesInMinutes.map(min2sec).sum()
		println("Total time is $totalTimeInSeconds secs") // Total time is 1680 secs

		// 3. 별도로 호출
		// 중괄호 뒤에 {}를 추가하고 안에 매개변수를 포함시켜 단독으로 호출시킬 수 있음
		println({txt: String -> txt.uppercase()}("hello")) // hello


	}
}