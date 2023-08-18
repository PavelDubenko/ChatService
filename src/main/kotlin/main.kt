import kotlin.reflect.KClass

data class Message(val text: String, var read: Boolean = false, val id: Int = 0)

data class Chat(val messages: MutableList<Message> = mutableListOf())

object ChatService {
    private var chats = mutableMapOf(100 to Chat())
    private var lastMessageId = 0

    fun sendMessage(userId: Int, message: Message) { //создает чат с пользователем при отправке сообщения
        chats.getOrPut(userId) { Chat() }.messages += message.copy(id = ++lastMessageId)
    }

    fun deleteMessage(userId: Int, messageId: Int):Boolean { //удаляет сообщения по ID пользователя и сообщения
        val deletedMessage = chats[userId]?.messages?.find { it.id == messageId } ?: throw ChatNotFoundException()
        chats[userId]?.messages?.remove(deletedMessage)
        return true
    }

    fun updateMessage(userId: Int, message: Message):Boolean { //редактирует сообщение
        val updatedMessage = chats[userId]?.messages?.find { it.id == message.id } ?: throw ChatNotFoundException()
        chats[userId]?.messages?.remove(updatedMessage)
        chats[userId]?.messages?.add(message)
        return true
    }

    fun deleteChat(userId: Int):Boolean { //удаляет все сообщения в чате по ID
        chats[userId]?.messages?.clear()
        return true
    }


    fun printChats() { //выводит на экран все чаты
        println(chats)
    }

    fun getUnreadChatsCount() =
        chats.values.count { chat -> chat.messages.any { !it.read } } //показывает кол-во непрочитанных чатов

    fun getLastMessages(): List<String> =
        chats.values.map { chat -> chat.messages.lastOrNull()?.text ?: "No messages" } //показывает последние сообщения

    fun getMessages(
        userId: Int,
        count: Int,
    ): List<Message> { //показывает необходимое кол-во сообщений и меняет статус на "прочитано"
        val chat = chats[userId] ?: throw ChatNotFoundException()
        return chat.messages.takeLast(count).onEach { it.read = true }
    }
    fun clear() {
        chats = mutableMapOf()
        lastMessageId = 0
    }


}

class ChatNotFoundException : Throwable()

fun main() {
    ChatService.sendMessage(1, Message("Hi"))
    ChatService.sendMessage(2, Message("Hi"))
    ChatService.sendMessage(2, Message("hello"))
    ChatService.printChats()
    ChatService.deleteChat(1)
    ChatService.printChats()
    ChatService.clear()
    ChatService.printChats()
}