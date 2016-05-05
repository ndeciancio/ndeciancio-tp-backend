package escuelita;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

public class WebApp {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);


	public static void main(String[] args) {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setContextConfigLocation("classpath:application-context.xml");

		ServletContextHandler handler = new ServletContextHandler(NO_SESSIONS);
		handler.setContextPath("/escuelita");
		handler.addServlet(new ServletHolder(dispatcherServlet), "/*");

		Server server = new Server();
		ServerConnector serverConnector = new ServerConnector(server);
		serverConnector.setPort(8080);
		server.setConnectors(new Connector[] { serverConnector });
		server.setHandler(handler);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			LOGGER.error("No se pudo iniciar la aplicacion",e);
		}
	}

}
