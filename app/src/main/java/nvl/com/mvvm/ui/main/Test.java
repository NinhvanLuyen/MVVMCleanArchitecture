package nvl.com.mvvm.ui.main;

import java.util.ArrayList;

public class Test {
    private ArrayList arrayList =new ArrayList<String>();
    public void demo()
    {
        arrayList =new ArrayList<Integer>();
        arrayList.add(1);
        arrayList.add(1);
        arrayList.add(1);
    }
    public ArrayList<String> getList() {
        arrayList.add(false);
        return arrayList;
    }
}
