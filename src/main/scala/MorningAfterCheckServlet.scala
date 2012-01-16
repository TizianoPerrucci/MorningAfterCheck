import java.io.File
import org.scalatra._
import scalate.ScalateSupport

class MorningAfterCheckServlet extends ScalatraServlet with ScalateSupport {

  before() {
    templateEngine.workingDirectory = new File("Working/templates")
    templateEngine.allowReload = true
    templateEngine.allowCaching = false

    contentType = "text/html"
  }

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say
        <a href="reaction">hello to Scalate</a>
        .
      </body>
    </html>
  }

  get("/reaction/:h/:a/:q/:w") {
    params
    println(params("h").toInt)
    templateEngine.layout("/WEB-INF/views/reaction.ssp",
      Map("habits" -> params("h").toInt , "alcohol" -> params("a").toInt, "quantity" -> params("q").toInt, "weight" -> params("w").toInt))
  }

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path =>
        contentType = "text/html"
        layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
