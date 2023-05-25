import requests.Response

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import java.net.URLEncoder
import org.jsoup.Jsoup

import scala.concurrent.duration.DurationInt

object futures_reqs extends App {

  val target_language = "pt"
  val source_language = "en"
  val texts = Array("Hello my friend","\nswaron is a free teaching platform that combines expert curation with the practicality of a website that you only need to search for when you are curious/difficult to learn.","I dont give a fuck","Hey bro")

  val translations = texts.map(text => {
    val translatedText = testFutureRequest(text)
    translatedText.onComplete {
      case Success(translated) => println(translated)
      case Failure(err) => println(err)
    }
    translatedText
  }
  )
  val result = Await.result(Future.sequence(translations.toList), 10.seconds)

  def testFutureRequest(text: String): Future[String] = {

    val escaped_text = URLEncoder.encode(text, "UTF-8").replace("+", "%20")
    val url = s"https://translate.google.com/m?tl=${target_language}&sl=${source_language}&q=$escaped_text"

    val r:Future[Response] = Future {
      requests.get(url)
    }
    r.map { response =>
      val doc = Jsoup.parse(response.text())
      val result = doc.select(".result-container").text()
      result
    }
  }
}
