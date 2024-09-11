package factory.impl;

import entity.WordTable;
import entity.impl.BaiCiZhanWordTable;
import factory.WordTableFactory;

public class BaiCiZhanWordTableFactory implements WordTableFactory {

    @Override
    public WordTable createWordTable() {
        return new BaiCiZhanWordTable();
    }
}
