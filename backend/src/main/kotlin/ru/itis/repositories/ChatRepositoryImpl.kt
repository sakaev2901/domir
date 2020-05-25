package ru.itis.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.itis.model.Chat
import ru.itis.model.User
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
open class ChatRepositoryImpl: ChatRepository {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun save(chat: Chat) {
        entityManager.persist(chat)
    }

    override fun findAll(): MutableList<Chat> {
        return entityManager.createQuery("select c from Chat c", Chat::class.java)
                .resultList
    }

    override fun findByName(name: String): Chat {
        return entityManager.createQuery("select c from Chat c where c.name = :name", Chat::class.java)
                .setParameter("name", name)
                .singleResult
    }

    override fun findUserChats(user: User): List<Chat> {
        return entityManager.createQuery("select c from Chat c where c.owner = :user", Chat::class.java)
                .setParameter("user", user)
                .resultList
    }

    override fun saveMember(user: User, chat: Chat) {
        chat.members.add(user)
        entityManager.merge(chat)
    }


}