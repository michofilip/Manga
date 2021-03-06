package dto

import db.files.model.FileTable.FileEntity

case class File(fileName: String, content: Array[Byte], contentLength: Long, contentType: String, key: String)

object File {
    def fromEntity(fileEntity: FileEntity): File = {
        File(
            fileName = fileEntity.fileName,
            content = fileEntity.content,
            contentLength = fileEntity.contentLength,
            contentType = fileEntity.contentType,
            key = fileEntity.key
        )
    }
}
