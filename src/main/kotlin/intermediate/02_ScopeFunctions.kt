package org.example.intermediate

class `02_ScopeFunctions` {
	// 객체의 컨텍스트 내에서 코드 블록을 실행하는 함수
	// 임시 스코프를 만들어 객체 이름 없이 해당 객체에 접근할 수 있게 해줌

	fun main() {
		/*
		 1. let
		 : Null 체크, 변환
		   객체 참조 : it (다른 이름으로 변경 가능)
		   반환값: Lambda 결과
		 */
		val result = "Kotlin".let {
			println("문자열: $it")
			it.length // 반환값
		}
		println(result) // 6

		// Null 체크에 활용 (가장 많이 사용!)
		// name이 null이면 블록이 실행되지 않음
		val name: String? = "홍길동"
		name?.let {
			println("이름: $it")
			println("길이: ${it.length}")
		}

		// 변수 이름 변경
		val number: Int? = 42
		number?.let { num ->
			println("숫자: $num")
		}

		// 실습 예제 - 리스트 변환
		val names = listOf("alice", "bob", "charlie")
		val upperNames = names.map { it.uppercase() }
			.let { transformedList ->
				println("변환된 리스트: $transformedList")
				transformedList
			}

		/*
		 2. run
		 : 객체 초기화 + 결과 계산
		   객체 참조: this (생략 가능)
		   반환값: Lambda 결과
		 */
		val result2 = Person().run {
			name2 = "김철수"
			age = 30
			"${name2}은 ${age}살입니다" // 반환값
		}
		println(result2) // 김철수은 30살입니다

		// 널 체크와 함께
		val person: Person? = Person()
		person?.run {
			name2 = "이영희"
			println("설정 완료: $name2")
		}

		/*
		 3. with
		 : 한 객체의 여러 메서드 호출
		   객체 참조: this (생략 가능)
		   반환값: Lambda 결과
		   Extension function이 아님 (객체를 파라미터로 받음)
		 */
		val person2 = Person()
		val description = with(person2) {
			name2 = "박민수"
			age = 25
			"이름: $name2, 나이: $age"  // 반환값
		}
		println(description) // 이름: 박민수, 나이: 25

		// 여러 메서드 호출
		val numbers = mutableListOf(1, 2, 3)
		with(numbers) {
			add(4)
			add(5)
			println("리스트: $this") // 리스트: 1, 2, 3, 4, 5
			remove(1)
		}

		/*
		 4. apply
		 : 객체 초기화 및 설정
		   객체 참조: this (생략 가능)
		   반환값: 객체 자체
		 */
		val person3 = Person2().apply {
			name3 = "최지우"
			age2 = 28
			city = "서울"
		}
		println(person3) // Person2(name3=최지우, age2=28, city=서울)

		// 메서드 체이닝
		val updatedPerson = person3.apply {
			age2 = 29
		}.apply {
			city = "부산"
		}
		println(updatedPerson) // Person2(name3=최지우, age2=29, city=부산)

		// 객체 복사 및 수정
		val newPerson = person3.copy().apply {
			age2 = 30
		}
		println(newPerson) // Person2(name3=최지우, age2=30, city=부산)

		/*
		 5. also
		 : 부가 작업, 로깅, 디버깅
		   객체 참조: it (다른 이름으로 변경 가능)
		   반환값: 객체 자체
		 */
		val person4 = Person().also {
			it.name2 = "정수진"
			it.age = 32
			println("Person 생성: $it") // 로깅
		}

		// 메서드 체이닝 중간에 로깅
		val numbers2 = mutableListOf(1, 2, 3)
			.also { println("초기 리스트: $it") } // 초기 리스트: 1, 2, 3
			.apply { add(4) }
			.also { println("4 추가 후: $it") } // 4 추가 후: 1, 2, 3, 4
	}

	/*
	어떤 함수를 써야할까?
	질문: 객체를 반환해야 하나요?
	├─ YES → apply 또는 also 사용
	│	└─ 질문: 객체 멤버를 주로 수정하나요?
	│		├─ YES → apply 사용 (this로 직접 접근) : 객체 설정 및 초기화
	│		└─ NO → also 사용 (로깅, 부가작업) : 로깅/디버깅
	│
	└─ NO (결과값을 반환) → let, run, with 사용
		└─ 질문: Null 체크가 필요한가요?
			├─ YES → let 사용 (?.let) : Null 체크
			└─ NO → run 또는 with 사용
				└─ 질문: Extension으로 호출하고 싶나요?
					├─ YES → run 사용 (object.run) : 계산 결과 반환
					└─ NO → with 사용 (with(object)) : 여러 메서드 호출
	*/

	// 실습 예제
	fun exercise() {
		// apply - 객체 초기화
		val user = User().apply {
			id = "user001"
			name = "김코틀린"
			email = "kotlin@example.com"
			age = 30
		}

		// also - 생성 확인(로깅)
		val verifiedUser = user.also {
			println("사용자 생성: ${it.name}")
			println("이메일: ${it.email}")
		}

		// let - null 체크 및 변환
		val userName: String? = "홍길동"
		userName?.let {
			println("환영합니다, ${it}님!")
		}

		// run - 정보 생성
		val userInfo = user.run {
			"""
			=== 사용자 정보 ===
			ID: $id
			이름: $name
			이메일: $email
			나이: $age
			""".trimIndent()
		}
		println(userInfo)

		// with - 여러 작업
		with(user) {
			println("\n사용자 업데이트 중...")
			age = 31
			email = "newemail@example.com"
			println("업데이트 완료!")
		}

		// 리스트 처리
		val numbers = mutableListOf<Int>()
			.apply {
				add(1)
				add(2)
				add(3)
			}
			.also { println("초기 리스트: $it") }

		// 변환 및 필터링
		val result = numbers
			.map { it * 2 }
			.also { println("2배 후: $it") }
			.filter { it > 3 }
			.also { println("필터링 후: $it") }
			.let { list ->
				"리스트 크기: ${list.size}, 합계: ${list.sum()}"
			}

		println(result)
	}
}

data class Person(var name2: String = "", var age: Int = 0)
data class Person2(var name3: String = "", var age2: Int = 0, var city: String = "")

// 실습 예제 클래스
data class User(
	var id: String = "",
	var name: String = "",
	var email: String = "",
	var age: Int = 0
)
