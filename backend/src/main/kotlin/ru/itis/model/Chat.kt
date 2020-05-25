package ru.itis.model

import java.util.*
import javax.persistence.*

@Entity
class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0
    lateinit var name: String
    @ManyToOne
    lateinit var owner: User
    @ManyToMany(fetch = FetchType.EAGER)
    var members: MutableList<User> = LinkedList<User>()
    @OneToMany(fetch = FetchType.EAGER)
    lateinit var messages: MutableList<Message>
}