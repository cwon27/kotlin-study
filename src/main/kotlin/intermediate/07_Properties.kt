package org.example.intermediate

import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty

class `07_Properties` {
	// Property : 자동으로 Getter/Setter를 생성해주는 강력한 기능 <-> Java의 필드(Field)
	// 클래스, 인터페이스, 최상위 레벨에서 선언 가능
	// 커스텀 접근자 정의 가능
	// Backing Field를 통한 값 저장

	fun main() {
		val person = Person()

		// Getter 호출 (자동)
		println(person.name) // 홍길동
		println(person.age) // 25

		// Setter 호출 (자동)
		person.age = 26
		println(person.age) // 26

		// val은 재할당 불가
		// person.name = 김철수 // 컴파일 에러!

		// Custom Accessors (커스텀 접근자)
		val rect = Rectangle(5, 10)
		println("넓이: ${rect.area}") // 50 (계산됨)

		rect.description = "파란색"
		println(rect.description) // 사각형: 파란색

		// 예제: 검증 로직
		val user = User()

		user.email = "HONG@EXAMPLE.COM"
		println("이메일: ${user.email}") // hong@example.com

		user.email = "잘못된이메일" // 유효하지 않은 이메일: 잘못된이메일 <- 경고 출력!

		user.age = -5
		println("나이: ${user.age}") // 나이: 0

		user.age = 200
		println("나이: ${user.age}") // 나이: 150

		// Late-Initialized Properties (lateinit)
		val activity = MainActivity()

		activity.checkInit() // 아직 초기화되지 않음
		activity.onCreate()
		activity.checkInit() // TextView: 초기화됨

		// 예제: DB 의존성 주입
		val repo = UserRepository()

		// 의존성 주입
		repo.database = Database()
		repo.database.connect()

		// 사용
		println(repo.findUser("123")) // SELECT * FROM users WHERE id = 123

		// Lazy Properties (지연 프로퍼티)
		val loader = DataLoader() // DataLoader 생성
		println(loader.heavyData) // 첫번째 접근 -> 이때 초기화됨
		println(loader.heavyData) // 두번째 접근 -> 캐시된 값 반환
		println(loader.simpleData) // simpleData 첫번째 접근 -> 이때 초기화됨

		// Extension properties(확장 프로퍼티)
		val person2 = Person2(firstName = "John", lastName = "Doe")

		// 확장 속성 사용
		println(person2.fullName) // John Doe

		// Delegated Properties (위임 프로퍼티)
		val user2 = User2()

		user2.name = "홍길동" // name: 초기값 → 홍길동
		user2.name = "김철수" // name: 홍길동 → 김철수

		user2.age = 25
		println("나이: ${user2.age}") // 25

		user2.age = -5 // 거부됨(양수가 아니라서)
		println("나이: ${user2.age}") // 25 (변경 안됨)

		// 커스텀 Delegate
		val form = Form()

		form.title = "kotlin properties" // title 설정: KOTLIN PROPERTIES
		form.description = "learning" // description 설정: LEARNING

		println("\n제목: ${form.title}")
		println("설명: ${form.description}")

		// Observable properties (관찰 가능한 프로퍼티)
		val thermostat = Thermostat()
		thermostat.temperature = 22.5 // Temperature updated: 20.0°C -> 22.5°C

		thermostat.temperature = 27.0 // Warning: Temperature is too high! (22.5°C -> 27.0°C)
	}
}

class Person {
	// 읽기 전용 프로퍼티 (val) - getter만 생성
	val name: String = "홍길동"

	// 변경 가능 프로퍼티 (var) - getter와 setter 생성
	var age: Int = 25

	// 내부적으론 다음과 같음 (age)
	var age2: Int = 25
		get() = field // 필드에서 속성 값을 검색함
		set(value) { // value 매개변수로 값을 받아서 필드에 할당함
			// 함수에서 속성을 직접 참조하면 무한 루프가 발생하고 런타임 오류 발생
			// 하지만 백킹 필드(field)를 사용하면 문제 해결!
			field = value
		}
}

// Custom Accessors (커스텀 접근자)
// 기본 Getter/Setter 대신 커스텀 로직을 추가
// 값 검증, 변환, 계산된 프로퍼티, 로깅 및 모니터링에 사용
class Rectangle(val width: Int, val height: Int) {
	// 커스텀 getter - 계산된 프로퍼티
	val area: Int
		get() = width * height

	// 커스텀 getter와 setter
	var description: String = ""
		get() = "사각형: $field"
		set(value) {
			field = value.uppercase()
		}
}

// 예제: 검증 로직
class User {
	var email: String = ""
		set(value) {
			if (value.contains("@")) {
				field = value.lowercase()
			} else {
				println("유효하지 않은 이메일: $value")
			}
		}

	var age: Int = 0
		set(value) {
			field = when {
				value < 0 -> 0
				value > 150 -> 150
				else -> value
			}
		}
}

// Backing Fields (백킹 필드)
// 프로퍼티의 실제 값을 메모리제 저장하는 필드
// 커스텀 접근자에서 field 키워드로 접근 -> 무한 재귀 호출 방지!
// 잘못된 예시 - 무한 재귀
class Wrong {
	var name: String = ""
		get() = name // 자기 자신을 다시 호출 → 무한 재귀!
		set(value) {
			name = value // 자기 자신을 다시 호출 → 무한 재귀!
		}
}

// 올바른 예시 - field 사용
class Correct {
	var name: String = ""
		get() = field // backing field 접근
		set(value) {
			field = value // backing field에 저장
		}
}

// Late-Initialized Properties (lateinit)
// 나중에 초기화할 것을 약속하는 키워드 -> 생성자에서 초기화 하지 않아도 됨
// var에만 사용(val X)
// Non-null 타입에만 사용 가능
// Primitive 타입 불가 (Int, Boolean 등)
// 초기화 전에 접근시 예외 발생
// ::property.isInitialized로 초기화 여부 확인 가능
class MainActivity {
	// lateinit 사용 - 나중에 초기화
	lateinit var textView: String

	fun onCreate() {
		textView = "초기화됨"
	}

	fun checkInit() {
		if (::textView.isInitialized) {
			println("TextView: $textView")
		} else {
			println("아직 초기화되지 않음")
		}
	}
}

// 예제: DB 의존성 주입
class Database {
	fun connect() = println("데이터베이스 연결됨")
	fun query(sql: String) = "결과: $sql"
}

class UserRepository {
	// DI 프레임워크가 나중에 주입
	lateinit var database: Database

	fun findUser(id: String): String {
		if (!::database.isInitialized) {
			throw IllegalStateException("Database가 주입되지 않았습니다")
		}
		return database.query("SELECT * FROM users WHERE id = $id")
	}
}

// Lazy Properties (지연 프로퍼티)
// 처음 접근할 때 초기화되는 프로퍼티
// 성능 최적화와 리소스 관리에 유용
// val에만 사용(var X)
// 첫 접근시 단 한번만 초기화
// 결과값이 캐시됨 (메모이제이션)
// Thread-safe (기본 동기화)
// 무거운 객체 생성 지연에 유용 (비용이 큰 계산)
class DataLoader {
	// lazy 초기화 - 처음 접근 시 실행
	val heavyData: List<String> by lazy {
		println("무거운 데이터 로딩 중...")
		Thread.sleep(1000) // 시간이 오래 걸리는 작업
		listOf("Data1", "Data2", "Data3")
	}

	val simpleData: String by lazy {
		println("Simple 데이터 초기화")
		"Simple"
	}
}

// Extension properties(확장 프로퍼티)
// 소스 코드를 수정하지 않고도 기존 클래스에서 새 속성을 추가할 수 있음
// 확장하려는 클래스 이름 뒤에 .속성이름
// 하지만 클래스의 기존 속성은 재정의 할 수 없음!
data class Person2(val firstName: String, val lastName: String)
// 전체 이름을 가져오기 위한 확장 속성
val Person2.fullName: String
	get() = "$firstName $lastName"

// Delegated Properties (위임 프로퍼티)
// 프로퍼티의 Getter/Setter 로직을 다른 객체에 위임하는 패턴
// -> 복잡한 속성 저장 요구 사항이 있는 경우 유용(DB 테이블, 브라우저 세션 등)
class User2 {
	// Observable - 값 변경 감지
	var name: String by Delegates.observable("초기값") { property, oldValue, newValue ->
		println("${property.name}: $oldValue → $newValue")
	}

	// Vetoable - 값 변경 검증
	var age: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
		newValue >= 0 // 양수일 때만 변경 허용
	}
}

// 커스텀 Delegate
class UppercaseDelegate {
	private var value: String = ""

	operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
		return value
	}

	operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: String) {
		value = newValue.uppercase()
		println("${property.name} 설정: $value")
	}
}

class Form {
	var title: String by UppercaseDelegate()
	var description: String by UppercaseDelegate()
}

// Observable properties (관찰 가능한 프로퍼티)
// 속성 값의 변경 여부를 모니터링할 때 사용
// 속성 값의 변경을 감지하고 이를 기반으로 반응을 트리거하려는 경우에 유용
class Thermostat {
	var temperature: Double by observable(20.0) { _, old, new ->
		if (new > 25) {
			println("Warning: Temperature is too high! ($old°C -> $new°C)")
		} else {
			println("Temperature updated: $old°C -> $new°C")
		}
	}
}