package dto

import db.files.model.FileTable.FileEntity

case class File(fileName: String, content: Array[Byte], contentLength: Long, contentType: String, key: String)

object File {
    def fromEntity(fileEntity: FileEntity): File = fileEntity match {
        case FileEntity(fileName, content, contentLength, contentType, key) =>
            File(fileName, content, contentLength, contentType, key)
    }
}
