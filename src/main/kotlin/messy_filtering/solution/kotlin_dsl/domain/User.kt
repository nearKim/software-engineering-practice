package messy_filtering.solution.kotlin_dsl.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val isActive: Boolean,
    val ldap: String,
    @OneToMany(mappedBy = "user")
    val orders: List<Order> = emptyList(),
)
