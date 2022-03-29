package Models;

public class ModelFactory {//do we need the modelfactory and it's family classes?
    private DataModel dataModel;

    public DataModel getDataModel() {
        if (dataModel == null) {
            dataModel = new DataModelManager();
        }
        return dataModel;
    }

}
