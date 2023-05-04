package translator

import translator.Files_management.createPdfFile

object text_test {

    def textFormat(text:String): Unit = {
        //val text = "Constructive Type theory has been a topic of research interest to computer\nscientists, mathematicians, logicians and philosophers for a number of years.\nFor computer scientists it provides a framework which brings together logic\nand programming languages in a most elegant and fertile way: program\ndevelopment and verification can proceed within a single system. Viewed\nin a different way, type theory is a functional programming language with\nsome novel features, such as the totality of all its functions, its expressive\ntype system allowing functions whose result type depends upon the value\nof its input, and sophisticated modules and abstract types whose interfaces\ncan contain logical assertions as well as signature information. A third\npoint of view emphasizes that programs (or functions) can be extracted\nfrom proofs in the logic. "
        val texts = text.split("\n").
          map(textLb =>  if (textLb.length > 5000) {
          println("This paragraph is longer.")
        } else {
            println("This paragraph is acceptably.")
          })

    }
    //val x = textFormat()
    //println(x)
}
