package org.example.beginner

import kotlin.random.Random

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
		val dayOfWeek = 3

		when (dayOfWeek) {
			// 하나가 충족될때까지 순차적으로 검사됨.
			1 -> println("월요일")
			2 -> println("화요일")
			3 -> println("수요일")
			4 -> println("목요일")
			5 -> println("금요일")
			6 -> println("토요일")
			7 -> println("일요일")
			else -> println("잘못된 입력")
		}

		// when을 표현식으로 사용
		val obj = "hello"

		val result = when (obj) {
			"1" -> "1"
			"hello" -> "Greeting"
			else -> "unknown"
		}
		println(result) // Greeting

		// 주어가 없는 when -> 굳이? 위같은 방법으로 쓰는게 좋다
		val trafficLightState = "red"

		val trafficAction = when {
			trafficLightState == "green" -> "hi"
			trafficLightState == "yellow" -> "hello"
			trafficLightState == "red" -> "hi hello"
			else -> "unknown"
		}

		println(trafficAction) // hi hello

		// 3. Range : 코틀린에서는 보통 .. 연산자를 사용하여 범위 표시
		val range1 = 1..10 // 1부터 10까지 (10 포함)
		val range2 = 1 until 10 // 1부터 10까지 (10 미포함) 또는 ..< (최신문법)
		val range3 = 10 downTo 1 // 10부터 1까지 (역순)
		val range4 = 1..10 step 2 // 1부터 10까지 2씩 증가 (1이 아닌 단위로 증가하는 범위를 선언할 땐 step)

		// in : 포함 여부 확인
		val range5 = 5
		if(range5 in 1..10) {
			println("ok")
		}

		// 실전 예제
		val month = 7

		val season = when(month) {
			in 3..5 -> "spring"
			in 6..8 -> "summer"
			in 9..11 -> "autumn"
			in 12..2 -> "winter"
			else -> "unknown"
		}

		println("${month}월은 ${season}입니다.")

		// 4. Roof
		// 4-1. for
		for(number in 1..10) {
			print("$number")
		} // 12345

		val fruits = listOf("apple", "orange", "pineapple")
		for(fruit in fruits) {
			println("Yummy, it's a $fruit cake!")
		}

		// 4-2. while (or do-while : 한번 무조건 실행 후 조건에 맞을때까지 실행)
		var cakesEaten = 0
		while (cakesEaten < 3) {
			println("Eat a cake")
			cakesEaten++
		}
	}

	// 연습문제
	fun exercise() {
		// 1
		val num1 = Random.nextInt(6)
		val num2 = Random.nextInt(6)

		if (num1 == num2) {
			println("You win :)")
		} else {
			println("You lose :(")
		}

		// 2
		val btn = "A"

		println(
			when (btn) {
				"A" -> "Yes"
				"B" -> "No"
				"X" -> "Menu"
				"Y" -> "Nothing"
				else -> "There is no such button"
			}
		)

		// 3
		// 3-1
		var pizzaSlices = 0

		while(pizzaSlices < 8) {
			println("There's only $pizzaSlices slice/s of pizza :(")
			pizzaSlices++
		}
		println("There are $pizzaSlices slices of pizza. Hooray! We have a whole pizza! :D")

		// 3-2
		do {
			println("There's only $pizzaSlices slice/s of pizza :(")
			pizzaSlices++
		} while (pizzaSlices < 8)
		println("There are $pizzaSlices slices of pizza. Hooray! We have a whole pizza! :D")

		// 4
		for(num in 1..100) {
			if(num % 15 == 0) {
				println("fizzbuzz")
			} else if (num % 3 == 0) {
				println("fizz")
			} else if (num % 5 == 0) {
				println("buzz")
			} else {
				println("$num")
			}

			// 또는 when
			println(
				when (num) {
					num % 15 -> "fizzbuzz"
					num % 3 -> "fizz"
					num % 5 -> "buzz"
					else -> "$num"
				}
			)
		}

		// 5
		val words = listOf("dinosaur", "limousine", "magazine", "language")

		for(word in words) {
			if(word.startsWith("l")) {
				println(word)
			}
		}
	}
}