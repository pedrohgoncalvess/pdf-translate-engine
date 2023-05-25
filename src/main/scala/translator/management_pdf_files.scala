package translator

import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream}
import org.apache.pdfbox.text.PDFTextStripper

import java.io.File
import java.net.URLDecoder
import scala.util.matching.Regex

case class PdfToTranslate(startIn:Int = 1,
                           endIn:Int = 0,
                           path:String,
                          nameForSave:String
                         ) {
  def formatNameForSave: String = {
    val pathToSave: String = s"${this.path.substring(0, this.path.lastIndexOf("\\") + 1)}\\${this.formatArchive}"
    pathToSave
  }

  private def formatArchive: String = {
    if (!(this.nameForSave.contains(".pdf"))) {
      val formatedName: String = this.nameForSave.replace(" ", "-") + ".pdf"
      formatedName
    } else {
      val formatedName: String = this.nameForSave.replace(" ", "-")
      formatedName
    }
  }
}


  object Files_management {

    def createPdfFile(textToPage: Array[String], pathToSave: String): Unit = {

      val newDocument = new PDDocument()
      val margin = 10

      val fontURL = getClass.getResource("/arial_unicode_ms.ttf")
      val fontPath = URLDecoder.decode(fontURL.getPath, "UTF-8")
      val fontFile = new File(fontPath)
      val defaultFont = PDType0Font.load(newDocument, fontFile)

      val newPageWithText = textToPage.map(text => {
        val regex = new Regex("\\p{Cntrl}")
        val cleanText = regex.replaceAllIn(text, "")

        val blankPage = new PDPage(PDRectangle.A4)
        blankPage.setMediaBox(new PDRectangle(margin, margin, PDRectangle.A4.getWidth() - margin, PDRectangle.A4.getHeight() - margin))
        newDocument.addPage(blankPage)

        val contentStream = new PDPageContentStream(newDocument, blankPage)
        contentStream.beginText()
        contentStream.setLeading(14)
        contentStream.setFont(defaultFont, 11)
        contentStream.newLineAtOffset(70, 750)
        var y = 750
        var line = ""

        for (word <- cleanText.split(" ")) {
          val width = defaultFont.getStringWidth(line + " " + word) / 1000 * 12
          if (width > blankPage.getMediaBox.getWidth - margin - 5) {
            contentStream.showText(line.trim())
            contentStream.newLineAtOffset(0, -14)
            y -= 14
            line = ""
          }
          line += " " + word
        }
        contentStream.showText(line.trim())

        contentStream.endText()
        contentStream.close()
      }
      )
      newDocument.save(pathToSave)
      newDocument.close()
    }

    def readPdfFiles(pdfPath: String, startIn: Int = 1, endIn: Int = 0): Array[String] = {

      val document = PDDocument.load(new File(pdfPath))

      val endPage = if (endIn == 0) {
        document.getNumberOfPages
      } else {
        endIn
      }
      val rangePages = Range(startIn, endPage + 1)


      val indTextPages = rangePages.map(numberPage => {
        val stripper = new PDFTextStripper()
        stripper.setStartPage(numberPage)
        stripper.setEndPage(numberPage)

        val text = stripper.getText(document)
        text
      })

      document.close()
      val textPages = indTextPages.toArray
      textPages
    }
  }
