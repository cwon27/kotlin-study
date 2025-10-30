package org.example.intermediate

class `05_Objects` {
	/*
	 Kotlin에서는 객체 선언을 사용하여 단일 인스턴스를 가진 클래스(싱글톤)를 선언할 수 있음
	 객체 선언은 프로그램의 단일 참조점으로 사용하거나 시스템 전체에서 동작을 조정하기 위해 클래스를 만들 때 유용함
	 Kotlin의 객체는 지연(Lazy) 방식 으로 접근 시에만 생성됨(처음 접근 시 생성)
	 또한 모든 객체가 스레드로부터 안전한 방식으로 생성되도록 보장하므로, 이를 직접 확인할 필요가 없음!

	 * 특징
	 1. 싱글톤(Singleton) 패턴을 구현
	 2. 단 하나의 인스턴스만 존재
	 3. Lazy 초기화 - 처음 접근할 때 생성됨
	 4. Thread-safe - 자동으로 동기화 처리
	 5. 생성자를 가질 수 없음

	 * 이럴때 사용하세요
	 1. 앱 전체에서 하나만 필요한 경우
	 2. 설정, 로거, 네트워크 매니저 등
	*/

	fun main() {
		// 클래스는 인스턴스를 생성해야 함
		val obj1 = MyClass()
		val obj2 = MyClass()
		println(obj1 === obj2) // false (다른 인스턴스)

		// Object는 이름으로 직접 접근
		MySingleton.doSomething()

		// 같은 인스턴스
		val singleton1 = MySingleton
		val singleton2 = MySingleton
		println(singleton1 === singleton2) // true (동일한 인스턴스)

		// 예제 1: 간단한 싱글톤
		// 직접 접근
		DatabaseManager.connectionString = "localhost:5432/test"
		DatabaseManager.connect()
		println(DatabaseManager.query("SELECT * FROM users"))
		DatabaseManager.disconnect()

		// Data object
		println(AppConfig) // AppConfig
		println(AppConfig.appName) // My Application

		// 예제 2: 인터페이스 구현
		println(AdminAuth) // AdminAuth
		println(UserAuth) // UserAuth

		val isAdmin = AdminAuth.authenticate("admin", "admin123")
		println("관리자 인증: $isAdmin") // true

		val isUser = UserAuth.authenticate("user", "password")
		println("사용자 인증: $isUser") // true

		// 같은 타입의 data object는 항상 같음
		println(AdminAuth == AdminAuth) // true
		println(AdminAuth == UserAuth) // false

		// Companion object의 멤버는 클래스 이름으로 접근
		MyClass2.staticFunction()
		println(MyClass2.staticProperty)

		// 인스턴스 함수는 객체를 생성해야 함
		val obj = MyClass2()
		obj.instanceFunction()

		// 예제: Factory 패턴
		val admin = User2.createAdmin("김관리", "admin@example.com")
		val user = User2.createRegularUser("이사용", "user@example.com")
		val guest = User2.createGuest()

		admin.displayInfo() // [ADMIN] 김관리 (admin@example.com)
		user.displayInfo() // [USER] 이사용 (user@example.com)
		guest.displayInfo() // [GUEST] Guest (guest@example.com)

		// Object Expressions (익명 객체)
		// 일회성으로 사용되는 익명 객체 -> Java의 익명 클래스와 유사
		val tempObject = object {
			val x = 10
			val y = 20

			fun sum() = x + y
		}

		println("x: ${tempObject.x}") // x: 10
		println("y: ${tempObject.y}") // y: 20
		println("sum: ${tempObject.sum()}") // sum: 30

		// 실전 활용 패턴 1: Logging 시스템
		Logger.setDebug(true)

		Logger.debug("앱 시작") // [DEBUG] 앱 시작
		Logger.info("사용자 로그인") // [INFO] 사용자 로그인
		Logger.error("네트워크 오류") // [ERROR] 네트워크 오류

		println("\n=== 전체 로그 ===")
		Logger.getLogs().forEach { println(it) }

		Logger.clearLogs()

		// 실전 활용 패턴 2: 빌더 패턴과 Companion Object
		val coffee1 = Coffee.builder()
			.size("Large")
			.type("Latte")
			.withMilk()
			.sugar(2)
			.build()

		val coffee2 = Coffee.builder()
			.type("Cappuccino")
			.hot()
			.sugar(1)
			.build()

		println(coffee1) // 아이스 Latte (Large) - 우유 O, 설탕 2개
		println(coffee2) // 핫 Cappuccino (Regular) - 우유 X, 설탕 1개
	}
}

// Class - 여러 인스턴스 생성 가능
class MyClass {
	fun doSomething() {
		println("클래스 인스턴스")
	}
}

// Object - 단 하나의 인스턴스
object MySingleton {
	fun doSomething() {
		println("싱글톤 인스턴스")
	}
}

// 예제 1: 간단한 싱글톤
// 데이터베이스 연결 관리자 (싱글톤)
object DatabaseManager {
	var connectionString: String = ""
	private var isConnected: Boolean = false

	fun connect() {
		if (!isConnected) {
			println("데이터베이스에 연결 중...")
			println("연결 문자열: $connectionString")
			isConnected = true
		} else {
			println("이미 연결되어 있습니다.")
		}
	}

	fun disconnect() {
		if (isConnected) {
			println("데이터베이스 연결 종료")
			isConnected = false
		}
	}

	fun query(sql: String): String {
		return if (isConnected) {
			"쿼리 실행: $sql"
		} else {
			"연결되지 않았습니다!"
		}
	}
}

// Data Objects : Data class처럼 자동으로 toString(), equals() 등을 생성해주는 Object
// copy() 함수는 없음 (싱글톤이므로)
data object AppConfig {
	var appName: String = "My Application"
	var version: String = "1.0.0"
}

// 예제 2: 인터페이스 구현
interface Auth {
	fun authenticate(username: String, password: String): Boolean
}

// Data object로 인터페이스 구현
data object AdminAuth : Auth {
	override fun authenticate(username: String, password: String): Boolean {
		println("관리자 인증: $username")
		return username == "admin" && password == "admin123"
	}
}

data object UserAuth : Auth {
	override fun authenticate(username: String, password: String): Boolean {
		println("사용자 인증: $username")
		return username.isNotEmpty() && password.length >= 6
	}
}

// Companion Objects (컴패니언 객체)
// 클래스 내부에 정의되는 객체로, Java의 static 멤버와 유사한 역할
// Factory 패턴 구현에 유용
class MyClass2 {
	companion object {
		fun staticFunction() {
			println("Static처럼 동작하는 함수")
		}

		val staticProperty = "Static 프로퍼티"
	}

	fun instanceFunction() {
		println("인스턴스 함수")
	}
}

// 예제: Factory 패턴
class User2 private constructor(
	val name: String,
	val email: String,
	val role: String
) {
	companion object Factory { // Companion object는 이름을 가질 수 있음 (선택사항)
		fun createAdmin(name: String, email: String): User2 {
			return User2(name, email, "ADMIN")
		}

		fun createRegularUser(name: String, email: String): User2 {
			return User2(name, email, "USER")
		}

		fun createGuest(): User2 {
			return User2("Guest", "guest@example.com", "GUEST")
		}
	}

	fun displayInfo() {
		println("[${role}] $name ($email)")
	}
}

// 실전 활용 패턴 1: Logging 시스템
object Logger {
	private var debugEnabled = false
	private val logs = mutableListOf<String>()

	fun setDebug(enabled: Boolean) {
		debugEnabled = enabled
	}

	fun debug(message: String) {
		if (debugEnabled) {
			val log = "[DEBUG] $message"
			println(log)
			logs.add(log)
		}
	}

	fun info(message: String) {
		val log = "[INFO] $message"
		println(log)
		logs.add(log)
	}

	fun error(message: String) {
		val log = "[ERROR] $message"
		println(log)
		logs.add(log)
	}

	fun getLogs(): List<String> = logs.toList()

	fun clearLogs() {
		logs.clear()
	}
}

// 실전 활용 패턴 2: 빌더 패턴과 Companion Object
data class Coffee(
	val size: String,
	val type: String,
	val milk: Boolean,
	val sugar: Int,
	val ice: Boolean
) {
	companion object {
		fun builder() = Builder()
	}

	class Builder {
		private var size: String = "Regular"
		private var type: String = "Americano"
		private var milk: Boolean = false
		private var sugar: Int = 0
		private var ice: Boolean = true

		fun size(size: String) = apply { this.size = size }
		fun type(type: String) = apply { this.type = type }
		fun withMilk() = apply { this.milk = true }
		fun sugar(count: Int) = apply { this.sugar = count }
		fun hot() = apply { this.ice = false }

		fun build() = Coffee(size, type, milk, sugar, ice)
	}

	override fun toString(): String {
		val temp = if (ice) "아이스" else "핫"
		val milkStr = if (milk) "우유 O" else "우유 X"
		val sugarStr = if (sugar > 0) "설탕 ${sugar}개" else "설탕 X"
		return "$temp $type ($size) - $milkStr, $sugarStr"
	}
}