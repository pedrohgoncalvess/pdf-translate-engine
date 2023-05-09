package translator

case class ScalaTranslator(target_language:String = "pt", source_language:String = "en") {

  import java.net.URLEncoder
  import org.jsoup.Jsoup

  def translator(text: String): String = {

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
      val textPage = this.translator(text)
      textPage
    })

    createPdfFile(textPagesPt, pdf.formatNameForSave)

  }
}
