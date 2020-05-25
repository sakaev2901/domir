package ru.itis.controller

import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.itis.dto.ChatDto
import ru.itis.dto.FriendDto
import ru.itis.dto.MemberDto
import ru.itis.dto.ProfileDto
import ru.itis.model.Chat
import ru.itis.model.User
import ru.itis.repositories.ChatRepository
import ru.itis.repositories.UsersRepository
import ru.itis.services.UserService
import java.lang.IllegalStateException
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    lateinit var usersRepository: UsersRepository
    @Autowired
    lateinit var gson: Gson
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var chatRepository: ChatRepository

    @GetMapping("/user/{username}")
    fun getUser(@PathVariable username: String): User {
        return usersRepository.findByUsername(username)?: throw IllegalStateException()

    }


    @GetMapping("/friends/{username}")
    fun getUserFriends(@PathVariable username: String): List<User>? {
        val friends = usersRepository.findAllFriendsByUsername(username)
        return friends
    }

    @PostMapping("/addFriend")
    fun addUser(@RequestBody friendDto: FriendDto) {
        val user = usersRepository.findByUsername(friendDto.username)?: throw IllegalStateException()
        usersRepository.saveFriend(user)
    }

    @GetMapping("/profile/{username}")
    fun getSimpleUser(@PathVariable username: String): ProfileDto {
        return usersRepository.findSimpleUserByUsername(username)
    }

    @GetMapping("/profile/image/{filename:.+}")
    fun getImage(@PathVariable filename: String,  response: HttpServletResponse) {
        IOUtils.copy(userService.getAvatar(filename), response.outputStream)
        response.flushBuffer()
    }

    @PostMapping("/create")
    fun createChat(@RequestBody chatDto: ChatDto) {
        val chat = Chat()
        println(chatDto)
        chat.name = chatDto.name
        val user = usersRepository.findByUsername(chatDto.username)?: throw IllegalStateException()
        chat.owner = user
        chat.members.add(user)
        chatRepository.save(chat)
    }

    @GetMapping("/allChats")
    fun getAllChats() = chatRepository.findAll()

    @GetMapping("/user/chats/{username}")
    fun getChats(@PathVariable username: String) = chatRepository.findUserChats(
            usersRepository.findByUsername(username)!!
    )

    @PostMapping("/addMember")
    fun addMember(@RequestBody memberDto: MemberDto) {
        chatRepository.saveMember(
                usersRepository.findByUsername(memberDto.username)!!,
                chatRepository.findByName(memberDto.chatName)
        )
    }   

}