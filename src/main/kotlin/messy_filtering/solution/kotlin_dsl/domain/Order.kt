package messy_filtering.solution.kotlin_dsl.domain

import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val status: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,
)
