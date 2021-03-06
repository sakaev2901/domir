package ru.itis.repositories

import org.springframework.stereotype.Repository
import ru.itis.model.Chat
import ru.itis.model.Message
import ru.itis.model.User
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
open class MessageRepositoryImpl : MessageRepository {


    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Transactional
    override fun save(message: Message) {
        println("repo")
        entityManager.persist(message)
        println("here")
    }

    override fun findAllRoomMessages(chat: Chat) =  entityManager.createQuery("select c from Message c where c.chat = :chat", Message::class.java)
                .setParameter("chat", chat)
                .resultList



}