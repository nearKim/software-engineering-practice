package messy_filtering.problem

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val ldap: String,
    val isActive: Boolean,
    val age: Int,
    val createdAt: LocalDateTime,
)
