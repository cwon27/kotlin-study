package org.example.beginner

class `06_Classes` {
	fun main() {
		// 1. Data Class -> toString, equals, copy 자동 제공
		val user1 = User("홍길동", 25)
		val user2 = User("홍길동", 25)

		println("user1: $user1") // toString() 자동 생성
		println("user1 == user2: ${user1 == user2}") // equals() 자동 생성
		// copy(): 일부 값만 변경한 복사본 생성
		val user3 = user1.copy(age = 26) // User("홍길동", 26)
		println("user3: $user3")

		// 구조 분해 (Destructuring)
		val (name, age) = user1
		println("이름: $name, 나이: $age")

		// 2. 상속
		val dog = Dog("멍멍이", 3)
		dog.makeSound() // 멍멍
		dog.eat()
		dog.play()

		// 3. 추상 클래스
		val circle = Circle(5.0)
		println("원의 넓이: ${circle.area()}")

		// 4. 인터페이스
		val smartphone = Smartphone()
		smartphone.call("010-1234-5678")
		smartphone.sendMessage("안녕하세요!")
		smartphone.browse("www.example.com")

		// 5. Companion Object (정적 멤버)
		val calc1 = Calculator.add(10, 20)
		val calc2 = Calculator.multiply(5, 6)
		println("10 + 20 = $calc1")
		println("5 × 6 = $calc2")

		println("파이: ${MathUtils.PI}")
		println("절대값: ${MathUtils.abs(-10)}")

		// 6. 싱글톤 (Object)
		// DB 연결이 1개만 생김! (효율적)
		Database.connect()
		Database.query("SELECT * FROM users")
		Database.disconnect()

		// 7. 중첩 클래스와 내부 클래스
		val outer = Outer()
		val nested = Outer.Nested()
		nested.printMessage()

		val inner = outer.Inner()
		inner.printMessage()

		// 8. Sealed Class
		fun handleResult(result: Result) {
			when (result) {
				is Result.Success -> println("성공: ${result.data}")
				is Result.Error -> println("에러: ${result.message}")
				is Result.Loading -> println("로딩 중...")
			}
		}

		handleResult(Result.Success("데이터"))
		handleResult(Result.Error("오류 발생"))
		handleResult(Result.Loading)
	}
}

// ========================================
// 클래스 정의들
// ========================================
// 1. Data Class
data class User(val name: String, val age: Int)

// 2. 상속 - 부모 클래스 (open 필수)
open class Animal(val name: String, val age: Int) {
	open fun makeSound() {
		println("$name: 소리를 냅니다")
	}

	fun eat() {
		println("$name: 먹이를 먹습니다")
	}
}

// 자식 클래스
class Dog(name: String, age: Int) : Animal(name, age) {
	override fun makeSound() {
		println("$name: 멍멍!")
	}

	fun play() {
		println("$name: 공놀이를 합니다")
	}
}

// 3. 추상 클래스
// abstract 키워드
// 추상 메서드와 일반 메서드 혼합
abstract class Shape {
	// 추상 메서드 : 반드시 구현하라고 강제!
	// 상속은 오버라이딩 안해도 문제 없지만 추상 메서드는 해야함 -> 실수로 구현 안 하는 버그 방지!
	// 따라서 공통 로직은 일반메서드. 반드시 구현해야하는 부분은 추상메서드로 구성
	abstract fun area(): Double
}

// 다중 상속 X
class Circle(val radius: Double) : Shape() {
	// 구현 안 하면 컴파일 에러! 강제됨
	override fun area() = Math.PI * radius * radius
}

// 4. 인터페이스
// interface 키워드
// 상속으로만 하면 필요 없어도 기능 구현 해야되는데 인터페이스는 필요한 기능만 조합할 수 있음!
interface Callable {
	fun call(number: String)
}

interface Messageable {
	fun sendMessage(message: String)
}

interface Browsable {
	fun browse(url: String)
}

// 다중 상속 가능
class Smartphone : Callable, Messageable, Browsable {
	override fun call(number: String) {
		println("${number}로 전화를 겁니다")
	}

	override fun sendMessage(message: String) {
		println("메시지 전송: $message")
	}

	override fun browse(url: String) {
		println("$url 접속 중...")
	}
}

// 5. Companion Object (정적 멤버)
// Java의 static과 유사
// 클래스 이름으로 직접 접근 -> 객체 생성 없이 바로 사용 가능!
// const val로 컴파일 타임 상수
class Calculator {
	companion object {
		fun add(a: Int, b: Int) = a + b
		fun multiply(a: Int, b: Int) = a * b
	}
}

class MathUtils {
	companion object {
		const val PI = 3.14159

		fun abs(x: Int) = if (x >= 0) x else -x
	}
}

// 6. 싱글톤 (Object)
// 인스턴스가 하나만 존재
// object 키워드로 선언
object Database {
	private var isConnected = false

	fun connect() {
		isConnected = true
		println("데이터베이스 연결됨")
	}

	fun disconnect() {
		isConnected = false
		println("데이터베이스 연결 해제")
	}

	fun query(sql: String) {
		if (isConnected) {
			println("쿼리 실행: $sql")
		} else {
			println("연결되지 않음")
		}
	}
}

// 7. 중첩 클래스와 내부 클래스
class Outer {
	private val outerData = "외부 데이터"

	// 중첩 클래스
	class Nested {
		fun printMessage() {
			println("중첩 클래스")
			// println(outerData) // 외부 클래스 접근 불가
		}
	}

	// 내부 클래스
	// inner 키워드 사용
	inner class Inner {
		fun printMessage() {
			println("내부 클래스")
			println("외부 데이터 접근: $outerData") // 외부 클래스 접근 가능
		}
	}
}

// 8. Sealed Class
// 제한된 클래스 계층
// when 표현식에서 모든 경우 처리
sealed class Result {
	data class Success(val data: String) : Result()
	data class Error(val message: String) : Result()
	object Loading : Result()
}

