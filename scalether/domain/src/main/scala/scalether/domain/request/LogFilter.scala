package scalether.domain.request

import scalether.domain.{Address, Word}

import scala.annotation.varargs
import scala.language.implicitConversions

case class LogFilter(topics: List[TopicFilter] = Nil,
                     address: List[Address] = Nil,
                     fromBlock: String = "latest",
                     toBlock: String = "latest") {
  @varargs def address(address: Address*):LogFilter = copy(address = address.toList)

  def blocks(fromBlock: String, toBlock: String): LogFilter =
    this.copy(fromBlock = fromBlock, toBlock = toBlock)

}

object LogFilter {
  @varargs def apply(topics: TopicFilter*): LogFilter = LogFilter(topics.toList)
}

sealed trait TopicFilter {

}

object TopicFilter {
  implicit def simple(word: Word): SimpleTopicFilter = SimpleTopicFilter(word)
  @varargs def or(word: Word*): OrTopicFilter = OrTopicFilter(word.toList)
}

case class SimpleTopicFilter(word: Word) extends TopicFilter

case class OrTopicFilter(words: List[Word]) extends TopicFilter

object OrTopicFilter {
  @varargs def apply(words: Word*): OrTopicFilter = OrTopicFilter(words.toList)
}