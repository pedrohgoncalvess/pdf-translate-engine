package translator

case class ScalaTranslator(var target_language:String = "pt", var source_language:String = "en") {

  import java.net.URLEncoder
  import java.net.URLDecoder
  import org.jsoup.Jsoup


  def translator(text: String): String = {

    val escaped_text = URLEncoder.encode(text, "UTF-8").replace("+", "%20")

    val url = s"https://translate.google.com/m?tl=${this.target_language}&sl=${this.source_language}&q=$escaped_text"

    val r = requests.get(url)
    val doc = Jsoup.parse(r.text())
    val result = doc.select(".result-container").text()
    val decodedResult = URLDecoder.decode(result, "UTF-8")
    decodedResult

  }
}
