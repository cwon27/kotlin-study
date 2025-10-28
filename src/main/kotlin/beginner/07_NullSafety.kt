package org.example.beginner

class `07_NullSafety` {
	fun main() {
		// 1. Nullable vs Non-Nullable
		var name: String = "Kotlin"
		// name = null -> 컴파일 에러! null 불가

		var nullableName: String? = "홍길동"
		nullableName = null // null 가능
		// 타입 뒤에 ? -> null 가능

		// 2. Safe Call 연산자 (?.) -> null이면 null 반환, 아니면 메서드 실행
		val text: String? = "Hello"
		val nullText: String? = null

		println("길이: ${text?.length}") // 5
		println("길이: ${nullText?.length}") // null

		// 체이닝 가능
		val city: String? = "seoul"
		val result = city?.uppercase()?.substring(0, 3)
		println("결과: $result")  // SEO

		// 3. Elvis 연산자 (?:) -> null이면 기본값 사용
		val name1: String? = null
		val name2: String? = "김철수"
		val displayName1 = name1 ?: "이름 없음"
		val displayName2 = name2 ?: "이름 없음"

		println("표시 이름 1: $displayName1") // 이름 없음
		println("표시 이름 2: $displayName2") // 김철수

		// 함수와 함께 사용
		fun getLength(str: String?): Int {
			return str?.length ?: 0
		}

		println("길이: ${getLength("Hello")}") // 5
		println("길이: ${getLength(null)}") // 0

		// 4. Safe Cast (as?) -> 캐스팅 실패하면 null 반환
		val obj: Any = "문자열"
		val str: String? = obj as? String
		val num: Int? = obj as? Int

		println("문자열 캐스팅: $str") // 문자열
		println("숫자 캐스팅: $num") // null

		// 5. let 함수와 null 처리 -> null이 아닐 때만 실행
		val nullableValue: String? = "Kotlin"
		nullableValue?.let {
			println("값이 존재합니다: $it")
			println("길이: ${it.length}")
		}

		val nullValue: String? = null
		nullValue?.let {
			println("실행 안됨") // null이라서 실행 안됨
		} ?: println("값이 null입니다")

		// 6. also, run, apply
		// also : 객체 자체를 반환
		val list1 = mutableListOf(1, 2, 3).also {
			println("리스트 생성: $it")
			it.add(4)
		}
		println("also 결과: $list1")

		// run: 람다 결과 반환
		val length = "Hello".run {
			println("문자열: $this")
			this.length
		}
		println("run 결과: $length")

		// apply: 객체 설정 후 반환
		data class Person(var name: String = "", var age: Int = 0)
		val person = Person().apply {
			name = "홍길동"
			age = 30
		}
		println("apply 결과: $person")

		// 7. 컬렉션과 null
		// Nullable 요소를 가진 리스트
		// List<Int?>: null 요소 가능
		val nullableList: List<Int?> = listOf(1, 2, null, 4, null, 6)
		println("전체: $nullableList")

		// null 제거 :filterNotNull()
		val nonNullList = nullableList.filterNotNull()
		println("null 제거: $nonNullList")

		// Nullable 리스트
		// List<Int>?: 리스트 자체가 null 가능
		val maybeList: List<Int>? = listOf(1, 2, 3)
		val emptyList: List<Int>? = null

		println("크기: ${maybeList?.size}") // 3
		println("크기: ${emptyList?.size}") // null'

		// 8. requireNotNull & checkNotNull
		val value1: String? = "Hello"
		val value2: String? = null

		// requireNotNull: null이면 IllegalArgumentException
		val required = requireNotNull(value1) { "값이 필요합니다!" }
		println("필수값: $required")
		// val fail = requireNotNull(value2) { "값이 없음!" } // 에러!

		// checkNotNull: null이면 IllegalStateException
		val checked = checkNotNull(value1) { "상태 오류!" }
		println("체크값: $checked")
	}
}