package com.lg.moneyregister;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2016-02-19.
 */
public interface dbInterface extends Serializable {
    void insertData(MoneyData data);
    void insertPersonData(PersonData data);
    void updatePersonData(PersonData data);
    List<MoneyData> getDataList();
    List<MoneyData> getSpecificDataList(String date);
    PersonData getPersonData();
}
