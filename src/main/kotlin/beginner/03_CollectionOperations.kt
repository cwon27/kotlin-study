package org.example.beginner

class `03_CollectionOperations` {
	fun operations() {
		// 1. forEach : 반복문
		val items = listOf("A", "B", "C")
		items.forEach { item ->
			println("항목: $item")
		}

		// 인덱스와 함께
		items.forEachIndexed { index, item ->
			println("$index: $item")
		}

		// 2. filter : 필터링
		// .filter { 조건 }
		val nums = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		val evenNumbers = nums.filter { it % 2 == 0 } // 짝수
		val greaterThan5 = nums.filter { it > 5 } // 5보다 큰 수
		println("짝수: $evenNumbers")
		println("5보다 큰 수: $greaterThan5")

		// 3. map : 변환
		// .map { 변환식 }
		val squared = nums.map { it * it }
		val doubled = nums.map { it * 2 }
		println("제곱: $squared")
		println("2배: $doubled")

		val names = listOf("kim", "lee", "park")
		val upperNames = names.map { it.uppercase() }
		println("대문자: $upperNames")

		// 4. any, all, none : 조건 확인
		val numbers2 = listOf(1, 3, 5, 7, 9)
		println("짝수가 있나? ${numbers2.any { it % 2 == 0 }}") // false
		println("모두 양수? ${numbers2.all { it > 0 }}") // true
		println("음수가 없나? ${numbers2.none { it < 0 }}") // true

		// 5. take, drop, slice : 일부 추출
		println("앞에서 3개: ${nums.take(3)}")
		println("뒤에서 3개: ${nums.takeLast(3)}")
		println("앞 3개 제외: ${nums.drop(3)}")
		println("인덱스 2~5: ${nums.slice(2..5)}")

		// 6. 컬렉션 체이닝(조합)
		val result = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
			.filter { it % 2 == 0 } // 짝수만
			.map { it * it } // 제곱
			.filter { it > 10 } // 10보다 큰 것만
			.sorted() // 정렬

		println("짝수 → 제곱 → 10 초과 → 정렬: $result")
	}
}