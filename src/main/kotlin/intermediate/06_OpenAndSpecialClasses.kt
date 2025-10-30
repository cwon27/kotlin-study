package org.example.intermediate

class `06_OpenAndSpecialClasses` {
	fun main() {
		// Open Class
		val child = ChildClass()
		child.method() // 재정의됨
		child.finalMethod() // 재정의 불가능

		// 예제: 프로퍼티 오버라이딩
		val triangle = TriangleEx()
		triangle.describe() // 삼각형: 3개의 변

		val rectangle = RectangleEx()
		rectangle.describe() // 사각형: 4개의 변을 가진 다각형

		// Sealed Classes (봉인 클래스)
		val network = NetworkManager()

		println("=== 네트워크 연결 테스트 ===")
		network.connect("MyWiFi", "password123")
		Thread.sleep(1000)
		network.disconnect()

		println("\n=== 인증 실패 테스트 ===")
		network.connect("MyWiFi", "123")

		// Enum Classes (열거형 클래스)
		// 모든 enum 값 가져오기
		for (direction in Direction.entries) {
			println("${direction.name} - 순서: ${direction.ordinal}")
		}

		// 이름으로 enum 값 가져오기
		val color = Color.valueOf("RED")
		println("색상: ${color.name}, RGB: ${color.rgb}")

		// Value Classes (값 클래스)
		val userId = UserId("user123")
		val email = Email("user@example.com")

		// 타입 안전성: 순서를 바꾸면 컴파일 에러
		// sendEmail(email, userId)  // 컴파일 에러!
		sendEmail(userId, email)
	}
}

// 코틀린에서는 기본적으로 모든 Class가 final임. 즉, 다른 클래스가 상속 불가! <-> Java
// Open Class
// 상속 가능한 클래스

// Final 클래스 (기본)
class FinalClass {
	fun method() {
		println("재정의 불가능")
	}
}

// Open 클래스 (상속 가능)
// 오버라이드하려면 open 키워드 필요
open class OpenClass {
	open fun method() {
		println("재정의 가능")
	}

	fun finalMethod() {
		println("재정의 불가능")
	}
}

// 상속
class ChildClass : OpenClass() {
	override fun method() {
		println("재정의됨")
	}

	// finalMethod()는 오버라이드 불가능
}

// 예제: 프로퍼티 오버라이딩
open class ShapeEx {
	open val name: String = "도형"
	open val sides: Int = 0

	open fun describe() {
		println("${name}: ${sides}개의 변")
	}
}

class TriangleEx : ShapeEx() {
	override val name: String = "삼각형"
	override val sides: Int = 3
}

class RectangleEx : ShapeEx() {
	override val name: String = "사각형"
	override val sides: Int = 4

	override fun describe() {
		println("$name: ${sides}개의 변을 가진 다각형")
	}
}

// Sealed Classes (봉인 클래스)
// 제한된 클래스 계층 구조를 정의하는 특별한 추상 클래스
// 하위 클래스는 같은 패키지/모듈에만 정의 가능
// when 표현식과 함께 사용 시 완전성 검사 (exhaustive)
// 타입 안전한 상태 관리에 최적
// 다중 인스턴스 가능 (Enum과의 차이점)
sealed class NetworkState {
	data object Disconnected : NetworkState()
	data object Connecting : NetworkState()
	data class Connected(val ssid: String, val signalStrength: Int) : NetworkState()
	sealed class Error : NetworkState() {
		data object Timeout : Error()
		data object AuthenticationFailed : Error()
		data class Unknown(val message: String) : Error()
	}
}

class NetworkManager {
	private var currentState: NetworkState = NetworkState.Disconnected

	fun connect(ssid: String, password: String) {
		currentState = NetworkState.Connecting
		displayStatus()

		Thread.sleep(500)

		currentState = when {
			password.length < 8 -> NetworkState.Error.AuthenticationFailed
			ssid.isEmpty() -> NetworkState.Error.Unknown("SSID가 비어있습니다")
			else -> NetworkState.Connected(ssid, (50..100).random())
		}
		displayStatus()
	}

	fun disconnect() {
		currentState = NetworkState.Disconnected
		displayStatus()
	}

	private fun displayStatus() {
		val status = when (val state = currentState) {
			NetworkState.Disconnected -> "연결 끊김"
			NetworkState.Connecting -> "연결 중..."
			is NetworkState.Connected ->
				"연결됨: ${state.ssid} (신호: ${state.signalStrength}%)"
			NetworkState.Error.Timeout -> "시간 초과"
			NetworkState.Error.AuthenticationFailed -> "인증 실패"
			is NetworkState.Error.Unknown -> "알 수 없는 오류: ${state.message}"
		}
		println(status)
	}
}

// Enum Classes (열거형 클래스)
// 제한된 상수 집합을 표현하는 특별한 클래스
// 각 상수는 단일 인스턴스로 존재함
// 각 열거 상수는 싱글톤 인스턴스
// name, ordinal 프로퍼티 자동 제공 및 entries, valueOf() 메서드 자동 생성
enum class Direction {
	NORTH, SOUTH, EAST, WEST
}

enum class Color(val rgb: Int) {
	RED(0xFF0000),
	GREEN(0x00FF00),
	BLUE(0x0000FF)
}

// Value Classes (값 클래스)
// 단일 값을 래핑하면서도 런타임 오버헤드가 없는 특별한 클래스
// 컴파일러가 가능한 경우 래퍼를 제거하고 원시 값을 사용함
// @JvmInline 어노테이션 필요 (JVM)
@JvmInline
value class UserId(val value: String)

@JvmInline
value class Email(val value: String) {
	init {
		require(value.contains("@")) { "유효한 이메일이 아닙니다" }
	}
}

fun sendEmail(userId: UserId, email: Email) {
	println("${userId.value}에게 ${email.value}로 이메일 전송")
}