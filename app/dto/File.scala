package dto

case class File(fileName: String, content: Array[Byte], contentLength: Long, contentType: String, key: String)
