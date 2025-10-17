package org.example.beginner

class `02_Collections` {
	fun list() {
		// List는 추가된 순서대로 저장하며 중복된 항목 허용
		// 1. List : 읽기 전용(불변)
		val fruits = listOf("사과", "바나나", "오렌지")
		println(fruits)
		println("첫 번째 과일: ${fruits[0]}")
		println("List 크기: ${fruits.size}")
		println("List 크기: ${fruits.count()}")
		println("바나나 포함? ${fruits.contains("바나나")}")

		// 2. MutableList : 변경 가능(가변)
		val numbers = mutableListOf(1, 2, 3)
		println("초기: $numbers") // [1, 2, 3]

		// 항목 엑세스
		println("첫번째 숫자는 ${numbers[0]}입니다.")
		println("첫번째 숫자는 ${numbers.first()}입니다.")
		println("마지막 숫자는 ${numbers.last()}입니다.")

		// 변경
		numbers.add(4) // 추가
		numbers.add(0, 0) // 인덱스 0에 추가
		numbers.remove(2) // 값 제거
		numbers.removeAt(0) // 인덱스로 제거
		println("변경 후: $numbers") // [1, 3, 4]

		// 3. List 생성 방법들
		// 빈 리스트에는 제너릭 타입을 명시해주는 것이 좋음!
		val emptyList = listOf<String>() // 빈 리스트
		val emptyMutableList = mutableListOf<Int>() // 빈 가변 리스트

		// 인터페이스로 받을 땐 변수에 타입 명시!
		// -> 캐스팅 : 의도적으로 수정을 막기 위해서 사용
		// 변수 타입을 List로 명시하면, 실제로는 수정 가능한 리스트지만 수정 기능을 사용할 수 없게 만드는 것 -> 인터페이스로 받는다는 의미
		val numbersLocked: List<Int> = numbers

		// 크키가 5인 리스트를 생성하면서 각 요소를 람다식으로 초기화함
		// it은 인덱스를 의미(0, 1, 2, 3, 4)
		// 따라서 각 인덱스에 2를 곱한 값이 저장됨
		val repeatedList = List(5) { it * 2 } // [0, 2, 4, 6, 8]
		println("반복 생성: $repeatedList")
	}

	fun set() {
		// Set은 순서가 없고 중복 허용 X
		// 순서가 없으므로 특정 인덱스의 항목에 접근 할 수 없음
		// 1. Set : 읽기 전용(불변)
		val uniqueNumbers = setOf(1, 2, 3, 2, 1) // 중복 자동 제거
		println(uniqueNumbers) // [1, 2, 3]

		// 2. MutableSet : 변경 가능(가변)
		val fruit = mutableSetOf("apple", "banana", "cherry", "cherry")
		println(fruit) // ["apple", "banana", "cherry"]

		// 의도적 수정 막기
		val fruitLocked: Set<String> = fruit
	}

	fun map() {
		// Map은 항목을 key-value 쌍으로 저장함.
		// key를 참조해 값에 접근
		// List처럼 index를 사용하지 않고 값을 찾고 싶을 때 사용
		// 모든 key 값은 고유해야함(중복 X)
		// 하지만 Map에는 중복된 값이 있을 수 있음
		// 1. Map : 읽기 전용(불변)
		val ages = mapOf(
			"철수" to 25,
			"영희" to 30,
			"민수" to 28
		)
		println(ages) // {철수=25, 영희=30, 민수=28}
		println("철수 나이: ${ages["철수"]}")
		println("키 목록: ${ages.keys}")
		println("값 목록: ${ages.values}")

		// 2. MutableMap : 변경 가능(가변)
		val scores = mutableMapOf(
			"국어" to 90,
			"영어" to 85
		)
		scores["수학"] = 95 // 추가
		scores["영어"] = 88 // 수정
		scores.remove("국어") // 제거
		println(scores) // {영어=88, 수학=95}

		// 특정 키 포함 여부 확인
		println(scores.containsKey("수학")) // true

		// 의도적 수정 막기
		val scoresLocked: Map<String, Int> = scores;
	}

	// 연습문제
	fun exercise() {
		//1. "녹색" 숫자 목록과 "빨간색" 숫자 목록이 있습니다. 총 몇 개의 숫자가 있는지 출력하는 코드를 완성하세요.
		val greenNumbers = listOf(1, 4, 23)
		val redNumbers = listOf(17, 2)
		// Write your code here
		println(greenNumbers.count() + redNumbers.count())

		// 2. 서버에서 지원하는 프로토콜 세트가 있습니다.
		// 사용자가 특정 프로토콜 사용을 요청합니다.
		// 요청된 프로토콜이 지원되는지 여부를 확인하는 프로그램을 작성하세요( isSupported = boolean 값이어야 함).
		val SUPPORTED = setOf("HTTP", "HTTPS", "FTP")
		val requested = "smtp"
		val isSupported = SUPPORTED.contains(requested.uppercase()) // Write your code here
		// val isSupported = requested.uppercase() in SUPPORTED
		println("Support for $requested: $isSupported")

		// 3. 1부터 3까지의 정수와 그에 해당하는 철자를 연결하는 지도를 정의하세요.
		// 이 지도를 사용하여 주어진 숫자의 철자를 쓰세요.
		val number2word = mapOf(
			1 to "one",
			2 to "two",
			3 to "three"
		)// Write your code here
		val n = 2
		println("$n is spelt as '${number2word[n]}'")
	}
}