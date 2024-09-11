package context;

import entity.WordTable;
import factory.WordTableFactory;

public class WordTableContext {
    private WordTableFactory factory;

    public void registerFactory(WordTableFactory factory) {
        this.factory = factory;
    }
    public WordTable getFactory() {
        return factory.createWordTable();
    }

}
