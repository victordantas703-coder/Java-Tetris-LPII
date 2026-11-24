package language;

import enums.LanguageEnum;
import utils.IFactory;

public class LanguageFactory implements IFactory<ILanguage, LanguageEnum> {
    
    @Override 
    public ILanguage create(LanguageEnum idiom){
        return switch (idiom) {
            case ENGLISH -> new EnglishLanguage();
            case PORTUGUESE -> new PortugueseLanguage();
        };
    }
    
}
