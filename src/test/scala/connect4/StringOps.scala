package connect4

import java.io.ByteArrayInputStream

object StringOps {

  implicit class RichString(x: String) {
    def byteInputStream = new ByteArrayInputStream(x.getBytes)
  }
}
