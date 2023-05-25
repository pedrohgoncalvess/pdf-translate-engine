package translator

object main extends App{

  val ptMessage = "Olá, meu amigo."
  val enMessage = "Hello, my friend."

  /*translater en > pt*/
  val translatorPT = ScalaTranslator()
  println(translatorPT.translator(enMessage))

  /*translater pt > en*/
  val translatorEN = ScalaTranslator(target_language="en", source_language= "pt")
  println(translatorEN.translator(ptMessage))



  /*instantiating pdf for translate*/
  val typeTheory = PdfToTranslate(startIn=2,path="C:\\Users\\Pedro\\Desktop\\Studies\\Books\\Type_theory_and_functional_programming.pdf",nameForSave="Teoria_de_tipos_func_programming.pdf")

  /*translating pdf*/
  val pdfTranslater = translatorPT.translatePdfFile(typeTheory)

  }