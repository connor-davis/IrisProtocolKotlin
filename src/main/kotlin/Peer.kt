import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress

class Peer(val name: String, port: Int) : Runnable {
    private val socket: DatagramSocket = DatagramSocket(port)
    private var running: Boolean = false

    fun start() {
        val thread = Thread(this)
        thread.start()
        running = true
    }

    fun stop() {
        running = false
        socket.close()
    }

    fun send(address: InetSocketAddress, message: String) {
        val buffer = message.toByteArray()

        val packet = DatagramPacket(buffer, buffer.size)
        packet.socketAddress = address

        socket.send(packet)
        print("::: ")
    }

    override fun run() {
        val buffer = ByteArray(1024)
        val packet = DatagramPacket(buffer, buffer.size)

        while (running) {
            try {
                socket.receive(packet)

                val message = String(buffer, 0, packet.length)
                println("\r$message")
                print("::: ")
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }
}