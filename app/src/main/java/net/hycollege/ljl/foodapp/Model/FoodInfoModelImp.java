package net.hycollege.ljl.foodapp.Model;

import net.hycollege.ljl.foodapp.bean.FoodBean;
import net.hycollege.ljl.foodapp.bean.User;

public interface FoodInfoModelImp {
    void InsertNotetoData(FoodBean foodBean);
    void DeleteNotefromData(FoodBean foodBean);
    void ChangeNotetoData(FoodBean foodBean);
}
