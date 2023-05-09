package translator

import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream}
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

case class PdfToTranslate(startIn:Int,
                           endIn:Int,
                           path:String,
                          nameForSave:String)  {
  def formatNameForSave: String = {
    val pathToSave:String = s"${this.path.substring(0,this.path.lastIndexOf("\\") + 1)}\\${this.nameForSave}"
    pathToSave
  }

}


object Files_management {

  def createPdfFile(textToPage:Array[String],pathToSave:String): Unit = {

    val newDocument = new PDDocument()
    val margin = 10

    val fontFile = new File("C:\\Users\\Pedro\\Downloads\\Arial Unicode MS\\arial_unicode_ms.ttf")
    val defaultFont = PDType0Font.load(newDocument,fontFile)

    val newPageWithText = textToPage.map(text => {
      val blankPage = new PDPage(PDRectangle.A4)
      blankPage.setMediaBox(new PDRectangle(margin, margin, PDRectangle.A4.getWidth() - margin, PDRectangle.A4.getHeight() - margin))
      newDocument.addPage(blankPage)

      val contentStream = new PDPageContentStream(newDocument, blankPage)
      contentStream.beginText()
      contentStream.setLeading(14) // Define o espaçamento entre as linhas
      contentStream.setFont(defaultFont, 11) // Define a fonte e o tamanho da fonte
      contentStream.newLineAtOffset(70, 750) // Define a posição inicial do texto
      var y = 750 // Posição Y inicial
      var line = ""

      for (word <- text.split(" ")) {
        val width = defaultFont.getStringWidth(line + " " + word) / 1000 * 12 // Calcula a largura do texto atual
        if (width > blankPage.getMediaBox.getWidth - margin - 5) { // Se a largura do texto exceder a margem direita, quebre a linha
          contentStream.showText(line.trim())
          contentStream.newLineAtOffset(0, -14)
          y -= 14
          line = ""
        }
        line += " " + word
      }
      // Mostra o restante do texto
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
