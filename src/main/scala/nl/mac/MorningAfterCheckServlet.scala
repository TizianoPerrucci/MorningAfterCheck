package nl.mac

import java.io.File
import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.util.Log

class MorningAfterCheckServlet extends ScalatraServlet with ScalateSupport {
  val log = Log(getClass);

  before() {
    templateEngine.workingDirectory = new File("Working/templates")
    templateEngine.allowReload = true
    templateEngine.allowCaching = false

    contentType = "text/html"
  }


  post("/reaction") {
    log.info("Parameters: " + params)
    layoutTemplate("reaction",
      ("habits", params("h").toDouble), ("weight", params("w").toDouble),
      ("percentage", params("p").toDouble), ("quantity", params("q").toDouble))
  }


  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path => layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

}
