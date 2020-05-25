package ru.itis.repositories

import ru.itis.model.Chat
import ru.itis.model.Message
import ru.itis.model.User

interface MessageRepository {
    fun save(message: Message)
    fun findAllRoomMessages(chat: Chat): List<Message>
}