package ru.itis.repositories

import ru.itis.model.Chat
import ru.itis.model.User

interface ChatRepository {
    fun save(chat: Chat)
    fun findAll(): MutableList<Chat>
    fun findByName(name: String): Chat
    fun findUserChats(user: User): List<Chat>
    fun saveMember(user: User, chat: Chat)
}