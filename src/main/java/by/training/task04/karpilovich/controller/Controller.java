package by.training.task04.karpilovich.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.command.Command;
import by.training.task04.karpilovich.command.constant.PageManager;
import by.training.task04.karpilovich.command.constant.RequestParameterManager;
import by.training.task04.karpilovich.factory.CommandFactory;

@WebServlet("/")
@MultipartConfig(maxFileSize=1024 * 10)
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(Controller.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		processRequest(request, responce);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		processRequest(request, responce);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandType = request.getParameter(RequestParameterManager.COMMAND.getParameterName());
		CommandFactory factory = CommandFactory.getInstance();
		Command command = factory.getCommand(commandType);
		PageManager page = command.execute(request, response);
		request.getRequestDispatcher(page.getPage()).forward(request, response);
	}
}
