package nl.mac

import java.io.File
import org.scalatra._
import scalate.ScalateSupport
import org.apache.log4j.Logger

class MorningAfterCheckServlet extends ScalatraServlet with ScalateSupport {

  private val log: Logger = Logger.getLogger(classOf[MorningAfterCheckServlet])

  before() {
    templateEngine.workingDirectory = new File("Working/templates")
    templateEngine.allowReload = true
    templateEngine.allowCaching = false

    contentType = "text/html"
  }

  get("/") {
    <html>
      <body>
        Check your
        <a href="reaction">reaction</a>
      </body>
    </html>
  }

  get("/reaction/:h/:a/:q/:w") {
    log.info("Parameters: " + params)
    templateEngine.layout(
      "/WEB-INF/views/reaction.ssp",
      Map(
        "habits" -> params("h").toDouble,
        "alcohol" -> params("a").toDouble,
        "quantity" -> params("q").toDouble,
        "weight" -> params("w").toDouble
      )
    )
  }
  
/*
  post("/reaction") {

  }
*/

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path => layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

}
