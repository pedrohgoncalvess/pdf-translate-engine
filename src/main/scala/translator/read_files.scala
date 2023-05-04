package translator

import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream}
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File


object Files_management {

  def createPdfFile(text:String,pathToSave:String): Unit = {

    val defaultFont = PDType1Font.COURIER

    val newDocument = new PDDocument()
    val blankPage = new PDPage(PDRectangle.A4)
    val margin = 10
    blankPage.setMediaBox(new PDRectangle(margin, margin, PDRectangle.A4.getWidth() - margin, PDRectangle.A4.getHeight() - margin))
    newDocument.addPage(blankPage)

    val contentStream = new PDPageContentStream(newDocument, blankPage)
    contentStream.beginText()
    contentStream.setLeading(14) // Define o espaçamento entre as linhas
    contentStream.setFont(defaultFont, 9) // Define a fonte e o tamanho da fonte
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

    newDocument.save(pathToSave)
    newDocument.close()
  }
//  createPdfFile(pathToSave = "C:\\Users\\Pedro\\Desktop\\Studies\\Books\\test.pdf",text = "I AM A DATA ENGINEER. I AM A DATA ENGINEER. I AM A DATA ENGINEER. I AM A DATA ENGINEER. I AM A DATA ENGINEER. I AM A DATA ENGINEER. I AM A DATA ENGINEER. I AM A DATA ENGINEER")

  def readPdfFiles(pdfPath: String, startIn: Int = 1, endIn: Int = 0): Unit = {
  val document = PDDocument.load(new File(pdfPath))

    val endPage = if (endIn == 0) {
      document.getNumberOfPages
    } else {
      endIn
    }

  val rangePages = Range(startIn,endPage+1)

  val stripper = new PDFTextStripper()
  val text = stripper.getText(document)

  document.close()
}
//   val pdfFilePath = "C:\\Users\\Pedro\\Desktop\\Studies\\Books\\A-Mind-For-Numbers-How-to-Excel-at-Math-and-Science.pdf"
//  readPdfFiles(pdfFilePath,startIn = 6, endIn = 214)

}
