package nl.mac

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler

/*
* Starts jetty for scalatra programatically
*/
object JettyLauncher {

  def main(args: Array[String]) {
    val port = if (System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

    val server = new Server(port)
    val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)

    context.addServlet(classOf[MorningAfterCheckServlet], "/*");
    context.setResourceBase("src/main/webapp")

    server.start
    server.join
  }

}