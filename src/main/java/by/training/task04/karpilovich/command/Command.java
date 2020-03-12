package by.training.task04.karpilovich.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.task04.karpilovich.resource.PageManager;

public interface Command {

	PageManager execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
