package org.example.intermediate

class `08_NullSafety` {
	fun main() {
		// is 와 !is 연산자
		val myInt = 42
		val myDouble = 3.14
		val myList = listOf(1, 2, 3)

		// Int 타입
		printObjectType(myInt) // It's an Integer with value 42

		// List 타입 (Double이 아님)
		printObjectType(myList) // It's NOT a Double

		// Double 타입 (else 분기 실행)
		printObjectType(myDouble) // Unknown type

		// 예제: Smart Cast 활용
		processValue("Kotlin") // 문자열 길이: 6 \n 대문자: KOTLIN \n 문자열: Kotlin

		processValue(42) // 정수: 42, 2배: 84

		processValue(3.14) // 실수: 3.14, 제곱: 9.8596

		processValue(listOf(1, 2, 3)) // 리스트 크기: 3

		// as? + Elvis 연산자(?:) 조합
		val mixedList: List<Any> = listOf("Hello", 42, "Kotlin", 3.14, "World")

		println("Total length (긴 버전): ${calculateTotalStringLength(mixedList)}") // Total length (긴 버전): 16
		println("Total length (짧은 버전): ${calculateTotalStringLengthShort(mixedList)}") // Total length (짧은 버전): 16

		// Early Returns and Elvis Operator
		// 샘플 사용자 생성
		val user1 = User2(1, "Alice", listOf(2, 3))
		val user2 = User2(2, "Bob", listOf(1))
		val user3 = User2(3, "Charlie", listOf(1))

		// 사용자 맵 생성
		val users = mapOf(1 to user1, 2 to user2, 3 to user3)

		// Early Return 사용
		println(getNumberOfFriends(users, 1)) // 2
		println(getNumberOfFriends(users, 2)) // 1
		println(getNumberOfFriends(users, 4)) // -1 (사용자 없음)

		// Safe Call 사용
		println(getNumberOfFriends2(users, 1)) // 2
		println(getNumberOfFriends2(users, 2)) // 1
		println(getNumberOfFriends2(users, 4)) // -1 (사용자 없음)
	}
}

// Smart Casts and Safe Casts (스마트 캐스트와 안전한 캐스트)
// Casting : 변수나 객체를 특정 타입으로 취급하도록 Kotlin에 지시하는 프로세스
// 타입이 자동으로 캐스팅 되는 것을 "스마트 캐스팅"이라고 함

// is 와 !is 연산자
// 객체가 특정 타입인지 확인하는 연산자
// is - 객체가 해당 타입이면 true 반환 / !is - 객체가 해당 타입이 아니면 true 반환
fun printObjectType(obj: Any) {
	when (obj) {
		is Int -> println("It's an Integer with value $obj")
		!is Double -> println("It's NOT a Double")
		else -> println("Unknown type")
	}
}

// 예제: Smart Cast 활용
fun processValue(value: Any) {
	// is로 타입 체크 후 자동으로 Smart Cast됨!
	if (value is String) {
		// value가 자동으로 String 타입으로 캐스팅됨
		println("문자열 길이: ${value.length}")
		println("대문자: ${value.uppercase()}")
	}

	// when과 함께 사용
	when (value) {
		is Int -> println("정수: $value, 2배: ${value * 2}")
		is Double -> println("실수: $value, 제곱: ${value * value}")
		is String -> println("문자열: $value")
		is List<*> -> println("리스트 크기: ${value.size}")
		else -> println("알 수 없는 타입")
	}
}

// as와 as? 연산자
// as(Unsafe Cast) - 객체를 다른 타입으로 명시적으로 캐스팅(형변환) 함. 캐스팅이 불가능하면 런타임에 프로그램이 크래시됨 -> 안전하지 않은 형변환 연산자
// as?(Safe Cast) - 안전한 캐스팅을 수행함. 캐스팅 실패시 에러 대신 null을 반환함 -> 안전한 형변환 연산자
fun asOperator() {
	val obj: Any = "Kotlin"

	// as(Unsafe Cast) - 실패하면 예외 발생
	try {
		val num: Int = obj as Int
	} catch (e: ClassCastException) {
		println("Unsafe cast 실패: ${e.message}")
	}

	// as?(Safe Cast) - 실패하면 null 반환
	val safeNum: Int? = obj as? Int
	println("Safe cast result: $safeNum") // null

	val safeStr: String? = obj as? String
	println("Safe cast result: $safeStr") // Kotlin
}

// as? + Elvis 연산자(?:) 조합
// 긴 버전
fun calculateTotalStringLength(items: List<Any>): Int {
	var totalLength = 0
	for (item in items) {
		totalLength += if (item is String) {
			item.length
		} else {
			0 // String이 아닌 항목은 0 추가
		}
	}
	return totalLength
}

// 짧은 버전 - as? + Elvis 사용
fun calculateTotalStringLengthShort(items: List<Any>): Int {
	// items.sumOf { ... } - 각 항목에 대해 람다 실행 후 합계 계산
	// it as? String - 항목을 String으로 안전하게 캐스팅
	// ?.length - String이면 length 반환, 아니면 null
	// ?: 0 - null이면 0 반환
	return items.sumOf { (it as? String)?.length ?: 0 }
}

// Null Values and Collections (Null 값과 컬렉션)
// Kotlin은 컬렉션에서 null 값을 처리하는 유용한 함수들을 제공함
// 1. filterNotNull() : null 값을 제거하고 non-null 항목만 남김
fun filterNotNullEx() {
	val emails: List<String?> = listOf(
		"alice@example.com",
		null,
		"bob@example.com",
		null,
		"carol@example.com"
	)

	val validEmails = emails.filterNotNull()
	println(validEmails) // [alice@example.com, bob@example.com, carol@example.com]
}

// 2. listOfNotNull() : 리스트 생성 시 null 값을 자동으로 필터링함
fun listOfNotNullEx() {
	val serverConfig = mapOf(
		"appConfig.json" to "App Configuration",
		"dbConfig.json" to "Database Configuration"
	)

	val requestedFile = "appConfig.json"
	val configFiles = listOfNotNull(serverConfig[requestedFile])
	println(configFiles) // [App Configuration]

	// 존재하지 않는 키 -> null이면 빈 리스트 반환
	val missingFile = "notFound.json"
	val missingConfigFiles = listOfNotNull(serverConfig[missingFile])
	println(missingConfigFiles) // [] (빈 리스트)
}

// 3. maxOrNull() / minOrNull() : 최대/최소 값을 찾고, 없으면 null 반환
fun maxminOrNullEx() {
	// 일주일간 기록된 온도
	val temperatures = listOf(15, 18, 21, 21, 19, 17, 16)

	// 최고 온도 찾기
	val maxTemperature = temperatures.maxOrNull()
	println("Highest temperature recorded: ${maxTemperature ?: "No data"}") // Highest temperature recorded: 21

	// 최저 온도 찾기
	val minTemperature = temperatures.minOrNull()
	println("Lowest temperature recorded: ${minTemperature ?: "No data"}") // Lowest temperature recorded: 15

	// 빈 리스트 테스트
	val emptyTemperatures = listOf<Int>()
	val noMax = emptyTemperatures.maxOrNull()
	println("Empty list max: ${noMax ?: "No data"}") // Empty list max: No data
}

// 4. singleOrNull() : 조건에 맞는 단일 항목을 찾고, 없거나 여러개면 null 반환
// 조건에 맞는 항목이 정확히 1개!일 때만 그 항목 반환
fun singleOrNullEx() {
	val temperatures = listOf(15, 18, 21, 21, 19, 17, 16)

	// 정확히 하루만 30도였는지 확인
	val singleHotDay = temperatures.singleOrNull { it == 30 }
	println("Single hot day with 30 degrees: ${singleHotDay ?: "None"}") // Single hot day with 30 degrees: None

	// 정확히 하나만 15도인지 확인
	val single15Degree = temperatures.singleOrNull { it == 15 }
	println("Single day with 15 degrees: ${single15Degree ?: "None or Multiple"}") // Single day with 15 degrees: 15

	// 21도가 여러 번 나타남
	val multiple21Degrees = temperatures.singleOrNull { it == 21 }
	println("Single day with 21 degrees: ${multiple21Degrees ?: "None or Multiple"}") // Single day with 21 degrees: None or Multiple
}

// 5. firstNotNullOfOrNull() : 람다로 변환 후 첫번째 non-null 값을 반환
data class User(val name: String?, val age: Int?)
fun firstNotNullOfOrNullEx() {
	val users = listOf(
		User(null, 25),
		User("Alice", null),
		User("Bob", 30)
	)

	val firstNonNullName = users.firstNotNullOfOrNull { it.name }
	println(firstNonNullName) // Alice -> 첫번째 non-null 값 반환

	// 나이 찾기
	val firstNonNullAge = users.firstNotNullOfOrNull { it.age }
	println(firstNonNullAge) // 25

	// 모두 null인 경우
	val allNullUsers = listOf(
		User(null, null),
		User(null, null)
	)
	val noName = allNullUsers.firstNotNullOfOrNull { it.name }
	println("No name: ${noName ?: "All null"}") // No name: All null
}

// 6. reduceOrNull() : 람다로 누적 계산하고, 빈 컬렉션이면 null 반환
fun reduceOrNullEx() {
	// 장바구니의 상품 가격들
	val itemPrices = listOf(20, 35, 15, 40, 10)

	// reduceOrNull()로 총 가격 계산
	val totalPrice = itemPrices.reduceOrNull { runningTotal, price ->
		// 첫번째 값을 초기값으로 사용
		// 이후 항목들을 순차적으로 누적
		runningTotal + price
	}
	println("Total price of items in the cart: ${totalPrice ?: "No items"}") // Total price of items in the cart: 120

	// 빈 장바구니
	val emptyCart = listOf<Int>()
	val emptyTotalPrice = emptyCart.reduceOrNull { runningTotal, price ->
		// 빈 컬렉션이면 null 반환
		runningTotal + price
	}
	println("Total price of items in the empty cart: ${emptyTotalPrice ?: "No items"}") // Total price of items in the empty cart: No items
}

// Early Returns and Elvis Operator
// Early Return : 특정 조건에서 함수를 일찍 종료하는 패턴. Elvis 연산자와 함께 사용하면 코드가 매우 간결해짐
data class User2(
	val id: Int,
	val name: String,
	// 친구 user ID 리스트
	val friends: List<Int>
)

// 사용자의 친구 수를 가져오는 함수
// Early Return vs Safe Call 비교
// Early Return 사용
fun getNumberOfFriends(users: Map<Int, User2>, userId: Int): Int {
	// 사용자를 가져오거나 찾지 못하면 -1 반환
	val user = users[userId] ?: return -1

	// 친구 수 반환
	return user.friends.size
}

// Safe Call 사용 (더 간결하지만 가독성은 떨어질 수 있음)
fun getNumberOfFriends2(users: Map<Int, User2>, userId: Int): Int {
	return users[userId]?.friends?.size ?: -1
}