package messy_filtering.solution.query_object.examples

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Column(name = "is_active")
    val isActive: Boolean,
    @Column(name = "age")
    val age: Int,
    @Column(name = "ldap")
    val ldap: String,
)
