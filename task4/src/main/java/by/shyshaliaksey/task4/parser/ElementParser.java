package by.shyshaliaksey.task4.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.shyshaliaksey.task4.entity.AbstractComponent;
import by.shyshaliaksey.task4.entity.ComponentName;
import by.shyshaliaksey.task4.entity.Element;
import by.shyshaliaksey.task4.entity.TextComposite;
import by.shyshaliaksey.task4.exception.TextException;

public class ElementParser implements Chain {

	private static final String ELEMENT_WITH_WHITESPACES = "^(\\t+| +|\\n+)*([\\w()<>|&^\\\\,'~-]+)([\\t\\n \\.])*";
	private static final String EMPTY_LINE = "";
	private Chain nextChain;
	
	@Override
	public void setNextChain(Chain nextChain) {
		this.nextChain = nextChain;
	}

	@Override
	public void parse(AbstractComponent abstractComponent, String content) throws TextException {
		if (abstractComponent.getComponentName() == ComponentName.ELEMENT) {
			Pattern pattern = Pattern.compile(ELEMENT_WITH_WHITESPACES);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String spaceSymbolsBeforeElement = matcher.group(1);
				if (spaceSymbolsBeforeElement != null) {
					for (String spaceSymbol: spaceSymbolsBeforeElement.split(EMPTY_LINE)) {
						abstractComponent.add(new Element(ComponentName.SYMBOL, spaceSymbol));
					}
				}

				String newElement = matcher.group(2);
				nextChain.parse(abstractComponent, newElement);
				
				String spaceSymbolsAfterElement = matcher.group(3);
				if (spaceSymbolsAfterElement != null) {
					for (String spaceSymbol: spaceSymbolsAfterElement.split(EMPTY_LINE)) {
						abstractComponent.add(new Element(ComponentName.SYMBOL, spaceSymbol));
					}
				}
			}
		} else {
			nextChain.parse(abstractComponent, content);
		}
	}
	
}
