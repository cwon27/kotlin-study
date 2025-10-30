package org.example.intermediate

class `04_ClassesAndInterfaces` {
	fun main() {
		// 1. Class
		// 코틀린에서 클래스는 단일 상속만 지원함 -> 한번에 하나의 클래스에서만 상속할 수 있음
		// 계층구조를 이룸

		// 추상 클래스 사용
		val laptop = Electronic(name = "Laptop", price = 1000.0, warranty = 2)
		println(laptop.productInfo()) // Product: Laptop, Category: Electronic, Price: 1000.0

		// 2. Interface
		// 추상 클래스와 마찬가지로, 인터페이스를 사용하여 클래스가 상속하고 나중에 구현할 수 있는 함수와 속성 집합을 정의함
		// 이 접근 방식은 구체적인 구현 세부 사항보다는 인터페이스에 설명된 추상화에 집중하는 데 도움이 됨
		// 상태(state)를 저장할 수 없음
		// 인터페이스는 작고 집중적으로 (Single Responsibility)
		// 다중 상속 지원
		val paymentMethod = CreditCardPayment("1234 5678 9012 3456", "John Doe", "12/25")
		println(paymentMethod.initiatePayment(100.0)) // Payment of $100.0 initiated using Credit Card ending in 3456.
		println("Payment is by ${paymentMethod.paymentType}") // Payment is by Credit Card

		// Properties in Interfaces
		val triangle = Triangle(sideLength = 5.0)
		println(triangle.description) // 도형 (변의 개수: 3)
		println("둘레: ${triangle.perimeter}") // 둘레: 15.0

		val square = Square(4.0)
		println(square.description) // 도형 (변의 개수: 4)
		println("둘레: ${square.perimeter}") // 둘레: 16.0

		// 실전 예제 - Strategy Pattern
		val cart = ShoppingCart()
		cart.addItem("노트북", 1500000.0)
		cart.addItem("마우스", 30000.0)

		// 다양한 결제 방법으로 결제
		println("=== 신용카드 결제 ===")
		cart.checkout(CreditCardPayment2())

		println("\n=== 카카오페이 결제 ===")
		cart.checkout(KakaoPayPayment())
	}
}

// 추상 클래스
// 인스턴스 생성 불가
abstract class Product(val name: String, var price: Double) {
	// Abstract property for the product category
	abstract val category: String

	// A function that can be shared by all products
	fun productInfo(): String {
		return "Product: $name, Category: $category, Price: $price"
	}
}

// Product에 대한 자식 클래스
class Electronic(name: String, price: Double, val warranty: Int) : Product(name, price) {
	// 추상 속성 구현
	override val category = "Electronic"
}

// Interface 정의
interface PaymentMethod {
	// Functions are inheritable by default
	fun initiatePayment(amount: Double): String

	// 충돌 해결
	fun greet() = println("Hello from A")
}

interface PaymentType {
	val paymentType: String

	// 충돌 해결
	fun greet() = println("Hello from B")
}

// Interface 구현
// 두 인터페이스 동시 구현
class CreditCardPayment(val cardNumber: String, val cardHolderName: String, val expiryDate: String) : PaymentMethod, PaymentType {
	override fun initiatePayment(amount: Double): String {
		// Simulate processing payment with credit card
		return "Payment of $$amount initiated using Credit Card ending in ${cardNumber.takeLast(4)}."
	}

	override val paymentType: String = "Credit Card"

	// 충돌 해결
	override fun greet() {
		// 선택 1: 하나만 호출
		super<PaymentMethod>.greet()

		// 선택 2: 둘 다 호출
		// super<PaymentMethod>.greet()
		// super<PaymentType>.greet()

		// 선택 3: 새로운 구현
		// println("Hello from MyClass")
	}
}

// Properties in Interfaces
// 인터페이스는 프로퍼티를 가질 수 있지만, backing field를 가질 수 없음
// 해결방법 = 구현 클래스에서 반드시 구현 or getter를 통해 값 제공
interface Shape {
	val sides: Int // abstract

	val perimeter: Double // abstract

	// Backing Field 불가 -> 컴파일 에러!
	// val value: Int = 10

	val description: String // getter 제공
		get() = "도형 (변의 개수: $sides)"
}

class Triangle(override val sides: Int = 3, val sideLength: Double) : Shape {
	override val perimeter: Double
		get() = sides * sideLength
}

class Square(val sideLength: Double) : Shape {
	override val sides: Int = 4
	override val perimeter: Double
		get() = sides * sideLength
}

// 실전 예제 - Strategy Pattern
// 결제 전략 인터페이스
interface PaymentStrategy {
	fun pay(amount: Double): String
}

// 구체적인 전략들
class CreditCardPayment2 : PaymentStrategy {
	override fun pay(amount: Double): String {
		return "신용카드로 ${amount}원 결제"
	}
}

class KakaoPayPayment : PaymentStrategy {
	override fun pay(amount: Double): String {
		return "카카오페이로 ${amount}원 결제"
	}
}

class NaverPayPayment : PaymentStrategy {
	override fun pay(amount: Double): String {
		return "네이버페이로 ${amount}원 결제"
	}
}

// 컨텍스트
class ShoppingCart {
	private val items = mutableListOf<Pair<String, Double>>()

	fun addItem(name: String, price: Double) {
		items.add(name to price)
	}

	fun checkout(strategy: PaymentStrategy) {
		val total = items.sumOf { it.second }
		println("상품 목록:")
		items.forEach { println("  ${it.first}: ${it.second}원") }
		println("총액: ${total}원")
		println(strategy.pay(total))
	}
}


