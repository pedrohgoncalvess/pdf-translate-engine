package translator

case class ScalaTranslator(target_language:String = "pt", source_language:String = "en") {

  import java.net.URLEncoder
  import org.jsoup.Jsoup
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  import requests.Response

  def translator(text: String, separator: String = "\n"): String = {
    val translatedText = if (text.length > 5000) {
      val rawTranslatedText: String = textParser(text, separator)
      rawTranslatedText
    } else {
      val rawTranslatedText: String = rawTranslator(text)
      rawTranslatedText
    }

    translatedText
  }

  def asyncTranslator(text: String): Future[String] = {

    if (text.length < 5000) {

      val escaped_text = URLEncoder.encode(text, "UTF-8").replace("+", "%20")
      val url = s"https://translate.google.com/m?tl=${this.target_language}&sl=${this.source_language}&q=$escaped_text"

      val r: Future[Response] = Future {
        requests.get(url)
      }
      r.map { response =>
        val doc = Jsoup.parse(response.text())
        val result = doc.select(".result-container").text()
        result
      }
    }
    else {
      println("This text is longer than 5000 characters.")
      val toReturn: Future[String] = Future {"None"}
      toReturn
    }

}

    private def textParser(text: String, split:String): String = {
      val treatmentText:String = {
            val splitText = text.split(split)

            var arrayString: String = ""
            val textNoLonger: String = splitText.map(textA => {
              if (textA.length < 5000 - arrayString.length) {
                arrayString = arrayString + textA
              } else {
                val textReturn = this.rawTranslator(text = arrayString)
                arrayString = ""
                textReturn
              }
            }
            ).mkString.replace("(", "").replace(")", "")

            textNoLonger
          }
          treatmentText
    }

   private def rawTranslator(text: String): String = {

    val escaped_text = URLEncoder.encode(text, "UTF-8").replace("+", "%20")

    val url = s"https://translate.google.com/m?tl=${this.target_language}&sl=${this.source_language}&q=$escaped_text"

    val r = requests.get(url)
    val doc = Jsoup.parse(r.text())
    val result = doc.select(".result-container").text()
    result
  }

  def translatePdfFile(pdf: PdfToTranslate): Unit = {
    import Files_management.{createPdfFile, readPdfFiles}

    val textPagesEn = readPdfFiles(pdf.path, startIn = pdf.startIn, endIn = pdf.endIn)

    val textPagesPt = textPagesEn.map(text => {
      val textPage = this.rawTranslator(text)
      textPage
    })

    createPdfFile(textPagesPt, pdf.formatNameForSave)

  }
}
