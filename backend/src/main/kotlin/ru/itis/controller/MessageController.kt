package ru.itis.controller

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itis.model.Chat
import ru.itis.model.Message
import ru.itis.repositories.ChatRepository
import ru.itis.repositories.MessageRepository
import ru.itis.services.MessageService

@RestController
@RequestMapping("/api")
class MessageController {
    @Autowired
    lateinit var messageService: MessageService
    @Autowired
    lateinit var chatRepository: ChatRepository
    @Autowired
    lateinit var messageRepository: MessageRepository

    @GetMapping("/messages/{firstUser}-{secondUser}")
    fun getMessages(@PathVariable firstUser: String, @PathVariable secondUser: String) = messageService.getAllRoomMessage(
            firstUser, secondUser
    )

    @GetMapping("/chat/{name}")
    fun getChat(@PathVariable name: String): List<Message> {
        return messageRepository.findAllRoomMessages(chatRepository.findByName(name))
    }
}