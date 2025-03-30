package messy_filtering.solution.query_object.jpa

import jakarta.persistence.*

/**
 * Entity Replaces DataMap and Domain Object
 * Instead of a plain domain object and a separate DataMap to link fields to columns,
 * JPA uses an entity class with annotations to define the mapping.
 */
@Entity
@Table(name = "persons")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "last_name")
    val lastName: String,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "number_of_dependents")
    val numberOfDependents: Int,
)
