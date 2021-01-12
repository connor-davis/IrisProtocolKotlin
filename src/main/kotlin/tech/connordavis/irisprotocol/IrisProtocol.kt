package tech.connordavis.irisprotocol

import java.net.InetSocketAddress
import java.util.*

object IrisProtocol {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        var name = System.getenv("NAME").orEmpty()
        var port = System.getenv("PORT").orEmpty()
        var destinationIP = System.getenv("UDP_DESTINATION_IP").orEmpty()
        var destinationPORT = System.getenv("UDP_DESTINATION_PORT").orEmpty()

        println("::: Configuration :::")

        print("Name::: ")
        if (name.isEmpty()) name = scanner.nextLine()
        else print("$name\n")

        print("PORT::: ")
        if (port.isEmpty()) port = scanner.nextLine()
        else print("$port\n")

        print("DESTINATION IP::: ")
        if (destinationIP.isEmpty()) destinationIP = scanner.nextLine()
        else print("$destinationIP\n")


        print("DESTINATION PORT::: ")
        if (destinationPORT.isEmpty()) destinationPORT = scanner.nextLine()
        else print("$destinationPORT\n")

        println(":::::::::::::::::::::")

        val peer = Peer(name, port.toInt())
        val address = InetSocketAddress(destinationIP, destinationPORT.toInt())

        println("::: Server Started :::")
        print("::: ")

        peer.start()

        while (true) {
            var message = scanner.nextLine()

            if (message.isEmpty()) break
            message = "$name::: $message"

            peer.send(address, message)
        }

        scanner.close()
        peer.stop()

        println("::::::::::::::::::::::")
    }
}