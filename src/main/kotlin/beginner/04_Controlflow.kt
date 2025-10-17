package org.example.beginner

class `04_Controlflow` {
	fun main() {
		// 1. if
		val age = 20

		// if를 표현식으로 사용 → 값을 반환
		val status = if (age >= 20) {
			"성인"
		} else {
			"미성년자"
		}
		println("나이 ${age}세 → $status")

		// 한 줄로도 가능 -> Kotlin에는 삼항연산자 없음
		val message = if (age >= 20) "성인" else "미성년자"
		println(message)

		// 2. when : 분기가 있는 조건식. Java의 swtich문 대체
		
	}
}