import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChatServiceTest {
    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }


    @Test
    fun sendMessageSuccess() {
        val chats = mutableMapOf<Int, Message>()
        ChatService.sendMessage(1,Message("Hi"))
        ChatService.sendMessage(2,Message("Hi"))
        assertNotNull(chats.values)
    }

    @Test(expected = ChatNotFoundException::class)
    fun deleteMessageNotExist() {
        ChatService.sendMessage(1, Message("hi"))
        ChatService.deleteMessage(2,0)
    }
    @Test
    fun deleteMessageExist() {
        ChatService.sendMessage(1, Message("hi"))
        assertTrue(ChatService.deleteMessage(1,1))
    }

    @Test(expected = ChatNotFoundException::class)
    fun updateMessageNotExist() {
        ChatService.sendMessage(1, Message("hi"))
        ChatService.updateMessage(2,Message(id = 1, text = "hello"))
    }
    @Test
    fun updateMessageExist() {
        ChatService.sendMessage(1, Message("hi"))
        assertTrue(ChatService.updateMessage(1,Message(id = 1, text = "hello")))
    }

    @Test
    fun deleteChat() {
        ChatService.sendMessage(1, Message("hi"))
        assertTrue(ChatService.deleteChat(1))
    }

    @Test
    fun getUnreadChatsCount() {
        ChatService.sendMessage(1, Message("hi"))
        ChatService.sendMessage(2, Message("hello"))
        val result = ChatService.getUnreadChatsCount()
        assertEquals(2,result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getMessagesNonExistChat() {
        ChatService.sendMessage(1, Message("hi"))
        ChatService.sendMessage(2, Message("hello"))
        ChatService.getMessages(3,5)
    }

}