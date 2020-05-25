package ru.itis.controller

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import ru.itis.dto.MessageDto
import ru.itis.model.Chat
import ru.itis.model.Message
import ru.itis.repositories.ChatRepository
import ru.itis.repositories.UsersRepository
import ru.itis.services.MessageService
import java.lang.IllegalStateException

@Controller
class ChatController {

    @Autowired
    lateinit var simpMessagingTemplate: SimpMessagingTemplate
    @Autowired
    lateinit var usersRepository: UsersRepository
    @Autowired
    lateinit var messageService: MessageService
    @Autowired
    lateinit var gson: Gson
    @Autowired
    lateinit var chatRepository: ChatRepository

    @MessageMapping("/message/{room}")
    fun chatting(@DestinationVariable room: String, messageDto: MessageDto){
        val message = Message()
        message.sender = usersRepository.findByUsername(messageDto.username)?: throw IllegalStateException()
        message.content = messageDto.content
        message.chat = chatRepository.findByName(messageDto.chatName)
        messageService.saveMessage(message)
        simpMessagingTemplate.convertAndSend("/chat/", message)
        simpMessagingTemplate.convertAndSend("/chat/$room", message)
    }
}