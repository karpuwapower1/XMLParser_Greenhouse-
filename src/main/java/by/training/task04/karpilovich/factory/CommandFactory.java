package by.training.task04.karpilovich.factory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.command.Command;
import by.training.task04.karpilovich.command.CommandType;
import by.training.task04.karpilovich.command.impl.EmptyCommand;
import by.training.task04.karpilovich.command.impl.ParserCommand;

public class CommandFactory {

	private final Lock lock = new ReentrantLock();
	private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

	private CommandFactory() {
	}

	private static class CommandFactoryInstanceHolder {
		public static final CommandFactory INSTANCE = new CommandFactory();
	}

	public static CommandFactory getInstance() {
		return CommandFactoryInstanceHolder.INSTANCE;
	}

	public Command getCommand(String commandName) {
		Command command;
		try {
			lock.lock();
			CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
			LOGGER.debug(commandName);
			switch (commandType) {
			case PARSE:
				command = new ParserCommand();
				break;
			default:
				command = new EmptyCommand();
				break;
			}
			return command;
		} catch (IllegalArgumentException | NullPointerException e) {
			LOGGER.debug("Illegal command " + commandName);
			command = new EmptyCommand();
			return command;
		} finally {
			lock.unlock();
		}
	}

}
