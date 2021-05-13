package by.shyshaliaksey.task4.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.shyshaliaksey.task4.entity.AbstractComponent;
import by.shyshaliaksey.task4.entity.ComponentName;
import by.shyshaliaksey.task4.entity.TextComposite;
import by.shyshaliaksey.task4.exception.TextException;

public class SentenceParser extends Chain {

	private static final String ELEMENT = "\\t* *([\\w()<>|&^.\\\\,'~-]+)( |\\n|[.!?])+";

	@Override
	public void parse(AbstractComponent abstractComponent, String content) throws TextException {
		if (abstractComponent.getComponentName() == ComponentName.SENTENCE) {
			Pattern pattern = Pattern.compile(ELEMENT);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String element = content.substring(matcher.start(), matcher.end());
				TextComposite elementComposite = new TextComposite(ComponentName.ELEMENT, abstractComponent);
				this.nextInChain.parse(elementComposite, element);
				abstractComponent.add(elementComposite);
			}
		} else {
			this.nextInChain.parse(abstractComponent, content);
		}
	}

}
