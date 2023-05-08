package translator


import translator.ScalaTranslator

object format_text extends App {

    val transl:ScalaTranslator = ScalaTranslator()

    def textFormat(text:String, split:String = "\n"): String = {
          val treatmentText = if (text.length > 5000) {

            val splitText = text.split(split)

            var arrayString:String = ""
            val textNoLonger:String = splitText.map(textA => {
                  if (textA.length < 5000 - arrayString.length) {
                    arrayString = arrayString + textA
                  } else {
                    val textReturn = transl.translator(text = arrayString)
                    arrayString = ""
                    textReturn
                  }
            }
              ).mkString.replace("(","").replace(")","")

            textNoLonger
          }

          else {
            val treatmentText = translator(text = text)
            treatmentText
          }
      treatmentText
    }
    val x = textFormat(text="""In conversations at technical conferences, I often hear the same question:
  How can I learn more about database internals? I don’t even know where
  to start.” Most of the books on database systems do not go into details of
  storage engine implementation, and cover the access methods, such as BTrees,
  on a rather high level. There are very few books that cover more
  recent concepts, such as different B-Tree variants and log-structured
  storage, so I usually recommend reading papers.
  Everyone who reads papers knows that it’s not that easy: you often lack
  context, the wording might be ambiguous, there’s little or no connection
  between papers, and they’re hard to find. This book contains concise
  summaries of important database systems concepts and can serve as a
  guide for those who’d like to dig in deeper, or as a cheat sheet for those
  already familiar with these concepts.
  Not everyone wants to become a database developer, but this book will
  help people who build software that uses database systems: software
  developers, reliability engineers, architects, and engineering managers.How can I learn more about database internals? I don’t even know where
      to start.” Most of the books on database systems do not go into details of
      storage engine implementation, and cover the access methods, such as BTrees,
      on a rather high level. There are very few books that cover more
      recent concepts, such as different B-Tree variants and log-structured
      storage, so I usually recommend reading papers.
      Everyone who reads papers knows that it’s not that easy: you often lack
      context, the wording might be ambiguous, there’s little or no connection
      between papers, and they’re hard to find. This book contains concise
      summaries of important database systems concepts and can serve as a
      guide for those who’d like to dig in deeper, or as a cheat sheet for those
      already familiar with these concepts.
      Not everyone wants to become a database developer, but this book will
      help people who build software that uses database systems: software
      developers, reliability engineers, architects, and engineering managers. How can I learn more about database internals? I don’t even know where
      to start.” Most of the books on database systems do not go into details of
      storage engine implementation, and cover the access methods, such as BTrees,
      on a rather high level. There are very few books that cover more
      recent concepts, such as different B-Tree variants and log-structured
      storage, so I usually recommend reading papers.
      Everyone who reads papers knows that it’s not that easy: you often lack
      context, the wording might be ambiguous, there’s little or no connection
      between papers, and they’re hard to find. This book contains concise
      summaries of important database systems concepts and can serve as a
      guide for those who’d like to dig in deeper, or as a cheat sheet for those
      already familiar with these concepts.
      Not everyone wants to become a database developer, but this book will
      help people who build software that uses database systems: software
      developers, reliability engineers, architects, and engineering managers. How can I learn more about database internals? I don’t even know where
      to start.” Most of the books on database systems do not go into details of
      storage engine implementation, and cover the access methods, such as BTrees,
      on a rather high level. There are very few books that cover more
      recent concepts, such as different B-Tree variants and log-structured
      storage, so I usually recommend reading papers.
      Everyone who reads papers knows that it’s not that easy: you often lack
      context, the wording might be ambiguous, there’s little or no connection
      between papers, and they’re hard to find. This book contains concise
      summaries of important database systems concepts and can serve as a
      guide for those who’d like to dig in deeper, or as a cheat sheet for those
      already familiar with these concepts.
      Not everyone wants to become a database developer, but this book will
      help people who build software that uses database systems: software
      developers, reliability engineers, architects, and engineering managers. How can I learn more about database internals? I don’t even know where
      to start.” Most of the books on database systems do not go into details of
      storage engine implementation, and cover the access methods, such as BTrees,
      on a rather high level. There are very few books that cover more
      recent concepts, such as different B-Tree variants and log-structured
      storage, so I usually recommend reading papers.
      Everyone who reads papers knows that it’s not that easy: you often lack
      context, the wording might be ambiguous, there’s little or no connection
      between papers, and they’re hard to find. This book contains concise
      summaries of important database systems concepts and can serve as a
      guide for those who’d like to dig in deeper, or as a cheat sheet for those
      already familiar with these concepts.
      Not everyone wants to become a database developer, but this book will
      help people who build software that uses database systems: software
      developers, reliability engineers, architects, and engineering managers. How can I learn more about database internals? I don’t even know where
      to start.” Most of the books on database systems do not go into details of
      storage engine implementation, and cover the access methods, such as BTrees,
      on a rather high level. There are very few books that cover more
      recent concepts, such as different B-Tree variants and log-structured
      storage, so I usually recommend reading papers.
      Everyone who reads papers knows that it’s not that easy: you often lack
      context, the wording might be ambiguous, there’s little or no connection
      between papers, and they’re hard to find. This book contains concise
      summaries of important database systems concepts and can serve as a
      guide for those who’d like to dig in deeper, or as a cheat sheet for those
      already familiar with these concepts.
      Not everyone wants to become a database developer, but this book will
      help people who build software that uses database systems: software
      developers, reliability engineers, architects, and engineering managers.""")
    println(x)
}
