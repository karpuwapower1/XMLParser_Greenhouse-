package by.training.task04.karpilovich.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.task04.karpilovich.command.Command;
import by.training.task04.karpilovich.command.constant.PageManager;

public class EmptyCommand implements Command {

	@Override
	public PageManager execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return PageManager.ERROR;
	}

}
